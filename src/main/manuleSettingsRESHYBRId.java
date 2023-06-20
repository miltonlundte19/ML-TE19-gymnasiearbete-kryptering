package main;

import setings.HYBRIDsettings;
import setings.RESsettings;
import setings.Settings;

import java.io.*;

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

    static File keyStartPath = new File("");
    // start path for a file choser for the keyfile.

    static boolean generateNyResKey = false;
    // true = program generats a ny key

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
        settingsfile = new File("ssetingsfile.txt");
        if (!settingsfile.exists())
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

    //-------------------------------------------------------------

    private static void setSettings() {
        settings.setId(id);
        settings.setCheekEncryption(encrypt);
        settings.setStorTOfile(storToFile);
        settings.setManulesnapshot(manualSnapthot);
        settings.setNumOFrepeteson(numOfRepetitions);
    }

    private static void resSettings() {
        res = new RESsettings();

    }

    private static void hybridSettings() {

    }

}
