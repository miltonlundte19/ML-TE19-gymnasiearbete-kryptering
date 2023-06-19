package main;

import setings.Settings;

import java.io.File;
import java.io.IOException;

public class manuleSettingsRESHYBRId {
    //------- globala -------------------------------------
    byte id = 2;
    // 0 = AES : 1 = RES : 2 = Hybrid.

    boolean encrypt = true;
    // true = encryption : fals = decryption.

    boolean storToFile = false;
    // true = stors output to a file.

    boolean manualSnapthot = true;
    // true = if the program shall pause for the user to take a snapshot.

    short numOfRepetitions = 1;

    //----------------------------------------------------------
    //-------- ras -----------------------------------------
    boolean PrivetKey = true;
    // true = privet : // fals = publik.

    File messageStartPaf = new File("");
    // start path for a file choser for the message.

    File keyStartPaf = new File("");
    // start path for a file choser for the keyfile.

    //----------------------------------------------------------
    //-------- Hybrid -----------------------------------------
    // set variable for res also
    byte iv = 0;
    // 0-12 for a set of pre generated iv:s (-1 for override, manual inserting at:[rad]).

    boolean aesKeyStordInFile = false;
    // If the key for aes is stord in the file

    File aesKeyStartPaf = new File("");
    // start path for a file choser for the keyfile.

    // message is res message variable

    //----------------------------------------------------------

    public static void main(String[] args) {
        settingsfile = new File("ssetingsfile.txt");
        if (!settingsfile.exists())
            try {
                settingsfile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }


    //------------ Fixt variable ----------------------------------
    Settings settings;
    static File settingsfile;

    //-------------------------------------------------------------
}
