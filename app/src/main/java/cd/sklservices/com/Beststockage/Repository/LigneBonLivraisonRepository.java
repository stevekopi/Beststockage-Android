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
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class LigneBonLivraisonRepository {

    private DaoLigneBonLivraison daoligne  ;
    private static LigneBonLivraisonRepository instance=null;
    private static LigneBonlivraison ligne ;
    private static Context context;
    private List<LigneBonlivraison> ligneArrayListe ;

    public LigneBonLivraisonRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoligne = mydata.daoLignebonlivraison() ;
    }

    public static final LigneBonLivraisonRepository getInstance(Context context){

        if (context!=null){
            LigneBonLivraisonRepository.context=context;}

        if (LigneBonLivraisonRepository.instance==null){
            LigneBonLivraisonRepository.instance=new LigneBonLivraisonRepository(context);
        }
        return LigneBonLivraisonRepository.instance;
    }

    public List<LigneBonlivraison> getLigneArrayListe() {
        return ligneArrayListe;
    }

    public void setLigneArrayListe(List<LigneBonlivraison> ligneArrayListe) {
        this.ligneArrayListe = ligneArrayListe;
    }

    public void ajout_sync(LigneBonlivraison instance)
    {
        LigneBonlivraison old = get(instance.getId()) ;
        if(old == null)
        {
            daoligne.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoligne.update(instance) ;
            }
        }
    }



    public AsyncTask delete2(String Id)
    {
        daoligne.delete2(Id) ;

        return null ;
    }

    public AsyncTask delete_all()
    {
        daoligne.delete_all();
        return null ;
    }



    public LigneBonlivraison get(String id){

        try{
           return daoligne.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," Dao ligne bon livraison  "+e.toString());
        }
        return null;
    }

    public List<LigneBonlivraison> getArraylistLigneBonLivraison(){

        try{
            return daoligne.select_LigneBonlivraison() ;

        }
        catch (Exception e){
            Log.d("Assert"," Dao ligne bon livraison  "+e.toString());
        }
        return null;
    }

    public List<LigneBonlivraison> getById_bonLivraison(String id){

        try{
            return daoligne.select_byid_BonLivraison_LigneBonlivraison(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," Dao ligne bon livraison  "+e.toString());
        }
        return null;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<LigneBonlivraison> mylist =  daoligne.select_LigneBonlivraison() ;
            instance.setLigneArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao Categorie.getsErreur: "+e.toString());
        }
    }


    public void update(LigneBonlivraison l)
    {
        new UpdateLigneAsyncTask(daoligne).execute(l) ;
    }

    public class UpdateLigneAsyncTask extends AsyncTask<LigneBonlivraison, Void, Void>
    {
        private DaoLigneBonLivraison daol ;

        private UpdateLigneAsyncTask(DaoLigneBonLivraison daol)
        {
            this.daol = daol ;
        }
        @Override
        protected Void doInBackground(LigneBonlivraison... l) {

            try{

                daol.update(l[0]);

            }catch (Exception e){
                Log.d("Assert","Ajout Ligne Bon de livraison   ");
            }

            return null ;
        }
    }



    public List<LigneBonlivraison> getLigneBonLivraisonSynchro(){
        try{

            return  daoligne.select_ligne_bonlivraison_bysend();
        }
        catch (Exception e){
            Log.d("Assert","DaoCommandeLoc().getDistinct : "+e.toString());
            return  null;

        }
    }


    public void delete(LigneBonlivraison l)
    {
        new DeleteLigneAsyncTask(daoligne).execute(l) ;
    }

    public class DeleteLigneAsyncTask extends AsyncTask<LigneBonlivraison, Void, Void>
    {
        private DaoLigneBonLivraison daol ;

        private DeleteLigneAsyncTask(DaoLigneBonLivraison daol)
        {
            this.daol = daol ;
        }
        @Override
        protected Void doInBackground(LigneBonlivraison... l) {

            try{
                    daol.delete(l[0]);

            }catch (Exception e){
                Log.d("Assert","Ajout Ligne Bon de livraison   ");
            }

            return null ;
        }
    }

    public void setLigneBonLivraison(LigneBonlivraison ligneB){
        LigneBonLivraisonRepository.ligne = ligneB;
    }

    public LigneBonlivraison getLigneBonLivraison(){
        if (ligne ==null){return null;}
        else{return  ligne ;}
    }

}
