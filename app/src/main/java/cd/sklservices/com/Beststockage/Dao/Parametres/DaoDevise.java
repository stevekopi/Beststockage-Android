package cd.sklservices.com.Beststockage.Dao.Parametres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoDevise {
    @Insert
    void insert(Devise devise) ;

    @Update
    void update(Devise devise) ;

    @Delete
    void delete(Devise devise) ;


    @Query("DELETE FROM devise")
    void delete_all() ;

    @Query("SELECT * FROM devise WHERE id LIKE  :Id ")
    public Devise get(String Id) ;

    @Query("SELECT * FROM devise WHERE is_default =1  and sync_pos!=3 and sync_pos!=4")
    public Devise getDefault();

    @Query("SELECT * FROM devise WHERE is_local =1 and sync_pos!=3 and sync_pos!=4")
    public Devise getLocal();

    @Query("SELECT * FROM devise WHERE is_default_converter =1  and sync_pos!=3 and sync_pos!=4")
    public Devise getDefaultConverter() ;

    @Query("SELECT * FROM devise  where sync_pos!='3' and sync_pos!='4'")
    public List<Devise> select_devise() ;

    @Query("SELECT COUNT(*) FROM devise  WHERE sync_pos!='3' and sync_pos!='4'")
    int count();

    @Query("SELECT * FROM devise  WHERE sync_pos!='3' and sync_pos!='4'")
    public List<Devise> loading();
}
