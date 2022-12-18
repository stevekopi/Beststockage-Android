package cd.sklservices.com.Beststockage.Cloud;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Repository.BonLivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.UserRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncBonlivraison extends DaoDist implements AsyncResponse {

    private BonLivraisonRepository bonlivraisonRepository;
    public SyncBonlivraison(BonLivraisonRepository repo) {
        this.bonlivraisonRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation bonlivraison "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String userId=info.getString("UserId");
                                String vehiculeId =info.getString("VehiculeId");
                                String delegueId=info.getString("DelegueId");
                                String driverId=info.getString("DriverId");
                                String convoyeurId=info.getString("ConvoyeurId");
                                String addingAgenceId=info.getString("AddingAgenceId");
                                String addingUserId =info.getString("AddingUserId");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");
                                String agenceIdForFilter=info.getString("AgenceId");


                                Bonlivraison instance=new Bonlivraison(id,userId,driverId,vehiculeId,delegueId,convoyeurId,
                                        addingUserId,addingAgenceId,adding_date,updated_date,sync_pos,pos);

                                if (MainActivity.getCurrentUser()!=null && agenceIdForFilter.equals(MainActivity.getCurrentUser().getAgence_id())){
                                    bonlivraisonRepository.ajout_sync(instance);
                                }


                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_bonlivraisons impossible ************************* "+e.toString());
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
                link=AddressFormatForGetConnectedAgence("bonlivraison",LessMonth());
            }
            else
            {
                link=AddressFormatForGetConnectedAgence("bonlivraison",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation bonlivraisons ********************"+e.toString());

        }


    }
}
