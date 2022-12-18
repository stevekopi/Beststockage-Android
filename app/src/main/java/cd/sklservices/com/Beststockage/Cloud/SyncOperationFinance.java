package cd.sklservices.com.Beststockage.Cloud;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.CloudObjects.WebService;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.Repository.DepenseRepository;
import cd.sklservices.com.Beststockage.Repository.OperationFinanceRepository;
import cd.sklservices.com.Beststockage.Repository.UserRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncOperationFinance extends DaoDist implements AsyncResponse {

    private OperationFinanceRepository operation_financeRepository;
    public SyncOperationFinance(OperationFinanceRepository repo) {
        this.operation_financeRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation operation_finance "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String agence_id=info.getString("AgenceId");
                                String fournisseur_id =info.getString("FournisseurId");
                                String date=info.getString("Date");
                                String type=info.getString("Type");
                                String categorie=info.getString("Categorie");
                                String insert_mode=info.getString("InsertMode");
                                int isCaisseCheched=info.getInt("IsCaisseChecked");
                                int isStockChecked =info.getInt("IsStockChecked");
                                int isUserConfirmed=info.getInt("IsUserConfirmed");
                                Double montant=info.getDouble("Montant");
                                String devise_id=info.getString("DeviseId");
                                String observation=info.getString("Observation");
                                String addingAgenceId=info.getString("AddingAgenceId");
                                String addingUserId =info.getString("AddingUserId");
                                String checking_agence_id=info.getString("CheckingAgenceId");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                OperationFinance instance=new OperationFinance(id,agence_id,fournisseur_id,devise_id,categorie
                                        ,date,type,montant,observation,insert_mode,isCaisseCheched,isStockChecked,isUserConfirmed,
                                        addingUserId,addingAgenceId,checking_agence_id,adding_date,updated_date,sync_pos,pos);


                                if (MainActivity.getCurrentUser()!=null && (id.equals(MainActivity.DefaultFinancialKey) || agence_id.equals(MainActivity.getCurrentUser().getAgence_id()))){
                                    operation_financeRepository.ajout_sync(instance);
                                }


                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_operation_finances impossible ************************* "+e.toString());
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
                link=AddressFormatForGetConnectedAgence("operation_finance",LessMonth());
            }
            else
            {
                link=AddressFormatForGetConnectedAgence("operation_finance",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation operation_finances ********************"+e.toString());

        }


    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<OperationFinance> instances=new OperationFinanceRepository(OperationFinanceRepository.getContext()).get_for_post_sync();
                    for(OperationFinance instance:instances){

                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("Id", instance.getId());
                        jsonParam.put("AgenceId", instance.getAgence_id());
                        jsonParam.put("FournisseurId", instance.getFournisseur_id());
                        jsonParam.put("Date", instance.getDate());
                        jsonParam.put("Observation", MesOutils.replace_accents(instance.getObservation()));
                        jsonParam.put("Montant", String.valueOf(instance.getMontant()));
                        jsonParam.put("DeviseId", instance.getDevise_id());
                        jsonParam.put("IsCaisseChecked", String.valueOf(instance.getIs_caisse_checked()));
                        jsonParam.put("IsStockChecked", String.valueOf(instance.getIs_stock_checked()));
                        jsonParam.put("IsUserConfirmed", String.valueOf(instance.getIs_user_confirmed()));
                        jsonParam.put("InsertMode", instance.getInsert_mode());
                        jsonParam.put("Categorie", instance.getCategorie());
                        jsonParam.put("Type", instance.getType());
                        jsonParam.put("AddingAgenceId", instance.getAdding_agence_id());
                        jsonParam.put("AddingUserId", instance.getAdding_user_id());
                        jsonParam.put("CheckingAgenceId", instance.getChecking_agence_id());
                        jsonParam.put("AddingDate", instance.getAdding_date());
                        jsonParam.put("UpdatedDate", instance.getUpdated_date());
                        String sp=instance.getSync_pos()==0?"1":"4";
                        jsonParam.put("SyncPos",sp);
                        jsonParam.put("Pos", String.valueOf(instance.getPos()));

                        new WebService().makeRequest(AddressFormatForAdd("operation_finance"),jsonParam.toString());
                    }


                } catch (Exception e) {
                    e.toString();
                }
            }


        });

        thread.start();
    }
}
