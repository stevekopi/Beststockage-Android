package cd.sklservices.com.Beststockage.ViewModel.Stocks;

import static java.lang.Thread.sleep;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import androidx.room.Transaction;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Finances.OperationFinance;
import cd.sklservices.com.Beststockage.Classes.Registres.Performance;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncOperation;
import cd.sklservices.com.Beststockage.Repository.Stocks.OperationRepository;
import cd.sklservices.com.Beststockage.ViewModel.Finances.OperationFinanceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class OperationViewModel extends AndroidViewModel {

    private OperationRepository repository ;

    public OperationViewModel(@NonNull Application application) {
        super(application);
        repository = new OperationRepository(application) ;
    }

    public int ajout(Operation instance){

        try{
            repository.ajout(instance);
            sleep(500);
            return 1;
        }
        catch (Exception e){
            Log.d("Assert","OperationViewModel Ajout_async Error "+e.toString());
            return 0;
        }


    }

    public int ajout(Operation instance, Operation seconde){

      try{
          if(instance.getType().contains("Vente")){

             return repository.add_fast_vente(getApplication(),instance,seconde);
          }
          else{
             return repository.add_fast_sortie(instance,seconde);
          }
      }
      catch (Exception e){
          Log.d("Assert","OperationViewModel Ajout_async Error "+e.toString());
      }
      return 0;

    }

    public int update(Operation instance,Operation seconde){

        try{
            if(instance.getType().contains("Vente")){

                return repository.update_fast_vente(getApplication(),instance,seconde);
            }
            else{
                return repository.update_fast_sortie(instance,seconde);
            }
        }
        catch (Exception e){
            Log.d("Assert","OperationViewModel update Error "+e.toString());
        }
        return 0;

    }

    public int update(Operation instance){

        try{
            if(instance.getType().contains("Vente")){
                OperationFinance of=new OperationFinance(instance.getOperation_finance_id(),instance.getAgence_1_id(),
                        instance.getArticle().getFournisseur_id(), MainActivity.getDefaultDevise().getId(),MainActivity.getCurrentUser().getAgence().getType(),instance.getDate(),"Entree",
                        instance.getMontant(),instance.getType()+" : "+instance.getObservation(),
                        "Auto",0,0,0,instance.getUser_id(),
                        instance.getAgence_1_id(),instance.getChecking_agence_id(),instance.getAdding_date(),
                        instance.getUpdated_date(),0,instance.getPos());
                instance.setOperationFinance(of);
                if(new OperationFinanceViewModel(getApplication()).update(of)==1)
                {
                    sleep(500);
                    repository.update(instance);
                    sleep(500);
                    new SyncOperation(new OperationRepository(getApplication().getApplicationContext())).envoi();
                    sleep(500);
                    return 1;
                }
            }
            else{
                repository.update(instance);
                sleep(500);
                return 1;
            }
        }
        catch (Exception e){
            Log.d("Assert","OperationViewModel Ajout_async Error "+e.toString());
        }
        return 0;

    }

    public void delete_all(){
        repository.delete_all();
    }

    public void delete_data_old_agence(String id){
        repository.delete_data_old_agence(id);
    }

    public Operation get(String Operation_id,boolean withAgence,boolean withArticle)
    {
         Operation instance=repository.get(Operation_id);
         if(withAgence)
             instance.setAgenceBeneficiaire(new AgenceViewModel(getApplication()).get(instance.getAgence_2_id(),false,true));
         if(withArticle)
             instance.setArticle(new ArticleViewModel(getApplication()).get(instance.getArticle_id(),false,true));
        return instance;
    }

    public List<String> getDistinctPeriode(){
        return repository.getDistinctPeriode() ;
    }

    public List<Operation> getByPeriode(String periode)
    {
        return repository.getByPeriode(periode) ;
    }

    public Performance getPerformancesByDates(String debut, String fin){
        return repository.getPerformancesByDates(debut, fin) ;
    }

    public Performance getPerformances(){
        return repository.getPerformances() ;
    }

    public List<Operation> select_operation(){
        return repository.select_operation() ;
    }

    public void setOperation(Operation op){
        repository.setOperation(op);
    }

    public Operation getOperation()
    {
        return repository.getOperation() ;
    }


    public List<Operation> getOperationWhere(String Id_user, String Id_agence1, String Id_agence2, String Id_article,
                                       String Type, int Quantite, int Bonus, String Date, int Sync_pos)
    {
        return repository.getOperationWhere(Id_user, Id_agence1, Id_agence2, Id_article,
                Type, Quantite, Bonus, Date, Sync_pos) ;
    }

    public Boolean test_if_operationNotSync(String date){
        return repository.test_if_not_sync_exist(date) ;
    }

    public int getOperationWhere(String Id_agence){
        return repository.getOperationWhere(Id_agence) ;
    }

    public List<Operation> select_byAgence_operation(String Id_agence){
        return repository.select_byAgence_operation(Id_agence) ;
    }



    public int count(){
        return repository.count();
    }

    public Double get_amount_by_date_and_agence_id(String date,String agence_id){
        return repository.get_amount_by_date_and_agence_id(date,agence_id);
    }


    public int count_distinct_date(){
        return repository.count_distinct_date();
    }

    public void getLoading2(String position,List<String> obs) {
        List<String> temp= repository.getLoading2(position) ;
        for (String instance:temp){
            obs.add(instance);
        }
    }

    public int delete(Operation instance){
        try{
            if(MainActivity.getCurrentUser().getId().equals(instance.getUser_id())){
                OperationFinance operationFinance=new OperationFinanceViewModel(getApplication()).get(instance.getOperation_finance_id(),false,false);
                if(new OperationFinanceViewModel(getApplication()).delete(operationFinance)==1){
                    new OperationFinanceViewModel(getApplication()).delete(operationFinance); //On force encore la MAJ de la suppression
                    sleep(1000);
                   if( repository.delete(instance)==1){
                       sleep(1000);
                       return 1;
                   }

                }
                sleep(5000);
                return 0;
            }
            else
            {
                User user=new UserViewModel(getApplication()).get(instance.getUser_id(),true,true);
                Toast.makeText(getApplication(), "Vous n'êtes pas autorisé à supprimer cet enregistrement, car il a été enregistré par "+user.getHuman().getIdentity().getName()+" de l'agence "+user.getAgence().getType()+" "+user.getAgence().getDenomination(), Toast.LENGTH_LONG).show();
                return 0;
            }
        }
        catch (Exception e){
            Log.d("Assert","DepenseViewModel Delete Error : "+e.toString());
            return 0;
        }

    }

    public Operation getOtherOperation(Operation instance)
    {
        String type="";
        try
        {
           if(instance.getType().equals("Sortie")){
               type="Entree";
           }
           else if(instance.getType().contains("Vente")){
               type="Achat";
           }
           else if(instance.getType().equals("Entree")){
               type="Sortie";
           }

        }
        catch (Exception ex)
        {

        }

        return   repository.getOtherOperation(instance,type);
    }

    @Transaction
    public int update_like_delete_transact(Operation instance){
       try{
           Operation second=getOtherOperation(instance);
           if( delete(second)==1)
           {
               sleep(1000);
               if(delete(instance)==1){
                   sleep(1000);
                   return 1;
               }
           }
           else{
               return 0;
           }
           sleep(10000);
           return 0;
       }
       catch (Exception e){
           return 0;
       }
    }

    public int update_confirmation_transact(Operation entree){
        try{

            entree.setIs_confirmed(1);
            entree.setUpdated_date(MainActivity.getAddingDate());
            entree.setPos(entree.getPos()+1);
            entree.setSync_pos(0);

            if(entree.getType().equals("Entree")){
                Operation sortie=getOtherOperation(entree);

                if (sortie==null)
                    return 0;
                sortie.setIs_confirmed(1);
                sortie.setUpdated_date(MainActivity.getAddingDate());
                sortie.setPos(entree.getPos());
                sortie.setSync_pos(0);

                return repository.confirmation(sortie,entree);
            }else{
               return repository.confirmation(entree);
            }

        }
        catch (Exception e){

        }
        return 0;
    }

}
