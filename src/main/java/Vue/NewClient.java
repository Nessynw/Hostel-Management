package Vue;

import Controler.ButtonEnregistrement;
import Model.Hotel;
import Model.Chambre;
import Model.Client;  // Assure-toi que cette classe existe et a les getters nécessaires
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class NewClient extends JPanel {
    private Hotel hotel;
    private Chambre chambre;
    private LocalDate debut, fin;

    private JComboBox<Client> anciensClientsCombo;
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


        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Clients existants :"), gbc);

        List<Client> anciensClients = hotel.getListClient();  // Récupérer liste clients
        anciensClientsCombo = new JComboBox<>(new Vector<>(anciensClients));
        anciensClientsCombo.insertItemAt(null, 0);  // Permet "aucun client"
        anciensClientsCombo.setSelectedIndex(0);
        gbc.gridx = 1;
        add(anciensClientsCombo, gbc);


        anciensClientsCombo.addActionListener(e -> {
            Client selected = (Client) anciensClientsCombo.getSelectedItem();
            if (selected != null) {
                nomField.setText(selected.getNom());
                prenomField.setText(selected.getPrenom());
                emailField.setText(selected.getEmail());
                phoneField.setText(selected.getTel());
                adresseField.setText(selected.getAdresse());
            } else {
                nomField.setText("");
                prenomField.setText("");
                emailField.setText("");
                phoneField.setText("");
                adresseField.setText("");
            }
        });

        row++;


        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Date début :"), gbc);
        debutField = new JTextField(20);
        debutField.setText(debut.format(ButtonEnregistrement.FORMATTER));
        debutField.setEditable(false);
        gbc.gridx = 1;
        add(debutField, gbc);


        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Date fin :"), gbc);
        finField = new JTextField(20);
        finField.setText(fin.format(ButtonEnregistrement.FORMATTER));
        finField.setEditable(false);
        gbc.gridx = 1;
        add(finField, gbc);


        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Nom :"), gbc);
        nomField = new JTextField(20);
        gbc.gridx = 1;
        add(nomField, gbc);


        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Prénom :"), gbc);
        prenomField = new JTextField(20);
        gbc.gridx = 1;
        add(prenomField, gbc);


        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Email :"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);


        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Téléphone :"), gbc);
        phoneField = new JTextField(20);
        gbc.gridx = 1;
        add(phoneField, gbc);


        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Adresse :"), gbc);
        adresseField = new JTextField(20);
        gbc.gridx = 1;
        add(adresseField, gbc);


        row++;
        JButton valider = new JButton("Valider");
        ButtonEnregistrement controller = new ButtonEnregistrement(
                hotel, nomField, prenomField, emailField,
                phoneField, adresseField, debutField, finField, chambre ,this
        );
        valider.addActionListener(controller);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        add(valider, gbc);
    }
    public Client getSelectedClient() {
        return (Client) anciensClientsCombo.getSelectedItem();
    }
}
