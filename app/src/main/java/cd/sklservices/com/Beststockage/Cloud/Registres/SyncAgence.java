package cd.sklservices.com.Beststockage.Cloud.Registres;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Registres.AgenceRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncAgence extends DaoDist implements AsyncResponse {

    private AgenceRepository agenceRepository;
      public SyncAgence(AgenceRepository repo) {
        this.agenceRepository = repo ;
    }



    @Override
    public void processFinish(String output)
    {
       Thread adding_data=new Thread(){
         public void run (){
             try{

                 if (output.contains("AddingDate")){
                     try {
                         Log.d("Assert","Synchronisation agence "+output);
                         String data=output;
                         if(data.contains("ï»¿"))
                             data= data.replace("ï»¿","");
                         JSONArray instances=new JSONArray(data);
                         for (int k=0;k<instances.length();k++){
                             JSONObject info=new JSONObject(instances.get(k).toString());
                             String id=info.getString("Id");
                             String idAdresse=info.getString("AddressId");
                             String proprietaire_id =info.getString("ProprietaireId");
                             String type=info.getString("Type");
                             String denomination=info.getString("Denomination");
                             String telephone=info.getString("Telephone");
                             String telephone2=info.getString("Telephone2");
                             String telephone3=info.getString("Telephone3");
                             String appartenance =info.getString("Appartenance");
                             String adding_date=info.getString("AddingDate");
                             String updated_date=info.getString("UpdatedDate");
                             int sync_pos=info.getInt("SyncPos");
                             int pos=info.getInt("Pos");

                             Agence instance=new Agence(id,idAdresse, proprietaire_id, type, denomination,
                                     telephone,telephone2,telephone3, appartenance, adding_date,updated_date,sync_pos,pos) ;

                                 agenceRepository.ajout_sync(instance);


                         }
                     } catch (JSONException e) {
                         Log.d("Assert"," Conversion JSONArray get_agences impossible ************************* "+e.toString());
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
    try {
        AccesHTTP accesDonnees=new AccesHTTP();
        //Lien de delegate
        accesDonnees.delegate=this;
        // Appel au serveur
        String link;
        if(MainActivity.isFirstRoundSync)
        {
            link=AddressFormatForGet("agence",LessMonth());
        }
        else
        {
            link=AddressFormatForGet("agence",LessDay());
        }

        accesDonnees.execute(link);
    }
    catch (Exception e){
        Log.d("Assert","Synchronisation agences ********************"+e.toString());

    }


    }
}