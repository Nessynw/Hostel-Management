package Vue;

import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class FacturePanel extends JPanel {
    private JComboBox<Sejour> sejourBox;
    private JTable table;
    private JLabel sousTotalLbl, tvaLbl, totalLbl;

    public FacturePanel(Hotel hotel) {
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_COLOR);

        // → Choix du séjour
        sejourBox = new JComboBox<>(new Vector<>(hotel.getListSejour()));
        sejourBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                if (v instanceof Sejour) {
                    Sejour sej = (Sejour) v;
                    setText("#" + sej.getRes().getId_res() + " - Chambre " + sej.getChambre().getNumero());
                }
                return this;
            }
        });
        sejourBox.addActionListener(e -> refreshTable());
        add(sejourBox, BorderLayout.NORTH);

        // → Tableau facturation
        String[] cols = {"Desc.", "Qté", "PU (€)", "Total (€)"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // → Totaux
        JPanel bottom = new JPanel(new GridLayout(3, 2));
        bottom.setBackground(AppColors.MAIN_COLOR);
        sousTotalLbl = new JLabel();
        tvaLbl = new JLabel();
        totalLbl = new JLabel();
        bottom.add(new JLabel("Sous‑total :", SwingConstants.RIGHT));
        bottom.add(sousTotalLbl);
        bottom.add(new JLabel("TVA (10%) :", SwingConstants.RIGHT));
        bottom.add(tvaLbl);
        bottom.add(new JLabel("Total :", SwingConstants.RIGHT));
        bottom.add(totalLbl);
        add(bottom, BorderLayout.SOUTH);

        // Initialisation
        refreshTable();
    }

    private void refreshTable() {
        Sejour s = (Sejour) sejourBox.getSelectedItem();
        DefaultTableModel m = (DefaultTableModel) table.getModel();
        m.setRowCount(0);
        if (s == null) return;

        // Chambre
        long nuits = java.time.temporal.ChronoUnit.DAYS.between(s.getDate_deb(), s.getDate_fin());
        double pu = s.getChambre().getPrix();
        double totChambre = pu * nuits;
        m.addRow(new Object[]{
                "Chambre " + s.getChambre().getNumero() + " (" + nuits + " nuits)",
                1,
                pu,
                totChambre
        });

        // Consommations
        for (Consommation c : s.getListCons()) {
            double totalC = c.getProduit().getPrix() * c.getQuantite();
            m.addRow(new Object[]{
                    c.getProduit().getNom(),
                    c.getQuantite(),
                    c.getProduit().getPrix(),
                    totalC
            });
        }


        double sous = s.calculerPrixTotal();
        double tva = sous * 0.10;
        double tot = sous + tva;
        sousTotalLbl.setText(String.format("%.2f€", sous));
        tvaLbl.setText(String.format("%.2f€", tva));
        totalLbl.setText(String.format("%.2f€", tot));
    }

}