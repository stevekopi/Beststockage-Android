package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Delegue;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoDelegue;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class DelegueRepository {

    private DaoDelegue daoDelegue  ;
    private static DelegueRepository instance=null;
    private static Delegue delegue ;
    private static Context context;
    private List<Delegue> delegueArrayListe ;

    public DelegueRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoDelegue = mydata.daoDelegue()  ;
    }

    public static final DelegueRepository getInstance(Context context){

        if (context!=null){
            DelegueRepository.context=context;}

        if (DelegueRepository.instance==null){
            DelegueRepository.instance=new DelegueRepository(context);
        }
        return DelegueRepository.instance;
    }

    public List<Delegue> getDelegueArrayListe() {
        return daoDelegue.select_delegue();
    }

    public Delegue get(String id) {
        return daoDelegue.get(id);
    }


    public void ajout_sync(Delegue instance)
    {
        Delegue old = get(instance.getId()) ;
        if(old == null)
        {
            daoDelegue.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoDelegue.update(instance) ;
            }
        }
    }

    public static final Context getContext(){
        return context;
    }

}
