package Vue;

import javax.swing.*;
import java.awt.*;
import Model.*;

public class InterfacePersonnel extends JPanel {
    private static final Color MAIN_COLOR = new Color(18, 11, 61);
    private static final Color SIDE_COLOR = new Color(9, 0, 91);

    private StyledButton btnTousEmployes;
    private StyledButton btnAjouterEmploye;
    private StyledButton btnSupprimerEmploye;
    private JPanel mainPanel;
    private Hotel hotel;
    private JFrame parentFrame;

    public InterfacePersonnel(JFrame frame, Hotel hotel) {
        this.parentFrame = frame;
        this.hotel = hotel;

        this.setLayout(new BorderLayout());
        setupSidebar();
        setupMainPanel();
    }

    private void setupSidebar() {
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(300, getMaximumSize().height));
        sidebar.setBackground(SIDE_COLOR);

        JLabel nomHotel = new JLabel("<html><div style='text-align: center;'>" +
                "<br><span style='color: #FFFFFF;'>Hôtel</span><br>" +
                "<span style='color: #ABA9C0;'>Blue Castle</span>" +
                "</div></html>", SwingConstants.CENTER);
        nomHotel.setFont(new Font("Serif", Font.BOLD, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(50, 0, 50, 0);

        sidebar.add(nomHotel, gbc);
        this.add(sidebar, BorderLayout.WEST);
    }

    private void setupMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(MAIN_COLOR);

        // Create button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(MAIN_COLOR);

        // Create the buttons and add them to button panel
        createButtons(buttonPanel);

        // Add panels to main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void createButtons(JPanel buttonPanel) {
        btnTousEmployes = new StyledButton("Voir tous les employés");
        btnAjouterEmploye = new StyledButton("Ajouter un employé");
        btnSupprimerEmploye = new StyledButton("Supprimer un employé");

        Dimension buttonSize = new Dimension(400, 60);
        btnTousEmployes.setPreferredSize(buttonSize);
        btnAjouterEmploye.setPreferredSize(buttonSize);
        btnSupprimerEmploye.setPreferredSize(buttonSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        buttonPanel.add(btnTousEmployes, gbc);
        gbc.gridy++;
        buttonPanel.add(btnAjouterEmploye, gbc);
        gbc.gridy++;
        buttonPanel.add(btnSupprimerEmploye, gbc);

        // Action listeners
        btnTousEmployes.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new AfficherEmploye(parentFrame, hotel));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        btnAjouterEmploye.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new AjouterEmploye(parentFrame, hotel));  // Ajouter l'objet hotel
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        btnSupprimerEmploye.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            SupprimerEmploye supprimerEmploye = new SupprimerEmploye(parentFrame, hotel);
            supprimerEmploye.setListeEmployes(hotel.getListEmploye());
            parentFrame.getContentPane().add(supprimerEmploye);
            parentFrame.revalidate();
            parentFrame.repaint();
        });
    }

    public AbstractButton getBtnTousEmployes() {
        return btnTousEmployes;
    }

    public AbstractButton getBtnAjouterEmploye() {
        return btnAjouterEmploye;
    }

    public AbstractButton getBtnSupprimerEmploye() {
        return btnSupprimerEmploye;
    }
}