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
 * Created by Steve Kopi Loseme on 03/02/2021.
 */

public class FournisseurRepository {

    private DaoFournisseur daofournisseur  ;
    private static FournisseurRepository instance=null;
    private static Fournisseur fournisseur ;
    private static Context context;
    private List<Fournisseur> fournisseurArrayListe ;

    public FournisseurRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daofournisseur = mydata.daoFournisseur() ;
    }

    public static final FournisseurRepository getInstance(Context context){

        if (context!=null){
            FournisseurRepository.context=context;}

        if (FournisseurRepository.instance==null){
            FournisseurRepository.instance=new FournisseurRepository(context);
        }
        return FournisseurRepository.instance;
    }

    public List<Fournisseur> getFournisseurArrayListe() {

       try{
           List<Fournisseur> instances=new ArrayList<>();
           List<Fournisseur> temp=new ArrayList<>();
           temp=daofournisseur.select_fournisseur();
           for (Fournisseur instance:temp) {
                instances.add(get(instance.getId(),true));
           }
           return instances;
       }
       catch (Exception e){

       }
       return null;
    }

    public void setFournisseurArrayListe(List<Fournisseur> fournisseurArrayListe) {
        this.fournisseurArrayListe = fournisseurArrayListe;
    }

    public void ajout_sync(Fournisseur instance)
    {
        Fournisseur old = get(instance.getId(),false) ;
        if(old == null)
        {
            daofournisseur.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daofournisseur.update(instance) ;
            }
        }
    }

    public ArrayList<Fournisseur> getByArticleId(String article_id){

        try{

            ArrayList<Fournisseur> arrayList=new ArrayList<Fournisseur>();
           List<Fournisseur> mylist =  daofournisseur.getByArticleId2(article_id) ;

            for (Fournisseur f : mylist){
                arrayList.add(f);
            }

            return arrayList;

        }
        catch (Exception e){
            Log.d("Assert","DaoFournisLoc.getByArticleId() "+e.toString());
        }
        return null;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<Fournisseur> mylist =  daofournisseur.select_fournisseur() ;
            instance.setFournisseurArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","FournisseurRepository getsErreur: "+e.toString());
        }
    }

    public String get_id_by_name(String name){

        try{
            return daofournisseur.get_id_by_name(name);

        }
        catch (Exception e){
            Log.d("Assert","FournisseurRepository get_id_by_name: "+e.toString());
        }
        return null;
    }



    public Fournisseur get(String id,boolean withIdentity){
        Fournisseur instance=null;
        try{
            instance=  daofournisseur.get(id) ;
            if(withIdentity)
                instance.setIdentity(new IdentityRepository(IdentityRepository.getContext()).get(instance.getId(),true));
        }
        catch (Exception e){
            Log.d("Assert"," DaoFournisseur  .get() "+e.toString());
        }
        return instance;
    }



    public AsyncTask delete_all()
    {
        daofournisseur.delete_all();
        return null ;
    }


    public void setFournisseur(Fournisseur fournisseur){
        instance.fournisseur =fournisseur;
    }


    public String getId(){
        if (fournisseur==null){return null;}
        else{return  fournisseur.getId();}
    }

    public Fournisseur getFournisseur(){
        if (fournisseur==null){return null;}
        else{return  fournisseur;}
    }


    public static final Context getContext(){
        return context;
    }


}
