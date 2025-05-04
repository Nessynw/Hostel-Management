package Vue;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateReservation extends JFrame {
    private static final Color main_color = new Color(18, 11, 61); // Base color
    private static final Color text_color = Color.WHITE;
    private static final Color fieldColor = new Color(33, 33, 33);
    private static final Font fieldFont = new Font("Arial", Font.PLAIN, 14);

    String date_debut;
    String date_fin;

    public DateReservation() {
        setLayout(new GridBagLayout());
        this.getContentPane().setBackground(main_color);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // marges autour des composants
        gbc.fill = GridBagConstraints.HORIZONTAL; // chaque composant s'étire horizontalement si possible
        gbc.anchor = GridBagConstraints.CENTER;   // et est centré dans sa case

        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Réservation d'une chambre");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(text_color);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Start Date
        JLabel startDateLabel = new JLabel("Date de début:");
        startDateLabel.setForeground(Color.WHITE);
        JTextField startDateField = new JTextField(20);
        startDateField.setBackground(fieldColor);
        startDateField.setForeground(Color.WHITE);
        startDateField.setFont(fieldFont);
        JButton startDateButton = createCalendarButton(startDateField);

        gbc.gridy = 1;
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        add(startDateLabel, gbc);

        gbc.gridx = 1;
        add(startDateField, gbc);

        gbc.gridx = 2;
        add(startDateButton, gbc);

        // End Date
        JLabel endDateLabel = new JLabel("Date de fin:");
        endDateLabel.setForeground(Color.WHITE);
        JTextField endDateField = new JTextField(20);
        endDateField.setBackground(fieldColor);
        endDateField.setForeground(Color.WHITE);
        endDateField.setFont(fieldFont);
        JButton endDateButton = createCalendarButton(endDateField);

        gbc.gridy = 2;

        gbc.gridx = 0;
        add(endDateLabel, gbc);

        gbc.gridx = 1;
        add(endDateField, gbc);

        gbc.gridx = 2;
        add(endDateButton, gbc);

        // "Previous" and "Next" Buttons
        JButton prevButton = new JButton("Précédent");
        prevButton.setBackground(new Color(58, 51, 124));
        prevButton.setForeground(Color.WHITE);
        prevButton.setFont(new Font("Arial", Font.BOLD, 16));
        prevButton.setPreferredSize(new Dimension(150, 40));
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });

        JButton nextButton = new JButton("Suivant");
        nextButton.setBackground(new Color(58, 51, 124));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setPreferredSize(new Dimension(150, 40));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                date_debut = startDateField.getText();
                date_fin = endDateField.getText();
                if (date_debut.isEmpty() || date_fin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    ChambreSimple chambreSimple = new ChambreSimple(true);
                    chambreSimple.setVisible(true);
                    setVisible(false);
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });

        // Create a panel to hold both buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // 20 px spacing between buttons
        buttonPanel.setBackground(main_color); // To keep consistent background
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        // Add the panel to the frame
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;  // Span across all columns
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Set the title, size, and close operation
        setTitle("Réservation");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
    }

    // Method to create a button that opens a calendar
    private JButton createCalendarButton(JTextField dateField) {
        JButton calendarButton = new JButton("C");
        calendarButton.setBackground(new Color(58, 51, 124));
        calendarButton.setForeground(Color.WHITE);
        calendarButton.setFont(new Font("Arial", Font.BOLD, 16));
        calendarButton.setPreferredSize(new Dimension(40, 40));
        calendarButton.setBorderPainted(false);
        calendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCalendar calendar = new JCalendar();
                int option = JOptionPane.showConfirmDialog(null, calendar, "Sélectionner une date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (option == JOptionPane.OK_OPTION) {
                    Date selectedDate = calendar.getDate();
                    String formattedDate = formatDate(selectedDate);
                    dateField.setText(formattedDate);  // Insert the formatted date into the text field
                }
            }
        });
        return calendarButton;
    }

    // Method to format the date in dd/MM/yyyy
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());  // Set Look and Feel globally here
            DateReservation reservationForm = new DateReservation();
            reservationForm.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
