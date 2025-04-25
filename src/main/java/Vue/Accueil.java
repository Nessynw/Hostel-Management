package Vue;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accueil extends JPanel {
    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color side_COLOR = new Color(9, 0, 91);  // Couleur du côté (sidebar)
    private JPanel mainPanel;     // Panneau principal où le contenu changera

    private CardLayout cardLayout;

    public Accueil(JFrame parentFrame) { // Passer la référence de la fenêtre parent
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
                "<span style='color: rgb(171,169,192);'>Blue Casel </span>" +
                "</div></html>", SwingConstants.CENTER);
        nomHotel.setFont(new Font("Serif", Font.BOLD, 25));
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
        buttonConstraints.gridy = 0;  // Centrer verticalement
        buttonConstraints.insets = new Insets(10, 10, 10, 10);  // Espacement autour des boutons
        buttonConstraints.anchor = GridBagConstraints.CENTER;

        // Création des boutons
        ClientButton clientButton = new ClientButton("Client");
        ClientButton personalButton = new ClientButton("Personnel");

        // Panneau pour les boutons centrés
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));  // FlowLayout pour centrer
        buttonPanel.setOpaque(false);

        // Ajouter les boutons au panneau
        buttonPanel.add(clientButton);
        buttonPanel.add(personalButton);

        // Ajouter le panneau des boutons à mainPanel
        mainPanel.add(buttonPanel, buttonConstraints);  // Utilisation de GridBagConstraints pour centrer les boutons

        // Contrainte pour centrer le texte
        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.gridx = 0;  // Centrer horizontalement
        textConstraints.gridy = 1;  // Placer le texte sous les boutons
        textConstraints.insets = new Insets(20, 0, 10, 0); // Espacement au-dessus du texte
        textConstraints.anchor = GridBagConstraints.CENTER;

        // Ajouter le texte
        JLabel welcomeLabel = new JLabel("Welcome to Blue Casel");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 50));  // Augmenter la taille de la police
        mainPanel.add(welcomeLabel, textConstraints);

        // Exemple d'action au clic sur le bouton Client
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une nouvelle fenêtre avec l'interface Client
                JFrame clientFrame = new JFrame("Interface Client");
                clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                clientFrame.setSize(600, 400);  // Taille de la fenêtre
                clientFrame.setLocationRelativeTo(null);  // Centrer la fenêtre à l'écran

                // Ajouter l'interface client à la fenêtre
                InterfaceClient intrfaceClientPanel = new InterfaceClient();
                clientFrame.add(intrfaceClientPanel);
                clientFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                // Rendre la fenêtre visible
                clientFrame.setVisible(true);

                // Rendre la fenêtre principale invisible
                parentFrame.setVisible(false);  // Masquer la fenêtre principale
            }
        });

        // Exemple d'action au clic sur le bouton Personnel
        personalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une nouvelle fenêtre avec l'interface Client
                JFrame PersonalFrame = new JFrame("Interface Client");
                PersonalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                PersonalFrame.setSize(600, 400);  // Taille de la fenêtre
                PersonalFrame.setLocationRelativeTo(null);  // Centrer la fenêtre à l'écran

                // Ajouter l'interface client à la fenêtre
                InterfacePersonnel interfacePersonnel = new InterfacePersonnel();
                PersonalFrame.add(interfacePersonnel);
                PersonalFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                // Rendre la fenêtre visible
                PersonalFrame.setVisible(true);

                // Rendre la fenêtre principale invisible
                parentFrame.setVisible(false);  // Masquer la fenêtre principale
            }
        });


    }
}
