package Controler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class validerBtnControler implements ActionListener {




      private JPasswordField password;
        private Runnable onSuccess;
        private JFrame parent ;

        public  validerBtnControler (JPasswordField password, Runnable onSuccess, JFrame parent) {
            this.password = password;
            this.onSuccess = onSuccess;
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            char[] codepin = password.getPassword();  // Récupérer le code PIN
            if (String.valueOf(codepin).equals("1234")) {
                onSuccess.run();  // Exécuter le callback si le code est correct
                parent.dispose();  // Fermer la fenêtre d'authentification
            } else {
                JOptionPane.showMessageDialog(null, "Code PIN incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

