package cd.sklservices.com.Beststockage.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Outils.*;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Dao.*;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class DeviseRepository {

    private DaoDevise daodevise  ;
    private static DeviseRepository instance=null;
    private static Devise devise;
    private static Context context;
    private List<Devise> deviseArrayListe ;

    public DeviseRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daodevise = mydata.daoDevise() ;
    }

    public static final DeviseRepository getInstance(Context context){

        if (context!=null){
            DeviseRepository.context=context;}

        if (DeviseRepository.instance==null){
            DeviseRepository.instance=new DeviseRepository(context);
        }
        return DeviseRepository.instance;
    }

    public List<Devise> getDeviseArrayListe() {
        return deviseArrayListe;
    }

    public void setDeviseArrayListe(List<Devise> articlesArrayListe) {
        this.deviseArrayListe = articlesArrayListe;
    }

    public void ajout_sync(Devise instance)
    {
        Devise old = get(instance.getId()) ;
        if(old == null)
        {
            daodevise.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daodevise.update(instance) ;
            }
        }
    }


    public Devise get(String id){

        try{
            return daodevise.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
        return null;
    }



    public AsyncTask delete_all()
    {
        daodevise.delete_all();
        return null ;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<Devise> mylist =  daodevise.select_devise() ;
            instance.setDeviseArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao Devise.getsErreur: "+e.toString());
        }
    }



    public String getName(){
        if (devise==null){return null;}
        else{return  devise.getName();}
    }

    public String get_description(){
        if (devise==null){return null;}
        else{return  devise.getDescription();}
    }

    public String get_id(){
        if (devise==null){return null;}
        else{return  devise.getId();}
    }


    public static final Context getContext(){
        return context;
    }

}
