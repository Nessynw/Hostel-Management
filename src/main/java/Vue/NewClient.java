package Vue;

import Controler.ButtonEnregistrement;
import Model.Hotel;
import Model.Chambre;
import java.awt.*;
import javax.swing.*;
import java.time.LocalDate;

public class NewClient extends JPanel {
    private Hotel hotel;
    private Chambre chambre;
    private LocalDate debut, fin;

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField adresseField;
    private JTextField debutField;
    private JTextField finField;

    public NewClient(Hotel hotel, Chambre chambre, LocalDate debut, LocalDate fin) {
        this.hotel = hotel;
        this.chambre = chambre;
        this.debut = debut;
        this.fin = fin;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        // Date début
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Date début :"), gbc);
        debutField = new JTextField(20);
        debutField.setText(debut.format(ButtonEnregistrement.FORMATTER));
        debutField.setEditable(false);
        gbc.gridx = 1;
        add(debutField, gbc);

        // Date fin
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Date fin :"), gbc);
        finField = new JTextField(20);
        finField.setText(fin.format(ButtonEnregistrement.FORMATTER));
        finField.setEditable(false);
        gbc.gridx = 1;
        add(finField, gbc);

        // Nom
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Nom :"), gbc);
        nomField = new JTextField(20);
        gbc.gridx = 1;
        add(nomField, gbc);

        // Prénom
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Prénom :"), gbc);
        prenomField = new JTextField(20);
        gbc.gridx = 1;
        add(prenomField, gbc);

        // Email
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Email :"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Téléphone
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Téléphone :"), gbc);
        phoneField = new JTextField(20);
        gbc.gridx = 1;
        add(phoneField, gbc);

        // Adresse
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Adresse :"), gbc);
        adresseField = new JTextField(20);
        gbc.gridx = 1;
        add(adresseField, gbc);

        // Bouton Valider
        row++;
        JButton valider = new JButton("Valider");
        // Utilise ButtonEnregistrement pour gérer l'enregistrement
        ButtonEnregistrement controller = new ButtonEnregistrement(
                hotel, nomField, prenomField, emailField,
                phoneField, adresseField, debutField, finField, chambre
        );
        valider.addActionListener(controller);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        add(valider, gbc);
    }
}
