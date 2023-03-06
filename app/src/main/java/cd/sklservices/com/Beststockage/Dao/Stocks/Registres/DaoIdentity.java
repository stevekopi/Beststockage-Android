package cd.sklservices.com.Beststockage.Dao.Stocks.Registres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Identity;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */


@Dao
public interface DaoIdentity {

    @Insert
    void insert(Identity identity) ;

    @Update
    void update(Identity identity) ;

    @Delete
    void delete(Identity identity) ;


    @Query("DELETE FROM identity")
    void delete_all() ;

    @Query("SELECT * FROM identity WHERE id LIKE  :Id ")
    public Identity get(String Id) ;

    @Query("SELECT * FROM identity  where sync_pos!='3' and sync_pos!='4'")
    public List<Identity> gets() ;

    @Query("SELECT * FROM identity WHERE sync_pos ='0' or sync_pos='3' ORDER BY RANDOM()")
    public List<Identity> select_identity_bysend() ;

    @Query("SELECT i.name FROM identity as i inner join agence as a on a.proprietaire_id=i.id where a.id=:AgenceId")
    public String select_name_by_agence_id(String AgenceId) ;



}
