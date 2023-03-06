package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.UserRole;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoUserRole;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class UserRoleRepository {

    private DaoUserRole daouser_role  ;
    private static UserRoleRepository instance=null;
    private static UserRole user_role;
    private static Context context;
    private List<UserRole> user_roleArrayListe ;

    public UserRoleRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daouser_role = mydata.daoUserRole() ;
    }

    public static final UserRoleRepository getInstance(Context context){

        if (context!=null){
            UserRoleRepository.context=context;}

        if (UserRoleRepository.instance==null){
            UserRoleRepository.instance=new UserRoleRepository(context);
        }
        return UserRoleRepository.instance;
    }

    public void ajout_sync(UserRole instance)
    {
        UserRole old = get(instance.getId()) ;
        if(old == null)
        {
            daouser_role.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daouser_role.update(instance) ;
            }
        }
    }



    public List<UserRole> getUserRoleArrayListe() {
        return user_roleArrayListe;
    }

    public void setUserRoleArrayListe(List<UserRole> articlesArrayListe) {
        this.user_roleArrayListe = articlesArrayListe;
    }



    public UserRole get(String id){

        try{
            return   daouser_role.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
        return null;
    }


    public AsyncTask delete_all()
    {
        daouser_role.delete_all();
        return null ;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<UserRole> mylist =  daouser_role.select_user_role() ;
            instance.setUserRoleArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao UserRole.getsErreur: "+e.toString());
        }
    }



    public String getDesignation(){
        if (user_role==null){return null;}
        else{return  user_role.getDesignation();}
    }

    public String get_id(){
        if (user_role==null){return null;}
        else{return  user_role.getId();}
    }


    public static final Context getContext(){
        return context;
    }

}
