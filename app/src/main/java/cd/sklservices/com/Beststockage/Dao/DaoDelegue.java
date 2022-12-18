package cd.sklservices.com.Beststockage.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Delegue;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoDelegue {

    @Insert
    void insert(Delegue delegue) ;

    @Update
    void update(Delegue delegue) ;

    @Delete
    void delete(Delegue delegue) ;

    @Query("DELETE FROM delegue")
    void delete_all() ;

    @Query("SELECT * FROM delegue WHERE id LIKE  :Id ")
    public Delegue get(String Id) ;

    @Query("SELECT * FROM delegue where sync_pos!='3' and sync_pos!='4'")
    public List<Delegue> select_delegue() ;



}
