package cd.sklservices.com.Beststockage.ViewModel.Stocks;

import static cd.sklservices.com.Beststockage.ViewModel.TableKeyIncrementorViewModel.keygen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Repository.Stocks.LigneFactureRepository;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ProduitFactureViewModel;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class LigneFactureViewModel extends AndroidViewModel {

    private LigneFactureRepository repository ;
    private FragmentActivity fragmentActivity;

    public LigneFactureViewModel(@NonNull Application application) {
        super(application);
        repository = new LigneFactureRepository(application) ;
    }

    public LigneFactureViewModel(@NonNull Application application, FragmentActivity fragmentActivity) {
        super(application);
        repository = new LigneFactureRepository(application) ;
        this.fragmentActivity=fragmentActivity;
    }


    public int ajout_async(LigneFacture instance){
        String id=keygen(fragmentActivity, "","ligne_facture");
        String id2=keygen(fragmentActivity, "","ligne_facture");

        instance.setId(id);
        instance.setSecond_id(id2);
        instance.setFacture_id(instance.getFacture().getId());
        instance.setSync_pos(instance.getFacture().getSync_pos());
        instance.setUpdated_date(instance.getFacture().getUpdated_date());
        instance.setAppartenance(instance.getAppartenance());
        instance.setSens_stock(instance.getFacture().getProduit().getSens_stock());
        instance.setAppartenance(instance.getFacture().getMembership());

        if(repository.add_async(instance)==1){
            LigneFacture instance2=instance.copie();
            instance2.setId(id2);
            instance2.setSecond_id(id2);
            instance2.setFacture_id(instance.getFacture().getSecond_id());
            ProduitFacture produitFacture=new ProduitFactureViewModel(MainActivity.application).get(instance.getFacture().getProduit().getSecond_id());
            String sensStock=produitFacture.getSens_stock();
            instance2.setAppartenance(instance.getFacture().getSecond().getMembership());
            instance2.setSens_stock(sensStock);

            return repository.add_async(instance2);
        }


        return repository.add_async(instance);
    }


    public void delete_all(){
        repository.delete_all();
    }


    public List<LigneFacture> getArraylistLigneFacture(){
        return repository.getLigneArrayListe() ;
    }

    public void setLigneFacture(LigneFacture ligneB){
        repository.setLigneFacture(ligneB);
    }

    public LigneFacture getLigneFacture(){
        return repository.getLigneFacture() ;
    }

    public void update(LigneFacture l)
    {
        repository.update(l);
    }

    public LigneFacture[] getByFactureId(String Id){
        return repository.getByFactureId(Id) ;
    }

    public List<LigneFacture> getListByFactureId(String Id){
        return repository.getListByFactureId(Id) ;
    }

}
