package Vue;

import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
public class InterfaceClient extends JPanel {
    private static final Color hover = new Color(58, 90, 153); // Couleur du survol
    private JPanel mainPanel;  // Panneau principal où le contenu changera
    private Hotel hotel;

    public InterfaceClient(Hotel hotel) {
        this.setLayout(new BorderLayout());
        this.hotel = hotel;

        // Barre latérale (Sidebar)
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(300, getMaximumSize().height)); // Modification pour la hauteur
        sidebar.setBackground(AppColors.SIDE_COLOR);
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

        // Pour retourner vers homepage
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
        String[] buttonMenu = {"Réservation", "État des Chambres", "Liste Des Réservation", "Gérer Séjours", "Nouveau Client", "Liste des Clients"};
        for (String buttonText : buttonMenu) {
            gbc.gridy++;  // Déplacer vers la ligne suivante
            JButton button = createSidebarButton(buttonText);
            sidebar.add(button, gbc);
        }

        // Panneau principal (Main panel)
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(AppColors.MAIN_COLOR);

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
        button.setBackground(AppColors.SIDE_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        // Augmentation de la largeur du bouton de 200 à 260 pixels
        Dimension buttonSize = new Dimension(260, 50); // Largeur augmentée à 260px et hauteur à 50px
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);

        // Le reste du code reste identique...
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(AppColors.SIDE_COLOR);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showClientForm(buttonText);
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
            case "Nouveau Client":
                // Afficher le formulaire client
                NewClient clientForm = new NewClient(hotel, null, LocalDate.now(), LocalDate.now());
                mainPanel.add(clientForm, BorderLayout.CENTER);
                break;
            case "Liste des Clients":
                // Afficher la liste des clients
                ListClient clientList = new ListClient(hotel);
                mainPanel.add(clientList, BorderLayout.CENTER);
                break;
            case "Réservation":
                // Afficher la gestion des chambres (garde la même fonctionnalité que "Chambres")
                Chambres chambres = new Chambres(hotel);
                mainPanel.add(chambres, BorderLayout.CENTER);
                break;
            case "Liste Des Réservation":
                ReservationForm reservationForm = new ReservationForm(hotel);
                mainPanel.add(reservationForm, BorderLayout.CENTER);
                break;
            case "Gérer Séjours":
                SejourForm sejourForm = new SejourForm();
                mainPanel.add(sejourForm, BorderLayout.CENTER);
                break;
            default:
                // Gérer un texte de bouton inattendu
                JOptionPane.showMessageDialog(this, "Option non supportée", "Erreur", JOptionPane.ERROR_MESSAGE);
                break;
        }

        mainPanel.revalidate();  // Revalidate the layout
        mainPanel.repaint();     // Repaint to show the new content
    }
}