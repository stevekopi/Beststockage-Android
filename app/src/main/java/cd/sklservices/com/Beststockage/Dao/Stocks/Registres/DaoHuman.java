package cd.sklservices.com.Beststockage.Dao.Stocks.Registres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Human;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */


@Dao
public interface DaoHuman {

    @Insert
    void insert(Human human) ;

    @Update
    void update(Human human) ;

    @Delete
    void delete(Human human) ;


    @Query("DELETE FROM human")
    void delete_all() ;

    @Query("SELECT * FROM human WHERE id LIKE  :Id ")
    public Human get(String Id) ;

    @Query("SELECT * FROM human  where sync_pos!='3' and sync_pos!='4'")
    public List<Human> select_human() ;

    @Query("SELECT * FROM human WHERE sync_pos LIKE '0' or sync_pos='3'  ORDER BY RANDOM()")
    public List<Human> select_human_bysend() ;

}
