package cd.sklservices.com.Beststockage.Repository.Controles;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Controles.Controle;
import cd.sklservices.com.Beststockage.Dao.Controles.DaoControle;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class ControleRepository {

    private DaoControle daocontrole  ;
    private static ControleRepository instance=null;
    private static Controle controle;
    private static Context context;
    private List<Controle> controleArrayListe ;

    public ControleRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daocontrole = mydata.daoControle() ;
    }

    public static final ControleRepository getInstance(Context context){

        if (context!=null){
            ControleRepository.context=context;}

        if (ControleRepository.instance==null){
            ControleRepository.instance=new ControleRepository(context);
        }
        return ControleRepository.instance;
    }

    public List<Controle> getControleArrayListe() {
        return controleArrayListe;
    }

    public void setControleArrayListe(List<Controle> articlesArrayListe) {
        this.controleArrayListe = articlesArrayListe;
    }

    public void ajout_sync(Controle instance)
    {
        Controle old = get(instance.getId()) ;
        if(old == null)
        {
            daocontrole.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daocontrole.update(instance) ;
            }
        }
    }


    public Controle get(String id){

        try{
            return daocontrole.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
        return null;
    }



    public AsyncTask delete_all()
    {
        daocontrole.delete_all();
        return null ;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<Controle> mylist =  daocontrole.gets() ;
            instance.setControleArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao Controle.getsErreur: "+e.toString());
        }
    }

    public List<Controle> get_for_post_sync(){

        try{
            return  daocontrole.get_for_post_sync();

        }
        catch (Exception e){
            Log.d("Assert"," DaoFournisseur  .get() "+e.toString());
        }
        return null;
    }

    public Controle get_by_date_and_agence_id(String date){

        try{
            return  daocontrole.get_by_date_and_agence_id(MainActivity.getCurrentUser().getAgence_id(),date);

        }
        catch (Exception e){
            Log.d("Assert"," DaoFournisseur  .get() "+e.toString());
        }
        return null;
    }





    public String getUserId(){
        if (controle==null){return null;}
        else{return  controle.getUser_id();}
    }

    public String getAgenceId(){
        if (controle==null){return null;}
        else{return  controle.getAgence_id();}
    }

    public String getId(){
        if (controle==null){return null;}
        else{return  controle.getId();}
    }

    public String getType(){
        if (controle==null){return null;}
        else{return  controle.getType();}
    }

    public String getDate(){
        if (controle==null){return null;}
        else{return  controle.getDate();}
    }

    public String getEtat(){
        if (controle==null){return null;}
        else{return  controle.getEtat();}
    }


    public static final Context getContext(){
        return context;
    }

}
