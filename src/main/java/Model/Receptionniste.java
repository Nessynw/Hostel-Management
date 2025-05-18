package Model;

import java.util.ArrayList;

public class Receptionniste extends Employe {
    private ArrayList<Reservation> Res = new ArrayList<>();

    public Receptionniste(String nom, String prenom, String email,String tel, String adresse,  Hotel hotel) {
        super( nom,  prenom,  email,  tel, adresse ,  hotel);
    }





    public void afficherDetails() {
        super.afficherDetails();
        System.out.println("Nombre de réservations gérées : " + this.Res.size());
    }


}
