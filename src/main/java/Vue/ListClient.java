package Vue;

import javax.swing.*;
import java.awt.*;

public class ListClient extends JPanel {

    private static final Color main_color = new Color(18, 11, 61);  // Base color
    private static final Color text_color = Color.BLACK; // Text color
    private static final Color buttonColor = new Color(58, 90, 153); // Button color (teal)
    private static final Color deleteButtonColor = new Color(76, 6, 6); // Delete button color (red)
    private static final Font fieldFont = new Font("Arial", Font.PLAIN, 16); // Font for labels

    public ListClient() {
        // Utilisation de BorderLayout pour que le JScrollPane occupe tout l'espace
        setLayout(new BorderLayout());
        this.setBackground(main_color);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);  // Espacement entre les composants

        // Barre de navigation (nav bar)
        JPanel navBar = new JPanel();
        navBar.setLayout(new BorderLayout());
        navBar.setBackground(main_color);
        // Titre de la fenêtre
        JLabel title = new JLabel("Réservations");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.white);
        navBar.add(title, BorderLayout.CENTER);

        // Champ de recherche
        JTextField searchField = new JTextField();
        searchField.setFont(fieldFont);
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Rechercher une réservation");

        // Ajouter le champ de recherche à la barre de navigation
        navBar.add(searchField, BorderLayout.EAST);

        // Ajouter la barre de navigation au top de la fenêtre
        add(navBar, BorderLayout.NORTH);

        // Données des colonnes
        String[] donnes = {"ID Client", "Nom Client", "Prénom Client", "numero", "Modifier", "Supprimer"};

        // Données des réservations (exemple)
        Object[][] data = {
                {1, "Dupont", "Jean",  "0541250320"},
                {2, "Martin", "Sophie",  "0541250320"},
                {3, "Bernard", "Pierre",  "0541250320"},
                {4, "Durand", "Marie",  "0541250320"},
                {1, "Dupont", "Jean",  "0541250320"},
                {2, "Martin", "Sophie", "0541250320"},
                {3, "Bernard", "Pierre",  "0541250320"},
                {4, "Durand", "Marie",  "0541250320"},
                {1, "Dupont", "Jean",  "0541250320"},
                {2, "Martin", "Sophie",  "0541250320"},
                {3, "Bernard", "Pierre",  "0541250320"},
                {4, "Durand", "Marie",  "0541250320"}
        };

        // Panel pour afficher les données dans un tableau
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridBagLayout());

        // Ajout des labels pour les colonnes
        gbc.gridwidth = 1;  // Chaque label occupe une seule colonne
        for (int i = 0; i < donnes.length; i++) {
            JLabel label = new JLabel(donnes[i]);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(buttonColor);
            gbc.gridx = i;  // Déplace les labels sur chaque colonne
            gbc.gridy = 0;  // Positionne les labels sur la première ligne
            gbc.anchor = GridBagConstraints.CENTER;  // Centrer le texte
            dataPanel.add(label, gbc);
        }

        // Remplir les données dans le tableau
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                String value = String.valueOf(data[i][j]); // Convertir les données en chaîne
                JLabel infoLabel = new JLabel(value);
                infoLabel.setFont(fieldFont);
                infoLabel.setForeground(text_color);
                gbc.gridx = j;  // Positionner le label selon la colonne
                gbc.gridy = i + 1;  // Positionner sur la ligne suivante après les en-têtes
                dataPanel.add(infoLabel, gbc);
            }

            // Bouton Supprimer
            JButton deleteButton = new JButton("Supprimer");
            deleteButton.setBackground(deleteButtonColor);
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setPreferredSize(new Dimension(100, 30)); // Bouton plus large
            deleteButton.setFocusPainted(false);
            gbc.gridx = 5; // Placer le bouton Supprimer dans la colonne 7
            gbc.gridy = i + 1;
            dataPanel.add(deleteButton, gbc);

            deleteButton.addActionListener(e -> {
                // Code à exécuter pour supprimer la ligne
                JOptionPane.showMessageDialog(this, "Suppression de la réservation ");
                // Vous pouvez ajouter une logique de suppression ici
            });

            // Bouton Modifier
            JButton editButton = new JButton("Modifier");
            editButton.setBackground(buttonColor);
            editButton.setForeground(Color.WHITE);
            editButton.setPreferredSize(new Dimension(100, 30)); // Bouton plus large
            editButton.setFocusPainted(false);
            gbc.gridx = 4; // Placer le bouton Modifier dans la colonne 6
            gbc.gridy = i + 1;
            dataPanel.add(editButton, gbc);

            editButton.addActionListener(e -> {
                // Code à exécuter pour modifier la ligne
                JOptionPane.showMessageDialog(this, "Modification de la réservation ");
                // Vous pouvez ajouter une logique de modification ici
            });
        }

        // Envelopper le panel des données dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(dataPanel);
        scrollPane.setBackground(main_color);
        JViewport viewport = scrollPane.getViewport();
        viewport.setOpaque(true);
        viewport.setBackground(main_color);
        dataPanel.setOpaque(true);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    // Méthode principale pour tester
    public static void main(String[] args) {
        JFrame frame = new JFrame("list client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ListClient());
        frame.setSize(800, 600);  // Taille de la fenêtre
        frame.setLocationRelativeTo(null);  // Centrer la fenêtre
        frame.setVisible(true);  // Afficher la fenêtre
    }
}
