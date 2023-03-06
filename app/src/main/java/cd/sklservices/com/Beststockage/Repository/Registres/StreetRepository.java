package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Street;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoStreet;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class StreetRepository {

    private DaoStreet daoStreet  ;
    private static StreetRepository instance=null;
    private static Street street;
    private static Context context;
    private List<Street> streetArrayListe ;

    public StreetRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoStreet = mydata.daoStreet()  ;
    }

    public static final StreetRepository getInstance(Context context){

        if (context!=null){
            StreetRepository.context=context;}

        if (StreetRepository.instance==null){
            StreetRepository.instance=new StreetRepository(context);
        }
        return StreetRepository.instance;
    }

    public List<Street> getStreetArrayListe() {
        return daoStreet.select_street();
    }

    public Street get(String id) {
        return daoStreet.get(id);
    }


    public void ajout_sync(Street instance)
    {
        Street old = get(instance.getId()) ;
        if(old == null)
        {
            daoStreet.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoStreet.update(instance) ;
            }
        }
    }



    public Street find(String Id_quarter, String Value)
    {
        Street val  = null ;
        List<Street> mylist = daoStreet.find( Id_quarter, Value ) ;

        for (Street t:mylist)
        {
            val = t ;
        }

        return val ;
    }

    public List<Street> getStreetSynchro()  {

        return daoStreet.select_street_bysend() ;
    }

    public static final Context getContext(){
        return context;
    }

}
