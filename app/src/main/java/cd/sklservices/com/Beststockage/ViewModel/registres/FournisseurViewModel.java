package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Repository.Registres.FournisseurRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class FournisseurViewModel  extends AndroidViewModel {

    private FournisseurRepository repository ;

    public FournisseurViewModel(@NonNull Application application) {
        super(application);
        repository = new FournisseurRepository(application) ;
    }

    public void gets()
    {
        repository.gets() ;
    }

    public void delete_all()
    {
        repository.delete_all() ;
    }

    public List<Fournisseur> getFournisseurArrayListe() {
       if(MainActivity.getCurrentUser()!=null && MainActivity.getCurrentUser().getRole()!=null && MainActivity.getCurrentUser().getRole().getDesignation().toLowerCase(Locale.ROOT).contains("super"))
           return repository.getFournisseurArrayListe();
        List<Fournisseur> instances =new ArrayList<>();
        List<Fournisseur> list= repository.getFournisseurArrayListe() ;
        for (Fournisseur instance:list){
            if(instance.getIdentity().getName().toLowerCase(Locale.ROOT).contains("african food") ||
                    instance.getIdentity().getName().toLowerCase(Locale.ROOT).contains("ibc") ||
                    instance.getIdentity().getName().toLowerCase(Locale.ROOT).contains("amigo foods sarl") ||
                    instance.getIdentity().getName().toLowerCase(Locale.ROOT).contains("mega congo sarl")
            ){
                instances.add(instance);
            }
        }
        return instances;
    }
    public ArrayList<Fournisseur> getByArticleId(String article_id)
    {
        return repository.getByArticleId(article_id) ;
    }

    public void setFournisseur(Fournisseur fournisseur)
    {
        repository.setFournisseur(fournisseur);
    }

    public void ajout_async(Fournisseur instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }


    public String getId()
    {
        return repository.getId();
    }



    public Fournisseur getFournisseur()
    {
        return repository.getFournisseur() ;
    }

    public Fournisseur get(String id,boolean withIdentity){
        return repository.get(id,withIdentity) ;
    }

    public String get_id_by_name(String name){

        try{
            return repository.get_id_by_name(name);

        }
        catch (Exception e){
            Log.d("Assert","FournisseurViewModel get_id_by_name: "+e.toString());
        }
        return null;
    }

}
