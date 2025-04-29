package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class retourBtnControler implements ActionListener {
    private Runnable action;

    public retourBtnControler(Runnable action) {
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (action != null) action.run();
    }
}
