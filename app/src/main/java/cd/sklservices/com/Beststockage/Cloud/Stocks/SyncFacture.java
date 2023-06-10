package cd.sklservices.com.Beststockage.Cloud.Stocks;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.CloudObjects.WebService;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.Repository.Stocks.FactureRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncFacture extends DaoDist implements AsyncResponse {

    private FactureRepository factureRepository;
    public SyncFacture(FactureRepository repo) {
        this.factureRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation facture "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String SecondId=info.getString("SecondId");
                                String ProduitId =info.getString("ProduitId");
                                String AgenceId=info.getString("AgenceId");
                                String Agence2Id=info.getString("Agence2Id");
                                String UserId=info.getString("UserId");
                                String ProprietaireId=info.getString("ProprietaireId");
                                String PaymentModeId=info.getString("PaymentModeId");
                                String Membership=info.getString("Membership");
                                String Date =info.getString("Date");
                                double ReductionRate =info.getDouble("ReductionRate");
                                String DeviseId=info.getString("DeviseId");
                                String ConvertDeviseId=info.getString("ConvertDeviseId");
                                String LocalDeviseId=info.getString("LocalDeviseId");
                                String Observation=info.getString("LocalDeviseId");
                                String CheckingAgenceId =info.getString("CheckingAgenceId");
                                String aa_id=info.getString("AddingAgenceId");
                                String au_id=info.getString("AddingUserId");
                                String luu_id=info.getString("LastUpdateUserId");
                                String ad=info.getString("AddingDate");
                                String ud=info.getString("UpdatedDate");
                                int sp=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Facture facture=new Facture(id,SecondId,ProduitId,AgenceId,Agence2Id,UserId,ProprietaireId,PaymentModeId,Membership,Date,ReductionRate,DeviseId,
                                        ConvertDeviseId,LocalDeviseId,Observation,CheckingAgenceId,au_id,luu_id,aa_id,ad,ud,sp,pos);


                                if (MainActivity.getCurrentUser()!=null &&  ( AgenceId.equals(MainActivity.getCurrentUser().getAgence_id()) || Agence2Id.equals(MainActivity.getCurrentUser().getAgence_id()) )  ){
                                    factureRepository.ajout_sync(facture);
                                }


                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_factures impossible ************************* "+e.toString());
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
                link=AddressFormatForGetConnectedAgence("facture",LessMonth());
            }
            else
            {
                link=AddressFormatForGetConnectedAgence("facture",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation factures ********************"+e.toString());

        }

    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<Facture> instances=new FactureRepository(FactureRepository.getContext()).getFactureSynchro();
                    for(Facture instance:instances){
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("Id", instance.getId());
                        jsonParam.put("SecondId", instance.getSecond_id());
                        jsonParam.put("ProduitId", instance.getProduit_id());
                        jsonParam.put("AgenceId", instance.getAgence_id());
                        jsonParam.put("Agence2Id", instance.getAgence_2_id());
                        jsonParam.put("UserId", instance.getUser_id());
                        jsonParam.put("ProprietaireId", instance.getProprietaire_id());
                        jsonParam.put("PaymentModeId", instance.getPayment_mode_id());
                        jsonParam.put("Membership", instance.getMembership());
                        jsonParam.put("Date", instance.getDate());
                        jsonParam.put("ReductionRate", instance.getReduction_rate());
                        jsonParam.put("DeviseId", instance.getDevise_id());
                        jsonParam.put("ConvertDeviseId", instance.getConvert_devise_id());
                        jsonParam.put("LocalDeviseId", instance.getDevise_local_id());
                        jsonParam.put("Observation", MesOutils.replace_accents(instance.getObservation()));
                        jsonParam.put("AddingAgenceId", instance.getAdding_agence_id());
                        jsonParam.put("CheckingAgenceId", instance.getChecking_agence_id());
                        jsonParam.put("AddingUserId", instance.getAdding_user_id());
                        jsonParam.put("LastUpdateUserId", instance.getLast_update_user_id());
                        jsonParam.put("AddingDate", instance.getAdding_date());
                        jsonParam.put("UpdatedDate", instance.getUpdated_date());
                        String sp=instance.getSync_pos()==0?"1":"4";
                        jsonParam.put("SyncPos",sp);
                        jsonParam.put("Pos", String.valueOf(instance.getPos()));

                        new WebService().makeRequest(AddressFormatForAdd("facture"),jsonParam.toString());
                    }

                } catch (Exception e) {
                    e.toString();
                }
            }
        });

        thread.start();
    }
}
