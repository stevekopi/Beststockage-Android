package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Town;
import cd.sklservices.com.Beststockage.Dao.Registres.DaoTown;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class TownRepository {

    private DaoTown daoTown  ;
    private static TownRepository instance=null;
    private static Town town;
    private static Context context;

    public TownRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoTown = mydata.daoTown()  ;
    }

    public static final TownRepository getInstance(Context context){

        if (context!=null){
            TownRepository.context=context;}

        if (TownRepository.instance==null){
            TownRepository.instance=new TownRepository(context);
        }
        return TownRepository.instance;
    }

    public void ajout_sync(Town instance)
    {
       try{
           Town old = get(instance.getId()) ;
           if(old == null)
           {
               daoTown.insert(instance);
           }
           else
           {
               if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
               {
                   daoTown.update(instance) ;
               }
           }
       }
       catch (Exception e){
           Log.d("Assert","TownRepository.gets() "+e.toString());
       }
    }



    public List<Town> getTownArrayListe() {
        return daoTown.select_town();
    }

    public Town get(String id) {
        return daoTown.get(id);
    }




    public Town find(String Value)
    {
        Town town = null ;
        List<Town> mylist = daoTown.find( Value ) ;

        for (Town t:mylist)
        {
           town = t ;
        }

        return town ;
    }



    public static final Context getContext(){
        return context;
    }

}
