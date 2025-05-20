package Vue;

import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceClient extends JPanel {
    private static final Color hover = new Color(58, 90, 153);
    private JPanel mainPanel;
    private Hotel hotel;

    public InterfaceClient(Hotel hotel) {
        this.setLayout(new BorderLayout());
        this.hotel = hotel;

        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(300, getMaximumSize().height));
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
                nomHotel.setForeground(new Color(171, 169, 192));
                nomHotel.setText("<html><div style='text-align: center;'>" +
                        "<span style='color: rgb(255,255,255);'>Hôtel </span><br>" +
                        "<span style='color: rgb(171,169,192);'>Blue Castel</span>" +
                        "</div></html>");
            }
        });

        sidebar.add(nomHotel, gbc);

        String[] buttonMenu = {"Réservation", "État des Chambres", "Liste Des Réservation", "Gérer Séjours", "Liste des Clients"};
        for (String buttonText : buttonMenu) {
            gbc.gridy++;
            JButton button = createSidebarButton(buttonText);
            sidebar.add(button, gbc);
        }


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

            case "Liste des Clients":

                ListClient clientList = new ListClient(hotel);
                mainPanel.add(clientList, BorderLayout.CENTER);
                break;
            case "Réservation":

                Chambres chambres = new Chambres(hotel);
                mainPanel.add(chambres, BorderLayout.CENTER);
                break;
            case "Liste Des Réservation":
                ReservationForm reservationForm = new ReservationForm(hotel);
                mainPanel.add(reservationForm, BorderLayout.CENTER);
                break;
            case "Gérer Séjours":
                SejourForm sejourForm = new SejourForm(hotel);
                mainPanel.add(sejourForm, BorderLayout.CENTER);
                break;

            case "État des Chambres":
                EtatChambres etatChambres = new EtatChambres(hotel);
                mainPanel.add(etatChambres, BorderLayout.CENTER);
                break;

            default:
                JOptionPane.showMessageDialog(this, "Option non supportée", "Erreur", JOptionPane.ERROR_MESSAGE);
                break;
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}