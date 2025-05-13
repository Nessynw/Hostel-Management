package Model;
import java.util.*;
import java.time.*;

/**
 *
 */
public class Hotel {

    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private int nbEtoiles;
    private Vector<Chambre> listChambre = new Vector<Chambre>();
    private Vector<Client> listClient = new Vector<Client>();
    private Vector<Produit> listProduit = new Vector<Produit>();
    private Vector<Employe> listEmploye = new Vector<Employe>();
    private List<Employe> listeEmployes;


    public Hotel(String n, String adrs, String tel, String eml, int nbrEt) {
        nom = n;
        adresse = adrs;
        telephone = tel;
        email = eml;
        nbEtoiles = nbrEt ;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public Vector<Chambre> getListChambre() {
        return listChambre;
    }

    public void setListChambre(Vector<Chambre> listChambre) {
        this.listChambre = listChambre;
    }

    public Vector<Client> getListClient() {
        return listClient;
    }

    public void setListClient(Vector<Client> listClient) {
        this.listClient = listClient;
    }

    public Vector<Produit> getListProduit() {
        return listProduit;
    }

    public void setListProduit(Vector<Produit> listProduit) {
        this.listProduit = listProduit;
    }

    public Vector<Employe> getListEmploye() {
        return listEmploye;
    }

    public void setListEmploye(Vector<Employe> listEmploye) {
        this.listEmploye = listEmploye;
    }

    public void ajouterChambre(Chambre c) {
        listChambre.add(c);
    }

    public void ajouterClient(Client c) {
        listClient.add(c);
    }

    public void ajouterProduit(Produit p) {
        listProduit.add(p);
    }

    public void ajouterEmploye(Employe e) {
        listEmploye.add(e);
    }
    public void afficherListeClients() {
        if (listClient.isEmpty()) {
            System.out.println("📭 Aucun client enregistré dans l'hôtel " + nom + ".");
        } else {
            System.out.println("🧾 Liste des clients de l'hôtel " + nom + " :");
            for (Client client : listClient) {
                System.out.println(client); // suppose que toString() est bien défini dans Client
            }
        }
    }
    public Chambre rechercherChambreDisponible(LocalDate debut, LocalDate fin) {
        for (Chambre c : listChambre) {
            boolean disponible = true;
            for (Reservation r : c.getListReservation()) {
                if (!(fin.isBefore(r.getDate_deb()) || debut.isAfter(r.getDate_fin()))) {
                    disponible = false;
                    break;
                }
            }
            if (disponible) return c;
        }
        return null;
    }


    public Vector<Chambre> getChambres() {
        return listChambre;
    }

    public List<Employe> getListeEmployes() {
        return listeEmployes;
    }
    /** Renvoie la chambre dont le numéro est num, ou null si non trouvée. */
    public Chambre getChambreParNumero(int num) {
        for (Chambre c : listChambre) {
            if (c.getNum_chambre() == num) return c;
        }
        return null;
    }
}

