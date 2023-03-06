package cd.sklservices.com.Beststockage.Dao.Stocks.Registres;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Client;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoClient {

    @Insert
    void insert(Client client) ;

    @Update
    void update(Client client) ;

    @Delete
    void delete(Client client) ;

    @Query("DELETE FROM client")
    void delete_all() ;

    @Query("SELECT * FROM client WHERE id LIKE  :Id ")
    public Client get(String Id) ;

    @Query("SELECT * FROM client where sync_pos!='3' and sync_pos!='4'")
    public List<Client> select_client() ;


    @Query("SELECT identity.id as id , identity.address_id as adresse_id, identity.type as type," +
            " identity.name as name, identity.telephone as telephone, identity.adding_date as adding_date, " +
            " identity.sync_pos as sync_pos,identity.pos as pos FROM client, identity WHERE  client.sync_pos!='3' and client.sync_pos!='4' and identity.id = client.id " +
            " AND identity.name > :Nom ORDER BY identity.name ASC")
    public List<Identity> select_clientLoading2(String Nom ) ;

    @Query("SELECT identity.id as id , identity.address_id as adresse_id, identity.type as type," +
            " identity.name as name, identity.telephone as telephone, identity.adding_date as adding_date, " +
            " identity.sync_pos as sync_pos,identity.pos as pos FROM client, identity WHERE  client.sync_pos!='3' and client.sync_pos!='4' and identity.id = client.id " +
            "  ORDER BY identity.name ASC LIMIT 1  ")
    public List<Identity> select_clientLoading() ;

    @Query("SELECT COUNT(*) FROM client  where sync_pos!='3' and sync_pos!='4'")
    int count();

}
