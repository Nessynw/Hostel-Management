package Vue;

import Controler.*;
import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Vector;
import java.time.LocalDateTime;

public class AfficherEmploye extends JPanel {
    private JTable tableEmployes;
    private JButton btnRetour;
    private JFrame parentFrame;
    private Hotel hotel;
    private static final Color backgroundColor = new Color(18, 11, 61);

    public AfficherEmploye(JFrame frame, Hotel hotel) {
        this.parentFrame = frame;
        this.hotel = hotel;

        setLayout(new BorderLayout());
        setBackground(AppColors.MAIN_COLOR);

        JLabel titleLabel = new JLabel("Liste des Employés", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(titleLabel, BorderLayout.NORTH);

        String[] colonnes = {"Nom", "Prénom", "Email", "Adresse", "Poste", "Chambre assignée", "Status"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        tableEmployes = new JTable(model);

        styleTable(tableEmployes);

        remplirTableau();

        JScrollPane scrollPane = new JScrollPane(tableEmployes);
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBoutons.setBackground(AppColors.MAIN_COLOR);

        JButton btnAssigner = new JButton("Assigner Chambre");
        JButton btnTerminer = new JButton("Terminer Intervention");
        btnRetour = new JButton("Retour");
        for (JButton btn : new JButton[]{btnAssigner, btnTerminer, btnRetour}) {
            btn.setBackground(AppColors.MAIN_COLOR);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setPreferredSize(new Dimension(250, 40));
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
        }


        panelBoutons.add(btnAssigner);
        panelBoutons.add(btnTerminer);
        panelBoutons.add(btnRetour);
        add(panelBoutons, BorderLayout.SOUTH);

        btnRetour.addActionListener(new retourBtnControler(() -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new InterfacePersonnel(parentFrame, hotel));
            parentFrame.revalidate();
            parentFrame.repaint();
        }));

        btnAssigner.addActionListener(e -> {
            int row = tableEmployes.getSelectedRow();
            if (row != -1) {
                Employe employe = hotel.getListEmploye().get(row);
                if (employe instanceof AgentE) {
                    afficherDialogueAssignation((AgentE) employe);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Seuls les agents d'entretien peuvent être assignés à des chambres",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Veuillez sélectionner un employé",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTerminer.addActionListener(e -> {
            int row = tableEmployes.getSelectedRow();
            if (row != -1) {
                Employe employe = hotel.getListEmploye().get(row);
                if (employe instanceof AgentE) {
                    AgentE agent = (AgentE) employe;
                    if (agent.getChambreAssignee() != null) {
                        agent.terminerIntervention();
                        refreshTable();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Cet agent n'a pas de chambre assignée",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void styleTable(JTable table) {
        table.setBackground(backgroundColor);
        table.setForeground(Color.white);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setBackground(backgroundColor);
        table.getTableHeader().setForeground(Color.black);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);

        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);
    }

    private void remplirTableau() {
        DefaultTableModel model = (DefaultTableModel) tableEmployes.getModel();
        model.setRowCount(0);

        Vector<Employe> employes = hotel.getListEmploye();
        if (employes != null) {
            for (Employe employe : employes) {
                String poste = "";
                String chambreAssignee = "-";
                String status = "-";

                if (employe instanceof Receptionniste) {
                    poste = "Réceptionniste";
                } else if (employe instanceof AgentE) {
                    AgentE agent = (AgentE) employe;
                    poste = "Agent d'entretien";
                    if (agent.getChambreAssignee() != null) {
                        chambreAssignee = "Chambre " + agent.getChambreAssignee().getNumero();
                        status = agent.getStatusIntervention();
                    }
                } else {
                    poste = "Employé";
                }

                Object[] row = {
                        employe.getNom(),
                        employe.getPrenom(),
                        employe.getEmail(),
                        employe.getAdresse(),
                        poste,
                        chambreAssignee,
                        status
                };
                model.addRow(row);
            }
        }
    }

    private void afficherDialogueAssignation(AgentE agent) {
        JDialog dialog = new JDialog(parentFrame, "Assigner une chambre", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(AppColors.MAIN_COLOR);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(AppColors.MAIN_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Sélectionner une chambre :");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JComboBox<String> comboChambre = new JComboBox<>();
        comboChambre.setPreferredSize(new Dimension(200, 30));
        comboChambre.setFont(new Font("Arial", Font.PLAIN, 14));
        comboChambre.setBackground(Color.WHITE);

        for (Chambre chambre : hotel.getListChambre()) {
            if (chambre.getAgentAssigne() == null) {
                comboChambre.addItem("Chambre " + chambre.getNumero());
            }
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);

        mainPanel.add(titleLabel, gbc);
        gbc.insets = new Insets(10, 0, 15, 0);
        mainPanel.add(comboChambre, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(AppColors.MAIN_COLOR);

        JButton btnValider = new JButton("Valider");
        btnValider.setPreferredSize(new Dimension(120, 35));
        btnValider.setBackground(AppColors.MAIN_COLOR);
        btnValider.setForeground(Color.WHITE);
        btnValider.setFont(new Font("Arial", Font.BOLD, 14));
        btnValider.setFocusPainted(false);
        btnValider.setBorderPainted(false);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setPreferredSize(new Dimension(120, 35));
        btnAnnuler.setBackground(AppColors.MAIN_COLOR);
        btnAnnuler.setForeground(Color.WHITE);
        btnAnnuler.setFont(new Font("Arial", Font.BOLD, 14));
        btnAnnuler.setFocusPainted(false);
        btnAnnuler.setBorderPainted(false);

        buttonPanel.add(btnValider);
        buttonPanel.add(btnAnnuler);

        btnValider.addActionListener(e -> {
            String selection = (String) comboChambre.getSelectedItem();
            if (selection != null) {
                int numeroChambre = Integer.parseInt(selection.split(" ")[1]);
                Chambre chambre = hotel.getListChambre().stream()
                        .filter(c -> c.getNumero() == numeroChambre)
                        .findFirst()
                        .orElse(null);

                if (chambre != null) {
                    agent.assignerChambre(chambre, LocalDateTime.now(),hotel);
                    dialog.dispose();
                    refreshTable();
                }
            }
        });

        btnAnnuler.addActionListener(e -> dialog.dispose());

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
    private void refreshTable() {
        remplirTableau();
        tableEmployes.repaint();
    }
}