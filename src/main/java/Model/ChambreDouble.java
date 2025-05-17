package Model;


public class ChambreDouble extends Chambre {
    private Hotel hotel;
    public double tarif;
    public ChambreDouble() {
        super(0, 0, "Double", 0.0);
        this.hotel = null;
    }

    public ChambreDouble(int numero, int num_etage, Hotel hotel, double prix) {
        super(numero, num_etage, "Double", prix);
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
        System.out.println("Tarif de la chambre double : " + this.tarif);
    }
}