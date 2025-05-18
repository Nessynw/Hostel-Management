package Model;


public class Personne {

       private String nom;
       private String prenom;
        private String email;
       private String tel;
       private String  adresse ;

    public Personne( String n, String pr , String eml, String tl,String adr) {
        nom = n;

        prenom = pr;
        email = eml;
        tel = tl;
        adresse = adr;
    }
        public String getAdresse() {
        return adresse;
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

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}


