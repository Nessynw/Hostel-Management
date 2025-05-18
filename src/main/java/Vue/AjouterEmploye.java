package Vue;

import Controler.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.*;
import java.util.List;

public class AjouterEmploye extends JPanel {
    private JTextField txtNom,txtPrenom,txtEmail,txtTel,txtAdresse;
    private JComboBox<String> comboPoste;
    private JFrame parentFrame;
    private List<Employe> listeEmployes;  // Déclaration de la liste
    private StyledButton btnAjouter;
    private StyledButton btnRetour;
    private Hotel hotel;
    public static final Color TEXT_COLOR = Color.WHITE;

    public AjouterEmploye(JFrame frame, Hotel hotel) {  // Modifier le constructeur
        this.parentFrame = frame;
        this.hotel = hotel;  // Ajouter cette ligne
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_COLOR);
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
        mainPanel.setBackground(AppColors.MAIN_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialisation des composants
        txtNom = createStyledTextField();
        txtPrenom = createStyledTextField();
        txtEmail = createStyledTextField();
        txtTel = createStyledTextField();
        txtAdresse = createStyledTextField();
        String[] postes = {"Réceptionniste", "Agent d'entretien"};
        comboPoste = new JComboBox<>(postes);
        styleComboBox(comboPoste);

        btnAjouter = new StyledButton("Ajouter");
        btnRetour = new StyledButton("Retour");

        addFormField(mainPanel, "Nom:", txtNom, gbc, 0);
        addFormField(mainPanel, "Prénom:", txtPrenom, gbc, 1);
        addFormField(mainPanel, "Email:", txtEmail, gbc, 2);
        addFormField(mainPanel, "Téléphone:", txtTel, gbc, 3);
        addFormField(mainPanel, "Adresse:", txtAdresse, gbc, 4); // Champ adresse
        addFormField(mainPanel, "Poste:", comboPoste, gbc, 5);



        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(AppColors.MAIN_COLOR);
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnRetour);

        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ValidateurEmploye validateur = new ValidateurEmploye(
                    txtNom, txtPrenom, txtEmail, txtTel, txtAdresse);
                
                if (validateur.valider()) {
                    String nom = getNom();
                    String prenom = getPrenom();
                    String email = getEmail();
                    String tel = getTel();
                    String adresse = getAdresse();
                    String poste = getPoste();

                    Employe nouvelEmploye;
                    if (poste.equals("Réceptionniste")) {
                        nouvelEmploye = new Receptionniste(nom, prenom, email, tel, adresse, hotel);
                    } else if (poste.equals("Agent d'entretien")) {
                        nouvelEmploye = new AgentE(nom, prenom, email, tel, adresse,  hotel);
                    } else {
                        nouvelEmploye = new Employe(nom, prenom, email, tel, adresse,  hotel);
                    }

                    hotel.ajouterEmploye(nouvelEmploye);
                    JOptionPane.showMessageDialog(parentFrame, "Employé ajouté avec succès !");
                    clearFields();
                }
            }
        });

        btnRetour.addActionListener(new retourBtnControler(() -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new InterfacePersonnel(parentFrame, hotel));
            parentFrame.revalidate();
            parentFrame.repaint();
        }));

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

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


    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setForeground(Color.WHITE);
        comboBox.setBackground(new Color(30, 30, 70));
        ((JComponent) comboBox.getRenderer()).setOpaque(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(AppColors.TEXT_COLOR);
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
    }

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


    public void clearFields() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtEmail.setText("");
        comboPoste.setSelectedIndex(0);
        txtAdresse.setText("");
        txtTel.setText("");
        txtNom.requestFocusInWindow();
    }


}