package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Delegue;
import cd.sklservices.com.Beststockage.Repository.Registres.DelegueRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class DelegueViewModel extends AndroidViewModel {

    private DelegueRepository repository ;

    public DelegueViewModel(@NonNull Application application) {
        super(application);
        repository = new DelegueRepository(application) ;
    }

    public void ajout_async(Delegue instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public List<Delegue> getDelegueArrayListe() {
        return repository.getDelegueArrayListe() ;
    }

    public Delegue get(String id)
    {
        return  repository.get(id) ;
    }

    }
