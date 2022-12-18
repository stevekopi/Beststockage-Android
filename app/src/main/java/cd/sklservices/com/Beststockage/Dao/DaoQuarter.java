package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.*;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoQuarter {

    @Insert
    void insert(Quarter quarter) ;

    @Update
    void update(Quarter quarter) ;

    @Delete
    void delete(Quarter quarter) ;


    @Query("DELETE FROM quarter")
    void delete_all() ;

    @Query("SELECT * FROM quarter WHERE id LIKE  :Id ")
    public Quarter get(String Id) ;

    @Query("SELECT * FROM quarter  where sync_pos!='3' and sync_pos!='4'")
    public List<Quarter> select_quarter() ;

    @Query("SELECT * FROM quarter WHERE township_id= :Id_township AND name LIKE :Value  and sync_pos!='3' and sync_pos!='4' ")
    public List<Quarter> find(String Id_township, String Value) ;

    @Query("SELECT * FROM quarter WHERE sync_pos='0' or sync_pos='3' ORDER BY RANDOM()")
    public List<Quarter> select_quarter_bysend() ;

}
