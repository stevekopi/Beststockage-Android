package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Convoyeur;
import cd.sklservices.com.Beststockage.Dao.Registres.DaoConvoyeur;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class ConvoyeurRepository {

    private DaoConvoyeur daoConvoyeur  ;
    private static ConvoyeurRepository instance=null;
    private static Convoyeur convoyeur ;
    private static Context context;
    private List<Convoyeur> convoyeurArrayListe ;

    public ConvoyeurRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoConvoyeur = mydata.daoConvoyeur()  ;
    }

    public static final ConvoyeurRepository getInstance(Context context){

        if (context!=null){
            ConvoyeurRepository.context=context;}

        if (ConvoyeurRepository.instance==null){
            ConvoyeurRepository.instance=new ConvoyeurRepository(context);
        }
        return ConvoyeurRepository.instance;
    }

    public List<Convoyeur> getConvoyeurArrayListe() {
        return daoConvoyeur.select_convoyeur();
    }

    public Convoyeur get(String id) {
        return daoConvoyeur.get(id);
    }


    public void ajout_sync(Convoyeur instance)
    {
        Convoyeur old = get(instance.getId()) ;
        if(old == null)
        {
            daoConvoyeur.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoConvoyeur.update(instance) ;
            }
        }
    }


    public static final Context getContext(){
        return context;
    }

}
