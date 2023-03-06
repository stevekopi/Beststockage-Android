package cd.sklservices.com.Beststockage.Cloud.Stocks;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Stocks.Bon;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Stocks.BonRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncBon extends DaoDist implements AsyncResponse {

    private BonRepository bonRepository;
    public SyncBon(BonRepository repo) {
        this.bonRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation bon "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String numero=info.getString("Numero");
                                String fournisseur_id =info.getString("FournisseurId");
                                String type=info.getString("Type");
                                String membership=info.getString("Membership");
                                String proprietaireId=info.getString("ProprietaireId");
                                String date=info.getString("Date");
                                String addingAgenceId=info.getString("AddingAgenceId");
                                String addingUserId =info.getString("AddingUserId");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");
                                String agenceIdForFilter=info.getString("AgenceId");

                                Bon instance=new Bon(id,numero,fournisseur_id,type,membership,proprietaireId,date,
                                        addingUserId,addingAgenceId,adding_date,updated_date,sync_pos,pos);


                                if (MainActivity.getCurrentUser()!=null && agenceIdForFilter.equals(MainActivity.getCurrentUser().getAgence_id())){
                                    bonRepository.ajout_sync(instance);
                                }



                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_bons impossible ************************* "+e.toString());
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
                link=AddressFormatForGetConnectedAgence("bon",LessMonth());
            }
            else
            {
                link=AddressFormatForGetConnectedAgence("bon",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation bons ********************"+e.toString());

        }


    }
}
