package cd.sklservices.com.Beststockage.Repository.Finances;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Finances.Depense;
import cd.sklservices.com.Beststockage.Dao.Finances.DaoDepense;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

public class DepenseRepository {

    private DaoDepense depensedao ;

    private static DepenseRepository instance = null;
    private static Context context;
    private List<Depense> depenseArrayListe ;

    public DepenseRepository(Context application)
    {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        depensedao = mydata.daoDepense() ;
    }

    public static final DepenseRepository getInstance(Application context){

        if (context!=null){
            DepenseRepository.context=context;}

        if (DepenseRepository.instance==null){
            DepenseRepository.instance=new DepenseRepository(context);
        }
        return DepenseRepository.instance;
    }

    public void ajout(Depense instance)
    {
        depensedao.insert(instance);
    }

    public void ajout_sync(Depense instance)
    {
        Depense old = get(instance.getId(),false) ;
        if(old == null)
        {
            depensedao.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                depensedao.update(instance) ;
            }
        }
    }

    public void update(Depense instance)
    {
        depensedao.update(instance) ;
    }

    public Depense get(String id,boolean withOperationFinance){

        Depense instance=null;
        try {
            instance= depensedao.get(id);
            if(withOperationFinance)
                instance.setOperationFinance(new OperationFinanceRepository(OperationFinanceRepository.getContext()).get(instance.getOperation_finance_id(),true,true));
        }
        catch (Exception e){
            Log.d("Assert","DaoAdresseLoc.get "+e.toString());
        }

        return instance;
    }

    public List<Depense> getByAgence(String id){

        try {
            return depensedao.select_byid_agence(id);
        }
        catch (Exception e){
            Log.d("Assert","DaoAdresseLoc.get "+e.toString());
        }

        return null;
    }

    public  void delete_data_old_agence(String Id){

        try{
            depensedao.delete_data_old_agence(Id);
        }
        catch (Exception e){      }

    }

    public AsyncTask delete_all()
    {
        depensedao.delete_all();
        return null ;
    }

    public int count_distinct_date()
    {
        return depensedao.count_distinct_date();
    }

    public int count()
    {
        return depensedao.count();
    }

    public List<String> getDistinctPeriode(){
        try{
            return   depensedao.select_date_from_depense() ;
        }
        catch (Exception e){
            Log.d("Assert","Erreur: getDistinctPeriode "+e.toString());
            return  null;
        }

    }

    public  List<Depense> getByPeriode(String periode){


        try{
            String agence_id = this.id_current_agence() ;
            return  depensedao.select_bydate_depense(agence_id, periode) ;

        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
        }
        return null;

    }

    public Boolean test_if_not_sync_exist(String date){
        try{
            Depense instance = depensedao.select_bydate_not_sync(MainActivity.getCurrentUser().getAgence_id(),date) ;
            return instance==null?false:true;
        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
        }
        return  null;
    }


    public List<Depense> get(){

        try{

            return  depensedao.select_depense() ;
        }
        catch (Exception e){
            Log.d("Assert"," Dao Bon de livraison  "+e.toString());
        }
        return null;
    }

    public List<Depense> get_for_post_sync(){

        try{
            return  depensedao.get_for_post_sync();

        }
        catch (Exception e){
            Log.d("Assert"," DaoFournisseur  .get() "+e.toString());
        }
        return null;
    }

    public void delete(Depense instance)
    {
        instance.setSync_pos(3);
        instance.setUpdated_date(MainActivity.getAddingDate());
        instance.setPos(instance.getPos()+1);
        depensedao.update(instance);
    }


    public String id_current_agence()
    {
       return ((MainActivity)getContext()).getCurrentUser().getAgence_id().toString() ;
    }

    public static final Context getContext(){
        return context;
    }

    public List<String>  getLoading2(String index) {
        return depensedao.select_date_from_depense2 (index) ;
    }

    public double  get_amount_by_date_and_agence_id(String date,String agence_id) {
        return depensedao.get_amount_by_date_and_agence_id (date,agence_id) ;
    }

}
