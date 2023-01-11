package tests;

import modules.SetingsModel;
import setings.AESsettings;
import setings.RESsettings;
import setings.Settings;
import setings.Settingsfile;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class testingsetings {
    // Dena filen är en test fill för att setta setings till programet medan sag inte har gjort den delen av programet.
    // (Debug för krypterings biten)
    public static void main(String[] args) {
        File setingsfile = new File("setingsfile.txt");
        if (!setingsfile.exists()) {
            try {
                setingsfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Settings settings;

        settings = testing();
        /*

        SetingsModel setmod = new SetingsModel();

        //settings = stringtest(true, setmod);
        // settings = filetest(true, setmod);

        //settings = stringtestres(true, setmod);

        // tester för hur setings objäktet fungerar (inte updaterat till senaste ändringarna)
        */
        System.out.println(settings.toString());
        try {
            ObjectOutputStream oou = new ObjectOutputStream(new FileOutputStream(setingsfile));
            oou.writeObject(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Settings testing() {
        Settings settings = new Settings();
        byte testid = 1;

        if (testid == 0) {
            settings = testaes();
        } else if (testid == 1) {
            settings = testres();
        }

        return settings;
    }

    private static Settings testres() {
        Settings settings = new Settings();



        settings.setId((byte) 2);
        //settings.setChekORen();
        //settings.setChekORstr();

        File tempkeyfilepub = new File("Temporikeyfilepub.txt");
        File tempkeyfilepri = new File("Temporikeyfilepri.txt");
        boolean first = false; // en variabel så att man kan genirera en ny kykeln
        try {
            if ((tempkeyfilepub.createNewFile() || (tempkeyfilepub.length() == 0)) || (tempkeyfilepri.createNewFile() || (tempkeyfilepri.length()== 0)) || first) {
                // genirerar ett nykel par med båda nyklarna
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                SecureRandom sr = new SecureRandom();
                kpg.initialize(2048, sr);

                KeyPair keyPair = kpg.generateKeyPair();

                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                // skriver den privata nykeln till en fill
                ObjectOutputStream outputStreampri = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(tempkeyfilepri)));
                RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
                outputStreampri.writeObject(privateKeySpec.getModulus());
                outputStreampri.writeObject(privateKeySpec.getPrivateExponent());
                outputStreampri.flush();
                outputStreampri.close();

                // skriver den publika nykeln till en fill
                ObjectOutputStream outputStreampub = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(tempkeyfilepub)));
                RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
                outputStreampub.writeObject(publicKeySpec.getModulus());
                outputStreampub.writeObject(publicKeySpec.getPublicExponent());
                outputStreampub.flush();
                outputStreampub.close();
            }
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        RESsettings res = new RESsettings();
        boolean priOrpub = true; // true = privet

        res.setKeyfile(tempkeyfilepri);
        //res.setKeyfile(tempkeyfilepub);
        res.setPriORpub(priOrpub);

        /*
        String mesige = "Test av res krypteringen";
        if (mesige.length() > 245) {
            System.err.println("mesige är för långt. Max:245 kurent:" + mesige.length());
        }
        //res.setMesige(mesige); */
        res.setFiles(testfilesres());

        settings.setRes(res);

        return settings;
    }

    private static Settingsfile testfilesres() {
        JFileChooser fileChooser = new JFileChooser();
        int i;
        long lengt;
        boolean c = true;
        while (c) {
            i = fileChooser.showOpenDialog(null);
            if (i == JFileChooser.CANCEL_OPTION) {
                System.out.println("user caned");
                System.exit(1);
            }
            lengt = fileChooser.getSelectedFile().length();

            if (lengt > 245) {
                int a = JOptionPane.showConfirmDialog(null,"filen som är vald har " + lengt +
                                " taken och kan vara för stor för krypteringen \n (Max storlek är 245 taken) \n Tryk på ok om du vill fortseta"
                        , "Filen kan vara för stor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (a == 0) {
                    c = false;
                }
            }
        }
        File in = fileChooser.getSelectedFile();
        i = fileChooser.showOpenDialog(null);
        if (i == JFileChooser.CANCEL_OPTION) {
            System.out.println("user caned");
            System.exit(1);
        }
        File ou = fileChooser.getSelectedFile();

        Settingsfile settingsfile = new Settingsfile();
        settingsfile.setIN(in);
        settingsfile.setOU(ou);

        return settingsfile;

    }

    private  static Settings testaes() {
        /*
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        System.out.println(Arrays.toString(iv));
         */
        /*
         * [-80, 65, 125, 85, -124, 70, 90, 52, -118, 116, 107, -12, -109, 89, -72, -6]
         * [41, -75, 67, -52, 107, 93, -55, 104, -9, 124, 49, 31, -34, 65, 18, 92]
         * [-112, -9, 86, -63, 78, -64, -91, 53, -47, -108, -27, 27, -105, 99, -60, 89]
         * några färdig genirerade byte arayer
         * */
        byte[] iv = new byte[] {-80, 65, 125, 85, -124, 70, 90, 52, -118, 116, 107, -12, -109, 89, -72, -6};
        File tempkeyfile = new File("Temporikeyfile.txt");
        System.out.println(tempkeyfile.length());

        boolean first = false; // en variabel så att man kan genirera en ny kykeln
        try {
            if (tempkeyfile.createNewFile() || (tempkeyfile.length() == 0) || first) {
                SecureRandom sr = new SecureRandom();
                KeyGenerator kg = KeyGenerator.getInstance("AES");
                kg.init(256, sr);
                ObjectOutputStream temkeywriter = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(tempkeyfile)));
                SecretKey key = kg.generateKey();
                temkeywriter.writeObject(key);
                temkeywriter.flush();
            }

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(tempkeyfile.length());

        SecretKey key;
        try {
            ObjectInputStream temkeyred = new ObjectInputStream(new BufferedInputStream(new FileInputStream(tempkeyfile)));
            key = (SecretKey) temkeyred.readObject();
            temkeyred.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Settings settings = new Settings();
        settings.setId((byte) 1);
        settings.setChekORen();
        settings.setChekORstr();
        AESsettings aes = new AESsettings();
        aes.setIv(iv);
        aes.setKey(key);

        aes.setPlainText("Varför vil inte cryptering fungera");
        //aes.setFiles(testfiles());

        settings.setAes(aes);

        return settings;
    }
    private static Settingsfile testfiles() {
        int i;
        JFileChooser fileChooser = new JFileChooser();
        i = fileChooser.showOpenDialog(null);
        if (i == JFileChooser.CANCEL_OPTION) {
            System.out.println("user caned");
            System.exit(1);
        }
        File in = fileChooser.getSelectedFile();
        i = fileChooser.showOpenDialog(null);
        if (i == JFileChooser.CANCEL_OPTION) {
            System.out.println("user caned");
            System.exit(1);
        }
        File ou = fileChooser.getSelectedFile();

        Settingsfile settingsfile = new Settingsfile();
        settingsfile.setIN(in);
        settingsfile.setOU(ou);

        return settingsfile;
    }






    private static Settings stringtestres(boolean enORde, SetingsModel setmod) { // GAMAL / Inte updaterad.
        setmod.setID((byte) 2);
        return null;
    }

    public static Settings stringtest(boolean enORde, SetingsModel setmod) { // GAMAL / Inte updaterad. (tror jag)
        setmod.setID((byte) 1);
        setmod.generateRkey();
        setmod.setMesige("deta är ett test av först setings modelen och senan crypteringen");
        if (enORde) {
            setmod.setENorDE();
        }


        if (setmod.check()) {
            return setmod.getSettings();
        } else {
            System.out.println("något gik fel med att seta setings");
            System.exit(-1);
        }

        return null;
    }
    public static Settings filetest(boolean enORde, SetingsModel setmod) { // GAMAL / Inte updaterad. (tror jag)
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File in = fileChooser.getSelectedFile();
        fileChooser.showOpenDialog(null);
        File ou = fileChooser.getSelectedFile();

        setmod.setID((byte) 1);
        setmod.generateRkey();
        if (enORde) {
            setmod.setENorDE();
        }

        setmod.setFiles(in, ou);

        if (setmod.check()) {
            return setmod.getSettings();
        } else {
            System.out.println("något gik fel med att seta setings");
            System.exit(-1);
        }
        return null;
    }
}
    //  setingsfile.txt