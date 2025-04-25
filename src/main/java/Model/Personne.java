package Model;


public class Personne {
       private String nom;
       private String prenom;
        private String email;
       private String tel;
       private String  adresse ;
        public Personne() {
            this.nom = "";
            this.prenom = "";
            this.email = "";
            this.tel = "";
        }
        public Personne(String nom, String prenom, String email, String tel, String a ) {
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.tel = tel;
            this.adresse = a;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    public String toString() {
        return prenom + " " + nom;
    }

    }


