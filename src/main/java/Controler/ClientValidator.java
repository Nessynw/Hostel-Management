package Controler;

import java.time.LocalDate;

public class ClientValidator {

    // Vérifie si le nom et le prénom sont valides (lettres uniquement)
    public static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]+");
    }

    // Vérifie si le numéro de téléphone contient exactement 10 chiffres
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    // Vérifie si l'email contient un '@'
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
    public static boolean verifierCoherenceDates(LocalDate dateDebut, LocalDate dateFin) {
        return dateDebut != null && dateFin != null && dateDebut.isBefore(dateFin);
}
}