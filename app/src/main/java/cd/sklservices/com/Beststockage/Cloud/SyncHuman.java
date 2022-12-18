package cd.sklservices.com.Beststockage.Cloud;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Human;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.HumanRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncHuman extends DaoDist implements AsyncResponse {

    private HumanRepository instanceRepository;
    public SyncHuman(HumanRepository repo)
    {
        this.instanceRepository = repo ;
       }

    @Override
    public void processFinish(final String output) {
        Thread adding_data=new Thread(){
            public void run(){
                try{
                    //S'il y a deux case donc j'ai reçu quelque choose de la part du serveur
                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation human "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String gender =info.getString("Gender");
                                String birthday =info.getString("Birthday");
                                String email =info.getString("Email");
                                String photo =info.getString("Photo");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_posp=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Human instance=new Human(id,gender,birthday,email,photo,adding_date,updated_date,sync_posp,pos) ;
                                instanceRepository.ajout_sync(instance);


                            }

                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_humans impossible ************************* "+e.toString());
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
              link=AddressFormatForGet("human",LessMonth());
          }
          else
          {
              link=AddressFormatForGet("human",LessDay());
          }

          accesDonnees.execute(link);

      }
      catch (Exception e){
          Log.d("Assert"," ******************** Synchronisation human "+e.toString());

      }
    }


}
