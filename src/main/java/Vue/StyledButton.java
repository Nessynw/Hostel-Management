package Vue;
import javax.swing.*;
import java.awt.*;

public class StyledButton extends JButton {
    public StyledButton(String text) {
        super(text);

        this.setOpaque(true);
        this.setBackground(new Color(29, 30, 106));
        this.setForeground(new Color(255, 255, 255));
        this.setFont(new Font("Arial", Font.PLAIN, 30));
        this.setPreferredSize(new Dimension(200, 60));


        this.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent e){
                setBackground(new Color(58, 90, 153));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(new Color(29, 30, 106));
            }
        });
    }
}