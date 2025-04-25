package Model;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Vector;




    public class FactureReservation {

        private Reservation reservation;
        private double montantTotal;
        private Date dateFacture;
        private int numeroFacture;
        private static int compteur = 1;

        public FactureReservation(Reservation reservation) {
            this.reservation = reservation;
            this.dateFacture = new Date();
            this.numeroFacture = compteur++;
            this.montantTotal = calculerMontantTotal();
        }

        private double calculerMontantTotal() {
            double total = 0;

            // 1. Calculer nombre de nuits
            long nbNuits = ChronoUnit.DAYS.between(
                    reservation.getDate_deb(), reservation.getDate_fin()
            );

            // 2. Récupérer le tarif selon le type de chambre
            Chambre chambre = reservation.getChambre();
            double tarif = 0;
            if (chambre instanceof Simple) {
                tarif = ((Simple) chambre).getTarif();
            } else if (chambre instanceof Double) {
                tarif = ((Double) chambre).getTarif();
            }

            total += nbNuits * tarif;

            // 3. Ajouter les consommations du séjour
            Sejour sej = reservation.getSej();
            if (sej != null) {
                Vector<Consommation> consommations = sej.getListCons();
                for (Consommation c : consommations) {
                    Produit produit = c.getProduit();
                    if (produit != null) {
                        total += produit.getPrix() * c.getQuantite();
                    }
                }
            }

            return total;
        }

        public void afficherFacture() {
            System.out.println("----- FACTURE N°" + numeroFacture + " -----");
            System.out.println("Client : " + reservation.getClient().getNom());
            System.out.println("Chambre : " + reservation.getChambre().getNum_chambre());
            System.out.println("Du " + reservation.getDate_deb() + " au " + reservation.getDate_fin());
            System.out.println("Montant total : " + montantTotal + " €");
            System.out.println("Date de la facture : " + dateFacture);
            System.out.println("------------------------------");
        }

        public double getMontantTotal() {
            return montantTotal;
        }
    }
