package tests;

import modules.SetingsModel;
import setings.AESsettings;
import setings.Settings;
import setings.Settingsfile;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
        byte testid = 0;

        if (testid == 0) {
            settings = testaes();
        }

        return settings;
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

        try {
            if (tempkeyfile.createNewFile()) {
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
        //settings.setChekORen();
        //settings.setChekORstr();
        AESsettings aes = new AESsettings();
        aes.setIv(iv);
        aes.setKey(key);

        // aes.setPlainText("xOaHUFimD3B4JoV7DUbarXJ8Sej5wr2bYoBsb0lzij0UfTA/GJa1ipyPUDeYXlwJ");
        aes.setFiles(testfiles());

        settings.setAes(aes);

        return settings;
    }
    private static Settingsfile testfiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File in = fileChooser.getSelectedFile();
        fileChooser.showOpenDialog(null);
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