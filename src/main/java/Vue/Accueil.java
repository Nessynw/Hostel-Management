package Vue;

import Model.*;
import javax.swing.*;
import java.awt.*;
import Controler.*; ;

public class Accueil extends JPanel {
    private JPanel mainPanel;

    public Accueil(JFrame parentFrame, Hotel hotel) {
        this.setLayout(new BorderLayout());

        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(300, getMaximumSize().height));
        sidebar.setBackground(AppColors.SIDE_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel nomHotel = new JLabel("<html><div style='text-align: center;'>" +
                "<br>" + "<span style='color: rgb(255,255,255);'>Hôtel </span><br>" +
                "<span style='color: rgb(171,169,192);'>Blue Castel </span>" +
                "</div></html>", SwingConstants.CENTER);
        nomHotel.setFont(new Font("Serif", Font.BOLD, 30));
        sidebar.add(nomHotel, gbc);

        this.add(sidebar, BorderLayout.WEST);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(AppColors.MAIN_COLOR);
        this.add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.insets = new Insets(10, 10, 10, 10);
        buttonConstraints.anchor = GridBagConstraints.CENTER;

        StyledButton clientButton = new StyledButton("Client");
        StyledButton personnelButton = new StyledButton("Personnel");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.setOpaque(false);

        buttonPanel.add(clientButton);
        buttonPanel.add(personnelButton);

        mainPanel.add(buttonPanel, buttonConstraints);


        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.gridx = 0;
        textConstraints.gridy = 0;
        textConstraints.insets = new Insets(20, 0, 10, 0);
        textConstraints.anchor = GridBagConstraints.CENTER;

        JLabel welcomeLabel = new JLabel("Welcome to Blue Castel");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 60));
        welcomeLabel.setForeground(new Color(176, 176, 176));
        mainPanel.add(welcomeLabel, textConstraints);

        ClientButtonControleur b = new ClientButtonControleur(parentFrame, hotel);
        clientButton.addActionListener(b);

        PersonnelButtonControleur personnelController = new PersonnelButtonControleur(parentFrame, hotel);
        personnelButton.addActionListener(personnelController);
    }
}