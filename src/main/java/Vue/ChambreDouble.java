package Vue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ChambreDouble extends JFrame {
    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color fieldColor = new Color(58, 90, 153); // Hover color
    private static final Color textColor = Color.WHITE;

    public ChambreDouble() {
        setTitle("Chambres Double");
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(main_color);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création du tableau
        String[] columnNames = {"Numéro", "Étage", "Prix (€)", "Disponibilité"};
        Object[][] data = {
                {"320", "1", "130", "Disponible"},
                {"323", "1", "130", "Occupée"},
                {"250", "2", "100", "Disponible"},
                {"450", "2", "150", "Disponible"},
                {"300", "3", "100", "Occupée"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setBackground(fieldColor);
        table.setForeground(textColor);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(main_color);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(scrollPane);

        setVisible(true);
    }
}

