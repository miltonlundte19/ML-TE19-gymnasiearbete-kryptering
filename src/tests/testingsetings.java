package tests;

import modules.Crypteringsmodule;
import modules.SetingsModel;
import setings.Settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class testingsetings {
    public static void main(String[] args) {
        File setingsfile = new File("setingsfile.txt");
        if (!setingsfile.exists()) {
            try {
                setingsfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        SetingsModel setmod = new SetingsModel();
        setmod.setID((byte) 1);
        setmod.generateRkey();
        setmod.setMesige("deta är ett test av först setings modelen och senan crypteringen");
        Settings settings = null;
        if (setmod.check()) {
            settings = setmod.getSettings();
            settings.setEncryptORdecrypt(true);
        } else {
            System.out.println("något gik fel med att seta setings");
            System.exit(-1);
        }
        System.out.println(settings.toString());
        try {
            ObjectOutputStream oou = new ObjectOutputStream(new FileOutputStream(setingsfile));
            oou.writeObject(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
    //  setingsfile.txt