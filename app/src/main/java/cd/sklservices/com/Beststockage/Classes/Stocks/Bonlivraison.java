package cd.sklservices.com.Beststockage.Classes.Stocks;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBaseX;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Convoyeur;
import cd.sklservices.com.Beststockage.Classes.Registres.Delegue;
import cd.sklservices.com.Beststockage.Classes.Registres.Driver;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Classes.Registres.Vehicule;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity(tableName = "bonlivraison",
        foreignKeys = { @ForeignKey (entity = Bon.class,parentColumns = "id",childColumns = "id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = User.class,parentColumns = "id",childColumns = "user_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Driver.class,parentColumns = "id",childColumns = "driver_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Vehicule.class,parentColumns = "id",childColumns = "vehicule_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Delegue.class,parentColumns = "id",childColumns = "delegue_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Convoyeur.class,parentColumns = "id",childColumns = "convoyeur_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE)
                      }
        )


public class Bonlivraison extends ModelBaseX implements Comparable<Bonlivraison> {

    @PrimaryKey @NonNull
    private String id ;
    private String user_id ;
    private String driver_id ;
    private String vehicule_id ;
    private String delegue_id ;
    private String convoyeur_id ;
    @Ignore
    private Bon bon;

    public Bonlivraison(@NonNull String id, String user_id, String driver_id, String vehicule_id, String delegue_id, String convoyeur_id,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.user_id = user_id;
        this.driver_id = driver_id;
        this.vehicule_id = vehicule_id;
        this.delegue_id = delegue_id;
        this.convoyeur_id = convoyeur_id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getVehicule_id() {
        return vehicule_id;
    }

    public void setVehicule_id(String vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public String getDelegue_id() {
        return delegue_id;
    }

    public void setDelegue_id(String delegue_id) {
        this.delegue_id = delegue_id;
    }

    public String getConvoyeur_id() {
        return convoyeur_id;
    }

    public void setConvoyeur_id(String convoyeur_id) {
        this.convoyeur_id = convoyeur_id;
    }

    public Bon getBon() {
        return bon;
    }

    public void setBon(Bon bon) {
        this.bon = bon;
    }

    @Override
    public int compareTo(@NonNull Bonlivraison o) {
        return 0;
    }
}
