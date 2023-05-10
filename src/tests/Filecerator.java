package tests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filecerator {
    private JPanel panel1;
    private JPanel siseinput;
    private JPanel filegenirator;
    private JPanel creationpanel;
    private JTextField DirektorytextField;
    private JTextField FileNamntextField;
    private JButton DirectoryButton;
    private JButton TestFilebutton;
    private JTextField GBtextField;
    private JButton Okbutton;
    private JLabel StatusLabel;
    private JLabel GBlabel;
    private JLabel DirectoryStatesLabel;
    private JLabel FileNameTestStateslabel;
    private JLabel StatusValueLabel;
    private File diraktoryFile;
    private String diraktoryString;
    private File newFilefile;
    private String newFilename;

    private final boolean[] chekse = new boolean[]{false,false,false,false};
    private static final Color defaltcolor = new Color(51,51,51);
    private static final Color betergrin = null;
    private static final Color beterred = null;
    private static final int maxsize = 100;
    private static final int maxlinelenkt = 2000;

    private final Pattern iligalchars = Pattern.compile("[<>:?*\\\\/\"|]");
    private final Pattern iligalstrings = Pattern.compile(
            "\\A(CON\\.|PRN\\.|AUX\\.|NUL\\.|COM0\\.|COM1\\.|COM2\\.|COM3\\.|COM4\\.|" +
                    "COM5\\.|COM6\\.|COM7\\.|COM8\\.|COM9\\.|LPT0\\.|LPT1\\.|LPT2\\.|LPT3\\.|" +
                    "LPT4\\.|LPT5\\.|LPT6\\.|LPT7\\.|LPT8\\.|LPT9\\.)"
    );

    /*
    private final char[] iligalchars = new char[]{'<','>',':','"','/','\\','|','?','*',};
    private final String[] iligalstrings = new String[]{
            "CON.","PRN.","AUX.","NUL.","COM0.","COM1.","COM2.","COM3.","COM4.","COM5.","COM6.","COM7.",
            "COM8.","COM9.","LPT0.","LPT1.","LPT2.","LPT3.","LPT4.","LPT5.","LPT6.","LPT7.","LPT8.","LPT9."
    };
    "CON","PRN","AUX","NUL","COM0","COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9","LPT0","LPT1","LPT2","LPT3","LPT4","LPT5","LPT6","LPT7","LPT8","LPT9",
    "CON.","PRN.","AUX.","NUL.","COM0.","COM1.","COM2.","COM3.","COM4.","COM5.","COM6.","COM7.","COM8.","COM9.","LPT0.","LPT1.","LPT2.","LPT3.","LPT4.","LPT5.","LPT6.","LPT7.","LPT8.","LPT9."
    */

    public Filecerator() {

        DirectoryButton.addActionListener(new diraktorygetor());

        TestFilebutton.addActionListener(new filegenirator());

        Okbutton.addActionListener(new geniratorAL());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Filecerator");
        frame.setContentPane(new Filecerator().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void resetlabels(JLabel label) {
        label.setForeground(defaltcolor);
        label.setText("");
    }



    private class diraktorygetor implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            resetlabels(DirectoryStatesLabel);
            JFileChooser diraktorychooser = new JFileChooser();
            diraktorychooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int o;
            o = diraktorychooser.showOpenDialog(null);
            if (o == JFileChooser.APPROVE_OPTION) {

                diraktoryFile = diraktorychooser.getSelectedFile();
                boolean exists = diraktoryFile.exists();
                boolean isNotAFile = diraktoryFile.isFile();
                if (exists && !isNotAFile) {
                    String nameOfDiraktory = diraktoryFile.getAbsolutePath();
                    if (!Objects.equals(diraktoryString, nameOfDiraktory)) {
                        diraktoryString = nameOfDiraktory;
                        chekse[0] = false;
                    }
                    if (!chekse[0]) {
                        chekse[0] = true;
                        DirektorytextField.setText(nameOfDiraktory);
                        DirectoryStatesLabel.setForeground(Color.green);
                        DirectoryStatesLabel.setText("successfully got the directory");
                    }
                } else {
                    DirectoryStatesLabel.setForeground(Color.red);
                    DirectoryStatesLabel.setText("Someting went wrong geting the selekted directory");
                }
            } else if (o != JFileChooser.CANCEL_OPTION){
                DirectoryStatesLabel.setForeground(Color.red);
                DirectoryStatesLabel.setText("something vent wrong");
            }
        }
    }

    private class filegenirator implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            resetlabels(FileNameTestStateslabel);
            String nameOfnewFile = FileNamntextField.getText().trim();
            if (!Objects.equals(nameOfnewFile, newFilename)) {
                newFilename = nameOfnewFile;
                chekse[1] = false;
            }

            boolean validecheck = false;
            boolean txtcheck = false;
            if (!chekse[1]) {
                int lentofname = newFilename.length();
                if (lentofname < 4) {
                    FileNameTestStateslabel.setForeground(defaltcolor);
                    FileNameTestStateslabel.setText("Name to short");
                } else if (lentofname >= 200) {
                    FileNameTestStateslabel.setForeground(defaltcolor);
                    FileNameTestStateslabel.setText("Name to long");
                } else {
                    validecheck = true;
                    if (!iligalchars.matcher(newFilename).find()) {
                        System.out.println(newFilename);
                        Matcher iligalstingsmach = iligalstrings.matcher(newFilename);

                        boolean fond = iligalstingsmach.find();
                        System.out.println(fond);


                        int contensdottxt = newFilename.lastIndexOf(".txt");
                        if (contensdottxt != -1) {
                            if (((lentofname - contensdottxt) == 4) && (contensdottxt >= 4)) {
                                txtcheck = true;
                            } else {
                                validecheck = false;
                                FileNameTestStateslabel.setForeground(defaltcolor);
                                FileNameTestStateslabel.setText("Name to short");
                            }
                        }
                    } else {
                        validecheck = false;
                        FileNameTestStateslabel.setForeground(Color.red);
                        FileNameTestStateslabel.setText("Name contains illegal characters");
                    }

                }
            }

                /*
                    check till att se så att namnet går att använda.
                    implementering senare.
                */
            if (validecheck) {
                if (!txtcheck) {
                    newFilename = newFilename + ".txt";
                }
                if (chekse[0]) {
                    newFilefile = new File(diraktoryFile, newFilename);
                    System.out.println(newFilefile.getName() + "  ||\n" + newFilefile.getAbsolutePath());
                } else {
                    newFilefile = new File(newFilename);
                    System.out.println(newFilefile.getName() + "  ||\n" + newFilefile.getAbsolutePath());
                }
                boolean filecreate;
                boolean eror = false;
                try {
                    filecreate = newFilefile.createNewFile();
                } catch (IOException ex) {
                    newFilefile = null;
                    filecreate = false;
                    chekse[1] = false;
                    eror = true;
                    FileNameTestStateslabel.setForeground(Color.red);
                    FileNameTestStateslabel.setText("something vent wrong (EROR!)");
                    throw new RuntimeException(ex);
                }
                if (!eror) {
                    if (filecreate) {
                        chekse[1] = true;
                        if (chekse[0]) {
                            FileNameTestStateslabel.setForeground(Color.green);
                            FileNameTestStateslabel.setText("File successfully created");
                            chekse[2] = true;
                        } else {
                            FileNameTestStateslabel.setForeground(defaltcolor);
                            FileNameTestStateslabel.setText("File name is valid");
                            newFilefile.delete();
                        }
                    } else {
                        if (newFilefile.exists()) {
                            FileNameTestStateslabel.setForeground(defaltcolor);
                            FileNameTestStateslabel.setText("The given name is valid but a file vid dat name already exists");
                        } else {
                            FileNameTestStateslabel.setForeground(Color.red);
                            FileNameTestStateslabel.setText("something vent wrong");
                        }
                    }
                }
            }
        }
    }

    private class geniratorAL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            resetlabels(StatusValueLabel);
            String input = GBtextField.getText().trim();
            boolean isNumerikal = isNumeric(input);
            if (!isNumerikal) {
                StatusValueLabel.setText("size is inputted inkorektly");
            }
            int size = 0;
            if (isNumerikal) {
                size = Integer.parseInt(input);
                chekse[3] = true;
                if (size > maxsize) {
                    StatusValueLabel.setText("size is to big");
                    chekse[3] = false;
                }
            }
            System.out.println(Arrays.toString(chekse));
            StatusValueLabel.setText("Filing file");
            if (AllTrue(chekse)) {
                System.out.println("Filing file");
                fillFilevidrandom(newFilefile,size);
                StatusValueLabel.setText("File suksesfuly fild to " + size + "GB");
            } else {
                StatusValueLabel.setText("andra är inte klar");
            }
        }
    }

    private void fillFilevidrandom(File newFilefile, int size) {
        int nekstnewline = 0;
        BufferedOutputStream outputStream;
        byte[] randomleters = new byte[1024];
        Random random = new Random();
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(newFilefile));
            for (int G = 0; G < size; G++) {
                for (int MB = 0; MB < 1024; MB++) {
                    for (int KB = 0; KB < 1024; KB++) {
                        random.nextBytes(randomleters);
                        outputStream.write(randomleters);
                        outputStream.flush();
                    }
                }
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean AllTrue(boolean[] chekse) {
        for (boolean value : chekse) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumeric(String input) {
        return input != null && input.matches("[0-9]+");
    }

}
