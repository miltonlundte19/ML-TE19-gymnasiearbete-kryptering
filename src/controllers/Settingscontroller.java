package controllers;

import modules.SettingsModel;
import view.Settingsview;

import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.Objects;

public class Settingscontroller {
    private Settingsview view;
    private SettingsModel modell;

    private File key;
    boolean selektedfile = false;
    boolean keyisasaind = false;

    public static void main(String[] args) {
        Settingscontroller fillecreitorcontroller = new Settingscontroller();
    }
    public Settingscontroller() {
        view = new Settingsview();
        modell = new SettingsModel();
        modell.setId((byte) 0); // temporary (coming son)

        JFrame frame = new JFrame("Fillecreitrorview");
        frame.setContentPane(view.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setButtons();

        frame.pack();
        frame.setVisible(true);
    }

    private void setButtons() {
        view.getIvselektbutton().addActionListener(new setivAL());
        view.getSelektpasbutton().addActionListener(new selektpaswerdfileAL());
        view.getGeniraitepasbutton().addActionListener(new genpaswirdeAL());
        view.getSaveButton().addActionListener(new savekeyAL());
        view.getFileinbutton().addActionListener(new getfilinAL());
        view.getFileoutcheckBox().addItemListener(new fileouttogelIL());
        view.getRepetisonkonter().addChangeListener(new repetsonconterCL());
        view.getFileoutbutton().addActionListener(new getfileoutAL());
    }

    private class setivAL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int posison = view.getIvselektorcombox().getSelectedIndex();
            System.out.println(posison);
            modell.setAesiv((byte) posison);
        }
    }

    private class selektpaswerdfileAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //JFileChooser.APPROVE_OPTION == 0
            //JFileChooser.CANCEL_OPTION == 1
            //JFileChooser.ERROR_OPTION == -1
            FileNameExtensionFilter filter = new FileNameExtensionFilter("key file filter", "key");
            File paswerd;
            boolean exists = false;

            JFileChooser chooser = new JFileChooser();

            chooser.setDialogTitle("Select password/key file or file to store password/key");
            chooser.setFileFilter(filter);

            if ((chooser.showOpenDialog(null)) == 0) {
                paswerd = chooser.getSelectedFile();
                System.out.println(paswerd.getAbsolutePath());
                if (paswerd.isDirectory()) {
                    int chois = JOptionPane.showConfirmDialog(null,
                            "Selekted file is a diraktory\nDo you vant to create a file",
                            "diraktory selekted" , JOptionPane.OK_CANCEL_OPTION);

                }

                if (!paswerd.exists()) {
                    if ((JOptionPane.showConfirmDialog(null,
                            "selekted file dos not exists\nDo you vant to create the file",
                            "selekted file dos not exists", JOptionPane.OK_CANCEL_OPTION)) == 0) {
                        exists = true;
                        try {
                            paswerd.createNewFile();
                        } catch (IOException ex) {
                            paswerd = null;
                            exists = false;
                            view.setPasvirddisplayfieldtextError("Error geting file");
                            throw new RuntimeException(ex);
                        }

                    }
                } else {
                    exists = true;
                }
                if (exists) {
                    long sise = paswerd.length();
                    if (sise > 0) {
                        byte choise;
                        if ((choise = (byte) JOptionPane.showConfirmDialog(
                                null,
                                "filen är inte tom ska den ändå användas\n(om det inte är en nykel så kommer allat att rederas)",
                                "filens storlek är inte 0",
                                JOptionPane.YES_NO_OPTION
                        )) == 0) {
                            byte status = testAESkey(paswerd);
                            System.out.println(status);
                        }
                        /*
                        if ((JOptionPane.showConfirmDialog(
                                null,
                                "filen har redan saker i den",
                                "fil storlek är int 0",
                                JOptionPane.YES_NO_OPTION)) != 0) {
                            exists = false;
                            paswerd = null;
                        }
                        if (exists) {
                            byte status;
                            if (((status = testAESkey(paswerd)) == 1)) {
                                key = paswerd;
                                selektedfile = true;
                                keyisasaind = true;
                            }
                            if (status == -1) {
                                view.setPasvirddisplayfieldtextError("en ioexception hapend");
                            }
                            if (status == -2) {
                                view.setPasvirddisplayfieldtextError("kund int hämta clasen");
                            }
                            if (status == 0) {
                                view.setPasvirddisplayfieldtext("nykelns algoritm var fel.");
                            }
                        }
                        */
                    } else {
                        key = paswerd;
                        selektedfile= true;
                    }
                }
            } else {
                view.setPasvirddisplayfieldtext("user canseld or an error akurde");
            }

        }
    }

    private byte testAESkey(File paswerd) {
        ObjectInputStream keyreder = null;
        BufferedInputStream bufferedInputStream;
        FileInputStream inputStream;
        System.out.println(paswerd.getAbsolutePath());
        try {
            inputStream = new FileInputStream(paswerd);
        } catch (FileNotFoundException e) {
            System.out.println(-9);
            throw new RuntimeException(e);
        }
        try {
            System.out.println(inputStream.available());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bufferedInputStream = new BufferedInputStream(inputStream);

        try {
            keyreder = new ObjectInputStream(bufferedInputStream);
        } catch (IOException e) {
            System.out.println(-8);
            throw new RuntimeException(e);
        }//return -5;
        //Object keyobject;
        SecretKey key1;
        try {
            key1 = (SecretKey) keyreder.readObject();
            keyreder.close();
        }  catch (IOException e) {
            return -2;
        } catch (ClassNotFoundException e) {
            return -3;
        }
        if (Objects.equals(key1.getAlgorithm(), "AES")) {
            return 1;
        }
        return 0;
    }

    private class genpaswirdeAL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {


        }
    }

    private void updatesavekey() {
        if (selektedfile && keyisasaind)
            view.enebilsavebuton();
    }
    private class savekeyAL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class getfilinAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class fileouttogelIL implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {

        }
    }

    private class repetsonconterCL implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {

        }
    }

    private class getfileoutAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
