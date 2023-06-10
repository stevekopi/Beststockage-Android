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

    @Query("SELECT * FROM ligne_facture WHERE facture_id= :Id  and sync_pos!='3' and sync_pos!='4'")
    public List<LigneFacture> get_by_facture_id(String Id) ;

    @Query("select ifnull(sum(quantite),0) from ligne_facture as lf inner join article_produit_facture as apf " +
            "on lf.article_produit_facture_id=apf.id inner join facture as f on lf.facture_id=f.id where agence_id =:Agence_id " +
            "and apf.article_id=:Article_id and lf.sens_stock='Entree' and " +
            "lf.sync_pos!='3' and lf.sync_pos!='4' and f.sync_pos!='3' and f.sync_pos!='4'  and apf.sync_pos!='3' and apf.sync_pos!='4' " +
            " AND lf.is_confirmed = 1;")
    public int quantite_add(String Agence_id, String Article_id) ;

    @Query("select ifnull(sum(quantite),0) from ligne_facture as lf inner join article_produit_facture as apf " +
            "on lf.article_produit_facture_id=apf.id inner join facture as f on lf.facture_id=f.id where agence_id =:Agence_id " +
            "and apf.article_id=:Article_id and lf.sens_stock='Sortie' and " +
            "lf.sync_pos!='3' and lf.sync_pos!='4' and f.sync_pos!='3' and f.sync_pos!='4'  and apf.sync_pos!='3' and apf.sync_pos!='4' " +
            " AND lf.is_confirmed = 0;")
    public int quantite_less(String Agence_id, String Article_id) ;

    @Query("select ifnull(sum(bonus),0) from ligne_facture as lf inner join facture as f on lf.facture_id=f.id where lf.sens_stock='Entree' and " +
            "f.agence_id=:Agence_id and lf.article_bonus_id=:Article_id and f.sync_pos!=3 and f.sync_pos!=4 and lf.sync_pos!=3 and " +
            "lf.sync_pos!=4;")
    public int bonus_add(String Agence_id, String Article_id) ;

    @Query("select ifnull(sum(bonus),0) from ligne_facture as lf inner join facture as f on lf.facture_id=f.id where lf.sens_stock='Sortie' and " +
            "f.agence_id=:Agence_id and lf.article_bonus_id=:Article_id and f.sync_pos!=3 and f.sync_pos!=4 and lf.sync_pos!=3 and " +
            "lf.sync_pos!=4;")
    public int bonus_less(String Agence_id, String Article_id) ;


}
