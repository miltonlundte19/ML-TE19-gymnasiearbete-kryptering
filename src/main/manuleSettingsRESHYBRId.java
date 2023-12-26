package main;

import setings.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class manuleSettingsRESHYBRId {
    //------- globala -------------------------------------
    static byte id = 1;
    // 1 = RSA : 2 = Hybrid.

    static boolean encrypt = true;
    // true = encryption : fals = decryption.

    static boolean storToFile = false;
    // true = stores output to a file.

    static boolean manualSnapthot = true;
    // true = if the program shall pause for the user to take a snapshot.

    static short numOfRepetitions = 1;
    // the number of times the program shall encrypt or decrypt the message. (min 1)

    //----------------------------------------------------------
    //-------- rsa -----------------------------------------
    static boolean PrivetKey = true;
    // true = privet : // false = publik.

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
    // 0-12 for a set of pre generated iv:s (-1 for override, manual inserting at:[71:32]).

    static boolean aesKeystoredInFile = false;
    // If the key for aes is stored in the file

    static boolean aesKeystorInFile = true;
    // if the key for aes wiled be stored in the file

    static File aesKeyStartPath = new File("");
    // start path for a file choser for the keyfile.

    static boolean generateNyAesKey = false;
    // true = program generats a ny key

    // message is rsa message variable.

    //----------------------------------------------------------

    static byte[] ivOVERRIDE = null;

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
        if (id == 1) {
            rsa = rsaSettings();
            rsa.setFiles(setFileInOut(messageInStartPath,messageOutStartPath));
            settings.setRsa(rsa);
        }

        if (id == 2)
            hybridSettings();
        System.out.println(settings.toString());
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(settingsfile));
            objectOutputStream.writeObject(settings);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //------------ Fixt variable ----------------------------------
    static Settings settings = new Settings();
    static File settingsfile;
    static RSAsettings rsa;
    static HYBRIDsettings hybrid;
    static AESsettings aes;
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
        String prok = "Pres ok to continue";
        while (true) {
            i = fileChooser.showOpenDialog(null);
            if (i == 0) {
                if (lengtchek) {
                    lengt = fileChooser.getSelectedFile().length();
                    if (lengt > 245) {
                        String m = "filen som är vald har " + lengt +
                                " taken och kan vara för stor för krypteringen" +
                                "\n (Max storlek är 245 taken) \n Tryk på ok om du vill fortseta";
                        int a = JOptionPane.showConfirmDialog(null, m,
                                "Filen kan vara för stor",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (a == 0) {
                            return fileChooser.getSelectedFile();
                        } else {
                            if (a == JFileChooser.CANCEL_OPTION) {
                                if (JOptionPane.showConfirmDialog(null,
                                        "user\n" + prok) != 0) {
                                    return null;
                                }
                            }
                            if (a == JFileChooser.ERROR_OPTION) {
                                if (JOptionPane.showConfirmDialog(
                                        null,
                                        "error\n" + prok) != 0) {
                                    return null;
                                }
                            }
                        }
                    } else {
                        return fileChooser.getSelectedFile();
                    }
                } else {
                    return fileChooser.getSelectedFile();
                }
            }
            if (i != 0) {
                if (i == JFileChooser.CANCEL_OPTION) {
                    if (JOptionPane.showConfirmDialog(null,
                            "user\n" + prok) != 0) {
                        return null;
                    }
                }
                if (i == JFileChooser.ERROR_OPTION) {
                    if (JOptionPane.showConfirmDialog(null,
                            "error\n" + prok) != 0) {
                        return null;
                    }
                }
            }
        }
    }

    private static RSAsettings rsaSettings() {
        rsa = new RSAsettings();
        rsa.setPriORpub(PrivetKey);
        File resKeyDir;
        if (generateNyResKey) {
            File[] resKeyPair;
            resKeyDir = getFile(resKeyStartPath, false, true, "Select the rsa key directory");
            if (resKeyDir == null) {
                System.err.println("inte implementerat om directory är null!!!");
                System.exit(-4);
            }
            resKeyPair = getResFilePair(resKeyDir);
            riteResKeyToFile(resKeyPair);
            if (PrivetKey) {
                if (testRsaKey(true, resKeyPair[0])) {
                    rsa.setKeyfile(resKeyPair[0]);
                } else {
                    System.err.println("An error a kurade setting the privet key");
                    System.exit(-3);
                }
            } else {
                if (testRsaKey(false, resKeyPair[1])) {
                    rsa.setKeyfile(resKeyPair[1]);
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
                    resKeyPrivet = getFile(resKeyStartPath,false,false, "Select the res privet password file", keyfilter);
                    if (resKeyPrivet != null) {
                        if (resKeyPrivet.exists())
                            if (resKeyPrivet.length() != 0) {
                                if (testRsaKey(PrivetKey, resKeyPrivet)) {
                                    rsa.setKeyfile(resKeyPrivet);
                                    break;
                                } else if (JOptionPane.showConfirmDialog(null, nyckelError) != 0)
                                    System.exit(1);
                            }
                    } else if (JOptionPane.showConfirmDialog(null, fileError) != 0)
                        System.exit(1);
                }
            } else {
                File resKeyPublik;
                while (true) {
                    resKeyPublik = getFile(resKeyStartPath, false, false, "Select the res publik password file", keyfilter);
                    if (resKeyPublik != null) {
                        if (resKeyPublik.exists())
                            if (resKeyPublik.length() != 0) {
                                if (testRsaKey(PrivetKey, resKeyPublik)) {
                                    rsa.setKeyfile(resKeyPublik);
                                    break;
                                } else if (JOptionPane.showConfirmDialog(null, nyckelError) != 0)
                                    System.exit(1);
                            }
                    } else if (JOptionPane.showConfirmDialog(null, fileError) != 0)
                        System.exit(1);
                }
            }
        }
        return rsa;
    }

    private static Settingsfile setFileInOut(File messageInPath, File messageOutPath) {
      Settingsfile files = new Settingsfile();
      files.setIN(getFile(messageInPath, true, false, "selekt fille in"));
      if (storToFile) {
          File out;
          while (true) {
              out = getFile(messageOutPath,false,false,"sellekt file out");
              if (out.length() != 0) {
                  byte anser = (byte) JOptionPane.showConfirmDialog(null, "filen har text i sej vil du radera datan? \n annars välj en annan fill");
                  if (anser == 0)
                      break;
                  if (anser == -1) {
                      System.out.println("user canseld \n closing program");
                      System.exit(-1);
                  }
              }
          }
          files.setOU(out);
      } else {
          files.setOuToNull();
      }
      return files;
    }

    private static File[] getResFilePair(File resKeyDir) {
        String nameFile = JOptionPane.showInputDialog(null, "name of the rsa key files.\n(adds .publik.key or .privet.key to the end");
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
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKeySpec privateKeySpec;
        RSAPublicKeySpec publicKeySpec;
        try {
            privateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
            publicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
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

    private static boolean testRsaKey(boolean privetKey, File resKeyFile) {
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
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        if (privetKey) {
            try {
                keyFactory.generatePrivate(new RSAPrivateKeySpec(modulus,exponent));
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                return false;
                //throw new RuntimeException(e);
            }
        } else {
            try {
                keyFactory.generatePublic(new RSAPublicKeySpec(modulus,exponent));
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                return false;
                //throw new RuntimeException(e);
            }
        }
        return true;
    }

    private static void hybridSettings() {
        hybrid = new HYBRIDsettings();
        aes = aesSettings();
        hybrid.setAes(true);
        hybrid.setKeyinfile(aesKeystoredInFile);
        hybrid.setStorkeyInFile(storToFile);
        rsa = rsaSettings();
        if (aesKeystorInFile) {
            rsa.setFiles(aes.getFileInOu());
        } else {
            File filein = getFile(aesKeyStartPath, false,false,"select aes ket file");
            if (filein == null)
                System.exit(-1);//_____________________----------------------------------________________-----_
            rsa.setFiles(new Settingsfile(filein,aes.getFileInOu().getOu()));
        }
        hybrid.setRsa(true);
    }

    private static byte[] aesSetIv(byte iv) {
        if (iv == -1) {
            if (ivOVERRIDE == null) {
                throw new RuntimeException("iv OVERRIDE is null", new NullPointerException());
            }
            return ivOVERRIDE;
        }
        return AesIv[iv];
    }
    private static final byte[][] AesIv = new byte[][]{
            {-100, -7, -35, 82, -71, -79, 53, 91, 88, 79, -106, -16, 71, -14, 83, -1},
            {-58, -113, 84, 89, 45, 126, 41, 0, 122, 37, 42, 101, -16, -30, -112, 79},
            {97, -85, -111, 38, -17, 123, 73, 37, 7, 42, -49, 9, 6, 67, -62, 38},
            {-81, -99, 54, -102, -66, -105, -66, -57, 52, 57, 121, 111, 57, 47, 23, 119},
            {62, -89, 125, 126, 6, -111, -84, -93, -114, -65, -58, -83, 70, 10, 0, 76},
            {38, 110, 50, -59, -96, 123, -94, -11, 65, 35, 52, -128, -96, -128, -1, 6},
            {-9, 49, -77, -41, 98, 63, 85, 39, -61, -119, -113, 14, 97, 102, 66, -69},
            {-94, -35, 103, -12, -10, -49, -10, 107, 109, 110, 101, -17, -113, -127, -124, -107},
            {-37, -70, -123, 71, -88, 18, -2, -86, -24, 5, -50, -61, -127, -15, -122, -66},
            {120, -101, 126, 106, 111, -13, 45, 99, -87, 106, 16, 116, 90, -54, 104, 52},
            {-80, 65, 125, 85, -124, 70, 90, 52, -118, 116, 107, -12, -109, 89, -72, -6},
            {41, -75, 67, -52, 107, 93, -55, 104, -9, 124, 49, 31, -34, 65, 18, 92},
            {-112, -9, 86, -63, 78, -64, -91, 53, -47, -108, -27, 27, -105, 99, -60, 89}
    };

}
