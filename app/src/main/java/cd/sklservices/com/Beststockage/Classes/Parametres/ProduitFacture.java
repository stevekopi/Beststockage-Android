package cd.sklservices.com.Beststockage.Classes.Parametres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBaseXR;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.User;

@Entity(tableName = "produit_facture",indices = {@Index("id")},
        foreignKeys = {
                @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "last_update_user_id",onUpdate = ForeignKey.CASCADE)}
)

public class ProduitFacture extends ModelBaseXR {
    @PrimaryKey
    @NonNull
    private String id;
    private String second_id;
    private String designation;
    private String type;
    private boolean with_bonus;
    private String sens_stock;
    private String sens_finance;
    private boolean default_checked;
    private boolean default_confirmed;

    public ProduitFacture(@NonNull String id, String second_id, String designation, String type, boolean with_bonus, String sens_stock, String sens_finance, boolean default_checked, boolean default_confirmed,String adding_user_id, String last_update_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, last_update_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.second_id = second_id;
        this.designation = designation;
        this.type = type;
        this.with_bonus = with_bonus;
        this.sens_stock = sens_stock;
        this.sens_finance = sens_finance;
        this.default_checked = default_checked;
        this.default_confirmed = default_confirmed;
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

    public boolean isWith_bonus() {
        return with_bonus;
    }

    public void setWith_bonus(boolean with_bonus) {
        this.with_bonus = with_bonus;
    }

    public String getSens_stock() {
        return sens_stock;
    }

    public void setSens_stock(String sens_stock) {
        this.sens_stock = sens_stock;
    }

    public String getSens_finance() {
        return sens_finance;
    }

    public void setSens_finance(String sens_finance) {
        this.sens_finance = sens_finance;
    }

    public boolean isDefault_checked() {
        return default_checked;
    }

    public void setDefault_checked(boolean default_checked) {
        this.default_checked = default_checked;
    }

    public boolean isDefault_confirmed() {
        return default_confirmed;
    }

    public void setDefault_confirmed(boolean default_confirmed) {
        this.default_confirmed = default_confirmed;
    }
}
