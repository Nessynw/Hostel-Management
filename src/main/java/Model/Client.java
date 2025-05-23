package Model;

import java.util.*;

public class Client extends Personne {
    private int id_client;
   private static   int compteur = 0;
    private Vector<Reservation> listReservation = new Vector<>() ;
   private Hotel hotel;



    public Client( String nom, String prenom, String email, String tel,String a , Hotel hotel) {
        super(nom, prenom, email, tel, a);
        this.id_client =compteur;
        this.hotel = hotel;
        this.listReservation= new Vector<>();
        compteur++;
    }

    public int getId_client() {
        return id_client;
    }



    public Vector<Reservation> getListReservation() {
        return listReservation;
    }



    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }


    public void ajouterReservation(Reservation r) {
        listReservation.add(r);
    }
    public void annulerReservation(Reservation r) {
        listReservation.remove(r);
    }
    public String toString() {
        return super.toString() ;
    }

}
