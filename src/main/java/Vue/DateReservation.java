package Vue;

import com.toedter.calendar.JCalendar;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.ZoneId;
import java.util.Date;

public class DateReservation extends JFrame {
    private Hotel hotel;
    private String chambreType;

    private JTextField debutField;
    private JTextField finField;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Color COULEUR_FOND = new Color(18, 11, 61);
    private static final Color COULEUR_TEXTE = Color.WHITE;
    private static final Color COULEUR_CHAMP = new Color(33, 33, 33);

    public DateReservation(Hotel hotel, String chambreType) {
        this.hotel = hotel;
        this.chambreType = chambreType;

        setTitle("Réservation de chambre");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COULEUR_FOND);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titre = new JLabel("Réservation d'une chambre");
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setForeground(COULEUR_TEXTE);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        add(titre, gbc);

        debutField = ajouterChampDate("Date de début:", 1, gbc);
        finField = ajouterChampDate("Date de fin:", 2, gbc);

        JPanel boutons = new JPanel();
        boutons.setBackground(COULEUR_FOND);

        JButton precedent = creerBouton("Précédent", e -> dispose());
        JButton suivant = creerBouton("Suivant", e -> validerDates());
        boutons.add(precedent);
        boutons.add(suivant);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        add(boutons, gbc);
    }
  public  String  getDateDebut()
    {



        return debutField.getText() ;
    }

    private JTextField ajouterChampDate(String labelTexte, int ligne, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelTexte);
        label.setForeground(COULEUR_TEXTE);

        JTextField champ = new JTextField(20);
        champ.setBackground(COULEUR_CHAMP);
        champ.setForeground(COULEUR_TEXTE);
        champ.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton calendrier = creerBoutonCalendrier(champ);

        gbc.gridwidth = 1;
        gbc.gridy = ligne;
        gbc.gridx = 0; add(label, gbc);
        gbc.gridx = 1; add(champ, gbc);
        gbc.gridx = 2; add(calendrier, gbc);

        return champ;
    }

    private JButton creerBouton(String texte, ActionListener action) {
        JButton bouton = new JButton(texte);
        bouton.setBackground(new Color(58, 51, 124));
        bouton.setForeground(COULEUR_TEXTE);
        bouton.setFont(new Font("Arial", Font.BOLD, 16));
        bouton.setPreferredSize(new Dimension(150, 40));
        bouton.addActionListener(action);
        return bouton;
    }

    private JButton creerBoutonCalendrier(JTextField champ) {
        JButton bouton = new JButton("C");
        bouton.setBackground(new Color(58, 51, 124));
        bouton.setForeground(COULEUR_TEXTE);
        bouton.setFont(new Font("Arial", Font.BOLD, 16));
        bouton.setPreferredSize(new Dimension(40, 40));
        bouton.setBorderPainted(false);
        bouton.addActionListener(e -> {
            JCalendar cal = new JCalendar();
            int opt = JOptionPane.showConfirmDialog(this, cal, "Choisir une date", JOptionPane.OK_CANCEL_OPTION);
            if (opt == JOptionPane.OK_OPTION) {
                Date date = cal.getDate();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                champ.setText(FORMATTER.format(localDate));
            }
        });
        return bouton;
    }

    private void validerDates() {
        String debutText = debutField.getText();
        String finText = finField.getText();

        try {
            if (debutText.isEmpty() || finText.isEmpty()) {
                throw new IllegalArgumentException("Tous les champs doivent être remplis.");
            }

            LocalDate debut = LocalDate.parse(debutText, FORMATTER);
            LocalDate fin = LocalDate.parse(finText, FORMATTER);
            LocalDate aujourdHui = LocalDate.now();

            if (debut.isBefore(aujourdHui)) {
                throw new IllegalArgumentException("La date de début doit être aujourd'hui ou après.");
            }
            if (fin.isBefore(debut)) {
                throw new IllegalArgumentException("La date de fin ne peut pas être avant la date de début.");
            }

            if ("Chambre Simple".equals(chambreType)) {
                new ChambreSmp(hotel, debut, fin).setVisible(true);
            } else {
                new ChambresDouble(hotel, debut, fin).setVisible(true);
            }
            dispose();

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez jj/mm/aaaa.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
