package cd.sklservices.com.Beststockage.Cloud.Stocks;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Stocks.LigneCommandeRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncLigneCommande extends DaoDist implements AsyncResponse {

    private LigneCommandeRepository ligne_commandeRepository;
    public SyncLigneCommande(LigneCommandeRepository repo) {
        this.ligne_commandeRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation ligne_commande "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String commandeId =info.getString("CommandeId");
                                String agenceId=info.getString("AgenceId");
                                String articleId=info.getString("ArticleId");
                                int quantite=info.getInt("Quantite");
                                int bonus=info.getInt("Bonus");
                                Double montant=info.getDouble("Montant");
                                String devise_id=info.getString("DeviseId");
                                String addingAgenceId=info.getString("AddingAgenceId");
                                String addingUserId =info.getString("AddingUserId");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                LigneCommande instance=new LigneCommande(id,commandeId,agenceId,articleId,quantite,bonus,montant,devise_id,
                                        addingUserId,addingAgenceId,adding_date,updated_date,sync_pos,pos);

                                ligne_commandeRepository.ajout_sync(instance);


                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_ligne_commandes impossible ************************* "+e.toString());
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
            accesDonnees.execute(ADRESSE+"get_lite/ligne_commande.php");
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation ligne_commandes ********************"+e.toString());

        }


    }
}
