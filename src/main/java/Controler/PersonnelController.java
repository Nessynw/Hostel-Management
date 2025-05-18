package Controler;

import Vue.*;
import Model.*;
import javax.swing.*;

public class PersonnelController {
    private JFrame parentFrame;
    private Hotel hotel;
    private InterfacePersonnel vue;

    public PersonnelController(JFrame frame, Hotel hotel) {
        this.parentFrame = frame;
        this.hotel = hotel;
        this.vue = new InterfacePersonnel(frame, hotel);
        initializeListeners();
    }

    private void initializeListeners() {
        vue.getBtnTousEmployes().addActionListener(e -> afficherEmployes());
        vue.getBtnAjouterEmploye().addActionListener(e -> afficherFormulaireAjout());
        vue.getBtnSupprimerEmploye().addActionListener(e -> afficherFormulaireSuppression());
    }

    private void afficherEmployes() {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new AfficherEmploye(parentFrame, hotel));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void afficherFormulaireAjout() {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new AjouterEmploye(parentFrame, hotel));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void afficherFormulaireSuppression() {
        parentFrame.getContentPane().removeAll();
        SupprimerEmploye supprimerEmploye = new SupprimerEmploye(parentFrame, hotel);
        supprimerEmploye.setListeEmployes(hotel.getListEmploye());
        parentFrame.getContentPane().add(supprimerEmploye);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    public JPanel getVue() {
        return vue;
    }
}