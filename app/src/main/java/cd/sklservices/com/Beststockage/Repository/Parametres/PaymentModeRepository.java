package cd.sklservices.com.Beststockage.Repository.Parametres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Parametres.PaymentMode;
import cd.sklservices.com.Beststockage.Dao.Parametres.DaoPaymentMode;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class PaymentModeRepository {

    private DaoPaymentMode daopaymentMode  ;
    private static PaymentModeRepository instance=null;
    private static PaymentMode paymentMode;
    private static Context context;
    private List<PaymentMode> paymentModeArrayListe ;

    public PaymentModeRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daopaymentMode = mydata.daoPaymentMode() ;
    }

    public static final PaymentModeRepository getInstance(Context context){

        if (context!=null){
            PaymentModeRepository.context=context;}

        if (PaymentModeRepository.instance==null){
            PaymentModeRepository.instance=new PaymentModeRepository(context);
        }
        return PaymentModeRepository.instance;
    }

    public List<PaymentMode> getPaymentModeArrayListe() {
        return paymentModeArrayListe;
    }

    public void setPaymentModeArrayListe(List<PaymentMode> articlesArrayListe) {
        this.paymentModeArrayListe = articlesArrayListe;
    }

    public void ajout_sync(PaymentMode instance)
    {
        PaymentMode old = get(instance.getId()) ;
        if(old == null)
        {
            daopaymentMode.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daopaymentMode.update(instance) ;
            }
        }
    }


    public PaymentMode get(String id){

        try{
            return daopaymentMode.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
        return null;
    }



    public AsyncTask delete_all()
    {
        daopaymentMode.delete_all();
        return null ;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<PaymentMode> mylist =  daopaymentMode.gets() ;
            instance.setPaymentModeArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao PaymentMode.getsErreur: "+e.toString());
        }
    }



    public String getDesignation(){
        if (paymentMode==null){return null;}
        else{return  paymentMode.getDesignation();}
    }

    public String getId(){
        if (paymentMode==null){return null;}
        else{return  paymentMode.getId();}
    }


    public static final Context getContext(){
        return context;
    }

}
