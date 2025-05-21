package Controler;

import Vue.Authentification;
import Model.Hotel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonnelButtonControleur implements ActionListener {
    private JFrame parentFrame;
    private Hotel hotel;

    public PersonnelButtonControleur(JFrame parentFrame, Hotel hotel) {
        this.parentFrame = parentFrame;
        this.hotel = hotel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Authentification authentification = new Authentification(() -> {
            PersonnelController controller = new PersonnelController(parentFrame, hotel);
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(controller.getVue());
            parentFrame.revalidate();
            parentFrame.repaint();
        }, hotel);
        authentification.setVisible(true);
    }
}