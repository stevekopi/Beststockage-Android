package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.OperationFinance;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */


@Dao
public interface DaoOperationFinance {

    @Insert
    void insert(OperationFinance operation_finance) ;

    @Update
    void update(OperationFinance operation_finance) ;

    @Delete
    void delete(OperationFinance operation_finance) ;


    @Query("DELETE FROM operation_finance")
    void delete_all() ;

    @Query("DELETE FROM operation_finance WHERE agence_id != :Id AND id!='00000000000000000000000000000000'")
    void delete_data_old_agence(String Id) ;

    //Ici on selectionne même les supprimés à cause de la synchronisation
    @Query("SELECT * FROM operation_finance WHERE id LIKE  :Id")
    public OperationFinance get(String Id) ;

    @Query("SELECT * FROM operation_finance WHERE agence_id LIKE  :Id and sync_pos!=3 and sync_pos!=4 ")
    public List<OperationFinance> select_byid_agence(String Id) ;

    @Query("SELECT * FROM operation_finance WHERE  sync_pos!=3 and sync_pos!=4 ")
    public List<OperationFinance> select_operation_finance() ;

    @Query("SELECT DISTINCT(date) FROM operation_finance WHERE sync_pos!=3 and sync_pos!=4  ORDER BY adding_date DESC")
    public List<String> select_date_from_operation_finance() ;

    @Query("SELECT DISTINCT(date) FROM operation_finance WHERE agence_id LIKE :AgenceId AND  Date(date)<Date(:Date)   and sync_pos!='3' and sync_pos!='4'  ORDER BY date DESC LIMIT 50")
    public List<String> select_date_from_operation_finance2(String AgenceId, String Date) ;

    @Query("SELECT * FROM operation_finance WHERE agence_id= :Agence_id AND date = :Date and sync_pos!=3 and sync_pos!=4 ORDER BY date DESC")
    public List<OperationFinance> select_bydate_operation_finance(String Agence_id, String Date) ;

    @Query("SELECT * FROM operation_finance WHERE agence_id=:AgenceId AND date = :Date AND (sync_pos='0' or sync_pos='3')  LIMIT 1")
    public OperationFinance select_bydate_not_sync(String AgenceId,String Date) ;

    @Query("SELECT * FROM operation_finance WHERE sync_pos LIKE '0' or sync_pos LIKE '3'")
    public List<OperationFinance> get_for_post_sync() ;

    @Query("UPDATE operation_finance SET is_user_confirmed=1,sync_pos=0,pos=pos+1,updated_date=:UpdatedDate WHERE agence_id=:AgenceId and date=:Date")
    public void confirmation_rapport_journalier(String AgenceId,String Date,String UpdatedDate) ;

    @Query("SELECT COUNT(*) FROM operation_finance WHERE  agence_id =:Id and sync_pos!='3' and sync_pos!='4'")
    public int count(String Id);

    @Query("SELECT COUNT(DISTINCT(date)) FROM operation_finance WHERE agence_id =:AgenceId  and sync_pos!='3' and sync_pos!='4'")
    public int count_distinct_date(String AgenceId);

    @Query("select sum(montant) from operation_finance where type='Entree' and insert_mode='Manuel' and  date=Date(:Date) and agence_id=:AgenceId  and sync_pos!='3' and sync_pos!='4'")
    public double get_entrees_amount_by_date_and_agence_id(String Date, String AgenceId);

    @Query("select sum(montant) from operation_finance where type='Sortie' and insert_mode='Manuel' and  date=Date(:Date) and agence_id=:AgenceId  and sync_pos!='3' and sync_pos!='4'")
    public double get_sorties_amount_by_date_and_agence_id(String Date, String AgenceId);

    @Query("select sum(montant) from operation_finance where (type='Entree' or type='Reception') and  date=Date(:Date) and agence_id=:AgenceId  and sync_pos!='3' and sync_pos!='4'")
    public double get_all_entrees_amount_by_date_and_agence_id(String Date, String AgenceId);


    @Query("select sum(montant) from operation_finance where (type='Sortie' or type='Transfert') and  date=Date(:Date) and agence_id=:AgenceId  and sync_pos!='3' and sync_pos!='4'")
    public double get_all_sorties_amount_by_date_and_agence_id(String Date, String AgenceId);
}
