package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.*;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */
@Dao
public interface DaoCommande {

    @Insert
    void insert(Commande commande) ;

    @Update
    void update(Commande commande) ;

    @Delete
    void delete(Commande commande) ;

    @Query("DELETE FROM commade")
    void delete_all() ;

    @Query("DELETE FROM commade WHERE id= :Id")
    void delete2(String Id) ;

    @Query("SELECT * FROM commade WHERE id LIKE  :Id")
    public Commande get(String Id) ;

    @Query("SELECT * FROM commade WHERE sync_pos!='3' and sync_pos!='4' ORDER BY date DESC ")
    public List<Commande> select_commande() ;

    @Query("SELECT * FROM commade WHERE sync_pos!='3' and sync_pos!='4'  ORDER BY date DESC ")
    public List<Commande> select_orderbydate_commande() ;

    @Query("SELECT * FROM commade WHERE  sync_pos!='3' and sync_pos!='4' and adding_user_id= :Id_user AND proprietaire_id= :Id_prorietaire AND fournisseur_id= :Id_fournisseur " +
            " AND date= :Date_commande AND membership= :Membership AND adding_agence_id= :Adding_agence_id AND  sync_pos != '3' ")
    public List<Commande> select_whereAll(String Id_user, String Id_prorietaire, String Id_fournisseur, String Date_commande,
                                           String Membership, String Adding_agence_id) ;

    @Query("SELECT ifnull(sum(a.quantite),0) from approvisionnement as a inner join livraison as l on a.livraison_id=l.id " +
            " inner join ligne_commande as lc on l.ligne_commande_id=lc.id " +
            " inner join commade as c on lc.commande_id=c.id WHERE c.id= :IdCommande and c.sync_pos!='3' and c.sync_pos!='4'")
    public List<String> getSommeQuantiteApprovisionner(String IdCommande) ;

    @Query("SELECT * FROM commade WHERE sync_pos = '0' and sync_pos='3' ORDER BY RANDOM() ")
    public List<Commande> select_commande_bysend() ;


}
