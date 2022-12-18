package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity(tableName = "delegue",
        foreignKeys = {  @ForeignKey (entity = Identity.class,parentColumns = "id",childColumns = "proprietaire_id",onUpdate = ForeignKey.CASCADE)
                      }

)


public class Delegue extends ModelBase implements Comparable<Delegue> {

    @PrimaryKey @NonNull
    private String id ;
    private String proprietaire_id ;
    private String name ;

    public Delegue(@NonNull String id, String proprietaire_id, String name,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.proprietaire_id = proprietaire_id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Delegue o) {
        return 0;
    }
}
