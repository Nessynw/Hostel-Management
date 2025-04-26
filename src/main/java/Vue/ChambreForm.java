package Vue;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChambreForm extends JPanel {
    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color hoverColor = new Color(58, 90, 153); // Hover color
    private JPanel mainPanel;
    private Hotel hotel;

    public ChambreForm(Hotel hotel) {
        this.hotel = hotel;
        this.setBackground(main_color);
        this.setLayout(new GridBagLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 20, 20)); // 1 ligne, 2 colonnes espacées
        mainPanel.setBackground(main_color);

        // Créer les panneaux de chambres
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

        // Ajouter les panels au mainPanel
        mainPanel.add(simplePanel);
        mainPanel.add(doublePanel);

        // Ajouter un scroll si la fenêtre est trop petite
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null); // Pas de bordure moche autour du scroll
        scrollPane.setBackground(main_color);
        scrollPane.getViewport().setBackground(main_color);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        this.add(scrollPane, gbc);
    }

    private JPanel createChambrePanel(String type, ImageIcon roomImage, String descriptionText) {
        JPanel chambrePanel = new JPanel();
        chambrePanel.setLayout(new BoxLayout(chambrePanel, BoxLayout.Y_AXIS));
        chambrePanel.setBackground(main_color);
        chambrePanel.setBorder(BorderFactory.createLineBorder(new Color(29, 42, 97), 0)); // Contour bleu

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

        chambrePanel.add(Box.createVerticalStrut(10)); // petit espace
        chambrePanel.add(typeLabel);
        chambrePanel.add(Box.createVerticalStrut(10));
        chambrePanel.add(imageLabel);
        chambrePanel.add(Box.createVerticalStrut(10));
        chambrePanel.add(description);
        chambrePanel.add(Box.createVerticalStrut(10));

        // Rendre cliquable
        chambrePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (type.equals("Chambre Simple")) {
                    afficherDetailsChambreSimple();
                } else if (type.equals("Chambre Double")) {
                    afficherDetailsChambreDouble();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                chambrePanel.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                chambrePanel.setBackground(main_color);
            }
        });

        return chambrePanel;
    }

    private void afficherDetailsChambreSimple() {
        ChambreSimple chambreSimpleWindow = new ChambreSimple();
        chambreSimpleWindow.setVisible(true);
    }


    private void afficherDetailsChambreDouble() {
        ChambreDouble chambreDoubleWindow = new ChambreDouble();
        chambreDoubleWindow.setVisible(true);    }
}
