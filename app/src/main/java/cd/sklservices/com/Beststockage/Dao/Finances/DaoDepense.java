package cd.sklservices.com.Beststockage.Dao.Finances;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Finances.Depense;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */


@Dao
public interface DaoDepense {

    @Insert
    void insert(Depense depense) ;

    @Update
    void update(Depense depense) ;

    @Delete
    void delete(Depense depense) ;


    @Query("DELETE FROM depense")
    void delete_all() ;


    @Query("DELETE FROM depense WHERE agence_id != :Id  and sync_pos!='3' and sync_pos!='4' ")
    void delete_data_old_agence(String Id) ;

    //Ici on selectionne même les supprimés à cause de la synchronisation
    @Query("SELECT * FROM depense WHERE id LIKE :Id")
    public Depense get(String Id) ;

    @Query("SELECT * FROM depense WHERE agence_id LIKE  :Id  and sync_pos!='3' and sync_pos!='4' ")
    public List<Depense> select_byid_agence(String Id) ;

    @Query("SELECT * FROM depense WHERE  sync_pos!='3' and sync_pos!='4' ")
    public List<Depense> select_depense() ;

    @Query("SELECT DISTINCT(date) FROM depense WHERE  sync_pos!='3' and sync_pos!='4'  ORDER BY adding_date DESC LIMIT 20")
    public List<String> select_date_from_depense() ;

    @Query("SELECT DISTINCT(date) FROM depense WHERE Date(date)<=Date(:Date) and sync_pos!='3' and sync_pos!='4'  ORDER BY date DESC LIMIT 20")
    public List<String> select_date_from_depense2(String Date) ;

    @Query("SELECT * FROM depense WHERE agence_id= :Agence_id AND date = :Date  and sync_pos!='3' and sync_pos!='4'  ORDER BY date DESC ")
    public List<Depense> select_bydate_depense(String Agence_id, String Date) ;

    @Query("SELECT * FROM depense WHERE agence_id=:AgenceId AND date = :Date AND (sync_pos='0' or sync_pos='3') LIMIT 1")
    public Depense select_bydate_not_sync(String AgenceId,String Date) ;

    @Query("SELECT * FROM depense WHERE sync_pos LIKE '0' or sync_pos LIKE '3'")
    public List<Depense> get_for_post_sync() ;

    @Query("SELECT count(distinct(date)) FROM depense  where sync_pos!='3' and sync_pos!='4' ")
    public int count_distinct_date() ;

    @Query("SELECT count(*) FROM depense  where sync_pos!='3' and sync_pos!='4'")
    public int count() ;

    @Query("select sum(montant) from depense where date=Date(:Date) and agence_id=:AgenceId  and sync_pos!='3' and sync_pos!='4'")
    public double get_amount_by_date_and_agence_id(String Date,String AgenceId);



}
