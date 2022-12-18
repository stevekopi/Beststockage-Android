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

public class ApprovisionnementViewModel extends AndroidViewModel {

    private ApprovisionnementRepository repository ;

    public ApprovisionnementViewModel(@NonNull Application application) {
        super(application);
        repository = new ApprovisionnementRepository(application) ;
    }



    public void ajout_async(Approvisionnement instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }
    public Approvisionnement get(String id)
    {
        return repository.get(id) ;
    }

    public List<Approvisionnement> getList(){
        return repository.getList() ;
    }

    public  List<Approvisionnement> getByPeriode(String periode){
        return repository.getByPeriode(periode) ;
    }

    public void setapprovisionnement(Approvisionnement approvisionnement){
       repository.setapprovisionnement( approvisionnement );
    }

    public Approvisionnement getApprovisionnement(){
        return repository.getApprovisionnement() ;
    }

    public void delete_all(){
        repository.delete_all();
    }

    public  List<Approvisionnement> byLivraison(String Id){
        return  repository.byLivraison(Id) ;
    }
}
