package cd.sklservices.com.Beststockage.Repository;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.TableKeyIncrementor;
import cd.sklservices.com.Beststockage.Dao.DaoTableKeyIncrementor;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class TableKeyIncrementorRepository {

    private DaoTableKeyIncrementor daoTable ;
    private static TableKeyIncrementorRepository instance=null;
    private static TableKeyIncrementor table;
    private static Context context;
    private List<TableKeyIncrementor> tableArrayListe ;

    public TableKeyIncrementorRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoTable = mydata.daoTableKeyIncrementor();
    }

    public static final TableKeyIncrementorRepository getInstance(Context context){

        if (context!=null){
            TableKeyIncrementorRepository.context=context;}

        if (TableKeyIncrementorRepository.instance==null){
            TableKeyIncrementorRepository.instance=new TableKeyIncrementorRepository(context);
        }
        return TableKeyIncrementorRepository.instance;
    }


    public void ajout_sync(TableKeyIncrementor instance)
    {
        TableKeyIncrementor old = get(instance.getTable_name()) ;
        if(old == null)
        {
            daoTable.insert(instance);
        }
        else
        {
            if (instance.getPosition()>old.getPosition() || instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoTable.update(instance) ;
            }
        }
    }


    public TableKeyIncrementor get(String Table){

        try{
            return  daoTable.get(MainActivity.getCurrentUser().getAgence_id(),Table) ;
        }
        catch (Exception e){
            Log.d("Assert","TableKeyIncrementorRepository - get error : "+e.toString());
        }
        return null ;
    }

    public int getPosition(String Table){

        try{
            return  daoTable.getPosition(MainActivity.getCurrentUser().getAgence_id(),Table) ;
        }
        catch (Exception e){
            Log.d("Assert","TableKeyIncrementorRepository - get error : "+e.toString());
        }
        return 0;
    }




    public static final Context getContext(){
        return context;
    }


}
