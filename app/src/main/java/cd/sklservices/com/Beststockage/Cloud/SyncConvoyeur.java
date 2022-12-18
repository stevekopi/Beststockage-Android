package cd.sklservices.com.Beststockage.Cloud;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Convoyeur;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.ConvoyeurRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncConvoyeur extends DaoDist implements AsyncResponse {

    private ConvoyeurRepository convoyeurRepository;
    public SyncConvoyeur(ConvoyeurRepository repo) {
        this.convoyeurRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation convoyeur "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String proprietaireId=info.getString("ProprietaireId");
                                String name=info.getString("Name");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Convoyeur instance=new Convoyeur(id,proprietaireId,name,adding_date,updated_date,sync_pos,pos);

                                convoyeurRepository.ajout_sync(instance);



                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_convoyeurs impossible ************************* "+e.toString());
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
                link=AddressFormatForGet("convoyeur",LessMonth());
            }
            else
            {
                link=AddressFormatForGet("convoyeur",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation convoyeurs ********************"+e.toString());

        }


    }
}
