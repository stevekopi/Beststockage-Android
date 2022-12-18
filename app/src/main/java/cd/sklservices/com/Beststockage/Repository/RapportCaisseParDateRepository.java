package cd.sklservices.com.Beststockage.Repository;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.RapportCaisseParDate;
import cd.sklservices.com.Beststockage.Dao.DaoRapportCaisseParDate;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 12/02/2021.
 */

public class RapportCaisseParDateRepository {

    private DaoRapportCaisseParDate daoRapportCaisseParDate  ;
    private static RapportCaisseParDateRepository instance=null;
    private static RapportCaisseParDate rapport;
    private static Context context;
    private List<RapportCaisseParDate> rapportArrayListe ;

    public RapportCaisseParDateRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoRapportCaisseParDate = mydata.daoRapportCaisseParDate() ;
    }



    public ArrayList<RapportCaisseParDate> getCumulByDate(){
        try{

            ArrayList arrayList=new ArrayList();

            Log.d("Assert"," Enter  Rapport Date !!!! ");
            List<RapportCaisseParDate> mylist = daoRapportCaisseParDate.select_rapport_caisse1() ;

            for (RapportCaisseParDate rap : mylist){

                    double montantBrut= rap.getMontantBrut();
                    double depense= rap.getDepense() ;
                    String dates = rap.getDate() ;
                    RapportCaisseParDate rapportCaisseParDate=new RapportCaisseParDate(dates,montantBrut,depense);
                    arrayList.add(rapportCaisseParDate);
                    Log.d("Assert"," Boucle   Rapport Date !!!! " + dates);
                }

            Log.d("Assert"," Taille  Rapport Date !!!! " + arrayList.size());
            return arrayList;

        }
        catch (Exception e){
            Log.d("Assert","DaoRapportsParDateLoc.getByDate(): Erreur: "+e.toString());
            return  null;
        }

    }

    public RapportCaisseParDate getLast(){
        try{
            ArrayList<RapportCaisseParDate> data=this.getCumulByDate();
            RapportCaisseParDate instance = data.get(0);
            return  instance;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<RapportCaisseParDate> getsByDate(String date){
        try{
            RapportCaisseParDate rapportCaisseParDate=null;
            ArrayList arrayList=new ArrayList();

            List<RapportCaisseParDate> mylist = daoRapportCaisseParDate.select_rapport_caisse2(date) ;

            for (RapportCaisseParDate rap : mylist){
                    String agence_id= rap.getAgence_Id();
                    double montantBrut= rap.getMontantBrut();
                    double depense= rap.getDepense();

                    rapportCaisseParDate=new RapportCaisseParDate(date,montantBrut,depense,agence_id);
                    arrayList.add(rapportCaisseParDate);
                }

            return arrayList;

        }
        catch (Exception e){
            Log.d("Assert","DaoRapportsParDateLoc.getByDate(): Erreur: "+e.toString());
        }
        return  null;
    }

}
