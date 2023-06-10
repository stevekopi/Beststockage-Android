package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Categorie;
import cd.sklservices.com.Beststockage.Dao.Registres.DaoCategorie;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class CategorieRepository {

    private DaoCategorie daocategorie  ;
    private static CategorieRepository instance=null;
    private static Categorie categorie;
    private static Context context;
    private List<Categorie> categorieArrayListe ;

    public CategorieRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daocategorie = mydata.daoCategorie() ;
    }

    public static final CategorieRepository getInstance(Context context){

        if (context!=null){
            CategorieRepository.context=context;}

        if (CategorieRepository.instance==null){
            CategorieRepository.instance=new CategorieRepository(context);
        }
        return CategorieRepository.instance;
    }

    public List<Categorie> getCategorieArrayListe() {
        return categorieArrayListe;
    }

    public void setCategorieArrayListe(List<Categorie> articlesArrayListe) {
        this.categorieArrayListe = articlesArrayListe;
    }

    public void ajout_sync(Categorie instance)
    {
        Categorie old = get(instance.getId()) ;
        if(old == null)
        {
            daocategorie.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daocategorie.update(instance) ;
            }
        }
    }


    public Categorie get(String id){

        try{
            return  daocategorie.get(id) ;


        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
        return null;
    }


    public Categorie getList(String id){

        try{
            return daocategorie.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
        return null;
    }

    public AsyncTask delete_all()
    {
        daocategorie.delete_all();
        return null ;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<Categorie> mylist =  daocategorie.select_categorie() ;
            instance.setCategorieArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao Categorie.getsErreur: "+e.toString());
        }
    }



    public String getDesignation(){
        if (categorie==null){return null;}
        else{return  categorie.getDesignation();}
    }

    public String get_id(){
        if (categorie==null){return null;}
        else{return  categorie.getId();}
    }


    public static final Context getContext(){
        return context;
    }

}
