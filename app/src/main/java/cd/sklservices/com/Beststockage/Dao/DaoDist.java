
package cd.sklservices.com.Beststockage.Dao;

import static cd.sklservices.com.Beststockage.Outils.MesOutils.converDateToStringForMySql;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Outils.ServiceManageFinancial;

/**
 * Created by Steve Kopi Loseme on 16/02/2021.
 */

public class DaoDist {


     protected static final String ADRESSE = "https://fatimefresh.online/api/android/" ;
    // protected static final String ADRESSE = "http://192.168.137.1:88/projects/api.fatimefresh.com/android/";

    protected static String AddressFormatForGet(String tableName, Date lastSyncDate)
    {
        return ADRESSE + "get/" + tableName.trim() + ".php?last_sync=" + converDateToStringForMySql(lastSyncDate);
    }

    protected static String AddressFormatForGetConnectedAgence(String tableName, Date lastSyncDate)
    {
        String aid="";
        if(MainActivity.getCurrentUser()!=null){
            aid=MainActivity.getCurrentUser().getAgence_id();
        }
        else if(ServiceManageFinancial.getCurrentUser()!=null){
            aid=MainActivity.getCurrentUser().getAgence_id();
        }

        Log.d("Assert","AddressFormat : Last Agence Id : "+ aid);
        if(aid!=""){
            return ADRESSE + "get_lite/" + tableName.trim() + ".php?agence_id="+ aid +"&last_sync=" + converDateToStringForMySql(lastSyncDate);
        }
        return "Error";
    }

    protected static final String AddressFormatForAdd(String tableName)
    {
         return ADRESSE  + "post/"+tableName+".php";
    }

    protected static final Date LessMonth(){
       Date date=new Date();
       Calendar c=Calendar.getInstance();
       c.setTime(date);
       c.add(Calendar.MONTH,-36);
       date=c.getTime();
       return date;
    }

   protected static final Date LessDay(){
      Date date=new Date();
      Calendar c=Calendar.getInstance();
      c.setTime(date);
      c.add(Calendar.DATE,-95);
      date=c.getTime();
      return date;
   }

}
