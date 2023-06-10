package cd.sklservices.com.Beststockage.Dao.Registres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.District;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoDistrict {

    @Insert
    void insert(District district) ;

    @Update
    void update(District district) ;

    @Delete
    void delete(District district) ;


    @Query("DELETE FROM district")
    void delete_all() ;

    @Query("SELECT * FROM district WHERE id LIKE  :Id ")
    public District get(String Id) ;

    @Query("SELECT * FROM district WHERE town_id= :Id_town AND name LIKE  :Value  and sync_pos!='3' and sync_pos!='4'")
    public List<District> find(String Id_town, String Value) ;

    @Query("SELECT * FROM district  where sync_pos!='3' and sync_pos!='4'")
    public List<District> select_district() ;

    @Query("SELECT * FROM district WHERE town_id= :Id  and sync_pos!='3' and sync_pos!='4'")
    public List<District> select_byTown_district(String Id) ;

}
