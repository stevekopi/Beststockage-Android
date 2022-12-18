package cd.sklservices.com.Beststockage.Cloud;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Township;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.TownshipRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncTownship extends DaoDist implements AsyncResponse  {
    private TownshipRepository repository ;

    public SyncTownship(TownshipRepository repo){
        this.repository= repo ;
    }

    @Override
    public void processFinish(final String output) {
       Thread adding_data=new Thread(){
           public void run(){
               try{
                   //S'il y a deux case donc j'ai reçu quelque choose de la part du serveur
                   if (output.contains("AddingDate")){
                       Log.d("Assert","Synchronisation township "+output);
                        String data=output;
                       try {
                           if(data.contains("ï»¿"))
                               data= data.replace("ï»¿","");
                           JSONArray values=new JSONArray(data);
                           for (int k=0;k< values.length();k++){
                               JSONObject info=new JSONObject(values.get(k).toString());

                               String id = info.getString("Id");
                               String district_id =info.getString("DistrictId");
                               String name = info.getString("Name");
                               String adding_date=info.getString("AddingDate");
                               String updated_date=info.getString("UpdatedDate");
                               int sync_posp=info.getInt("SyncPos");

                               Township t = new Township(id,district_id, name, adding_date,updated_date,sync_posp,0)  ;
                               repository.ajout_sync(t);


                           }


                       } catch (JSONException e) {
                           Log.d("Assert"," Conversion JSONArray SyncTownship Erreur : ***** "+e.toString());
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
              link=AddressFormatForGet("township",LessMonth());
          }
          else
          {
              link=AddressFormatForGet("township",LessDay());
          }

          accesDonnees.execute(link);
              }
      catch (Exception e){
          Log.d("Assert","Synchronisation users"+e.toString());

      }
    }


}
