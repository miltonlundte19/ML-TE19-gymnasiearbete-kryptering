package main;

import setings.HYBRIDsettings;
import setings.RESsettings;
import setings.Settings;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class manuleSettingsRESHYBRId {
    //------- globala -------------------------------------
    static byte id = 2;
    // 1 = RES : 2 = Hybrid.

    static boolean encrypt = true;
    // true = encryption : fals = decryption.

    static boolean storToFile = false;
    // true = stores output to a file.

    static boolean manualSnapthot = true;
    // true = if the program shall pause for the user to take a snapshot.

    static short numOfRepetitions = 1;
    // the number of times the program shall encrypt or decrypt the message. (min 1)

    //----------------------------------------------------------
    //-------- res -----------------------------------------
    static boolean PrivetKey = true;
    // true = privet : // fals = publik.

    static File messageInStartPath = new File("");
    // start path for a file choser for the in message.

    static File messageOutStartPath = new File("");
    // Start Path for the enkrypted or dekrypted messige out.

    static File resKeyStartPath = new File("");
    // start path for a file choser for the keyfile.

    static boolean generateNyResKey = false;
    // true = program generats ny key:s

    //----------------------------------------------------------
    //-------- Hybrid -----------------------------------------
    // set variable for res also.
    static byte iv = 0;
    // 0-12 for a set of pre generated iv:s (-1 for override, manual inserting at:[rad]).

    static boolean aesKeystoredInFile = false;
    // If the key for aes is stored in the file

    static File aesKeyStartPath = new File("");
    // start path for a file choser for the keyfile.

    static boolean generateNyAesKey = false;
    // true = program generats a ny key

    // message is res message variable.

    //----------------------------------------------------------

    public static void main(String[] args) {
        settingsfile = new File("setingsfile.txt");
        try {
            settingsfile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setSettings();
        if (id == 0) {
            System.out.println("Manual AES settings not implemented");
            System.exit(404);
        }
        if (id == 1)
            resSettings();
        if (id == 2)
            hybridSettings();
        System.out.println(settings.toString());
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(settingsfile));
            objectOutputStream.writeObject(settings);
            objectOutputStream.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //------------ Fixt variable ----------------------------------
    static Settings settings = new Settings();
    static File settingsfile;
    static RESsettings res;
    static HYBRIDsettings hybrid;
    static FileNameExtensionFilter keyfilter = new FileNameExtensionFilter("Key file filter", "key");

    //-------------------------------------------------------------

    private static void setSettings() {
        settings.setId(id);
        settings.setCheekEncryption(encrypt);
        settings.setStorTOfile(storToFile);
        settings.setManulesnapshot(manualSnapthot);
        settings.setNumOFrepeteson(numOfRepetitions);
    }

    private static File getFile(File startPath, boolean lengtchek, boolean diraktory , String title) {
        return getFile(startPath,lengtchek,diraktory,title,null);
    }

    private static File getFile(File startPath, boolean lengtchek, boolean diraktory, String title, FileNameExtensionFilter filter) {
        JFileChooser fileChooser = new JFileChooser(startPath);
        fileChooser.setDialogTitle(title);
        if (filter != null) {
            fileChooser.setFileFilter(filter);
        }
        if (diraktory) {
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            lengtchek = false;
        }
        int i;
        long lengt;
        boolean c = true;
        File file = new File("");
        while (c) {
            i = fileChooser.showOpenDialog(null);
            if (i == 0) {
                lengt = fileChooser.getSelectedFile().length();
                if (lengtchek) {
                    if (lengt > 245) {
                        String m = "filen som är vald har " + lengt +
                                " taken och kan vara för stor för krypteringen" +
                                "\n (Max storlek är 245 taken) \n Tryk på ok om du vill fortseta";
                        int a = JOptionPane.showConfirmDialog(null, m,
                                "Filen kan vara för stor",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (a == 0) {
                            file = fileChooser.getSelectedFile();
                            c = false;
                        } else {
                            if (a == JFileChooser.CANCEL_OPTION) {
                                if (JOptionPane.showConfirmDialog(null,
                                        "user\n" + "pres ok to continue") != 0) {
                                    c = false;
                                    file = null;
                                }
                            }
                            if (a == JFileChooser.ERROR_OPTION) {
                                if (JOptionPane.showConfirmDialog(
                                        null,
                                        "error\n" + "pres ok to continue") != 0) {
                                    c = false;
                                    file = null;
                                }
                            }
                        }
                    }
                } else {
                    file = fileChooser.getSelectedFile();
                    c = false;
                }
            }
            if (i != 0) {
                if (i == JFileChooser.CANCEL_OPTION) {
                    if (JOptionPane.showConfirmDialog(null,
                            "user\n" + "pres ok to continue") != 0) {
                        c = false;
                        file = null;
                    }
                }
                if (i == JFileChooser.ERROR_OPTION) {
                    if (JOptionPane.showConfirmDialog(null,
                            "error\n" + "pres ok to continue") != 0) {
                        c = false;
                        file = null;
                    }
                }
            }
        }
        return file;
    }

    private static void resSettings() {
        res = new RESsettings();
        res.setPriORpub(PrivetKey);
        File resKeyDir;
        if (generateNyResKey) {
            File[] resKeyPair;
            resKeyDir = getFile(resKeyStartPath, false, true);
            if (resKeyDir == null) {
                System.err.println("inte implementerat om direktory är null!!!");
                System.exit(-4);
            }
            resKeyPair = getResFilePair(resKeyDir);
            riteResKeyToFile(resKeyPair);
            if (PrivetKey) {
                if (testResKey(PrivetKey, resKeyPair[0])) {
                    res.setKeyfile(resKeyPair[0]);
                } else {
                    System.err.println("An error a kurade setting the privet key");
                    System.exit(-3);
                }
            } else {
                if (testResKey(PrivetKey, resKeyPair[1])) {
                    res.setKeyfile(resKeyPair[1]);
                } else {
                    System.err.println("An error a kurade setting the publik key");
                    System.exit(-3);
                }
            }
        } else {
            String nyckelError = """
                    Nyckeln som är vald är inte en res nyckel
                    vill du försöka i gen
                    (annars stängs programmet av)
                    """;
            String fileError = """
                    Filen som valdes existera inte eller så den tom
                    vill du försöka i gen
                    (annars stängs programmet av)
                    """;
            if (PrivetKey) {
                File resKeyPrivet;
                while (true) {
                    resKeyPrivet = getFile(resKeyStartPath,false,false, keyfilter);
                    if (resKeyPrivet.exists())
                        if (resKeyPrivet.length() != 0)
                            if (testResKey(PrivetKey, resKeyPrivet)) {
                                res.setKeyfile(resKeyPrivet);
                                break;
                            } else if (JOptionPane.showConfirmDialog(null, nyckelError) != 0)
                                System.exit(1);
                    if (JOptionPane.showConfirmDialog(null, fileError) != 0)
                        System.exit(1);
                }
            } else {
                File resKeyPublik;
                while (true) {
                    resKeyPublik = getFile(resKeyStartPath, false, false, keyfilter);
                    if (resKeyPublik.exists())
                        if (resKeyPublik.length() != 0)
                            if (testResKey(PrivetKey, resKeyPublik)) {
                                res.setKeyfile(resKeyPublik);
                                break;
                            } else if (JOptionPane.showConfirmDialog(null, nyckelError) != 0)
                                System.exit(1);
                    if (JOptionPane.showConfirmDialog(null, fileError) != 0)
                        System.exit(1);
                }
            }
        }

    }

    private static File[] getResFilePair(File resKeyDir) {
        String nameFile = JOptionPane.showInputDialog(null, "name of the res key files.\n(adds .publik.key or .privet.key to the end");
        File resKeyPrivet = new File(resKeyDir, nameFile + ".Privet.key");
        File resKeyPublik = new File(resKeyDir, nameFile + ".Publik.key");
        try {
            if (!resKeyPrivet.createNewFile()) {
                if (resKeyPrivet.length() != 0) {
                    if ( JOptionPane.showConfirmDialog(null,"inte tom, privet") != 0) {
                        System.err.println("inte implementerat om svarar är inte ja!!!");
                        System.exit(-4);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if (!resKeyPublik.createNewFile()) {
                if (resKeyPublik.length() != 0) {
                    if (JOptionPane.showConfirmDialog(null, "int tom, publik") != 0) {
                        System.err.println("inte implementerat om svarar är inte ja!!!");
                        System.exit(-4);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File[] {resKeyPrivet,resKeyPublik};
    }

    private static void riteResKeyToFile(File[] resKeyPair) {
        KeyPairGenerator keyPairGenerator;
        KeyFactory keyFactory;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RES");
            keyFactory = KeyFactory.getInstance("RES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKeySpec privateKeySpec;
        RSAPublicKeySpec publicKeySpec;
        try {
            privateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateCrtKeySpec.class);
            publicKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPublicKeySpec.class);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(resKeyPair[0])));
            outputStream.writeObject(privateKeySpec.getModulus());
            outputStream.writeObject(privateKeySpec.getPrivateExponent());
            outputStream.flush();
            outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(resKeyPair[1])));
            outputStream.writeObject(publicKeySpec.getModulus());
            outputStream.writeObject(publicKeySpec.getPublicExponent());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean testResKey(boolean privetKey, File resKeyFile) {
        if (resKeyFile == null)
            return false;
        ObjectInputStream objectInputStream;
        BigInteger modulus;
        BigInteger exponent;
        try {
            objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(resKeyFile)));
            modulus = (BigInteger) objectInputStream.readObject();
            exponent = (BigInteger) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance("RES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        if (privetKey) {
            try {
                keyFactory.generatePrivate(new RSAPrivateKeySpec(modulus,exponent));
            } catch (InvalidKeySpecException e) {
                System.err.println(e);
                return false;
                //throw new RuntimeException(e);
            }
        } else {
            try {
                keyFactory.generatePublic(new RSAPublicKeySpec(modulus,exponent));
            } catch (InvalidKeySpecException e) {
                System.err.println(e);
                return false;
                //throw new RuntimeException(e);
            }
        }
        return true;
    }

    private static void hybridSettings() {

    }

}
