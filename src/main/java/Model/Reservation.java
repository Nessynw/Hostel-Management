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
    private static int compteur = 1;




    public Reservation(Client client, Chambre chambre, LocalDate date_deb, LocalDate date_fin) {
        this.id_res = compteur++;
        this.client = client;
        this.chambre = chambre;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
    }

    public int getId_res() {
        return id_res;
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

}