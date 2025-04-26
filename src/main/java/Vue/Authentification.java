package Vue;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class Authentification extends JFrame {
    public Authentification() {
        setTitle("Authentification - Serenity Hotel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Image de fond
        ImageIcon backgroundImage = new ImageIcon("resources/20608.jpg");
        Image image = backgroundImage.getImage();
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel codePinLabel = new JLabel("Authentification requise");
        codePinLabel.setFont(new Font("Georgia", Font.BOLD, 25));
        codePinLabel.setForeground(Color.DARK_GRAY);
        codePinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);



        JPasswordField password = new JPasswordField(10);
        password.setMaximumSize(new Dimension(200, 40));
        password.setHorizontalAlignment(JTextField.CENTER);
        password.setFont(new Font("SansSerif", Font.PLAIN, 18));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton validerBtn = new JButton("Valider");
        JButton retourBtn = new JButton("Retour");
        validerBtn.setPreferredSize(new Dimension(120, 40));
        retourBtn.setPreferredSize(new Dimension(120, 40));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(retourBtn);
        buttonPanel.add(validerBtn);

        // Panel principal central
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(codePinLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(password);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(buttonPanel);

        // Position centrale sur fond
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(centerPanel, gbc);

        setContentPane(backgroundPanel);
    }


}