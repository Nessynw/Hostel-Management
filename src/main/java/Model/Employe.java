package Model;


public class Employe extends Personne {

    private  static int id_Emp=0;

    private Hotel hotel;



    public Employe(String nom, String prenom, String email, String tel,String adr ,   Hotel h) {
        super( nom, prenom, email, tel,adr);
        id_Emp ++;

        hotel = h;
    }




        public Hotel getHotel() {
            return hotel;
        }

        public void setHotel(Hotel hotel) {
            this.hotel = hotel;
        }

        public void afficherDetails() {
            System.out.println("ID Employé: " + this.id_Emp);

            System.out.println("Hôtel: " + (this.hotel != null ? this.hotel.getNom() : "Aucun hôtel associé"));
        }

    }