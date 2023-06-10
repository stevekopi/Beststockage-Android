package cd.sklservices.com.Beststockage.ViewModel.Parametres;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Repository.Parametres.ProduitFactureRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class ProduitFactureViewModel extends AndroidViewModel {
    private ProduitFactureRepository repository ;

    public ProduitFactureViewModel(@NonNull Application application) {
        super(application);
        repository = new ProduitFactureRepository(application) ;
    }

    public void ajout_async(ProduitFacture instance){
            instance.setSync_pos(0);
            instance.setUpdated_date(MainActivity.getAddingDate());
            repository.ajout_sync(instance);
        }


    public ProduitFacture get(String id)
    {
        return repository.get(id) ;
    }



    public void delete_all()
    {
        repository.delete_all() ;
    }

    public List<ProduitFacture> loading() {
        List<ProduitFacture> list= repository.getLoading() ;
        List<ProduitFacture> instances=new ArrayList<>();
        for (ProduitFacture a:list){
            instances.add(get(a.getId()));
        }
        return instances;
    }

    public void setInstance(ProduitFacture instance){
        repository.setInstance(instance);
    }

    public List<Devise>getDistinctDevises(){
        List<Devise> instances=new ArrayList<>();
        List<String> rep= repository.getDistinctDevisesId();
        for(int i=0;i<rep.size();i++){
            instances.add(new DeviseViewModel(MainActivity.application).get(rep.get(i)));
        }
        return instances;
    }

    public List<ProduitFacture[]> gets(List<Devise> devises){
        try{
            List<ProduitFacture[]> instances=new ArrayList<ProduitFacture[]>();

            for (int k=0;k<devises.size();k++){

                instances.add(getByDevise(devises.get(k)));
            }

            return instances;

        }
        catch (Exception e){
            Log.d("Assert","ProduitFactureViewModel.gets(): "+e.toString());
            return  null;
        }
    }

    public ProduitFacture[] getByDevise(Devise devise){
        try{
            int k = 0 ;
            List<ProduitFacture> mylist =  repository.getByDevise(devise);

            List<ProduitFacture> instance_1 = new ArrayList<>();
            for (ProduitFacture instance : mylist){
                instance_1.add(instance) ;
            }

            ProduitFacture[] outputInstance = new ProduitFacture[instance_1.size()] ;
            for(ProduitFacture obj:instance_1)
            {
                outputInstance[k] = get(obj.getId()) ;
                k++ ;
            }

            return outputInstance;

        }
        catch (Exception e){
            Log.d("Assert","ProduitFactureViewModel.getByDevise(): "+e.toString());
            return  null;
        }
    }



}
