package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

@Entity(tableName = "district",
        foreignKeys = { @ForeignKey(entity = Town.class,parentColumns = "id",childColumns = "town_id",onUpdate = ForeignKey.CASCADE) }
)

public class District extends ModelBase implements Comparable<District> {

    @PrimaryKey @NonNull
    private String id ;

    private String town_id ;

    private String name ;



    public District(@NonNull String id,String town_id, String name, String adding_date,String updated_date,int sync_pos,int pos) {
        super(adding_date,updated_date,sync_pos,pos);
        this.town_id = town_id;
        this.name = name;
        this.id=id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTown_id() {
        return town_id;
    }

    public void setTown_id(String town_id) {
        this.town_id = town_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int compareTo(@NonNull District o) {
        return 0;
    }
}
