package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Controle;
import cd.sklservices.com.Beststockage.Repository.ControleRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class ControleViewModel extends AndroidViewModel {
    private ControleRepository repository ;

    public ControleViewModel(@NonNull Application application) {
        super(application);
        repository = new ControleRepository(application) ;
    }

    public int ajout_async(Controle instance){
       try{
           instance.setSync_pos(0);
           instance.setUpdated_date(MainActivity.getAddingDate());
           repository.ajout_sync(instance);
           return 1;
       }
       catch (Exception e){

       }
       return 0;
    }

    public Controle get_by_date_and_agence_id(String date){
        try{
          return   repository.get_by_date_and_agence_id(date);
        }
        catch (Exception e){

        }
        return null;
    }




    public Controle get(String id)
    {
        return repository.get(id) ;
    }

    public void delete_all()
    {
        repository.delete_all() ;
    }
}
