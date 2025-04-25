package Model;


public class Employe extends Personne {

        public int id_Emp;
        public double salaire;
        public Hotel hotel;

        public Employe() {
        }

        public Employe(int id_Emp, double salaire, Hotel hotel) {
            this.id_Emp = id_Emp;
            this.salaire = salaire;
            this.hotel = hotel;
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


