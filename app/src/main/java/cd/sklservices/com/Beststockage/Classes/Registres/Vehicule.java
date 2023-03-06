package cd.sklservices.com.Beststockage.Classes.Registres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity(tableName = "vehicule",
        foreignKeys = {@ForeignKey (entity = Identity.class,parentColumns = "id",childColumns = "proprietaire_id",onUpdate = ForeignKey.CASCADE)
                      }

)

public class Vehicule extends ModelBase implements Comparable<Vehicule> {

    @PrimaryKey @NonNull
    private String id ;
    private String matricule ;
    private String proprietaire_id ;
    private String marque ;

    public Vehicule(@NonNull String id,@NonNull String matricule, String proprietaire_id, String marque,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.matricule=matricule;
        this.proprietaire_id = proprietaire_id;
        this.marque = marque;
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

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    @Override
    public int compareTo(@NonNull Vehicule o) {
        return 0;
    }
}
