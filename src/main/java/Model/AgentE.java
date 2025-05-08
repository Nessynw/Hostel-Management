package Model;

import java.time.LocalDateTime;

public class AgentE extends Employe {
    private Chambre chambreAssignee;
    private String statusIntervention;
    private LocalDateTime dateIntervention;

    public AgentE(String nom, String prenom, String email, String tel, String adresse, double salaire, Hotel hotel) {
        super(nom, prenom, email, tel, adresse, salaire, hotel);
        this.statusIntervention = "Non commencée";
    }

    // Méthode pour assigner une chambre à l'agent
    public void assignerChambre(Chambre chambre, LocalDateTime date) {
        this.chambreAssignee = chambre;
        this.dateIntervention = date;
        this.statusIntervention = "En cours";
        chambre.setAgentAssigne(this); // Lie l'agent à la chambre
        chambre.setEstNettoyee(false); // Marque la chambre comme non nettoyée
    }

    // Méthode pour terminer une intervention
    public void terminerIntervention() {
        if (chambreAssignee != null) {
            chambreAssignee.setEstNettoyee(true); // Marque la chambre comme nettoyée
            this.statusIntervention = "Terminée";
            this.chambreAssignee = null; // Libère l'agent
        }
    }

    // Getters existants
    public Chambre getChambreAssignee() {
        return chambreAssignee;
    }

    public String getStatusIntervention() {
        return statusIntervention;
    }

    public LocalDateTime getDateIntervention() {
        return dateIntervention;
    }
}