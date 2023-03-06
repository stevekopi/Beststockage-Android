package cd.sklservices.com.Beststockage.Cloud.Parametres;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Parametres.ArticleProduitFactureRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncArticleProduitFacture extends DaoDist implements AsyncResponse {

    private ArticleProduitFactureRepository articleProduitFactureRepository;
    public SyncArticleProduitFacture(ArticleProduitFactureRepository repo) {
        this.articleProduitFactureRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation articleProduitFacture "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String Id=info.getString("Id");
                                String ArticleId=info.getString("ArticleId");
                                String ProduitId=info.getString("ProduitId");
                                Double MontantHt =info.getDouble("MontantHt");
                                Double TvaRate =info.getDouble("TvaRate");
                                Double Tva =info.getDouble("Tva");
                                Double MontantTtc =info.getDouble("MontantTtc");
                                String DeviseId =info.getString("DeviseId");
                                int QuantiteMin =info.getInt("QuantiteMin");
                                String aa_id=info.getString("AddingAgenceId");
                                String au_id=info.getString("AddingUserId");
                                String luu_id=info.getString("LastUpdateUserId");
                                String ad=info.getString("AddingDate");
                                String ud=info.getString("UpdatedDate");
                                int sp=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                ArticleProduitFacture articleProduitFacture=new ArticleProduitFacture(Id,ArticleId,ProduitId,MontantHt,TvaRate,
                                        Tva,MontantTtc,DeviseId,QuantiteMin,au_id,luu_id,aa_id,ad,ud,sp,pos) ;
                                articleProduitFactureRepository.ajout_sync(articleProduitFacture);

                            }


                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_articleProduitFacture impossible ************************* "+e.toString());
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
                link=AddressFormatForGet("article_produit_facture",LessMonth());
            }
            else
            {
                link=AddressFormatForGet("article_produit_facture",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation ArticleProduitFacture ********************"+e.toString());

        }


    }
}
