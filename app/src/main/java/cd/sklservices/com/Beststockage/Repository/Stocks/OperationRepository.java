package cd.sklservices.com.Beststockage.Repository.Stocks;

import static java.lang.Thread.*;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Transaction;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Performance;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncOperation;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoOperation;
import cd.sklservices.com.Beststockage.Outils.*;
import cd.sklservices.com.Beststockage.ViewModel.Finances.OperationFinanceViewModel;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class OperationRepository {

    private DaoOperation daoOperation  ;
    private static OperationRepository instance=null;
    private static Operation operation;
    private static Context context;
    private List<Operation> operationArrayListe ;

    public OperationRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoOperation = mydata.daoOperation() ;
    }


    public static final OperationRepository getInstance(Context context){

        if (context!=null){
            OperationRepository.context=context;}

        if (OperationRepository.instance==null){
            OperationRepository.instance=new OperationRepository(context);
        }
        return OperationRepository.instance;
    }

    public void ajout(@NonNull Operation instance)
    {
        daoOperation.insert(instance);
    }

    public void ajout_sync(@NonNull Operation instance)
    {
      try{
          Operation old = get(instance.getId()) ;
          if(old == null)
          {
              daoOperation.insert(instance);
          }
          else
          {
              if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
              {
                  daoOperation.update(instance) ;
              }
          }
      }
      catch (Exception e){
          Log.d("Assert","OperationRepository Ajout_async Error "+e.toString());
      }
    }

    public static final Context getContext(){
        return context;
    }

    @Transaction
    public int add_fast_vente(Application application, @NonNull Operation instance, @NonNull Operation seconde)
    {
        try{
            if(new OperationFinanceViewModel(application).ajout_async(instance.getOperationFinance())==1){

                sleep(500);
                daoOperation.insert(instance);
                sleep(500);
                daoOperation.insert(seconde);
                sleep(500);
                return 1;
            }
            sleep(5000);
            return 0;
        }
        catch (Exception e){
            return 0;
        }

    }

    @Transaction
    public int add_fast_sortie(@NonNull Operation instance, @NonNull Operation seconde)
    {
        try{
            daoOperation.insert(instance);
            sleep(500);
            daoOperation.insert(seconde);
            sleep(500);
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }

    @Transaction
    public int update_fast_vente(Application application, @NonNull Operation instance, @NonNull Operation seconde)
    {
        instance.getOperationFinance().setSync_pos(0);
        instance.getOperationFinance().setMontant(instance.getMontant());
        instance.getOperationFinance().setDate(instance.getDate());
        instance.getOperationFinance().setFournisseur_id(instance.getArticle().getFournisseur_id());
        instance.getOperationFinance().setPos(instance.getPos());
        instance.getOperationFinance().setUpdated_date(instance.getUpdated_date());

        try{
            if(new OperationFinanceViewModel(application).update(instance.getOperationFinance())==1){
              sleep(1000);
              daoOperation.update(instance);
              sleep(1000);
              daoOperation.update(seconde);
              sleep(1000);
              return 1;
            }
            sleep(7000);
            return 0;
        }
        catch (Exception e){
            return 0;
        }


    }

    @Transaction
    public int update_fast_sortie(@NonNull Operation instance, @NonNull Operation seconde)
    {

        try {
            daoOperation.update(instance);
            sleep(500);
            daoOperation.update(seconde);
            sleep(500);
            return 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        }

    }


    public void update(@NonNull Operation instance)
    {
        try{
            daoOperation.update(instance) ;
        }
        catch (Exception e){
            Log.d("Assert","OperationRepository update Error "+e.toString());
        }
    }



    public List<Operation> getOperationArrayListe() {

        return operationArrayListe;
    }

    public void setOperationArrayListe(List<Operation> operationArrayListe) {
        this.operationArrayListe = operationArrayListe;
    }




    public AsyncTask delete_all()
    {
        daoOperation.delete_all();
        return null ;
    }


    public Operation get(String instance){

        try{
            return daoOperation.get(instance);
        }
        catch (Exception e){
            Log.d("Assert"," Dao Operation  "+e.toString());
        }
        return null;
    }


    public void gets(){

        try{
            List<Operation> mylist =  daoOperation.select_operation() ;
            instance.setOperationArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao Operation.getsErreur: "+e.toString());
        }
    }

    public List<Operation> select_operation(){

        try{
            return daoOperation.select_operation() ;
        }
        catch (Exception e){
            Log.d("Assert","Dao Operation.getsErreur: "+e.toString());
        }

        return null ;
    }

    public  List<Operation> getByPeriode(String periode){

        try{
            return daoOperation.select_bydate_operation(periode,MainActivity.getCurrentUser().getAgence_id()) ;
        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
            return null;
        }

    }


    public  AsyncTask delete_data_old_agence(String Id){

        try{
            daoOperation.delete_data_old_agence(Id);
        }
        catch (Exception e){      }
        return null ;
    }

    public List<String> getDistinctPeriode(){
        try{

           return   daoOperation.select_date_from_operation(MainActivity.getCurrentUser().getAgence_id()) ;



        }
        catch (Exception e){
            Log.d("Assert","Erreur: getDistinctPeriode "+e.toString());
            return  null;

        }

    }

    public Performance getPerformancesByDates(String date_debut, String date_fin){

        try{
            Performance vente =  daoOperation.select_bydate_performance_vente(MainActivity.getCurrentUser().getAgence_id(),date_debut, date_fin);
            Performance livraison =  daoOperation.select_bydate_performance_livraison(MainActivity.getCurrentUser().getAgence_id(),date_debut, date_fin);
            Performance instance=new Performance();
            if(vente!=null && livraison!=null){
                instance=vente;
                instance.nombre_livraison=livraison.nombre_livraison;
                instance.quantite_livraison=livraison.quantite_livraison;
            }
            else if(vente!=null){
                instance=vente;
            }
            else if(livraison!=null){
                instance=livraison;
            }

            return instance;

        }
        catch (Exception e){
            Log.d("Assert","DaoOperationLoc.getPerformancesByDates() "+e.toString());
            return null;
        }
    }

    public Performance getPerformances(){

        try{

            Performance vente=  daoOperation.select_performance_vente(MainActivity.getCurrentUser().getAgence_id());
            Performance livraison=  daoOperation.select_performance_livraison(MainActivity.getCurrentUser().getAgence_id());
           Performance instance=new Performance();
            if(vente!=null && livraison!=null){
                instance=vente;
                instance.nombre_livraison=livraison.nombre_livraison;
                instance.quantite_livraison=livraison.quantite_livraison;
            }
            else if(vente!=null){
                instance=vente;
            }
            else if(livraison!=null){
                instance=livraison;
            }

            return instance;
        }
        catch (Exception e){
            Log.d("Assert","DaoOperationLoc.getPerformancesByDates() "+e.toString());
           return null;
        }

    }

    public List<Operation> getOperationSynchro(){
        try{
            return  daoOperation.select_operation_bysend() ;

        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
        }
        return  null;
    }



    public List<Operation> getOperationWhere(String Id_user, String Id_agence1, String Id_agence2, String Id_article,
                                             String Type, int Quantite, int Bonus, String Date, int Sync_pos){

        try{
            return  daoOperation.select_byAll_operation(Id_user, Id_agence1, Id_agence2, Id_article,
                    Type, Quantite, Bonus, Date,  Sync_pos) ;

        }
        catch (Exception e){
            Log.d("Assert"," Dao Stock  "+e.toString());
        }
        return null;
    }


    public int getOperationWhere(String Id_agence){

        try{
            return Integer.valueOf(daoOperation.select_byArticle_operation(Id_agence).size()) ;

        }
        catch (Exception e){
            Log.d("Assert"," Dao Stock  "+e.toString());
        }
        return 0;
    }


    public Boolean test_if_not_sync_exist(String date){
        try{
            Operation instance = daoOperation.select_bydate_not_sync(MainActivity.getCurrentUser().getAgence_id(),date) ;
            return instance==null?false:true;
        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
        }
        return  null;
    }


    public int delete(Operation instance)
    {
        try{
            instance.setSync_pos(3);
            instance.setUpdated_date(MainActivity.getAddingDate());
            instance.setPos(instance.getPos()+1);
            daoOperation.update(instance);
            sleep(500);
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }

    public void setOperation(Operation op){
        OperationRepository.operation = op;
    }

    public Operation getOperation() {
        return OperationRepository.operation ;
    }

    public int count(){
        return daoOperation.count(MainActivity.getCurrentUser().getAgence_id());
    }

    public int count_distinct_date(){
        return daoOperation.count_distinct_date(MainActivity.getCurrentUser().getAgence_id());
    }

    public List<String>  getLoading2(String index) {
        return daoOperation.select_date_from_operation2(MainActivity.getCurrentUser().getAgence_id(),index) ;
    }

    public double  get_amount_by_date_and_agence_id(String date,String agence_id) {
        return daoOperation.get_amount_by_date_and_agence_id (date,agence_id) ;
    }

    public Operation getOtherOperation(Operation instance, String type) {
        return   daoOperation.getOtherOperation(instance.getUser_id(),instance.getArticle_id(),type,instance.getAdding_date());
    }

    public List<Operation> stock_agence(String agenceId) {
        return daoOperation.stock_agence(agenceId) ;
    }

    @Transaction
    public int confirmation(Operation instance){
       try{
           daoOperation.update(instance);
           sleep(500);
           return 1;
       }
       catch (Exception e){
           return 0;
       }

    }

    @Transaction
    public int confirmation(Operation sortie,Operation entree){
        try{
            daoOperation.update(sortie);
            sleep(500);
            new SyncOperation(new OperationRepository(getContext())).sendPost();
            sleep(500);
            daoOperation.update(entree);
            sleep(500);
            new SyncOperation(new OperationRepository(getContext())).sendPost();
            sleep(500);
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }

    public int quantite_stock(String agence_id, String article_id)
    {
        try{
            int qa=daoOperation.quantite_add(agence_id, article_id);
            int ql=daoOperation.quantite_less(agence_id, article_id);
            return qa - ql;
        }
        catch (Exception e){
            return 0;
        }
    }

}
