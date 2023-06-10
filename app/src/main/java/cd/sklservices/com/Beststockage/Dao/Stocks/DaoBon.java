package cd.sklservices.com.Beststockage.Dao.Stocks;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.Bon;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoBon {

    @Insert
    void insert(Bon abon) ;

    @Update
    void update(Bon bon) ;

    @Delete
    void delete(Bon bon) ;

    @Query("DELETE FROM bon")
    void delete_all() ;

    @Query("SELECT * FROM bon WHERE id LIKE  :Id ")
    Bon get(String Id) ;

    @Query("SELECT * FROM bon WHERE proprietaire_id LIKE  :ProprietaireId  and sync_pos!='3' and sync_pos!='4'")
    List<Bon> select_by_proprietaire_id(String ProprietaireId) ;


    @Query("SELECT * FROM bon  where sync_pos!='3' and sync_pos!='4' ORDER BY date DESC ")
    List<Bon> gets_order_by_date() ;


    @Query("SELECT * FROM bon  where sync_pos!='3' and sync_pos!='4' ORDER BY date DESC LIMIT 35 ")
    List<Bon> get_Loading() ;



    @Query("SELECT * FROM bon  WHERE membership LIKE 'Privee'  and sync_pos!='3' and sync_pos!='4' ")
    List<Bon> select_bonPrivee() ;




}
