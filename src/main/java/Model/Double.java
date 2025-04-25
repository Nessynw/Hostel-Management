package Model;


public class Double extends Chambre {

        public double tarif;

        public Double() {
        }

        public Double(int num_chambre, int num_etage, Hotel hotel, double tarif) {
            super(num_chambre, num_etage, hotel); // Appelle le constructeur de la classe Chambre
            this.tarif = tarif;
        }

        public double getTarif() {
            return tarif;
        }

        public void setTarif(double tarif) {
            this.tarif = tarif;
        }

        public void afficherDetails() {
            super.afficherDetails(); // Appelle la méthode afficherDetails de la classe parente
            System.out.println("Tarif de la chambre double : " + this.tarif);
        }
    }

