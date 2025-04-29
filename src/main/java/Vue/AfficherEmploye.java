package Vue;

import Controler.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AfficherEmploye extends JPanel {
    private JTable tableEmployes;
    private JButton btnRetour;
    private JFrame parentFrame;

    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color side_COLOR = new Color(9, 0, 91);    // Couleur du côté (sidebar)

    public AfficherEmploye(JFrame frame) {
        this.parentFrame = frame;

        setLayout(new BorderLayout());
        setBackground(main_color);

        String[] colonnes = {"Id", "Nom", "Prenom", "Email", "Poste", "Salaire", "Tâche", "Intervention"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        tableEmployes = new JTable(model);

        // Style du tableau
        tableEmployes.setBackground(side_COLOR);
        tableEmployes.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(tableEmployes);
        add(scrollPane, BorderLayout.CENTER);

        // Bouton de retour
        btnRetour = new JButton("Retour");
        btnRetour.setPreferredSize(new Dimension(100, 40));
        JPanel panelRetour = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRetour.setBackground(main_color);
        panelRetour.add(btnRetour);
        add(panelRetour, BorderLayout.SOUTH);

        // Action du bouton Retour
        btnRetour.addActionListener(new retourBtnControler(() -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new InterfacePersonnel(parentFrame));
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
