package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Township;
import cd.sklservices.com.Beststockage.Repository.TownshipRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class TownshipViewModel extends AndroidViewModel {

    private TownshipRepository repository ;

    public TownshipViewModel(@NonNull Application application) {
        super(application);
        repository = new TownshipRepository(application) ;
    }

    public void ajout_async(Township instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public Township get(String id){

        return repository.get(id) ;
    }

    public Township find(String Id_district, String Value)
    {
        return repository.find(Id_district, Value) ;
    }

    public List<Township> getByDistrict(String Id) {
        return repository.getByDistrict(Id) ;
    }

    }
