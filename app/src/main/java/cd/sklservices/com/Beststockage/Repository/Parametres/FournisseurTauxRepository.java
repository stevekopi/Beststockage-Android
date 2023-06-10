package cd.sklservices.com.Beststockage.Repository.Parametres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Parametres.FournisseurTaux;
import cd.sklservices.com.Beststockage.Dao.Parametres.DaoFournisseurTaux;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class FournisseurTauxRepository {

    private DaoFournisseurTaux daofournisseurTaux  ;
    private static FournisseurTauxRepository instance=null;
    private static FournisseurTaux fournisseurTaux;
    private static Context context;
    private List<FournisseurTaux> fournisseurTauxArrayListe ;

    public FournisseurTauxRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daofournisseurTaux = mydata.daoFournisseurTaux() ;
    }

    public static final FournisseurTauxRepository getInstance(Context context){

        if (context!=null){
            FournisseurTauxRepository.context=context;}

        if (FournisseurTauxRepository.instance==null){
            FournisseurTauxRepository.instance=new FournisseurTauxRepository(context);
        }
        return FournisseurTauxRepository.instance;
    }

    public List<FournisseurTaux> getFournisseurTauxArrayListe() {
        return fournisseurTauxArrayListe;
    }

    public void setFournisseurTauxArrayListe(List<FournisseurTaux> articlesArrayListe) {
        this.fournisseurTauxArrayListe = articlesArrayListe;
    }

    public void ajout_sync(FournisseurTaux instance)
    {
        FournisseurTaux old = get(instance.getId()) ;
        if(old == null)
        {
            daofournisseurTaux.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daofournisseurTaux.update(instance) ;
            }
        }
    }


    public FournisseurTaux get(String id){

        try{
            return daofournisseurTaux.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," FournisseurTauxRepository.get() "+e.toString());
        }
        return null;
    }


    public AsyncTask delete_all()
    {
        daofournisseurTaux.delete_all();
        return null ;
    }

    public List<FournisseurTaux> getLoading() {
        try{
            return daofournisseurTaux.loading();
        }
        catch (Exception e){

        }
        return null;
    }

    public int count(){
        return daofournisseurTaux.count();
    }

    public static final Context getContext(){
        return context;
    }

}
