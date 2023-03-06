package cd.sklservices.com.Beststockage.Classes.Registres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;

/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

@Entity(tableName = "town")

public class Town extends ModelBase implements Comparable<Town> {

    @PrimaryKey @NonNull
    private String id ;

    private String province_id ;

    private String name ;

    public Town(@NonNull String id,String province_id, String name, String adding_date,String updated_date,int sync_pos,int pos) {
        super(adding_date,updated_date,sync_pos,pos);
        this.province_id = province_id;
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

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(@NonNull Town o) {
        return 0;
    }

}
