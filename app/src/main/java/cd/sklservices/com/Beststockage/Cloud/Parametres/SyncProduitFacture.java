package cd.sklservices.com.Beststockage.Cloud.Parametres;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Parametres.ProduitFactureRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncProduitFacture extends DaoDist implements AsyncResponse {

    private ProduitFactureRepository produitFactureRepository;
    public SyncProduitFacture(ProduitFactureRepository repo) {
        this.produitFactureRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation produitFacture "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String second_id=info.getString("SecondId");
                                String designation=info.getString("Designation");
                                String type =info.getString("Type");
                                Boolean with_bonus =info.getBoolean("WithBonus");
                                String sens_stock =info.getString("SensStock");
                                String sens_finance =info.getString("SensFinance");
                                Boolean default_checked =info.getBoolean("DefaultChecked");
                                Boolean default_confirmed =info.getBoolean("DefaultConfirmed");
                                String aa_id=info.getString("AddingAgenceId");
                                String au_id=info.getString("AddingUserId");
                                String luu_id=info.getString("LastUpdateUserId");
                                String ad=info.getString("AddingDate");
                                String ud=info.getString("UpdatedDate");
                                int sp=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                ProduitFacture produitFacture=new ProduitFacture(id,second_id,designation,type,with_bonus,sens_stock,
                                        sens_finance,default_checked,default_confirmed,au_id,luu_id,aa_id,ad,ud,sp,pos) ;
                                produitFactureRepository.ajout_sync(produitFacture);

                            }


                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_produitFacture impossible ************************* "+e.toString());
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
                link=AddressFormatForGet("produit_facture",LessMonth());
            }
            else
            {
                link=AddressFormatForGet("produit_facture",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation ProduitFacture ********************"+e.toString());

        }


    }
}
