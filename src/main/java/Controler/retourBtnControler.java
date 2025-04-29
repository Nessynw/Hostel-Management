package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class retourBtnControler implements ActionListener {
    JFrame f ;
    public retourBtnControler(JFrame f) {
        this.f = f;
    }
    public void actionPerformed(ActionEvent e) {
       f. dispose();  // Fermer la fenêtre d'authentification
    }
}

