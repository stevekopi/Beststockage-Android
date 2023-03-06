package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Contenance;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoContenance;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class ContenanceRepository {

    private DaoContenance daoContenance  ;
    private static ContenanceRepository instance=null;
    private static Contenance contenance ;
    private static Context context;
    private List<Contenance> contenanceArrayListe ;

    public ContenanceRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoContenance = mydata.daoContenance()  ;
    }

    public static final ContenanceRepository getInstance(Context context){

        if (context!=null){
            ContenanceRepository.context=context;}

        if (ContenanceRepository.instance==null){
            ContenanceRepository.instance=new ContenanceRepository(context);
        }
        return ContenanceRepository.instance;
    }

    public List<Contenance> getContenanceArrayListe() {
        return daoContenance.select_contenance();
    }

    public Contenance get(String id) {
        return daoContenance.get(id);
    }



    public void ajout_sync(Contenance instance)
    {
        Contenance old = get(instance.getId()) ;
        if(old == null)
        {
            daoContenance.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoContenance.update(instance) ;
            }
        }
    }

    public static final Context getContext(){
        return context;
    }

}
