package Vue;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controler.*;

public class Authentification extends JFrame {
    private static final Color main_color = new Color(26, 31, 75);  // Couleur de base

    private char[] codepin;
    private JButton validerBtn;
    private JPasswordField password;
    private Runnable onSuccess;  // Callback pour signaler que l'authentification a réussi

    public Authentification(Runnable onSuccess) {

        this.onSuccess = onSuccess;  // On récupère le callback

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Configuration de la fenêtre principale
        setTitle("Authentification - Blue Castel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Créer un panneau pour l'arrière-plan
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setBackground(main_color);
        // Créer les composants principaux
        JLabel codePinLabel = new JLabel("Authentification requise");
        codePinLabel.setFont(new Font("Georgia", Font.BOLD, 25));
        codePinLabel.setForeground(new Color(176, 176, 176));  // Gris clair pour le texte

        codePinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        password = new JPasswordField(10);
        password.setMaximumSize(new Dimension(200, 40));
        password.setHorizontalAlignment(JTextField.CENTER);
        password.setFont(new Font("SansSerif", Font.PLAIN, 18));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);

        validerBtn = new JButton("Valider");
        JButton retourBtn = new JButton("Retour");
        validerBtn.setPreferredSize(new Dimension(120, 40));
        retourBtn.setPreferredSize(new Dimension(120, 40));

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(retourBtn);
        buttonPanel.add(validerBtn);

        // Panel principal pour organiser les éléments
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(codePinLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(password);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(buttonPanel);

        // Ajouter le centrePanel au GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(centerPanel, gbc);

        // Ajouter le backgroundPanel à la fenêtre
        setContentPane(backgroundPanel);

        // Action du bouton Valider
        validerBtnControler b = new validerBtnControler(password,onSuccess,this);
        validerBtn.addActionListener(b);

        // Action du bouton Retour
        retourBtn.addActionListener(new retourBtnControler(() -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll();
            topFrame.getContentPane().add(new InterfacePersonnel(topFrame));
            topFrame.revalidate();
            topFrame.repaint();
        }));
    }

    public char[] getCodePin() {
        return codepin;
    }
}
