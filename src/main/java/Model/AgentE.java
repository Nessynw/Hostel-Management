package Model;

import java.time.LocalDateTime;

public class AgentE extends Employe {
    private Chambre chambreAssignee;
    private String statusIntervention;

    public AgentE(String nom, String prenom, String email, String tel, String adresse,  Hotel hotel) {
        super(nom, prenom, email, tel, adresse, hotel);
        this.statusIntervention = "En cours";
    }

public void assignerChambre(Chambre chambre, LocalDateTime dateTime, Hotel hotel) {
    Intervention intervention = new Intervention();
    intervention.setDate(dateTime.toLocalDate());
    intervention.setAgent(this);
    intervention.setChambre(chambre);
    
    this.chambreAssignee = chambre;
    chambre.setAgentAssigne(this);
    
    hotel.ajouterIntervention(intervention);
}
 public void terminerIntervention() {
        if (chambreAssignee != null) {
            chambreAssignee.setEstNettoyee(true);
            this.statusIntervention = "Terminée";
            this.chambreAssignee = null;
        }
    }

    public Chambre getChambreAssignee() {
        return chambreAssignee;
    }

    public String getStatusIntervention() {
        return statusIntervention;
    }


}