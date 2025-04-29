package Controler;
import Vue.*;
import Model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class PersonnelControler implements ActionListener {
    private Hotel hotel;
    private InterfacePersonnel vuePrincipale;
    private AfficherEmploye vueEmploye;
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private JFrame frame;

    public PersonnelControler(JFrame frame, InterfacePersonnel vue, Hotel h) {
        this.frame = frame;
        vuePrincipale = vue;
        hotel = h;
        this.vueEmploye = new AfficherEmploye(frame); // par exemple, si c’est le bon constructeur

        //Configuration des containers
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        mainContainer.add(vuePrincipale, "principal");
        mainContainer.add(vueEmploye, "employes");

        //écouteurs
        setupListeners();
    }

    private void setupListeners() {

        //Voir tous les employés
        vuePrincipale.getBtnTousEmployes().addActionListener(e -> {
            chargerDonneesEmployes(); //Remplir le tableau d'employes
            cardLayout.show(mainContainer, "employes");
        });
        vueEmploye.getBtnRetour().addActionListener(e -> {
            cardLayout.show(mainContainer, "principal");
        });
    }

    private void chargerDonneesEmployes() {
        DefaultTableModel model = (DefaultTableModel) vueEmploye.getTableEmployes().getModel();
        model.setRowCount(0); //vider le tableau

        Object[] receptionniste = {"Dupont", "Jean", "Receptionniste", 2500, "Accueil clients", ""};
        Object[] agentE = {"Martin", "Lucie", "AgentE", 2000, "Nettoyage", "Chambre 204"};

        model.addRow(receptionniste);
        model.addRow(agentE);

    }

    public JPanel getMainContainer() {
        return mainContainer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Créer et afficher la fenêtre d'authentification avec un callback
        Authentification authentification = new Authentification(new Runnable() {
            @Override
            public void run() {
                // Si l'authentification est réussie, changer l'interface
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new InterfacePersonnel(frame));
                frame.revalidate();
                frame.repaint();
            }
        });
        authentification.setVisible(true);

        frame.setContentPane(mainContainer);
          frame.revalidate();
            frame.repaint();// Afficher la fenêtre d'authentification
    }
}




