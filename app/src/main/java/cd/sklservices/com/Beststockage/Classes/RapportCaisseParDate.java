package cd.sklservices.com.Beststockage.Classes;


/**
 * Created by Steve Kopi Loseme on 08/02/2021.
 */



public class RapportCaisseParDate {

    public String date_operation;
    public double montant;
    public double depense;
    public double reste;
    public String agence_id;

    public RapportCaisseParDate() {
    }

    public RapportCaisseParDate(String date1, double montantBrut, double depense) {
        this.date_operation = date1;
        this.montant = montantBrut;
        this.depense = depense;
        this.reste= montant - depense;
    }

    public RapportCaisseParDate(String date1, double montantBrut, double depense, String agence_Id) {
        this(date1,montantBrut,depense);
        this.agence_id=agence_Id;
    }

    public void setDate(String date1) {
        date_operation = date1;
    }

    public void setMontantBrut(int montantBrut) {
        montant = montantBrut;
    }

    public void setDepense(int depense) {
        depense = depense;
    }

    public String getDate() {
        return date_operation;
    }

    public double getMontantBrut() {
        return montant ;
    }

    public double getDepense() {
        return depense;
    }

    public double getReste() {
        return reste;
    }

    public String getAgence_Id() {
        return agence_id;
    }
}
