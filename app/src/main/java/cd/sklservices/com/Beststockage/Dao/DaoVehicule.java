package cd.sklservices.com.Beststockage.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Vehicule;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoVehicule {

    @Insert
    void insert(Vehicule vehicule) ;

    @Update
    void update(Vehicule vehicule) ;

    @Delete
    void delete(Vehicule vehicule) ;

    @Query("DELETE FROM vehicule")
    void delete_all() ;

    @Query("SELECT * FROM vehicule WHERE id LIKE  :Id ")
    public Vehicule get(String Id) ;

    @Query("SELECT * FROM vehicule where  sync_pos!='3' and sync_pos!='4'")
    public List<Vehicule> select_vehicule() ;
}
