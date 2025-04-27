import Model.Hotel;
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
        // Ajouter le panneau Accueil à la fenêtre
        frame.setContentPane(new Accueil(frame , hotel));  // Remplacer par le panneau Accueil

        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


}}