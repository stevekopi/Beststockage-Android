package cd.sklservices.com.Beststockage.ViewModel.Stocks;

import static cd.sklservices.com.Beststockage.ViewModel.TableKeyIncrementorViewModel.keygen;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Repository.Stocks.FactureRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class FactureViewModel extends AndroidViewModel {

    private FactureRepository repository ;
private FragmentActivity fragmentActivity;
    public FactureViewModel(@NonNull Application application) {
        super(application);
        repository = new FactureRepository(application) ;
        this.fragmentActivity=fragmentActivity;
    }

    public FactureViewModel(@NonNull Application application, FragmentActivity fragmentActivity) {
        super(application);
        repository = new FactureRepository(application) ;
        this.fragmentActivity=fragmentActivity;
    }

    public int ajout_async(Facture instance){
        try{
            String id=keygen(fragmentActivity, "","facture");
            String id2=keygen(fragmentActivity, "","facture");
            String agence_id=instance.getAgence().getId();
            String agence2_id=instance.getAgence2().getId();
            instance.setId(id);
            instance.setSecond_id(id2);
            instance.setSync_pos(0);
            instance.setUpdated_date(MainActivity.getAddingDate());
            instance.setPayment_mode_id("00000000000022461000000000000002");
            instance.setMembership(instance.getAgence().getAppartenance());
            String memb2 = instance.getAgence2().getAppartenance();
            String produitId2= instance.getProduit().getSecond_id()!=null ? instance.getProduit().getSecond_id() : instance.getProduit().getId();

            if(repository.add_sync(instance)==1){
                Facture instance2=instance.copie();
                instance2.setId(id2);
                instance2.setSecond_id(id2);
                instance2.setProduit_id(produitId2);
                instance2.setMembership(memb2);
                instance2.setAgence_id(agence2_id);
                instance2.setAgence_2_id(agence_id);
                instance.setSecond(instance2);

                if(repository.add_sync(instance2)==1){
                   return 1;
                }
            }
        }
        catch (Exception e){
            Log.d("Assert","FactureViewModel.ajout_async(): "+e.toString());

        }
        return -1;
    }


    public int manage_add_async(Facture instance){

      if (ajout_async(instance) == 1)
        {
            for(int i=0;i<instance.getLines().size();i++){
                new LigneFactureViewModel(MainActivity.application,fragmentActivity).ajout_async(instance.getLines().get(i));
            }
            return 1;
        }

        return -1;
    }


    public void gets()
    {
        repository.gets() ;
    }


    public List<Facture> getLoading() {
        return repository.getLoading () ;
    }


    public Facture get(String id)
    {
        return repository.get(id,true) ;
    }

    public String getId()
    {
        return repository.getId() ;
    }


    public void delete_all(){
        repository.delete_all() ;
    }

    public void delete_data_old_agence(String id){
   //     repository.delete_data_old_agence(id);
    }

    public List<Facture[]> gets(List<String> date){
        try{
            List<Facture[]> instances=new ArrayList<Facture[]>();

            for (int k=0;k<date.size();k++){

                instances.add(getByDate(date.get(k)));
            }

            return instances;

        }
        catch (Exception e){
            Log.d("Assert","FactureViewModel.gets(): "+e.toString());
            return  null;
        }
    }

    public Facture[] getByDate(String date){
        try{
            int k = 0 ;
            List<Facture> mylist =  repository.getsByDate(date);

            List<Facture> instance_1 = new ArrayList<>();
            for (Facture instance : mylist){
                instance_1.add(instance) ;
            }

            Facture[] bons2 = new Facture[instance_1.size()] ;
            for(Facture obj:instance_1)
            {
                bons2[k] = get(obj.getId()) ;
                k++ ;
            }

            return bons2;

        }
        catch (Exception e){
            Log.d("Assert","FactureViewModel.getsByDate(): "+e.toString());
            return  null;
        }
    }

    public List<String> getDistinctDates(){
        return repository.getDistinctDates() ;
    }

}


