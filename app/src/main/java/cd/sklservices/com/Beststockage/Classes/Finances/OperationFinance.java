package cd.sklservices.com.Beststockage.Classes.Finances;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.AmountModelBase;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Classes.Registres.User;

@Entity(tableName = "operation_finance",
        foreignKeys = { @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "user_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Fournisseur.class,parentColumns = "id",childColumns = "fournisseur_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "devise_id",onUpdate = ForeignKey.CASCADE)
            }

)

public class OperationFinance extends AmountModelBase {
    @PrimaryKey @NonNull
    private String id;
    private String agence_id;
    private String user_id;
    private String fournisseur_id;
    private String categorie;
    private String date;
    private String type;
    private String observation;
    private String insert_mode;
    private int is_caisse_checked;
    private int is_stock_checked;
    private int is_user_confirmed;
    private String checking_agence_id;

    @Ignore
    private Agence agence;
    @Ignore
    private Fournisseur fournisseur;

    @Ignore
    public OperationFinance(){

    }
    @Ignore
    public OperationFinance( @NonNull String id, String agence_id, String fournisseur_id, String devise_id, String categorie, String date, String type, Double montant, String observation, String insert_mode, int is_caisse_checked, int is_stock_checked, int is_user_confirmed,String adding_user_id, String adding_agence_id,String checking_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(montant,0.0,0.0,devise_id,"","",adding_user_id,adding_agence_id,adding_date,updated_date,sync_pos,pos);
        this.id = id;
        this.agence_id = agence_id;
        this.categorie = categorie;
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.observation = observation;
        this.insert_mode = insert_mode;
        this.fournisseur_id = fournisseur_id;
        this.is_caisse_checked = is_caisse_checked;
        this.is_user_confirmed = is_user_confirmed;
        this.checking_agence_id=checking_agence_id;
    }

    public OperationFinance( @NonNull String id, String agence_id,String user_id, String fournisseur_id, String devise_id,String local_devise_id,String convert_devise_id, String categorie, String date, String type, Double montant, Double montant_local, Double converted_amount, String observation, String insert_mode, int is_caisse_checked, int is_stock_checked, int is_user_confirmed,String adding_user_id, String adding_agence_id,String checking_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(montant,montant_local,converted_amount,devise_id,local_devise_id,convert_devise_id,adding_user_id,adding_agence_id,adding_date,updated_date,sync_pos,pos);
        this.id = id;
        this.agence_id = agence_id;
        this.user_id=user_id;
        this.fournisseur_id = fournisseur_id;
        this.categorie = categorie;
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.observation = observation;
        this.insert_mode = insert_mode;
        this.is_caisse_checked = is_caisse_checked;
        this.is_stock_checked = is_stock_checked;
        this.is_user_confirmed = is_user_confirmed;
        this.checking_agence_id=checking_agence_id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getAgence_id() {
        return agence_id;
    }

    public void setAgence_id(String agence_id) {
        this.agence_id = agence_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFournisseur_id() {
        return fournisseur_id;
    }

    public void setFournisseur_id(String fournisseur_id) {
        this.fournisseur_id = fournisseur_id;
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {
        this.devise_id = devise_id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getInsert_mode() {
        return insert_mode;
    }

    public void setInsert_mode(String insert_mode) {
        this.insert_mode = insert_mode;
    }

    public int getIs_caisse_checked() {
        return is_caisse_checked;
    }

    public void setIs_caisse_checked(int is_caisse_checked) {
        this.is_caisse_checked = is_caisse_checked;
    }

    public int getIs_stock_checked() {
        return is_stock_checked;
    }

    public void setIs_stock_checked(int is_stock_checked) {
        this.is_stock_checked = is_stock_checked;
    }

    public int getIs_user_confirmed() {
        return is_user_confirmed;
    }

    public void setIs_user_confirmed(int is_user_confirmed) {
        this.is_user_confirmed = is_user_confirmed;
    }

    public String getChecking_agence_id() {
        return checking_agence_id;
    }

    public void setChecking_agence_id(String checking_agence_id) {
        this.checking_agence_id = checking_agence_id;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
        this.setAgence_id(agence.getId());
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
        this.setFournisseur_id(fournisseur.getId());
    }
}
