package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */
@Entity(tableName = "commade",
        foreignKeys = { @ForeignKey (entity = Fournisseur.class,parentColumns = "id",childColumns = "fournisseur_id", onUpdate = ForeignKey.CASCADE) ,
                        @ForeignKey (entity = Identity.class,parentColumns = "id",childColumns = "proprietaire_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE)}


)
public class Commande  extends ModelBaseX implements Comparable<Commande> {

    @PrimaryKey @NonNull
    private String id ;

    private String numero ;

    private String proprietaire_id ;

    private  String fournisseur_id ;

    private String date;

    private String membership ;
    @Ignore
    private double montant ;


    public Commande(@NonNull String id,String numero, String proprietaire_id, String fournisseur_id, String date, String membership,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos ) {
        super(adding_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.numero=numero;
        this.proprietaire_id = proprietaire_id;
        this.fournisseur_id = fournisseur_id;
        this.date = date;
        this.membership = membership;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getProprietaire_id() {
        return proprietaire_id;
    }

    public void setProprietaire_id(String proprietaire_id) {
        this.proprietaire_id = proprietaire_id;
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

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public int compareTo(@NonNull Commande o) {
        return 0;
    }
}
