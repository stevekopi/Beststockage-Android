package cd.sklservices.com.Beststockage.Cloud.Controles;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Controles.Controle;
import cd.sklservices.com.Beststockage.CloudObjects.WebService;
import cd.sklservices.com.Beststockage.Dao.DaoDist;
import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.AccesHTTP;
import cd.sklservices.com.Beststockage.Repository.Controles.ControleRepository;

/**
 * Created by SKL on 23/04/2019.
 */

public class SyncControle extends DaoDist implements AsyncResponse {

    private ControleRepository controleRepository;
    public SyncControle(ControleRepository repo) {
        this.controleRepository = repo ;
    }

    @Override
    public void processFinish(String output)
    {
        Thread adding_data=new Thread(){
            public void run (){
                try{

                    if (output.contains("AddingDate")){
                        try {
                            Log.d("Assert","Synchronisation controle "+output);
                            String data=output;
                            if(data.contains("ï»¿"))
                                data= data.replace("ï»¿","");
                            JSONArray instances=new JSONArray(data);
                            for (int k=0;k<instances.length();k++){
                                JSONObject info=new JSONObject(instances.get(k).toString());

                                String id=info.getString("Id");
                                String userId=info.getString("UserId");
                                String agenceId =info.getString("AgenceId");
                                String type =info.getString("Type");
                                String etat=info.getString("Etat");
                                String date=info.getString("Date");
                                String addingAgenceId=info.getString("AddingAgenceId");
                                String addingUserId =info.getString("AddingUserId");
                                String adding_date=info.getString("AddingDate");
                                String updated_date=info.getString("UpdatedDate");
                                int sync_pos=info.getInt("SyncPos");
                                int pos=info.getInt("Pos");

                                Controle instance=new Controle(id,userId,agenceId,date,type,etat,addingUserId,addingAgenceId,adding_date,updated_date,
                                        sync_pos,pos);


                                if (MainActivity.getCurrentUser()!=null && agenceId.equals(MainActivity.getCurrentUser().getAgence_id())){
                                    controleRepository.ajout_sync(instance);
                                }


                            }
                        } catch (JSONException e) {
                            Log.d("Assert"," Conversion JSONArray get_controles impossible ************************* "+e.toString());
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
                link=AddressFormatForGetConnectedAgence("controle",LessMonth());
            }
            else
            {
                link=AddressFormatForGetConnectedAgence("controle",LessDay());
            }

            accesDonnees.execute(link);
        }
        catch (Exception e){
            Log.d("Assert","Synchronisation controles ********************"+e.toString());

        }


    }

    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<Controle> instances=new ControleRepository(ControleRepository.getContext()).get_for_post_sync();
                    for(Controle instance:instances){

                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("Id", instance.getId());
                        jsonParam.put("UserId", instance.getUser_id());
                        jsonParam.put("AgenceId", instance.getAgence_id());
                        jsonParam.put("Type", instance.getType());
                        jsonParam.put("Etat", instance.getEtat());
                        jsonParam.put("Date", instance.getDate());
                        jsonParam.put("AddingAgenceId", instance.getAdding_agence_id());
                        jsonParam.put("AddingUserId", instance.getAdding_user_id());
                        jsonParam.put("AddingDate", instance.getAdding_date());
                        jsonParam.put("UpdatedDate", instance.getUpdated_date());
                        String sp=instance.getSync_pos()==0?"1":"4";
                        jsonParam.put("SyncPos",sp);
                        jsonParam.put("Pos", String.valueOf(instance.getPos()));

                        if(!instance.getDate().equals("0000-00-00"))
                            new WebService().makeRequest(AddressFormatForAdd("controle"),jsonParam.toString());
                    }


                } catch (Exception e) {
                    e.toString();
                }
            }


        });

        thread.start();
    }
}
