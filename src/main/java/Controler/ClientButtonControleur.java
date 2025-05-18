package Controler;

import Vue.Authentification;
import Vue.InterfaceClient;
import Model.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientButtonControleur implements ActionListener {
    private JFrame parentFrame;
    private Hotel hotel;

    public ClientButtonControleur(JFrame f, Hotel h) {
        this.parentFrame = f;
        this.hotel = h;
    }

    public void actionPerformed(ActionEvent e) {
        Authentification authentification = new Authentification(new Runnable() {
            @Override
            public void run() {
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new InterfaceClient(hotel));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        }, hotel);
        authentification.setVisible(true);
    }
}