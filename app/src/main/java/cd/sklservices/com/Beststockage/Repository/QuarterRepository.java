package cd.sklservices.com.Beststockage.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.LigneBonlivraison;
import cd.sklservices.com.Beststockage.Classes.Quarter;
import cd.sklservices.com.Beststockage.Dao.DaoQuarter;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class QuarterRepository {

    private DaoQuarter daoQuarter  ;
    private static QuarterRepository instance=null;
    private static Quarter quarter;
    private static Context context;
    private List<Quarter> quarterArrayListe ;

    public QuarterRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoQuarter = mydata.daoQuarter()  ;
    }

    public static final QuarterRepository getInstance(Context context){

        if (context!=null){
            QuarterRepository.context=context;}

        if (QuarterRepository.instance==null){
            QuarterRepository.instance=new QuarterRepository(context);
        }
        return QuarterRepository.instance;
    }

    public List<Quarter> getQuarterArrayListe() {
        return daoQuarter.select_quarter();
    }

    public Quarter get(String id) {
        return daoQuarter.get(id);
    }


    public void ajout_sync(Quarter instance)
    {
        Quarter old = get(instance.getId()) ;
        if(old == null)
        {
            daoQuarter.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoQuarter.update(instance) ;
            }
        }
    }


    public Quarter find(String Id_township, String Value)
    {
        Quarter val  = null ;
        List<Quarter> mylist = daoQuarter.find( Id_township, Value ) ;

        for (Quarter t:mylist)
        {
            val = t ;
        }

        return val ;
    }


    public List<Quarter> getQuarterSynchro()  {

        return daoQuarter.select_quarter_bysend() ;
    }

    public static final Context getContext(){
        return context;
    }

}
