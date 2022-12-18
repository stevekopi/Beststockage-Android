package cd.sklservices.com.Beststockage.Cloud;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Identity;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.IdentityRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncIdentity extends DaoDist implements AsyncResponse {

    private IdentityRepository identityRepository;
    public SyncIdentity(IdentityRepository repo)
    {
        this.identityRepository = repo ;
       }

    @Override
    public void processFinish(final String output) {
       Thread adding_data=new Thread(){
           public void run(){
               try{
                   //S'il y a deux case donc j'ai reçu quelque choose de la part du serveur
                   if (output.contains("AddingDate")){
                       try {
                           Log.d("Assert","Synchronisation identity "+output);
                           String data=output;
                           if(data.contains("ï»¿"))
                               data= data.replace("ï»¿","");
                           JSONArray instances=new JSONArray(data);
                           for (int k=0;k<instances.length();k++){
                               JSONObject info=new JSONObject(instances.get(k).toString());
                               String id=info.getString("Id");
                               String adresse_id =info.getString("AddressId");
                               String name = info.getString("Name");
                               String telephone = info.getString("Telephone");
                               String telephone2 = info.getString("Telephone2");
                               String telephone3 = info.getString("Telephone3");
                               String type = info.getString("Type");
                               String adding_date=info.getString("AddingDate");
                               String updated_date=info.getString("UpdatedDate");
                               int sync_posp=info.getInt("SyncPos");
                               int pos=info.getInt("Pos");

                               Identity identity = new Identity(id,adresse_id, type, name, telephone,telephone2,telephone3, adding_date,updated_date,sync_posp,pos) ;
                                identityRepository.ajout_async(identity);

                           }

                       } catch (JSONException e) {
                           Log.d("Assert"," Conversion JSONArray get_identity impossible ************************* "+e.toString());
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
              link=AddressFormatForGet("identity",LessMonth());
          }
          else
          {
              link=AddressFormatForGet("identity",LessDay());
          }

          accesDonnees.execute(link);

      }
      catch (Exception e){
          Log.d("Assert"," ******************** Synchronisation identity "+e.toString());

      }
    }


}
