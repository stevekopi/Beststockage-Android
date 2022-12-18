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
import cd.sklservices.com.Beststockage.Repository.ArticleRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncArticle extends DaoDist implements AsyncResponse {

    private ArticleRepository articleRepository;

    public SyncArticle(ArticleRepository repo) {
        this.articleRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation article "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String categorie_id=info.getString("CategoryId");
                                String fournisseur_id=info.getString("FournisseurId");
                                String designation=info.getString("Designation");
                                String section_1=info.getString("Section1");
                                String section_2=info.getString("Section2");
                                String devise_id=info.getString("DeviseId");
                                String contenance_id =info.getString("ContenanceId");
                                Double prix_detail=info.getDouble("PrixDetail");
                                Double prix=info.getDouble("Prix");
                                Double remise=info.getDouble("Remise");
                                Double poids=info.getDouble("Poids");
                                String unite_poids=info.getString("UnitePoids");
                                String grammage=info.getString("Grammage");
                                String qr_code=info.getString("QrCode");
                                String code_barre=info.getString("CodeBarre");
                                String lien_web=info.getString("LienWeb");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Article instance=new Article(id,categorie_id,fournisseur_id,devise_id,contenance_id,section_1,
                                        section_2,designation,prix,prix_detail,remise,poids,unite_poids,grammage,qr_code,code_barre,lien_web,adding_date,updated_date,sync_pos,pos);
                                articleRepository.ajout_sync(instance);


                            }


                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_articles impossible ************************* "+e.toString());
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
                link=AddressFormatForGet("article",LessMonth());
            }
            else
            {
                link=AddressFormatForGet("article",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation articles ********************"+e.toString());

        }


    }
}
