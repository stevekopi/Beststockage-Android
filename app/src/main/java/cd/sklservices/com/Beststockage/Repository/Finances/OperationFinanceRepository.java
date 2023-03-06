package cd.sklservices.com.Beststockage.Repository.Finances;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Finances.OperationFinance;
import cd.sklservices.com.Beststockage.Dao.Finances.DaoOperationFinance;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;
import cd.sklservices.com.Beststockage.Repository.Parametres.DeviseRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.AgenceRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.FournisseurRepository;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

public class OperationFinanceRepository {

    private DaoOperationFinance operation_financedao ;

    private static OperationFinanceRepository instance = null;
    private static Context context;
    private List<OperationFinance> operation_financeArrayListe ;

    public OperationFinanceRepository(Context application)
    {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        operation_financedao = mydata.daoOperationFinance() ;
    }

    public static final OperationFinanceRepository getInstance(Application context){

        if (context!=null){
            OperationFinanceRepository.context=context;}

        if (OperationFinanceRepository.instance==null){
            OperationFinanceRepository.instance=new OperationFinanceRepository(context);
        }
        return OperationFinanceRepository.instance;
    }

    public int count(){
        return operation_financedao.count(MainActivity.getCurrentUser().getAgence_id());
    }

    public int count_distinct_date(){
        return operation_financedao.count_distinct_date(MainActivity.getCurrentUser().getAgence_id());
    }


    public void ajout_sync(OperationFinance instance)
    {
        try{
            OperationFinance old = get(instance.getId(),false,false) ;
            if(old == null)
            {
                operation_financedao.insert(instance);
            }
            else
            {
                if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
                {
                    operation_financedao.update(instance) ;
                }
            }
        }
        catch (Exception e){
            Log.d("Assert","OperationFinanceRepository "+e.toString());
        }
    }


    public void update(OperationFinance instance)
    {
        operation_financedao.update(instance) ;
    }



    public  void delete_data_old_agence(String Id){

        try{
            operation_financedao.delete_data_old_agence(Id);
        }
        catch (Exception e){      }

    }



    public  int confirmation_rapport_journalier(String date){

        try{
            operation_financedao.confirmation_rapport_journalier(MainActivity.getCurrentUser().getAgence_id(),date,MainActivity.getAddingDate());
            return 1;
        }
        catch (Exception e){      }
        return 0;
    }




    public OperationFinance get(String id,boolean withAgence,boolean withFournisseur){
        OperationFinance instance=null;
        try {
            instance= operation_financedao.get(id);
            if(withFournisseur)
                instance.setFournisseur(new FournisseurRepository(FournisseurRepository.getContext()).get(instance.getFournisseur_id(),true));
            if(withAgence)
                instance.setAgence(new AgenceRepository(AgenceRepository.getContext()).get(instance.getAgence_id(),false,false));
        }
        catch (Exception e){
            Log.d("Assert","DaoAdresseLoc.get "+e.toString());
        }

        return instance;
    }

    public List<OperationFinance> getByAgence(String id){

        try {
            return operation_financedao.select_byid_agence(id);
        }
        catch (Exception e){
            Log.d("Assert","DaoAdresseLoc.get "+e.toString());
        }

        return null;
    }


    public List<OperationFinance> get_for_post_sync(){

        try{
            return  operation_financedao.get_for_post_sync();

        }
        catch (Exception e){
            Log.d("Assert"," DaoFournisseur  .get() "+e.toString());
        }
        return null;
    }



    public AsyncTask delete_all()
    {
        operation_financedao.delete_all();
        return null ;
    }



    public ArrayList<String> getDistinctPeriode(){
        try{
            ArrayList periodeArrayListe=new ArrayList();

            List<String> mylist =  operation_financedao.select_date_from_operation_finance() ;

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

    public  List<OperationFinance> getByPeriode(String periode){

        try{
            String agence_id = MainActivity.getCurrentUser().getAgence_id() ;
            return operation_financedao.select_bydate_operation_finance(agence_id, periode) ;
        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
            return null;
        }

    }


    public Boolean test_if_not_sync_exist(String date){
        try{
            OperationFinance instance = operation_financedao.select_bydate_not_sync(MainActivity.getCurrentUser().getAgence_id(),date) ;
            return instance==null?false:true;
        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
        }
        return  null;
    }

    public List<OperationFinance> get(){

        try{

            return  operation_financedao.select_operation_finance() ;
        }
        catch (Exception e){
            Log.d("Assert"," Dao Bon de livraison  "+e.toString());
        }
        return null;
    }

    public void delete(OperationFinance instance)
    {
        instance.setSync_pos(3);
        instance.setUpdated_date(MainActivity.getAddingDate());
        instance.setPos(instance.getPos()+1);
        operation_financedao.update(instance);
    }

    public List<String>  getLoading2(String index) {
        return operation_financedao.select_date_from_operation_finance2(MainActivity.getCurrentUser().getAgence_id(),index) ;
    }


    public static final Context getContext(){
        return context;
    }

    public double get_entrees_amount_by_date_and_agence_id(String date, String agence_id) {
        return operation_financedao.get_entrees_amount_by_date_and_agence_id(date,agence_id) ;
    }

    public double  get_sorties_amount_by_date_and_agence_id(String date,String agence_id) {
        return operation_financedao.get_sorties_amount_by_date_and_agence_id(date,agence_id) ;
    }

    public double get_all_entrees_amount_by_date_and_agence_id(String date, String agence_id) {
        return operation_financedao.get_all_entrees_amount_by_date_and_agence_id(date,agence_id) ;
    }

    public double  get_all_sorties_amount_by_date_and_agence_id(String date,String agence_id) {
        return operation_financedao.get_all_sorties_amount_by_date_and_agence_id(date,agence_id) ;
    }
}
