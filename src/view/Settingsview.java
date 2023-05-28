package view;

import javax.swing.*;
import java.awt.Color;


public class Settingsview {
    private JPanel panel1;
    private JLabel ivlabel;
    private JComboBox ivselektorcombox;
    private JPanel ivselekterpanel;
    private JPanel pasvirdpanel;
    private JLabel Passwirde;
    private JButton selektpasbutton;
    private JButton geniraitepasbutton;
    private JTextField pasvirddisplayfield;
    private JPanel filesilektorpanel;
    private JLabel filemesigelabel;
    private JButton fileinbutton;
    private JTextField fileintextfild;
    private JCheckBox fileoutcheckBox;
    private JButton fileoutbutton;
    private JTextField fileouttextField;
    private JSpinner repetisonkonter;
    private JLabel repetisonlabel;
    private JButton ivselektbutton;
    private JButton doneButton;
    private JLabel genirationstatuslabel;
    private JCheckBox mesidecheckbox;
    private JCheckBox enkryptionCheckBox;
    private JLabel statuslabel;
    private JButton saveButton;

    private static final Color defaltcolor = new Color(51,51,51);
    private static final Color betergrin = null;
    private static final Color beterred = null;

    public JPanel getPanel1() {
        return panel1;
    }

    public JComboBox getIvselektorcombox() {
        return ivselektorcombox;
    }
    public JButton getIvselektbutton() {
        return ivselektbutton;
    }

    public JButton getSelektpasbutton() {
        return selektpasbutton;
    }

    public JButton getGeniraitepasbutton() {
        return geniraitepasbutton;
    }

    public JTextField getPasvirddisplayfield() {
        return pasvirddisplayfield;
    }

    public JButton getFileinbutton() {
        return fileinbutton;
    }

    public JTextField getFileintextfild() {
        return fileintextfild;
    }

    public JCheckBox getFileoutcheckBox() {
        return fileoutcheckBox;
    }

    public JButton getFileoutbutton() {
        return fileoutbutton;
    }

    public JTextField getFileouttextField() {
        return fileouttextField;
    }

    public JSpinner getRepetisonkonter() {
        return repetisonkonter;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setPasvirddisplayfieldtext(String s) {
        pasvirddisplayfield.setBackground(defaltcolor);
        pasvirddisplayfield.setText(s);
    }
    public void setPasvirddisplayfieldtextError(String s) {
        pasvirddisplayfield.setBackground(Color.red);
        pasvirddisplayfield.setText(s);
    }

    public void enebilsavebuton() {
        saveButton.setEnabled(true);
    }
}
