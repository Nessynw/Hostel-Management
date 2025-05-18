package Model;


public class Consommation {

        public int quantite;
        public Produit produit;
        public Sejour sej;



        public Consommation(int quantite, Produit produit, Sejour sej) {
            this.quantite = quantite;
            this.produit = produit;
            this.sej = sej;
        }

        public int getQuantite() {
            return quantite;
        }



        public Produit getProduit() {
            return produit;
        }

    }
