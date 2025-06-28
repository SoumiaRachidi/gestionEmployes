package Gestion;

import java.util.Date;

public class Admin extends Employe{

    public Admin(String nom_employe, String prenom_employe, String email_employe, int numero_employe, Date datenaissance_employe, String departement_employe) {
        super(nom_employe, prenom_employe, email_employe, numero_employe, datenaissance_employe, departement_employe);
    }


}
