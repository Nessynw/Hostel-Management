package Vue;

import Model.Hotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

public class ReservationForm extends JPanel {
     private  Hotel hotel;
    private static final Color hover = new Color(58, 90, 153);
    private static final Color text_color = Color.BLACK; // Text color
    private static final Color buttonColor = new Color(34, 193, 195); // Button color (teal)
    private static final Color deleteButtonColor = new Color(255, 69, 0); // Delete button color (red)
    private static final Color min_color = new Color(40, 45, 80);  // Couleur pour le JScrollPane
    private static final Font fieldFont = new Font("Arial", Font.PLAIN, 16); // Font for labels

    public ReservationForm() {
        setLayout(new BorderLayout());
        this.setBackground(AppColors.MAIN_COLOR);




        // GridBagConstraints setup
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);  // Espacement entre les composants

        // Navigation bar setup
        JPanel navBar = new JPanel();
        navBar.setLayout(new BorderLayout());
        navBar.setBackground(AppColors.MAIN_COLOR);
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
        searchPanel.setBackground(AppColors.MAIN_COLOR);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add the search panel to the navigation bar
        navBar.add(searchPanel, BorderLayout.EAST);

        // Add the navigation bar to the top of the window
        add(navBar, BorderLayout.NORTH);
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Padding around the navbar

        // Data setup for table
        String[] donnes = {"ID Client", "Nom Client", "Prénom Client", "ID Chambre", "Date Début", "Date Fin", "Modifier", "Supprimer"};
      String[][] data = {};

        // Panel to display data
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridBagLayout());
        dataPanel.setBackground(min_color);  // Set background color to min_color

        // Add column labels
        gbc.gridwidth = 1;
        for (int i = 0; i < donnes.length; i++) {
            JLabel label = new JLabel(donnes[i]);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.WHITE);
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            dataPanel.add(label, gbc);
        }

        // Fill the data in the table
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                String value = String.valueOf(data[i][j]);
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
            gbc.gridx = 7;
            gbc.gridy = i + 1;
            dataPanel.add(deleteButton, gbc);

            deleteButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Suppression de la réservation");
            });

            // Add Edit button
            JButton editButton = new JButton("Modifier");
            editButton.setBackground(buttonColor);
            editButton.setForeground(Color.WHITE);
            editButton.setPreferredSize(new Dimension(100, 30));
            editButton.setFocusPainted(false);
            gbc.gridx = 6;
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

    // Main method for testing
    public static void main(String[] args) {
        JFrame frame = new JFrame("Réservations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ReservationForm());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);  // Center the window
        frame.setVisible(true);  // Show the window
    }
}
