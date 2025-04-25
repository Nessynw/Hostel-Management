package Model;

import java.util.*;

public class Receptionniste extends Employe {

    public Vector<Reservation> Res = new Vector<Reservation>();

    public Receptionniste() {
    }

    public Receptionniste(int id_Emp, double salaire, Hotel hotel, Vector<Reservation> Res) {
        super(id_Emp, salaire, hotel); // Appelle le constructeur de la classe parente Employe
        this.Res = Res;
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