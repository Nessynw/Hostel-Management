package Controler;

import Model.*;
import Vue.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class personnelButtonControler implements ActionListener {
    private JFrame parentFrame;
    private Hotel hotel;

    public personnelButtonControler(JFrame f, Hotel h) {
        this.parentFrame = f;
        this.hotel = h;
    }

    public void actionPerformed(ActionEvent e) {
        // Créer et afficher la fenêtre d'authentification
        Authentification authentification = new Authentification(new Runnable() {
            @Override
            public void run() {
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new InterfacePersonnel(parentFrame, hotel));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        }, hotel);  // Ajout du paramètre hotel
        authentification.setVisible(true);
    }
}