package Vue;

import javax.swing.*;
import java.awt.*;

public class InterfacePersonnel extends JPanel {

    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color side_COLOR = new Color(9, 0, 91);    // Couleur du côté (sidebar)

    private JPanel mainPanel;  // Panneau principal où le contenu changera
    private StyledButton btnTousEmployes;
    private StyledButton btnAjouterEmploye;
    private StyledButton btnSupprimerEmploye;

    public InterfacePersonnel() {
        this.setLayout(new BorderLayout());

        // Barre latérale (Sidebar)
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(300, getMaximumSize().height));
        sidebar.setBackground(side_COLOR);

        // Nom de l'hôtel centré
        JLabel nomHotel = new JLabel("<html><div style='text-align: center;'>" +
                "<br>" + "<span style='color: rgb(255,255,255);'>Hôtel </span><br>" +
                "<span style='color: rgb(171,169,192);'>Blue Casel </span>" +
                "</div></html>", SwingConstants.CENTER);
        nomHotel.setFont(new Font("Serif", Font.BOLD, 25));
        sidebar.add(nomHotel);

        this.add(sidebar, BorderLayout.WEST);

        // Panneau principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(main_color);
        this.add(mainPanel, BorderLayout.CENTER);

        // Création des boutons
        btnTousEmployes = new StyledButton("Voir tous les employés");
        btnAjouterEmploye = new StyledButton("Ajouter un employé");
        btnSupprimerEmploye = new StyledButton("Supprimer un employé");

        // Modifier la taille des boutons
        btnTousEmployes.setPreferredSize(new Dimension(400, 60));
        btnAjouterEmploye.setPreferredSize(new Dimension(400, 60));
        btnSupprimerEmploye.setPreferredSize(new Dimension(400, 60));

        // Ajouter les boutons au panneau principal
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 20, 20);
        constraints.anchor = GridBagConstraints.CENTER;

        mainPanel.add(btnTousEmployes, constraints);
        constraints.gridy++;
        mainPanel.add(btnAjouterEmploye, constraints);
        constraints.gridy++;
        mainPanel.add(btnSupprimerEmploye, constraints);



    }
}