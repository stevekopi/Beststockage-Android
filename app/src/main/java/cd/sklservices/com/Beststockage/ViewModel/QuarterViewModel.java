package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Quarter;
import cd.sklservices.com.Beststockage.Repository.QuarterRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class QuarterViewModel extends AndroidViewModel {

    private QuarterRepository repository ;

    public QuarterViewModel(@NonNull Application application) {
        super(application);
        repository = new QuarterRepository(application) ;
    }

    public void ajout_async(Quarter instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public Quarter get(String id){

        return repository.get(id) ;
    }

    public Quarter find(String Id_township, String Value)
    {
        return repository.find(Id_township, Value) ;
    }


}
