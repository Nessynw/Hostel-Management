package Model;


public class Employe extends Personne {

    private  static int id_Emp=0;
    private double salaire;
    private Hotel hotel;



    public Employe(String nom, String prenom, String email, String tel,String adr ,  double s, Hotel h) {
        super( nom, prenom, email, tel,adr);
        id_Emp ++;
        salaire = s;
        hotel = h;
    }
        public int getId_Emp() {
            return id_Emp;
        }



        public void setId_Emp(int id_Emp) {
            this.id_Emp = id_Emp;
        }

        public double getSalaire() {
            return salaire;
        }

        public void setSalaire(double salaire) {
            this.salaire = salaire;
        }

        public Hotel getHotel() {
            return hotel;
        }

        public void setHotel(Hotel hotel) {
            this.hotel = hotel;
        }

        public void afficherDetails() {
            System.out.println("ID Employé: " + this.id_Emp);
            System.out.println("Salaire: " + this.salaire);
            System.out.println("Hôtel: " + (this.hotel != null ? this.hotel.getNom() : "Aucun hôtel associé"));
        }

    }