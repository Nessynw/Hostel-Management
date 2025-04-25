package Vue;

import javax.swing.*;
import java.awt.*;

public class InterfacePersonnel extends JPanel {
    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color side_COLOR = new Color(9, 0, 91);  // Couleur du côté (sidebar)
    private JPanel mainPanel;     // Panneau principal où le contenu changera


    public  InterfacePersonnel() {
        this.setLayout(new BorderLayout());

        // Barre latérale (Sidebar)
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(300, getMaximumSize().height)); // Modification pour la hauteur
        sidebar.setBackground(side_COLOR);



        // Nom de l'hôtel centré
        JLabel nomHotel = new JLabel("<html><div style='text-align: center;'>" +
                "<br>" + "<span style='color: rgb(255,255,255);'>Hôtel </span><br>" +
                "<span style='color: rgb(171,169,192);'>Blue Casel </span>" +
                "</div></html>", SwingConstants.CENTER);
        nomHotel.setFont(new Font("Serif", Font.BOLD, 25));
        sidebar.add(nomHotel);

        this.add(sidebar, BorderLayout.WEST);

        // Panneau principal (Main Panel)
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());  // Utilisation de GridBagLayout
        mainPanel.setBackground(main_color);
        this.add(mainPanel, BorderLayout.CENTER);

        // Ajouter plus de composants pour l'interface client ici (comme des champs de texte, boutons, etc.)
        JLabel clientLabel = new JLabel(" InterfacePersonnel", SwingConstants.CENTER);
        clientLabel.setFont(new Font("Serif", Font.BOLD, 40));

        // Contrainte pour centrer le texte
        GridBagConstraints clientLabelConstraints = new GridBagConstraints();
        clientLabelConstraints.gridx = 0;
        clientLabelConstraints.gridy = 0;
        clientLabelConstraints.insets = new Insets(20, 20, 20, 20);
        clientLabelConstraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(clientLabel, clientLabelConstraints);
    }

    // Retourner le panneau principal de l'interface client
    public JPanel getContentPanel() {
        return mainPanel;
    }
}

