package cd.sklservices.com.Beststockage.Classes.Registres;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;

/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */


@Entity(tableName = "fournisseur",
        foreignKeys = { @ForeignKey(entity = Identity.class,parentColumns = "id",childColumns = "id",onUpdate = ForeignKey.CASCADE) }

)

public class Fournisseur extends ModelBase implements Comparable<Fournisseur> {

    @PrimaryKey @NonNull
    private String id ;
    private int is_default ;
    private int can_receive_tva ;
    @Ignore
    private Identity identity;

    @Ignore
    public Fournisseur(@NonNull String id,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id=id;
    }

    public Fournisseur(@NonNull String id, int is_default, int can_receive_tva,String adding_date, String updated_date, int sync_pos, int pos) {
        this(id, adding_date,updated_date, sync_pos, pos);
        this.is_default = is_default;
        this.can_receive_tva = can_receive_tva;
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

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public int getCan_receive_tva() {
        return can_receive_tva;
    }

    public void setCan_receive_tva(int can_receive_tva) {
        this.can_receive_tva = can_receive_tva;
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
