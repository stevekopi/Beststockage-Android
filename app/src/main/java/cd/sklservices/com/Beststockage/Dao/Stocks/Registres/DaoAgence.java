package cd.sklservices.com.Beststockage.Dao.Stocks.Registres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Agence;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoAgence {

    @Insert
    void insert(Agence aagence) ;

    @Update
    void update(Agence agence) ;

    @Delete
    void delete(Agence agence) ;

    @Query("DELETE FROM agence")
    void delete_all() ;

    @Query("SELECT * FROM agence ")
    List<Agence> select_agence() ;

    @Query("SELECT * FROM agence WHERE id LIKE  :Id ")
    Agence get(String Id) ;

    @Query("SELECT * FROM agence WHERE denomination LIKE  :Denomination  and sync_pos!='3' and sync_pos!='4'")
    List<Agence> select_byDenomination(String Denomination) ;


    @Query("SELECT * FROM agence  WHERE sync_pos!='3' and sync_pos!='4' ORDER BY denomination ASC ")
    List<Agence> gets() ;

    @Query("SELECT * FROM agence WHERE denomination > :Denomination  and sync_pos!='3' and sync_pos!='4' ORDER BY denomination ASC")
    List<Agence> select_agenceLoading2( String Denomination ) ;

    @Query("SELECT * FROM agence  WHERE sync_pos!='3' and sync_pos!='4' ORDER BY Denomination ASC LIMIT 1 ")
    List<Agence> select_agenceLoading() ;

    @Query("SELECT * FROM agence WHERE id =:Id ORDER BY Denomination ASC LIMIT 50 ")
    List<Agence> select_agenceLoading(String Id) ;

    @Query("SELECT * FROM agence AS A INNER JOIN user AS U ON U.agence_id=A.id WHERE  U.id=:Id_user  and A.sync_pos!='3' and A.sync_pos!='4'")
    List<Agence> select_byid_user_agence(String Id_user) ;

    @Query("SELECT * FROM agence  WHERE appartenance LIKE 'Client'  and sync_pos!='3' and sync_pos!='4' ORDER BY denomination LIMIT 1")
    List<Agence> select_agenceCustumer() ;

    @Query("SELECT * FROM agence  WHERE denomination > :Denomination AND appartenance LIKE 'Client'  and sync_pos!='3' and sync_pos!='4' ORDER BY denomination")
    List<Agence> select_agenceCustumer2(String Denomination) ;



    @Query("SELECT * FROM agence  WHERE appartenance LIKE 'Privee'  and sync_pos!='3' and sync_pos!='4' ORDER BY denomination")
    List<Agence> select_agencePrivee() ;

    @Query("SELECT COUNT(*) FROM agence  WHERE sync_pos!='3' and sync_pos!='4'")
    int count();

    @Query("SELECT agence.id as id , agence.adresse_id as adresse_id, agence.proprietaire_id as proprietaire_id, " +
            " agence.type as type, agence.denomination as denomination," +
            " agence.tel as tel, agence.appartenance as appartenance,  agence.adding_date as adding_date,  agence.sync_pos as sync_pos,  agence.pos as pos" +
            "  FROM agence, identity  WHERE  agence.sync_pos!='3' and agence.sync_pos!='4' AND agence.proprietaire_id = identity.id AND agence.appartenance= :Appartenance AND " +
            "  identity.type= :Proprietaire ")
    List<Agence> select_agenceAppartenanceProprietaire(String Appartenance, String Proprietaire) ;



}
