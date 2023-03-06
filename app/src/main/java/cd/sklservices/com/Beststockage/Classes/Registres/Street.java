package cd.sklservices.com.Beststockage.Classes.Registres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;

/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

@Entity(tableName = "street",
        foreignKeys = {@ForeignKey(entity = Quarter.class,parentColumns = "id",childColumns = "quarter_id",onUpdate = ForeignKey.CASCADE) }
)

public class Street extends ModelBase implements Comparable<Street> {

    @PrimaryKey @NonNull
    private String id ;
    private String quarter_id ;
    private String name ;

    public Street(@NonNull String id,String quarter_id, String name, String adding_date,String updated_date,int sync_pos,int pos) {
        super(adding_date,updated_date,sync_pos,pos);
        this.quarter_id = quarter_id;
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

    public String getQuarter_id() {
        return quarter_id;
    }

    public void setQuarter_id(String quarter_id) {
        this.quarter_id = quarter_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Street o) {
        return 0;
    }
}
