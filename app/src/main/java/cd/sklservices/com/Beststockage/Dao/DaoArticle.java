package cd.sklservices.com.Beststockage.Dao;

import cd.sklservices.com.Beststockage.Classes.*;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoArticle {
    @Insert
    void insert(Article article) ;

    @Update
    void update(Article article) ;

    @Delete
    void delete(Article article) ;


    @Query("DELETE FROM article")
    void delete_all() ;

    @Query("SELECT * FROM article WHERE id LIKE  :Id ")
    public Article get(String Id) ;

    @Query("SELECT * FROM article  where sync_pos!='3' and sync_pos!='4'")
    public List<Article> select_article() ;

    @Query("SELECT * FROM article WHERE fournisseur_id= :Fournisseur_id  and sync_pos!='3' and sync_pos!='4'")
    public List<Article> get_byforunisseur_article(String Fournisseur_id) ;

    @Query("SELECT * FROM article WHERE designation= :Designation  and sync_pos!='3' and sync_pos!='4'")
    public List<Article> get_byDesignation_article(String Designation) ;

    @Query("SELECT * FROM article WHERE designation >= :Designation  and sync_pos!='3' and sync_pos!='4' ORDER BY designation ASC")
    public List<Article> select_articleLoading2( String Designation ) ;

    @Query("SELECT * FROM article  where sync_pos!='3' and sync_pos!='4' ORDER BY designation ASC LIMIT 1 ")
    public List<Article> select_articleLoading() ;

    @Query("SELECT COUNT(*) FROM article")
    int count();
}
