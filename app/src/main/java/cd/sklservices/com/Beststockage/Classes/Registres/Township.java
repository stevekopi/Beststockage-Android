package cd.sklservices.com.Beststockage.Classes.Registres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;

/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

@Entity(tableName = "township",
        foreignKeys = { @ForeignKey(entity = District.class,parentColumns = "id",childColumns = "district_id",onUpdate = ForeignKey.CASCADE) }
)

public class Township extends ModelBase implements Comparable<Township> {

    @PrimaryKey @NonNull
    private String id ;

    private String district_id ;

    private String name ;


    public Township(@NonNull String id,String district_id, String name, String adding_date,String updated_date,int sync_pos,int pos) {
        super(adding_date,updated_date,sync_pos,pos);
        this.district_id = district_id;
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

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Township o) {
        return 0;
    }
}
