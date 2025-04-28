package Controler;

import Vue.Authentification;
import Vue.InterfaceClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.* ;
import javax.swing.*;
public class ClientButtonControleur  implements ActionListener {
    JFrame parentFrame ;
    Hotel hotel;
    public ClientButtonControleur(JFrame f , Hotel h ) {
        parentFrame = f;
        hotel = h;
    }
    public void actionPerformed(ActionEvent e) {
        // Créer et afficher la fenêtre d'authentification avec un callback
        Authentification authentification = new Authentification(new Runnable() {
            @Override
            public void run() {
                // Si l'authentification est réussie, changer l'interface
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new InterfaceClient(hotel));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
        authentification.setVisible(true);  // Afficher la fenêtre d'authentification
    }


}
