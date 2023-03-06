package cd.sklservices.com.Beststockage.Classes.Parametres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;

@Entity(tableName = "fournisseur_taux",
        indices = {
                @Index("id"),
                        },
        foreignKeys = {
                @ForeignKey(entity = Fournisseur.class,parentColumns = "id",childColumns = "fournisseur_id",onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "from",onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "to",onUpdate = ForeignKey.CASCADE)
        }
)

public class FournisseurTaux extends ModelBase {
    @PrimaryKey
    @NonNull
    private String id;
    private String fournisseur_id;
    private String date;
    private String from;
    private String to;
    private String amount;
    private String statut;

    public FournisseurTaux(String id, String fournisseur_id, String date, String from, String to, String amount, String statut,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.fournisseur_id = fournisseur_id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.statut = statut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFournisseur_id() {
        return fournisseur_id;
    }

    public void setFournisseur_id(String fournisseur_id) {
        this.fournisseur_id = fournisseur_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
