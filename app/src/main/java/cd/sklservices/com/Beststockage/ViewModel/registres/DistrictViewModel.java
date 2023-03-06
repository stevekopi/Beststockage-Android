package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.District;
import cd.sklservices.com.Beststockage.Repository.Registres.DistrictRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class DistrictViewModel extends AndroidViewModel {

    private DistrictRepository repository ;

    public DistrictViewModel(@NonNull Application application) {
        super(application);
        repository = new DistrictRepository(application) ;
    }

    public void ajout_async(District instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public District get(String id){

        return repository.get(id) ;
    }

    public List<District> getDistrictArrayListe() {
        return repository.getDistrictArrayListe() ;
    }

    public List<District> getByTown(String Id) {
        return repository.getByTown(Id) ;
    }

        public District find(String Id_town, String Value)
    {
        return repository.find(Id_town, Value) ;
    }

    }
