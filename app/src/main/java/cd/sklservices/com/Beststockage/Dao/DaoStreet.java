package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Street;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoStreet {

    @Insert
    void insert(Street street) ;

    @Update
    void update(Street street) ;

    @Delete
    void delete(Street street) ;


    @Query("DELETE FROM street")
    void delete_all() ;

    @Query("SELECT * FROM street WHERE id LIKE  :Id ")
    public Street get(String Id) ;

    @Query("SELECT * FROM street where sync_pos!='3' and sync_pos!='4' ")
    public List<Street> select_street() ;

    @Query("SELECT * FROM street WHERE quarter_id= :Id AND name LIKE :Value and  sync_pos!='3' and sync_pos!='4'")
    public List<Street> find(String Id, String Value) ;

    @Query("SELECT * FROM street WHERE sync_pos ='0' or sync_pos='3' ORDER BY RANDOM()")
    public List<Street> select_street_bysend() ;

}
