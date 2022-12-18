package cd.sklservices.com.Beststockage.Repository;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Outils.*;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Dao.*;
import cd.sklservices.com.Beststockage.R;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class BonLivraisonRepository {

    private DaoBonLivraison daoBonLivraison  ;
    private DaoLigneBonLivraison daoLigneBonLivraison  ;
    private static BonLivraisonRepository instance=null;
    private static Bonlivraison bonLivraison;
    private static Context context;
    private List<Bonlivraison> bonlivraisonArrayList;

    public BonLivraisonRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoBonLivraison = mydata.daoBonlivraison() ;
        daoLigneBonLivraison = mydata.daoLignebonlivraison() ;
    }

    public static final BonLivraisonRepository getInstance(Context context){

        if (context!=null){
            BonLivraisonRepository.context=context;}

        if (BonLivraisonRepository.instance==null){
            BonLivraisonRepository.instance=new BonLivraisonRepository(context);
        }
        return BonLivraisonRepository.instance;
    }

    public void ajout_sync(Bonlivraison instance)
    {
        Bonlivraison old = get(instance.getId()) ;
        if(old == null)
        {
            daoBonLivraison.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoBonLivraison.update(instance) ;
            }
        }
    }


    public void setBonlivraisonArrayList(List<Bonlivraison> bonlivraisonArrayList) {
        this.bonlivraisonArrayList = bonlivraisonArrayList;
    }




    public AsyncTask delete_all()
    {
        daoBonLivraison.delete_all();
        return null ;
    }



    public Bonlivraison get(String id){

        try{
           return  daoBonLivraison.get(id) ;
        }
        catch (Exception e){
            Log.d("Assert"," Dao Bon de livraison  "+e.toString());
        }
        return null;
    }

    public List<Bonlivraison> getArrayListBonLivraison(){

        try{

            return  daoBonLivraison.select_bonlivraison() ;
        }
        catch (Exception e){
            Log.d("Assert"," Dao Bon de livraison  "+e.toString());
        }
        return null;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<Bonlivraison> mylist =  daoBonLivraison.select_bonlivraison() ;
            instance.setBonlivraisonArrayList(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao  Bon de livraison  "+e.toString());
        }
    }

    public List<String> getDistinctDates(){

        try{

            return   daoBonLivraison.select_distinct_bonlivraison();

        }
        catch (Exception e){
            Log.d("Assert","DaoBonlivraison.getDistinctDatess : "+e.toString());
            return null;
        }
    }

    public List<Bonlivraison> getBonlivraisonSynchro(){
        try{
            return  daoBonLivraison.select_bonlivraison_bysend() ;

        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
        }
        return  null;
    }

    public Bonlivraison getLast(){
        try{
            return daoBonLivraison.getLast() ;
            }
        catch (Exception e){
            return null;
        }
    }

    public LigneBonlivraison[] getByBonlivraisonId(String bonlivraisonId){
        try{
            int k = 0 ;
            LigneBonlivraison ligneBonlivraison=null;
            LigneBonlivraison[] ligneBons = null ;

            List<LigneBonlivraison> mylist_ligne_commande =
                    daoLigneBonLivraison.select_byid_BonLivraison_LigneBonlivraison(bonlivraisonId);

            ligneBons=new LigneBonlivraison[mylist_ligne_commande.size()];

            for (LigneBonlivraison ligne : mylist_ligne_commande){

                ligneBons[k] = ligne ;
                k++ ;
            }

            return ligneBons;

        }
        catch (Exception e){
            Log.d("Assert","DaoLigneBonlivraisonLoc.getByIdBonlivraison(): "+e.toString());
            return  null;
        }

    }

    public List<Bonlivraison> getByDateBonlivraison(String date){
        try{

         return daoBonLivraison.select_bydate_bonlivraison(date);


        }
        catch (Exception e){
            Log.d("Assert","BonlivraisonRepository.getByDateBonlivraison(): "+e.toString());
            return  null;
        }

    }

    public void delete(Bonlivraison b)
    {
        new DeleteBonLivraisonAsyncTask(daoBonLivraison).execute(b) ;

    }

    public class DeleteBonLivraisonAsyncTask extends AsyncTask<Bonlivraison, Void, Void>
    {
        private DaoBonLivraison daoBonLivraison ;

        private DeleteBonLivraisonAsyncTask(DaoBonLivraison dao)
        {
            this.daoBonLivraison = dao;
        }
        @Override
        protected Void doInBackground(Bonlivraison... b) {

            try{

                daoBonLivraison.delete(b[0]);

            }catch (Exception e){
                Log.d("Assert","Ajout Bon Livraison  ");
            }

            return null ;
        }
    }


    public void setBonLivraison(Bonlivraison bonLivraison){
        this.bonLivraison = bonLivraison;
    }

    public String getId() {
        return bonLivraison.getId();
    }

    public String getUser_Id() {
        return bonLivraison.getUser_id();
    }

    public static final Context getContext(){
        return context;
    }

}
