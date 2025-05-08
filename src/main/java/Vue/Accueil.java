package Vue;

import Model.*;
import javax.swing.*;
import java.awt.*;
import Controler.*; ;

public class Accueil extends JPanel {
    private static final Color main_color = new Color(26, 31, 75);  // Couleur de base
    private static final Color side_COLOR = new Color(46, 59, 142);  // Couleur du côté (sidebar)
    private JPanel mainPanel;  // Panneau principal où le contenu changera

    public Accueil(JFrame parentFrame, Hotel hotel) {
        this.setLayout(new BorderLayout());

        // Barre latérale (Sidebar)
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(300, getMaximumSize().height)); // Modification pour la hauteur
        sidebar.setBackground(side_COLOR);

        // Contrainte pour centrer les composants dans la sidebar
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Marges internes
        gbc.anchor = GridBagConstraints.CENTER;

        // Nom de l'hôtel centré
        JLabel nomHotel = new JLabel("<html><div style='text-align: center;'>" +
                "<br>" + "<span style='color: rgb(255,255,255);'>Hôtel </span><br>" +
                "<span style='color: rgb(171,169,192);'>Blue Castel </span>" +
                "</div></html>", SwingConstants.CENTER);
        nomHotel.setFont(new Font("Serif", Font.BOLD, 30));
        sidebar.add(nomHotel, gbc);

        this.add(sidebar, BorderLayout.WEST);

        // Panneau principal (Main Panel)
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());  // Utilisation de GridBagLayout
        mainPanel.setBackground(main_color);
        this.add(mainPanel, BorderLayout.CENTER);

        // Contrainte pour centrer les éléments dans mainPanel
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;  // Centrer horizontalement
        buttonConstraints.gridy = 1;  // Placer les boutons en dessous du texte
        buttonConstraints.insets = new Insets(10, 10, 10, 10);  // Espacement autour des boutons
        buttonConstraints.anchor = GridBagConstraints.CENTER;

        // Création des boutons
        StyledButton clientButton = new StyledButton("Client");
        StyledButton personnelButton = new StyledButton("Personnel");

        // Panneau pour les boutons centrés
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));  // FlowLayout pour centrer
        buttonPanel.setOpaque(false);

        // Ajouter les boutons au panneau
        buttonPanel.add(clientButton);
        buttonPanel.add(personnelButton);

        // Ajouter le panneau des boutons à mainPanel
        mainPanel.add(buttonPanel, buttonConstraints);  // Utilisation de GridBagConstraints pour centrer les boutons

        // Contrainte pour centrer le texte
        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.gridx = 0;  // Centrer horizontalement
        textConstraints.gridy = 0;  // Placer le texte au-dessus des boutons
        textConstraints.insets = new Insets(20, 0, 10, 0); // Espacement au-dessus du texte
        textConstraints.anchor = GridBagConstraints.CENTER;

        // Ajouter le texte
        JLabel welcomeLabel = new JLabel("Welcome to Blue Castel");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 60));  // Augmenter la taille de la police
        welcomeLabel.setForeground(new Color(176, 176, 176));  // Gris clair pour le texte
        mainPanel.add(welcomeLabel, textConstraints);

        // Action au clic sur le bouton "Client"
        ClientButtonControleur b = new ClientButtonControleur(parentFrame, hotel);
        clientButton.addActionListener(b);

        // Action au clic sur le bouton "Personnel"
        personnelButton.addActionListener(e -> {
            Authentification authentification = new Authentification(() -> {
                PersonnelController controller = new PersonnelController(parentFrame, hotel);
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(controller.getVue());
                parentFrame.revalidate();
                parentFrame.repaint();
            }, hotel);
            authentification.setVisible(true);
        });
    }
}