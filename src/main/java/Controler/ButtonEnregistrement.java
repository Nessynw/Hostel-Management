package Controler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import Model.*;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ButtonEnregistrement implements ActionListener {
    // Déclarer les champs de l'interface utilisateur
    private JTextField nameField;
    private JTextField prenomField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField adresseField;
    private JTextField startDateField;
    private JTextField endDateField;
    private Hotel h ;
    private Client c ;
    // Définir les couleurs pour la validation
    public static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    public static final Color fieldColor = new Color(33, 33, 33); // Couleur de fond des champs
    public static final Color errorColor = new Color(255, 102, 102);  // Couleur rouge pour les champs invalides
    public  static final Color validColor = Color.WHITE;
    // Constructeur pour initialiser les champs
    public ButtonEnregistrement(Hotel hotel , JTextField nameField, JTextField prenomField, JTextField emailField,
                                JTextField phoneField, JTextField adresseField, JTextField startDateField, JTextField endDateField) {
        this.nameField = nameField;
        this.prenomField = prenomField;
        this.emailField = emailField;
        this.phoneField = phoneField;
        this.adresseField = adresseField;
        this.startDateField = startDateField;
        this.endDateField = endDateField;
        this.h = hotel;
    }

    // Méthode qui sera appelée lorsque le bouton d'enregistrement est cliqué
    @Override
    public void actionPerformed(ActionEvent e) {
        // Récupérer les données saisies
        String name = nameField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String adresse = adresseField.getText();

        // Vérifier si la date de début et de fin sont vides et les convertir en LocalDate uniquement si elles ne sont pas vides
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (!startDateField.getText().isEmpty()) {
            startDate = parseLocalDate(startDateField.getText());
        }
        if (!endDateField.getText().isEmpty()) {
            endDate = parseLocalDate(endDateField.getText());
        }

        // Vérification si les dates sont bien converties
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);

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

        // Vérifier la cohérence des dates
        if (!ClientValidator.verifierCoherenceDates(startDate, endDate)) {
            startDateField.setBackground(errorColor);
            endDateField.setBackground(errorColor);
            valid = false;
        } else {
            startDateField.setBackground(fieldColor);
            endDateField.setBackground(fieldColor);
        }

        // Si tous les champs sont valides, procéder à l'enregistrement
        if (valid) {
            JOptionPane.showMessageDialog(null, "Client enregistré avec succès!");
            c= new Client ( name , prenom ,email,phone , adresse , h);
            h.ajouterClient(c);
            h.afficherListeClients();


            // Logique d'enregistrement ici (ajouter votre code pour enregistrer les données dans la base de données)
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez corriger les erreurs dans le formulaire.");
        }
    }

    // Méthode pour analyser les dates sous forme de chaîne
    private LocalDate parseLocalDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            System.out.println("Erreur de conversion de la date : " + dateStr);
            return null;  // Retourner null si la date est invalide
        }
    }
}