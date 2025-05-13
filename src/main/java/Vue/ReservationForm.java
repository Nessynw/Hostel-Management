package Vue;

import Model.Hotel;
import Model.Client;
import Model.Reservation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import Model.Client;
import java.time.LocalDate;

public class ReservationForm extends JPanel {
    private Hotel hotel;
    private static final Color hover = new Color(58, 90, 153);
    private static final Color text_color = Color.WHITE;
    private static final Color buttonColor = new Color(34, 193, 195);
    private static final Color deleteButtonColor = new Color(255, 69, 0);
    private static final Color min_color = new Color(40, 45, 80);
    private static final Font fieldFont = new Font("Arial", Font.PLAIN, 16);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JTextField searchField;
    private JPanel dataPanel;
    private GridBagConstraints gbc;

    public ReservationForm(Hotel hotel) {
        this.hotel = hotel;
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_COLOR);

        // Navigation bar
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(AppColors.MAIN_COLOR);
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel title = new JLabel("Liste des Réservations");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        navBar.add(title, BorderLayout.WEST);

        searchField = new JTextField();
        searchField.setFont(fieldFont);
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(40, 30));
        searchButton.setBackground(Color.WHITE);
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                searchButton.setBackground(hover);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                searchButton.setBackground(Color.WHITE);
                setCursor(Cursor.getDefaultCursor());
            }
        });
        searchButton.addActionListener(e -> doSearch());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setBackground(AppColors.MAIN_COLOR);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        navBar.add(searchPanel, BorderLayout.EAST);

        add(navBar, BorderLayout.NORTH);

        // Data area
        dataPanel = new JPanel(new GridBagLayout());
        dataPanel.setBackground(min_color);
        JScrollPane scrollPane = new JScrollPane(dataPanel);
        scrollPane.getViewport().setBackground(min_color);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initial display
        afficherReservations(collectReservations(hotel.getListClient()));
    }

    private Vector<Reservation> collectReservations(Vector<Client> clients) {
        Vector<Reservation> all = new Vector<>();
        for (Client c : clients) {
            if (c.getListReservation() != null) {
                all.addAll(c.getListReservation());
            }
        }
        return all;
    }

    private void afficherReservations(Vector<Reservation> reservations) {
        dataPanel.removeAll();
        String[] columns = {"ID", "Client", "Chambre", "Début", "Fin", "Modifier", "Supprimer"};
        for (int i = 0; i < columns.length; i++) {
            gbc.gridx = i; gbc.gridy = 0;
            JLabel lbl = new JLabel(columns[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 16));
            lbl.setForeground(text_color);
            dataPanel.add(lbl, gbc);
        }
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            int row = i + 1;
            Object[] vals = {
                    r.getId_res(),
                    r.getClient().getNom() + " " + r.getClient().getPrenom(),
                    r.getChambre().getNum_chambre(),
                    FORMATTER.format(r.getDate_deb()),
                    FORMATTER.format(r.getDate_fin())
            };
            for (int j = 0; j < vals.length; j++) {
                gbc.gridx = j; gbc.gridy = row;
                JLabel cell = new JLabel(vals[j].toString());
                cell.setFont(fieldFont); cell.setForeground(text_color);
                dataPanel.add(cell, gbc);
            }
            JButton editBtn = new JButton("Modifier");
            editBtn.setBackground(buttonColor); editBtn.setForeground(text_color);
            editBtn.setPreferredSize(new Dimension(100, 30));
            gbc.gridx = vals.length; gbc.gridy = row;
            dataPanel.add(editBtn, gbc);
            editBtn.addActionListener(e -> {
                // Ouvre un dialog pour modifier la réservation
                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier Réservation", true);
                dialog.setSize(300, 300);
                dialog.setLocationRelativeTo(this);
                JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
                panel.add(new JLabel("Date début (jj/MM/aaaa):"));
                JTextField debutField = new JTextField(FORMATTER.format(r.getDate_deb()));
                panel.add(debutField);
                panel.add(new JLabel("Date fin (jj/MM/aaaa):"));
                JTextField finField = new JTextField(FORMATTER.format(r.getDate_fin()));
                panel.add(finField);
                panel.add(new JLabel());
                JButton saveBtn = new JButton("Enregistrer");
                panel.add(saveBtn);
                saveBtn.addActionListener(ev -> {
                    try {
                        r.setDate_deb(LocalDate.parse(debutField.getText(), FORMATTER));
                        r.setDate_fin(LocalDate.parse(finField.getText(), FORMATTER));
                        dialog.dispose();
                        afficherReservations(collectReservations(hotel.getListClient()));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog,
                                "Format de date invalide.",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                });
                dialog.add(panel);
                dialog.setVisible(true);
            });
            JButton delBtn = new JButton("Supprimer");
            delBtn.setBackground(deleteButtonColor); delBtn.setForeground(text_color);
            delBtn.setPreferredSize(new Dimension(100, 30));
            gbc.gridx = vals.length + 1; gbc.gridy = row;
            dataPanel.add(delBtn, gbc);
            delBtn.addActionListener(e -> {
                r.getChambre().supprimerReservation(r);
                r.getClient().annulerReservation(r);
                afficherReservations(collectReservations(hotel.getListClient()));
            });
        }
        dataPanel.revalidate(); dataPanel.repaint();
    }

    private void doSearch() {
        String filter = searchField.getText().toLowerCase();
        Vector<Reservation> all = collectReservations(hotel.getListClient());
        Vector<Reservation> filtered = new Vector<>();
        for (Reservation r : all) {
            String full = (r.getClient().getNom() + " " + r.getClient().getPrenom()).toLowerCase();
            if (full.contains(filter)) filtered.add(r);
        }
        afficherReservations(filtered);
    }
}
