package Vue;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ListClient extends JPanel {

    private JTable clientTable;
    private DefaultTableModel tableModel;

    // Données fictives pour la table
    private Object[][] data = {{"1", "Dupont", "Jean"}, {"2", "Martin", "Sophie"}, {"3", "Bernard", "Michel"}, {"4", "Petit", "Claire"}, {"5", "Robert", "Thomas"}, {"6", "Richard", "Emma"}, {"7", "Durand", "Lucas"}, {"8", "Moreau", "Camille"}, {"9", "Simon", "Julie"}, {"10", "Laurent", "Antoine"}, {"11", "Michel", "Léa"}, {"12", "Lefebvre", "Hugo"}, {"13", "Leroy", "Chloé"}
    };

    public ListClient() {
        setLayout(new BorderLayout());

        // Création du modèle de table
        String[] columnNames = {"ID", "Nom", "Prénom"};

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Cells are not editable
            }
        };

        // Création de la table
        clientTable = new JTable(tableModel);
        clientTable.setRowHeight(30);
        clientTable.setShowGrid(false);
        clientTable.setIntercellSpacing(new Dimension(0, 0));
        clientTable.setBackground(new Color(100, 120, 200, 150));
        clientTable.setForeground(Color.WHITE);
        clientTable.setFont(new Font("Arial", Font.PLAIN, 14));
        clientTable.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        clientTable.getTableHeader().setBackground(new Color(80, 100, 180, 150));
        clientTable.getTableHeader().setForeground(Color.WHITE);

        // Ajout de la table à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(clientTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Création d'un panneau pour centrer la table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50)); // Adjust padding
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        // Ajouter le panneau contenant la table au panel principal
        add(tableContainer, BorderLayout.CENTER);
    }

    // Get content of the ListClient panel for integration into main panel
    public JPanel getListPanel() {
        return this;
    }
}
