package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Town;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoTown {

    @Insert
    void insert(Town town) ;

    @Update
    void update(Town town) ;

    @Delete
    void delete(Town town) ;


    @Query("DELETE FROM town")
    void delete_all() ;

    @Query("SELECT * FROM town WHERE id LIKE  :Id ")
    public Town get(String Id) ;

    @Query("SELECT * FROM town WHERE name LIKE  :Value and  sync_pos!='3' and sync_pos!='4' ")
    public List<Town> find(String Value) ;

    @Query("SELECT * FROM town where sync_pos!='3' and sync_pos!='4'")
    public List<Town> select_town() ;

}
