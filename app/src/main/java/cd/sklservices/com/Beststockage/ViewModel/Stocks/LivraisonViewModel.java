package cd.sklservices.com.Beststockage.ViewModel.Stocks;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Stocks.Livraison;
import cd.sklservices.com.Beststockage.Repository.Stocks.LivraisonRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class LivraisonViewModel extends AndroidViewModel {

    private LivraisonRepository repository ;

    public LivraisonViewModel(@NonNull Application application) {
        super(application);
        repository = new LivraisonRepository(application) ;
    }

    public void delete_all(){
        repository.delete_all();
    }

    public void ajout_async(Livraison instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public List<Livraison> getList(){
        return repository.getList() ;
    }

    public ArrayList<String> getDistinctPeriode(){
        return repository.getDistinctPeriode() ;
    }

    public Livraison get(String id){
        return repository.get(id) ;
    }

    public void update2(Livraison l){
        repository.update2(l) ;
    }

    public List<Livraison> getbyId_ligneCommande(String Id){
        return repository.getbyId_ligneCommande(Id) ;
    }


}
