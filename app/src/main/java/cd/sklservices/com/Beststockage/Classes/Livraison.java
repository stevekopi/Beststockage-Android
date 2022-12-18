package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity(tableName = "livraison",
        foreignKeys = { @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE)}

)

public class Livraison extends ModelBaseX  implements Comparable<Livraison> {

    @PrimaryKey @NonNull
    private String id ;
    private String ligne_commande_id ;
    private String date ;

    public Livraison(@NonNull String id, String ligne_commande_id, String date,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.ligne_commande_id = ligne_commande_id;
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getLigne_commande_id() {
        return ligne_commande_id;
    }

    public void setLigne_commande_id(String ligne_commande_id) {
        this.ligne_commande_id = ligne_commande_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(@NonNull Livraison o) {
        return 0;
    }
}
