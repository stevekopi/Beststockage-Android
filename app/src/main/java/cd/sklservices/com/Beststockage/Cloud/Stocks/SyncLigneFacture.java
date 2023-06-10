package cd.sklservices.com.Beststockage.Cloud.Stocks;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.CloudObjects.WebService;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.Repository.Stocks.LigneFactureRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncLigneFacture extends DaoDist implements AsyncResponse {

    private LigneFactureRepository ligneFactureRepository;
    public SyncLigneFacture(LigneFactureRepository repo) {
        this.ligneFactureRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation ligneFacture "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());
                                String id=info.getString("Id");
                                String SecondId=info.getString("SecondId");
                                String FactureId =info.getString("FactureId");
                                String Appartenance=info.getString("Appartenance");
                                String ArticleProduitFactureId=info.getString("ArticleProduitFactureId");
                                String OperationFinanceId=info.getString("OperationFinanceId");
                                String SensStock=info.getString("SensStock");
                                int Quantite=info.getInt("Quantite");
                                Double MontantHt=info.getDouble("MontantHt");
                                Double MontantTtc =info.getDouble("MontantTtc");
                                Double ConvertedAmount=info.getDouble("ConvertedAmount");
                                Double MontantLocal=info.getDouble("MontantLocal");
                                Double Reduction=info.getDouble("Reduction");
                                Double Tva=info.getDouble("Tva");
                                Double MontantNet=info.getDouble("MontantNet");
                                String ArticleBonusId=info.getString("ArticleBonusId");
                                int Bonus =info.getInt("Bonus");
                                int IsChecked=info.getInt("IsChecked");
                                int IsConfirmed=info.getInt("IsConfirmed");
                                String CheckingAgenceId =info.getString("CheckingAgenceId");
                                String aa_id=info.getString("AddingAgenceId");
                                String au_id=info.getString("AddingUserId");
                                String luu_id=info.getString("LastUpdateUserId");
                                String ad=info.getString("AddingDate");
                                String ud=info.getString("UpdatedDate");
                                int sp=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                String VerifAgenceId=info.getString("VerifAgenceId");
                                String VerifAgenceId2=info.getString("VerifAgenceId2");

                                LigneFacture ligneFacture=new LigneFacture(id,SecondId,FactureId,Appartenance,ArticleProduitFactureId,OperationFinanceId,SensStock,
                                        Quantite,MontantHt,MontantTtc,ConvertedAmount,MontantLocal,Reduction,Tva,MontantNet,ArticleBonusId,Bonus,IsChecked,IsConfirmed,CheckingAgenceId,
                                        au_id,luu_id,aa_id,ad,ud,sp,pos);


                                if (MainActivity.getCurrentUser()!=null &&  ( VerifAgenceId.equals(MainActivity.getCurrentUser().getAgence_id()) || VerifAgenceId2.equals(MainActivity.getCurrentUser().getAgence_id()))){
                                    ligneFactureRepository.ajout_sync(ligneFacture);
                                }


                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_ligneFactures impossible ************************* "+e.toString());
                        }

                    }
                }
                catch (
                        Exception ex){ex.printStackTrace();}
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
                link=AddressFormatForGetConnectedAgence("ligne_facture",LessMonth());
            }
            else
            {
                link=AddressFormatForGetConnectedAgence("ligne_facture",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation ligneFactures ********************"+e.toString());

        }

    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<LigneFacture> instances=new LigneFactureRepository(LigneFactureRepository.getContext()).getLigneFactureSynchro();
                    for(LigneFacture instance:instances){
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("Id", instance.getId());
                        jsonParam.put("SecondId", instance.getSecond_id());
                        jsonParam.put("FactureId", instance.getFacture_id());
                        jsonParam.put("Appartenance", instance.getAppartenance());
                        jsonParam.put("ArticleProduitFactureId", instance.getArticle_produit_facture_id());
                        jsonParam.put("OperationFinanceId", instance.getOperation_finance_id());
                        jsonParam.put("SensStock", instance.getSens_stock());
                        jsonParam.put("Quantite", instance.getQuantite());
                        jsonParam.put("MontantHt", instance.getMontant_ht());
                        jsonParam.put("ConvertedAmount", instance.getConverted_amount());
                        jsonParam.put("MontantLocal", instance.getMontant_local());
                        jsonParam.put("Reduction", instance.getReduction());
                        jsonParam.put("Tva", instance.getTva());
                        jsonParam.put("MontantTtc", instance.getMontant_ttc());
                        jsonParam.put("MontantNet", instance.getMontant_net());
                        jsonParam.put("ArticleBonusId", instance.getArticle_bonus_id());
                        jsonParam.put("Bonus", instance.getBonus());
                        jsonParam.put("IsChecked", instance.getIs_checked());
                        jsonParam.put("IsConfirmed", instance.getIs_confirmed());
                        jsonParam.put("AddingAgenceId", instance.getAdding_agence_id());
                        jsonParam.put("CheckingAgenceId", instance.getChecking_agence_id());
                        jsonParam.put("AddingUserId", instance.getAdding_user_id());
                        jsonParam.put("LastUpdateUserId", instance.getLast_update_user_id());
                        jsonParam.put("AddingDate", instance.getAdding_date());
                        jsonParam.put("UpdatedDate", instance.getUpdated_date());
                        String sp=instance.getSync_pos()==0?"1":"4";
                        jsonParam.put("SyncPos",sp);
                        jsonParam.put("Pos", String.valueOf(instance.getPos()));

                        new WebService().makeRequest(AddressFormatForAdd("ligne_facture"),jsonParam.toString());
                    }

                } catch (Exception e) {
                    e.toString();
                }
            }
        });

        thread.start();
    }
}
