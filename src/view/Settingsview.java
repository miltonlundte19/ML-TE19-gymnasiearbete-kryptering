package view;

import javax.swing.*;
import java.awt.*;

public class Settingsview {

    private JPanel panel1;
    private JPanel ivselekterpanel;
    private JPanel pasvirdpanel;
    private JPanel filesilektorpanel;


    private JComboBox ivselektorcombox;
    private JButton ivselektbutton;
    private JButton selektpasbutton;
    private JButton geniraitepasbutton;
    private JButton saveButton;
    private JTextField pasvirddisplayfield;
    private JLabel genirationstatuslabel;
    private JCheckBox mesideCheckBox;
    private JCheckBox enkryptionCheckBox;
    private JButton inFileButton;
    private JTextField fileInTextField;
    private JCheckBox outputFileCheckBox;
    private JSpinner repetisonKonterSpinner;
    private JButton outFileButton;
    private JTextField outFileTextField;
    private JButton doneButton;
    private JLabel statusLabel;
    private JCheckBox manulSnapshotCheckBox;


    public JPanel getPanel1() {
        return panel1;
    }

    public JComboBox getIvselektorcombox() {
        return ivselektorcombox;
    }
    public int getIvselektedcombox() {
        return ivselektorcombox.getSelectedIndex();
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
    public JButton getSaveButton() {
        return saveButton;
    }
    public JButton getInFileButton() {
        return inFileButton;
    }
    public JButton getOutFileButton() {
        return outFileButton;
    }
    public JButton getDoneButton() {
        return doneButton;
    }

    public JLabel getGenirationstatuslabel() {
        return genirationstatuslabel;
    }
    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JTextField getPasvirddisplayfield() {
        return pasvirddisplayfield;
    }
    public JTextField getFileInTextField() {
        return fileInTextField;
    }
    public JTextField getOutFileTextField() {
        return outFileTextField;
    }

    public boolean getsavebuttonenabeld() {
        return saveButton.isEnabled();
    }
    public void setGenirationstatuslabeltext(String text) {
        genirationstatuslabel.setText(text);
    }

    public void setGenirationstatuslabelcolor(Color color) {
        genirationstatuslabel.setBackground(color);
    }
    public void enebulsavbutton(boolean b) {
        saveButton.setEnabled(b);
    }

    public JCheckBox getEnkryptionCheckBox() {
        return enkryptionCheckBox;
    }
    public JCheckBox getOutputFileCheckBox() {
        return outputFileCheckBox;
    }
    public JCheckBox getManulSnapshotCheckBox() {
        return manulSnapshotCheckBox;
    }

    public JSpinner getRepetisonKonterSpinner() {
        return repetisonKonterSpinner;
    }
}
