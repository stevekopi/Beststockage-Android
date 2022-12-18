package cd.sklservices.com.Beststockage.Cloud;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Repository.ContenanceRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncContenance extends DaoDist implements AsyncResponse {

    private ContenanceRepository contenanceRepository;

    public SyncContenance(ContenanceRepository repo) {
        this.contenanceRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation contenance "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String capacite =info.getString("Capacite");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Contenance instance=new Contenance(id,capacite,adding_date,updated_date,sync_pos,pos);
                                contenanceRepository.ajout_sync(instance);

                            }

                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_contenances impossible ************************* "+e.toString());
                        }

                    }
                }
                catch (Exception ex){ex.printStackTrace();}
                finally {
                    envoi();
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
                link=AddressFormatForGet("contenance",LessMonth());
            }
            else
            {
                link=AddressFormatForGet("contenance",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation contenances ********************"+e.toString());

        }


    }
}
