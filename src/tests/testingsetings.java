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

        // settings = stringtest(true);
        settings = filetest(true);


        System.out.println(settings.toString());
        try {
            ObjectOutputStream oou = new ObjectOutputStream(new FileOutputStream(setingsfile));
            oou.writeObject(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Settings stringtest(boolean enORde) {
        SetingsModel setmod = new SetingsModel();
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
    public static Settings filetest(boolean enORde) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File in = fileChooser.getSelectedFile();
        fileChooser.showOpenDialog(null);
        File ou = fileChooser.getSelectedFile();

        SetingsModel setmod = new SetingsModel();
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