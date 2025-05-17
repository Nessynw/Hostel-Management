package Model;
import java.time.LocalDate;


    public class Intervention {

        public LocalDate date;
        public AgentE agent;
        public Chambre chambre;


        public void setDate(LocalDate date) {
            this.date = date;
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


    }