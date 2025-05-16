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
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
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

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);

        // Panneau du total
        JLabel totalTextLabel = new JLabel("Total");
        totalTextLabel.setForeground(Color.WHITE);
        totalTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        totalLabel = new JLabel("0.00€");
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(new Color(20, 20, 70));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));
        totalPanel.add(totalTextLabel, BorderLayout.WEST);
        totalPanel.add(totalLabel, BorderLayout.EAST);

        // Footer Panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(20, 20, 70));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Ajouter le bouton "Valider"
        JButton validateButton = new JButton("Valider");
        validateButton.setBackground(new Color(34, 32, 64));
        validateButton.setForeground(Color.WHITE);
        validateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        validateButton.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 100), 1, true));
        validateButton.addActionListener(e -> showTotalMessage());
        validateButton.setPreferredSize(new Dimension(100, 30));

        footerPanel.add(totalPanel, BorderLayout.NORTH);
        footerPanel.add(validateButton, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(footerPanel, gbc);
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

        JPanel spinPanel = new JPanel();
        spinPanel.setBackground(new Color(20, 20, 70));
        spinPanel.setLayout(new BoxLayout(spinPanel, BoxLayout.X_AXIS));

        spinPanel.add(spinner);

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mini Bar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500, 650);
        frame.setLocationRelativeTo(null);

        MiniBarPanel panel = new MiniBarPanel();
        frame.setContentPane(panel);

        frame.setVisible(true);
    }
}
