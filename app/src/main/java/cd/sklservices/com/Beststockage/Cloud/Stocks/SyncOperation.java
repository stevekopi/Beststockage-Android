package cd.sklservices.com.Beststockage.Cloud.Stocks;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.CloudObjects.WebService;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.Repository.Stocks.OperationRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncOperation extends DaoDist implements AsyncResponse {

    private OperationRepository operationRepository;
    public SyncOperation(OperationRepository repo) {
        this.operationRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation operation "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String userId=info.getString("UserId");
                                String agence1Id =info.getString("Agence1Id");
                                String agence2Id=info.getString("Agence2Id");
                                String articleId=info.getString("ArticleId");
                                String operationFinanceId=info.getString("OperationFinanceId");
                                String sensStock=info.getString("SensStock");
                                String sensFinance=info.getString("SensFinance");
                                int isChecked=info.getInt("IsChecked");
                                int isConfirmed =info.getInt("IsConfirmed");
                                String type=info.getString("Type");
                                int quantite=info.getInt("Quantite");
                                int bonus=info.getInt("Bonus");
                                Double montant=info.getDouble("Montant");
                                String deviseId =info.getString("DeviseId");
                                String date=info.getString("Date");
                                String observation=info.getString("Observation");
                                String checking_agence_id=info.getString("CheckingAgenceId");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Operation operation=new Operation(id,userId,operationFinanceId,sensStock,sensFinance,agence1Id,agence2Id,articleId,deviseId,type,quantite,
                                        bonus,montant,date,observation,isConfirmed,isChecked,checking_agence_id,adding_date,updated_date,sync_pos,pos);


                                if (MainActivity.getCurrentUser()!=null &&  ( agence1Id.equals(MainActivity.getCurrentUser().getAgence_id()) || agence2Id.equals(MainActivity.getCurrentUser().getAgence_id()) )  ){
                                    operationRepository.ajout_sync(operation);
                                }


                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_operations impossible ************************* "+e.toString());
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
                link=AddressFormatForGetConnectedAgence("operation",LessMonth());
            }
            else
            {
                link=AddressFormatForGetConnectedAgence("operation",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation operations ********************"+e.toString());

        }

    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<Operation> instances=new OperationRepository(OperationRepository.getContext()).getOperationSynchro();
                    for(Operation instance:instances){
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("Id", instance.getId());
                        jsonParam.put("UserId", instance.getUser_id());
                        jsonParam.put("Agence1Id", instance.getAgence_1_id());
                        jsonParam.put("Agence2Id", instance.getAgence_2_id());
                        jsonParam.put("ArticleId", instance.getArticle_id());
                        jsonParam.put("OperationFinanceId", instance.getOperation_finance_id());
                        jsonParam.put("SensStock", instance.getSens_stock());
                        jsonParam.put("SensFinance", instance.getSens_finance());
                        jsonParam.put("IsChecked", String.valueOf(instance.getIs_checked()));
                        jsonParam.put("IsConfirmed", String.valueOf(instance.getIs_confirmed()));
                        jsonParam.put("Type", instance.getType());
                        jsonParam.put("Quantite", String.valueOf(instance.getQuantite()));
                        jsonParam.put("Bonus", String.valueOf(instance.getBonus()));
                        jsonParam.put("Montant", String.valueOf(instance.getMontant()));
                        jsonParam.put("DeviseId", instance.getDevise_id());
                        jsonParam.put("Date", instance.getDate());
                        jsonParam.put("Observation", MesOutils.replace_accents(instance.getObservation()));
                        jsonParam.put("CheckingAgenceId", instance.getChecking_agence_id());
                        jsonParam.put("AddingDate", instance.getAdding_date());
                        jsonParam.put("UpdatedDate", instance.getUpdated_date());
                        String sp=instance.getSync_pos()==0?"1":"4";
                        jsonParam.put("SyncPos",sp);
                        jsonParam.put("Pos", String.valueOf(instance.getPos()));

                        new WebService().makeRequest(AddressFormatForAdd("operation"),jsonParam.toString());
                    }

                } catch (Exception e) {
                    e.toString();
                }
            }
        });

        thread.start();
    }
}
