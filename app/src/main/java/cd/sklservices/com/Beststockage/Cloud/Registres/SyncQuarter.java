package cd.sklservices.com.Beststockage.Cloud.Registres;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Quarter;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Registres.QuarterRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncQuarter extends DaoDist implements AsyncResponse  {

    private QuarterRepository repository ;

    public SyncQuarter(QuarterRepository repo){
        this.repository= repo ;
    }

    @Override
    public void processFinish(final String output) {
        Thread adding_data=new Thread(){
          public void run(){
              try{

                  if (output.contains("AddingDate")){
                      Log.d("Assert","Synchronisation quarter "+output);
                      String data=output;
                      try {
                          if(data.contains("ï»¿"))
                              data= output.replace("ï»¿","");

                          JSONArray values=new JSONArray(data);
                          for (int k=0;k< values.length();k++){
                              JSONObject info=new JSONObject(values.get(k).toString());

                              String id = info.getString("Id");
                              String township_id=info.getString("TownshipId");
                              String name = info.getString("Name");
                              String adding_date=info.getString("AddingDate");
                              String updated_date=info.getString("UpdatedDate");
                              int sync_posp=info.getInt("SyncPos");
                              int pos=info.getInt("Pos");

                              Quarter instance = new Quarter(id,township_id,  name,adding_date,updated_date,sync_posp,pos)  ;
                              repository.ajout_sync(instance);

                          }
                           } catch (JSONException e) {
                          Log.d("Assert"," Conversion JSONArray syncQuarter Erreur ***** "+e.toString());
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
              link=AddressFormatForGet("quarter",LessMonth());
          }
          else
          {
              link=AddressFormatForGet("quarter",LessDay());
          }

          accesDonnees.execute(link);
          }
      catch (Exception e){
          Log.d("Assert","SyncQuarter Erreur"+e.toString());

      }
    }


}
