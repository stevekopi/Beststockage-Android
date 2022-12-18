package cd.sklservices.com.Beststockage.Classes;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */


@Entity(tableName = "fournisseur",
        foreignKeys = { @ForeignKey(entity = Identity.class,
        parentColumns = "id",
        childColumns = "id",
        onUpdate = ForeignKey.CASCADE) }

)

public class Fournisseur extends ModelBase implements Comparable<Fournisseur> {

    @PrimaryKey @NonNull
    private String id ;
    @Ignore
    private Identity identity;

    public Fournisseur(@NonNull String id,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id=id;
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    @Override
    public int compareTo(@NonNull Fournisseur o) {

       return this.id.equals(o.id)?1:0;
    }

    @Override
    public boolean equals(Object o){
        return this.id.equals(((Fournisseur)o).getId());
    }
}
