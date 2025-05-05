package Vue;

import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceClient extends JPanel {
    private static final Color main_color = new Color(26, 31, 75);  // Couleur de base
    private static final Color side_COLOR = new Color(46, 59, 142);
    private static final Color hover = new Color(58, 90, 153); // Couleur du survol
    private JPanel mainPanel;  // Panneau principal où le contenu changera
    private Hotel hotel;

    public InterfaceClient(Hotel hotel) {
        this.setLayout(new BorderLayout());
        this.hotel = hotel;

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
                "<span style='color: rgb(171,169,192);'>Blue Castel</span>" +
                "</div></html>");
        nomHotel.setFont(new Font("Serif", Font.BOLD, 30));

        // Add MouseListener to navigate to home page on click and apply hover effect
        nomHotel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Navigate back to the home page when the hotel name is clicked
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(InterfaceClient.this);
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new Accueil(parentFrame, hotel));
                parentFrame.revalidate();
                parentFrame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Change color on hover
                nomHotel.setForeground(new Color(255, 255, 255)); // White color on hover
                nomHotel.setText("<html><div style='text-align: center;'>" +
                        "<span style='color: rgb(255,255,255);'>Hôtel </span><br>" +
                        "<span style='color: rgb(255, 255, 255);'>Blue Castel</span>" +
                        "</div></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Reset the color when mouse exits
                nomHotel.setForeground(new Color(171, 169, 192)); // Original color
                nomHotel.setText("<html><div style='text-align: center;'>" +
                        "<span style='color: rgb(255,255,255);'>Hôtel </span><br>" +
                        "<span style='color: rgb(171,169,192);'>Blue Castel</span>" +
                        "</div></html>");
            }
        });

        sidebar.add(nomHotel, gbc);

        // Ajouter les boutons du menu avec un espacement vertical
        String[] buttonMenu = {"Chambres","Etat_chambes", "Réservations", "Séjours", "Clients", "Liste de clients"};
        for (String buttonText : buttonMenu) {
            gbc.gridy++;  // Déplacer vers la ligne suivante
            JButton button = createSidebarButton(buttonText);
            sidebar.add(button, gbc);
        }

        // Panneau principal (Main panel)
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(main_color);

        // Texte par défaut
        JLabel defaultLabel = new JLabel("Blue Castel", SwingConstants.CENTER);
        defaultLabel.setFont(new Font("Serif", Font.BOLD, 60));
        defaultLabel.setForeground(new Color(176, 176, 176));
        mainPanel.add(defaultLabel, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    // Méthode pour créer un bouton avec effet de survol
    private JButton createSidebarButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Serif", Font.BOLD, 25));
        button.setBackground(side_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        Dimension buttonSize = new Dimension(200, 40); // Par exemple 250px de large, 60px de haut
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        // Ajouter les effets de survol
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
                try {
                    showClientForm(buttonText);  // Pass button text to showClientForm
                } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return button;
    }

    // Méthode pour afficher le formulaire du client ou la liste des clients
    private void showClientForm(String buttonText) throws UnsupportedLookAndFeelException {
        mainPanel.removeAll();  // Clear any existing content

        switch (buttonText) {
            case "Clients":
                // Afficher le formulaire client
                NewClient clientForm = new NewClient(hotel);
                mainPanel.add(clientForm, BorderLayout.CENTER);
                break;
            case "Liste de clients":
                // Afficher la liste des clients
                ListClient clientList = new ListClient();
                mainPanel.add(clientList, BorderLayout.CENTER);
                break;
            case "Chambres":
                // Afficher la gestion des chambres
                Chambres chambres = new Chambres(hotel);
                mainPanel.add(chambres, BorderLayout.CENTER);
                break;
            case "Réservations":
                ReservationForm reservationForm = new ReservationForm();
                mainPanel.add(reservationForm, BorderLayout.CENTER);
                break;
            case "Séjours":
                  SejourForm sejourForm = new SejourForm();
                  mainPanel.add(sejourForm, BorderLayout.CENTER);
            default:
                // Gérer un texte de bouton inattendu
                JOptionPane.showMessageDialog(this, "Option non supportée", "Erreur", JOptionPane.ERROR_MESSAGE);
                break;
        }

        mainPanel.revalidate();  // Revalidate the layout
        mainPanel.repaint();     // Repaint to show the new content
    }
}
