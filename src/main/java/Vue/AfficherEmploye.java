package Vue;

import Controler.*;
import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class AfficherEmploye extends JPanel {
    private JTable tableEmployes;
    private JButton btnRetour;
    private JFrame parentFrame;
    private Hotel hotel;


    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color side_COLOR = new Color(9, 0, 91);    // Couleur du côté (sidebar)

    public AfficherEmploye(JFrame frame, Hotel hotel) {
        this.parentFrame = frame;
        this.hotel = hotel;

        setLayout(new BorderLayout());
        setBackground(main_color);

        String[] colonnes = {"Nom", "Prénom", "Email", "Adresse", "Poste", "Salaire"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        tableEmployes = new JTable(model);

        // Style du tableau
        tableEmployes.setBackground(side_COLOR);
        tableEmployes.setForeground(Color.WHITE);
        tableEmployes.setFont(new Font("Arial", Font.PLAIN, 14));

        // Remplir le tableau avec les employés
        Vector<Employe> employes = hotel.getListEmploye();
        if (employes != null) {
            for (Employe employe : employes) {
                String poste = "";
                if (employe instanceof Receptionniste) {
                    poste = "Réceptionniste";
                } else if (employe instanceof AgentE) {
                    poste = "Agent d'entretien";
                } else {
                    poste = "Employé";
                }

                Object[] row = {
                    employe.getNom(),
                    employe.getPrenom(),
                    employe.getEmail(),
                    employe.getAdresse(),
                    poste,
                    String.format("%.2f €", employe.getSalaire())
                };
                model.addRow(row);
            }
        }

        // Ajuster la taille des colonnes
        tableEmployes.getColumnModel().getColumn(0).setPreferredWidth(100); // Nom
        tableEmployes.getColumnModel().getColumn(1).setPreferredWidth(100); // Prénom
        tableEmployes.getColumnModel().getColumn(2).setPreferredWidth(150); // Email
        tableEmployes.getColumnModel().getColumn(3).setPreferredWidth(200); // Adresse
        tableEmployes.getColumnModel().getColumn(4).setPreferredWidth(100); // Poste
        tableEmployes.getColumnModel().getColumn(5).setPreferredWidth(100); // Salaire

        // Rendre le tableau non éditable
        tableEmployes.setDefaultEditor(Object.class, null);

        // Ajouter le tableau dans un ScrollPane
        JScrollPane scrollPane = new JScrollPane(tableEmployes);
        add(scrollPane, BorderLayout.CENTER);

        // Ajouter le bouton retour
        btnRetour = new StyledButton("Retour");
        JPanel panelRetour = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRetour.setBackground(main_color);
        panelRetour.add(btnRetour);
        add(panelRetour, BorderLayout.SOUTH);

        // Action du bouton Retour
        btnRetour.addActionListener(new retourBtnControler(() -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new InterfacePersonnel(parentFrame, hotel));
            parentFrame.revalidate();
            parentFrame.repaint();
        }));
    }

    public JTable getTableEmployes() {
        return tableEmployes;
    }

    public JButton getBtnRetour() {
        return btnRetour;
    }

}