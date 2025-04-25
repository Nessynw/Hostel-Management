package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceClient extends JPanel {
    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color side_COLOR = new Color(9, 0, 91);
    private static final Color hover = new Color(58, 51, 124, 255); // Couleur du survol
    private JPanel mainPanel;  // Panneau principal où le contenu changera

    public InterfaceClient() {
        this.setLayout(new BorderLayout());

        // Barre latérale (Sidebar)
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(300, getMaximumSize().height)); // Modification pour la hauteur
        sidebar.setBackground(side_COLOR);
        this.add(sidebar, BorderLayout.WEST);

        // Configuration du GridBagLayout pour centrer les composants
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20); // Espacement autour des composants

        // Nom de l'hôtel
        JLabel nomHotel = new JLabel("<html><div style='text-align: center;'>" +
                "<span style='color: rgb(255,255,255);'>Hôtel </span><br>" +
                "<span style='color: rgb(171,169,192);'>Blue Castle</span>" +
                "</div></html>");
        nomHotel.setFont(new Font("Serif", Font.BOLD, 25));
        sidebar.add(nomHotel, gbc);

        // Ajouter les boutons du menu avec un espacement vertical
        String[] buttonMenu = {"Chambre", "Réservation", "Séjour", "Client", "list client"};
        for (String buttonText : buttonMenu) {
            gbc.gridy++;  // Déplacer vers la ligne suivante
            JButton button = new JButton(buttonText);
            button.setFont(new Font("Serif", Font.BOLD, 25));
            button.setBackground(side_COLOR);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setSize(290, 60);
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(hover);
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                public void mouseExited(MouseEvent e) {
                    button.setBackground(side_COLOR);
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });

            // Action pour chaque bouton du menu
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showClientForm(buttonText);  // Pass button text to showClientForm
                }
            });

            sidebar.add(button, gbc);
        }

        // Panneau principal (Main panel)
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());  // Utilisation de BorderLayout pour le contenu
        mainPanel.setBackground(main_color);
        this.add(mainPanel, BorderLayout.CENTER);
    }

    // Méthode pour afficher le formulaire du client ou la liste des clients
    private void showClientForm(String buttonText) {
        mainPanel.removeAll();  // Clear any existing content

        if (buttonText.equals("Client")) {
            // Show the client form
            NewClient clientForm = new NewClient();  // Créer une instance du formulaire client
            mainPanel.add(clientForm, BorderLayout.CENTER);  // Ajouter le formulaire du client
        }
        else if (buttonText.equals("list client")) {
            // Show the list of clients
            ListClient clientList = new ListClient();  // Créer une instance du panneau de liste de clients
            mainPanel.add(clientList.getListPanel(), BorderLayout.CENTER);  // Add the ListClient content to the main panel
        }

        mainPanel.revalidate();  // Revalidate the layout
        mainPanel.repaint();     // Repaint to show the new content
    }
}
