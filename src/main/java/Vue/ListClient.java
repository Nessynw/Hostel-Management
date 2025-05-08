package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class ListClient extends JPanel {

    private static final Color hover = new Color(58, 90, 153);
    private static final Color main_color = new Color(18, 11, 61);  // Base color
    private static final Color text_color = Color.BLACK; // Text color
    private static final Color buttonColor = new Color(34, 193, 195); // Button color (teal)
    private static final Color deleteButtonColor = new Color(255, 69, 0); // Delete button color (red)
    private static final Color min_color = new Color(40, 45, 80);  // Color for JScrollPane
    private static final Font fieldFont = new Font("Arial", Font.PLAIN, 16); // Font for labels

    public ListClient() {
        setLayout(new BorderLayout());
        this.setBackground(main_color);

        // GridBagConstraints setup
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);  // Spacing between components

        // Navigation bar setup
        JPanel navBar = new JPanel();
        navBar.setLayout(new BorderLayout());
        navBar.setBackground(main_color);
        navBar.setBorder(BorderFactory.createLineBorder(text_color));

        // Title setup
        JLabel title = new JLabel("List Réservations");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        navBar.add(title, BorderLayout.WEST);

        // Create the search field and set its properties
        JTextField searchField = new JTextField();
        searchField.setFont(fieldFont);
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Search for a reservation");

        ImageIcon searchIcon = new ImageIcon("src/main/resources/search.png");
        Icon scaledSearchIcon = new ImageIcon(searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        // Create the search button with the icon
        JButton searchButton = new JButton(scaledSearchIcon);
        searchButton.setPreferredSize(new Dimension(40, 30));  // Adjust button size to fit the icon
        searchButton.setBorder(BorderFactory.createEmptyBorder());  // Remove default border
        searchButton.setBackground(Color.WHITE);  // Make background color same as the navbar
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                searchButton.setBackground(hover);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                searchButton.setBackground(Color.white);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Add the search field and button to the navBar panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));  // Right-align the components with some spacing
        searchPanel.setBackground(main_color);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add the search panel to the navigation bar
        navBar.add(searchPanel, BorderLayout.EAST);

        // Add the navigation bar to the top of the window
        add(navBar, BorderLayout.NORTH);
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Padding around the navbar

        // Panel to display data
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridBagLayout());
        dataPanel.setBackground(min_color);  // Set background color to min_color

        // Add column labels
        gbc.gridwidth = 1;
        String[] columns = {"ID Client", "Nom Client", "Prénom Client", "Numéro Client", "Email", "Adresse", "Modifier", "Supprimer"};
        for (int i = 0; i < columns.length; i++) {
            JLabel label = new JLabel(columns[i]);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.WHITE);
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            dataPanel.add(label, gbc);
        }

        // Fill the data in the table
        for (int i = 0; i < NewClient.clientMatrix.length; i++) {
            if (NewClient.clientMatrix[i][0] == null) continue;  // Skip empty rows

            for (int j = 0; j < 7; j++) {
                String value = String.valueOf(NewClient.clientMatrix[i][j]);
                JLabel infoLabel = new JLabel(value);
                infoLabel.setFont(fieldFont);
                infoLabel.setForeground(Color.WHITE);
                gbc.gridx = j;
                gbc.gridy = i + 1;
                dataPanel.add(infoLabel, gbc);
            }

            // Add Delete button
            JButton deleteButton = new JButton("Supprimer");
            deleteButton.setBackground(deleteButtonColor);
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setPreferredSize(new Dimension(100, 30));
            deleteButton.setFocusPainted(false);
            gbc.gridx = 8;
            gbc.gridy = i + 1;
            dataPanel.add(deleteButton, gbc);

            // Ajouter un ActionListener pour supprimer le client
            int finalI = i;
            deleteButton.addActionListener(e -> {
                // Suppression du client
                deleteClient(finalI);
                // Réactualiser l'affichage
                refreshData(dataPanel, gbc);
            });

            // Add Edit button
            JButton editButton = new JButton("Modifier");
            editButton.setBackground(buttonColor);
            editButton.setForeground(Color.WHITE);
            editButton.setPreferredSize(new Dimension(100, 30));
            editButton.setFocusPainted(false);
            gbc.gridx = 7;
            gbc.gridy = i + 1;
            dataPanel.add(editButton, gbc);
            editButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Modification de la réservation");
            });
        }

        // Wrap the data panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(dataPanel);
        scrollPane.setBackground(min_color);  // Set background color to min_color
        scrollPane.getViewport().setBackground(min_color);  // Set viewport background color
        scrollPane.setBorder(BorderFactory.createEmptyBorder());  // Remove border of JScrollPane

        add(scrollPane, BorderLayout.CENTER);

        // Add action listener to search button
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            JOptionPane.showMessageDialog(this, "Searching for: " + searchText);
            // Add search logic here to filter the data based on the searchText
        });
    }

    // Méthode pour supprimer un client à partir de l'index
    private void deleteClient(int index) {
        // Décaler les éléments après l'index supprimé vers la gauche
        for (int i = index; i < NewClient.clientMatrix.length - 1; i++) {
            NewClient.clientMatrix[i] = NewClient.clientMatrix[i + 1];
        }

        // Effacer la dernière ligne qui devient dupliquée après le décalage
        NewClient.clientMatrix[NewClient.clientMatrix.length - 1] = new Object[8];
    }

    // Méthode pour rafraîchir l'affichage des données après une modification ou suppression
    private void refreshData(JPanel dataPanel, GridBagConstraints gbc) {
        dataPanel.removeAll();  // Effacer les composants existants
        // Réinitialiser les labels de la première ligne (titres des colonnes)
        String[] columns = {"ID Client", "Nom Client", "Prénom Client", "Numéro Client", "Email", "Adresse", "Modifier", "Supprimer"};
        for (int i = 0; i < columns.length; i++) {
            JLabel label = new JLabel(columns[i]);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.WHITE);
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            dataPanel.add(label, gbc);
        }

        // Réafficher les clients après suppression
        for (int i = 0; i < NewClient.clientMatrix.length; i++) {
            if (NewClient.clientMatrix[i][0] == null) continue;  // Skip empty rows

            for (int j = 0; j < 7; j++) {
                String value = String.valueOf(NewClient.clientMatrix[i][j]);
                JLabel infoLabel = new JLabel(value);
                infoLabel.setFont(fieldFont);
                infoLabel.setForeground(Color.WHITE);
                gbc.gridx = j;
                gbc.gridy = i + 1;
                dataPanel.add(infoLabel, gbc);
            }

            // Add Delete button again after reloading data
            JButton deleteButton = new JButton("Supprimer");
            deleteButton.setBackground(deleteButtonColor);
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setPreferredSize(new Dimension(100, 30));
            deleteButton.setFocusPainted(false);
            gbc.gridx = 8;
            gbc.gridy = i + 1;
            dataPanel.add(deleteButton, gbc);

            int finalI = i;
            deleteButton.addActionListener(e -> {
                // Suppression du client
                deleteClient(finalI);
                // Réactualiser l'affichage
                refreshData(dataPanel, gbc);
            });
        }

        // Refaire l'affichage
        revalidate();
        repaint();
    }
}
