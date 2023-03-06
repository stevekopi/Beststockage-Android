package cd.sklservices.com.Beststockage.Dao.Stocks;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.LigneBonlivraison;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

@Dao
public interface DaoLigneBonLivraison {
    @Insert
    void insert(LigneBonlivraison ligneBonlivraison) ;

    @Update
    void update(LigneBonlivraison ligneBonlivraison) ;

    @Delete
    void delete(LigneBonlivraison ligneBonlivraison) ;

    @Query("DELETE FROM ligne_bonlivraison WHERE id= :Id")
    void delete2(String Id) ;

    @Query("DELETE FROM ligne_bonlivraison")
    void delete_all() ;

    @Query("SELECT * FROM ligne_bonlivraison WHERE id LIKE  :Id ")
    public LigneBonlivraison get(String Id) ;

    @Query("SELECT * FROM ligne_bonlivraison WHERE bonlivraison_id= :Id  and sync_pos!='3' and sync_pos!='4'")
    public List<LigneBonlivraison> select_byid_BonLivraison_LigneBonlivraison(String Id) ;

    @Query("SELECT * FROM ligne_bonlivraison WHERE  sync_pos!='3' and sync_pos!='4'")
    public List<LigneBonlivraison> select_LigneBonlivraison() ;

    @Query("SELECT * FROM ligne_bonlivraison WHERE sync_pos = '0' or sync_pos='3' ORDER BY RANDOM() ")
    public List<LigneBonlivraison> select_ligne_bonlivraison_bysend() ;

}
