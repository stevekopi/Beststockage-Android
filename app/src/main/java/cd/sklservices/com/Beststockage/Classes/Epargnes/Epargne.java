package cd.sklservices.com.Beststockage.Classes.Epargnes;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBaseXR;

public class Epargne extends ModelBaseXR {
    @PrimaryKey
    @NonNull
    private String id;
    private String identity_id;
    private String produit_id;
    private String code_manuel;
    private boolean statut;

    public Epargne(@NonNull String id, String identity_id, String produit_id, String code_manuel, boolean statut,String adding_user_id, String last_update_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, last_update_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.identity_id = identity_id;
        this.produit_id = produit_id;
        this.code_manuel = code_manuel;
        this.statut = statut;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public String getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(String produit_id) {
        this.produit_id = produit_id;
    }

    public String getCode_manuel() {
        return code_manuel;
    }

    public void setCode_manuel(String code_manuel) {
        this.code_manuel = code_manuel;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }
}
