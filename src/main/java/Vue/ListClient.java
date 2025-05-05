package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

        // Data setup for table
        String[] columns = {"ID Client", "Nom Client", "Prénom Client", "Numéro Client", "Email", "Adresse", "Chambre", "Modifier", "Supprimer"};
        Object[][] list_client = {
                {"1", "Dupont", "Pierre", "0123456789", "pierre.dupont@mail.com", "Chambre 101", "123 Rue de Paris"},
                {"2", "Martin", "Sophie", "0123456790", "sophie.martin@mail.com", "Chambre 102", "456 Rue des Champs"},
                {"3", "Lemoine", "Luc", "0123456791", "luc.lemoine@mail.com", "Chambre 103", "789 Avenue Victor Hugo"},
                {"4", "Petit", "Marie", "0123456792", "marie.petit@mail.com", "Chambre 104", "234 Rue de Lyon"},
                {"5", "Durand", "Jean", "0123456793", "jean.durand@mail.com", "Chambre 105", "567 Boulevard Saint-Germain"},
                {"6", "Lemoine", "Claire", "0123456794", "claire.lemoine@mail.com", "Chambre 106", "345 Boulevard Montparnasse"},
                {"7", "Leclerc", "Jacques", "0123456795", "jacques.leclerc@mail.com", "Chambre 107", "678 Rue de la République"},
                {"8", "Rousseau", "Paul", "0123456796", "paul.rousseau@mail.com", "Chambre 108", "123 Avenue des Ternes"},
                {"9", "Lopez", "Anna", "0123456797", "anna.lopez@mail.com", "Chambre 109", "234 Rue du Faubourg"},
                {"10", "Bernard", "Claire", "0123456798", "claire.bernard@mail.com", "Chambre 110", "456 Avenue de la Liberté"},
                {"11", "Thomas", "Henri", "0123456799", "henri.thomas@mail.com", "Chambre 111", "789 Rue de la Gare"},
                {"12", "Gauthier", "Michel", "0123456800", "michel.gauthier@mail.com", "Chambre 112", "123 Rue du Parc"},
                {"13", "Robert", "Lucie", "0123456801", "lucie.robert@mail.com", "Chambre 113", "456 Boulevard Haussmann"},
                {"14", "Richard", "Gérard", "0123456802", "gerard.richard@mail.com", "Chambre 114", "789 Rue de la Bastille"},
                {"15", "David", "Sylvie", "0123456803", "sylvie.david@mail.com", "Chambre 115", "123 Rue de la Concorde"},
                {"16", "Lopez", "Carlos", "0123456804", "carlos.lopez@mail.com", "Chambre 116", "234 Avenue de Versailles"},
                {"17", "Benoit", "Isabelle", "0123456805", "isabelle.benoit@mail.com", "Chambre 117", "567 Rue de la Ville"},
                {"18", "Dufresne", "Jacques", "0123456806", "jacques.dufresne@mail.com", "Chambre 118", "678 Rue de l'Opéra"},
                {"19", "Moreau", "Bernadette", "0123456807", "bernadette.moreau@mail.com", "Chambre 119", "789 Rue des Etoiles"},
                {"20", "Perrot", "Anne", "0123456808", "anne.perrot@mail.com", "Chambre 120", "123 Rue de la Mer"},
                {"21", "Caron", "Michel", "0123456809", "michel.caron@mail.com", "Chambre 121", "456 Rue de la Montagne"},
                {"22", "Garnier", "Sophie", "0123456810", "sophie.garnier@mail.com", "Chambre 122", "789 Boulevard de la République"},
                {"23", "Boulanger", "Pierre", "0123456811", "pierre.boulanger@mail.com", "Chambre 123", "234 Rue du Soleil"},
                {"24", "Mathieu", "Florence", "0123456812", "florence.mathieu@mail.com", "Chambre 124", "567 Rue de l'Eglise"},
                {"25", "Noel", "David", "0123456813", "david.noel@mail.com", "Chambre 125", "678 Avenue de l'Indépendance"},
                {"26", "Francois", "Emilie", "0123456814", "emilie.francois@mail.com", "Chambre 126", "789 Rue de la Paix"},
                {"27", "Michel", "Thierry", "0123456815", "thierry.michel@mail.com", "Chambre 127", "123 Rue du Commerce"},
                {"28", "Lemoine", "Jean", "0123456816", "jean.lemoine@mail.com", "Chambre 128", "234 Avenue de la France"},
                {"29", "Guerrier", "Véronique", "0123456817", "veronique.guerrier@mail.com", "Chambre 129", "567 Boulevard de l'Université"},
                {"30", "Guillaume", "Jacqueline", "0123456818", "jacqueline.guillaume@mail.com", "Chambre 130", "789 Rue du Marché"}
        };


        // Panel to display data
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridBagLayout());
        dataPanel.setBackground(min_color);  // Set background color to min_color

        // Add column labels
        gbc.gridwidth = 1;
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
        for (int i = 0; i < list_client.length; i++) {
            for (int j = 0; j < list_client[i].length; j++) {
                String value = String.valueOf(list_client[i][j]);
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

            deleteButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Suppression de la réservation");
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

    // Main method for testing
    public static void main(String[] args) {
        JFrame frame = new JFrame("Réservations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ListClient());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);  // Center the window
        frame.setVisible(true);  // Show the window
    }
}
