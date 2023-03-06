package cd.sklservices.com.Beststockage.Dao.Stocks;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.Bonlivraison;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoBonLivraison {

    @Insert
    void insert(Bonlivraison bonlivraison) ;

    @Update
    void update(Bonlivraison bonlivraison) ;

    @Delete
    void delete(Bonlivraison bonlivraison) ;


    @Query("DELETE FROM bonlivraison ")
    void delete_all() ;

    @Query("SELECT * FROM bonlivraison WHERE id LIKE  :Id ")
    public Bonlivraison get(String Id) ;

    @Query("SELECT * FROM bonlivraison as bl inner join bon as b on bl.id=b.id WHERE  bl.sync_pos!='3' and bl.sync_pos!='4' ORDER BY b.date DESC")
    public List<Bonlivraison> select_bonlivraison() ;

    @Query("SELECT * FROM bonlivraison as bl inner join bon as b on bl.id=b.id WHERE  bl.sync_pos!='3' and bl.sync_pos!='4' ORDER BY b.date DESC LIMIT 1")
    public Bonlivraison getLast() ;

    @Query("SELECT DISTINCT(date)  FROM bon WHERE sync_pos != '3' and sync_pos != '4' ORDER BY date DESC ")
    public List<String> select_distinct_bonlivraison() ;

    @Query("SELECT *  FROM bonlivraison as bl inner join bon as b on bl.id=b.id WHERE b.date= :Date AND bl.sync_pos != '3' AND bl.sync_pos != '4' ORDER BY b.date DESC ")
    public List<Bonlivraison> select_bydate_bonlivraison(String Date) ;

    @Query("SELECT * FROM bonlivraison WHERE sync_pos ='0' and sync_pos=3  ORDER BY RANDOM()")
    public List<Bonlivraison> select_bonlivraison_bysend() ;



}
