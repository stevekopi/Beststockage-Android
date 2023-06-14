package cd.sklservices.com.Beststockage.Repository.Stocks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoFacture;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLigneFacture;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 02/02/2021.
 */

public class FactureRepository {

    private DaoFacture daofacture  ;
    private DaoLigneFacture daoLigneFacture ;
    private static FactureRepository instance=null;
    private static Facture facture;
    private static Context context;
    private List<Facture> factureArrayList;

    public FactureRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daofacture = mydata.daoFacture() ;
        daoLigneFacture = mydata.daoLigneFacture() ;
    }

    public static final FactureRepository getInstance(Context context){

        if (context!=null){
            FactureRepository.context=context;}

        if (FactureRepository.instance==null){
            FactureRepository.instance=new FactureRepository(context);
        }
        return FactureRepository.instance;
    }

    public List<Facture> getFactureArrayListe() {
        return factureArrayList;
    }

    public void setFactureArrayList(List<Facture> bonlivraisonArrayList) {
        this.factureArrayList = bonlivraisonArrayList;
    }

    public Facture get(String id,boolean withAmount){
            Facture instance=null;
        try{
             instance=  daofacture.get(id) ;
             if (withAmount)
             {
                 instance.setMontant_ht(new LigneFactureRepository(getContext()).get_montant_ht_facture(id));
                 instance.setMontant_net(new LigneFactureRepository(getContext()).get_montant_net_facture(id));
                 instance.setMontant_ttc(new LigneFactureRepository(getContext()).get_montant_ttc_facture(id));
                 instance.setMontant_local(new LigneFactureRepository(getContext()).get_montant_local_facture(id));
             }
        }
        catch (Exception e){
            Log.d("Assert"," DaoFacture .get() "+e.toString());
        }
        return instance;
    }

    public List<Facture> getLoading() {
        return daofacture.get_Loading() ;
    }

    public void gets(){

        try{

            List<Facture> mylist =
                    daofacture.gets_order_by_date() ;
            instance.setFactureArrayList(mylist);

        }
        catch (Exception e){
            Log.d("Assert","DaoBonLoc.gets() "+e.toString());
        }
    }

    public ArrayList<Facture> getDistinct(){
        try{

            ArrayList arrayListe=new ArrayList();
            List<Facture> mylist =  daofacture.gets_order_by_date();

            for (Facture c : mylist){

                    arrayListe.add(get(c.getId(),true));
                }
            return arrayListe;

        }
        catch (Exception e){
            Log.d("Assert","DaoFactureLoc().getDistinct : "+e.toString());
            return  null;

        }
    }

    public List<Facture> getList(){
        try{

            return  daofacture.gets_order_by_date() ;
          }
        catch (Exception e){
            Log.d("Assert","DaoFactureLoc().getList : "+e.toString());
            return  null;

        }
    }

    public List<Facture> getsByDate(String date){
        try{
            return daofacture.getsByDate(date, MainActivity.getCurrentUser().getAgence().getAppartenance());
        }
        catch (Exception e){
            Log.d("Assert","FactureRepository.getByDate(): "+e.toString());
            return  null;
        }
    }


    public int add_sync(Facture instance)
    {
        try{
            Facture old = get(instance.getId(),false) ;
            if(old == null)
            {
                instance.setDevise_local_id(instance.getDevise_id());
                instance.setConvert_devise_id(instance.getDevise_id());

                daofacture.insert(instance);
                return  1;
            }
            else
            {
                if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
                {
                    daofacture.update(instance) ;
                    return  1;
                }
            }

        }
        catch (Exception e){

            Log.d("Assert","DaoFactureLoc.getQuantiteFacturer() : "+e.toString());
            return  0;
        }
        return  0;
    }

    public void ajout_sync(Facture instance)
    {
        try{
            Facture old = get(instance.getId(),false) ;
            if(old == null)
            {
                daofacture.insert(instance);
            }
            else
            {
                if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
                {
                    daofacture.update(instance) ;
                }
            }

        }
        catch (Exception e){

            Log.d("Assert","DaoFactureLoc.getQuantiteFacturer() : "+e.toString());
        }

    }


    public Facture getLast(){
        try{
            ArrayList<Facture> data=this.getDistinct();
            Facture instance=data.get(0);
            return  instance;
        }
        catch (Exception e){
            return null;
        }
    }

    public int getSommeQuantiteFacturer(String IdFacture){

        try{
            int val=10000;
            List<String> mylist =  daoLigneFacture.select_sum_quantite_ligne_facture(IdFacture);

            for (String l : mylist){
                    val= Integer.valueOf(l) ;
            }

            return val;

        }
        catch (Exception e){

            Log.d("Assert","DaoFactureLoc.getQuantiteFacturer() : "+e.toString());
            return 10000;
        }
    }




    public List<Facture> getFactureSynchro(){
        try{

            return  daofacture.select_facture_bysend();
        }
        catch (Exception e){
            Log.d("Assert","DaoFactureLoc().getDistinct : "+e.toString());
            return  null;

        }
    }

    public List<String> getDistinctDates(){

        try{

            return   daofacture.gets_distinct_dates();

        }
        catch (Exception e){
            Log.d("Assert","DFactureRepository.getDistinctDatess : "+e.toString());
            return null;
        }
    }

    public List<Facture> select_facture(){
        try{

            return  daofacture.select_facture() ;
        }
        catch (Exception e){
            Log.d("Assert","DaoFactureLoc().getDistinct : "+e.toString());
            return  null;

        }
    }

    public AsyncTask delete2(String Id){
        try{
            daofacture.delete2(Id);
        }
        catch (Exception e){ }
        return null ;
    }

    public AsyncTask delete_all()
    {
        daofacture.delete_all();
        return null ;
    }

    public void delete(Facture com)
    {
        new DeleteFacturesyncTask(daofacture).execute(com) ;
    }

    public class DeleteFacturesyncTask extends AsyncTask<Facture, Void, Void>
    {
        private DaoFacture daoC ;

        private DeleteFacturesyncTask(DaoFacture daoO)
        {
            this.daoC = daoO ;
        }
        @Override
        protected Void doInBackground(Facture... com) {

            try{

                daoC.delete(com[0]) ;

            }catch (Exception e){
                Log.d("Assert"," Erreur Update Facture  " + e.toString());
            }

            return null ;
        }
    }


    public void setFacture(Facture facture){
        FactureRepository.facture =facture;
    }


    public String getDateFacture(){
        if (facture ==null){return null;}
        else{return  facture.getDate();}
    }

    public String getId(){
        if (facture ==null){return null;}
        else{return  facture.getId();}
    }

    public static final Context getContext(){
        return context;
    }


    public Facture getFacture(){
        if (facture==null){return null;}
        else{return  facture;}
    }


}
