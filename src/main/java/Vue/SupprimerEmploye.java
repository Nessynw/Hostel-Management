package Vue;

import java.awt.*;
import javax.swing.*;
import Controler.retourBtnControler;
import Model.*;
import java.util.ArrayList;
import java.util.List;

public class SupprimerEmploye extends JPanel {
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
        setBackground(AppColors.MAIN_COLOR);
        

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(AppColors.MAIN_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        

        JLabel lblEmploye = new JLabel("Sélectionner l'employé :");
        lblEmploye.setForeground(AppColors.TEXT_COLOR);
        lblEmploye.setFont(new Font("Arial", Font.BOLD, 14));
        

        comboEmployes = new JComboBox<>();
        comboEmployes.setPreferredSize(new Dimension(300, 40));
        comboEmployes.setFont(new Font("Arial", Font.PLAIN, 14));
        comboEmployes.setBackground(new Color(30, 30, 70));
        comboEmployes.setForeground(Color.WHITE);
        ((JComponent) comboEmployes.getRenderer()).setOpaque(true);
        

        btnSupprimer = new StyledButton("Supprimer");
        btnRetour = new StyledButton("Retour");
        

        Dimension buttonSize = new Dimension(200, 40);
        btnSupprimer.setPreferredSize(buttonSize);
        btnRetour.setPreferredSize(buttonSize);
        

        gbc.insets = new Insets(20, 20, 20, 20);
        

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblEmploye, gbc);
        

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 30, 20); // Plus d'espace en bas
        mainPanel.add(comboEmployes, gbc);
        

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(AppColors.MAIN_COLOR);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnRetour);
        

        gbc.gridy = 2;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        

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

        Object[] options = {"Oui", "Non"};
        
        int confirmation = JOptionPane.showOptionDialog(
            this,
            "Êtes-vous sûr de vouloir supprimer cet employé ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]
        );
        
        if (confirmation == 0) {
            listeEmployes.remove(selectedEmploye);
            comboEmployes.removeItem(selectedEmploye);

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