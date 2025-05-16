package Model;

import java.util.Vector;
import java.time.LocalDate;

public class Chambre {
    private int numero;
    private int num_etage;  // Ajouter cet attribut
    private boolean estOccupee;
    private boolean estNettoyee;
    private AgentE agentAssigne;
    private String type;
    private double prix;
    private Vector<Reservation> listReservation;

    public Chambre(int numero, int num_etage, String type, double prix) {  // Modifier le constructeur
        this.numero = numero;
        this.num_etage = num_etage;  // Initialiser num_etage
        this.type = type;
        this.prix = prix;
        this.estOccupee = false;
        this.estNettoyee = true;
        this.agentAssigne = null;
        this.listReservation = new Vector<>();
    }

    // Getters

    public int getNum_chambre() {
        return numero;  // Retourne la même valeur que getNumero()
    }

    // Ajouter cette méthode getter
    public int getNum_etage() {
        return num_etage;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isEstOccupee() {
        return estOccupee;
    }

    public boolean isEstNettoyee() {
        return estNettoyee;
    }

    public AgentE getAgentAssigne() {
        return agentAssigne;
    }

    public String getType() {
        return type;
    }

    public double getPrix() {
        return prix;
    }

    public Vector<Reservation> getListReservation() {
        return listReservation;
    }

    // Setters
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setEstOccupee(boolean estOccupee) {
        this.estOccupee = estOccupee;
        if (!estOccupee) {
            this.estNettoyee = false;
        }
    }

    public void setEstNettoyee(boolean estNettoyee) {
        this.estNettoyee = estNettoyee;
    }

    public void setAgentAssigne(AgentE agent) {
        this.agentAssigne = agent;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setType(String type) {
        this.type = type;
    }



    @Override
    public String toString() {
        return "Chambre " + numero + " (" + type + ")";
    }
    public boolean isAvailable(LocalDate debut, LocalDate fin) {
        if (listReservation.isEmpty()) {
            return true;
        }

        for (Reservation reservation : listReservation) {
            // Si la nouvelle période chevauche une réservation existante
            if (!(fin.isBefore(reservation.getDate_deb()) || debut.isAfter(reservation.getDate_fin()))) {
                return false;
            }
        }
        return true;
    }

public void afficherDetails() {
        System.out.println("Numéro de chambre : " + numero);
        System.out.println("Type : " + type);
        System.out.println("Prix : " + prix);
        System.out.println("Occupée : " + (estOccupee ? "Oui" : "Non"));
        System.out.println("Nettoyée : " + (estNettoyee ? "Oui" : "Non"));
        if (agentAssigne != null) {
            System.out.println("Agent assigné : " + agentAssigne);
        }
    }
    // ... vos attributs et autres méthodes existants ...

    // Ajouter ces deux méthodes
    public void ajouterReservation(Reservation reservation) {
        listReservation.add(reservation);
        this.estOccupee = true;  // Met à jour le statut de la chambre
    }

    public void supprimerReservation(Reservation reservation) {
        listReservation.remove(reservation);
        if (listReservation.isEmpty()) {
            this.estOccupee = false;  // Si plus de réservations, la chambre n'est plus occupée
            this.estNettoyee = false; // La chambre doit être nettoyée
        }
    }

    // ... reste de votre code ...
}