package Model;
import java.util.*;



    public class Sejour extends Reservation {

        public Reservation res;
        public Vector<Consommation> listCons = new Vector<Consommation>();

        public Sejour() {
        }

        public Sejour(Reservation res, Vector<Consommation> listCons) {
            this.res = res;
            this.listCons = listCons;
        }

        public Reservation getRes() {
            return res;
        }

        public void setRes(Reservation res) {
            this.res = res;
        }

        public Vector<Consommation> getListCons() {
            return listCons;
        }

        public void setListCons(Vector<Consommation> listCons) {
            this.listCons = listCons;
        }

        public void addConsommation(Consommation consommation) {
            this.listCons.add(consommation);
        }

        public void afficherDetails() {
            System.out.println("Réservation ID: " + this.res.getId_res());
            System.out.println("Nombre de consommations: " + this.listCons.size());
        }
        public void ajouterConsommation(Consommation c) {
            listCons.add(c);
        }
        public double calculerConsommation() {
            double total = 0;
            for (Consommation c : listCons) {
                total += c.getProduit().getPrix() * c.getQuantite();
            }
            return total;
        }
        public double calculerPrixTotal() {
            double total = 0;

            total = chambre.getTarif(); // Pas besoin de instanceof
            total += calculerConsommation();


            return total;
        }

    }

