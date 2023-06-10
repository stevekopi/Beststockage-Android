package cd.sklservices.com.Beststockage.Repository.Parametres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Dao.Parametres.DaoProduitFacture;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class ProduitFactureRepository {

    private DaoProduitFacture daoproduitFacture  ;
    private static ProduitFactureRepository instance=null;
    private static ProduitFacture produitFacture;
    private static Context context;
    private List<ProduitFacture> produitFactureArrayListe ;

    public ProduitFactureRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoproduitFacture = mydata.daoProduitFacture() ;
    }

    public static final ProduitFactureRepository getInstance(Context context){

        if (context!=null){
            ProduitFactureRepository.context=context;}

        if (ProduitFactureRepository.instance==null){
            ProduitFactureRepository.instance=new ProduitFactureRepository(context);
        }
        return ProduitFactureRepository.instance;
    }

    public List<ProduitFacture> getProduitFactureArrayListe() {
        return produitFactureArrayListe;
    }

    public void setProduitFactureArrayListe(List<ProduitFacture> articlesArrayListe) {
        this.produitFactureArrayListe = articlesArrayListe;
    }

    public void ajout_sync(ProduitFacture instance)
    {
        try{
            ProduitFacture old = get(instance.getId()) ;
            if(old == null)
            {
                daoproduitFacture.insert(instance);
            }
            else
            {
                if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
                {
                    daoproduitFacture.update(instance) ;
                }
            }

        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
    }


    public ProduitFacture get(String id){

        try{
            return daoproduitFacture.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
        return null;
    }



    public AsyncTask delete_all()
    {
        daoproduitFacture.delete_all();
        return null ;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<ProduitFacture> mylist =  daoproduitFacture.gets() ;
            instance.setProduitFactureArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao ProduitFacture.getsErreur: "+e.toString());
        }
    }



    public String getDesignation(){
        if (produitFacture==null){return null;}
        else{return  produitFacture.getDesignation();}
    }

    public String getSensFinance(){
        if (produitFacture==null){return null;}
        else{return  produitFacture.getSens_finance();}
    }

    public String get_id(){
        if (produitFacture==null){return null;}
        else{return  produitFacture.getId();}
    }

    public List<ProduitFacture> getLoading() {
        try{
            return daoproduitFacture.loading();
        }
        catch (Exception e){

        }
        return null;
    }

    public void setInstance(ProduitFacture instance){
        ProduitFactureRepository.produitFacture=instance;
    }

    public static final Context getContext(){
        return context;
    }

    public List<String> getDistinctDevisesId(){
        return daoproduitFacture.getDistinctDevisesId();
    }

    public List<ProduitFacture> getByDevise(Devise devise){
        return daoproduitFacture.getByDeviseId(devise.getId());
    }

}
