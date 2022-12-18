package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Repository.*;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class LigneBonLivraisonViewModel extends AndroidViewModel {

    private LigneBonLivraisonRepository repository ;

    public LigneBonLivraisonViewModel(@NonNull Application application) {
        super(application);
        repository = new LigneBonLivraisonRepository(application) ;
    }

    public void ajout_async(LigneBonlivraison instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }


    public void delete_all(){
        repository.delete_all();
    }


    public List<LigneBonlivraison> getArraylistLigneBonLivraison(){
        return repository.getArraylistLigneBonLivraison() ;
    }

    public void setLigneBonLivraison(LigneBonlivraison ligneB){
        repository.setLigneBonLivraison(ligneB);
    }

    public LigneBonlivraison getLigneBonLivraison(){
        return repository.getLigneBonLivraison() ;
    }

    public void update(LigneBonlivraison l)
    {
        repository.update(l);
    }

}
