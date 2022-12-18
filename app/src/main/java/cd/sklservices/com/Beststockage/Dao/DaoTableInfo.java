package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.TableInfo;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */


@Dao
public interface DaoTableInfo {

    @Insert
    void insert(TableInfo tableInfo) ;

    @Query("SELECT * FROM table_info WHERE table_name LIKE  :Table ")
    public List<TableInfo> select_byTable_name_Table_info(String Table) ;

}
