package Model;


public class ChambreSimple extends Chambre {

        public double tarif;
    private Hotel hotel;

    public ChambreSimple() {
        super(0, 0, "Simple", 0.0); // Appel du constructeur parent avec des valeurs par défaut
        this.hotel = null;
    }

    public ChambreSimple(int numero, int num_etage, Hotel hotel, double prix) {
        super(numero, num_etage, "Simple", prix);
        this.hotel = hotel;
    }


    public double getTarif() {
            return tarif;
        }

        public void setTarif(double tarif) {
            this.tarif = tarif;
        }

        public void afficherDetails() {
            super.afficherDetails(); // Appelle la méthode afficherDetails de la classe parente
            System.out.println("Tarif de la chambre : " + this.tarif);
        }
    }

