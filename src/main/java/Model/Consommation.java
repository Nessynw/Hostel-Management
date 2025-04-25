package Model;


public class Consommation {

        public int quantite;
        public Produit produit;
        public Sejour sej;

        public Consommation() {
        }

        public Consommation(int quantite, Produit produit, Sejour sej) {
            this.quantite = quantite;
            this.produit = produit;
            this.sej = sej;
        }

        public int getQuantite() {
            return quantite;
        }

        public void setQuantite(int quantite) {
            this.quantite = quantite;
        }

        public Produit getProduit() {
            return produit;
        }

        public void setProduit(Produit produit) {
            this.produit = produit;
        }

        public Sejour getSej() {
            return sej;
        }
        public void setSej(Sejour sej) {}
    }
