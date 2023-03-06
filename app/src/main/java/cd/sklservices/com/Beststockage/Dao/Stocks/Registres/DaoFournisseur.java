package cd.sklservices.com.Beststockage.Dao.Stocks.Registres;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

@Dao
public interface DaoFournisseur {

    @Insert
    void insert(Fournisseur fournisseur) ;

    @Update
    void update(Fournisseur fournisseur) ;

    @Delete
    void delete(Fournisseur fournisseur) ;


    @Query("DELETE FROM fournisseur ")
    void delete_all() ;

    @Query("SELECT * FROM fournisseur WHERE id LIKE  :Id ")
    public Fournisseur get(String Id) ;

    @Query("SELECT * FROM fournisseur  where sync_pos!='3' and sync_pos!='4'")
    public List<Fournisseur> select_fournisseur() ;

     @Query("SELECT f.id as id,f.is_default,f.can_receive_tva , f.adding_date as adding_date," +
            "  f.sync_pos as sync_pos,f.pos as pos FROM fournisseur as f inner join article as a on a.fournisseur_id = f.id WHERE f.sync_pos!='3' and f.sync_pos!='4' and  a.id= :Article_id ")
    public List<Fournisseur> getByArticleId2(String Article_id) ;

    @Query("SELECT f.id FROM fournisseur as f INNER JOIN identity as i on f.id=i.id WHERE i.name=:Name  and f.sync_pos!='3' and f.sync_pos!='4'")
    public String get_id_by_name(String Name) ;

}
