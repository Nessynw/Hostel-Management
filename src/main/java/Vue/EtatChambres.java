package Vue;

import Model.Hotel;
import Model.Chambre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class EtatChambres extends JPanel {
    private Hotel hotel;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;
    private JComboBox<String> searchCriteria;

    public EtatChambres(Hotel hotel) {
        this.hotel = hotel;
        this.setBackground(AppColors.MAIN_COLOR);
        this.setLayout(new BorderLayout(20, 20));

        // Panel pour le titre et la recherche
        JPanel topPanel = new JPanel(new BorderLayout(20, 10));
        topPanel.setBackground(AppColors.MAIN_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Créer le titre
        JLabel titre = new JLabel("État des Chambres", SwingConstants.CENTER);
        titre.setFont(new Font("Serif", Font.BOLD, 24));
        titre.setForeground(Color.WHITE);
        topPanel.add(titre, BorderLayout.WEST);

        // Panel de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(AppColors.MAIN_COLOR);

        // Créer le menu déroulant pour les critères de recherche
        String[] criteria = {"Numéro", "Étage", "Type"};
        searchCriteria = new JComboBox<>(criteria);
        searchCriteria.setPreferredSize(new Dimension(100, 30));

        // Créer le champ de recherche
        searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 30));

        // Bouton de recherche
        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(40, 30));
        searchButton.addActionListener(e -> filterTable());

        // Dans le constructeur de EtatChambres, modifiez la création du JLabel
        JLabel searchLabel = new JLabel("Rechercher par: ");
        searchLabel.setForeground(Color.WHITE); // Met le texte en blanc
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Change la police et la taille

        // Ajouter les composants au panel de recherche
        searchPanel.add(searchLabel);
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        topPanel.add(searchPanel, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);

        // Créer le modèle de table
        String[] colonnes = {"Numéro", "Étage", "Type", "Prix", "Disponibilité"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Personnaliser l'apparence de la table
        table.setBackground(AppColors.MAIN_COLOR);
        table.setForeground(Color.WHITE);
        table.setGridColor(AppColors.MAIN_COLOR);
        table.getTableHeader().setBackground(AppColors.MAIN_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(30);
        
        // Remplir la table
        remplirTable();

        // Créer le scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(AppColors.MAIN_COLOR);
        scrollPane.getViewport().setBackground(AppColors.MAIN_COLOR);
        
        // Ajouter des marges
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(AppColors.MAIN_COLOR);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(wrapperPanel, BorderLayout.CENTER);

        // Ajouter un écouteur pour la recherche en temps réel
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTable();
            }
        });
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        String selectedCriteria = (String) searchCriteria.getSelectedItem();
        
        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            int columnIndex;
            switch (selectedCriteria) {
                case "Numéro":
                    columnIndex = 0;
                    break;
                case "Étage":
                    columnIndex = 1;
                    break;
                case "Type":
                    columnIndex = 2;
                    break;
                default:
                    columnIndex = 0;
            }
            
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, columnIndex));
        }
    }

    private void remplirTable() {
        for (Chambre chambre : hotel.getChambres()) {
            Object[] row = {
                chambre.getNumero(),
                chambre.getNum_etage(),
                chambre.getType(),
                chambre.getPrix() + " €",
                chambre.isEstOccupee() ? "Occupée" : "Disponible"
            };
            tableModel.addRow(row);
        }
    }
}