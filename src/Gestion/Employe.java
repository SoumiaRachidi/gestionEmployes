package Gestion;
import java.util.Date;

public class Employe {

    static int id_employe=1;
    String nom_employe;
    String prenom_employe;
    String email_employe;
    int numero_employe;
    Date datenaissance_employe;
    String Departement_employe;

    public int getId_employe() {
        return id_employe;
    }

    public String getNom_employe() {
        return nom_employe;
    }
    public void setNom_employe(String nom_employe) {
        this.nom_employe = nom_employe;
    }

    public String getPrenom_employe() {
        return prenom_employe;
    }
    public void setPrenom_employe(String prenom_employe) {
        this.prenom_employe = prenom_employe;
    }
    public String getEmail_employe() {
        return email_employe;
    }
    public void setEmail_employe(String email_employe) {
        this.email_employe = email_employe;
    }
    public int getNumero_employe() {
        return numero_employe;
    }
    public void setNumero_employe(int numero_employe) {
        this.numero_employe = numero_employe;
    }
    public Date getDatenaissance_employe() {
        return datenaissance_employe;
    }
    public void setDatenaissance_employe(Date datenaissance_employe) {
        this.datenaissance_employe = datenaissance_employe;
    }
    public String getDepartement_employe() {return Departement_employe;}
    public void setDepartement_employe(String departement_employe) {
        Departement_employe = departement_employe;
    }
    public Employe(String nom_employe, String prenom_employe, String email_employe, int numero_employe, Date datenaissance_employe, String departement_employe) {
        id_employe ++;
        this.nom_employe = nom_employe;
        this.prenom_employe = prenom_employe;
        this.email_employe = email_employe;
        this.numero_employe = numero_employe;
        this.datenaissance_employe = datenaissance_employe;
        this.Departement_employe = departement_employe;
    }


}
