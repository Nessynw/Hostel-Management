package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class AjouterEmploye extends JPanel {
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtEmail;
    private JComboBox<String> comboPoste;
    private JTextField txtSalaire;
    private JTextArea txtTaches;
    private StyledButton btnAjouter;
    private StyledButton btnRetour;
    private static final Color MAIN_COLOR = new Color(18, 11, 61);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);

    public AjouterEmploye() {
        setLayout(new BorderLayout());
        setBackground(MAIN_COLOR);
        setupComponents();
    }

    private void setupComponents() {
        // Titre
        JLabel lblTitre = new JLabel("Ajouter un nouvel employé");
        lblTitre.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitre.setForeground(TEXT_COLOR);
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitre, BorderLayout.NORTH);

        // Panel principal pour les champs
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(MAIN_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialisation des composants
        txtNom = createStyledTextField();
        txtPrenom = createStyledTextField();
        txtEmail = createStyledTextField();
        String[] postes = {"Réceptionniste", "Agent d'entretien", "Manager", "Chef cuisinier", "Serveur"};
        comboPoste = new JComboBox<>(postes);
        styleComboBox(comboPoste);
        txtSalaire = createStyledTextField();
        txtTaches = createStyledTextArea();
        btnAjouter = new StyledButton("Ajouter");
        btnRetour = new StyledButton("Retour");

        // Ajout des composants avec leurs labels
        addFormField(mainPanel, "Nom:", txtNom, gbc, 0);
        addFormField(mainPanel, "Prénom:", txtPrenom, gbc, 1);
        addFormField(mainPanel, "Email:", txtEmail, gbc, 2);
        addFormField(mainPanel, "Poste:", comboPoste, gbc, 3);
        addFormField(mainPanel, "Salaire (€):", txtSalaire, gbc, 4);

        // Champ tâches avec scroll
        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel lblTaches = createLabel("Tâches:");
        mainPanel.add(lblTaches, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JScrollPane scrollTaches = new JScrollPane(txtTaches);
        scrollTaches.setPreferredSize(new Dimension(300, 100));
        mainPanel.add(scrollTaches, gbc);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(MAIN_COLOR);
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnRetour);

        // Ajout des panels au panel principal
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ajout des tooltips
        addTooltips();
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        textField.setForeground(Color.WHITE);
        textField.setBackground(new Color(30, 30, 70));
        textField.setCaretColor(Color.WHITE);
        return textField;
    }

    private JTextArea createStyledTextArea() {
        JTextArea textArea = new JTextArea(4, 20);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(30, 30, 70));
        textArea.setCaretColor(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setForeground(Color.WHITE);
        comboBox.setBackground(new Color(30, 30, 70));
        ((JComponent) comboBox.getRenderer()).setOpaque(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private void addFormField(JPanel panel, String labelText, JComponent field,
                              GridBagConstraints gbc, int gridy) {
        gbc.gridy = gridy;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(createLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(field, gbc);
    }

    private void addTooltips() {
        txtNom.setToolTipText("Entrez le nom de famille de l'employé");
        txtPrenom.setToolTipText("Entrez le prénom de l'employé");
        txtEmail.setToolTipText("Entrez l'adresse email professionnelle");
        comboPoste.setToolTipText("Sélectionnez le poste de l'employé");
        txtSalaire.setToolTipText("Entrez le salaire mensuel en euros");
        txtTaches.setToolTipText("Décrivez les principales tâches de l'employé");
    }

    // Getters pour les champs
    public String getNom() {
        return txtNom.getText().trim();
    }

    public String getPrenom() {
        return txtPrenom.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getPoste() {
        return (String) comboPoste.getSelectedItem();
    }

    public String getSalaire() {
        return txtSalaire.getText().trim();
    }

    public String getTaches() {
        return txtTaches.getText().trim();
    }

    public StyledButton getBtnAjouter() {
        return btnAjouter;
    }

    public StyledButton getBtnRetour() {
        return btnRetour;
    }

    // Méthode pour vider les champs
    public void clearFields() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtEmail.setText("");
        comboPoste.setSelectedIndex(0);
        txtSalaire.setText("");
        txtTaches.setText("");
    }

    // Méthode pour vérifier si tous les champs obligatoires sont remplis
    public boolean areRequiredFieldsFilled() {
        return !getNom().isEmpty() &&
                !getPrenom().isEmpty() &&
                !getEmail().isEmpty() &&
                !getSalaire().isEmpty();
    }

    // Méthode pour ajouter la validation des champs
    public void addValidationListeners() {
        // Validation du salaire (nombres uniquement)
        txtSalaire.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }
            }
        });

        // Validation de l'email
        txtEmail.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    txtEmail.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                }
            }
        });
    }
}
