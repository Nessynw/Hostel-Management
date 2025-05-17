package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static Vue.AppColors.FIELD_COLOR;

public class FacturePanel extends JPanel {

    private JTextArea notesArea;
    private JComboBox<String> paiementBox;
    private JTable table;
    private JScrollPane tableScrollPane;
    private JLabel totalLabel;
    String code = genererCode(1, LocalDate.now());
    LocalDate date = LocalDate.now();

    public FacturePanel() {
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Section Séjour et Date
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.setOpaque(false);

        JComboBox<String> sejourBox = new JComboBox<>(new String[]{"Sélectionner un séjour"});

        JFormattedTextField dateField = new JFormattedTextField(date);
        topPanel.add(sejourBox);
        topPanel.add(dateField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(topPanel, gbc);

        // Facture header
        JLabel factureLabel = label("code facture" + code, JLabel.LEFT, 16);
        gbc.gridy++;
        mainPanel.add(factureLabel, gbc);

        JLabel hotelInfo = label("<html>Hôtel BLue castle<br/>123 Avenue des Hôtels<br/>75008 Paris, France</html>", JLabel.LEFT, 12);
        gbc.gridy++;
        mainPanel.add(hotelInfo, gbc);

        JLabel clientLabel = label("<html><br/>Facturé à :<br/><b>Dupont Jean</b><br/>15 rue de Paris, 75001 Paris<br/>jean.dupont@email.com</html>", JLabel.LEFT, 12);
        gbc.gridy++;
        mainPanel.add(clientLabel, gbc);

        // Table produits
        String[] columns = {"Description", "Quantité", "Prix unitaire", "Total"};
        Object[][] data = {
                {"Chambre 101 - Simple", 3, "80€", "240€"},
                {"Petit-déjeuner", 3, "12€", "36€"},
                {"Parking", 3, "15€", "45€"},
                {"Mini bar - Eau minérale", 2, "3€", "6€"},
                {"Mini bar - Bière", 1, "6€", "6€"},
        };

        table = new JTable(data, columns);

        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        tableScrollPane = new JScrollPane(table);

        tableScrollPane.setPreferredSize(new Dimension(450, 100));
        tableScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        gbc.gridy++;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tableScrollPane, gbc);

        // Totaux
        JPanel totalPanel = new JPanel(new GridLayout(3, 2));
        totalPanel.setOpaque(false);
        totalPanel.add(new JLabel());
        totalPanel.add(new JLabel());
        totalPanel.add(label("Sous-total:", JLabel.RIGHT, 12));
        totalPanel.add(label("333€", JLabel.LEFT, 12));
        totalPanel.add(label("TVA (10%):", JLabel.RIGHT, 12));
        totalPanel.add(label("33.3€", JLabel.LEFT, 12));
        totalPanel.add(label("Total:", JLabel.RIGHT, 12));
        totalLabel = label("366.3€", JLabel.LEFT, 12);
        totalPanel.add(totalLabel);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(totalPanel, gbc);

        // Méthode de paiement
        JPanel paymentPanel = new JPanel(new GridLayout(2, 1));
        paymentPanel.setOpaque(false);

        JLabel paiementLabel = label("Méthode de paiement", JLabel.LEFT, 12);
        paiementBox = new JComboBox<>(new String[]{"Carte bancaire", "Espèces", "Chèque"});

        paymentPanel.add(paiementLabel);
        paymentPanel.add(paiementBox);

        gbc.gridy++;
        mainPanel.add(paymentPanel, gbc);

        // Notes
        JPanel notesPanel = new JPanel(new BorderLayout());
        notesPanel.setOpaque(false);
        JLabel notesLabel = label("Notes", JLabel.LEFT, 12);
        notesArea = new JTextArea("Notes additionnelles pour la facture...");
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setBackground(AppColors.Box_Color);
        notesArea.setForeground(AppColors.TEXT_COLOR);
        notesArea.setCaretColor(AppColors.TEXT_COLOR);
        notesArea.setPreferredSize(new Dimension(200, 30));
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesPanel.add(notesLabel, BorderLayout.NORTH);
        notesPanel.add(notesScroll, BorderLayout.CENTER);
        notesPanel.setPreferredSize(new Dimension(400, 80));

        gbc.gridy++;
        mainPanel.add(notesPanel, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton validerButton = new JButton("Valider");
        JButton pdfButton = new JButton("Envoyer par mail");

        validerButton.addActionListener(e -> afficherFacture());
        pdfButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "La facture a bien été envoyé avec succès!"));

        buttonPanel.add(validerButton);
        buttonPanel.add(pdfButton);

        gbc.gridy++;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JLabel label(String text, int align, int size) {
        JLabel label = new JLabel(text, align);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, size));
        return label;
    }

    private void afficherFacture() {
        String codeFacture = code;
        String montant = totalLabel.getText();
        String methode = (String) paiementBox.getSelectedItem();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String note = notesArea.getText();

        String message = String.format("""
                Facture %s validée avec succès.
                Montant total : %s
                Méthode de paiement : %s
                Date de règlement : %s
                
                Note du client :
                %s
                """, codeFacture, montant, methode, date, note);

        JOptionPane.showMessageDialog(this, message, "Facture validée", JOptionPane.INFORMATION_MESSAGE);
    }


    public static String genererCode(int idClient, LocalDate dateReglement) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM_dd");
        String datePart = dateReglement.format(formatter);
        return "#" + idClient + "_" + datePart;
    }



}
