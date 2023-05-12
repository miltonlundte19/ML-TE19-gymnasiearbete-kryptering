package controllers;

import modules.Fillecriatormodel;
import view.Fillecreitrorview;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fillecreitorcontroller {
    private Fillecreitrorview view;
    private Fillecriatormodel modell;

    public static void main(String[] args) {
        Fillecreitorcontroller fillecreitorcontroller = new Fillecreitorcontroller();
    }
    public Fillecreitorcontroller() {
        view = new Fillecreitrorview();
        modell = new Fillecriatormodel();

        JFrame frame = new JFrame("Fillecreitrorview");
        frame.setContentPane(view.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        frame.pack();
        frame.setVisible(true);
    }
}
