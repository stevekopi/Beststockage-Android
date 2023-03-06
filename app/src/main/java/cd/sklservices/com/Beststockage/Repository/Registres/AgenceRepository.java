package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoAgence;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class AgenceRepository {

    private DaoAgence daoagence ;
    private static AgenceRepository instance=null;
    private static Agence agence;
    private static Context context;
    private List<Agence> agencesArrayListe ;

    public AgenceRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoagence = mydata.daoAgence() ;
    }

    public static final AgenceRepository getInstance(Context context){

        if (context!=null){
            AgenceRepository.context=context;}

        if (AgenceRepository.instance==null){
            AgenceRepository.instance=new AgenceRepository(context);
        }
        return AgenceRepository.instance;
    }

    public List<Agence> getAgencesArrayListe() {
        return daoagence.gets() ;
    }

    public List<Agence> getAgenceLoading2(String  value) {
        try{

            return daoagence.select_agenceLoading2(value);

        }
        catch (Exception e){

        }
        return null;

    }

    public List<Agence> getAgenceLoading() {
        try{
           return daoagence.select_agenceLoading();

        }
        catch (Exception e){

        }
        return null;
    }

    public List<Agence> getAgenceLoading(String id) {
        try{

            return daoagence.select_agenceLoading (id);

        }
        catch (Exception e){

        }
        return null;
    }

    public List<Agence> getAgenceByDenomination(String denomination) {
        return daoagence.select_byDenomination(denomination) ;
    }

    public void setAgencesArrayListe(List<Agence> agencesArrayListe) {
        this.agencesArrayListe = agencesArrayListe;
    }


    public AsyncTask ajout_sync(Agence instance)
    {
        Agence old = get(instance.getId(),false,false) ;
        if(old == null)
        {
            daoagence.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoagence.update(instance) ;
            }
        }
        return null ;
    }


    public Agence get(String agence_id,boolean withAddress,boolean withProprietaire){
        Agence instance =daoagence.get(agence_id) ;
        try{
            if(withAddress)
                instance.setAddress(new AddressRepository(AddressRepository.getContext()).get(instance.getAdresse_id(),true));

            if(withProprietaire)
                instance.setProprietaire(new IdentityRepository(AddressRepository.getContext()).get(instance.getProprietaire_id(),false));

        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
        return instance;
    }


    public List<Agence> getByUserId(String user_id){

        try{
             return  daoagence.select_byid_user_agence(user_id) ;
        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.getByUserId() "+e.toString());
        }
        return null;
    }

    public List<Agence> getByCustomer(){

        try{
            return  daoagence.select_agenceCustumer() ;
        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.getByUserId() "+e.toString());
        }
        return null;
    }

    public List<Agence> getByPrivee(){

        try{
            return  daoagence.select_agencePrivee() ;
        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.getByUserId() "+e.toString());
        }
        return null;
    }

    public List<Agence> getByAppartenanceProprietaire(String appartenance, String proprietaire){

        try{
            return  daoagence.select_agenceAppartenanceProprietaire(appartenance, proprietaire) ;
        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.getByUserId() "+e.toString());
        }
        return null;
    }

    public AsyncTask delete_all()
    {
        daoagence.delete_all();
        return null ;
    }

    public void gets(){

        try{

            List<Agence> mylist =
                    daoagence.gets() ;
            instance.setAgencesArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","DaoAgenceLoc.gets() "+e.toString());
        }
    }

    public void setAgence(Agence agence){
        AgenceRepository.agence=agence;
    }

    public Agence getAgence(){
        if (agence==null){return null;}
        else{
             return  get(agence.getId(),true,false) ;}
    }


    public String getId(){
        if (agence==null){return null;}
        else{return  agence.getId();}
    }

    public String get_adresse_id(){
        if (agence==null){return null;}
        else{return  agence.getAdresse_id();}
    }

    public String get_type(){
        if (agence==null){return null;}
        else{return  agence.getType();}
    }

    public String get_denomination(){
        if (agence==null){return null;}
        else{return  agence.getDenomination();}
    }


    public String get_telephone(){
        if (agence==null){return null;}
        else{return  agence.getTel();}
    }




    public static final Context getContext(){
        return context;
    }

    public int count(){
        return daoagence.count();
    }

}
