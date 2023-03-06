package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Driver;
import cd.sklservices.com.Beststockage.Repository.Registres.DriverRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class DriverViewModel extends AndroidViewModel {

    private DriverRepository repository ;

    public DriverViewModel(@NonNull Application application) {
        super(application);
        repository = new DriverRepository(application) ;
    }

    public void ajout_async(Driver instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }


    public List<Driver> getDriverArrayListe() {
        return repository.getDriverArrayListe() ;
    }

    public Driver get(String id) {
        return repository.get(id);
    }

    }
