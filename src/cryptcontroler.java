import modules.Crypteringsmodule;
import setings.Settings;

import java.io.*;

public class cryptcontroler {
    public static void main(String[] args) {
        long logtime = System.nanoTime();
        File setingsfile = new File("setingsfile.txt");
        if (!setingsfile.exists()) {
            System.out.println("filen fins inte");
            System.exit(-1);
        }
        File startupfile = new File("startuptime.txt");
        FileWriter startup;
        try {
            if (!startupfile.exists()) {
                startupfile.createNewFile();
            }
            startup = new FileWriter(startupfile);
            startup.write("start time:\n" + logtime + "\n");
            startup.flush();
            ObjectInputStream setingsreder = new ObjectInputStream(new FileInputStream(setingsfile));
            Settings settings = (Settings) setingsreder.readObject();
            Crypteringsmodule crypteringsmodule = new Crypteringsmodule(settings);
            crypteringsmodule.setLooger(startup);
            crypteringsmodule.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }


    }
}
