package Model;


public class ChambreSimple extends Chambre {

        public double tarif;
    private Hotel hotel;



    public ChambreSimple(int numero, int num_etage, Hotel hotel, double prix) {
        super(numero, num_etage, "Simple", prix);
        this.hotel = hotel;
    }


        public void afficherDetails() {
            super.afficherDetails();
            System.out.println("Tarif de la chambre : " + this.tarif);
        }
    }

