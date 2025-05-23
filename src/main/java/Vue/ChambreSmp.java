package Vue;

import Model.Chambre;
import Model.ChambreSimple;
import Model.Hotel;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ChambreSmp extends JFrame {
    private static final Color backgroundColor = new Color(18, 11, 61);
    private JTable chamberTable;

    public ChambreSmp(Hotel hotel, LocalDate debut, LocalDate fin) {
        setTitle("Chambres Simples Disponibles");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Informations des Chambres Simples", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(titleLabel, BorderLayout.NORTH);


        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Num", "Étage", "Prix (€)", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        for (Chambre c : hotel.getListChambre()) {
            if (c.getType().equals("Simple") && c.isAvailable(debut, fin)) {
                model.addRow(new Object[]{
                    c.getNum_chambre(),
                    c.getNum_etage(),
                    c.getPrix() + "€",
                    "Libre"
                });
            }
        }


        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "Désolé, aucune chambre simple n'est disponible pour ces dates.",
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
                        JOptionPane.showMessageDialog(ChambreSmp.this, form,
                                "Nouveau Client & Réservation", JOptionPane.PLAIN_MESSAGE);

                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(ChambreSmp.this,
                                "Cette chambre n'est plus disponible.", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        // Rafraîchir la liste
                        dispose();
                        new ChambreSmp(hotel, debut, fin).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(ChambreSmp.this,
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
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground("Libre".equals(value) ? AppColors.libreColor : AppColors.occupeeColor);
                c.setForeground(Color.WHITE);
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });
    }
}