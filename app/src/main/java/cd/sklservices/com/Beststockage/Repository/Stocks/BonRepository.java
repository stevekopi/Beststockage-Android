package cd.sklservices.com.Beststockage.Repository.Stocks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.Bon;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoBon;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class BonRepository {

    private DaoBon daobon ;
    private static BonRepository instance=null;
    private static Bon bon;
    private static Context context;
    private List<Bon> bonsArrayListe ;

    public BonRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daobon = mydata.daoBon() ;
    }

    public static final BonRepository getInstance(Context context){

        if (context!=null){
            BonRepository.context=context;}

        if (BonRepository.instance==null){
            BonRepository.instance=new BonRepository(context);
        }
        return BonRepository.instance;
    }


    public List<Bon> getBonLoading() {
        return daobon.get_Loading() ;
    }

    public void setBonsArrayListe(List<Bon> bonsArrayListe) {
        this.bonsArrayListe = bonsArrayListe;
    }


    public void ajout_sync(Bon instance)
    {
        Bon old = get(instance.getId()) ;
        if(old == null)
        {
            daobon.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daobon.update(instance) ;
            }
        }
    }



    public Bon get(String bon_id){

        try{

            return daobon.get(bon_id) ;


        }
        catch (Exception e){
            Log.d("Assert"," DaoBonLoc.get() "+e.toString());
        }
        return null;
    }

    public  void delete_data_old_agence(String Id){

        try{
            //daobon.delete_data_old_agence(Id);
        }
        catch (Exception e){      }

    }

    public List<Bon> getByProprietaireId(String proprietaire_id){

        try{
            return  daobon.select_by_proprietaire_id(proprietaire_id) ;
        }
        catch (Exception e){
            Log.d("Assert"," DaoBonLoc.getByProprietaireId() "+e.toString());
        }
        return null;
    }


    public List<Bon> getByPrivee(){

        try{
            return  daobon.select_bonPrivee() ;
        }
        catch (Exception e){
            Log.d("Assert"," DaoBonLoc.getByUserId() "+e.toString());
        }
        return null;
    }



    public AsyncTask delete_all()
    {
        daobon.delete_all();
        return null ;
    }

    public void gets(){

        try{

            List<Bon> mylist =
                    daobon.gets_order_by_date() ;
            instance.setBonsArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","DaoBonLoc.gets() "+e.toString());
        }
    }

    public void setBon(Bon bon){
        BonRepository.bon=bon;
    }

    public Bon getBon(){
        if (bon==null){return null;}
        else{return  bon ;}
    }


    public String getId(){
        if (bon==null){return null;}
        else{return  bon.getId();}
    }

    public String get_fournisseur_id(){
        if (bon==null){return null;}
        else{return  bon.getFournisseur_id();}
    }

    public String get_type(){
        if (bon==null){return null;}
        else{return  bon.getType();}
    }

    public String get_membership(){
        if (bon==null){return null;}
        else{return  bon.getMembership();}
    }


    public String get_date(){
        if (bon==null){return null;}
        else{return  bon.getDate();}
    }


    public static final Context getContext(){
        return context;
    }

}
