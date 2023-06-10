package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncUser;
import cd.sklservices.com.Beststockage.Dao.Registres.DaoUser;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class UserRepository {

    private DaoUser daoUser  ;
    private static UserRepository instance=null;
    private static User user ;
    private static Context context;
    private List<User> userArrayListe ;

    public UserRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoUser = mydata.daoUser()  ;
    }

    public static final UserRepository getInstance(Context context){

        if (context!=null){
            UserRepository.context=context;}

        if (UserRepository.instance==null){
            UserRepository.instance=new UserRepository(context);
        }
        return UserRepository.instance;
    }

    public List<User> getUserArrayListe() {
        List<User> values=new ArrayList<>();
        try{
            List<User> instances= daoUser.select_user();

            for (User instance:instances){
                values.add(get(instance.getId(),true,true,true));
            }

        }
        catch (Exception e){
            Log.d("Assert","UserRepository getUserArrayList "+e.toString());
        }
        return values;
    }

    public void setUserArrayListe(List<User> userArrayListe) {

        try{
                this.userArrayListe = userArrayListe;

        }
        catch (Exception e){
            Log.d("Assert","Dao User2222 "+e.toString());
        }

    }

    public void ajout_sync(User instance)
    {
        User old = get(instance.getId(),false,false,false) ;
        if(old == null)
        {
            daoUser.insert(instance);
            new SyncUser(new UserRepository(context)).sendPost();
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoUser.update(instance) ;
                new SyncUser(new UserRepository(context)).sendPost();
            }
        }
    }

    public void update(User instance)
    {
        daoUser.update(instance) ;
        new SyncUser(new UserRepository(context)).sendPost();
    }



    public User get(String id,boolean withRole,boolean withHuman,boolean withAgence){

        try{
            User instance=daoUser.get(id) ;

            if(withRole)
                instance.setRole(new UserRoleRepository(getContext()).get(instance.getUser_role_id()));

            if(withHuman)
                instance.setHuman(new HumanRepository(getContext()).get(instance.getId(),true));

            if(withAgence)
                instance.setAgence(new AgenceRepository(getContext()).get(instance.getAgence_id(),true,false));
            return  instance;
        }
        catch (Exception e){
            Log.d("Assert"," Dao User pppp  "+e.toString());
        }
        return null;
    }



    public List<User> getOperationSynchro(){

        try{
            return  daoUser.select_user_bysend() ;
        }
        catch (Exception e){
            Log.d("Assert"," Dao User pppp  "+e.toString());
        }
        return null;
    }

    public List<User> select_all(){

        try{
            return   daoUser.select_user() ;

        }
        catch (Exception e){
            Log.d("Asset"," Dao User kkk  "+e.toString());
        }
        return null;
    }


    public void gets(){

        try{

            List<User> mylist =  daoUser.select_user() ;
            if(mylist != null)
            {
                instance.setUserArrayListe(mylist);
            }

        }
        catch (Exception e){
            Log.d("Assert","Dao User tost "+e.toString());
        }
    }


    public User acces(String login, String pwd){

        try{
            User user=null;

            List<User> mylist =  daoUser.get_access(login, pwd);

            for (User user1 : mylist){
                user = user1 ;
            }

            return user ;

        }
        catch (Exception e){
            Log.d("Assert","DaoUserLoc.acces ++++++ "+e.toString());
            return null;
        }
    }

    public ArrayList<User> getByAgenceId(String agence_id){

        try{
            ArrayList arrayList=new ArrayList();
            List<User> mylist =  daoUser.get_by_agence(agence_id);

            for (User user1 : mylist){
                arrayList.add(get(user1.getId(),true,true,false)) ;
            }

            return arrayList;

        }
        catch (Exception e){
            Log.d("Assert","DaoUserLoc.acces ....."+e.toString());
            return null;
        }
    }

    public void delete(User instance){
        try{
            new DeleteUserAsyncTask(daoUser).execute(instance) ;
        }
        catch (Exception e){
            Log.d("Assert"," Error Update User  " + e.toString());
        }

    }

    public class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>
    {
        private DaoUser daoU ;

        private DeleteUserAsyncTask(DaoUser daoUser)
        {
            this.daoU = daoUser ;
        }
        @Override
        protected Void doInBackground(User... u) {

            try{

                daoU.delete(u[0]);

            }catch (Exception e){
                Log.d("Assert","Update User " + e.toString());
            }

            return null ;
        }
    }


    public void setUser(User user){
        instance.user = user;
    }

    public User getUser(){
        if (user==null){return null;}
        else{return  user ;}
    }

    public String getAgence_id(){
        if (user==null){return null;}
        else{return  user.getAgence_id();}
    }

     public String getUsername(){
        if (user==null){return null;}
        else{return  user.getUsername();}
    }

    public String getPassword(){
        if (user==null){return null;}
        else{return  user.getPassword();}
    }


    public String getUserRoleId(){
        if (user==null){return null;}
        else{return  user.getUser_role_id();}
    }

    public String getAddingDate(){
        if (user==null){return null;}
        else{return  user.getAdding_date();}
    }

    public String getIdUser(){
        if (user==null){return null;}
        else{return  user.getId();}
    }
    public static final Context getContext(){
        return context;
    }



}
