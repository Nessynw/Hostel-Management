package Model;
import java.time.LocalDate;
import java.util.*;



    public class Sejour extends Reservation {

        public Reservation res;
        public Vector<Consommation> listCons = new Vector<Consommation>();

        public Sejour() {
        }

        public Sejour(int id_res, LocalDate date_deb, LocalDate date_fin, Chambre chambre, Client client, Sejour sej, Receptionniste recept) {
            super(id_res, date_deb, date_fin, chambre, client, sej, recept);
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

            total = chambre.getTarif();
            total += calculerConsommation();


            return total;
        }
    }

