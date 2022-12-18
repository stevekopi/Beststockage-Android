package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;

/**
 * Created by Steve Kopi Loseme on 12/02/2021.
 */

public class Performance  implements Comparable<Performance> {

    public String agence_1_id ;

    public Double montant ;

    public String devise_id ;

    public String nombre_vente;

    public String quantite_vente;

    public String nombre_livraison;

    public String quantite_livraison;

    public Performance(){}

    public Performance(String agence_1_id, Double montant, String devise_id, String nombre_vente, String quantite_vente, String nombre_livraison, String quantite_livraison) {
        this.agence_1_id = agence_1_id;
        this.montant = montant;
        this.devise_id = devise_id;
        this.nombre_vente = nombre_vente;
        this.quantite_vente = quantite_vente;
        this.nombre_livraison = nombre_livraison;
        this.quantite_livraison = quantite_livraison;
    }



    public String getAgence_1_id() {
        return agence_1_id;
    }

    public void setAgence_1_id(String agence_1_id) {
        this.agence_1_id = agence_1_id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getNombre_vente() {
        return nombre_vente;
    }

    public void setNombre_vente(String nombre_vente) {
        this.nombre_vente = nombre_vente;
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {
        this.devise_id = devise_id;
    }

    public String getQuantite_vente() {
        return quantite_vente;
    }

    public void setQuantite_vente(String quantite_vente) {
        this.quantite_vente = quantite_vente;
    }

    public String getNombre_livraison() {
        return nombre_livraison;
    }

    public void setNombre_livraison(String nombre_livraison) {
        this.nombre_livraison = nombre_livraison;
    }

    public String getQuantite_livraison() {
        return quantite_livraison;
    }

    public void setQuantite_livraison(String quantite_livraison) {
        this.quantite_livraison = quantite_livraison;
    }

    @Override
    public int compareTo(@NonNull Performance o) {
        return 0;
    }
}
