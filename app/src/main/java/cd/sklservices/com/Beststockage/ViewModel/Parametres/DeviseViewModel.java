package cd.sklservices.com.Beststockage.ViewModel.Parametres;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Repository.Parametres.DeviseRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class DeviseViewModel extends AndroidViewModel {
    private DeviseRepository repository ;

    public DeviseViewModel(@NonNull Application application) {
        super(application);
        repository = new DeviseRepository(application) ;
    }

    public void ajout_async(Devise instance){
            instance.setSync_pos(0);
            instance.setUpdated_date(MainActivity.getAddingDate());
            repository.ajout_sync(instance);
        }


    public Devise get(String id)
    {
        return repository.get(id) ;
    }

    public Devise getDefault()
    {
        return repository.getDefault() ;
    }

    public Devise getLocal()
    {
        return repository.getLocal() ;
    }

    public Devise getDefaultConverter()
    {
        return repository.getDefaultConverter() ;
    }

    public void delete_all()
    {
        repository.delete_all() ;
    }

    public void setDevise(Devise instance){
        repository.setDevise(instance);
    }

    public Devise getDevise(){
        return repository.getDevise();
    }

    public int count(){
        return repository.count();
    }

    public List<Devise> loading(){
        return repository.loading();
    }
}
