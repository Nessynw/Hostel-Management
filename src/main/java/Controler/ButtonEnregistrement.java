package Controler;

import Model.Client;
import Model.Hotel;
import Model.Reservation;
import Model.Chambre;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ButtonEnregistrement implements ActionListener {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Hotel hotel;
    private Chambre chambre;
    private JTextField nameField, prenomField, emailField, phoneField, adresseField;
    private JTextField startField, endField;

    public ButtonEnregistrement(Hotel hotel,
                                JTextField nameField,
                                JTextField prenomField,
                                JTextField emailField,
                                JTextField phoneField,
                                JTextField adresseField,
                                JTextField startField,
                                JTextField endField,
                                Chambre chambre) {
        this.hotel        = hotel;
        this.nameField    = nameField;
        this.prenomField  = prenomField;
        this.emailField   = emailField;
        this.phoneField   = phoneField;
        this.adresseField = adresseField;
        this.startField   = startField;
        this.endField     = endField;
        this.chambre      = chambre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name    = nameField.getText().trim();
        String prenom  = prenomField.getText().trim();
        String email   = emailField.getText().trim();
        String phone   = phoneField.getText().trim();
        String adresse = adresseField.getText().trim();
        String sDate   = startField.getText().trim();
        String eDate   = endField.getText().trim();

        boolean valid = true;

        // Vérifie les champs vides
        for (JTextField f : new JTextField[]{
                nameField, prenomField, emailField,
                phoneField, adresseField, startField, endField
        }) {
            if (f.getText().trim().isEmpty()) {
                valid = false;
                f.setBackground(Color.PINK);
            } else {
                f.setBackground(new Color(33, 33, 33));
            }
        }

        // Vérifie le format du numéro de téléphone
        if (!phone.matches("\\d{10}")) {
            valid = false;
            phoneField.setBackground(Color.PINK);
            JOptionPane.showMessageDialog(
                    null,
                    "Le numéro de téléphone doit contenir exactement 10 chiffres.",
                    "Numéro invalide",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        // Vérifie les dates
        LocalDate dateDebut = null;
        LocalDate dateFin   = null;
        try {
            dateDebut = LocalDate.parse(sDate, FORMATTER);
            dateFin   = LocalDate.parse(eDate, FORMATTER);
        } catch (DateTimeParseException ex) {
            valid = false;
            startField.setBackground(Color.PINK);
            endField.setBackground(Color.PINK);
        }

        if (!valid) {
            JOptionPane.showMessageDialog(
                    null,
                    "Veuillez corriger les champs en rouge.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Création du client et enregistrement
        Client client = new Client(name, prenom, email, phone, adresse, hotel);
        hotel.ajouterClient(client);

        Reservation res = new Reservation(client, chambre, dateDebut, dateFin);
        chambre.ajouterReservation(res);
        client.ajouterReservation(res);

        JOptionPane.showMessageDialog(
                null,
                "Réservation confirmée pour " + name +
                        " du " + dateDebut.format(FORMATTER) +
                        " au " + dateFin.format(FORMATTER),
                "Confirmation",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}