package view;

import javax.swing.*;

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

    public JPanel getPanel1() {
        return panel1;
    }

    public JComboBox getIvselektorcombox() {
        return ivselektorcombox;
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
}
