package cd.sklservices.com.Beststockage.Repository.Stocks;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.Classes.Stocks.Stock;
import cd.sklservices.com.Beststockage.Dao.Registres.DaoAgence;
import cd.sklservices.com.Beststockage.Dao.Registres.DaoArticle;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoOperation;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class StockRepository {

    private DaoOperation daoOperation  ;
    private DaoArticle daoArticle  ;
    private DaoAgence daoAgence  ;
    private static StockRepository instance=null;
    private static Stock stock;
    private static Context context;
    private List<Stock> stockArrayListe ;

    public StockRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoOperation = mydata.daoOperation() ;
        daoAgence = mydata.daoAgence() ;
        daoArticle = mydata.daoArticle() ;
    }

    public static final StockRepository getInstance(Context context){

        if (context!=null){
            StockRepository.context=context;}

        if (StockRepository.instance==null){
            StockRepository.instance=new StockRepository(context);
        }
        return StockRepository.instance;
    }

    public int Qte(String agence_id, String article_id)
    {
        try{
            int qt_operation=new OperationRepository(MainActivity.application).quantite_stock(agence_id,article_id);
            int qt_facture=new LigneFactureRepository(MainActivity.application).quantite_stock(agence_id,article_id);
            return qt_operation + qt_facture;
        }
        catch (Exception e){
            return 0;
        }
    }


    public Boolean test_if_operation_attente(String Id_agence, String Id_article)
    {
        Boolean test = false ;

        List<Operation> mylist = daoOperation.select_byAgencebyArticle_operation(Id_agence, Id_article) ;
        for(Operation op:mylist)
        {
            if(op.getSync_pos() == 0)
            {
                test = true ;
            }
        }

        return test ;
    }



}
