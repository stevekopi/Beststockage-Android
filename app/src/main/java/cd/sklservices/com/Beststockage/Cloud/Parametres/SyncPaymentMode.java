package cd.sklservices.com.Beststockage.Cloud.Parametres;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.PaymentMode;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Parametres.PaymentModeRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncPaymentMode extends DaoDist implements AsyncResponse {

    private PaymentModeRepository paymentModeRepository;
    public SyncPaymentMode(PaymentModeRepository repo) {
        this.paymentModeRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation paymentMode "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String designation=info.getString("Designation");
                                int is_default =info.getInt("IsDefault");
                                String aa_id=info.getString("AddingAgenceId");
                                String au_id=info.getString("AddingUserId");
                                String luu_id=info.getString("LastUpdateUserId");
                                String ad=info.getString("AddingDate");
                                String ud=info.getString("UpdatedDate");
                                int sp=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                PaymentMode paymentMode=new PaymentMode(id,designation,is_default,aa_id,au_id,luu_id,ad,ud,sp,pos) ;
                                paymentModeRepository.ajout_sync(paymentMode);

                            }


                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_paymentMode impossible ************************* "+e.toString());
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
                link=AddressFormatForGet("payment_mode",LessMonth());
            }
            else
            {
                link=AddressFormatForGet("payment_mode",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation PaymentMode ********************"+e.toString());

        }


    }
}
