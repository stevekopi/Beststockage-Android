package cd.sklservices.com.Beststockage.Dao.Parametres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoArticleProduitFacture {
    @Insert
    void insert(ArticleProduitFacture articleProduitFacture) ;

    @Update
    void update(ArticleProduitFacture articleProduitFacture) ;

    @Delete
    void delete(ArticleProduitFacture articleProduitFacture) ;


    @Query("DELETE FROM article_produit_facture")
    void delete_all() ;

    @Query("SELECT * FROM article_produit_facture WHERE id LIKE  :Id ")
    public ArticleProduitFacture get(String Id) ;

    @Query("SELECT * FROM article_produit_facture  where sync_pos!='3' and sync_pos!='4'")
    public List<ArticleProduitFacture> gets() ;

    @Query("SELECT * FROM article_produit_facture  WHERE sync_pos!='3' and sync_pos!='4'")
    List<ArticleProduitFacture> loading() ;

    @Query("SELECT * FROM article_produit_facture  WHERE produit_id LIKE :ProduitId and sync_pos!='3' and sync_pos!='4'")
    List<ArticleProduitFacture> loadingByProduit(String ProduitId) ;

    @Query("SELECT COUNT(*) FROM article_produit_facture WHERE sync_pos!='3' AND sync_pos!='4'")
    int count();

    @Query("SELECT * FROM article_produit_facture WHERE produit_id= :Id  and sync_pos!='3' and sync_pos!='4'")
    public List<ArticleProduitFacture> getByProduitFactureId(String Id) ;
}
