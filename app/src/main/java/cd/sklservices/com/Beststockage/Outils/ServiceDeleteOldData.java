package cd.sklservices.com.Beststockage.Outils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.ViewModel.DepenseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationFinanceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationViewModel;

/**
 * Created by SKL on 29/10/2019.
 */

public class
ServiceDeleteOldData extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
            }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.d("Assert","  Synchronisation Get financial ..... ") ;
        restart_timer_delete_old_data();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ServiceSKL ","Synchronisation Arretée");

    }

    CountDownTimer countDownTimerDeleteOldData =  new CountDownTimer(10000, 1000) {
        public void onTick(long millisUntilFinished) {
            try {
                Log.d("Assert","countDownTimerDeleteOldData");

            } catch (Exception ex) {
                //Toast.makeText(getApplicationContext(), "Ezamaka ception 111  = " + ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish() {

            restart_timer_delete_old_data();
        }
    }.start();

    void restart_timer_delete_old_data(){
        countDownTimerDeleteOldData.start();
    }

}
