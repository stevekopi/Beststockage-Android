package cd.sklservices.com.Beststockage.Repository.Stocks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.Livraison;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLivraison;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class LivraisonRepository {

    private DaoLivraison daoLivraison  ;
    private static LivraisonRepository instance=null;
    private static Livraison livraison;
    private static Context context;
    private List<Livraison> livraisonArrayListe ;

    public LivraisonRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoLivraison = mydata.daoLivraison() ;
    }

    public static final LivraisonRepository getInstance(Context context)
    {
        if (context!=null){
            LivraisonRepository.context=context;}

        if (LivraisonRepository.instance==null){
            LivraisonRepository.instance=new LivraisonRepository(context);
        }
        return LivraisonRepository.instance;
    }

    public List<Livraison> getLivraisonArrayListe() {
        return livraisonArrayListe;
    }

    public void setLiraisonArrayListe(List<Livraison> livraisonArrayListe) {
        this.livraisonArrayListe = livraisonArrayListe;
    }

    public void ajout_sync(Livraison instance)
    {
       try{
           Livraison old = get(instance.getId()) ;
           if(old == null)
           {
               daoLivraison.insert(instance);
           }
           else
           {
               if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
               {
                   daoLivraison.update(instance) ;
               }
           }
       }
       catch (Exception e){
           Log.d("Assert","LivraisonRepository Ajout_async().getDistinct : "+e.toString());
       }
    }


    public AsyncTask delete_all()
    {
        daoLivraison.delete_all();
        return null ;
    }

    public Livraison get(String id){

        try{
            return daoLivraison.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," Dao livraison "+e.toString());
        }
        return null;
    }


    public void gets(){

        try{

            instance.setLiraisonArrayListe(daoLivraison.select_livraison());

        }
        catch (Exception e){
            Log.d("Assert","Dao Livraison .getsErreur: "+e.toString());
        }
    }

    public List<Livraison> getList(){

        try{
            return  daoLivraison.select_livraison() ;

        }
        catch (Exception e){
            Log.d("Assert","Dao Livraison .getsErreur: "+e.toString());
        }

        return null ;
    }

    public List<Livraison> getbyId_ligneCommande(String Id){

        try{
            return  daoLivraison.select_byId_ligneCommande(Id) ;

        }
        catch (Exception e){
            Log.d("Assert","Dao Livraison .getsErreur: "+e.toString());
        }

        return null ;
    }


    public List<Livraison> getLivraisonSynchro(){
        try{

            return  daoLivraison.select_livraison_bysend() ;
        }
        catch (Exception e){
            Log.d("Assert","DaoCommandeLoc().getDistinct : "+e.toString());
            return  null;

        }
    }

    public AsyncTask update(Livraison l)
    {
        l.setSync_pos(1);
        new UpdateLivraisonAsyncTask(daoLivraison).execute(l) ;

        return null ;
    }

    public AsyncTask delete2(String Id)
    {
        daoLivraison.delete2(Id);

        return null ;
    }

    public void update2(Livraison l)
    {
        daoLivraison.update(l);
    }


    public class UpdateLivraisonAsyncTask extends AsyncTask<Livraison, Void, Void>
    {
        private DaoLivraison daol ;

        private UpdateLivraisonAsyncTask(DaoLivraison daol)
        {
            this.daol = daol ;
        }
        @Override
        protected Void doInBackground(Livraison... l) {

            try{ daol.update(l[0]); }catch (Exception e){
                Log.d("Assert","Ajout Livraison  ");
            }

            return null ;
        }
    }

    public void delete(Livraison l)
    {
        new DeleteLivraisonAsyncTask(daoLivraison).execute(l) ;
    }

    public class DeleteLivraisonAsyncTask extends AsyncTask<Livraison, Void, Void>
    {
        private DaoLivraison daol ;

        private DeleteLivraisonAsyncTask(DaoLivraison daol)
        {
            this.daol = daol ;
        }
        @Override
        protected Void doInBackground(Livraison... l) {

            try{ daol.delete(l[0]); }catch (Exception e){
                Log.d("Assert","Ajout Livraison  ");
            }

            return null ;
        }
    }

    public ArrayList<String> getDistinctPeriode(){
        try{
            ArrayList periodeArrayListe=new ArrayList();

            List<String> mylist =  daoLivraison.select_date_from_livraison();

            for (String periode : mylist){
                periodeArrayListe.add(periode);
            }

            return periodeArrayListe;

        }
        catch (Exception e){
            Log.d("Assert","Erreur: getDistinctPeriode "+e.toString());
            return  null;

        }

    }







}
