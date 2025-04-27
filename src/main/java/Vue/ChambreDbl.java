package Vue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;
import Model.*;

public class ChambreDbl extends JFrame {
    private static final Color main_color = new Color(18, 11, 61);  // Couleur de base
    private static final Color fieldColor = new Color(58, 90, 153); // Hover color
    private static final Color textColor = Color.WHITE;

    public ChambreDbl(Hotel hotel) {
        setTitle("Chambres Double");
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(main_color);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Colonnes
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Numéro");
        columnNames.add("Étage");
        columnNames.add("Prix (€)");
        columnNames.add("Disponibilité");

        // Données
        Vector<Vector<Object>> data = new Vector<>();

        for (Chambre chambre : hotel.getChambres()) {
            // Vérifier si c'est une chambre double
            if (chambre instanceof ChambreDouble) {
                Vector<Object> row = new Vector<>();
                row.add(chambre.getNum_chambre());
                row.add(chambre.getNum_etage());
                row.add(chambre.getTarif());
                row.add(chambre.isAvailableNow() ? "Disponible" : "Occupée");  // Utilisation de ta méthode existante
                data.add(row);
            }
        }

        // Création du modèle
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

