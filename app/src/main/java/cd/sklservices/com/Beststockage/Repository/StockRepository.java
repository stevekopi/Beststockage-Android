package cd.sklservices.com.Beststockage.Repository;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Outils.*;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Dao.*;

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


    public ArrayList<Agence> getDistinctAgence(){
        try{


            ArrayList stockAgenceArrayListe=new ArrayList();

            List<Agence> mylist =  daoAgence.select_agence() ;

            for (Agence ag : mylist){
                stockAgenceArrayListe.add(ag);
            }

            return stockAgenceArrayListe;
         }
        catch (Exception e){
            Log.d("Assert","Erreur: "+e.toString());
        }
        return  null;
    }

    public int Qte(String agence_id, String article_id)
    {
        try{
            int qa=daoOperation.quantite_add(agence_id, article_id);
            int ql=daoOperation.quantite_less(agence_id, article_id);
            return qa - ql;
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
