package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SejourForm extends JPanel {

    private static final Color FIELD_COLOR = new Color(9, 0, 91, 255);

    private JComboBox<String> clientCombo;
    private JComboBox<String> chambreCombo;
    private JTextField dateArrivee;
    private JTextField dateDepart;
    private JTextArea notes;
    private JTextArea reservationsTextArea;

    // Structures simples pour simuler les données
    private ArrayList<ClientData> clients = new ArrayList<>();

    public SejourForm() {
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_COLOR);
        mockData(); // Remplit la liste "clients"

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(AppColors.MAIN_COLOR);
        tabbedPane.setForeground(AppColors.TEXT_COLOR);

        JPanel sejourPanel = new JPanel();
        sejourPanel.setLayout(new BoxLayout(sejourPanel, BoxLayout.Y_AXIS));
        sejourPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sejourPanel.setBackground(AppColors.MAIN_COLOR);

        // Ligne 1 : Client / Chambre
        JPanel ligne1 = new JPanel(new GridLayout(1, 2, 20, 10));
        ligne1.setBackground(AppColors.MAIN_COLOR);

        clientCombo = new JComboBox<>();
        chambreCombo = new JComboBox<>();
        clientCombo.addItem("Sélectionner un client");
        chambreCombo.addItem("Sélectionner une chambre");

        for (ClientData client : clients) {
            clientCombo.addItem(client.nom);
        }

        styleComboBox(clientCombo);
        styleComboBox(chambreCombo);

        clientCombo.addActionListener(e -> afficherReservations());
        chambreCombo.addActionListener(e -> remplirDates());

        ligne1.add(clientCombo);
        ligne1.add(chambreCombo);

        // Ligne 2 : Dates
        JPanel ligne2 = new JPanel(new GridLayout(1, 2, 20, 10));
        ligne2.setBackground(AppColors.MAIN_COLOR);

        dateArrivee = new JTextField();
        dateDepart = new JTextField();
        styleTextField(dateArrivee);
        styleTextField(dateDepart);

        ligne2.add(dateArrivee);
        ligne2.add(dateDepart);

        // Services
        JPanel servicesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        servicesPanel.setBackground(AppColors.MAIN_COLOR);

        JCheckBox parking = new JCheckBox("Parking (15€/jour)");
        JCheckBox petitDej = new JCheckBox("Petit-déjeuner (12€/pers)");
        JCheckBox wifi = new JCheckBox("WiFi Premium (8€/jour)");
        JCheckBox blanchisserie = new JCheckBox("Blanchisserie");

        styleCheckBox(parking);
        styleCheckBox(petitDej);
        styleCheckBox(wifi);
        styleCheckBox(blanchisserie);

        servicesPanel.add(parking);
        servicesPanel.add(petitDej);
        servicesPanel.add(wifi);
        servicesPanel.add(blanchisserie);

        // Notes
        JLabel notesLabel = new JLabel("Notes spéciales :");
        notesLabel.setForeground(AppColors.TEXT_COLOR);
        notes = new JTextArea("Demandes particulières...");
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notes.setBackground(FIELD_COLOR);
        notes.setForeground(AppColors.TEXT_COLOR);
        JScrollPane notesScroll = new JScrollPane(notes);
        notesScroll.setPreferredSize(new Dimension(500, 80));

        // Réservations
        JLabel resLabel = new JLabel("Réservations existantes du client :");
        resLabel.setForeground(AppColors.TEXT_COLOR);
        reservationsTextArea = new JTextArea();
        reservationsTextArea.setEditable(false);
        reservationsTextArea.setBackground(FIELD_COLOR);
        reservationsTextArea.setForeground(AppColors.TEXT_COLOR);
        JScrollPane resScroll = new JScrollPane(reservationsTextArea);
        resScroll.setPreferredSize(new Dimension(500, 60));

        // Bouton
        JButton creerSejour = new JButton("Créer le séjour");
        creerSejour.setBackground(new Color(90, 86, 190));
        creerSejour.setForeground(AppColors.TEXT_COLOR);
        creerSejour.setFocusPainted(false);
        creerSejour.setAlignmentX(Component.CENTER_ALIGNMENT);

        creerSejour.addActionListener(e -> {
            if (clientCombo.getSelectedIndex() == 0 || chambreCombo.getSelectedIndex() == 0 ||
                    dateArrivee.getText().isEmpty() || dateDepart.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.");
            } else {
                JOptionPane.showMessageDialog(this, "Séjour créé avec succès !");
            }
        });

        // Ajout au panel principal
        sejourPanel.add(ligne1);
        sejourPanel.add(Box.createVerticalStrut(15));
        sejourPanel.add(ligne2);
        sejourPanel.add(Box.createVerticalStrut(15));
        sejourPanel.add(new JLabel("Services additionnels :") {{
            setForeground(AppColors.TEXT_COLOR);
        }});
        sejourPanel.add(servicesPanel);
        sejourPanel.add(Box.createVerticalStrut(15));
        sejourPanel.add(resLabel);
        sejourPanel.add(resScroll);
        sejourPanel.add(Box.createVerticalStrut(10));
        sejourPanel.add(notesLabel);
        sejourPanel.add(notesScroll);
        sejourPanel.add(Box.createVerticalStrut(15));
        sejourPanel.add(creerSejour);

        tabbedPane.addTab("Créer un séjour", sejourPanel);
        tabbedPane.addTab("Consommations mini bar", new MiniBarPanel());
        tabbedPane.addTab("Facturation", createEmptyPanel());

        this.add(tabbedPane, BorderLayout.CENTER);
    }

    private void afficherReservations() {
        reservationsTextArea.setText("");
        chambreCombo.removeAllItems();
        chambreCombo.addItem("Sélectionner une chambre");
        dateArrivee.setText("");
        dateDepart.setText("");

        int selectedIndex = clientCombo.getSelectedIndex() - 1; // car index 0 = "Sélectionner"
        if (selectedIndex >= 0) {
            ClientData client = clients.get(selectedIndex);

            for (String res : client.reservations) {
                reservationsTextArea.append(res + "\n");
            }

            for (ChambreData chambre : client.chambres) {
                chambreCombo.addItem(chambre.numero);
            }
        }
    }

    private void remplirDates() {
        int selectedClientIndex = clientCombo.getSelectedIndex() - 1;
        int selectedChambreIndex = chambreCombo.getSelectedIndex() - 1;

        if (selectedClientIndex >= 0 && selectedChambreIndex >= 0) {
            ClientData client = clients.get(selectedClientIndex);
            ChambreData chambre = client.chambres.get(selectedChambreIndex);

            dateArrivee.setText(chambre.dateArrivee);
            dateDepart.setText(chambre.dateDepart);
        }
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(FIELD_COLOR);
        comboBox.setForeground(AppColors.TEXT_COLOR);
        comboBox.setPreferredSize(new Dimension(200, 30));
    }

    private void styleTextField(JTextField textField) {
        textField.setBackground(FIELD_COLOR);
        textField.setForeground(AppColors.TEXT_COLOR);
        textField.setCaretColor(AppColors.TEXT_COLOR);
        textField.setPreferredSize(new Dimension(200, 30));
    }

    private void styleCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(AppColors.MAIN_COLOR);
        checkBox.setForeground(AppColors.TEXT_COLOR);
    }

    private JPanel createEmptyPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(AppColors.MAIN_COLOR);
        return panel;
    }

    // === Structures internes pour stocker les données ===

    private static class ClientData {
        String nom;
        ArrayList<String> reservations = new ArrayList<>();
        ArrayList<ChambreData> chambres = new ArrayList<>();

        ClientData(String nom) {
            this.nom = nom;
        }
    }

    private static class ChambreData {
        String numero;
        String dateArrivee;
        String dateDepart;

        ChambreData(String numero, String dateArrivee, String dateDepart) {
            this.numero = numero;
            this.dateArrivee = dateArrivee;
            this.dateDepart = dateDepart;
        }
    }

    private void mockData() {
        ClientData alice = new ClientData("Alice Dupont");
        alice.reservations.add("01/06/2025 - 03/06/2025");
        alice.reservations.add("10/07/2025 - 12/07/2025");
        alice.chambres.add(new ChambreData("Chambre 101", "10/06/2025", "13/06/2025"));
        alice.chambres.add(new ChambreData("Chambre 102", "15/06/2025", "18/06/2025"));

        ClientData karim = new ClientData("Karim Benali");
        karim.reservations.add("05/08/2025 - 08/08/2025");
        karim.chambres.add(new ChambreData("Chambre 101", "20/06/2025", "22/06/2025"));

        ClientData marie = new ClientData("Marie Curie");
        marie.reservations.add("20/09/2025 - 25/09/2025");
        marie.chambres.add(new ChambreData("Chambre 201", "01/07/2025", "05/07/2025"));

        clients.add(alice);
        clients.add(karim);
        clients.add(marie);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Interface Hôtel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 650);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new SejourForm());
        frame.setVisible(true);
    }
}
