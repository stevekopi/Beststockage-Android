package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

@Entity( tableName = "table_key_incrementor",primaryKeys = {"agence_id","table_name"})

public class TableKeyIncrementor extends ModelBase implements Comparable<TableKeyIncrementor> {

    @NonNull
    private String agence_id;
    @NonNull
    private String table_name;

    private int position ;

    public TableKeyIncrementor(@NonNull String agence_id,@NonNull String table_name, int position,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.table_name = table_name;
        this.agence_id = agence_id;
        this.position = position;
    }

    @NonNull
    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(@NonNull String table_name) {
        this.table_name = table_name;
    }

    public String getAgence_id() {
        return agence_id;
    }

    public void setAgence_id(String agence_id) {
        this.agence_id = agence_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(@NonNull TableKeyIncrementor o) {
        return 0;
    }
}
