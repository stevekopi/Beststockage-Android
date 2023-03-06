package cd.sklservices.com.Beststockage.ViewModel.Stocks;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.Repository.Stocks.LigneCommandeRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class LigneCommandeViewModel extends AndroidViewModel {

    private LigneCommandeRepository repository ;

    public LigneCommandeViewModel(@NonNull Application application) {
        super(application);
        repository = new LigneCommandeRepository(application) ;
    }

    public void ajout_async(LigneCommande instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public void delete_all()
    {
        repository.delete_all() ;
    }
    public List<LigneCommande> ligne_commande_from_commande(String CommandeId)
    {
        return repository.ligne_commande_from_commande(CommandeId) ;
    }

    public void setLigneCommande(LigneCommande ligneC)
    {
        repository.setLigneCommande(ligneC);
    }

    public LigneCommande getLigneCommande(){
        return repository.getLigneCommande() ;
    }

    public List<LigneCommande> getList(){
        return repository.getList() ;
    }

    public void update2(LigneCommande l)
    {
        repository.update2(l);
    }

    public List<LigneCommande> ligne_commande_from_commande2(String CommandeId){
        return repository.ligne_commande_from_commande2(CommandeId) ;
    }

    public LigneCommande get(String id){
        return repository.get(id) ;
    }

    public double get_montant_commande(String commande_id){
        return repository.get_montant_commande(commande_id);
    }
}
