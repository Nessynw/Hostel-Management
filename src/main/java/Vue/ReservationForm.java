package Vue;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;

public class ReservationForm extends JPanel {
    private static final Color main_color = new Color(18, 11, 61);  // Couleur principale
    private static final Color panel_color = new Color(35, 30, 67); // Couleur secondaire
    private static final Color text_color = Color.WHITE;            // Couleur du texte

    private JTable chambreTable; // Déclaration de la table

    public ReservationForm() {
        setLayout(new GridBagLayout());
        setBackground(main_color);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Titre
        JLabel titleLabel = new JLabel("Réservation d'une chambre");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(text_color);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Panneau de gauche (Liste des chambres)
        JPanel chambreList = new JPanel(new BorderLayout());
        chambreList.setBackground(panel_color);
        chambreList.setPreferredSize(new Dimension(500, 600));

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5; // Donne 50% de l'espace horizontal
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(chambreList, gbc);

        // Créer la table des chambres
        String[] columns = {"ID", "Type", "Étage", "État"};
        Object[][] data = {
                {101, "Simple", 1, "Libre"},
                {102, "Double", 1, "Occupée"},
                {103, "Suite", 2, "Libre"},
                {104, "Simple", 2, "Occupée"},
                {105, "Suite", 3, "Libre"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêcher l'édition
            }
        };

        chambreTable = new JTable(model);
        chambreTable.setBackground(panel_color);
        chambreTable.setForeground(Color.WHITE);
        chambreTable.setFont(new Font("Arial", Font.PLAIN, 14));
        chambreTable.setRowHeight(30);
        chambreTable.getTableHeader().setBackground(main_color);
        chambreTable.getTableHeader().setForeground(Color.WHITE);
        chambreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Centrer les cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < chambreTable.getColumnCount(); i++) {
            chambreTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(chambreTable);
        scrollPane.getViewport().setBackground(panel_color);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        chambreList.add(scrollPane, BorderLayout.CENTER);

        // Panneau de droite (Calendriers)
        JPanel chambreDisp = new JPanel(new GridBagLayout());
        chambreDisp.setBackground(panel_color);
        chambreDisp.setPreferredSize(new Dimension(500, 600));

        // ATTENTION ici : poids à droite aussi
        gbc.gridx = 1;
        gbc.weightx = 0.5; // égal à chambreList pour que les deux panneaux partagent l'espace
        add(chambreDisp, gbc);

        // Contraintes internes pour chambreDisp
        GridBagConstraints dispGbc = new GridBagConstraints();
        dispGbc.insets = new Insets(10, 10, 10, 10);
        dispGbc.fill = GridBagConstraints.NONE;
        dispGbc.anchor = GridBagConstraints.CENTER;
        dispGbc.gridx = 0;
        dispGbc.weightx = 1.0;

        // Label Date Début
        JLabel dateDebutLabel = new JLabel("Date Début");
        dateDebutLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateDebutLabel.setForeground(text_color);
        dispGbc.gridy = 0;
        chambreDisp.add(dateDebutLabel, dispGbc);

        // Calendrier Date Début
        JCalendar datedebut = createStyledCalendar();
        dispGbc.gridy = 1;
        chambreDisp.add(datedebut, dispGbc);

        // Label Date Fin
        JLabel dateFinLabel = new JLabel("Date Fin");
        dateFinLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateFinLabel.setForeground(text_color);
        dispGbc.gridy = 2;
        chambreDisp.add(dateFinLabel, dispGbc);

        // Calendrier Date Fin
        JCalendar datefin = createStyledCalendar();
        dispGbc.gridy = 3;
        chambreDisp.add(datefin, dispGbc);
    }

    // Créer un JCalendar stylisé
    private JCalendar createStyledCalendar() {
        JCalendar calendar = new JCalendar(Locale.FRANCE);
        calendar.setPreferredSize(new Dimension(200, 300));
        calendar.setBackground(new Color(45, 40, 77));
        calendar.getDayChooser().setBackground(new Color(45, 40, 77));
        calendar.getDayChooser().setForeground(Color.WHITE);
        calendar.getMonthChooser().setBackground(new Color(45, 40, 77));
        calendar.getMonthChooser().setForeground(Color.WHITE);
        calendar.getYearChooser().setBackground(new Color(45, 40, 77));
        calendar.getYearChooser().setForeground(Color.WHITE);
        calendar.setForeground(Color.WHITE);
        return calendar;
    }
}
