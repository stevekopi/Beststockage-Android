package cd.sklservices.com.Beststockage.Dao.Stocks;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

@Dao
public interface DaoLigneFacture {

    @Insert
    void insert(LigneFacture ligneFacture) ;

    @Update
    void update(LigneFacture ligneFacture) ;

    @Delete
    void delete(LigneFacture ligneFacture) ;

    @Query("DELETE FROM ligne_facture WHERE id= :Id")
    void delete2(String Id) ;

    @Query("DELETE FROM ligne_facture ")
    void delete_all() ;

    @Query("SELECT * FROM ligne_facture WHERE id LIKE  :Id ")
    public LigneFacture get(String Id) ;

    @Query("SELECT ifnull(sum(quantite),0) AS quantite FROM ligne_facture WHERE facture_id = :Id  and sync_pos!='3' and sync_pos!='4'")
    public List<String> select_sum_quantite_ligne_facture(String Id) ;

    @Query("SELECT * FROM ligne_facture WHERE  sync_pos!='3' and sync_pos!='4'")
    public List<LigneFacture> select_ligne_facture() ;

    @Query("SELECT * FROM ligne_facture WHERE id= :Id")
    public List<LigneFacture> select_quantite_ligne_facture(String Id) ;

    @Query("SELECT * FROM ligne_facture WHERE facture_id= :Id  and sync_pos!='3' and sync_pos!='4'")
    public List<LigneFacture> select_byId_facture_ligne_facture(String Id) ;

    @Query("SELECT * FROM ligne_facture WHERE sync_pos = '0' or sync_pos='3' ORDER BY RANDOM() ")
    public List<LigneFacture> select_ligne_facture_bysend() ;

    @Query("SELECT sum(montant_ht) FROM ligne_facture WHERE facture_id= :FactureId  and sync_pos!='3' and sync_pos!='4'")
    public double get_montant_ht_facture(String FactureId) ;

    @Query("SELECT sum(montant_local) FROM ligne_facture WHERE facture_id= :FactureId  and sync_pos!='3' and sync_pos!='4'")
    public double get_montant_local_facture(String FactureId) ;

    @Query("SELECT sum(montant_net) FROM ligne_facture WHERE facture_id= :FactureId  and sync_pos!='3' and sync_pos!='4'")
    public double get_montant_net_facture(String FactureId) ;

    @Query("SELECT sum(montant_ttc) FROM ligne_facture WHERE facture_id= :FactureId  and sync_pos!='3' and sync_pos!='4'")
    public double get_montant_ttc_facture(String FactureId) ;

}
