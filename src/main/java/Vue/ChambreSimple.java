
package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ChambreSimple extends JFrame {
    private static final Color backgroundColor = new Color(18, 11, 61); // Couleur du fond
    private static final Color libreColor = new Color(67, 119, 67, 166); // Vert
    private static final Color occupeeColor = new Color(140, 61, 61, 173); // Rouge
    private static final Color main_color = new Color(58, 51, 124);  // La couleur principale pour les boutons

    private final JTable chamberTable;

    public ChambreSimple(boolean uniquementLibres) {
        setTitle("Informations des Chambres Simples");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Informations des Chambres", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Colonnes et données brutes
        String[] columns = {"Num", "Étage", "Prix", "État"};
        Object[][] toutesLesChambres = {
                {101, 1, "109€", "Libre"},
                {102, 1, "127€", "Occupée"},
                {103, 1, "81€", "Occupée"},
                {104, 1, "167€", "Libre"},
                {105, 2, "131€", "Occupée"},
                {106, 2, "141€", "Occupée"},
                {107, 2, "182€", "Libre"},
                {108, 2, "158€", "Occupée"}
        };

        // Filtrage si nécessaire
        Object[][] data;
        if (uniquementLibres) {
            data = Arrays.stream(toutesLesChambres)
                    .filter(chambre -> chambre[3].equals("Libre"))
                    .toArray(Object[][]::new);
        } else {
            data = toutesLesChambres;
        }

        // Création du modèle
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        chamberTable = new JTable(model);
        chamberTable.setBackground(backgroundColor);
        chamberTable.setForeground(Color.white);
        chamberTable.setFont(new Font("Arial", Font.PLAIN, 14));
        chamberTable.setRowHeight(30);
        chamberTable.getTableHeader().setBackground(backgroundColor);
        chamberTable.getTableHeader().setForeground(new Color(29, 42, 97, 236));
        chamberTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Centrer le texte dans toutes les cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < chamberTable.getColumnCount(); i++) {
            chamberTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Rendu spécial pour l’état
        chamberTable.getColumnModel().getColumn(3).setCellRenderer(new EtatCellRenderer());

        JScrollPane scrollPane = new JScrollPane(chamberTable);
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Définition des champs de dates
        JTextField startDateField = new JTextField(10);  // Champs pour la date de début
        JTextField endDateField = new JTextField(10);    // Champs pour la date de fin

        // Validation Button
        JButton validateButton = new JButton("Valider");
        validateButton.setBackground(main_color);
        validateButton.setForeground(Color.WHITE);
        validateButton.setFont(new Font("Arial", Font.BOLD, 16));
        validateButton.setPreferredSize(new Dimension(150, 40));
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date_debut = startDateField.getText();
                String date_fin = endDateField.getText();

                if (date_debut.isEmpty() || date_fin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Logique de validation de la réservation
                    JOptionPane.showMessageDialog(null, "Réservation confirmée du " + date_debut + " au " + date_fin, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Ajouter le bouton de validation à un panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(validateButton); // Ajouter le bouton "Valider"

        // Ajouter le panel avec le bouton dans le layout
        add(buttonPanel, BorderLayout.SOUTH);

        // Bouton changer état
        JButton changeEtatButton = new JButton("Changer Disponibilité");
        changeEtatButton.setBackground(new Color(46, 59, 142));
        changeEtatButton.setForeground(Color.WHITE);
        changeEtatButton.setFocusPainted(false);
        changeEtatButton.setFont(new Font("Arial", Font.BOLD, 14));
        changeEtatButton.addActionListener(e -> changerDisponibilite());

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setBackground(backgroundColor);
        buttonPanel2.add(changeEtatButton);
        add(buttonPanel2, BorderLayout.SOUTH);
    }

    private void changerDisponibilite() {
        int selectedRow = chamberTable.getSelectedRow();
        if (selectedRow != -1) {
            String currentEtat = (String) chamberTable.getValueAt(selectedRow, 3);
            if (currentEtat.equals("Libre")) {
                chamberTable.setValueAt("Occupée", selectedRow, 3);
            } else {
                chamberTable.setValueAt("Libre", selectedRow, 3);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une chambre.");
        }
    }

    // Rendu de cellule avec couleurs pour l'état
    private static class EtatCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String etat = (String) value;
            if ("Libre".equals(etat)) {
                c.setForeground(Color.WHITE);
                c.setBackground(libreColor);
            } else {
                c.setForeground(Color.WHITE);
                c.setBackground(occupeeColor);
            }
            setHorizontalAlignment(SwingConstants.CENTER);
            return c;
        }
    }
}
