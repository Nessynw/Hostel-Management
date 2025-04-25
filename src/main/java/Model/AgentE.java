package Model;
import java.util.*;



    public class AgentE extends Employe {

        public Vector<Intervention> listInter ;



        public AgentE(int id_Emp, String nom, String prenom, String email, String tel,String adresse ,  double salaire, Hotel hotel) {
            super( nom, prenom, email, tel, adresse , salaire, hotel);
                listInter = new Vector<>();
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

