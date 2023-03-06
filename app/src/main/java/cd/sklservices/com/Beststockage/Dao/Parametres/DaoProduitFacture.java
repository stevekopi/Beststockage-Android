package cd.sklservices.com.Beststockage.Dao.Parametres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoProduitFacture {
    @Insert
    void insert(ProduitFacture produitFacture) ;

    @Update
    void update(ProduitFacture produitFacture) ;

    @Delete
    void delete(ProduitFacture produitFacture) ;


    @Query("DELETE FROM produit_facture")
    void delete_all() ;

    @Query("SELECT * FROM produit_facture WHERE id LIKE  :Id ")
    public ProduitFacture get(String Id) ;

    @Query("SELECT * FROM produit_facture  where sync_pos!='3' and sync_pos!='4'")
    public List<ProduitFacture> gets() ;


}
