package Vue;

import Model.*;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;

public class SejourForm extends JPanel {
    private static final Color FIELD_COLOR = new Color(9, 0, 91, 255);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JComboBox<Client> clientCombo;
    private JComboBox<Chambre> chambreCombo;
    private JTextField dateArrivee;
    private JTextField dateDepart;
    private JTextArea notes;
    private JTextArea reservationsTextArea;
    private JCheckBox parking;
    private JCheckBox petitDej;
    private JCheckBox wifi;
    private JCheckBox blanchisserie;

    private final Hotel hotel;

    public SejourForm(Hotel hotel) {
        if (hotel == null) throw new IllegalArgumentException("L'hôtel ne peut pas être null");
        this.hotel = hotel;
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_COLOR);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(AppColors.MAIN_COLOR);
        tabbedPane.setForeground(AppColors.TEXT_COLOR);

        JPanel sejourPanel = new JPanel();
        sejourPanel.setLayout(new BoxLayout(sejourPanel, BoxLayout.Y_AXIS));
        sejourPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        sejourPanel.setBackground(AppColors.MAIN_COLOR);


        JPanel ligne1 = new JPanel(new GridLayout(1,2,20,10));
        ligne1.setBackground(AppColors.MAIN_COLOR);
        clientCombo = new JComboBox<>();
        clientCombo.addItem(null);
        for (Client c : hotel.getListClient()) clientCombo.addItem(c);
        styleCombo(clientCombo);
        clientCombo.addActionListener(e -> afficherReservations());
        chambreCombo = new JComboBox<>();
        styleCombo(chambreCombo);
        chambreCombo.setEnabled(false);
        ligne1.add(clientCombo);
        ligne1.add(chambreCombo);
        sejourPanel.add(ligne1);


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


        sejourPanel.add(Box.createVerticalStrut(15));
        sejourPanel.add(new JLabel("Services additionnels :"){{ setForeground(AppColors.TEXT_COLOR); }});
        JPanel servicesPanel = new JPanel(new GridLayout(2,2,10,10));
        servicesPanel.setBackground(AppColors.MAIN_COLOR);
        parking = new JCheckBox("Parking (15€)"); styleCheck(parking);
        petitDej = new JCheckBox("Petit-déjeuner (12€/pers)"); styleCheck(petitDej);
        wifi = new JCheckBox("WiFi Premium (8€/jour)"); styleCheck(wifi);
        blanchisserie = new JCheckBox("Blanchisserie"); styleCheck(blanchisserie);
        servicesPanel.add(parking); servicesPanel.add(petitDej);
        servicesPanel.add(wifi); servicesPanel.add(blanchisserie);
        sejourPanel.add(servicesPanel);


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


        sejourPanel.add(Box.createVerticalStrut(10));
        JLabel notesLabel = new JLabel("Notes spéciales :");
        notesLabel.setForeground(AppColors.TEXT_COLOR);
        notes = new JTextArea(); notes.setLineWrap(true); notes.setWrapStyleWord(true);
        notes.setBackground(FIELD_COLOR); notes.setForeground(AppColors.TEXT_COLOR);
        JScrollPane scrollNotes = new JScrollPane(notes);
        scrollNotes.setPreferredSize(new Dimension(500,80));
        sejourPanel.add(notesLabel);
        sejourPanel.add(scrollNotes);


        sejourPanel.add(Box.createVerticalStrut(15));
        JButton creer = new JButton("Créer le séjour");
        creer.setBackground(new Color(90,86,190)); creer.setForeground(AppColors.TEXT_COLOR);
        creer.setAlignmentX(Component.CENTER_ALIGNMENT);
        creer.addActionListener(e -> creerSejour());
        sejourPanel.add(creer);

        tabbedPane.addTab("Créer un séjour", sejourPanel);
        tabbedPane.addTab("Consommations mini bar", new MiniBarPanel(hotel));
        tabbedPane.addTab("Facturation", new FacturePanel(hotel));

        add(tabbedPane, BorderLayout.CENTER);


        afficherReservations();
    }

    private void afficherReservations() {
        reservationsTextArea.setText("");
        chambreCombo.removeAllItems();
        dateArrivee.setText("");
        dateDepart.setText("");

        Client client = (Client) clientCombo.getSelectedItem();
        if (client == null) return;

        List<Reservation> list = client.getListReservation();
        if (list == null || list.isEmpty()) {
            reservationsTextArea.setText("Aucune réservation trouvée pour ce client.");
            return;
        }


        for (Reservation r : list) {
            reservationsTextArea.append(
                    r.getDate_deb().format(DATE_FORMAT) + " → " +
                            r.getDate_fin().format(DATE_FORMAT) +
                            " (Chambre " + r.getChambre().getNumero() + ")\n"
            );
        }


        LocalDate aujourdHui = LocalDate.now();
        Reservation prochaine = null;
        for (Reservation r : list) {
            if (r.getDate_deb().isAfter(aujourdHui)) {
                if (prochaine == null || r.getDate_deb().isBefore(prochaine.getDate_deb())) {
                    prochaine = r;
                }
            }
        }
        if (prochaine == null) prochaine = list.get(0);


        chambreCombo.addItem(prochaine.getChambre());
        chambreCombo.setSelectedItem(prochaine.getChambre());
        dateArrivee.setText(prochaine.getDate_deb().format(DATE_FORMAT));
        dateDepart.setText(prochaine.getDate_fin().format(DATE_FORMAT));
    }

    private void creerSejour() {
        Client client = (Client) clientCombo.getSelectedItem();
        if (client == null || client.getListReservation().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune réservation disponible pour créer un séjour.");
            return;
        }

        List<Reservation> list = client.getListReservation();
        Reservation prochaine = null;
        LocalDate aujourdHui = LocalDate.now();

        for (Reservation r : list) {
            if (r.getDate_deb().isAfter(aujourdHui)) {
                if (prochaine == null || r.getDate_deb().isBefore(prochaine.getDate_deb())) {
                    prochaine = r;
                }
            }
        }
        if (prochaine == null) prochaine = list.get(0);

        Sejour s = new Sejour(client, prochaine.getChambre(), prochaine.getDate_deb(), prochaine.getDate_fin());
        s.setRes(prochaine);


        if (parking.isSelected())       s.ajouterConsommation(new Consommation(1, new Produit("Parking",15), s));
        if (petitDej.isSelected())      s.ajouterConsommation(new Consommation(1, new Produit("Petit-déjeuner",12), s));
        if (wifi.isSelected())          s.ajouterConsommation(new Consommation(1, new Produit("WiFi Premium",8), s));
        if (blanchisserie.isSelected()) s.ajouterConsommation(new Consommation(1, new Produit("Blanchisserie",10), s));

        hotel.ajouterSejour(s);

        client.getListReservation().remove(prochaine);

        JOptionPane.showMessageDialog(this, "Séjour créé pour la réservation #" + prochaine.getId_res());


        clientCombo.setSelectedIndex(0);
        reservationsTextArea.setText("");
        chambreCombo.removeAllItems();
        dateArrivee.setText("");
        dateDepart.setText("");
        notes.setText("");
    }




private void styleCombo(JComboBox<?> combo) {
    combo.setBackground(FIELD_COLOR);
    combo.setForeground(AppColors.TEXT_COLOR);
    combo.setPreferredSize(new Dimension(200,30));
}
private void styleField(JTextField field) {
    field.setBackground(FIELD_COLOR);
    field.setForeground(AppColors.TEXT_COLOR);
    field.setCaretColor(AppColors.TEXT_COLOR);
    field.setPreferredSize(new Dimension(200,30));
}
private void styleCheck(JCheckBox cb) {
    cb.setBackground(AppColors.MAIN_COLOR);
    cb.setForeground(AppColors.TEXT_COLOR);}}
