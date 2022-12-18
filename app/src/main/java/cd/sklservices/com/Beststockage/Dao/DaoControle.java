package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Controle;
import cd.sklservices.com.Beststockage.Classes.Devise;
import cd.sklservices.com.Beststockage.Classes.OperationFinance;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoControle {
    @Insert
    void insert(Controle devise) ;

    @Update
    void update(Controle devise) ;

    @Delete
    void delete(Controle devise) ;


    @Query("DELETE FROM controle")
    void delete_all() ;

    @Query("SELECT * FROM controle WHERE id LIKE  :Id ")
    public Controle get(String Id) ;

    @Query("SELECT * FROM controle  where sync_pos!='3' and sync_pos!='4'")
    public List<Controle> gets() ;

    @Query("SELECT * FROM controle WHERE sync_pos LIKE '0' or sync_pos LIKE '3'")
    public List<Controle> get_for_post_sync() ;

    @Query("SELECT * FROM controle where agence_id=:AgenceId and date=:Date and type='Confirmation' and sync_pos!='3' and sync_pos!='4'")
    public Controle get_by_date_and_agence_id(String AgenceId,String Date) ;


}
