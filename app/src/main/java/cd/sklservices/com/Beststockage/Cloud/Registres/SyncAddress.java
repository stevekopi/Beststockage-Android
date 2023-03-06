package cd.sklservices.com.Beststockage.Cloud.Registres;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Address;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Registres.AddressRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncAddress extends DaoDist implements AsyncResponse {

    private AddressRepository addressRepository;
    public SyncAddress(AddressRepository repo)
    {
        this.addressRepository = repo ;
       }

    @Override
    public void processFinish(final String output) {
       Thread adding_data=new Thread(){
           public void run(){
               try{
                   if (output.contains("AddingDate")){
                       try {
                           Log.d("Assert","Synchronisation address "+output);
                           String data=output;
                           if(data.contains("ï»¿"))
                               data= data.replace("ï»¿","");

                           JSONArray adresses=new JSONArray(data);
                           for (int k=0;k<adresses.length();k++){
                               JSONObject info=new JSONObject(adresses.get(k).toString());
                               String id=info.getString("Id");
                               String street_id =info.getString("StreetId");
                               String number =info.getString("Number");
                               String reference =info.getString("Reference");
                               String adding_date=info.getString("AddingDate");
                               String updated_date=info.getString("UpdatedDate");
                               int sync_posp=info.getInt("SyncPos");
                               int pos=info.getInt("Pos");

                               Address instance=new Address(id,street_id, number,reference, adding_date,updated_date,sync_posp,pos) ;

                               addressRepository.ajout_sync(instance);
                           }

                       } catch (JSONException e) {
                           Log.d("Assert"," Conversion JSONArray get_adresses impossible ************************* "+e.toString());
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
          // Appel au serveur
          String link;
          if(MainActivity.isFirstRoundSync)
          {
              link=AddressFormatForGet("address",LessMonth());
          }
          else
          {
              link=AddressFormatForGet("address",LessDay());
          }

          accesDonnees.execute(link);
      }
      catch (Exception e){
          Log.d("Assert"," ******************** Synchronisation adresses "+e.toString());

      }
    }


}
