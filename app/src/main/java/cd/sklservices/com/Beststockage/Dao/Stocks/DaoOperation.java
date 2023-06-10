package cd.sklservices.com.Beststockage.Dao.Stocks;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Performance;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

@Dao
public interface DaoOperation {

    @Insert
    void insert(Operation operation ) ;

    @Update
    void update(Operation operation) ;

    @Delete
    void delete(Operation operation) ;

    @Query("DELETE FROM operation WHERE id= :Id")
    void delete2(String Id) ;

    @Query("DELETE FROM operation WHERE  agence_1_id != :Id AND agence_2_id != :Id")
    void delete_data_old_agence(String Id) ;

    @Query("DELETE FROM operation")
    void delete_all() ;

    //Ici on selectionne même les supprimés à cause de la synchronisation
    @Query("SELECT * FROM operation WHERE id LIKE  :Id")
    public Operation get(String Id) ;


    @Query("SELECT * FROM operation WHERE  sync_pos!='3' and sync_pos!='4'  ")
    public List<Operation> select_operation() ;

    @Query("SELECT * FROM operation WHERE agence_1_id LIKE :AgenceId AND date = :Date   and sync_pos!='3' and sync_pos!='4'  ORDER BY date DESC")
    public List<Operation> select_bydate_operation(String Date,String AgenceId) ;

    @Query("SELECT DISTINCT(date) FROM operation WHERE (agence_1_id LIKE :AgenceId OR agence_2_id LIKE :AgenceId)   and sync_pos!='3' and sync_pos!='4'  ORDER BY date DESC LIMIT 50")
    public List<String> select_date_from_operation(String AgenceId) ;

    @Query("SELECT DISTINCT(date) FROM operation WHERE (agence_1_id LIKE :AgenceId OR agence_2_id LIKE :AgenceId) AND  Date(date)<Date(:Date)   and sync_pos!='3' and sync_pos!='4'  ORDER BY date DESC LIMIT 50")
    public List<String> select_date_from_operation2(String AgenceId, String Date) ;


    @Query("SELECT count(*) as nombre_vente,sum(quantite) as quantite_vente,sum(montant) as montant, devise_id,agence_1_id FROM " +
            " operation where agence_1_id LIKE :AgenceId AND  (lower(type) like '%vente_detail%' OR lower(type) like '%vente_avec_bonus%') " +
            " and (date(date)  >=:Date_debut  and date(date)<=:Date_fin) and sync_pos!='3' and sync_pos!='4' group by agence_1_id  order by count(*) desc;")
    public Performance select_bydate_performance_vente(String AgenceId, String Date_debut, String Date_fin) ;

    @Query("SELECT count(*) as nombre_livraison,sum(quantite) as quantite_livraison, devise_id,agence_1_id FROM operation where " +
            " agence_1_id LIKE :AgenceId AND  lower(type) like 'livraison_client' and (date(date)  >= :Date_debut  and date(date)<= :Date_fin) " +
            "   and sync_pos!='3' and sync_pos!='4' group by agence_1_id  order by count(*) desc;")
    public Performance select_bydate_performance_livraison(String AgenceId, String Date_debut, String Date_fin) ;

    @Query("SELECT count(*) as nombre_vente,sum(montant) as montant,sum(quantite) as quantite_vente, devise_id,agence_1_id FROM operation " +
            " where agence_1_id LIKE :AgenceId AND  (lower(type) like '%Vente_detail%' OR lower(type) like '%Vente_avec_bonus%')" +
            "   and sync_pos!='3' and sync_pos!='4'  group by agence_1_id order by count(*) desc ")
    public Performance select_performance_vente(String AgenceId) ;

    @Query("SELECT count(*) as nombre_livraison,sum(quantite) as quantite_livraison, devise_id,agence_1_id FROM operation " +
            " where agence_1_id LIKE :AgenceId AND  lower(type) like '%livraison_client%' " +
            "   and sync_pos!='3' and sync_pos!='4'  group by agence_1_id order by count(*) desc ")
    public Performance select_performance_livraison(String AgenceId) ;




    @Query("SELECT * FROM operation WHERE sync_pos LIKE '0' or sync_pos LIKE '3'")
    public List<Operation> select_operation_bysend() ;

    @Query("SELECT * FROM operation as o inner join article as a on o.article_id=a.id WHERE (o.agence_1_id= :Id_agence OR o.agence_2_id= :Id_agence) and o.sync_pos!='3' and o.sync_pos!='4'  group by agence_1_id,article_id,sens_stock  ORDER BY a.designation")
    public List<Operation> stock_agence(String Id_agence) ;

    @Query("SELECT * FROM operation WHERE agence_1_id= :Id_agence AND article_id= :Id_article " +
            " AND sync_pos!='3' and sync_pos!='4'  ")
    public List<Operation> select_byAgencebyArticle_operation(String Id_agence, String Id_article) ;

    @Query("SELECT SUM(quantite+ bonus) FROM operation WHERE (type='Entree' OR type='Entree_speciale' " +
            " OR type='Livraison_PC' OR type = 'Livraison' OR type = 'Livraison_Commande' OR type = 'Achat' " +
            " or type = 'Remise' OR type = 'Recouvrement')  " +
            " AND (agence_1_id= :Agence_id AND article_id= :Article_id) " +
            "   and sync_pos!='3' and sync_pos!='4' AND is_confirmed = 1 GROUP BY agence_1_id,article_id; ")
    public int quantite_add(String Agence_id, String Article_id) ;


    @Query("SELECT SUM(quantite+ bonus) FROM operation WHERE (type='Vente_detail' OR type='Vente_avec_bonus' " +
            " OR type='Sortie' OR type='Livraison_Client' or type='Dette')  AND (agence_1_id= :Agence_id AND article_id= :Article_id) " +
            "   and sync_pos!='3' and sync_pos!='4' AND is_confirmed = 1   group by agence_1_id,article_id; ")
    public int quantite_less(String Agence_id, String Article_id) ;


    @Query("SELECT * FROM operation WHERE user_id= :Id_user  AND agence_1_id= :Id_agence1 AND agence_2_id= :Id_agence2 AND article_id= :Id_article " +
            " AND type= :Type AND quantite= :Quantite AND bonus= :Bonus AND date= :Date AND sync_pos= :Sync_pos")
    public List<Operation> select_byAll_operation(String Id_user, String Id_agence1, String Id_agence2, String Id_article,
                                                  String Type, int Quantite, int Bonus, String Date, int Sync_pos) ;

    @Query("SELECT * FROM operation  WHERE agence_1_id LIKE :AgenceId AND  date = :Date AND (sync_pos='0' or sync_pos='3') LIMIT 1")
    public Operation select_bydate_not_sync(String AgenceId, String Date) ;

    @Query("SELECT DISTINCT(article_id) FROM operation WHERE( agence_1_id LIKE :Id_agence OR agence_2_id LIKE :Id_agence )   and sync_pos!='3' and sync_pos!='4'")
    public  List<String> select_byArticle_operation(String Id_agence) ;

    @Query("SELECT COUNT(DISTINCT(date)) FROM operation WHERE (agence_1_id =:AgenceId or agence_2_id =:AgenceId)  and sync_pos!='3' and sync_pos!='4'")
    public int count_distinct_date(String AgenceId);

    @Query("SELECT COUNT(*) FROM operation WHERE  (agence_1_id =:Id or agence_2_id =:Id) and sync_pos!='3' and sync_pos!='4'")
    public int count(String Id);

    @Query("select sum(montant) from operation where date=Date(:Date) and agence_1_id=:AgenceId  and sync_pos!='3' and sync_pos!='4'")
    public double get_amount_by_date_and_agence_id(String Date,String AgenceId);


    @Query("SELECT * FROM operation WHERE sync_pos!='3' and sync_pos!='4' AND type=:Type AND article_id=:ArticleId AND user_id=:UserId AND adding_date=:AddingDate;")
    public Operation getOtherOperation(String  UserId, String  ArticleId, String Type, String AddingDate) ;


}

