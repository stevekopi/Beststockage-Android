package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.District;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoDistrict;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class DistrictRepository {

    private DaoDistrict daoDistrict  ;
    private static DistrictRepository instance=null;
    private static District district;
    private static Context context;
    private List<District> districtArrayListe ;

    public DistrictRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoDistrict = mydata.daoDistrict()  ;
    }

    public static final DistrictRepository getInstance(Context context){

        if (context!=null){
            DistrictRepository.context=context;}

        if (DistrictRepository.instance==null){
            DistrictRepository.instance=new DistrictRepository(context);
        }
        return DistrictRepository.instance;
    }

    public List<District> getDistrictArrayListe() {
        return daoDistrict.select_district();
    }

    public District get(String id) {
        return daoDistrict.get(id);
    }


    public void ajout_sync(District instance)
    {
        District old = get(instance.getId()) ;
        if(old == null)
        {
            daoDistrict.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoDistrict.update(instance) ;
            }
        }
    }


    public District find(String Id_town, String Value)
    {
        District val  = null ;
        List<District> mylist = daoDistrict.find( Id_town, Value ) ;

        for (District t:mylist)
        {
            val = t ;
        }

        return val ;
    }

    public List<District> getByTown(String Id) {
        return daoDistrict.select_byTown_district(Id) ;
    }


    public static final Context getContext(){
        return context;
    }

}
