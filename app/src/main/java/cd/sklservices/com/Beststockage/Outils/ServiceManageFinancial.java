package cd.sklservices.com.Beststockage.Outils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.User;
import cd.sklservices.com.Beststockage.Cloud.SyncDepense;
import cd.sklservices.com.Beststockage.Cloud.SyncOperationFinance;
import cd.sklservices.com.Beststockage.Repository.DepenseRepository;
import cd.sklservices.com.Beststockage.Repository.OperationFinanceRepository;

/**
 * Created by SKL on 29/10/2019.
 */

public class
ServiceManageFinancial extends Service {
    public  static User current_user;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
            }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.d("Assert","  Synchronisation Get financial ..... ") ;
        if(MainActivity.getCurrentUser()!=null){
            current_user=MainActivity.getCurrentUser();
        }
        restart_timer_financial();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ServiceSKL ","Synchronisation Arretée");

    }

    CountDownTimer countDownTimerfinancial =  new CountDownTimer(10000, 1000) {
        public void onTick(long millisUntilFinished) {
            try {
                Log.d("Assert","countDownTimerfinancial");

            } catch (Exception ex) {
                //Toast.makeText(getApplicationContext(), "Ezamaka ception 111  = " + ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish() {

            new SyncOperationFinance(new OperationFinanceRepository(getBaseContext())).sendPost();
            new SyncDepense(new DepenseRepository(getBaseContext())).sendPost();

            new SyncOperationFinance(new OperationFinanceRepository(getBaseContext())).envoi();
            new SyncDepense(new DepenseRepository(getBaseContext())).envoi();
            restart_timer_financial();
        }
    }.start();

    public static User getCurrentUser(){
        return current_user;
    }
    void restart_timer_financial(){
        countDownTimerfinancial.start();
    }

}
