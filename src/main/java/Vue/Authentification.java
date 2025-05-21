package Vue;

import Model.Hotel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Authentification extends JDialog {
    private Runnable onSuccess;
    private Hotel hotel;
    private JPasswordField txtMotDePasse;

    public Authentification(Runnable onSuccess, Hotel hotel) {
        this.onSuccess = onSuccess;
        this.hotel = hotel;
        
        setTitle("Authentification");
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(AppColors.MAIN_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        

        JLabel lblMotDePasse = new JLabel("Mot de passe :");
        lblMotDePasse.setForeground(AppColors.TEXT_COLOR);
        
        txtMotDePasse = new JPasswordField(15);
        JButton btnConnexion = new JButton("Se connecter");
        

        
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(lblMotDePasse, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtMotDePasse, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(btnConnexion, gbc);
        //Passer à la méthode vérifierMdp quand je clique sur le bouton connecter
        btnConnexion.addActionListener(e -> verifierMotDePasse());
        //passer à la méthode vérifierMdp quand je clique sur la toucher Entrer dans clavier
        txtMotDePasse.addKeyListener(new KeyAdapter() {
        //KeyAdapter est une classe d'adaptation qui permet donc d'écouter les événements du clavier
            //En gros pour écouter les clavier events on utilise keyListener avec trois méthodes Key Pressed, Keyreleased,KeyTyped, ces trois sont implémentés dans KeyAdapter
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) //Vk_ENTER: touche entré du clavier
                     {
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