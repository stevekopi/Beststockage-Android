package cd.sklservices.com.Beststockage.ViewModel.Finances;

import static java.lang.Thread.sleep;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Finances.OperationFinance;
import cd.sklservices.com.Beststockage.Cloud.Finances.SyncOperationFinance;
import cd.sklservices.com.Beststockage.Repository.Finances.OperationFinanceRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class OperationFinanceViewModel extends AndroidViewModel {

    private OperationFinanceRepository repository ;

    public OperationFinanceViewModel(@NonNull Application application) {
        super(application);
        repository = new OperationFinanceRepository(application) ;
    }

    public int count(){
        return repository.count();
    }

    public int count_distinct_date(){
        return repository.count_distinct_date();
    }

    public OperationFinance get(String Id,boolean withAgence,boolean withFournisseur){
        return repository.get(Id,withAgence,withFournisseur) ;
    }

    public int ajout_async(OperationFinance instance){
       try{
           if (instance.getId().equals(MainActivity.DefaultFinancialKey)){
               return 1;
           }
           else {
               repository.ajout_sync(instance);
               new SyncOperationFinance(new OperationFinanceRepository(getApplication().getApplicationContext())).envoi();
               return 1;
           }

       }
       catch (Exception e){
           return 0;
       }

    }

    public  int confirmation_rapport_journalier(String date){
            return repository.confirmation_rapport_journalier(date);
    }

    public int update(OperationFinance instance){
        try{
            instance.setAgence_id(MainActivity.getCurrentUser().getAgence_id());
            instance.setIs_caisse_checked(0);
            instance.setIs_stock_checked(0);
            instance.setIs_user_confirmed(0);
            instance.setSync_pos(0);

            repository.update(instance);
            sleep(500);
            new SyncOperationFinance(new OperationFinanceRepository(getApplication().getApplicationContext())).envoi();
            sleep(500);
            return 1;

        }
        catch (Exception e){
            return 0;
        }

    }

    public Boolean test_if_operationNotSync(String date){
        return repository.test_if_not_sync_exist(date) ;
    }

    public  List<OperationFinance> getByPeriode(String periode){
        return repository.getByPeriode(periode) ;
    }

    public  void delete_all(){
        repository.delete_all();
    }

    public void delete_data_old_agence(String id){
        repository.delete_data_old_agence(id);
    }

    public ArrayList<String> getDistinctPeriode(){
        return repository.getDistinctPeriode() ;
    }

    public Boolean test_if_operation_financeNotSync(String date){
        return repository.test_if_not_sync_exist(date) ;
    }

    public List<OperationFinance> getByAgence(String id){
        return repository.getByAgence(id) ;
    }

    public List<OperationFinance> getList(){
        return repository.get() ;
    }

    public int delete(OperationFinance instance){
        try{
           if(instance.getId().equals(MainActivity.DefaultFinancialKey))
           {
               return 1;
           }
           else {
               repository.delete(instance);
               return 1;
           }
        }
        catch (Exception e){

        }
        return 0;
    }

    public void getLoading2(String position,List<String> obs) {
        List<String> temp= repository.getLoading2(position) ;
        for (String instance:temp){
            obs.add(instance);
        }
    }

    public Double get_entrees_amount_by_date_and_agence_id(String date,String agence_id){
        return repository.get_entrees_amount_by_date_and_agence_id(date,agence_id);
    }

    public Double get_sorties_amount_by_date_and_agence_id(String date,String agence_id){
        return repository.get_sorties_amount_by_date_and_agence_id(date,agence_id);
    }

    public Double get_all_entrees_amount_by_date_and_agence_id(String date,String agence_id){
        return repository.get_all_entrees_amount_by_date_and_agence_id(date,agence_id);
    }

    public Double get_all_sorties_amount_by_date_and_agence_id(String date,String agence_id){
        return repository.get_all_sorties_amount_by_date_and_agence_id(date,agence_id);
    }

    public Double get_net_amount_by_date_and_agence_id(String date,String agence_id){
        return get_all_entrees_amount_by_date_and_agence_id(date,agence_id) - get_all_sorties_amount_by_date_and_agence_id(date,agence_id);
    }

}
