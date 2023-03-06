package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Repository.Registres.IdentityRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class IdentityViewModel extends AndroidViewModel {

    private IdentityRepository repository ;

    public IdentityViewModel(@NonNull Application application) {
        super(application);
            repository = new IdentityRepository(application) ;
    }

    public List<Identity> getAgencesArrayListe() {
        return repository.gets() ;
    }

        public void ajout(Identity instance){
            instance.setSync_pos(0);
            instance.setUpdated_date(MainActivity.getAddingDate());
            repository.ajout_async(instance);
        }


    public Identity get(String id,boolean withAddress)
    {
        return repository.get(id,withAddress) ;
    }

    public void setIdentity(Identity identity){
        repository.setIdentity(identity);
    }

    public String select_name_by_agence_id(String AgenceId)  {

        return repository.select_name_by_agence_id(AgenceId) ;
    }

    public Identity getIdentity(){
        return repository.getIdentity() ;
    }

}


