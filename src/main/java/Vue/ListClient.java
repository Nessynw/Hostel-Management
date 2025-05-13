package Vue;

import Model.Hotel;
import Model.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class ListClient extends JPanel {
    private static final Color text_color = Color.WHITE;
    private static final Color buttonColor = new Color(34, 193, 195);
    private static final Color deleteButtonColor = new Color(255, 69, 0);
    private static final Color min_color = new Color(40, 45, 80);
    private static final Font fieldFont = new Font("Arial", Font.PLAIN, 16);

    private Hotel hotel;
    private GridBagConstraints gbc;
    private JPanel dataPanel;

    public ListClient(Hotel hotel) {
        this.hotel = hotel;
        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_COLOR);

        // Header
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(AppColors.MAIN_COLOR);
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel title = new JLabel("Liste des Clients");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(text_color);
        navBar.add(title, BorderLayout.WEST);
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

        refreshData();
    }

    private void refreshData() {
        dataPanel.removeAll();
        String[] columns = {"ID", "Nom", "Prénom", "Email", "Téléphone", "Adresse", "Modifier", "Supprimer"};
        for (int i = 0; i < columns.length; i++) {
            gbc.gridx = i; gbc.gridy = 0;
            JLabel lbl = new JLabel(columns[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 16));
            lbl.setForeground(text_color);
            dataPanel.add(lbl, gbc);
        }

        Vector<Client> clients = hotel.getListClient();
        for (int i = 0; i < clients.size(); i++) {
            Client c = clients.get(i);
            int row = i + 1;
            String[] values = {
                    String.valueOf(c.getId_client()), c.getNom(), c.getPrenom(),
                    c.getEmail(), c.getTel(), c.getAdresse()
            };
            for (int j = 0; j < values.length; j++) {
                gbc.gridx = j; gbc.gridy = row;
                JLabel cell = new JLabel(values[j]);
                cell.setFont(fieldFont); cell.setForeground(text_color);
                dataPanel.add(cell, gbc);
            }
            // Modifier button opens simple dialog
            JButton editBtn = new JButton("Modifier");
            editBtn.setBackground(buttonColor); editBtn.setForeground(text_color);
            gbc.gridx = values.length; gbc.gridy = row;
            dataPanel.add(editBtn, gbc);
            editBtn.addActionListener(e -> openEditDialog(c));

            // Supprimer
            JButton delBtn = new JButton("Supprimer");
            delBtn.setBackground(deleteButtonColor); delBtn.setForeground(text_color);
            gbc.gridx = values.length + 1; gbc.gridy = row;
            dataPanel.add(delBtn, gbc);
            delBtn.addActionListener(e -> {
                hotel.getListClient().remove(c);
                refreshData();
            });
        }
        dataPanel.revalidate(); dataPanel.repaint();
    }

    private void openEditDialog(Client c) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Modifier Client", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel(new GridLayout(6,2,5,5));

        panel.add(new JLabel("Nom:"));
        JTextField nomField = new JTextField(c.getNom()); panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        JTextField prenomField = new JTextField(c.getPrenom()); panel.add(prenomField);
        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(c.getEmail()); panel.add(emailField);
        panel.add(new JLabel("Téléphone:"));
        JTextField telField = new JTextField(c.getTel()); panel.add(telField);
        panel.add(new JLabel("Adresse:"));
        JTextField addrField = new JTextField(c.getAdresse()); panel.add(addrField);

        JButton saveBtn = new JButton("Enregistrer");
        saveBtn.addActionListener(ev -> {
            // Update client
            c.setNom(nomField.getText());
            c.setPrenom(prenomField.getText());
            c.setEmail(emailField.getText());
            c.setTel(telField.getText());
            c.setAdresse(addrField.getText());
            dialog.dispose();
            refreshData();
            if (!telField.getText().matches("\\d{10}")) {
                JOptionPane.showMessageDialog(dialog,
                        "Le numéro de téléphone doit contenir exactement 10 chiffres.",
                        "Erreur de format", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });
        panel.add(new JLabel()); panel.add(saveBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}
