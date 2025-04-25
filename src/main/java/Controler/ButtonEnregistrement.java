package Controler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import Model.*;


public class ButtonEnregistrement implements ActionListener {
        JTextField nameField , prenomField, emailField, phoneField, adresseField , startDateField , endDateField ;
        Client c ;
        Hotel hot ;
        public static final Color main_color = new Color(18, 11, 61);  // Couleur de base
        public static final Color fieldColor = new Color(33, 33, 33); // Couleur de fond des champs
        public static final Color errorColor = new Color(255, 102, 102);  // Couleur rouge pour les champs invalides
        public  static final Color validColor = Color.WHITE;
        public ButtonEnregistrement(Hotel h, JTextField n , JTextField pr , JTextField e , JTextField p , JTextField a  ) {
            hot = h ;
            nameField=n;
            prenomField=pr;
            emailField=e;
            phoneField=p;
            adresseField=a;
        }
        public void actionPerformed(ActionEvent e) {
            // Récupérer les données saisies
            String name = nameField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String adresse = adresseField.getText();
            LocalDate startDate = (startDateField.getText().isEmpty()) ? null : LocalDate.parse(startDateField.getText());
            LocalDate endDate = (endDateField.getText().isEmpty()) ? null : LocalDate.parse(endDateField.getText());

            // Validation des champs
            boolean valid = true;

            // Vérifier le nom
            if (!ClientValidator.isValidName(name)) {
                nameField.setBackground(errorColor);
                valid = false;
            } else {
                nameField.setBackground(fieldColor);
            }

            // Vérifier le prénom
            if (!ClientValidator.isValidName(prenom)) {
                prenomField.setBackground(errorColor);
                valid = false;
            } else {
                prenomField.setBackground(fieldColor);
            }

            // Vérifier l'email
            if (!ClientValidator.isValidEmail(email)) {
                emailField.setBackground(errorColor);
                valid = false;
            } else {
                emailField.setBackground(fieldColor);
            }

            // Vérifier le numéro de téléphone
            if (!ClientValidator.isValidPhoneNumber(phone)) {
                phoneField.setBackground(errorColor);
                valid = false;
            } else {
                phoneField.setBackground(fieldColor);
            }

            if(!ClientValidator.verifierCoherenceDates(startDate,endDate))
            {
                startDateField.setBackground(errorColor);
                endDateField.setBackground(errorColor);
                valid = false;
            }else {
                startDateField.setBackground(fieldColor);
                endDateField.setBackground(fieldColor);
            }

            // Si tous les champs sont valides, procéder à l'enregistrement
            if (valid) {

                JOptionPane.showMessageDialog(null, "Client enregistré avec succès!");


                // Logique d'enregistrement ici
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez corriger les erreurs dans le formulaire.");
            }
        }
    }

