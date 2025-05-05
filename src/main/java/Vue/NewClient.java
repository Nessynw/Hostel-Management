package Vue;
import Controler.*;
import Model.*;
 // Importation de JCalendar pour les champs de date
import Model.Hotel;
import com.toedter.calendar.JCalendar;
import Controler.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;
public class NewClient extends JPanel {
    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color fieldColor = new Color(33, 33, 33);  // Couleur de fond des champs
    private static final Color errorColor = new Color(255, 102, 102);  // Couleur rouge pour les champs invalides
    private static final Color validColor = fieldColor;  // Couleur de fond valide

    public NewClient(Hotel hotel){
        setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour un meilleur contrôle
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espacement entre les composants
        this.setBackground(main_color);

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
        JLabel adresseLabel = new JLabel("Adresse:");
        JTextField adresseField = new JTextField(20);
        adresseField.setBackground(fieldColor);
        adresseField.setForeground(Color.WHITE);
        adresseField.setFont(fieldFont);
        adresseLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(adresseLabel, gbc);
        gbc.gridx = 1;
        add(adresseField, gbc);

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

        // Champ pour la date de fin
        JLabel endDateLabel = new JLabel("Date de fin:");
        JTextField endDateField = new JTextField(20);
        endDateField.setBackground(fieldColor);
        endDateField.setForeground(Color.WHITE);
        endDateField.setFont(fieldFont);
        endDateLabel.setForeground(Color.WHITE);
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
        ButtonEnregistrement b = new ButtonEnregistrement(hotel, nameField, prenomField, emailField, phoneField, adresseField, startDateField, endDateField);
        submitButton.addActionListener(b);

        // Placer le bouton d'enregistrement en bas
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(submitButton, gbc);
    }

    // Méthode pour créer un bouton qui ouvre un calendrier
    private JButton createCalendarButton(JTextField dateField) {
        JButton calendarButton = new JButton("📅");
        calendarButton.setBackground(new Color(58, 51, 124));
        calendarButton.setForeground(Color.WHITE);
        calendarButton.setFont(new Font("Arial", Font.BOLD, 16));
        calendarButton.setPreferredSize(new Dimension(40, 40));
        calendarButton.setBorderPainted(false);
        calendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Créer et afficher le calendrier dans une nouvelle fenêtre
                JCalendar calendar = new JCalendar();
                int option = JOptionPane.showConfirmDialog(null, calendar, "Sélectionner une date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // Si l'utilisateur valide une date, la mettre dans le champ
                if (option == JOptionPane.OK_OPTION) {
                    Date selectedDate = calendar.getDate();
                    String formattedDate = formatDate(selectedDate);
                    dateField.setText(formattedDate);  // Insère la date formatée dans le champ texte
                }
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
}