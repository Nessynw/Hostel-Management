package Vue;

import Controler.retourBtnControler;
import Model.AgentE;
import Model.Employe;
import Model.Hotel;
import Model.Receptionniste;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.*;
import java.util.List;

public class AjouterEmploye extends JPanel {
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtEmail;
    private JTextField txtTel;
    private JTextField txtAdresse; // Nouveau champ adresse
    private JComboBox<String> comboPoste;
    private JTextField txtSalaire;
    private JTextArea txtTaches;
    private StyledButton btnAjouter;
    private StyledButton btnRetour;
    private static final Color MAIN_COLOR = new Color(18, 11, 61);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private JFrame parentFrame;
    private List<Employe> listeEmployes;  // Déclaration de la liste

    public AjouterEmploye(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());
        setBackground(MAIN_COLOR);
        listeEmployes = new ArrayList<>();  // Initialisation de la liste
        setupComponents();

    }

    private void setupComponents() {
        // Titre
        JLabel lblTitre = new JLabel("Ajouter un nouvel employé");
        lblTitre.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitre.setForeground(AjouterEmploye.TEXT_COLOR);
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
        txtTel = createStyledTextField();
        txtAdresse = createStyledTextField(); // Champ adresse
        String[] postes = {"Réceptionniste", "Agent d'entretien"};
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
        addFormField(mainPanel, "Téléphone:", txtTel, gbc, 3);
        addFormField(mainPanel, "Adresse:", txtAdresse, gbc, 4); // Champ adresse
        addFormField(mainPanel, "Poste:", comboPoste, gbc, 5);
        addFormField(mainPanel, "Salaire (€):", txtSalaire, gbc, 6);

        // Champ tâches avec scroll
        gbc.gridy = 7;
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

        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Vérifier si tous les champs obligatoires sont remplis
                if (areRequiredFieldsFilled()) {
                    // Récupérer les valeurs des champs
                    String nom = getNom();
                    String prenom = getPrenom();
                    String email = getEmail();
                    String tel = getTel();
                    String adresse = getAdresse(); // Récupérer l'adresse
                    double salaire = Double.parseDouble(getSalaire());
                    String taches = getTaches();
                    String poste = getPoste();

                    Hotel hotel = new Hotel("BlueCastel","11 e Paris 75001","0104010504","BlueCastle@gmail.fr", 4);

                    // Créer l'employé selon le poste sélectionné
                    Employe nouvelEmploye;
                    if (poste.equals("Réceptionniste")) {
                        nouvelEmploye = new Receptionniste(nom, prenom, email,tel,adresse, salaire, taches,hotel);
                    } else if (poste.equals("Agent d'entretien")) {
                        nouvelEmploye = new AgentE(nom, prenom, email, tel, adresse, salaire,hotel); // Correction ici
                    } else {
                        nouvelEmploye = new Employe(nom, prenom, email, tel, adresse, salaire, hotel);
                    }

                    // Ajouter l'employé à la liste (ou à la base de données)
                    listeEmployes.add(nouvelEmploye);

                    // Optionnel : afficher un message de succès
                    JOptionPane.showMessageDialog(parentFrame, "Employé ajouté avec succès !");

                    // Effacer les champs du formulaire après ajout
                    clearFields();
                } else {
                    // Afficher un message d'erreur si certains champs ne sont pas remplis
                    JOptionPane.showMessageDialog(parentFrame, "Veuillez remplir tous les champs obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Code du bouton retour
        btnRetour.addActionListener(new retourBtnControler(() -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new InterfacePersonnel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        }));

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
    public String getTel() {
        return txtTel.getText().trim();
    }
    public String getAdresse() {
        return txtAdresse.getText().trim();
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
        // Validation du salaire
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
