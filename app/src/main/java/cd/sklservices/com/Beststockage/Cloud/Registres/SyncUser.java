package cd.sklservices.com.Beststockage.Cloud.Registres;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.CloudObjects.WebService;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Registres.UserRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncUser extends DaoDist implements AsyncResponse  {

    private UserRepository userRepository ;
    public SyncUser(UserRepository repo){
        this.userRepository = repo ;
    }

    @Override
    public void processFinish(String output) {

       Thread adding_data=new Thread(){
           public void run(){
               try{
                   Log.d("Assert","Synchronisation user "+output);
                   if (output.contains("AddingDate")){
                       try {
                           String data=output;
                           if(data.contains("ï»¿"))
                               data= data.replace("ï»¿","");
                           JSONArray instances=new JSONArray(data);
                           for (int k=0;k<instances.length();k++){
                               JSONObject info=new JSONObject(instances.get(k).toString());

                               String idUser=info.getString("Id");
                               String idAgence=info.getString("AgenceId");
                               String user_role_id=  info.getString("UserRoleId");
                               String username=info.getString("Username");
                               String pwd=info.getString("Password");
                               String statut = info.getString("Statut");

                               String adding_date=info.getString("AddingDate");
                               String updated_date=info.getString("UpdatedDate");
                               int sync_pos=info.getInt("SyncPos");
                               int pos =info.getInt("Pos");

                               User user = new User(idUser,idAgence,user_role_id,username,pwd,statut,adding_date,updated_date,sync_pos,pos);
                               userRepository.ajout_sync(user);

                           }

                       } catch (JSONException e) {
                           Log.d("Assert"," Conversion JSONArray get_users impossible ***** "+e.toString());
                       }

                   }


               }
               catch (Exception ex){ex.printStackTrace();}
               finally {
               }
           }
       };
       adding_data.start();
    }



    public void envoi(){
      try{
          AccesHTTP accesDonnees=new AccesHTTP();
          //Lien de delegate
          accesDonnees.delegate=this;
          // Appel au serveur
          String link;
          if(MainActivity.isFirstRoundSync)
          {
              link=AddressFormatForGet("user",LessMonth());
          }
          else
          {
              link=AddressFormatForGet("user",LessDay());
          }

          accesDonnees.execute(link);
         }
      catch (Exception e){
          Log.d("Assert","Synchronisation users"+e.toString());

      }
    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<User> instances=new UserRepository(UserRepository.getContext()).getOperationSynchro();
                    for(User instance:instances){
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("Id", instance.getId());
                        jsonParam.put("AgenceId", instance.getAgence_id());
                        jsonParam.put("UserRoleId", instance.getUser_role_id());
                        jsonParam.put("Username", instance.getUsername());
                        jsonParam.put("Password", instance.getPassword());
                        jsonParam.put("Statut", instance.getStatut());
                        jsonParam.put("AddingDate", instance.getAdding_date());
                        jsonParam.put("UpdatedDate", instance.getUpdated_date());
                        String sp=instance.getSync_pos()==0?"1":"4";
                        jsonParam.put("SyncPos",sp);
                        jsonParam.put("Pos", String.valueOf(instance.getPos()));

                        new WebService().makeRequest(AddressFormatForAdd("user"),jsonParam.toString());
                    }

                } catch (Exception e) {
                    e.toString();
                }
            }
        });

        thread.start();
    }

}
