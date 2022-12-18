package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.*;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

@Dao
public interface DaoLigneCommande {

    @Insert
    void insert(LigneCommande ligneCommande) ;

    @Update
    void update(LigneCommande ligneCommande) ;

    @Delete
    void delete(LigneCommande ligneCommande) ;

    @Query("DELETE FROM ligne_commande WHERE id= :Id")
    void delete2(String Id) ;

    @Query("DELETE FROM ligne_commande ")
    void delete_all() ;

    @Query("SELECT * FROM ligne_commande WHERE id LIKE  :Id ")
    public LigneCommande get(String Id) ;

    @Query("SELECT ifnull(sum(quantite),0) AS quantite FROM ligne_commande WHERE commande_id = :Id  and sync_pos!='3' and sync_pos!='4'")
    public List<String> select_sum_quantite_ligne_commande(String Id) ;

    @Query("SELECT * FROM ligne_commande WHERE  sync_pos!='3' and sync_pos!='4'")
    public List<LigneCommande> select_ligne_commande() ;

    @Query("SELECT * FROM ligne_commande WHERE id= :Id")
    public List<LigneCommande> select_quantite_ligne_commande(String Id) ;

    @Query("SELECT * FROM ligne_commande WHERE commande_id= :Id  and sync_pos!='3' and sync_pos!='4'")
    public List<LigneCommande> select_byId_commande_ligne_commande(String Id) ;

    @Query("SELECT * FROM ligne_commande WHERE sync_pos = '0' or sync_pos='3' ORDER BY RANDOM() ")
    public List<LigneCommande> select_ligne_commande_bysend() ;

    @Query("SELECT sum(montant) FROM ligne_commande WHERE commande_id= :CommandeId  and sync_pos!='3' and sync_pos!='4'")
    public double get_montant_commande(String CommandeId) ;

}
