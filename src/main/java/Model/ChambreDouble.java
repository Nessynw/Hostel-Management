package Model;


public class ChambreDouble extends Chambre {
    private Hotel hotel;
    public double tarif;


    public ChambreDouble(int numero, int num_etage, Hotel hotel, double prix) {
        super(numero, num_etage, "Double", prix);
        this.hotel = hotel;
    }




    public void afficherDetails() {
        super.afficherDetails();
        System.out.println("Tarif de la chambre double : " + this.tarif);
    }
}