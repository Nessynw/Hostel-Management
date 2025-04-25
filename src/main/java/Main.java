import Vue.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        // Créer la fenêtre principale (JFrame)
        JFrame frame = new JFrame("Hôtel Blue Castle");


        // Ajouter le panneau Accueil à la fenêtre
        frame.setContentPane(new Accueil(frame));  // Remplacer par le panneau Accueil

        // Maximiser la fenêtre à l'ouverture pour plein écran
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximiser la fenêtre

        // Fermer l'application à la fermeture de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }
}