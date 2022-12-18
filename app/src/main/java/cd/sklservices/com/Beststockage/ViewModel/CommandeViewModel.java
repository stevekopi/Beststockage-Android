package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Repository.*;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class CommandeViewModel extends AndroidViewModel {

    private CommandeRepository repository ;

    public CommandeViewModel(@NonNull Application application) {
        super(application);
        repository = new CommandeRepository(application) ;
    }

    public void ajout_async(Commande instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }



    public Commande getLast()
    {
        return repository.getLast() ;
    }

    public Commande get(String id,boolean withAmount)
    {
        return repository.get(id,withAmount) ;
    }

    public int  getSommeQuantiteApprovisionner(String IdCommande)
    {
        return repository.getSommeQuantiteApprovisionner(IdCommande) ;
    }

    public int getSommeQuantiteCommander(String IdCommande)
    {
        return repository.getSommeQuantiteCommander(IdCommande) ;
    }

    public List<Commande> getList(){
        return repository.getList() ;
    }

    public List<Commande> getWhereAll(String Id_user, String Id_prorietaire, String Id_fournisseur, String Date_commande,
                                      String Membership, String Adding_agence_id){
        return repository.getWhereAll(Id_user, Id_prorietaire, Id_fournisseur, Date_commande,
                Membership, Adding_agence_id) ;
    }



    public void delete_all()
    {
        repository.delete_all() ;
    }

    public List<Commande> select_commande(){
        return repository.select_commande() ;
    }

    public ArrayList<Commande> getDistinct()
    {
        return repository.getDistinct() ;
    }
}
