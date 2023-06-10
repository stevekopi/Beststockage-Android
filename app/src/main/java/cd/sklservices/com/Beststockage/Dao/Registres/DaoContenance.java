package cd.sklservices.com.Beststockage.Dao.Registres;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Contenance;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoContenance {

    @Insert
    void insert(Contenance contenance) ;

    @Update
    void update(Contenance contenance) ;

    @Delete
    void delete(Contenance contenance) ;

    @Query("DELETE FROM contenance")
    void delete_all() ;

    @Query("SELECT * FROM contenance WHERE id LIKE  :Id ")
    public Contenance get(String Id) ;

    @Query("SELECT * FROM contenance  where sync_pos!='3' and sync_pos!='4'")
    public List<Contenance> select_contenance() ;



}
