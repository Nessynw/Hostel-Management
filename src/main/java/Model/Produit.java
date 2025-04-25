package Model;
import java.util.*;



    public class Produit {

        public String nom;
        public double prix;
        public Hotel hotel;
        public Vector<Consommation> listCon = new Vector<Consommation>();

        public Produit() {
        }

        public Produit(String nom, double prix, Hotel hotel) {
            this.nom = nom;
            this.prix = prix;
            this.hotel = hotel;

        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public Hotel getHotel() {
            return hotel;
        }

        public void setHotel(Hotel hotel) {
            this.hotel = hotel;
        }

        public Vector<Consommation> getListCon() {
            return listCon;
        }

        public void setListCon(Vector<Consommation> listCon) {
            this.listCon = listCon;
        }

        public void addConsommation(Consommation consommation) {
            this.listCon.add(consommation);
        }

        public void afficherDetails() {
            System.out.println("Nom du produit: " + this.nom);
            System.out.println("Prix: " + this.prix);
            System.out.println("Nombre de consommations associées: " + this.listCon.size());
        }
        public boolean estDisponible() {
            return prix > 0;
        }
    }


