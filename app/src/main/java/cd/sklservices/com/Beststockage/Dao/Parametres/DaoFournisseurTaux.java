package cd.sklservices.com.Beststockage.Dao.Parametres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Parametres.FournisseurTaux;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoFournisseurTaux {
    @Insert
    void insert(FournisseurTaux fournisseurTaux) ;

    @Update
    void update(FournisseurTaux fournisseurTaux) ;

    @Delete
    void delete(FournisseurTaux fournisseurTaux) ;


    @Query("DELETE FROM fournisseur_taux")
    void delete_all() ;

    @Query("SELECT * FROM fournisseur_taux WHERE id LIKE  :Id ")
    public FournisseurTaux get(String Id) ;

    @Query("SELECT * FROM fournisseur_taux  where sync_pos!='3' and sync_pos!='4'")
    public List<FournisseurTaux> gets();

    @Query("SELECT * FROM fournisseur_taux  where fournisseur_id =:fournisseur_id AND `from`=:from_id AND `to`=:to_id AND statut=1 AND sync_pos!='3' and sync_pos!='4'")
    public double GetConvertedAmount(String fournisseur_id,String from_id,String to_id);


}
