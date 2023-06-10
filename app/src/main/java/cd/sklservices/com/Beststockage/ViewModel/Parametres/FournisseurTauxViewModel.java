package cd.sklservices.com.Beststockage.ViewModel.Parametres;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.FournisseurTaux;
import cd.sklservices.com.Beststockage.Repository.Parametres.FournisseurTauxRepository;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class FournisseurTauxViewModel extends AndroidViewModel {
    private FournisseurTauxRepository repository ;

    public FournisseurTauxViewModel(@NonNull Application application) {
        super(application);
        repository = new FournisseurTauxRepository(application) ;
    }

    public void ajout_async(FournisseurTaux instance){
            instance.setSync_pos(0);
            instance.setUpdated_date(MainActivity.getAddingDate());
            repository.ajout_sync(instance);
        }


    public FournisseurTaux get(String id,boolean withFournisseur,boolean withDevises)
    {
        FournisseurTaux instance=repository.get(id);
        if (withFournisseur){
            instance.setFournisseur(new FournisseurViewModel(MainActivity.application).get(instance.getFournisseur_id(),true));
        }
        if (withDevises){
            instance.setFrom(new DeviseViewModel(MainActivity.application).get(instance.getFrom_id()));
            instance.setTo(new DeviseViewModel(MainActivity.application).get(instance.getTo_id()));
        }
        return instance ;
    }



    public void delete_all()
    {
        repository.delete_all() ;
    }

    public List<FournisseurTaux> loading() {
        List<FournisseurTaux> list= repository.getLoading() ;
        List<FournisseurTaux> instances=new ArrayList<>();
        for (FournisseurTaux a:list){
            instances.add(get(a.getId(),true,true));
        }
        return instances;
    }

    public int count(){
        return repository.count();
    }

}
