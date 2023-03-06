package cd.sklservices.com.Beststockage.Classes.Parametres;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBaseXR;

public class Compte extends ModelBaseXR {
    @PrimaryKey
    @NonNull
    private String id;
    private String enterprise_id;
    private String compte_general;
    private String classe;
    private String categorie;
    private String designation;
    private String nature;
    private boolean refus_ecriture;
    private String sens;
    private boolean utilisable;
    private boolean can_convert;
    private boolean ventilable;

    public Compte(@NonNull String id, String enterprise_id, String compte_general, String classe, String categorie, String designation, String nature, boolean refus_ecriture, String sens, boolean utilisable, boolean can_convert, boolean ventilable,String adding_user_id, String last_update_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, last_update_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.enterprise_id = enterprise_id;
        this.compte_general = compte_general;
        this.classe = classe;
        this.categorie = categorie;
        this.designation = designation;
        this.nature = nature;
        this.refus_ecriture = refus_ecriture;
        this.sens = sens;
        this.utilisable = utilisable;
        this.can_convert = can_convert;
        this.ventilable = ventilable;
    }
}
