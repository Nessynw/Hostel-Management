package Vue;

import Model.Hotel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Authentification extends JDialog {
    private Runnable onSuccess;
    private Hotel hotel;
    private JTextField txtIdentifiant;
    private JPasswordField txtMotDePasse;
    private static final Color MAIN_COLOR = new Color(18, 11, 61);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);

    public Authentification(Runnable onSuccess, Hotel hotel) {
        this.onSuccess = onSuccess;
        this.hotel = hotel;
        
        // Configuration de base du dialogue
        setTitle("Authentification");
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        
        // Configuration du panneau principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(MAIN_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Création des composants

        JLabel lblMotDePasse = new JLabel("Mot de passe :");
        lblMotDePasse.setForeground(TEXT_COLOR);
        
        txtMotDePasse = new JPasswordField(15);
        JButton btnConnexion = new JButton("Se connecter");
        
        // Ajout des composants

        
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(lblMotDePasse, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtMotDePasse, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(btnConnexion, gbc);
        
        // Action du bouton
        btnConnexion.addActionListener(e -> verifierMotDePasse());
        
        // Action sur la touche Entrée
        txtMotDePasse.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    verifierMotDePasse();
                }
            }
        });
        
        setContentPane(mainPanel);
    }
    
    private void verifierMotDePasse() {
        String motDePasse = new String(txtMotDePasse.getPassword());
        
        if (motDePasse.equals("1234")) {
            dispose();
            if (onSuccess != null) {
                onSuccess.run();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "mot de passe incorrect",
                    "Erreur d'authentification",
                    JOptionPane.ERROR_MESSAGE);
            txtMotDePasse.setText("");
        }
    }
}