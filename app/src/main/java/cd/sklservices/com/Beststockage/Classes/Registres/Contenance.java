package cd.sklservices.com.Beststockage.Classes.Registres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity(tableName = "contenance")


public class Contenance extends ModelBase implements Comparable<Contenance> {

    @PrimaryKey @NonNull
    private String id ;

    private String capacite;

    public Contenance(@NonNull String id, String capacite,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.capacite = capacite;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }


    public String getCapacite() {
        return capacite;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }

    @Override
    public int compareTo(@NonNull Contenance o) {
        return 0;
    }
}
