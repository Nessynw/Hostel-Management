package Vue;
import javax.swing.*;
import java.awt.*;

public class StyledButton extends JButton {
    public StyledButton(String text) {
        super(text); // Appel du constructeur parent pour définir le texte du bouton

        this.setOpaque(true);
        this.setBackground(new Color(29, 30, 106)); // Couleur de fond initiale
        this.setForeground(new Color(255, 255, 255)); // Couleur du texte\
        this.setFont(new Font("Arial", Font.PLAIN, 30));
        this.setPreferredSize(new Dimension(200, 60)); // Taille du bouton

        // Ajouter l'effet de survol (hover)
        this.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent e){
                setBackground(new Color(58, 90, 153)); // Couleur au survol
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(new Color(29, 30, 106));  // Revenir à la couleur d'origine
            }
        });
    }
}