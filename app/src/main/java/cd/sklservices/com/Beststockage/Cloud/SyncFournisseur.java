package cd.sklservices.com.Beststockage.Cloud;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Repository.FournisseurRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncFournisseur extends DaoDist implements AsyncResponse  {

    private FournisseurRepository fournisseurRepository;
     public SyncFournisseur(FournisseurRepository repo){
        this.fournisseurRepository = repo ;
    }

    @Override
    public void processFinish(String output) {


        final Thread adding_data=new Thread(){
            public  void run(){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation fournisseur "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);

                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_posp=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Fournisseur fournisseur=new Fournisseur(id, adding_date,updated_date,sync_posp,pos) ;
                                fournisseurRepository.ajout_sync(fournisseur);



                            }

                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray Syncfournisseur impossible ************************* "+e.toString());
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
      try{
          AccesHTTP accesDonnees=new AccesHTTP();
          //Lien de delegate
          accesDonnees.delegate=this;
          String link;
          if(MainActivity.isFirstRoundSync)
          {
              link=AddressFormatForGet("fournisseur",LessMonth());
          }
          else
          {
              link=AddressFormatForGet("fournisseur",LessDay());
          }

          accesDonnees.execute(link);
      }
      catch (Exception e){
          Log.d("Assert","Synchronisation fournisseurs ********************"+e.toString());

      }
    }


}
