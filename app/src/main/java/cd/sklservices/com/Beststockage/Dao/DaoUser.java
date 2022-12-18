package cd.sklservices.com.Beststockage.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.*;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

@Dao
public interface DaoUser {

    @Insert
    void insert(User user) ;

    @Update
    void update(User user) ;

    @Delete
    void delete(User user) ;

    @Query("DELETE FROM user")
    void delete_all() ;

    @Query("SELECT * FROM user WHERE id=:Id")
    public User get(String Id) ;

    @Query("SELECT u.id,u.agence_id,u.username,u.password,u.user_role_id,u.statut,u.adding_date,u.updated_date,u.sync_pos,u.pos FROM user AS u " +
            " INNER JOIN agence AS a ON u.agence_id=a.id WHERE a.appartenance='Privee' and u.sync_pos!='3' and u.sync_pos!='4' and a.sync_pos!='3' and a.sync_pos!='4';")
    public List<User> select_user() ;

    @Query("SELECT * FROM user WHERE username = :username and password = :Password and sync_pos!='3' and sync_pos!='4'")
    public List<User> get_access(String username, String Password) ;

    @Query("SELECT * FROM user WHERE agence_id= :Agence_id and sync_pos!='3' and sync_pos!='4'")
    public List<User> get_by_agence(String Agence_id) ;

    @Query("SELECT * FROM user WHERE sync_pos = '0' or sync_pos='3' ORDER BY RANDOM() ")
    public List<User> select_user_bysend() ;

}
