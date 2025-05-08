package Vue;

import java.awt.*;
import javax.swing.*;
import Controler.retourBtnControler;
import Model.*;
import java.util.ArrayList;
import java.util.List;

public class SupprimerEmploye extends JPanel {
    private static final Color MAIN_COLOR = new Color(18, 11, 61);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private JComboBox<Employe> comboEmployes;
    private JFrame parentFrame;
    private List<Employe> listeEmployes;
    private StyledButton btnSupprimer;
    private StyledButton btnRetour;
    private Hotel hotel;
    
    public SupprimerEmploye(JFrame frame, Hotel hotel) {
        this.parentFrame = frame;
        this.hotel = hotel;
        this.listeEmployes = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(MAIN_COLOR);
        
        // Création du panneau principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(MAIN_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Label pour sélectionner l'employé
        JLabel lblEmploye = new JLabel("Sélectionner l'employé :");
        lblEmploye.setForeground(TEXT_COLOR);
        lblEmploye.setFont(new Font("Arial", Font.BOLD, 14));
        
        // ComboBox avec taille personnalisée
        comboEmployes = new JComboBox<>();
        comboEmployes.setPreferredSize(new Dimension(300, 40));
        comboEmployes.setFont(new Font("Arial", Font.PLAIN, 14));
        comboEmployes.setBackground(new Color(30, 30, 70));
        comboEmployes.setForeground(Color.WHITE);
        ((JComponent) comboEmployes.getRenderer()).setOpaque(true);
        
        // Boutons avec taille réduite
        btnSupprimer = new StyledButton("Supprimer");
        btnRetour = new StyledButton("Retour");
        
        // Définir la taille des boutons
        Dimension buttonSize = new Dimension(200, 40);
        btnSupprimer.setPreferredSize(buttonSize);
        btnRetour.setPreferredSize(buttonSize);
        
        // Configuration du layout
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // Ajout du label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblEmploye, gbc);
        
        // Ajout de la combobox
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 30, 20); // Plus d'espace en bas
        mainPanel.add(comboEmployes, gbc);
        
        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(MAIN_COLOR);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnRetour);
        
        // Ajout du panel de boutons
        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Ajout des listeners (le reste du code reste identique)
        btnSupprimer.addActionListener(e -> supprimerEmploye());
        btnRetour.addActionListener(new retourBtnControler(() -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new InterfacePersonnel(parentFrame, hotel));
            parentFrame.revalidate();
            parentFrame.repaint();
        }));
    }
    
    private void supprimerEmploye() {
        Employe selectedEmploye = (Employe) comboEmployes.getSelectedItem();
        if (selectedEmploye != null) {
        // Créer un array d'options en français
        Object[] options = {"Oui", "Non"};
        
        int confirmation = JOptionPane.showOptionDialog(
            this,
            "Êtes-vous sûr de vouloir supprimer cet employé ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1] // option "Non" par défaut
        );
        
        if (confirmation == 0) { // 0 correspond à "Oui"
            listeEmployes.remove(selectedEmploye);
            comboEmployes.removeItem(selectedEmploye);
            // Supprimer l'employé de l'hôtel aussi
            hotel.getListEmploye().remove(selectedEmploye);
            
            JOptionPane.showMessageDialog(
                this,
                "L'employé a été supprimé avec succès !",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    } else {
        JOptionPane.showMessageDialog(
            this,
            "Veuillez sélectionner un employé à supprimer.",
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    public void setListeEmployes(List<Employe> employes) {
        this.listeEmployes = employes;
        comboEmployes.removeAllItems();
        for (Employe employe : employes) {
            comboEmployes.addItem(employe);
        }
    }
}