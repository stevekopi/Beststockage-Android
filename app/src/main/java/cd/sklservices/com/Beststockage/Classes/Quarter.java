package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

@Entity(tableName = "quarter",
        foreignKeys = { @ForeignKey(entity = Township.class,parentColumns = "id",childColumns = "township_id",onUpdate = ForeignKey.CASCADE) }
)

public class Quarter extends ModelBase implements Comparable<Quarter> {

    @PrimaryKey @NonNull
    private String id ;
    private String township_id ;
    private String name ;



    public Quarter(@NonNull String id,String township_id, String name, String adding_date,String updated_date,int sync_pos,int pos) {
        super(adding_date,updated_date,sync_pos,pos);
        this.township_id = township_id;
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

    public String getTownship_id() {
        return township_id;
    }

    public void setTownship_id(String township_id) {
        this.township_id = township_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @Override
    public int compareTo(@NonNull Quarter o) {
        return 0;
    }
}
