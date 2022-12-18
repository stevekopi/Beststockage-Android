package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

@Entity(tableName = "categorie")

public class Categorie extends ModelBase implements Comparable<Categorie> {

    @PrimaryKey @NonNull
    private String id ;

    private String designation ;
    private String description;


    public Categorie(@NonNull String id, String designation, String description,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.designation = designation;
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(@NonNull Categorie o) {
        return 0;
    }
}
