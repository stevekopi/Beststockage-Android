package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Convoyeur;
import cd.sklservices.com.Beststockage.Repository.Registres.ConvoyeurRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class ConvoyeurViewModel extends AndroidViewModel {

    private ConvoyeurRepository repository ;

    public ConvoyeurViewModel(@NonNull Application application) {
        super(application);
        repository = new ConvoyeurRepository(application) ;
    }

    public void ajout_async(Convoyeur instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }



    public Convoyeur get(String id)
    {
        return  repository.get(id) ;
    }

    public List<Convoyeur> getConvoyeurArrayListe() {
        return repository.getConvoyeurArrayListe() ;
    }

    }
