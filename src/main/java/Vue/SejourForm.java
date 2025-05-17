package Vue;

import Model.*;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class SejourForm extends JPanel {
    private static final Color FIELD_COLOR = new Color(9, 0, 91, 255);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JComboBox<Client> clientCombo;
    private JComboBox<Chambre> chambreCombo;
    private JTextField dateArrivee;
    private JTextField dateDepart;
    private JTextArea notes;
    private JTextArea reservationsTextArea;

    private final Hotel hotel;

    public SejourForm(Hotel hotel) {
        if (hotel == null) throw new IllegalArgumentException("L'hôtel ne peut pas être null");
        this.hotel = hotel;
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_COLOR);

        // Création des onglets
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(AppColors.MAIN_COLOR);
        tabbedPane.setForeground(AppColors.TEXT_COLOR);

        // Onglet 1 : Création de séjour
        JPanel sejourPanel = new JPanel();
        sejourPanel.setLayout(new BoxLayout(sejourPanel, BoxLayout.Y_AXIS));
        sejourPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        sejourPanel.setBackground(AppColors.MAIN_COLOR);

        // Ligne 1 : Client et chambre réservée
        JPanel ligne1 = new JPanel(new GridLayout(1,2,20,10));
        ligne1.setBackground(AppColors.MAIN_COLOR);
        clientCombo = new JComboBox<>();
        clientCombo.addItem(null);
        for (Client c : hotel.getListClient()) clientCombo.addItem(c);
        styleCombo(clientCombo);
        clientCombo.addActionListener(e -> afficherReservations());
        chambreCombo = new JComboBox<>();
        styleCombo(chambreCombo);
        chambreCombo.setEnabled(false); // lecture seule
        ligne1.add(clientCombo);
        ligne1.add(chambreCombo);
        sejourPanel.add(ligne1);

        // Ligne 2 : Dates auto-remplies
        JPanel ligne2 = new JPanel(new GridLayout(1,2,20,10));
        ligne2.setBackground(AppColors.MAIN_COLOR);
        dateArrivee = new JTextField(); dateArrivee.setEditable(false);
        dateDepart  = new JTextField(); dateDepart.setEditable(false);
        styleField(dateArrivee);
        styleField(dateDepart);
        ligne2.add(dateArrivee);
        ligne2.add(dateDepart);
        sejourPanel.add(Box.createVerticalStrut(15));
        sejourPanel.add(ligne2);

        // Services additionnels
        sejourPanel.add(Box.createVerticalStrut(15));
        sejourPanel.add(new JLabel("Services additionnels :"){{ setForeground(AppColors.TEXT_COLOR); }});
        JPanel servicesPanel = new JPanel(new GridLayout(2,2,10,10));
        servicesPanel.setBackground(AppColors.MAIN_COLOR);
        JCheckBox parking = new JCheckBox("Parking (15€/jour)"); styleCheck(parking);
        JCheckBox petitDej = new JCheckBox("Petit-déjeuner (12€/pers)"); styleCheck(petitDej);
        JCheckBox wifi = new JCheckBox("WiFi Premium (8€/jour)"); styleCheck(wifi);
        JCheckBox blanchisserie = new JCheckBox("Blanchisserie"); styleCheck(blanchisserie);
        servicesPanel.add(parking); servicesPanel.add(petitDej);
        servicesPanel.add(wifi); servicesPanel.add(blanchisserie);
        sejourPanel.add(servicesPanel);

        // Réservations existantes
        sejourPanel.add(Box.createVerticalStrut(15));
        JLabel resLabel = new JLabel("Réservations existantes du client :");
        resLabel.setForeground(AppColors.TEXT_COLOR);
        reservationsTextArea = new JTextArea(5,30);
        reservationsTextArea.setEditable(false);
        reservationsTextArea.setBackground(FIELD_COLOR);
        reservationsTextArea.setForeground(AppColors.TEXT_COLOR);
        JScrollPane scrollRes = new JScrollPane(reservationsTextArea);
        scrollRes.setPreferredSize(new Dimension(500,100));
        sejourPanel.add(resLabel);
        sejourPanel.add(scrollRes);

        // Notes spéciales
        sejourPanel.add(Box.createVerticalStrut(10));
        JLabel notesLabel = new JLabel("Notes spéciales :");
        notesLabel.setForeground(AppColors.TEXT_COLOR);
        notes = new JTextArea(); notes.setLineWrap(true); notes.setWrapStyleWord(true);
        notes.setBackground(FIELD_COLOR); notes.setForeground(AppColors.TEXT_COLOR);
        JScrollPane scrollNotes = new JScrollPane(notes);
        scrollNotes.setPreferredSize(new Dimension(500,80));
        sejourPanel.add(notesLabel);
        sejourPanel.add(scrollNotes);

        // Bouton créer séjour
        sejourPanel.add(Box.createVerticalStrut(15));
        JButton creer = new JButton("Créer le séjour");
        creer.setBackground(new Color(90,86,190)); creer.setForeground(AppColors.TEXT_COLOR);
        creer.setAlignmentX(Component.CENTER_ALIGNMENT);
        creer.addActionListener(e -> creerSejour());
        sejourPanel.add(creer);

        tabbedPane.addTab("Créer un séjour", sejourPanel);
        // Onglet mini-bar
        tabbedPane.addTab("Consommations mini bar", new MiniBarPanel(hotel));
        // Onglet facturation
        tabbedPane.addTab("Facturation", new FacturePanel());
        add(tabbedPane, BorderLayout.CENTER);
    }

    /** Récupère et affiche la première réservation du client */
    private void afficherReservations() {
        reservationsTextArea.setText("");
        chambreCombo.removeAllItems();
        dateArrivee.setText(""); dateDepart.setText("");
        Client client = (Client) clientCombo.getSelectedItem();
        if (client==null) return;
        List<Reservation> list = client.getListReservation();
        if (list!=null && !list.isEmpty()) {
            Reservation r0 = list.get(0);
            // affichage zone texte
            for (Reservation r: list) {
                reservationsTextArea.append(
                        r.getDate_deb().format(DATE_FORMAT)+" → "+
                                r.getDate_fin().format(DATE_FORMAT)+
                                " (Chambre "+r.getChambre().getNumero()+")\n"
                );
            }
            // remplir chambre et dates
            chambreCombo.addItem(r0.getChambre());
            dateArrivee.setText(r0.getDate_deb().format(DATE_FORMAT));
            dateDepart .setText(r0.getDate_fin().format(DATE_FORMAT));
        }
    }

    /** Crée un séjour à partir de la réservation affichée */
    private void creerSejour() {
        Client client = (Client) clientCombo.getSelectedItem();
        if (client==null || client.getListReservation().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Aucune réservation sélectionnée."); return;
        }
        Reservation r0 = client.getListReservation().get(0);
        Sejour s = new Sejour(client, r0.getChambre(), r0.getDate_deb(), r0.getDate_fin());
        s.setRes(r0);
        hotel.ajouterSejour(s);
        JOptionPane.showMessageDialog(this,"Séjour créé pour la réservation #"+r0.getId_res());
    }


    private JPanel createEmptyPanel() { JPanel p=new JPanel(); p.setBackground(AppColors.MAIN_COLOR); return p; }
    private void styleCombo(JComboBox<?> combo) { combo.setBackground(FIELD_COLOR); combo.setForeground(AppColors.TEXT_COLOR); combo.setPreferredSize(new Dimension(200,30)); }
    private void styleField(JTextField field) { field.setBackground(FIELD_COLOR); field.setForeground(AppColors.TEXT_COLOR); field.setCaretColor(AppColors.TEXT_COLOR); field.setPreferredSize(new Dimension(200,30)); }
    private void styleCheck(JCheckBox cb) { cb.setBackground(AppColors.MAIN_COLOR); cb.setForeground(AppColors.TEXT_COLOR); }
}
