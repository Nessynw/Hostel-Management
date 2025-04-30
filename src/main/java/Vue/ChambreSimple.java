package Vue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import javax.swing.table.DefaultTableCellRenderer;

public class ChambreSimple extends JFrame {
    private static final Color backgroundColor = new Color(18, 11, 61); // Couleur du fond
    private static final Color libreColor = new Color(67, 119, 67, 166); // Vert
    private static final Color occupeeColor = new Color(140, 61, 61, 173); // Rouge

    private final JTable chamberTable;

    public ChambreSimple() {
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

        // Données de la table
        String[] columns = {"Num", "Étage", "Prix", "État"};
        Object[][] data = {
                {101, 1, "109€", "Libre"},
                {102, 1, "127€", "Occupée"},
                {103, 1, "81€", "Occupée"},
                {104, 1, "167€", "Libre"},
                {105, 2, "131€", "Occupée"},
                {106, 2, "141€", "Occupée"},
                {107, 2, "182€", "Libre"},
                {108, 2, "158€", "Occupée"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêcher l'édition directe
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

        // Centrer le texte
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < chamberTable.getColumnCount(); i++) {
            chamberTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Couleur pour l'état
        chamberTable.getColumnModel().getColumn(3).setCellRenderer(new EtatCellRenderer());

        JScrollPane scrollPane = new JScrollPane(chamberTable);
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(scrollPane, BorderLayout.CENTER);

        // Bouton pour changer la disponibilité
        JButton changeEtatButton = new JButton("Changer Disponibilité");
        changeEtatButton.setBackground(new Color(46, 59, 142));
        changeEtatButton.setForeground(Color.WHITE);
        changeEtatButton.setFocusPainted(false);
        changeEtatButton.setFont(new Font("Arial", Font.BOLD, 14));
        changeEtatButton.addActionListener(e -> changerDisponibilite());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(changeEtatButton);

        add(buttonPanel, BorderLayout.SOUTH);
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

    // Classe pour colorer la colonne État
    private static class EtatCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String etat = (String) value;
            if ("Libre".equals(etat)) {
                c.setForeground(Color.WHITE);
                c.setBackground(libreColor); // Vert semi-transparent
            } else {
                c.setForeground(Color.WHITE);
                c.setBackground(occupeeColor); // Rouge semi-transparent
            }
            setHorizontalAlignment(SwingConstants.CENTER);
            return c;
        }
    }
}


