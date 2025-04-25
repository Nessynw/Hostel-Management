package Vue;
import javax.swing.*;
import java.awt.*;

public class ClientButton extends JButton {

    // Constructeur pour initialiser le bouton
    public ClientButton(String text) {
        super(text); // Appel du constructeur parent pour définir le texte du bouton

        // Définir les propriétés du bouton
        this.setOpaque(true);
        this.setBackground(new Color(9, 0, 91)); // Couleur de fond initiale
        this.setForeground(Color.WHITE); // Couleur du texte\
        this.setFont(new Font("Arial", Font.PLAIN, 30));
        this.setPreferredSize(new Dimension(200, 60)); // Taille du bouton

        // Ajouter l'effet de survol (hover)
        this.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent e){
                setBackground(new Color(58, 51, 124)); // Couleur au survol
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(new Color(9, 0, 91));  // Revenir à la couleur d'origine
            }
        });
    }
}
