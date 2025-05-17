package Vue;

import Model.Hotel;
import Model.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Vector;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.TableColumn;

public class ListClient extends JPanel {
    private Hotel hotel;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;
    private JComboBox<String> searchCriteria;

    public ListClient(Hotel hotel) {
        this.hotel = hotel;
        this.setBackground(AppColors.MAIN_COLOR);
        this.setLayout(new BorderLayout(20, 20));

        // Panel pour le titre et la recherche
        JPanel topPanel = new JPanel(new BorderLayout(20, 10));
        topPanel.setBackground(AppColors.MAIN_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Créer le titre
        JLabel titre = new JLabel("Liste des Clients", SwingConstants.CENTER);
        titre.setFont(new Font("Serif", Font.BOLD, 24));
        titre.setForeground(Color.WHITE);
        topPanel.add(titre, BorderLayout.WEST);

        // Panel de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(AppColors.MAIN_COLOR);

        // Créer le menu déroulant pour les critères de recherche
        String[] criteria = {"Nom", "Prénom", "Email", "Téléphone"};
        searchCriteria = new JComboBox<>(criteria);
        searchCriteria.setPreferredSize(new Dimension(100, 30));

        // Créer le champ de recherche
        searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 30));
        // Ajouter ceci après la configuration du searchField
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTable();
            }
        });

        // Bouton de recherche
        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(40, 30));
        searchButton.addActionListener(e -> filterTable());

        JLabel searchLabel = new JLabel("Rechercher par: ");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 10));

        searchPanel.add(searchLabel);
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        topPanel.add(searchPanel, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);

// Dans la méthode constructor de ListClient, remplacez la partie de configuration de la table par :

// Configuration de la table
String[] colonnes = {"ID", "Nom", "Prénom", "Email", "Téléphone", "Adresse", "Actions"};
tableModel = new DefaultTableModel(colonnes, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 6;
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

// Configuration de la colonne Actions
TableColumn actionColumn = table.getColumnModel().getColumn(6);
actionColumn.setCellRenderer(new ButtonsRenderer());
actionColumn.setCellEditor(new ButtonsEditor(table));
actionColumn.setPreferredWidth(200); // Ajustez cette valeur selon vos besoins

// ScrollPane avec marges
JScrollPane scrollPane = new JScrollPane(table);
scrollPane.setBackground(AppColors.MAIN_COLOR);
scrollPane.getViewport().setBackground(AppColors.MAIN_COLOR);

JPanel wrapperPanel = new JPanel(new BorderLayout());
wrapperPanel.setBackground(AppColors.MAIN_COLOR);
wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
wrapperPanel.add(scrollPane, BorderLayout.CENTER);

this.add(wrapperPanel, BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        Vector<Client> clients = hotel.getListClient();
        for (Client client : clients) {
            Object[] rowData = {
                    client.getId_client(),
                    client.getNom(),
                    client.getPrenom(),
                    client.getEmail(),
                    client.getTel(),
                    client.getAdresse(),
                    "" // La colonne Actions sera gérée par le renderer et l'editor
            };
            tableModel.addRow(rowData);
        }
    }

    // Méthode pour filtrer les données du tableau
private void filterTable() {
    String searchText = searchField.getText().toLowerCase();
    String selectedCriteria = (String) searchCriteria.getSelectedItem();

    if (searchText.trim().length() == 0) {
        sorter.setRowFilter(null);
    } else {
        int columnIndex;
        switch (selectedCriteria) {
            case "Nom":
                columnIndex = 1; // Index de la colonne Nom
                break;
            case "Prénom":
                columnIndex = 2; // Index de la colonne Prénom
                break;
            case "Email":
                columnIndex = 3; // Index de la colonne Email
                break;
            case "Téléphone":
                columnIndex = 4; // Index de la colonne Téléphone
                break;
            default:
                columnIndex = 1;
        }
        
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, columnIndex));
    }
}

// Remplacez les classes ButtonRenderer et ButtonEditor existantes par celles-ci :

class ButtonsRenderer implements TableCellRenderer {
    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;

    public ButtonsRenderer() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");

        // Réduire la taille des boutons
        editButton.setPreferredSize(new Dimension(80, 30));
        deleteButton.setPreferredSize(new Dimension(80, 30));
        
        editButton.setBackground(AppColors.BUTTON_COLOR);
        editButton.setForeground(Color.WHITE);
        deleteButton.setBackground(AppColors.ERROR_COLOR);
        deleteButton.setForeground(Color.WHITE);

        panel.add(editButton);
        panel.add(Box.createHorizontalStrut(5)); // Espace entre les boutons
        panel.add(deleteButton);
        panel.setBackground(AppColors.MAIN_COLOR);
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
    private Client client;
    private JTable table;

    public ButtonsEditor(JTable table) {
        super(new JTextField());
        this.table = table;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");

        editButton.setBackground(AppColors.BUTTON_COLOR);
        editButton.setForeground(Color.WHITE);
        deleteButton.setBackground(AppColors.ERROR_COLOR);
        deleteButton.setForeground(Color.WHITE);

        editButton.addActionListener(e -> {
            if (client != null) {
                openEditDialog(client);
                fireEditingStopped();
            }
        });

        deleteButton.addActionListener(e -> {
            if (client != null) {
                deleteClient(client);
                fireEditingStopped();
            }
        });

        panel.add(editButton);
        panel.add(deleteButton);
        panel.setBackground(AppColors.MAIN_COLOR);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        client = hotel.getListClient().get(table.convertRowIndexToModel(row));
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}

    private void openEditDialog(Client client) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Modifier Client", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel nomLabel = new JLabel("Nom:");
        JTextField nomField = new JTextField(client.getNom());
        JLabel prenomLabel = new JLabel("Prénom:");
        JTextField prenomField = new JTextField(client.getPrenom());
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(client.getEmail());
        JLabel telLabel = new JLabel("Téléphone:");
        JTextField telField = new JTextField(client.getTel());
        JLabel adresseLabel = new JLabel("Adresse:");
        JTextField adresseField = new JTextField(client.getAdresse());

        panel.add(nomLabel);
        panel.add(nomField);
        panel.add(prenomLabel);
        panel.add(prenomField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(telLabel);
        panel.add(telField);
        panel.add(adresseLabel);
        panel.add(adresseField);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            // Validation du numéro de téléphone
            if (!telField.getText().matches("\\d{10}")) {
                JOptionPane.showMessageDialog(dialog,
                        "Le numéro de téléphone doit contenir exactement 10 chiffres.",
                        "Erreur de format", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mise à jour des informations du client
            client.setNom(nomField.getText());
            client.setPrenom(prenomField.getText());
            client.setEmail(emailField.getText());
            client.setTel(telField.getText());
            client.setAdresse(adresseField.getText());

            loadData(); // Refresh the table
            dialog.dispose();
        });

        panel.add(new JLabel());
        panel.add(saveButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void deleteClient(Client client) {
        UIManager.put("OptionPane.yesButtonText", "Oui");
        UIManager.put("OptionPane.noButtonText", "Non");

        int option = JOptionPane.showConfirmDialog(
                SwingUtilities.getWindowAncestor(this),
                "Êtes-vous sûr de vouloir supprimer ce client ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            hotel.getListClient().remove(client);
            loadData(); // Refresh the table
        }
    }

    // Dans la classe ListClient, modifiez la méthode refreshData() comme suit :
    private void refreshData() {
        tableModel.setRowCount(0);
        Vector<Client> clients = hotel.getListClient();
        for (Client c : clients) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            buttonPanel.setBackground(AppColors.MAIN_COLOR);

            JButton editBtn = new JButton("Modifier");
            editBtn.setBackground(AppColors.BUTTON_COLOR);
            editBtn.setForeground(Color.WHITE);
            editBtn.addActionListener(e -> openEditDialog(c));

            JButton delBtn = new JButton("Supprimer");
            delBtn.setBackground(AppColors.ERROR_COLOR);
            delBtn.setForeground(Color.WHITE);
            delBtn.addActionListener(e -> {
                UIManager.put("OptionPane.yesButtonText", "Oui");
                UIManager.put("OptionPane.noButtonText", "Non");

                int option = JOptionPane.showConfirmDialog(
                    SwingUtilities.getWindowAncestor(this),
                    "Êtes-vous sûr de vouloir supprimer ce client ?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (option == JOptionPane.YES_OPTION) {
                    hotel.getListClient().remove(c);
                    refreshData();
                }
            });

            buttonPanel.add(editBtn);
            buttonPanel.add(delBtn);

            Object[] row = {
                c.getId_client(),
                c.getNom(),
                c.getPrenom(),
                c.getEmail(),
                c.getTel(),
                c.getAdresse(),
                buttonPanel
            };
            tableModel.addRow(row);
        }

        // Configurer le rendu des boutons dans la dernière colonne
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof JPanel) {
                    return (JPanel) value;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        // Configurer l'éditeur pour la colonne des boutons
        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                return (Component) value;
            }
        });
    }
}