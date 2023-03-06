package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Street;
import cd.sklservices.com.Beststockage.Repository.Registres.StreetRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class StreetViewModel extends AndroidViewModel {

    private StreetRepository repository ;

    public StreetViewModel(@NonNull Application application) {
        super(application);
        repository = new StreetRepository(application) ;
    }

    public void ajout_async(Street instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public Street get(String id){
        return repository.get(id) ;
    }

    public Street find(String Id_quarter, String Value)
    {
        return repository.find(Id_quarter, Value) ;
    }

    }
