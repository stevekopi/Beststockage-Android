package cd.sklservices.com.Beststockage.Dao.Stocks.Registres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Township;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoTownship {

    @Insert
    void insert(Township township) ;

    @Update
    void update(Township township) ;

    @Delete
    void delete(Township township) ;


    @Query("DELETE FROM township")
    void delete_all() ;

    @Query("SELECT * FROM township WHERE id LIKE  :Id ")
    public Township get(String Id) ;

    @Query("SELECT * FROM township where  sync_pos!='3' and sync_pos!='4' ")
    public List<Township> select_township() ;

    @Query("SELECT * FROM township WHERE district_id= :Id and  sync_pos!='3' and sync_pos!='4'")
    public List<Township> select_byDistrict_township(String Id) ;

    @Query("SELECT * FROM township WHERE district_id= :Id_district AND name LIKE  :Value and  sync_pos!='3' and sync_pos!='4' ")
    public List<Township> find(String Id_district, String Value) ;

}
