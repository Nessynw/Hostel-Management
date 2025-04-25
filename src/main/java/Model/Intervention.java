package Model;
import java.time.LocalDate;




    public class Intervention {

        public LocalDate date;
        public AgentE agent;
        public Chambre chambre;

        public Intervention() {
        }

        public Intervention(LocalDate d, AgentE a, Chambre c) {
            date = d;
            agent = a;
            chambre = c;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public AgentE getAgent() {
            return agent;
        }

        public void setAgent(AgentE agent) {
            this.agent = agent;
        }

        public Chambre getChambre() {
            return chambre;
        }

        public void setChambre(Chambre chambre) {
            this.chambre = chambre;
        }

        public void afficherDetails() {
            System.out.println("Date de l'intervention: " + this.date);
            System.out.println("Agent: " + (this.agent != null ? this.agent.getNom() : "Aucun agent"));
            System.out.println("Chambre: " + (this.chambre != null ? this.chambre.getNum_chambre() : "Aucune chambre"));
        }

    }

