package Model;
import java.time.LocalDate;
import java.util.*;



    public class Sejour extends Reservation {

        public Reservation res;
        public Vector<Consommation> listCons = new Vector<Consommation>();



        public Sejour ( Client client, Chambre chambre, LocalDate date_deb, LocalDate date_fin) {
            super(client, chambre, date_deb, date_fin);
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
            total = chambre.getPrix();  // Au lieu de getTarif()
            total += calculerConsommation();
            return total;
        }
        public String toString() {
            // Retourner les informations pertinentes du séjour
            return "- Chambre " + chambre.getNumero();
            // Adaptez cet exemple selon vos besoins
        }

    }