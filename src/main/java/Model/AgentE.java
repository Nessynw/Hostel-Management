package Model;

import java.time.LocalDateTime;

public class AgentE extends Employe {
    private Chambre chambreAssignee;
    private String statusIntervention;
    private LocalDateTime dateIntervention;

    public AgentE(String nom, String prenom, String email, String tel, String adresse, double salaire, Hotel hotel) {
        super(nom, prenom, email, tel, adresse, salaire, hotel);
        this.statusIntervention = "En cours";
    }

public void assignerChambre(Chambre chambre, LocalDateTime dateTime, Hotel hotel) {
    // Création de l'intervention
    Intervention intervention = new Intervention();
    intervention.setDate(dateTime.toLocalDate());
    intervention.setAgent(this);
    intervention.setChambre(chambre);
    
    // Mise à jour des relations
    this.chambreAssignee = chambre;
    chambre.setAgentAssigne(this);
    
    // Ajout de l'intervention à l'hôtel
    hotel.ajouterIntervention(intervention);
}
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