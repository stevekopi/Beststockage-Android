package cd.sklservices.com.Beststockage.ViewModel.Finances;


import static java.lang.Thread.sleep;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Finances.Depense;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Cloud.Finances.SyncDepense;
import cd.sklservices.com.Beststockage.Repository.Finances.DepenseRepository;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class DepenseViewModel extends AndroidViewModel {

    private DepenseRepository repository ;

    public DepenseViewModel(@NonNull Application application) {
        super(application);
        repository = new DepenseRepository(application) ;
    }

    public Depense get(String Id,boolean withOperationFinance){
        return repository.get(Id,withOperationFinance) ;
    }

    public int ajout(Depense instance){
          try{
              if( new OperationFinanceViewModel(getApplication()).ajout_async(instance.getOperationFinance())==1)
              {
                  sleep(500);
                  repository.ajout(instance);
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

    public int update(Depense instance){
        try{
            instance.setSync_pos(0);
            instance.setPos(instance.getPos()+1);
            instance.setUpdated_date(MainActivity.getAddingDate());

             instance.getOperationFinance().setSync_pos(0);
             instance.getOperationFinance().setPos(instance.getPos());
             instance.getOperationFinance().setAdding_date(instance.getAdding_date());
             instance.getOperationFinance().setUpdated_date(instance.getUpdated_date());

            if( new OperationFinanceViewModel(getApplication()).update(instance.getOperationFinance())==1)
            {
                sleep(1000);
                repository.update(instance);
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

    public  List<Depense> getByPeriode(String periode){
        return repository.getByPeriode(periode) ;
    }

    public int count_distinct_date(){
        return repository.count_distinct_date();
    }

    public int count(){
        return repository.count();
    }

    public  void delete_all(){
        repository.delete_all();
    }

    public List<String> getDistinctPeriode(){
        return repository.getDistinctPeriode() ;
    }

    public Boolean test_if_depenseNotSync(String date){
        return repository.test_if_not_sync_exist(date) ;
    }


    public List<Depense> getByAgence(String id){
        return repository.getByAgence(id) ;
    }

    public void delete_data_old_agence(String id){
        repository.delete_data_old_agence(id);
    }

    public List<Depense> get(){
        return repository.get() ;
    }

    public void getLoading2(String position,List<String> obs) {
        List<String> temp= repository.getLoading2(position) ;
        for (String instance:temp){
            obs.add(instance);
        }
    }

    public Double get_amount_by_date_and_agence_id(String date,String agence_id){
        return repository.get_amount_by_date_and_agence_id(date,agence_id);
    }


    public int update_like_delete_transact(Depense instance)
    {
        try{
            if(MainActivity.getCurrentUser().getId().equals(instance.getAdding_user_id())){
                if(new OperationFinanceViewModel(getApplication()).delete(instance.getOperationFinance())==1){
                    new OperationFinanceViewModel(getApplication()).delete(instance.getOperationFinance()); //On force encore la MAJ de la suppression
                    sleep(1000);
                    repository.delete(instance);
                    sleep(1000);
                    new SyncDepense(new DepenseRepository(DepenseRepository.getContext())).sendPost();
                    sleep(1000);
                    return 1;
                }
                sleep(5000);
                return 0;
            }
            else
            {
                User user=new UserViewModel(getApplication()).get(instance.getAdding_user_id(),true,true);
                Toast.makeText(getApplication(), "Vous n'êtes pas autorisé à supprimer cet enregistrement, car il a été enregistré par "+user.getHuman().getIdentity().getName()+" de l'agence "+user.getAgence().getType()+" "+user.getAgence().getDenomination(), Toast.LENGTH_LONG).show();
                return 0;
            }

        }
        catch (Exception e){
            return 0;
        }
        }
    }
