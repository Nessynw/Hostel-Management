package Controler;

import Model.Hotel;
import Vue.Authentification;
import Vue.InterfacePersonnel;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class personnelButtonControler implements ActionListener {
    JFrame parentFrame;
    Hotel hotel;

    public personnelButtonControler(JFrame f) {
        parentFrame = f;


    }

    public void actionPerformed(ActionEvent e) {
        // Créer et afficher la fenêtre d'authentification
        Authentification authentification = new Authentification(new Runnable() {
            @Override
            public void run() {
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new InterfacePersonnel());
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        authentification.setVisible(true);  // Afficher la fenêtre d'authentification
    }
}
