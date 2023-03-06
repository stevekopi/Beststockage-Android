package cd.sklservices.com.Beststockage.Cloud.Finances;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Finances.Depense;
import cd.sklservices.com.Beststockage.CloudObjects.WebService;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.Repository.Finances.DepenseRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncDepense extends DaoDist implements AsyncResponse {

    private DepenseRepository depenseRepository;
    public SyncDepense(DepenseRepository repo) {
        this.depenseRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation depense "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String agence_id=info.getString("AgenceId");
                                String operationFinanceId =info.getString("OperationFinanceId");
                                int isChecked=info.getInt("IsChecked");
                                String date=info.getString("Date");
                                String observation=info.getString("Observation");
                                Double montant=info.getDouble("Montant");
                                String deviseId=info.getString("DeviseId");
                                String addingAgenceId =info.getString("AddingAgenceId");
                                String addingUserId =info.getString("AddingUserId");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Depense instance=new Depense(id,agence_id,Boolean.getBoolean(String.valueOf(isChecked)),operationFinanceId,date,observation,montant,deviseId,
                                addingUserId,addingAgenceId,adding_date,updated_date,sync_pos,pos);


                                if (MainActivity.getCurrentUser()!=null && agence_id.equals(MainActivity.getCurrentUser().getAgence_id())){
                                    depenseRepository.ajout_sync(instance);
                                }


                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_depenses impossible ************************* "+e.toString());
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
                link=AddressFormatForGetConnectedAgence("depense",LessMonth());
            }
            else
            {
                link=AddressFormatForGetConnectedAgence("depense",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation depenses ********************"+e.toString());

        }


    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<Depense> instances=new DepenseRepository(DepenseRepository.getContext()).get_for_post_sync();
                    for(Depense instance:instances){
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("Id", instance.getId());
                        jsonParam.put("AgenceId", instance.getAgence_id());
                        jsonParam.put("OperationFinanceId", instance.getOperation_finance_id());
                        jsonParam.put("Date", instance.getDate());
                        jsonParam.put("Observation", MesOutils.replace_accents(instance.getObservation()));
                        jsonParam.put("Montant", String.valueOf(instance.getMontant()));
                        jsonParam.put("DeviseId", instance.getDevise_id());
                        jsonParam.put("IsChecked", instance.getIs_checked()==true?"1":"0");
                        jsonParam.put("AddingAgenceId", instance.getAdding_agence_id());
                        jsonParam.put("AddingUserId", instance.getAdding_user_id());
                        jsonParam.put("AddingDate", instance.getAdding_date());
                        jsonParam.put("UpdatedDate", instance.getUpdated_date());
                        String sp=instance.getSync_pos()==0?"1":"4";
                        jsonParam.put("SyncPos",sp);
                        jsonParam.put("Pos", String.valueOf(instance.getPos()));

                        new WebService().makeRequest(AddressFormatForAdd("depense"),jsonParam.toString());
                    }
                } catch (Exception e) {
                    e.toString();
                }
            }
        });

        thread.start();
    }
}
