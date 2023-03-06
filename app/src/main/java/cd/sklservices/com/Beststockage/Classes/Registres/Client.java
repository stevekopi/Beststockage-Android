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

@Entity(tableName = "client",
        foreignKeys = { @ForeignKey(entity = Human.class,parentColumns = "id",childColumns = "id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "parent_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "parent_user_id",onUpdate = ForeignKey.CASCADE)
}

)
public class Client extends ModelBase implements Comparable<Client> {

    @PrimaryKey @NonNull
    private String id ;
    private String parent_user_id ;
    private String parent_agence_id;
    private String statut;
    @Ignore
    private Human human;


    public Client(@NonNull String id, String parent_user_id, String parent_agence_id,String statut,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.parent_user_id = parent_user_id;
        this.parent_agence_id = parent_agence_id;
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getParent_user_id() {
        return parent_user_id;
    }

    public void setParent_user_id(String parent_user_id) {
        this.parent_user_id = parent_user_id;
    }

    public String getParent_agence_id() {
        return parent_agence_id;
    }

    public void setParent_agence_id(String parent_agence_id) {
        this.parent_agence_id = parent_agence_id;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    @Override
    public int compareTo(@NonNull Client o) {
        return 0;
    }
}

