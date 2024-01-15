package controllers;

import modules.Crypteringsmodule;
import setings.Settings;

import java.io.*;
import java.time.LocalDateTime;

public class Cryptcontroller {
    public static void main(String[] args) {
        LocalDateTime starttime = LocalDateTime.now();
        // Börjar med att sparar tiden som programet börjar.

        File setingsfile = new File("setingsfile.txt");
        if (!setingsfile.exists()) {
            System.out.println("filen fins inte");
            System.exit(-1);
        }
        // Hitar en fill som har parametrarna för att automatiskt köra programet och titar så att filen existerar.

        File startupfile = new File("startuptime.txt");
        // en fill som loggar skrivs till.
        FileWriter startup;
        try {
            if (!startupfile.exists())
                startupfile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            startup = new FileWriter(startupfile);
            startup.write("Start time:\n" + starttime + "\n");
            startup.flush();
            /* skriver tiden som programet startade i logg filen
               så att men kan räkna ut hur lång tid upstarten tog.
            */
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Settings settings;
        ObjectInputStream setingsreder;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(setingsfile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            setingsreder = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Object setingsobjekt;
        try {
            setingsobjekt = setingsreder.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (setingsobjekt.getClass() == Settings.class) {
            settings = (Settings) setingsobjekt;
            // Läser in en egen jord variabel som har alltför krypteringen för dig gjort
            // mer om Settings varabeln fins /setings/Settings
            try {
                setingsreder.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Crypteringsmodule crypteringsmodule = new Crypteringsmodule(settings);
            crypteringsmodule.setLooger(startup);
            crypteringsmodule.start();
            // ladar in några objekt till krypteringen och sedan startar klasen.
        } else {
            System.out.println("Objektet i setingsfile.txt är inte ett Settings objekt");
            System.exit(7);
        }
    }
}
/*
 Å = U+00C5  c3 85
 Ä = U+00C4  c3 84
 Ö = U+00D6  c3 96
 å = U+00E5  c3 a5
 ä = U+00E4  c3 a4
 ö = U+00F6  c3 b6
 */