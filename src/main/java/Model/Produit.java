package Model;
import java.util.*;



    public class Produit {

        public String nom;
        public double prix;
        public Hotel hotel;
        public Vector<Consommation> listCon = new Vector<Consommation>();




        public Produit(String nom, double prix) {
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


        public Hotel getHotel() {
            return hotel;
        }

        public void setHotel(Hotel hotel) {
            this.hotel = hotel;
        }


    }


