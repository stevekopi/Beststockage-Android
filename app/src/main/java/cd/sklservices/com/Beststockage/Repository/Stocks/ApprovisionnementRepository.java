package cd.sklservices.com.Beststockage.Repository.Stocks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.Approvisionnement;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoApprovisionnement;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLivraison;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class ApprovisionnementRepository {

    private DaoApprovisionnement daoapprovisionnement ;
    private DaoLivraison daolivraison ;
    private static ApprovisionnementRepository instance=null;
    private static Approvisionnement approvisionnement;
    private static Context context;
    private List<Approvisionnement> approvisionnementArrayListe ;

    public ApprovisionnementRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoapprovisionnement = mydata.daoApprovisionnement() ;
        daolivraison = mydata.daoLivraison() ;
    }

    public static final ApprovisionnementRepository getInstance(Context context){

        if (context!=null){
            ApprovisionnementRepository.context=context;}

        if (ApprovisionnementRepository.instance==null){
            ApprovisionnementRepository.instance=new ApprovisionnementRepository(context);
        }
        return ApprovisionnementRepository.instance;
    }

    public void setApprovisionnementArrayList(List<Approvisionnement> approvisionnementArrayList) {
        this.approvisionnementArrayListe = approvisionnementArrayList;
    }

    public AsyncTask ajout_sync(Approvisionnement instance)
    {
        Approvisionnement old = get(instance.getId()) ;
        if(old == null)
        {
            daoapprovisionnement.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoapprovisionnement.update(instance) ;
            }
        }
        return null ;
    }

    public List<Approvisionnement> getApprovisionnementSynchro(){
        try{

            return  daoapprovisionnement.select_approvisionnement_bysend();
        }
        catch (Exception e){
            Log.d("Assert","DaoCommandeLoc().getDistinct : "+e.toString());
            return  null;

        }
    }





    public void gets(){

        try{
            List<Approvisionnement> mylist =  daoapprovisionnement.select_approvisionnement() ;
            instance.setApprovisionnementArrayList(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao Approvisionement.getsErreur: "+e.toString());
        }
    }

    public List<Approvisionnement> getList(){

        try{
            return  daoapprovisionnement.select_approvisionnement() ;

        }
        catch (Exception e){
            Log.d("Assert","Dao Approvisionement.getsErreur: "+e.toString());
        }
        return null ;
    }

    public Approvisionnement get(String appprovisionnement_id){

        try{
            Approvisionnement approvisionnement=null;
            List<Approvisionnement> mylist =  daoapprovisionnement.select_byid_approvisionnement(appprovisionnement_id) ;

            for (Approvisionnement appro : mylist){
                    return appro ;
                }

        }
        catch (Exception e){
            Log.d("Assert","Approvisionnement  Erreur: "+e.toString());
        }

        return null;
    }

    public List<Approvisionnement> getList(String appprovisionnement_id){

        try{
            return  daoapprovisionnement.select_byid_approvisionnement(appprovisionnement_id) ;
        }
        catch (Exception e){
            Log.d("Assert","Approvisionnement  Erreur: "+e.toString());
        }

        return null;
    }

    public AsyncTask delete_all()
    {
        daoapprovisionnement.delete_all();
        return null ;
    }

    public void delete(Approvisionnement approvisionnement)
    {
        new DeleteApprovisionnementAsyncTask(daoapprovisionnement).execute(approvisionnement) ;

    }

    public class DeleteApprovisionnementAsyncTask extends AsyncTask<Approvisionnement, Void, Void>
    {
        private DaoApprovisionnement daoApprovisionnement ;

        private DeleteApprovisionnementAsyncTask(DaoApprovisionnement daoApprovisionnement)
        {
            this.daoApprovisionnement = daoApprovisionnement;
        }
        @Override
        protected Void doInBackground(Approvisionnement... approvisionnement) {

            try{

                daoApprovisionnement.delete(approvisionnement[0]);

            }catch (Exception e){
                Log.d("Assert","Ajout Approvisionnement  ");
            }

            return null ;
        }
    }

    public  List<Approvisionnement> getByPeriode(String periode){

        try{
            return daoapprovisionnement.select_bydate_approvisionnement(periode) ;
        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
            return null;
        }

    }

    public  List<Approvisionnement> byLivraison(String Id){

        try{
            return daoapprovisionnement.byLivraison(Id) ;
        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
            return null;
        }

    }

    public void setapprovisionnement(Approvisionnement approvisionnement){
        ApprovisionnementRepository.approvisionnement = approvisionnement;
    }

    public Approvisionnement getApprovisionnement(){
        if (approvisionnement ==null){return null;}
        else{return  approvisionnement ;}
    }

    public String getId() {
        return approvisionnement.getId() ;
    }

    public String getLivraison_Id() {
        return approvisionnement.getLivraison_id() ;
    }

    public String getOperation_Id() {
        return approvisionnement.getOperation_id() ;
    }

    public String getAgence_Id() {
        return approvisionnement.getAgence_id() ;
    }

    public String getArticle_Id() {
        return approvisionnement.getArticle_id() ;
    }

    public int getQuantite() {
        return approvisionnement.getQuantite() ;
    }

    public int getBonus() {
        return approvisionnement.getBonus() ;
    }

    public static final Context getContext(){
        return context;
    }


}
