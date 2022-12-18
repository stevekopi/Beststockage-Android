package cd.sklservices.com.Beststockage.Cloud;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Street;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.StreetRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncStreet extends DaoDist implements AsyncResponse  {

    private StreetRepository repository ;
     public SyncStreet(StreetRepository repo){
        this.repository= repo ;
    }

    @Override
    public void processFinish(final String output) {
       Thread adding_data=new Thread(){
           public void run(){
               try{
                   if (output.contains("AddingDate")){
                       try {
                           Log.d("Assert","Synchronisation street "+output);
                           String data=output;
                           if(data.contains("ï»¿"))
                               data= data.replace("ï»¿","");

                           JSONArray values=new JSONArray(data);
                           for (int k=0;k< values.length();k++){
                               JSONObject info=new JSONObject(values.get(k).toString());

                               String id = info.getString("Id");
                               String quarter_id=info.getString("QuarterId");
                               String name = info.getString("Name");
                               String adding_date=info.getString("AddingDate");
                               String updated_date=info.getString("UpdatedDate");
                               int sync_posp=info.getInt("SyncPos");
                               int pos=info.getInt("Pos");

                               Street instance = new Street(id,quarter_id, name,adding_date,updated_date,sync_posp,pos);
                                repository.ajout_sync(instance);

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
                      link=AddressFormatForGet("street",LessMonth());
                  }
                  else
                  {
                      link=AddressFormatForGet("street",LessDay());
                  }

                  accesDonnees.execute(link);
              }
      catch (Exception e){
          Log.d("Assert","Synchronisation users"+e.toString());

      }
    }


}
