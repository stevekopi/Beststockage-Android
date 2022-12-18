package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "bon",
        foreignKeys = { @ForeignKey (entity = Fournisseur.class,parentColumns = "id",childColumns = "fournisseur_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Identity.class,parentColumns = "id",childColumns = "proprietaire_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE)
        }
)


public class Bon extends ModelBaseX {
    @PrimaryKey @NonNull
    private String id;
    private String numero;
    private String fournisseur_id;
    private String type;
    private String Membership;
    private String proprietaire_id;
    private String date;

    public Bon(){
        
    }

    public Bon(@NonNull String id, String numero, String fournisseur_id, String type, String membership, String proprietaire_id, String date,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.numero = numero;
        this.fournisseur_id = fournisseur_id;
        this.type = type;
        Membership = membership;
        this.proprietaire_id = proprietaire_id;
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFournisseur_id() {
        return fournisseur_id;
    }

    public void setFournisseur_id(String fournisseur_id) {
        this.fournisseur_id = fournisseur_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMembership() {
        return Membership;
    }

    public void setMembership(String membership) {
        Membership = membership;
    }

    public String getProprietaire_id() {
        return proprietaire_id;
    }

    public void setProprietaire_id(String proprietaire_id) {
        this.proprietaire_id = proprietaire_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
