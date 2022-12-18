package cd.sklservices.com.Beststockage.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.TableInfo;
import cd.sklservices.com.Beststockage.Dao.DaoTableInfo;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class TableInfoRepository {

    private DaoTableInfo daoTableInfo  ;
    private static TableInfoRepository instance=null;
    private static TableInfo table ;
    private static Context context;

    public TableInfoRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoTableInfo = mydata.daoTableInfo()  ;
    }
    public static final TableInfoRepository getInstance(Context context){

        if (context!=null){
            TableInfoRepository.context=context;}

        if (TableInfoRepository.instance==null){
            TableInfoRepository.instance=new TableInfoRepository(context);
        }
        return TableInfoRepository.instance;
    }

    public void intitialisation()
    {
        table = new TableInfo("approvisionnement", 32) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("operation", 32) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("livraison", 14) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("commande", 14) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("ligne_commande", 32) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("bonlivraison", 14) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("ligne_bonlivraison", 32) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("deleted", 32) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("depense", 32) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("quarter", 32) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("street", 32) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("address", 32) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

        table = new TableInfo("identity", 8) ;
        new InsertTableInfoAsyncTask(daoTableInfo).execute(table) ;

    }

    public class InsertTableInfoAsyncTask extends AsyncTask<TableInfo, Void, Void>
    {
        private DaoTableInfo daoTable ;

        private InsertTableInfoAsyncTask(DaoTableInfo dao)
        {
            this.daoTable = dao;
        }
        @Override
        protected Void doInBackground(TableInfo... table) {

            try{
                daoTable.insert(table[0]);
              }catch (Exception e){
                Log.d("Assert"," Erreur  Table Info" + e.toString());
            }

            return null ;
        }
    }

    public List<TableInfo> getByTableName_TableInfo2(String table_name){
        try{
            return  daoTableInfo.select_byTable_name_Table_info(table_name) ;
        }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
        }
        return  null;
    }


}
