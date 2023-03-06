package cd.sklservices.com.Beststockage.Classes.Parametres;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBaseXR;

public class ProduitOperationEpargne extends ModelBaseXR {
    @PrimaryKey
    @NonNull
    private String id;
    private String second_id;
    private String designation;
    private String type;
    private String sens_finance;
    private boolean is_default_payment;

    public ProduitOperationEpargne(@NonNull String id, String second_id, String designation, String type, String sens_finance, boolean is_default_payment,String adding_user_id, String last_update_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, last_update_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.second_id = second_id;
        this.designation = designation;
        this.type = type;
        this.sens_finance = sens_finance;
        this.is_default_payment = is_default_payment;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSecond_id() {
        return second_id;
    }

    public void setSecond_id(String second_id) {
        this.second_id = second_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSens_finance() {
        return sens_finance;
    }

    public void setSens_finance(String sens_finance) {
        this.sens_finance = sens_finance;
    }

    public boolean isIs_default_payment() {
        return is_default_payment;
    }

    public void setIs_default_payment(boolean is_default_payment) {
        this.is_default_payment = is_default_payment;
    }
}
