package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Town;
import cd.sklservices.com.Beststockage.Repository.Registres.TownRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class TownViewModel extends AndroidViewModel {

    private TownRepository repository ;

    public TownViewModel(@NonNull Application application) {
        super(application);
        repository = new TownRepository(application) ;
    }

    public void ajout_async(Town instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }
    public Town get(String id){

        return repository.get(id) ;
    }

    public List<Town> getTownArrayListe()
    {
        return repository.getTownArrayListe() ;
    }
    public Town find(String Value)
    {
        return repository.find(Value) ;
    }

    }
