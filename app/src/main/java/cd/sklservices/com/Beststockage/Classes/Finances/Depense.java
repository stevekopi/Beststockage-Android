package cd.sklservices.com.Beststockage.Classes.Finances;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.AmountModelBase;
import cd.sklservices.com.Beststockage.Classes.ModelBaseX;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.User;

/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

@Entity(tableName = "depense",
        foreignKeys = { @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "devise_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = OperationFinance.class,parentColumns = "id",childColumns = "operation_finance_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE)}

)

public class Depense extends AmountModelBase implements Comparable<Depense> {

    @PrimaryKey @NonNull
    private String id ;

    private String agence_id ;

    private boolean is_checked;
    private String operation_finance_id ;
    private String date ;
    private String observation ;

    @Ignore
    private OperationFinance operationFinance;

    @Ignore
    public Depense(){
        super();
    //this.operationFinance=new OperationFinance();
    }

    public Depense( @NonNull String id, String agence_id, boolean is_checked, String operation_finance_id, String date, String observation, double montant, String devise_id,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(montant,0.0,0.0,devise_id,"","",adding_user_id,adding_agence_id,adding_date,updated_date,sync_pos,pos);
        this.id = id;
        this.agence_id = agence_id;
        this.is_checked = is_checked;
        this.operation_finance_id = operation_finance_id;
        this.date = date;
        this.observation = observation;
        this.montant = montant;
        this.devise_id = devise_id;
        String categorie= MainActivity.getCurrentUser().getAgence().getType().toLowerCase(Locale.ROOT).equals("depot")?"Maison":MainActivity.getCurrentUser().getAgence().getType();

        this.operationFinance=new OperationFinance(operation_finance_id,agence_id,null,devise_id,categorie,date,"Sortie",montant,"Depense : "+observation,
                "Auto",0,0,0,adding_user_id,adding_agence_id,adding_agence_id,adding_date,updated_date,sync_pos,pos);

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
        this.operationFinance.setAgence_id(agence_id);
    }

    public boolean getIs_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public String getOperation_finance_id() {
        return operation_finance_id;
    }

    public void setOperation_finance_id(String operation_finance_id) {
        this.operation_finance_id = operation_finance_id;
        this.operationFinance.setId(operation_finance_id);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        this.operationFinance.setDate(date);
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
        this.operationFinance.setObservation("Depense : "+observation);
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
        this.operationFinance.setMontant(montant);
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {
        this.devise_id = devise_id;
        this.operationFinance.setDevise_id(devise_id);
    }

    public OperationFinance getOperationFinance() {
        return operationFinance;
    }

    public void setOperationFinance(OperationFinance operationFinance) {
        this.operationFinance = operationFinance;
    }

    @Override
    public int compareTo(@NonNull Depense o) {
        return 0;
    }
}
