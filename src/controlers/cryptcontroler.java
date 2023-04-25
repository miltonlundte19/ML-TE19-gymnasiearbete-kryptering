package controlers;

import modules.Crypteringsmodule;
import setings.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;

public class cryptcontroler {
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
            if (!startupfile.exists()) {
                startupfile.createNewFile();
            }
            startup = new FileWriter(startupfile);
            startup.write("start time:\n" + starttime + "\n");
            startup.flush();
            /* skriver tiden som programet startade i logg filen
               så att men kan räkna ut hur lång tid upstarten tog.
            */
            ObjectInputStream setingsreder = new ObjectInputStream(new FileInputStream(setingsfile));
            Settings settings = (Settings) setingsreder.readObject();
            setingsreder.close();
            // Läser in en egen jord variabel som har alltför krypteringen för dig gjort
            // mer om Settings varabeln fins /setings/Settings

            Crypteringsmodule crypteringsmodule = new Crypteringsmodule(settings);
            crypteringsmodule.setLooger(startup);
            crypteringsmodule.start();
            // ladar in några objekt till krypteringen och sedan startar klasen.

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
