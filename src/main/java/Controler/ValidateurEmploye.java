package Controler;

import javax.swing.*;
import java.awt.*;

public class ValidateurEmploye {
    private static final Color COULEUR_ERREUR = Color.PINK;
    private static final Color COULEUR_NORMALE = new Color(30, 30, 70);
    
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtEmail;
    private JTextField txtTel;
    private JTextField txtAdresse;

    public ValidateurEmploye(JTextField txtNom, JTextField txtPrenom, 
                           JTextField txtEmail, JTextField txtTel, 
                           JTextField txtAdresse) {
        this.txtNom = txtNom;
        this.txtPrenom = txtPrenom;
        this.txtEmail = txtEmail;
        this.txtTel = txtTel;
        this.txtAdresse = txtAdresse;
    }

    public boolean valider() {
        boolean valide = true;
        StringBuilder erreurs = new StringBuilder();

        // Vérification des champs vides
        JTextField[] champsObligatoires = {
            txtNom, txtPrenom, txtEmail, txtTel, txtAdresse
        };

        boolean champsManquants = false;
        for (JTextField champ : champsObligatoires) {
            if (champ.getText().trim().isEmpty()) {
                champ.setBackground(COULEUR_ERREUR);
                champsManquants = true;
                valide = false;
            } else {
                champ.setBackground(COULEUR_NORMALE);
            }
        }

        if (champsManquants) {
            erreurs.append("Veuillez remplir tous les champs obligatoires.\n");
            JOptionPane.showMessageDialog(null, erreurs.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Si les champs sont remplis, on vérifie leur format
        if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            txtEmail.setBackground(COULEUR_ERREUR);
            erreurs.append("Format d'email invalide.\n");
            valide = false;
        }

        if (!txtTel.getText().matches("\\d{10}")) {
            txtTel.setBackground(COULEUR_ERREUR);
            erreurs.append("Le numéro de téléphone doit contenir 10 chiffres.\n");
            valide = false;
        }



        if (!valide) {
            JOptionPane.showMessageDialog(null, erreurs.toString(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return valide;
    }
}