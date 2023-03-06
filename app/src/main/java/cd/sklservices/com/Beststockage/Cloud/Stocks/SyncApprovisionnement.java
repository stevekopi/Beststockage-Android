package cd.sklservices.com.Beststockage.Cloud.Stocks;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Stocks.Approvisionnement;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Stocks.ApprovisionnementRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncApprovisionnement extends DaoDist implements AsyncResponse {

    private ApprovisionnementRepository approvisionnementRepository;
    public SyncApprovisionnement(ApprovisionnementRepository repo) {
        this.approvisionnementRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation approvisionnement "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String livraisonId=info.getString("LivraisonId");
                                String operationId =info.getString("OperationId");
                                String agenceId=info.getString("AgenceId");
                                String articleId=info.getString("ArticleId");
                                int quantite=info.getInt("Quantite");
                                int bonus=info.getInt("Bonus");
                                double montant=info.getDouble("Montant");
                                String addingAgenceId =info.getString("AddingAgenceId");
                                String addingUserId =info.getString("AddingUserId");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");
                                String AgenceFilterId=info.getString("AgenceFilterId");

                                Approvisionnement approvisionnement=new Approvisionnement(id,livraisonId,operationId,agenceId,articleId,quantite,
                                        bonus,montant,addingUserId,addingAgenceId,adding_date,updated_date,sync_pos,pos);

                                if (MainActivity.getCurrentUser()!=null && AgenceFilterId.equals(MainActivity.getCurrentUser().getAgence_id())){
                                    approvisionnementRepository.ajout_sync(approvisionnement);
                                }




                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_approvisionnements impossible ************************* "+e.toString());
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
                link=AddressFormatForGetConnectedAgence("approvisionnement",LessMonth());
            }
            else
            {
                link=AddressFormatForGetConnectedAgence("approvisionnement",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation approvisionnements ********************"+e.toString());

        }


    }
}
