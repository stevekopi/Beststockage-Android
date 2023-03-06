package cd.sklservices.com.Beststockage.Dao.Stocks.Registres;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Convoyeur;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoConvoyeur {

    @Insert
    void insert(Convoyeur convoyeur) ;

    @Update
    void update(Convoyeur convoyeur) ;

    @Delete
    void delete(Convoyeur convoyeur) ;

    @Query("DELETE FROM convoyeur")
    void delete_all() ;

    @Query("SELECT * FROM convoyeur WHERE id LIKE  :Id ")
    public Convoyeur get(String Id) ;

    @Query("SELECT * FROM convoyeur  where sync_pos!='3' and sync_pos!='4'")
    public List<Convoyeur> select_convoyeur() ;



}
