package Vue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class MiniBar extends JFrame {

    private JComboBox<String> sejourCombo;
    private Map<String, Double> produitsPrix;
    private Map<String, JSpinner> quantitesSpinners;
    private JLabel totalLabel;

    public MiniBar() {
        setTitle("Mini Bar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 580);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(34, 32, 64));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(34, 32, 64));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        // Label "Séjour"
        JLabel sejourLabel = new JLabel("Séjour");
        sejourLabel.setForeground(Color.WHITE);
        sejourLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sejourLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(sejourLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // ComboBox séjour
        sejourCombo = new JComboBox<>(new String[] {"Sélectionner un séjour"});
        sejourCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        sejourCombo.setBackground(new Color(34, 32, 64));
        sejourCombo.setForeground(Color.WHITE);
        sejourCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sejourCombo.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 100), 1, true));
        mainPanel.add(sejourCombo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Mini bar panel
        JPanel miniBarPanel = new JPanel();
        miniBarPanel.setBackground(new Color(20, 20, 70));
        miniBarPanel.setLayout(new BoxLayout(miniBarPanel, BoxLayout.Y_AXIS));
        miniBarPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        miniBarPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(miniBarPanel);

        // Titre mini bar
        JLabel miniBarTitle = new JLabel("Articles du mini bar");
        miniBarTitle.setForeground(Color.WHITE);
        miniBarTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        miniBarTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        miniBarPanel.add(miniBarTitle);
        miniBarPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Produits + prix
        produitsPrix = new HashMap<>();
        produitsPrix.put("Eau minérale", 3.00);
        produitsPrix.put("Soda", 4.00);
        produitsPrix.put("Bière", 6.00);
        produitsPrix.put("Vin (37.5cl)", 12.00);
        produitsPrix.put("Chips", 3.50);
        produitsPrix.put("Chocolat", 4.00);
        produitsPrix.put("Cacahuètes", 3.50);

        quantitesSpinners = new HashMap<>();

        for (Map.Entry<String, Double> entry : produitsPrix.entrySet()) {
            miniBarPanel.add(createProduitRow(entry.getKey(), entry.getValue()));
            miniBarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Total panel
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(new Color(20, 20, 70));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        totalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel totalTextLabel = new JLabel("Total");
        totalTextLabel.setForeground(Color.WHITE);
        totalTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalPanel.add(totalTextLabel, BorderLayout.WEST);

        totalLabel = new JLabel("0.00€");
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalPanel.add(totalLabel, BorderLayout.EAST);

        mainPanel.add(totalPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Date de consommation label
        JLabel dateLabel = new JLabel("Date de consommation");
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(dateLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Champ date
        JFormattedTextField dateField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("MM/dd/yyyy")));
        dateField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        dateField.setBackground(new Color(34, 32, 64));
        dateField.setForeground(Color.WHITE);
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateField.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 100), 1, true));


        setVisible(true);
    }

    private JPanel createProduitRow(String nom, double prix) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(20, 20, 70));

        // Nom + prix
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(new Color(20, 20, 70));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));

        JLabel nomLabel = new JLabel(nom);
        nomLabel.setForeground(Color.WHITE);
        nomLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelPanel.add(nomLabel);

        JLabel prixLabel = new JLabel(String.format(" %.2f€", prix));
        prixLabel.setForeground(new Color(180, 180, 180));
        prixLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        labelPanel.add(prixLabel);

        row.add(labelPanel, BorderLayout.WEST);

        // Spinner quantite
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setPreferredSize(new Dimension(70, 28));
        spinner.setMaximumSize(new Dimension(70, 28));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);

        quantitesSpinners.put(nom, spinner);

        spinner.addChangeListener(e -> updateTotal());

        // Panel pour boutons - et +
        JPanel spinPanel = new JPanel();
        spinPanel.setBackground(new Color(20, 20, 70));
        spinPanel.setLayout(new BoxLayout(spinPanel, BoxLayout.X_AXIS));

        JButton minusBtn = new JButton("−");
        styleBtn(minusBtn);
        minusBtn.addActionListener(ev -> {
            int val = (int) spinner.getValue();
            if (val > 0) spinner.setValue(val - 1);
        });

        JButton plusBtn = new JButton("+");
        styleBtn(plusBtn);
        plusBtn.addActionListener(ev -> {
            int val = (int) spinner.getValue();
            spinner.setValue(val + 1);
        });

        spinPanel.add(minusBtn);
        spinPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        spinPanel.add(spinner);
        spinPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        spinPanel.add(plusBtn);

        row.add(spinPanel, BorderLayout.EAST);

        return row;
    }

    private void styleBtn(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(34, 32, 64));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 100), 1, true));
        btn.setPreferredSize(new Dimension(30, 28));
    }

    private void updateTotal() {
        double total = 0.0;
        for (Map.Entry<String, Double> entry : produitsPrix.entrySet()) {
            int qte = (int) quantitesSpinners.get(entry.getKey()).getValue();
            total += qte * entry.getValue();
        }
        totalLabel.setText(String.format("%.2f€", total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniBar());
    }
}
