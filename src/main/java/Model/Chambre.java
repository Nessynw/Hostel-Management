package Model;

import java.util.*;
import  java.time.LocalDate;

public abstract class Chambre {

    public int num_chambre;
    public int num_etage;
    public Vector<Reservation> listReservation = new Vector<Reservation>();
    public Hotel hotel;
    public Vector<Intervention> listInter = new Vector<Intervention>();

    public Chambre() {
    }

    public Chambre(int num_chambre, int num_etage, Hotel hotel) {
        this.num_chambre = num_chambre;
        this.num_etage = num_etage;
        this.hotel = hotel;
    }
    public abstract double getTarif();

    public int getNum_chambre() {
        return num_chambre;
    }

    public void setNum_chambre(int num_chambre) {
        this.num_chambre = num_chambre;
    }

    public int getNum_etage() {
        return num_etage;
    }

    public void setNum_etage(int num_etage) {
        this.num_etage = num_etage;
    }

    public Vector<Reservation> getListReservation() {
        return listReservation;
    }

    public void setListReservation(Vector<Reservation> listReservation) {
        this.listReservation = listReservation;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Vector<Intervention> getListInter() {
        return listInter;
    }

    public void setListInter(Vector<Intervention> listInter) {
        this.listInter = listInter;
    }

    public void addReservation(Reservation reservation) {
        this.listReservation.add(reservation);
    }

    public void addIntervention(Intervention intervention) {
        this.listInter.add(intervention);
    }

    public void afficherDetails() {
        System.out.println("Numéro de chambre: " + this.num_chambre);
        System.out.println("Numéro d'étage: " + this.num_etage);
        System.out.println("Hôtel: " + (this.hotel != null ? this.hotel.getNom() : "Aucun hôtel associé"));
        System.out.println("Nombre de réservations: " + this.listReservation.size());
        System.out.println("Nombre d'interventions: " + this.listInter.size());
    }
    public boolean isAvailable(LocalDate dateDebut, LocalDate dateFin) {
        for (Reservation r : listReservation) {
            if (!(dateFin.isBefore(r.getDate_deb()) || dateDebut.isAfter(r.getDate_fin()))) {
                return false;
            }
        }
        return true;
    }
    public boolean isAvailableNow() {
        // Utiliser la date d'aujourd'hui pour vérifier si la chambre est disponible
        LocalDate today = LocalDate.now();
        return isAvailable(today, today); // Appel à la méthode isAvailable avec la même date de début et de fin
    }
    public void ajouterReservation(Reservation r) {
        listReservation.add(r);
    }


}
