package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Vector;

import Model.*;

public class FacturePanel extends JPanel {

    private JTextArea notesArea;
    private JComboBox<Sejour> sejourBox;
    private JComboBox<String> paiementBox;
    private JTable table;
    private JScrollPane tableScrollPane;
    private JLabel totalLabel;
    private Hotel hotel;
    private Sejour sejourActuel;

    public FacturePanel(Hotel hotel) {
        this.hotel = hotel;

        setLayout(new BorderLayout());
        setBackground(new Color(27, 25, 53));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Section Séjour
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.setOpaque(false);

        sejourBox = new JComboBox<>(new Vector<>(hotel.getListSejour()));
        sejourBox.insertItemAt(null, 0);
        sejourBox.setSelectedIndex(0);
        sejourBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Sejour s) {
                    setText("#" + s.getRes().getId_res() + " - Chambre " + s.getChambre().getNumero());
                } else {
                    setText("Sélectionner un séjour");
                }
                return this;
            }
        });

        JFormattedTextField dateField = new JFormattedTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        topPanel.add(sejourBox);
        topPanel.add(dateField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(topPanel, gbc);

        // Facture Header
        JLabel factureLabel = label("Facture #INV-2023-XXXX", JLabel.LEFT, 16);
        gbc.gridy++;
        mainPanel.add(factureLabel, gbc);

        JLabel hotelInfo = label("<html><br/>123 Avenue des Hôtels<br/>75008 Paris, France</html>", JLabel.LEFT, 12);
        gbc.gridy++;
        mainPanel.add(hotelInfo, gbc);

        JLabel clientLabel = label("", JLabel.LEFT, 12);
        gbc.gridy++;
        mainPanel.add(clientLabel, gbc);

        // Table
        String[] columns = {"Description", "Quantité", "Prix unitaire", "Total"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(450, 100));

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tableScrollPane, gbc);

        // Totaux
        JPanel totalPanel = new JPanel(new GridLayout(3, 2));
        totalPanel.setOpaque(false);
        totalPanel.add(new JLabel());
        totalPanel.add(new JLabel());
        totalPanel.add(label("Sous-total:", JLabel.RIGHT, 12));
        JLabel sousTotalLabel = label("0.00€", JLabel.LEFT, 12);
        totalPanel.add(sousTotalLabel);
        totalPanel.add(label("TVA (10%):", JLabel.RIGHT, 12));
        JLabel tvaLabel = label("0.00€", JLabel.LEFT, 12);
        totalPanel.add(tvaLabel);
        totalPanel.add(label("Total:", JLabel.RIGHT, 12));
        totalLabel = label("0.00€", JLabel.LEFT, 12);
        totalPanel.add(totalLabel);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(totalPanel, gbc);

        // Paiement
        JPanel paymentPanel = new JPanel(new GridLayout(2, 1));
        paymentPanel.setOpaque(false);
        paiementBox = new JComboBox<>(new String[]{"Carte bancaire", "Espèces", "Chèque"});
        paymentPanel.add(label("Méthode de paiement", JLabel.LEFT, 12));
        paymentPanel.add(paiementBox);

        gbc.gridy++;
        mainPanel.add(paymentPanel, gbc);

        // Notes
        JPanel notesPanel = new JPanel(new BorderLayout());
        notesPanel.setOpaque(false);
        notesArea = new JTextArea("Notes additionnelles pour la facture...");
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesPanel.add(label("Notes", JLabel.LEFT, 12), BorderLayout.NORTH);
        notesPanel.add(notesScroll, BorderLayout.CENTER);
        notesPanel.setPreferredSize(new Dimension(400, 80));

        gbc.gridy++;
        mainPanel.add(notesPanel, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton validerButton = new JButton("Valider");
        JButton pdfButton = new JButton("Exporter en PDF");

        validerButton.addActionListener(e -> afficherFacture());
        pdfButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fonction PDF à implémenter."));

        buttonPanel.add(validerButton);
        buttonPanel.add(pdfButton);

        gbc.gridy++;
        mainPanel.add(buttonPanel, gbc);

        // Action changement de séjour
        sejourBox.addActionListener(e -> {
            sejourActuel = (Sejour) sejourBox.getSelectedItem();
            if (sejourActuel != null) {
                remplirTableAvecSejour(sejourActuel);
                factureLabel.setText("Facture #" + "INV-" + sejourActuel.getRes().getId_res());
                clientLabel.setText(String.format(
                        "<html><br/>Facturé à :<br/><b>%s</b><br/>%s<br/>%s</html>",
                        sejourActuel.getRes().getClient().getNom(),
                        sejourActuel.getRes().getClient().getAdresse(),
                        sejourActuel.getRes().getClient().getEmail()
                ));
            }
        });

        add(mainPanel, BorderLayout.CENTER);
    }

    private void remplirTableAvecSejour(Sejour s) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        long nbNuits = ChronoUnit.DAYS.between(s.getDate_deb(), s.getDate_fin());
        double prixChambre = s.getChambre().getPrix();
        model.addRow(new Object[]{
                "Chambre " + s.getChambre().getNumero(), nbNuits, String.format("%.2f€", prixChambre), String.format("%.2f€", prixChambre * nbNuits)
        });

        for (Consommation c : s.getListCons()) {
            Produit p = c.getProduit();
            int qte = c.getQuantite();
            double prix = p.getPrix();
            model.addRow(new Object[]{
                    "Mini bar - " + p.getNom(), qte, String.format("%.2f€", prix), String.format("%.2f€", prix * qte)
            });
        }

        // Calcul total
        double totalHT = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String montantStr = ((String) model.getValueAt(i, 3)).replace("€", "").replace(",", ".");
            totalHT += Double.parseDouble(montantStr);
        }
        double tva = totalHT * 0.10;
        double totalTTC = totalHT + tva;

        totalLabel.setText(String.format("%.2f€", totalTTC));
    }

    private void afficherFacture() {
        if (sejourActuel == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un séjour.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codeFacture = "INV-" + sejourActuel.getRes().getId_res();
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

    private JLabel label(String text, int align, int size) {
        JLabel label = new JLabel(text, align);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, size));
        return label;
    }
}
