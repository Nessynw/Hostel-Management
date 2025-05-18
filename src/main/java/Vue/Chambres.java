package Vue;

import Controler.ControlerReservation;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Chambres extends JPanel {
    private JPanel mainPanel;
    private Hotel hotel;
    private static final Color hoverColor = new Color(58, 90, 153);


    public Chambres(Hotel hotel) {
        this.hotel = hotel;
        this.setBackground(AppColors.MAIN_COLOR);
        this.setLayout(new GridBagLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 20, 20));
        mainPanel.setBackground(AppColors.MAIN_COLOR);

        JPanel simplePanel = createChambrePanel(
                "Chambre Simple",
                new ImageIcon("src/main/resources/simpleRoom.jpg"),
                "<html>Une chambre simple confortable pour une personne.<br>Idéale pour les séjours d'affaires ou les voyageurs seuls.</html>"
        );

        JPanel doublePanel = createChambrePanel(
                "Chambre Double",
                new ImageIcon("src/main/resources/doubleRoom.jpg"),
                "<html>Une chambre double spacieuse pour deux personnes.<br>Parfaite pour les couples ou les amis.</html>"
        );

        mainPanel.add(simplePanel);
        mainPanel.add(doublePanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(AppColors.MAIN_COLOR);
        scrollPane.getViewport().setBackground(AppColors.MAIN_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        this.add(scrollPane, gbc);
    }

    private JPanel createChambrePanel(String type, ImageIcon roomImage, String descriptionText) {
        JPanel chambrePanel = new JPanel();
        chambrePanel.setLayout(new BoxLayout(chambrePanel, BoxLayout.Y_AXIS));
        chambrePanel.setBackground(AppColors.MAIN_COLOR);
        chambrePanel.setBorder(BorderFactory.createLineBorder(new Color(29, 42, 97), 0));

        JLabel typeLabel = new JLabel(type, SwingConstants.CENTER);
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        typeLabel.setForeground(Color.WHITE);
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        Image image = roomImage.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        roomImage = new ImageIcon(image);

        JLabel imageLabel = new JLabel(roomImage);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel description = new JLabel(descriptionText, SwingConstants.CENTER);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setForeground(Color.WHITE);
        description.setFont(new Font("Arial", Font.PLAIN, 12));

        chambrePanel.add(Box.createVerticalStrut(10));
        chambrePanel.add(typeLabel);
        chambrePanel.add(Box.createVerticalStrut(10));
        chambrePanel.add(imageLabel);
        chambrePanel.add(Box.createVerticalStrut(10));
        chambrePanel.add(description);
        chambrePanel.add(Box.createVerticalStrut(10));

        chambrePanel.addMouseListener( new ControlerReservation(hotel, chambrePanel,type,hoverColor));



        return chambrePanel;
    }
}
