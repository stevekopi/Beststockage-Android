package cd.sklservices.com.Beststockage.Cloud.Parametres;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.FournisseurTaux;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Parametres.FournisseurTauxRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncFournisseurTaux extends DaoDist implements AsyncResponse {

    private FournisseurTauxRepository fournisseurTauxRepository;
    public SyncFournisseurTaux(FournisseurTauxRepository repo) {
        this.fournisseurTauxRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation fournisseur_taux "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String fournisseurId=info.getString("FournisseurId");
                                String date=info.getString("Date");
                                String from=info.getString("From");
                                String to =info.getString("To");
                                double amount =info.getDouble("Amount");
                                int statut =info.getInt("Statut");
                                String ad=info.getString("AddingDate");
                                String ud=info.getString("UpdatedDate");
                                int sp=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                FournisseurTaux fournisseurTaux=new FournisseurTaux(id,fournisseurId,date,from,to,amount,statut,ad,ud,sp,pos) ;
                                fournisseurTauxRepository.ajout_sync(fournisseurTaux);

                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_fournisseurTaux impossible ************************* "+e.toString());
                        }
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
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
                link=AddressFormatForGet("fournisseur_taux",LessMonth());
            }
            else
            {
                link=AddressFormatForGet("fournisseur_taux",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation FournisseurTaux ********************"+e.toString());

        }


    }
}
