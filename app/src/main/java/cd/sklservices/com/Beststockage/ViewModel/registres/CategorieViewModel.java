package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Categorie;
import cd.sklservices.com.Beststockage.Repository.Registres.CategorieRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class CategorieViewModel extends AndroidViewModel {
    private CategorieRepository repository ;

    public CategorieViewModel(@NonNull Application application) {
        super(application);
        repository = new CategorieRepository(application) ;
    }

    public void ajout_async(Categorie instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);}



    public Categorie get(String id)
    {
        return repository.get(id) ;
    }

    public void delete_all()
    {
        repository.delete_all() ;
    }
}
