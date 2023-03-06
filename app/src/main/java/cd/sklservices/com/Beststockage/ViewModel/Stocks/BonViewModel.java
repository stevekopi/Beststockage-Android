package cd.sklservices.com.Beststockage.ViewModel.Stocks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Stocks.Bon;
import cd.sklservices.com.Beststockage.Repository.Stocks.BonRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class BonViewModel extends AndroidViewModel {

    private BonRepository repository ;

    public BonViewModel(@NonNull Application application) {
        super(application);
        repository = new BonRepository(application) ;
    }

    public void ajout_async(Bon instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }


    public void gets()
    {
        repository.gets() ;
    }


    public List<Bon> getLoading() {
        return repository.getBonLoading () ;
    }


    public Bon get(String id)
    {
        return repository.get(id) ;
    }

    public String getId()
    {
        return repository.getId() ;
    }


    public String get_type()
    {
        return repository.get_type() ;
    }

    public String get_date()
    {
        return repository.get_date() ;
    }

    public String get_fournisseur_id()
    {
        return repository.get_fournisseur_id() ;
    }

    public void setBon(Bon bon){
        repository.setBon(bon);
    }


    public void delete_all(){
        repository.delete_all() ;
    }

    public void delete_data_old_agence(String id){
        repository.delete_data_old_agence(id);
    }

    public Bon getBon(){
        return repository.getBon() ;
    }

    public List<Bon> getByProprietaireId(String proprietaire_id)
    {
        return repository.getByProprietaireId(proprietaire_id) ;
    }

    public List<Bon> getByPrivee(){
        return repository.getByPrivee() ;
    }



}


