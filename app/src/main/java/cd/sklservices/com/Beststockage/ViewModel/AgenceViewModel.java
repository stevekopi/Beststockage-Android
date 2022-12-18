package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Repository.*;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class AgenceViewModel extends AndroidViewModel {

    private AgenceRepository repository ;
    public static Agence selectedAgence;

    public AgenceViewModel(@NonNull Application application) {
        super(application);
        repository = new AgenceRepository(application) ;
    }

    public void ajout_async(Agence instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public void gets()
    {
        repository.gets() ;
    }

    public List<Agence> getAgencesArrayListe()
    {
        return repository.getAgencesArrayListe();
    }

    public List<Agence> gets(boolean withAddress,boolean withProprietaire)
    {
        List<Agence> instances=new ArrayList<>();
        for (Agence instance:repository.getAgencesArrayListe())
        {
            instances.add(repository.get(instance.getId(),withAddress,withProprietaire));
        }
        return instances;
    }

  /*
       public List<Agence> getLoading() {
        List<Agence> list= repository.getAgenceLoading () ;
        List<Agence> instances=new ArrayList<>();
        for (Agence a:list){
            instances.add(get(a.getId(),false,true));
        }

        return instances;
    }


     public List<Agence> getLoading2(String value ) {

        List<Agence> list= repository.getAgenceLoading2 (value) ;
        List<Agence> instances=new ArrayList<>();
         for (Agence a:list){
            instances.add(get(a.getId(),false,true));
        }

        return instances;
    }


   */

    public List<Agence> getLoading() {
        List<Agence> list= repository.getAgenceLoading () ;
        List<Agence> instances=new ArrayList<>();
        for (Agence a:list){
            instances.add(get(a.getId(),false,true));
        }

        return instances;
    }

    public List<Agence> getLoading2(String value ) {

        List<Agence> list= repository.getAgenceLoading2 (value) ;
        List<Agence> instances=new ArrayList<>();
        for (Agence instance:list){
            Identity identity=new Identity();
            identity.setName(new IdentityViewModel(getApplication()).select_name_by_agence_id(instance.getId()));
            instance.setProprietaire(identity);
            instances.add(instance);
        }

        return instances;
    }


    public Agence get(String id,boolean withAddress,boolean withProprietaire)
    {
        return repository.get(id,withAddress,withProprietaire) ;
    }

    public String getId()
    {
        return repository.getId() ;
    }

    public String get_denomination()
    {
        return repository.get_denomination() ;
    }

    public String get_type()
    {
        return repository.get_type() ;
    }

    public String get_telephone()
    {
        return repository.get_telephone() ;
    }

    public String get_adresse_id()
    {
        return repository.get_adresse_id() ;
    }

    public void setAgence(Agence agence){
        repository.setAgence(agence);
    }


    public void delete_all(){
        repository.delete_all() ;
    }

    public Agence getAgence(){
        return repository.getAgence() ;
    }

    public List<Agence> getByUserId(String user_id)
    {
        return repository.getByUserId(user_id) ;
    }

    public List<Agence> getByCustomer(boolean withAddress,boolean withProprietaire){
        List<Agence> instances=new ArrayList<>();
        for (Agence item:repository.getByCustomer())
        {
            instances.add(repository.get(item.getId(),withAddress,withProprietaire));
        }
        return instances;
    }

    public List<Agence> getByPrivee(){
        return repository.getByPrivee() ;
    }

    public List<Agence> getByAppartenanceProprietaire(String appartenance, String proprietaire){
        return repository.getByAppartenanceProprietaire(appartenance, proprietaire) ;
    }

    public int count(){
        return repository.count();
    }

    public static Agence getSelectedAgence() {
        return selectedAgence;
    }

    public static void setSelectedAgence(Agence selectedAgence) {
        AgenceViewModel.selectedAgence = selectedAgence;
    }
}


