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

        // En-tête avec titre
        JLabel titleLabel = new JLabel("Liste des Employés", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Configuration du tableau
        String[] colonnes = {"Nom", "Prénom", "Email", "Adresse", "Poste", "Salaire", "Chambre assignée", "Status"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        tableEmployes = new JTable(model);

        // Style du tableau
        styleTable(tableEmployes);

        // Remplir le tableau avec les employés
        remplirTableau();

        // Ajouter le tableau dans un ScrollPane
        JScrollPane scrollPane = new JScrollPane(tableEmployes);
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBoutons.setBackground(AppColors.MAIN_COLOR);

        // Création des boutons
        JButton btnAssigner = new JButton("Assigner Chambre");
        JButton btnTerminer = new JButton("Terminer Intervention");
        btnRetour = new JButton("Retour");
        for (JButton btn : new JButton[]{btnAssigner, btnTerminer, btnRetour}) {
            btn.setBackground(AppColors.MAIN_COLOR);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setPreferredSize(new Dimension(250, 40));
            btn.setBorderPainted(false);  // Optionnel : pour enlever la bordure
            btn.setFocusPainted(false);   // Optionnel : pour enlever l'effet de focus
        }


        // Ajout des boutons au panel
        panelBoutons.add(btnAssigner);
        panelBoutons.add(btnTerminer);
        panelBoutons.add(btnRetour);
        add(panelBoutons, BorderLayout.SOUTH);

        // Actions des boutons
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

        // Centrer le texte dans les cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ajuster la taille des colonnes
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Nom
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Prénom
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Email
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Adresse
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Poste
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Salaire
        table.getColumnModel().getColumn(6).setPreferredWidth(150); // Chambre assignée
        table.getColumnModel().getColumn(7).setPreferredWidth(150); // Status

        // Rendre le tableau non éditable
        table.setDefaultEditor(Object.class, null);
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
                        String.format("%.2f €", employe.getSalaire()),
                        chambreAssignee,
                        status
                };
                model.addRow(row);
            }
        }
    }

    private void afficherDialogueAssignation(AgentE agent) {
        JDialog dialog = new JDialog(parentFrame, "Assigner une chambre", true);
        dialog.setLayout(new GridLayout(3, 1, 10, 10));
        dialog.getContentPane().setBackground(AppColors.MAIN_COLOR);

        // Créer un ComboBox avec les chambres disponibles
        JComboBox<String> comboChambre = new JComboBox<>();
        for (Chambre chambre : hotel.getListChambre()) {
            if (chambre.getAgentAssigne() == null) {
                comboChambre.addItem("Chambre " + chambre.getNumero());
            }
        }

        JPanel panelCombo = new JPanel();
        panelCombo.setBackground(AppColors.MAIN_COLOR);
        panelCombo.add(new JLabel("Sélectionner une chambre :"));
        panelCombo.add(comboChambre);

        JButton btnValider = new StyledButton("Valider");
        btnValider.addActionListener(e -> {
            String selection = (String) comboChambre.getSelectedItem();
            if (selection != null) {
                int numeroChambre = Integer.parseInt(selection.split(" ")[1]);
                Chambre chambre = hotel.getListChambre().stream()
                        .filter(c -> c.getNumero() == numeroChambre)
                        .findFirst()
                        .orElse(null);

                if (chambre != null) {
                    agent.assignerChambre(chambre, LocalDateTime.now());
                    dialog.dispose();
                    refreshTable();
                }
            }
        });

        JPanel panelBouton = new JPanel();
        panelBouton.setBackground(AppColors.MAIN_COLOR);
        panelBouton.add(btnValider);

        dialog.add(panelCombo);
        dialog.add(panelBouton);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    private void refreshTable() {
        remplirTableau();
        tableEmployes.repaint();
    }
}