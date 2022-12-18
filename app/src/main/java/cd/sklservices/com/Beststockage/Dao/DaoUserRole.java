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
public interface DaoUserRole {
    @Insert
    void insert(UserRole user_role) ;

    @Update
    void update(UserRole user_role) ;

    @Delete
    void delete(UserRole user_role) ;


    @Query("DELETE FROM user_role")
    void delete_all() ;

    @Query("SELECT * FROM user_role WHERE id LIKE  :Id ")
    public UserRole get(String Id) ;

    @Query("SELECT * FROM user_role where  sync_pos!='3' and sync_pos!='4'")
    public List<UserRole> select_user_role() ;


}
