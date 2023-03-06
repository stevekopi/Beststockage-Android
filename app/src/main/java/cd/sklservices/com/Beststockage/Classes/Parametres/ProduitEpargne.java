package cd.sklservices.com.Beststockage.Classes.Parametres;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBaseXR;

public class ProduitEpargne extends ModelBaseXR {
    @PrimaryKey
    @NonNull
    private String id;
    private String devise_id;
    private String compte_id;
    private String designation;

    public ProduitEpargne(@NonNull String id, String devise_id, String compte_id, String designation,String adding_user_id, String last_update_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, last_update_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.devise_id = devise_id;
        this.compte_id = compte_id;
        this.designation = designation;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {
        this.devise_id = devise_id;
    }

    public String getCompte_id() {
        return compte_id;
    }

    public void setCompte_id(String compte_id) {
        this.compte_id = compte_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
