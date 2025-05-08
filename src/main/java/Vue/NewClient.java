package Vue;

import Model.*;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;

public class NewClient extends JPanel {
    private static final Color fieldColor = new Color(33, 33, 33);  // Couleur de fond des champs

    // Matrice pour stocker les clients (chaque ligne représente un client)
    public static Object[][] clientMatrix = new Object[10][7]; // 10 clients max (peut être modifié)
    private int clientCount = 0; // Compteur pour suivre le nombre de clients enregistrés

    public NewClient(Hotel hotel) {
        setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour un meilleur contrôle
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espacement entre les composants
        this.setBackground(AppColors.MAIN_COLOR);

        // Définir les couleurs et la police
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // Titre du formulaire
        JLabel titleLabel = new JLabel("Nouveau Client");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Champ pour le nom
        JLabel nameLabel = new JLabel("Nom:");
        JTextField nameField = new JTextField(20);
        nameField.setBackground(fieldColor);
        nameField.setForeground(Color.WHITE);
        nameField.setFont(fieldFont);
        nameLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Champ pour le prénom
        JLabel prenomLabel = new JLabel("Prénom:");
        JTextField prenomField = new JTextField(20);
        prenomField.setBackground(fieldColor);
        prenomField.setForeground(Color.WHITE);
        prenomField.setFont(fieldFont);
        prenomLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(prenomLabel, gbc);
        gbc.gridx = 1;
        add(prenomField, gbc);

        // Champ pour l'email
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        emailField.setBackground(fieldColor);
        emailField.setForeground(Color.WHITE);
        emailField.setFont(fieldFont);
        emailLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Champ pour le numéro de téléphone
        JLabel phoneLabel = new JLabel("Numéro de téléphone:");
        JTextField phoneField = new JTextField(20);
        phoneField.setBackground(fieldColor);
        phoneField.setForeground(Color.WHITE);
        phoneField.setFont(fieldFont);
        phoneLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(phoneLabel, gbc);
        gbc.gridx = 1;
        add(phoneField, gbc);

        // Champ pour l'adresse
        JLabel addressLabel = new JLabel("Adresse:");
        JTextField addressField = new JTextField(20);
        addressField.setBackground(fieldColor);
        addressField.setForeground(Color.WHITE);
        addressField.setFont(fieldFont);
        addressLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(addressLabel, gbc);
        gbc.gridx = 1;
        add(addressField, gbc);

        // Champ pour la date de début
        JLabel startDateLabel = new JLabel("Date de début:");
        JTextField startDateField = new JTextField(20);
        startDateField.setBackground(fieldColor);
        startDateField.setForeground(Color.WHITE);
        startDateField.setFont(fieldFont);
        startDateLabel.setForeground(Color.WHITE);
        JButton startDateButton = createCalendarButton(startDateField);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(startDateLabel, gbc);
        gbc.gridx = 1;
        add(startDateField, gbc);
        gbc.gridx = 2;
        add(startDateButton, gbc);

        startDateField.setEditable(false);

        // Champ pour la date de fin
        JLabel endDateLabel = new JLabel("Date de fin:");
        JTextField endDateField = new JTextField(20);
        endDateField.setBackground(fieldColor);
        endDateField.setForeground(Color.WHITE);
        endDateField.setFont(fieldFont);
        endDateLabel.setForeground(Color.WHITE);

        endDateField.setEditable(false);
        JButton endDateButton = createCalendarButton(endDateField);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(endDateLabel, gbc);
        gbc.gridx = 1;
        add(endDateField, gbc);
        gbc.gridx = 2;
        add(endDateButton, gbc);

        // Bouton d'enregistrement
        JButton submitButton = new JButton("Enregistrer");
        submitButton.setBackground(new Color(58, 51, 124));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setPreferredSize(new Dimension(150, 40));

        submitButton.addActionListener(e -> {
            // Récupérer les données des champs
            String name = nameField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            LocalDate startDate = parseLocalDate(startDateField.getText());
            LocalDate endDate = parseLocalDate(endDateField.getText());

            // Vérifier si la matrice est pleine et augmenter la taille si nécessaire
            if (clientCount >= clientMatrix.length) {
                clientMatrix = expandMatrix(clientMatrix);
            }

            // Ajouter un ID auto-incrémenté
            int clientId = clientCount + 1; // ID auto-incrémenté

            // Ajouter les données du client dans la matrice
            clientMatrix[clientCount][0] = clientId; // ID
            clientMatrix[clientCount][1] = name;
            clientMatrix[clientCount][2] = prenom;
            clientMatrix[clientCount][3] = email;
            clientMatrix[clientCount][4] = phone;
            clientMatrix[clientCount][5] = address;
            clientMatrix[clientCount][6] = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            clientMatrix[clientCount][7] = endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Augmenter le nombre de clients
            clientCount++;

            // Afficher un message de confirmation
            JOptionPane.showMessageDialog(this, "Client enregistré avec succès.");
        });

        // Placer le bouton d'enregistrement en bas
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(submitButton, gbc);
    }

    // Méthode pour créer un bouton qui ouvre un calendrier
    private JButton createCalendarButton(JTextField dateField) {
        JButton calendarButton = new JButton("C");
        calendarButton.setBackground(new Color(58, 51, 124));
        calendarButton.setForeground(Color.WHITE);
        calendarButton.setFont(new Font("Arial", Font.BOLD, 16));
        calendarButton.setPreferredSize(new Dimension(40, 40));
        calendarButton.setBorderPainted(false);

        calendarButton.addActionListener(e -> {
            // Créer et afficher le calendrier dans une nouvelle fenêtre
            JCalendar calendar = new JCalendar();
            int option = JOptionPane.showConfirmDialog(null, calendar, "Sélectionner une date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            // Si l'utilisateur valide une date, la mettre dans le champ
            if (option == JOptionPane.OK_OPTION) {
                Date selectedDate = calendar.getDate();
                String formattedDate = formatDate(selectedDate);
                dateField.setText(formattedDate);  // Insère la date formatée dans le champ texte
            }
        });
        return calendarButton;
    }

    // Méthode pour formater la date au format dd/MM/yyyy
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    // Méthode pour analyser une chaîne de caractères en date
    private LocalDate parseLocalDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    // Méthode pour agrandir la matrice si elle est pleine
    private Object[][] expandMatrix(Object[][] originalMatrix) {
        Object[][] expandedMatrix = new Object[originalMatrix.length + 10][8]; // Ajouter 10 lignes et 1 colonne pour l'ID
        System.arraycopy(originalMatrix, 0, expandedMatrix, 0, originalMatrix.length);
        return expandedMatrix;
    }
}
