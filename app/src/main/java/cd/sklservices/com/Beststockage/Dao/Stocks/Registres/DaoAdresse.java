package cd.sklservices.com.Beststockage.Dao.Stocks.Registres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import cd.sklservices.com.Beststockage.Classes.Registres.Address;


import java.util.List;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */


@Dao
public interface DaoAdresse {

    @Insert
    void insert(Address adresse) ;

    @Update
    void update(Address adresse) ;

    @Delete
    void delete(Address adresse) ;


    @Query("DELETE FROM address")
    void delete_all() ;

    @Query("SELECT * FROM address WHERE id LIKE  :Id ")
    public Address get(String Id) ;

    @Query("SELECT * FROM address ")
    public List<Address> select_adresse() ;

    @Query("SELECT * FROM address WHERE sync_pos LIKE '0'  ORDER BY RANDOM()")
    public List<Address> select_adresse_bysend() ;

}
