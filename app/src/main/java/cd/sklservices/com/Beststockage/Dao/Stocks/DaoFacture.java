package cd.sklservices.com.Beststockage.Dao.Stocks;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */
@Dao
public interface DaoFacture {

    @Insert
    void insert(Facture facture) ;

    @Update
    void update(Facture facture) ;

    @Delete
    void delete(Facture facture) ;

    @Query("DELETE FROM facture")
    void delete_all() ;

    @Query("DELETE FROM facture WHERE id= :Id")
    void delete2(String Id) ;

    @Query("SELECT * FROM facture WHERE id LIKE  :Id")
    public Facture get(String Id) ;

    @Query("SELECT * FROM facture WHERE sync_pos!='3' and sync_pos!='4' ORDER BY date DESC ")
    public List<Facture> select_facture() ;

    @Query("SELECT * FROM facture WHERE sync_pos!='3' and sync_pos!='4'  ORDER BY date DESC ")
    public List<Facture> select_orderbydate_facture() ;

    @Query("SELECT * FROM facture WHERE sync_pos = '0' and sync_pos='3' ORDER BY RANDOM() ")
    public List<Facture> select_facture_bysend() ;


}