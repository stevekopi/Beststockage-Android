package cd.sklservices.com.Beststockage.Dao.Stocks;


import cd.sklservices.com.Beststockage.Classes.Stocks.Approvisionnement;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoApprovisionnement {

    @Insert
    void insert(Approvisionnement approvisionnement) ;

    @Update
    void update(Approvisionnement approvisionnement) ;

    @Delete
    void delete(Approvisionnement approvisionnement) ;

    @Query("DELETE FROM approvisionnement")
    void delete_all() ;

    @Query("SELECT * FROM approvisionnement WHERE id LIKE  :Id ")
    public List<Approvisionnement> select_byid_approvisionnement(String Id) ;

    @Query("SELECT * FROM approvisionnement WHERE  sync_pos!='3' and sync_pos!='4'")
    public List<Approvisionnement> select_approvisionnement() ;

    @Query("SELECT * FROM approvisionnement WHERE sync_pos = '0' or sync_pos='3'")
    public List<Approvisionnement> select_approvisionnement_bysend() ;

    @Query("SELECT DISTINCT(approvisionnement.id) ,  approvisionnement.livraison_id, approvisionnement.operation_id, " +
            " approvisionnement.agence_id, approvisionnement.article_id,approvisionnement.adding_user_id, approvisionnement.adding_agence_id, " +
            "  approvisionnement.quantite, approvisionnement.bonus,approvisionnement.montant,approvisionnement.pos, approvisionnement.adding_date, approvisionnement.sync_pos" +
            " FROM livraison, approvisionnement WHERE approvisionnement.livraison_id= livraison.id AND  livraison.date = :Date" +
            " AND  livraison.sync_pos!='3' and livraison.sync_pos!='4' AND  approvisionnement.sync_pos!='3' and approvisionnement.sync_pos!='4'  ORDER BY livraison.date DESC")
    public List<Approvisionnement> select_bydate_approvisionnement(String Date) ;

    @Query("SELECT * FROM approvisionnement WHERE livraison_id= :Id  and sync_pos!='3' and sync_pos!='4'")
    public List<Approvisionnement> byLivraison(String Id) ;

}
