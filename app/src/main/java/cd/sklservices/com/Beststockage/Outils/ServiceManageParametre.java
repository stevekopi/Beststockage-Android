package cd.sklservices.com.Beststockage.Outils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import cd.sklservices.com.Beststockage.Cloud.Parametres.SyncArticleProduitFacture;
import cd.sklservices.com.Beststockage.Cloud.Parametres.SyncPaymentMode;
import cd.sklservices.com.Beststockage.Cloud.Parametres.SyncProduitFacture;
import cd.sklservices.com.Beststockage.Repository.Parametres.ArticleProduitFactureRepository;
import cd.sklservices.com.Beststockage.Repository.Parametres.PaymentModeRepository;
import cd.sklservices.com.Beststockage.Repository.Parametres.ProduitFactureRepository;

/**
 * Created by SKL on 29/10/2019.
 */

public class
ServiceManageParametre extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
            }


    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.d("Assert","  Synchronisation Get Stock ..... ") ;
        restart_timer_parametre();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ServiceSKL ","Synchronisation Arretée");

    }

    CountDownTimer countDownTimerstock =  new CountDownTimer(10000, 1000) {
        public void onTick(long millisUntilFinished) {
            try {
                Log.d("Assert","countDownTimerstock");

            } catch (Exception ex) {
                //Toast.makeText(getApplicationContext(), "Ezamaka ception 111  = " + ex.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFinish() {
            new SyncPaymentMode(new PaymentModeRepository(getApplication().getApplicationContext())).envoi();
            new SyncProduitFacture(new ProduitFactureRepository(getApplication().getApplicationContext())).envoi();
            new SyncArticleProduitFacture(new ArticleProduitFactureRepository(getApplication().getApplicationContext())).envoi();
            restart_timer_parametre();
        }
    }.start();

    void restart_timer_parametre(){
        countDownTimerstock.start();
    }


}
