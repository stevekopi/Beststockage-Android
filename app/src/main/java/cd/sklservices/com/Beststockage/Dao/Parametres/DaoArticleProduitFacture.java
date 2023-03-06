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


}
