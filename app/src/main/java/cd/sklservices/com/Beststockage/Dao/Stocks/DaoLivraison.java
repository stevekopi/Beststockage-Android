package cd.sklservices.com.Beststockage.Dao.Stocks;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.Livraison;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

@Dao
public interface DaoLivraison {

    @Insert
    void insert(Livraison livraison) ;

    @Update
    void update(Livraison livraison) ;

    @Delete
    void delete(Livraison livraison) ;

    @Query("DELETE FROM livraison WHERE id= :Id")
    void delete2(String Id) ;

    @Query("DELETE FROM livraison")
    void delete_all() ;

    @Query("SELECT * FROM livraison WHERE id LIKE  :Id ")
    public Livraison get(String Id) ;

    @Query("SELECT * FROM livraison where sync_pos!='3' and sync_pos!='4' order by date desc")
    public List<Livraison> select_livraison() ;

    @Query("SELECT * FROM livraison WHERE sync_pos = '0' or sync_pos='3'")
    public List<Livraison> select_livraison_bysend() ;

    @Query("SELECT DISTINCT(date) FROM livraison  where sync_pos!='3' and sync_pos!='4' ORDER BY date DESC")
    public List<String> select_date_from_livraison() ;

    @Query("SELECT * FROM livraison WHERE ligne_commande_id= :Id and sync_pos!='3' and sync_pos!='4'")
    public List<Livraison> select_byId_ligneCommande(String Id) ;

}
