package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Vehicule;
import cd.sklservices.com.Beststockage.Repository.Registres.VehiculeRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class VehiculelViewModel extends AndroidViewModel {

    private VehiculeRepository repository ;

    public VehiculelViewModel(@NonNull Application application) {
        super(application);
        repository = new VehiculeRepository(application) ;
    }

    public void ajout_async(Vehicule instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public Vehicule get(String id){

        return repository.get(id) ;
    }

    public List<Vehicule> getVehiculeArrayListe() {
        return repository.getVehiculeArrayListe() ;
    }

    }
