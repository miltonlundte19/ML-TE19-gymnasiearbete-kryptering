package controllers;

import modules.SettingsModel;
import setings.Settingsfile;
import view.Settingsview;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;

public class Settingscontroller {

    private final Settingsview view;
    private final SettingsModel model;

    private File key;
    private File fileOut;
    private File settingsfile = null;
    private SecretKey secretKey;
    boolean selektedfile = false;
    boolean keyisinfile = false;
    boolean keyisasaind = false;
    boolean keyisused = false;

    private static final Color defaltcolor = new Color(51,51,51);

    public static void main(String[] args) {
        new Settingscontroller();
    }

    public Settingscontroller() {
        view = new Settingsview();
        model = new SettingsModel();
        modelStartup();

        JFrame frame = new JFrame(setTitle());
        frame.setContentPane(view.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(setMenu());

        setButtons();

        frame.pack();
        frame.setVisible(true);
    }

    private JMenuBar setMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu menulode = new JMenu("Lode");
        JMenuItem menuItemlode = new JMenuItem("Lode in setings");
        menuItemlode.setToolTipText("Lode in an alredy set settings in a file");
        //menuItemlode.addActionListener(new );
        menulode.add(menuItemlode);
        mb.add(menulode);
        JMenu menudebug = new JMenu("debug");
        JMenuItem menuItemdebug = new JMenuItem("debug out");
        menuItemdebug.addActionListener(new debugmenuAL());
        menudebug.add(menuItemdebug);
        mb.add(menudebug);
        return mb;
    }

    public String setTitle() {
        settingsfile = new File("setingsfile.txt");
        try {
            if  (!settingsfile.createNewFile())
                if (!settingsfile.exists()) {
                    settingsfile = null;
                }
        } catch (IOException e) {
            settingsfile = null;
        }
        if (settingsfile == null)
            return "error geting setingsfile can not komplet";
        return "Setingscreitor";
    }
    private void modelStartup() {
        model.setId((byte) 0);
        model.setmesigetyp(false);
        model.setEncryption(true);
        model.setManusnapshot();
        model.setNumofRepit((short) 1);
    }

    private void setButtons() {
        view.getIvselektbutton().addActionListener(new setIvAL());
        view.getSelektpasbutton().addActionListener(new selektPaswerdFileAL());
        view.getGeniraitepasbutton().addActionListener(new geniraitkeyAL());
        view.getSaveButton().addActionListener(new savekeyAL());
        view.getEnkryptionCheckBox().addItemListener(new enkrypTogelIL());
        view.getInFileButton().addActionListener(new inFileAL());
        view.getOutFileButton().addActionListener(new outFileAL());
        view.getOutputFileCheckBox().addItemListener(new outFileTogelIL());
        view.getManulSnapshotCheckBox().addItemListener(new manulSnapsoTogelIL());
        view.getRepetisonKonterSpinner().addChangeListener(new repetisonsCL());
        view.getRepetisonKonterSpinner().setValue(1);
        view.getDoneButton().addActionListener(new doneSettingsAL());
    }

    public class setIvAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.setAESIv((byte) view.getIvselektedcombox());
        }
    }

    private class selektPaswerdFileAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Key file filter", "key");
            File paswerd;
            boolean exists = true;

            JFileChooser chooser = new JFileChooser();

            chooser.setFileFilter(filter);

            if ((chooser.showOpenDialog(null)) == 0) {
                paswerd = chooser.getSelectedFile();

                if (paswerd.isDirectory()) {
                    int chois = JOptionPane.showConfirmDialog(null,
                            "Selekted file is a diraktory\nDo you vant to create a file",
                            "diraktory selekted" , JOptionPane.OK_CANCEL_OPTION);
                    System.out.println("en diraktory : " + chois);
                    exists = false;
                }
                if (exists &(!paswerd.exists())) {
                    if ((JOptionPane.showConfirmDialog(null,
                            "selekted file dos not exists\nDo you vant to create the file",
                            "selekted file dos not exists", JOptionPane.OK_CANCEL_OPTION)) == 0)
                    {
                        int dif = (paswerd.getName().length()) - (paswerd.getName().lastIndexOf(".key"));
                        if (dif != 4) {
                            paswerd = new File(paswerd.getAbsolutePath() + ".key");
                        }
                        try {
                            paswerd.createNewFile();
                        } catch (IOException ex) {
                            view.setGenirationstatuslabeltext("Error geting file");
                            throw new RuntimeException(ex);
                        }
                    } else exists = false;
                }
                if (exists) {
                    long sise = paswerd.length();
                    if (sise > 0) {

                        if (JOptionPane.showConfirmDialog(
                                null,
                                """
                                        filen som är selektad är int tom\s
                                        ska den användas än då\s
                                        (om det inte är en nykel så kommer allt på filen raderas)""",
                                "filens storlek är inte 0",
                                JOptionPane.YES_NO_OPTION
                        ) == 0) {
                            byte status = testAesKye(paswerd);
                            if ((status == -4) || (status == -3)) {
                                if (JOptionPane.showConfirmDialog(
                                        null,
                                        "det som är på filen är inte serialise\n" +
                                        "ska programme fortfarande använda fillen och ta bort innehållet på filen",
                                        "programme kan inte läsa det",
                                        JOptionPane.YES_NO_OPTION
                                ) == JOptionPane.YES_OPTION) {
                                    key = paswerd;
                                    view.getPasvirddisplayfield().setText(key.getAbsolutePath());
                                    selektedfile = true;
                                    uppdatesavebutton();
                                } else {
                                    selektedfile = false;
                                }
                            }
                            if (status == -1) {
                                if (JOptionPane.showConfirmDialog(
                                        null,
                                        "Det som är på filen verkar inte vara en nyckel.\n" +
                                                "ska programme fortsätta och ta bort data på filen") == 0) {
                                    selektedfile = true;
                                    key = paswerd;
                                    view.getPasvirddisplayfield().setText(key.getAbsolutePath());
                                    uppdatesavebutton();
                                } else {
                                    selektedfile = false;
                                }
                            }
                            if (status == 1) {
                                key = paswerd;
                                view.getPasvirddisplayfield().setText(key.getAbsolutePath());
                                selektedfile = true;
                                keyisinfile = true;
                                uppdatesavebutton();
                                if (JOptionPane.showConfirmDialog(null,
                                        "do you vant to juse the key",
                                        "AES key in file",
                                        JOptionPane.YES_NO_OPTION) == 0) {

                                    view.setGenirationstatuslabelcolor(defaltcolor);
                                    view.setGenirationstatuslabeltext("Aes key is set");
                                    model.setAESkey(key);
                                    secretKey = model.getAESkey();
                                } else {
                                    view.setGenirationstatuslabeltext("AES key is in file.");
                                    view.setGenirationstatuslabelcolor(defaltcolor);
                                    keyisused = true;
                                }

                            }
                            if (status == 0) {
                                selektedfile = true;
                                keyisinfile = false;
                                uppdatesavebutton();
                                view.setGenirationstatuslabeltext("File dos not hav a AES key in it");
                                view.setGenirationstatuslabelcolor(defaltcolor);
                            }
                        }
                    }
                }
            }

        }
    }
    private void uppdatesavebutton() {
        view.enebulsavbutton(selektedfile && keyisasaind);
    }
    private byte testAesKye(File paswerd) {
        BufferedInputStream bufferedInputStream;
        ObjectInputStream inputStream;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(paswerd));
        } catch (FileNotFoundException e) {
            return -5;
            //throw new RuntimeException(e);
        }
        try {
            inputStream = new ObjectInputStream(bufferedInputStream);
        } catch (IOException e) {
            return -4;
            //throw new RuntimeException(e);
        }
        Object object;
        try {
            object = inputStream.readObject();
        } catch (IOException e) {
            return -3;
            //throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            return -2;
            //throw new RuntimeException(e);
        }
        if (object.getClass() == SecretKeySpec.class) {
            SecretKey secretKey = (SecretKey) object;
            if (Objects.equals(secretKey.getAlgorithm(), "AES")) {
                return 1;
            }
        }
        System.out.println(0);
        return 0;
    }

    private class geniraitkeyAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean c = true;
            if (keyisused) {
                if (!(
                JOptionPane.showConfirmDialog(null,
                        "Key is alredy asainde\n" +
                                "do you vant to over rite it",
                        "key is alrady asainde",
                        JOptionPane.YES_NO_OPTION) == 1)) {
                    c = false;
                }
            }
            if (c) {
                SecureRandom sr = new SecureRandom();
                KeyGenerator kg;
                try {
                    kg = KeyGenerator.getInstance("AES");
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
                kg.init(256, sr);
                SecretKey key1 = kg.generateKey();
                secretKey = key1;
                keyisasaind = true;
                model.setAeskey(key1);
                uppdatesavebutton();
                view.setGenirationstatuslabeltext("key is genirated");
                view.setGenirationstatuslabelcolor(defaltcolor);
            } else {
                view.setGenirationstatuslabeltext("");
                view.setGenirationstatuslabelcolor(defaltcolor);
            }
        }
    }

    private class savekeyAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ((key != null) && (secretKey != null)) {
                ObjectOutputStream outputStream;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(key));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    outputStream.writeObject(secretKey);
                    outputStream.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                view.enebulsavbutton(false);
                view.setGenirationstatuslabeltext("Kye is saved to the file");
                view.setGenirationstatuslabelcolor(defaltcolor);
            }
        }
    }

    private class enkrypTogelIL implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            int stat = e.getStateChange();
            if (stat == 1) {
                model.setEncryption(true);
            }
            if (stat == 2) {
                model.setEncryption(false);
            }
        }
    }

    private class inFileAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File fileIN = fileChooser.getSelectedFile();
                model.setSettingsFileIn(fileIN);
                view.getFileInTextField().setText(fileIN.getAbsolutePath());
            }
        }
    }

    private class outFileAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                fileOut = fileChooser.getSelectedFile();
                model.setSettingsFileOu(fileOut);
                view.getOutFileTextField().setText(fileOut.getAbsolutePath());
            }
        }
    }

    private class outFileTogelIL implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            int stat = e.getStateChange();
            if (stat == 1) {
                view.getOutFileButton().setEnabled(true);
                if (fileOut != null) {
                    model.setSettingsFileOu(fileOut);
                }
                model.setSettingsFileOutStor(true);
            }
            if (stat == 2) {
                view.getOutFileButton().setEnabled(false);
                model.setSettingsFileOuNull();
                model.setSettingsFileOutStor(false);
            }
        }
    }

    private class manulSnapsoTogelIL implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            int status;
            if ((status = e.getStateChange()) == 1) {
                model.setManusnapshot(true);
            }
            if (status == 2) {
                model.setManusnapshot(false);
            }
        }
    }

    private class repetisonsCL implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            short max = 999;
            try {
                view.getRepetisonKonterSpinner().commitEdit();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            int numint = (Integer) view.getRepetisonKonterSpinner().getValue();
            short num = (short) numint;
            if (num < 1) {
                view.getRepetisonKonterSpinner().setValue(1);
                model.setNumofRepit(1);
            }
            if (num > max) {
                view.getRepetisonKonterSpinner().setValue(max);
                model.setNumofRepit(max);
            }
            if ((num >= 1) && (num < max)) {
                model.setNumofRepit(num);
            }
        }
    }

    private class doneSettingsAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.isCheckTrue()) {
                model.updattSettings();
                ObjectOutputStream outputStream;
                try {
                    outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(settingsfile)));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    outputStream.writeObject(model.getSettings());
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                byte[] bytes = model.getNumofNonCheck();
                if (bytes.length > 3) {
                    view.getStatusLabel().setText("multibel filds ar not set");
                } else {
                    view.getStatusLabel().setText("som filds ar not set");
                }
            }
        }
    }

    private class debugmenuAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.updattSettings();
            System.out.println("--------- debug -----------\n" +
                    model.getSettings() + '\n' +
                    Arrays.toString(model.getCheck()) + '\n' +
                    Arrays.toString(model.getNumofNonCheck()) + "\n\n" +
                    model.getAes());
            Settingsfile settingsfile1 = model.getSettingsfile();
            String files;
            if (settingsfile1 == null) {
                files = "settingsfile = null";
            } else
                files = settingsfile1.toString();
            System.out.println(files);
            System.out.println("--------- debug -----------");
        }
    }
}
