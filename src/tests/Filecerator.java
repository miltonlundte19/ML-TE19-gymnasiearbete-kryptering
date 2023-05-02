package tests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Objects;

public class Filecerator {
    private JPanel panel1;
    private JTextField DirektorytextField;
    private JTextField FileNametextField;
    private JButton DirektoryButton;
    private JButton TestFilebutton;
    private JTextField GBtextField;
    private JButton Okbutton;
    private JLabel StatusLabel;
    private JLabel GBlabel;
    private JLabel DirektoryStatesLabel;
    private JLabel FileNameTestStateslabel;
    private JLabel StatusValuLabel;
    private File diraktoryFile;
    private String diraktoryString;
    private File newFilefile;
    private String newFilename;

    private boolean[] chekse = new boolean[4];
    private final Color defaltcolor = new Color(51,51,51);

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

        DirektoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                            DirektoryStatesLabel.setForeground(Color.green);
                            DirektoryStatesLabel.setText("successfully got the directory");
                        }
                    } else {
                        DirektoryStatesLabel.setForeground(Color.red);
                        DirektoryStatesLabel.setText("Someting went wrong geting the selekted directory");
                    }
                } else if (o != JFileChooser.CANCEL_OPTION){
                    DirektoryStatesLabel.setForeground(Color.red);
                    DirektoryStatesLabel.setText("something vent wrong");
                }
            }
        });

        TestFilebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameOfnewFile = FileNametextField.getText();
                if (!Objects.equals(nameOfnewFile, newFilename)) {
                    newFilename = nameOfnewFile;
                    chekse[1] = false;
                }

                boolean lentchek = false;
                boolean txtchek = false;

                int lentofname = newFilename.length();
                if (lentofname < 4) {
                    FileNameTestStateslabel.setForeground(defaltcolor);
                    FileNameTestStateslabel.setText("Name to short");
                } else if (lentofname >= 200) {
                    FileNameTestStateslabel.setForeground(defaltcolor);
                    FileNameTestStateslabel.setText("Name to long");
                } else {
                    lentchek = true;
                    int contensdottxt = newFilename.lastIndexOf(".txt");
                    if (contensdottxt != -1) {
                        if (((lentofname - contensdottxt) == 4) && (contensdottxt >= 4)) {
                            txtchek = true;
                        } else {
                            lentchek = false;
                            FileNameTestStateslabel.setForeground(defaltcolor);
                            FileNameTestStateslabel.setText("Name to short");
                        }
                    }
                }
                /*
                    chek till att se så att namnet går att använda.
                    implementering senare.
                */
                if (lentchek) {
                    if (chekse[0]) {
                        if (!txtchek) {
                            newFilename = newFilename + ".txt";
                        }
                        newFilefile = new File(diraktoryFile, newFilename);
                        System.out.println(diraktoryFile.getAbsolutePath() + " | ");

                    }
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Filecerator");
        frame.setContentPane(new Filecerator().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void Funktions() {

    }
}
