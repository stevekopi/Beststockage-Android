package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity( tableName = "table_info" )

public class TableInfo implements Comparable<TableInfo> {

    @PrimaryKey @NonNull
    private String table_name ;

    private int key_length ;

    public TableInfo(@NonNull String table_name, int key_length) {
        this.table_name = table_name;
        this.key_length = key_length;
    }

    @NonNull
    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(@NonNull String table_name) {
        this.table_name = table_name;
    }

    public int getKey_length() {
        return key_length;
    }

    public void setKey_length(int key_length) {
        this.key_length = key_length;
    }

    @Override
    public int compareTo(@NonNull TableInfo o) {
        return 0;
    }
}
