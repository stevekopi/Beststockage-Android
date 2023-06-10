package cd.sklservices.com.Beststockage.Dao.Registres;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Driver;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoDriver {

    @Insert
    void insert(Driver driver) ;

    @Update
    void update(Driver driver) ;

    @Delete
    void delete(Driver driver) ;

    @Query("DELETE FROM driver")
    void delete_all() ;

    @Query("SELECT * FROM driver WHERE id LIKE  :Id ")
    public Driver get(String Id) ;

    @Query("SELECT * FROM driver  where sync_pos!='3' and sync_pos!='4'")
    public List<Driver> select_driver() ;



}
