package cd.sklservices.com.Beststockage.Dao.Stocks.Registres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Categorie;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoCategorie {
    @Insert
    void insert(Categorie categorie) ;

    @Update
    void update(Categorie categorie) ;

    @Delete
    void delete(Categorie categorie) ;


    @Query("DELETE FROM categorie")
    void delete_all() ;

    @Query("SELECT * FROM categorie WHERE id LIKE  :Id ")
    public Categorie get(String Id) ;

    @Query("SELECT * FROM categorie where sync_pos!='3' and sync_pos!='4' ")
    public List<Categorie> select_categorie() ;


}
