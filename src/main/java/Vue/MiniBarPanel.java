package Vue;

import Model.Produit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MiniBarPanel extends JPanel {
    private JComboBox<String> sejour_box;
    private Produit[] produits;
    private JLabel totalLabel;
    private Map<String, JSpinner> quantitesSpinners;
    private Map<String, Double> produitsprix;

    public MiniBarPanel() {
        // Initialisation du GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espace entre les composants
        setBackground(new Color(34, 32, 64)); // Fond du JPanel
        setLayout(new BorderLayout()); // Layout principal BorderLayout
        setPreferredSize(new Dimension(450, 580)); // Taille préférée du JPanel

        JPanel header = new JPanel();
        // Initialise les produits
        produits = new Produit[]{
                new Produit("Pomme", 2.5),
                new Produit("Banane", 1.2),
                new Produit("Orange", 3.0),
                new Produit("Poire", 2.0),
                new Produit("Raisin", 4.5),
                new Produit("Mangue", 5.0),
                new Produit("Pêche", 3.5),
                new Produit("Cerise", 6.0),
                new Produit("Fraise", 4.2),
                new Produit("Ananas", 6.5),
                new Produit("Pastèque", 7.0),
                new Produit("Kiwi", 2.8),
                new Produit("Grenade", 3.8),
                new Produit("Abricot", 2.4),
                new Produit("Cantaloup", 3.2),
                new Produit("Prune", 2.9),
                new Produit("Pomelo", 3.7),
                new Produit("Litchi", 5.5),
                new Produit("Figues", 4.0)
        };

        // Initialise la Map des prix
        produitsprix = new HashMap<>();
        for (Produit p : produits) {
            produitsprix.put(p.getNom(), p.getPrix());
        }

        quantitesSpinners = new HashMap<>();

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(34, 32, 64));
        mainPanel.setLayout(new GridBagLayout()); // Utilisation de GridBagLayout
        mainPanel.setBorder(new EmptyBorder(15, 20, 20, 20)); // Bordure vide
        add(mainPanel, BorderLayout.CENTER);

        // Label "Séjour"
        JLabel sejour = new JLabel("Séjour");
        sejour.setForeground(Color.white);
        sejour.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Largeur du label
        mainPanel.add(sejour, gbc);

        // JComboBox "Séjour"
        sejour_box = new JComboBox<>(new String[]{"Sélectionner un séjour"});
        sejour_box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Largeur du JComboBox
        sejour_box.setBackground(new Color(34, 32, 64));
        sejour_box.setForeground(Color.white);
        sejour_box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sejour_box.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 100), 1, true));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Réinitialisation de la largeur du composant
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement
        mainPanel.add(sejour_box, gbc);

        // miniBarPanel pour les articles
        JPanel miniBarPanel = new JPanel();
        miniBarPanel.setBackground(new Color(20, 20, 70));
        miniBarPanel.setLayout(new BoxLayout(miniBarPanel, BoxLayout.Y_AXIS)); // Utilisation de BoxLayout
        miniBarPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        miniBarPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alignement à gauche
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3; // Remplir sur toute la largeur
        mainPanel.add(miniBarPanel, gbc);

        // Titre "Articles du mini bar"
        JLabel miniBarTitle = new JLabel("Articles du mini bar");
        miniBarTitle.setForeground(Color.WHITE);
        miniBarTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        miniBarTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        miniBarPanel.add(miniBarTitle);

        // Ajout d'un espacement rigide entre le titre et les articles
        miniBarPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Ajout des produits et quantités
        for (Produit produit : produits) {
            miniBarPanel.add(createProduitRow(produit));
            miniBarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Ajout d'un JScrollPane autour du miniBarPanel pour gérer les produits défilants
        JScrollPane scrollPane = new JScrollPane(miniBarPanel);
        scrollPane.setBackground(new Color(20, 20, 70));
        scrollPane.setPreferredSize(new Dimension(450, 400));// Ajuste la taille du JScrollPane
        gbc.gridx = 0;
        gbc.gridy = 3; // Positionner après les produits
        mainPanel.add(scrollPane, gbc);

        // Panneau du total
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

        totalPanel.setPreferredSize(new Dimension(450, 50));  // Ajuste la taille du panneau du total pour qu'il ait la même largeur que le JScrollPane

        // Footer Panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(20, 20, 70));
        footerPanel.setPreferredSize(new Dimension(450, 70));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Ajouter le bouton "Valider"
        JButton validateButton = new JButton("Valider");
        validateButton.setBackground(new Color(34, 32, 64));
        validateButton.setForeground(Color.WHITE);
        validateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        validateButton.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 100), 1, true));
        validateButton.addActionListener(e -> showTotalMessage()); // Affiche un message avec le total lors de la validation
        footerPanel.add(validateButton, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 4; // Placer à la position gridy 2
        gbc.gridwidth = 3;
        mainPanel.add(footerPanel, gbc);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private JPanel createProduitRow(Produit produit) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(20, 20, 70));

        // Nom + prix
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(new Color(20, 20, 70));
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));

        JLabel nomLabel = new JLabel(produit.getNom());
        nomLabel.setForeground(Color.WHITE);
        nomLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelPanel.add(nomLabel);

        JLabel prixLabel = new JLabel(String.format(" %.2f€", produit.getPrix()));
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

        quantitesSpinners.put(produit.getNom(), spinner);

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

    private void updateTotal() {
        double total = 0.0;
        for (Produit produit : produits) {
            int quantity = (int) quantitesSpinners.get(produit.getNom()).getValue();
            total += produit.getPrix() * quantity;
        }
        totalLabel.setText(String.format("%.2f€", total));
    }

    private void showTotalMessage() {
        double total = 0.0;
        for (Produit produit : produits) {
            int quantity = (int) quantitesSpinners.get(produit.getNom()).getValue();
            total += produit.getPrix() * quantity;
        }
        JOptionPane.showMessageDialog(this, "Le total des consommations est : " + String.format("%.2f€", total), "Total", JOptionPane.INFORMATION_MESSAGE);
    }

    private void styleBtn(JButton btn) {
        btn.setBackground(new Color(34, 32, 64));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 100), 1, true));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Exemple JPanel");
        frame.setTitle("Mini Bar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500, 650); // Taille de la fenêtre
        frame.setLocationRelativeTo(null); // Centrer la fenêtre

        // Ajouter le MiniBarPanel au JFrame
        MiniBarPanel panel = new MiniBarPanel();
        frame.setContentPane(panel);

        frame.setVisible(true); // Afficher la fenêtre
    }
}
