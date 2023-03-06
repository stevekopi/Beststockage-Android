package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Township;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoTownship;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class TownshipRepository {

    private DaoTownship daoTownship  ;
    private static TownshipRepository instance=null;
    private static Township township;
    private static Context context;
    private List<Township> townships ;

    public TownshipRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoTownship = mydata.daoTownship()  ;
    }

    public static final TownshipRepository getInstance(Context context){

        if (context!=null){
            TownshipRepository.context=context;}

        if (TownshipRepository.instance==null){
            TownshipRepository.instance=new TownshipRepository(context);
        }
        return TownshipRepository.instance;
    }

    public void ajout_sync(Township instance)
    {
        Township old = get(instance.getId()) ;
        if(old == null)
        {
            daoTownship.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoTownship.update(instance) ;
            }
        }
    }



    public List<Township> getTownshipArrayListe() {
        return daoTownship.select_township();
    }

    public Township get(String id) {
        return daoTownship.get(id);
    }



    public Township find(String Id_district, String Value)
    {
        Township val  = null ;
        List<Township> mylist = daoTownship.find( Id_district, Value ) ;

        for (Township t:mylist)
        {
            val = t ;
        }

        return val ;
    }


    public List<Township> getByDistrict(String Id) {
        return daoTownship.select_byDistrict_township(Id) ;
    }

    public static final Context getContext(){
        return context;
    }

}
