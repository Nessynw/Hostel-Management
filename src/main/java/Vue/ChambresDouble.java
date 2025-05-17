package Vue;

import Model.Chambre;
import Model.ChambreDouble;
import Model.Hotel;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class ChambresDouble extends JFrame {
    private static final Color backgroundColor = new Color(18, 11, 61);

    private JTable chamberTable;

    public ChambresDouble(Hotel hotel, LocalDate debut, LocalDate fin) {
        setTitle("Chambres Doubles Disponibles");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Informations des Chambres Doubles", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Créer le modèle de table avec uniquement les chambres doubles disponibles
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Num", "Étage", "Prix (€)", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Seule la colonne "Action" est éditable
            }
        };

        // Ajouter uniquement les chambres doubles disponibles
        for (Chambre c : hotel.getListChambre()) {
            if (c.getType().equals("Double") && c.isAvailable(debut, fin)) {
                model.addRow(new Object[]{
                    c.getNum_chambre(),
                    c.getNum_etage(),
                    c.getPrix() + "€",
                    "libre"
                });
            }
        }

        // Si aucune chambre n'est disponible
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "Désolé, aucune chambre double n'est disponible pour ces dates.",
                "Pas de disponibilité",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        chamberTable = new JTable(model);
        styleTable(chamberTable);

        JScrollPane scrollPane = new JScrollPane(chamberTable);
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        southPanel.setBackground(backgroundColor);
        JButton reserveButton = new JButton("Réserver une chambre");
        reserveButton.setBackground(AppColors.MAIN_COLOR);
        reserveButton.setForeground(Color.WHITE);
        reserveButton.setFont(new Font("Arial", Font.BOLD, 14));
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = chamberTable.getSelectedRow();
                if (row >= 0) {
                    int num = (int) chamberTable.getValueAt(row, 0);
                    Chambre chambre = hotel.getChambreParNumero(num);
                    if (chambre != null && chambre.isAvailable(debut, fin)) {
                        NewClient form = new NewClient(hotel, chambre, debut, fin);
                        JOptionPane.showMessageDialog(ChambresDouble.this, form,
                                "Nouveau Client & Réservation", JOptionPane.PLAIN_MESSAGE);
                        // Fermer la fenêtre après la réservation
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(ChambresDouble.this,
                                "Cette chambre n'est plus disponible.", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        // Rafraîchir la liste
                        dispose();
                        new ChambresDouble(hotel, debut, fin).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(ChambresDouble.this,
                            "Veuillez sélectionner une chambre.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        southPanel.add(reserveButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void styleTable(JTable table) {
        table.setBackground(backgroundColor);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setBackground(backgroundColor);
        table.getTableHeader().setForeground(new Color(29, 42, 97, 236));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Style spécial pour la colonne "Action"
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                         boolean isSelected, boolean hasFocus,
                                                         int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(AppColors.libreColor);  // Toutes les chambres affichées sont disponibles
                c.setForeground(Color.WHITE);
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });
    }
}