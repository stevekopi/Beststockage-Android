package cd.sklservices.com.Beststockage.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.*;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

@Dao
public interface DaoTableKeyIncrementor {

    @Insert
    void insert(TableKeyIncrementor message) ;

    @Update
    void update(TableKeyIncrementor table) ;


    @Query("SELECT * FROM table_key_incrementor WHERE table_name LIKE  :Table ")
    public List<TableKeyIncrementor> select_byid_tableIncrementor(String Table) ;

    @Query("SELECT * FROM table_key_incrementor WHERE agence_id LIKE :AgenceId AND table_name LIKE  :Table ")
    public TableKeyIncrementor get(String AgenceId,String Table) ;

    @Query("SELECT position FROM table_key_incrementor WHERE agence_id LIKE :AgenceId AND table_name LIKE  :Table ")
    public int getPosition(String AgenceId,String Table) ;



    //@Query("SELECT * FROM table_key_incrementor WHERE sync_pos LIKE '0' ORDER BY RANDOM()")
    //public List<TableKeyIncrementor> select_table_key_incrementor_bysend() ;
}
