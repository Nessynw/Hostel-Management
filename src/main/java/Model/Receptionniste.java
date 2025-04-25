package Model;

import java.util.*;

public class Receptionniste extends Employe {

    public Vector<Reservation> Res = new Vector<Reservation>();



    public Receptionniste( String nom, String prenom, String email, String tel,String a , double salaire, Hotel hotel) {
        super( nom, prenom, email, tel,a, salaire, hotel);
    }

    public Vector<Reservation> getRes() {
        return Res;
    }

    public void setRes(Vector<Reservation> Res) {
        this.Res = Res;
    }

    public void addReservation(Reservation reservation) {
        this.Res.add(reservation);
    }

    public void afficherDetails() {
        super.afficherDetails(); // Appelle la méthode afficherDetails de la classe parente
        System.out.println("Nombre de réservations gérées : " + this.Res.size());
    }
    public void ajouterReservation(Reservation r) {
        Res.add(r);
    }
}