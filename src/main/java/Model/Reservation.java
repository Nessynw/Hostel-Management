package Model;

import java.time.LocalDate;

public class Reservation {

    public int id_res;
    public LocalDate date_deb;
    public LocalDate date_fin;
    public Chambre chambre;
    public Client client;
    public Sejour sej;
    public Receptionniste recept;

    public Reservation() {
    }

    public Reservation(int id_res, LocalDate date_deb, LocalDate date_fin, Chambre chambre, Client client, Sejour sej, Receptionniste recept) {
        this.id_res = id_res;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.chambre = chambre;
        this.client = client;
        this.sej = sej;
        this.recept = recept;
    }

    public int getId_res() {
        return id_res;
    }

    public void setId_res(int id_res) {
        this.id_res = id_res;
    }

    public LocalDate getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(LocalDate date_deb) {
        this.date_deb = date_deb;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Sejour getSej() {
        return sej;
    }

    public void setSej(Sejour sej) {
        this.sej = sej;
    }

    public Receptionniste getRecept() {
        return recept;
    }

    public void setRecept(Receptionniste recept) {
        this.recept = recept;
    }

    public void afficherDetails() {
        System.out.println("ID Réservation: " + this.id_res);
        System.out.println("Date de début: " + this.date_deb);
        System.out.println("Date de fin: " + this.date_fin);
        System.out.println("Chambre réservée: " + (this.chambre != null ? this.chambre.getNum_chambre() : "Aucune chambre"));
        System.out.println("Client: " + (this.client != null ? this.client.getNom() : "Aucun client"));
    }
    public boolean estConfirmee() {
        return sej != null;
    }
}