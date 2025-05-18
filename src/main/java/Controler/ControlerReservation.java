package Controler;

import Model.Hotel;
import Vue.AppColors;
import Vue.DateReservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class ControlerReservation implements MouseListener {
    private Hotel hotel;
    private JPanel chambrePanel;
    private String type;
    private static final Color hoverColor = new Color(58, 90, 153); // Hover color

    public ControlerReservation(Hotel hotel, JPanel chambrePanel, String type, Color hoverColor) {
        this.hotel = hotel;
        this.chambrePanel = chambrePanel;
        this.type = type;
        hoverColor = hoverColor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        DateReservation dr = new DateReservation(hotel, type);
        dr.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        chambrePanel.setBackground(hoverColor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        chambrePanel.setBackground(AppColors.MAIN_COLOR);
    }
}