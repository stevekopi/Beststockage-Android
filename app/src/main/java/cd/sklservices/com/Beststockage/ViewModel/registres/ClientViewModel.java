package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Client;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Repository.Registres.ClientRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class ClientViewModel extends AndroidViewModel {

    private ClientRepository repository ;

    public ClientViewModel(@NonNull Application application) {
        super(application);
        repository = new ClientRepository(application) ;
    }

    public List<Identity> getLoading2(String value ) {
        return repository.getClientLoading2 (value) ;
    }

    public Client get(String id, boolean withHuman)
    {
        return repository.get(id,withHuman) ;
    }

    public int count( ) {
        return repository.count () ;
    }

    public List<Identity> getLoading() {
        return repository.getClientLoading () ;
    }

}


