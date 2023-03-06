package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Address;
import cd.sklservices.com.Beststockage.Repository.Registres.AddressRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class AdresseViewModel extends AndroidViewModel {

    private AddressRepository repository ;

    public AdresseViewModel(@NonNull Application application) {
        super(application);
        repository = new AddressRepository(application) ;

    }

    public void ajout(Address instance) {
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public void ajout_sync(Address adresse) {
        repository.ajout_sync(adresse);
    }

    public void delete_all() {
        repository.delete_all() ;
    }

    public void gets(){
        repository.gets();
    }

    public Address get(String id,boolean isComplete)
    {
        return repository.get(id,isComplete) ;
    }


    public List<Address> getAdresseSynchro()  {

        return repository.getAdresseSynchro() ;
    }

}

