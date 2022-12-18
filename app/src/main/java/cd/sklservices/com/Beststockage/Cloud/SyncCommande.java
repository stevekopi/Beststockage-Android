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
import cd.sklservices.com.Beststockage.Repository.CommandeRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncCommande extends DaoDist implements AsyncResponse {

    private CommandeRepository commandeRepository;
    public SyncCommande(CommandeRepository repo) {
        this.commandeRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation commande "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String numero=info.getString("Numero");
                                String proprietaireId=info.getString("ProprietaireId");
                                String fournisseurId =info.getString("FournisseurId");
                                String date=info.getString("Date");
                                String membership=info.getString("Membership");
                                String addingAgenceId =info.getString("AddingAgenceId");
                                String addingUserId =info.getString("AddingUserId");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Commande instance=new Commande(id,numero,proprietaireId,fournisseurId,date,membership,addingUserId,addingAgenceId,adding_date,updated_date,sync_pos,pos);

                                commandeRepository.ajout_sync(instance);


                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_commandes impossible ************************* "+e.toString());
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

            accesDonnees.execute(ADRESSE+"get_lite/commande.php");
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation commandes ********************"+e.toString());

        }


    }
}
