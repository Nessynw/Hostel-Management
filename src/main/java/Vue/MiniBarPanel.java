package Vue;

import Model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MiniBarPanel extends JPanel {
    private JComboBox<Sejour> sejourBox;
    private JLabel totalLabel;
    private Map<Produit, JSpinner> quantitesSpinners = new HashMap<>();
    private Hotel hotel;

    public MiniBarPanel(Hotel hotel) {
        this.hotel = hotel;
        setBackground(AppColors.MAIN_COLOR);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 580));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(AppColors.MAIN_COLOR);
        mainPanel.setBorder(new EmptyBorder(15,20,20,20));
        add(mainPanel, BorderLayout.CENTER);


        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.NONE;
        JLabel lblSej = new JLabel("Séjour");
        lblSej.setForeground(Color.WHITE);
        lblSej.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainPanel.add(lblSej, gbc);


        gbc.gridy = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        Vector<Sejour> sejours = new Vector<>(hotel.getListSejour());
        sejours.add(0, null);  // première option vide
        sejourBox = new JComboBox<>(sejours);
        sejourBox.setOpaque(true);
        sejourBox.setForeground(Color.WHITE);
        sejourBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sejourBox.setBorder(BorderFactory.createLineBorder(new Color(50,50,100),1,true));
        sejourBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,int index,boolean isSelected,boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Sejour) {
                    Sejour s = (Sejour) value;
                    setText("#" + s.getRes().getId_res() + " - Chambre " + s.getChambre().getNumero());
                } else {
                    setText("Sélectionner un séjour");
                }
                return this;
            }
        });
        sejourBox.addActionListener(e -> updateTotal());
        mainPanel.add(sejourBox, gbc);


        JPanel miniBarPanel = new JPanel();
        miniBarPanel.setBackground(new Color(20,20,70));
        miniBarPanel.setLayout(new BoxLayout(miniBarPanel, BoxLayout.Y_AXIS));
        miniBarPanel.setBorder(new EmptyBorder(15,15,15,15));
        miniBarPanel.add(Box.createRigidArea(new Dimension(0,10)));
        JLabel title = new JLabel("Articles du mini bar");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        miniBarPanel.add(title);
        miniBarPanel.add(Box.createRigidArea(new Dimension(0,15)));
        for (Produit p: hotel.getListProduit()) {
            miniBarPanel.add(createProduitRow(p));
            miniBarPanel.add(Box.createRigidArea(new Dimension(0,10)));
        }
        JScrollPane scroll = new JScrollPane(miniBarPanel);
        scroll.setBackground(AppColors.Box_Color);
        scroll.setOpaque(true);
        gbc.gridy = 2; gbc.weightx = 1; gbc.weighty = 1; gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scroll, gbc);

        // Footer: total + bouton valider (inchangé)
        gbc.gridy = 3; gbc.weighty = 0; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(AppColors.Box_Color);
        footer.setBorder(new EmptyBorder(10,15,10,15));
        JPanel totalP = new JPanel(new BorderLayout());
        totalP.setBackground(AppColors.Box_Color);
        totalP.setBorder(new EmptyBorder(0,15,10,15));
        JLabel lblTotal = new JLabel("Total");
        lblTotal.setForeground(Color.WHITE);
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalLabel = new JLabel("0.00€");
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalP.add(lblTotal, BorderLayout.WEST);
        totalP.add(totalLabel, BorderLayout.EAST);
        JButton btnValider = new JButton("Valider");
        btnValider.setBackground(new Color(86,112,178));
        btnValider.setForeground(Color.WHITE);
        btnValider.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnValider.setBorder(BorderFactory.createLineBorder(new Color(50,50,100),1,true));
        btnValider.setPreferredSize(new Dimension(100,30));
        btnValider.addActionListener(e -> {
            Sejour s = (Sejour) sejourBox.getSelectedItem();
            if (s == null) return;
            for (Map.Entry<Produit,JSpinner> entry : quantitesSpinners.entrySet()) {
                int q = (int) entry.getValue().getValue();
                if (q > 0) {
                    s.ajouterConsommation(new Consommation(q, entry.getKey(), s));
                    entry.getValue().setValue(0);
                }
            }
            updateTotal();
            JOptionPane.showMessageDialog(this,
                    "Total des consommations : " + totalLabel.getText(),
                    "Total", JOptionPane.INFORMATION_MESSAGE);
        });
        footer.add(totalP, BorderLayout.NORTH);
        footer.add(btnValider, BorderLayout.SOUTH);
        mainPanel.add(footer, gbc);


        updateTotal();
    }

    private JPanel createProduitRow(Produit produit) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(20,20,70));
        JPanel labelP = new JPanel();
        labelP.setBackground(new Color(20,20,70));
        labelP.setLayout(new BoxLayout(labelP, BoxLayout.X_AXIS));
        JLabel nom = new JLabel(produit.getNom());
        nom.setForeground(Color.WHITE);
        nom.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel prix = new JLabel(String.format(" %.2f€", produit.getPrix()));
        prix.setForeground(new Color(180,180,180));
        prix.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelP.add(nom);
        labelP.add(Box.createRigidArea(new Dimension(5,0)));
        labelP.add(prix);
        row.add(labelP, BorderLayout.WEST);
        SpinnerNumberModel model = new SpinnerNumberModel(0,0,100,1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(70,28));
        spinner.setMaximumSize(new Dimension(70,28));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        quantitesSpinners.put(produit, spinner);
        spinner.addChangeListener(e -> updateTotal());
        JPanel spinP = new JPanel();
        spinP.setBackground(new Color(20,20,70));
        spinP.setLayout(new BoxLayout(spinP, BoxLayout.X_AXIS));
        spinP.add(spinner);
        row.add(spinP, BorderLayout.EAST);
        return row;
    }

    private void updateTotal() {
        Sejour s = (Sejour) sejourBox.getSelectedItem();
        double sum = 0;
        if (s != null) {
            long nuits = ChronoUnit.DAYS.between(s.getDate_deb(), s.getDate_fin());
            sum += s.getChambre().getPrix() * nuits;
            sum += s.calculerConsommation();
        }
        for (Map.Entry<Produit,JSpinner> entry : quantitesSpinners.entrySet()) {
            sum += entry.getKey().getPrix() * (int)entry.getValue().getValue();
        }
        totalLabel.setText(String.format("%.2f€", sum));
    }
}