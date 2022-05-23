package tests;

import modules.SetingsModel;
import setings.Settings;

import javax.swing.*;
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
        Settings settings;
        SetingsModel setmod = new SetingsModel();

        // settings = stringtest(true, setmod);
        settings = filetest(true, setmod);

        settings = stringtestres(true, setmod);

        System.out.println(settings.toString());
        try {
            ObjectOutputStream oou = new ObjectOutputStream(new FileOutputStream(setingsfile));
            oou.writeObject(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Settings stringtestres(boolean enORde, SetingsModel setmod) {
        setmod.setID((byte) 2);
        return null;
    }

    public static Settings stringtest(boolean enORde, SetingsModel setmod) {
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
    public static Settings filetest(boolean enORde, SetingsModel setmod) {
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