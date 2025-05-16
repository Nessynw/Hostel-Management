import Model.*;
import Model.ChambreDouble;
import Vue.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } //
        // Créer la fenêtre principale (JFrame)
        JFrame frame = new JFrame("Hôtel Blue Castle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Hotel hotel = new Hotel("BlueCastel","11 e Paris 75001","0104010504","BlueCastle@gmail.fr", 4);
        Client client1 = new Client("Ali", "Bencheikh", "ali.bencheikh@gmail.com", "0612345678", "12 rue des Fleurs", hotel);
        Client client2 = new Client("Sara", "Bouzid", "sara.bouzid@gmail.com", "0698765432", "45 avenue Victor Hugo", hotel);
        Client client3 = new Client("Mehdi", "Kacem", "mehdi.kacem@gmail.com", "0789456123", "78 boulevard Haussmann", hotel);
        // Ajout des clients à l'hôtel
        hotel.ajouterClient(client1);
        hotel.ajouterClient(client2);
        hotel.ajouterClient(client3);
        hotel.ajouterChambre(new ChambreDouble(320, 1,hotel, 130));
        hotel.ajouterChambre(new ChambreDouble(323, 1,hotel, 130));
        hotel.ajouterChambre(new ChambreDouble(450, 2,hotel ,  150));
        hotel.ajouterChambre(new ChambreSimple(101, 1, hotel, 109));
        hotel.ajouterChambre(new ChambreSimple(102, 1, hotel, 127));
        hotel.ajouterChambre(new ChambreSimple(103, 1, hotel,  81));
        hotel.ajouterChambre(new ChambreSimple(104, 1, hotel, 167));
        hotel.ajouterChambre(new ChambreSimple(105, 2, hotel, 131));
        hotel.ajouterChambre(new ChambreSimple(106, 2, hotel, 141));
        hotel.ajouterChambre(new ChambreSimple(107, 2, hotel, 182));
        hotel.ajouterChambre(new ChambreSimple(108, 2, hotel, 158));
        // Ajouter le panneau Accueil à la fenêtre
        frame.setContentPane(new Accueil(frame , hotel));  // Remplacer par le panneau Accueil


        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }}