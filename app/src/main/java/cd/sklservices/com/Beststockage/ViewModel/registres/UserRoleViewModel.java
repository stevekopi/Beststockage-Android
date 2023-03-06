package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.UserRole;
import cd.sklservices.com.Beststockage.Repository.Registres.UserRoleRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class UserRoleViewModel extends AndroidViewModel {
    private UserRoleRepository repository ;

    public UserRoleViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRoleRepository(application) ;
    }

    public void ajout_async(UserRole instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public UserRole get(String id)
    {
        return repository.get(id) ;
    }

    public void delete_all()
    {
        repository.delete_all() ;
    }
}
