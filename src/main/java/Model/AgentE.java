package Model;
import java.util.*;



    public class AgentE extends Employe {

        public Vector<Intervention> listInter = new Vector<Intervention>();

        public AgentE() {
        }

        public AgentE(int id_Emp, double salaire, Hotel hotel, Vector<Intervention> listInter) {
            super(id_Emp, salaire, hotel); // Appelle le constructeur de la classe parente Employe
            this.listInter = listInter;
        }

        public Vector<Intervention> getListInter() {
            return listInter;
        }

        public void setListInter(Vector<Intervention> listInter) {
            this.listInter = listInter;
        }

        public void addIntervention(Intervention intervention) {
            this.listInter.add(intervention);
        }

        public void afficherDetails() {
            super.afficherDetails(); // Appelle la méthode afficherDetails de la classe parente
            System.out.println("Nombre d'interventions effectuées : " + this.listInter.size());
        }
        public void ajouterIntervention(Intervention i) {
            listInter.add(i);
        }
    }

