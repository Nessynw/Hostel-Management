package Model;
import Model.*;

import java.util.ArrayList;

public class Receptionniste extends Employe {
    private ArrayList<Reservation> Res = new ArrayList<>();

    public Receptionniste(String nom, String prenom, String email,String tel, String adresse,  Hotel hotel) {
        super( nom,  prenom,  email,  tel, adresse ,  hotel);  // Call the superclass constructor with all arguments
    }

    public ArrayList<Reservation> getRes() {
        return Res;
    }

    public void addReservation(Reservation reservation) {
        this.Res.add(reservation);
    }

    public void afficherDetails() {
        super.afficherDetails();  // Calls the method from the parent class (Employe)
        System.out.println("Nombre de réservations gérées : " + this.Res.size());
    }

    public String getPoste() {
        return "Réceptionniste";
    }
}
