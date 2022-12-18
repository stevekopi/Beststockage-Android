package cd.sklservices.com.Beststockage.Repository;

import android.content.Context;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Driver;
import cd.sklservices.com.Beststockage.Dao.DaoDriver;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class DriverRepository {

    private DaoDriver daoDriver  ;
    private static DriverRepository instance=null;
    private static Driver driver ;
    private static Context context;
    private List<Driver> vehiculeArrayListe ;

    public DriverRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoDriver = mydata.daoDriver()  ;
    }

    public static final DriverRepository getInstance(Context context){

        if (context!=null){
            DriverRepository.context=context;}

        if (DriverRepository.instance==null){
            DriverRepository.instance=new DriverRepository(context);
        }
        return DriverRepository.instance;
    }

    public List<Driver> getDriverArrayListe() {
        return daoDriver.select_driver();
    }

    public Driver get(String id) {
        return daoDriver.get(id);
    }


    public void ajout_sync(Driver instance)
    {
        Driver old = get(instance.getId()) ;
        if(old == null)
        {
            daoDriver.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoDriver.update(instance) ;
            }
        }
    }

    public static final Context getContext(){
        return context;
    }

}
