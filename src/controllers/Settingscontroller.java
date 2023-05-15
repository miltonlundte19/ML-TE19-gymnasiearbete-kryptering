package controllers;

import modules.SettingsModel;
import view.Settingsview;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Settingscontroller {
    private Settingsview view;
    private SettingsModel modell;

    public static void main(String[] args) {
        Settingscontroller fillecreitorcontroller = new Settingscontroller();
    }
    public Settingscontroller() {
        view = new Settingsview();
        modell = new SettingsModel();

        JFrame frame = new JFrame("Fillecreitrorview");
        frame.setContentPane(view.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setButtons();

        frame.pack();
        frame.setVisible(true);
    }

    private void setButtons() {
        view.getIvselektorcombox().addActionListener(new setcrypidAL());
        view.getSelektpasbutton().addActionListener(new selektpaswerdAL());
        view.getGeniraitepasbutton().addActionListener(new getpaswirdeAL());
        view.getFileinbutton().addActionListener(new getfilinAL());
        view.getFileoutcheckBox().addItemListener(new fileouttogelIL());
        view.getRepetisonkonter().addChangeListener(new repetsonconterCL());
        view.getFileoutbutton().addActionListener(new getfileoutAL());
    }

    private class setcrypidAL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class selektpaswerdAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class getpaswirdeAL implements ActionListener{
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
