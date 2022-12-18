package cd.sklservices.com.Beststockage.Repository;

import android.content.Context;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Vehicule;
import cd.sklservices.com.Beststockage.Dao.DaoVehicule;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class VehiculeRepository {

    private DaoVehicule daoVehicule  ;
    private static VehiculeRepository instance=null;
    private static Vehicule vehicule ;
    private static Context context;
    private List<Vehicule> vehiculeArrayListe ;

    public VehiculeRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoVehicule = mydata.daoVehicule()  ;
    }

    public static final VehiculeRepository getInstance(Context context){

        if (context!=null){
            VehiculeRepository.context=context;}

        if (VehiculeRepository.instance==null){
            VehiculeRepository.instance=new VehiculeRepository(context);
        }
        return VehiculeRepository.instance;
    }

    public void ajout_sync(Vehicule instance)
    {
        Vehicule old = get(instance.getId()) ;
        if(old == null)
        {
            daoVehicule.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoVehicule.update(instance) ;
            }
        }
    }



    public List<Vehicule> getVehiculeArrayListe() {
        return daoVehicule.select_vehicule();
    }

    public Vehicule get(String id) {
        return daoVehicule.get(id);
    }




    public static final Context getContext(){
        return context;
    }

}
