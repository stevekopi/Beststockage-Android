package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Devise;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoDevise {
    @Insert
    void insert(Devise devise) ;

    @Update
    void update(Devise devise) ;

    @Delete
    void delete(Devise devise) ;


    @Query("DELETE FROM devise")
    void delete_all() ;

    @Query("SELECT * FROM devise WHERE id LIKE  :Id ")
    public Devise get(String Id) ;

    @Query("SELECT * FROM devise  where sync_pos!='3' and sync_pos!='4'")
    public List<Devise> select_devise() ;


}
