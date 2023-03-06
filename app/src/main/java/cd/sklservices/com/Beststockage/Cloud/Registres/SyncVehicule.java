package cd.sklservices.com.Beststockage.Cloud.Registres;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Vehicule;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Registres.VehiculeRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncVehicule extends DaoDist implements AsyncResponse {

    private VehiculeRepository vehiculeRepository;
    public SyncVehicule(VehiculeRepository repo) {
        this.vehiculeRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation vehicule "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String proprietaireId=info.getString("ProprietaireId");
                                String marque=info.getString("Marque");
                                String matricule=info.getString("Matricule");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Vehicule instance=new Vehicule(id,matricule,proprietaireId,marque,adding_date,updated_date,sync_pos,pos);
                                vehiculeRepository.ajout_sync(instance);

                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_vehicules impossible ************************* "+e.toString());
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
                link=AddressFormatForGet("vehicule",LessMonth());
            }
            else
            {
                link=AddressFormatForGet("vehicule",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation vehicules ********************"+e.toString());

        }


    }
}
