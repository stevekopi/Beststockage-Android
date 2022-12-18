package cd.sklservices.com.Beststockage.ViewModel;

import static java.lang.Thread.sleep;

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

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository ;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application) ;
    }



    public void ajout_sync(User instance){
        repository.ajout_sync(instance);
    }

    public int update(User instance){
        try {
            instance.setSync_pos(0);
            instance.setUpdated_date(MainActivity.getAddingDate());
            instance.setPos(instance.getPos()+1);
            repository.update(instance);
            sleep(500);
            return 1;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public User get(String id,boolean withHuman,boolean withAgence)
    {
        return repository.get(id,true,withHuman,withAgence) ;
    }

    public ArrayList<User> getByAgenceId(String agence_id)
    {
        return repository.getByAgenceId(agence_id) ;
    }


    public String getRole(){
        return repository.getUserRoleId() ;
    }

    public void gets(){
        repository.gets();
    }

    public List<User> getUserArrayListe() {
        return repository.getUserArrayListe() ;
    }

    public User acces(String login, String pwd){
        return repository.acces(login, pwd) ;
    }

    public List<User> select_user(){
        return repository.select_all() ;
    }

    public void setUser(User user){
        user.setAgence(new AgenceViewModel(getApplication()).get(user.getAgence_id(),true,true));
         repository.setUser(user);
    }

    public User getUser(){
        return  repository.getUser() ;
     }

    public String getAgence_id()
    {
        return repository.getAgence_id() ;
    }


    }
