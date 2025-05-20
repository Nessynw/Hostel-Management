package Vue;

import Model.*;
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


        JPanel topPanel = new JPanel(new BorderLayout(20, 10));
        topPanel.setBackground(AppColors.MAIN_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        JLabel titre = new JLabel("Liste des Clients", SwingConstants.CENTER);
        titre.setFont(new Font("Serif", Font.BOLD, 24));
        titre.setForeground(Color.WHITE);
        topPanel.add(titre, BorderLayout.WEST);


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(AppColors.MAIN_COLOR);

        String[] criteria = {"Nom", "Prénom", "Email", "Téléphone"};
        searchCriteria = new JComboBox<>(criteria);
        searchCriteria.setPreferredSize(new Dimension(100, 30));

        searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 30));
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTable();
            }
        });


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


JTableHeader header = table.getTableHeader();
header.setReorderingAllowed(false);
header.setBackground(AppColors.MAIN_COLOR);
header.setForeground(AppColors.MAIN_COLOR);
header.setFont(new Font("Arial", Font.BOLD, 14));


table.setBackground(AppColors.MAIN_COLOR);
table.setForeground(Color.WHITE);
table.setGridColor(new Color(70, 70, 70));
table.setRowHeight(40);


TableColumn actionColumn = table.getColumnModel().getColumn(6);
actionColumn.setCellRenderer(new ButtonsRenderer());
actionColumn.setCellEditor(new ButtonsEditor(table));
actionColumn.setPreferredWidth(200); // Ajustez cette valeur selon vos besoins

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
                    ""
            };
            tableModel.addRow(rowData);
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
            case "Nom":
                columnIndex = 1;
                break;
            case "Prénom":
                columnIndex = 2;
                break;
            case "Email":
                columnIndex = 3;
                break;
            case "Téléphone":
                columnIndex = 4;
                break;
            default:
                columnIndex = 1;
        }
        
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, columnIndex));
    }
}


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
        panel.add(Box.createHorizontalStrut(5));
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
            loadData();
        }
    }

}