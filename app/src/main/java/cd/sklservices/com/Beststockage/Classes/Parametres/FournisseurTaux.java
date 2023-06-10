package cd.sklservices.com.Beststockage.Classes.Parametres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
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
                @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "from_id",onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "to_id",onUpdate = ForeignKey.CASCADE)
        }
)

public class FournisseurTaux extends ModelBase {
    @PrimaryKey
    @NonNull
    private String id;
    private String fournisseur_id;
    private String date;
    private String from_id;
    private String to_id;
    private double amount;
    private int statut;

    @Ignore
    private String statut_means;
    @Ignore
    private Fournisseur Fournisseur;
    @Ignore
    private Devise From,To;

    public FournisseurTaux(String id, String fournisseur_id, String date, String from_id, String to_id, double amount, int statut, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.fournisseur_id = fournisseur_id;
        this.date = date;
        this.from_id = from_id;
        this.to_id = to_id;
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

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {

        this.statut = statut;
    }

    public Fournisseur getFournisseur() {
        return Fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        Fournisseur = fournisseur;
    }

    public void setFrom(Devise from) {
        From = from;
    }

    public void setTo(Devise to) {
        To = to;
    }

    public String getStatut_means() {
        if(statut==1){
            return "En cours";
        }
        else if(statut==0){
            return  "Expiré";
        }else{
            return "Erreur, vérifiez svp";
        }
    }


    public Devise getFrom() {
        return From;
    }

    public Devise getTo() {
        return To;
    }
}
