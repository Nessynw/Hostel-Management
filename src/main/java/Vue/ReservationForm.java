package Vue;

import Model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Vector;

public class ReservationForm extends JPanel {
    private Hotel hotel;
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JComboBox<String> searchCriteria;
    public ReservationForm(Hotel hotel) {
        this.hotel = hotel;
        this.setBackground(AppColors.MAIN_COLOR);
        this.setLayout(new BorderLayout(20, 20));

        // Panel pour le titre et la recherche
        JPanel topPanel = new JPanel(new BorderLayout(20, 10));
        topPanel.setBackground(AppColors.MAIN_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Titre
        JLabel titre = new JLabel("Liste des Réservations", SwingConstants.CENTER);
        titre.setFont(new Font("Serif", Font.BOLD, 24));
        titre.setForeground(Color.WHITE);
        topPanel.add(titre, BorderLayout.WEST);

        // Panel de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(AppColors.MAIN_COLOR);

        // Créer le menu déroulant pour les critères de recherche
        String[] criteria = {"Numéro", "Étage", "Type"};
        JComboBox<String> searchCriteria = new JComboBox<>(criteria);
        searchCriteria.setPreferredSize(new Dimension(100, 30));

        // Créer le champ de recherche
        searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 30));

        // Bouton de recherche
        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(40, 30));
        searchButton.addActionListener(e -> filterTable());

        JLabel searchLabel = new JLabel("Rechercher par: ");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 10));

        // Ajouter les composants au panel de recherche
        searchPanel.add(searchLabel);
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        topPanel.add(searchPanel, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);
        // Configuration de la table
        String[] colonnes = {"ID", "Client", "Chambre", "Début", "Fin", "Actions"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Seule la colonne Actions est éditable
            }
        };

        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Style du header
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(AppColors.MAIN_COLOR);
        header.setForeground(AppColors.MAIN_COLOR);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Style de la table
        table.setBackground(AppColors.MAIN_COLOR);
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(70, 70, 70));
        table.setRowHeight(40);

        TableColumn actionColumn = table.getColumnModel().getColumn(5);
        actionColumn.setCellRenderer(new ButtonsRenderer());
        actionColumn.setCellEditor(new ButtonsEditor(table));
        actionColumn.setPreferredWidth(200);

        // Remplir la table
        remplirTable();

        // ScrollPane avec marges
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(AppColors.MAIN_COLOR);
        scrollPane.getViewport().setBackground(AppColors.MAIN_COLOR);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(AppColors.MAIN_COLOR);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(wrapperPanel, BorderLayout.CENTER);

        // la recherche en temps réel
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTable();
            }
        });
    }

    class ButtonsRenderer implements TableCellRenderer {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;

        public ButtonsRenderer() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setBackground(AppColors.MAIN_COLOR);

            editButton = new JButton("Modifier");
            editButton.setBackground(AppColors.BUTTON_COLOR);
            editButton.setForeground(Color.WHITE);

            deleteButton = new JButton("Supprimer");
            deleteButton.setBackground(AppColors.ERROR_COLOR);
            deleteButton.setForeground(Color.WHITE);

            panel.add(editButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return panel;
        }
    }

    class ButtonsEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private String[] reservationData;
        private JTable table;

        public ButtonsEditor(JTable table) {
            super(new JCheckBox());
            this.table = table;
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setBackground(AppColors.MAIN_COLOR);

            editButton = new JButton("Modifier");
            editButton.setBackground(AppColors.BUTTON_COLOR);
            editButton.setForeground(Color.WHITE);

            deleteButton = new JButton("Supprimer");
            deleteButton.setBackground(AppColors.ERROR_COLOR);
            deleteButton.setForeground(Color.WHITE);

            editButton.addActionListener(e -> editReservation());
            deleteButton.addActionListener(e -> deleteReservation());

            panel.add(editButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            reservationData = new String[5];
            for (int i = 0; i < 5; i++) {
                reservationData[i] = table.getValueAt(row, i).toString();
            }
            return panel;
        }

        private void editReservation() {
            Reservation r = findReservationById(Integer.parseInt(reservationData[0]));
            if (r != null) {
                openEditDialog(r);
            }
            fireEditingStopped();
        }

        private void deleteReservation() {
            Reservation r = findReservationById(Integer.parseInt(reservationData[0]));
            if (r != null) {
                handleDelete(r);
            }
            fireEditingStopped();
        }
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
        tableModel.setRowCount(0);
        Vector<Reservation> reservations = collectReservations();
        for (Reservation r : reservations) {
            Object[] row = {
                r.getId_res(),
                r.getClient().getNom() + " " + r.getClient().getPrenom(),
                r.getChambre().getNum_chambre(),
                FORMATTER.format(r.getDate_deb()),
                FORMATTER.format(r.getDate_fin()),
                ""  // Colonne pour les boutons
            };
            tableModel.addRow(row);
        }
    }

    private Vector<Reservation> collectReservations() {
        Vector<Reservation> all = new Vector<>();
        for (Client c : hotel.getListClient()) {
            if (c.getListReservation() != null) {
                all.addAll(c.getListReservation());
            }
        }
        return all;
    }

    private Reservation findReservationById(int id) {
        for (Client c : hotel.getListClient()) {
            if (c.getListReservation() != null) {
                for (Reservation r : c.getListReservation()) {
                    if (r.getId_res() == id) {
                        return r;
                    }
                }
            }
        }
        return null;
    }

    private void openEditDialog(Reservation r) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier Réservation", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Date début (jj/MM/aaaa):"));
        JTextField debutField = new JTextField(FORMATTER.format(r.getDate_deb()));
        panel.add(debutField);

        panel.add(new JLabel("Date fin (jj/MM/aaaa):"));
        JTextField finField = new JTextField(FORMATTER.format(r.getDate_fin()));
        panel.add(finField);

        JButton saveBtn = new JButton("Enregistrer");
        saveBtn.addActionListener(e -> {
            try {
                r.setDate_deb(LocalDate.parse(debutField.getText(), FORMATTER));
                r.setDate_fin(LocalDate.parse(finField.getText(), FORMATTER));
                dialog.dispose();
                remplirTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Format de date invalide.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel());
        panel.add(saveBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void handleDelete(Reservation r) {
        UIManager.put("OptionPane.yesButtonText", "Oui");
        UIManager.put("OptionPane.noButtonText", "Non");

        int option = JOptionPane.showConfirmDialog(
                SwingUtilities.getWindowAncestor(this),
                "Êtes-vous sûr de vouloir supprimer cette réservation ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            r.getChambre().supprimerReservation(r);
            r.getClient().annulerReservation(r);
            remplirTable();
        }
    }

}