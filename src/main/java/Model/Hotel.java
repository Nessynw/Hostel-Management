package Model;
import java.util.*;
import java.time.*;


public class
Hotel {

    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private int nbEtoiles;
    private Vector<Chambre> listChambre = new Vector<Chambre>();
    private Vector<Client> listClient = new Vector<Client>();
    private Vector<Produit> listProduit = new Vector<Produit>();
    private Vector<Employe> listEmploye = new Vector<Employe>();
    private Vector<Sejour>listSejours = new Vector<>();
    private List<Intervention> interventions;



    public Hotel(String n, String adrs, String tel, String eml, int nbrEt) {
        nom = n;
        adresse = adrs;
        telephone = tel;
        email = eml;
        nbEtoiles = nbrEt ;
        this.interventions = new ArrayList<>();

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Vector<Chambre> getListChambre() {
        return listChambre;
    }



    public Vector<Client> getListClient() {
        return listClient;
    }



    public Vector<Produit> getListProduit() {
        return listProduit;
    }


    public Vector<Employe> getListEmploye() {
        return listEmploye;
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


    public Vector<Chambre> getChambres() {
        return listChambre;
    }


    public void ajouterSejour(Sejour s) { listSejours.add(s);}
    public Vector<Sejour> getListSejour() { return listSejours;}
    public Chambre getChambreParNumero(int num) {
        for (Chambre c : listChambre) {
            if (c.getNum_chambre() == num) return c;
        }
        return null;
    }
    public void ajouterIntervention(Intervention intervention) {
        if (intervention != null) {
            interventions.add(intervention);
        }
    }



}