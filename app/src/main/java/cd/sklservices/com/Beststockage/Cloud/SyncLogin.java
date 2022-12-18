package cd.sklservices.com.Beststockage.Cloud;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.ActivityFolder.SecureView;
import cd.sklservices.com.Beststockage.ActivityFolder.SecureViewLoading;
import cd.sklservices.com.Beststockage.Classes.Human;
import cd.sklservices.com.Beststockage.Classes.Identity;
import cd.sklservices.com.Beststockage.Classes.User;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.HumanRepository;
import cd.sklservices.com.Beststockage.Repository.IdentityRepository;
import cd.sklservices.com.Beststockage.Repository.UserRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncLogin extends DaoDist implements AsyncResponse  {

    private UserRepository userRepository;
    private Application application;

    public SyncLogin(UserRepository repo,Application application){

        this.userRepository = repo ;
        this.application=application;
    }

    @Override
    public void processFinish(final String output) throws Exception {
        try{
            if (output.contains("AddingDate")){
                try {
                    Log.d("Assert","Synchronisation login "+output);
                    String data=output;
                    if(data.contains("ï»¿"))
                        data= data.replace("ï»¿","");

                    JSONArray users=new JSONArray(data);
                    for (int k=0;k<users.length();k++){

                        JSONObject info=new JSONObject(users.get(k).toString());

                        String id = info.getString("Id");
                        String agence_id = info.getString("AgenceId");
                        String user_role_id =  info.getString("UserRoleId");
                        String username = info.getString("Username");
                        String statut = info.getString("Statut");
                        String password = info.getString("Password");
                        String adding_date = info.getString("AddingDate");
                        String updated_date = info.getString("UpdatedDate");
                        int sync_pos = info.getInt("SyncPos");
                        int pos = info.getInt("Pos");


                        User user=new User(id,agence_id,user_role_id,username,password,statut,adding_date,updated_date,sync_pos,pos);
                        MainActivity.setCurrentUser(application,user);



                    }


                } catch (JSONException e) {
                    Log.d("Assert"," Conversion JSONArray JsonLogin impossible ************************* "+e.toString());
                }


            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
        }

    }

    public void envoi(String username, String password){
        try{
            AccesHTTP accesDonnees=new AccesHTTP();
            //Lien de delegate
            accesDonnees.delegate=this;
            // AJout parametres
            // Appel au serveur
            String link=ADRESSE  + "get_lite/login.php?username="+username+"&password="+password;
            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation user login"+e.toString());

        }
    }


}

