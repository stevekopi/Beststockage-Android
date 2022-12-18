package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Human;
import cd.sklservices.com.Beststockage.Classes.User;
import cd.sklservices.com.Beststockage.Repository.HumanRepository;
import cd.sklservices.com.Beststockage.Repository.UserRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class HumanViewModel extends AndroidViewModel {

    private HumanRepository repository ;

    public HumanViewModel(@NonNull Application application) {
        super(application);
        repository = new HumanRepository(application) ;
    }

    public void ajout_async(Human instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }

    public Human get(String id,boolean withIdentity)
    {
        return repository.get(id,withIdentity) ;
    }

    public String getGender(){
        return repository.getGender() ;
    }

    public String getBirthday(){
        return repository.getBirthday() ;
    }

    public String getEmail(){
        return repository.getEmail() ;
    }

    public void gets(){
        repository.gets();
    }

    public List<Human> getHumanArrayListe() {
        return repository.getHumanArrayListe() ;
    }

    public List<Human> select_human(){
        return repository.select_all() ;
    }

    public void setHuman(Human instance){
         repository.setHuman(instance);
    }

    public Human getHuman(){
        return  repository.getHuman() ;
     }

    }
