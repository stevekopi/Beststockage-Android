package cd.sklservices.com.Beststockage.Dao.Parametres;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Parametres.PaymentMode;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Dao
public interface DaoPaymentMode {
    @Insert
    void insert(PaymentMode paymentMode) ;

    @Update
    void update(PaymentMode paymentMode) ;

    @Delete
    void delete(PaymentMode paymentMode) ;


    @Query("DELETE FROM payment_mode")
    void delete_all() ;

    @Query("SELECT * FROM payment_mode WHERE id LIKE  :Id ")
    public PaymentMode get(String Id) ;

    @Query("SELECT * FROM payment_mode  where sync_pos!='3' and sync_pos!='4'")
    public List<PaymentMode> gets() ;


}
