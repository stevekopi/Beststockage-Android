package cd.sklservices.com.Beststockage.Outils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncApprovisionnement;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncBon;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncBonlivraison;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncFacture;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncLigneBonlivraison;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncLigneFacture;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncLivraison;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncOperation;
import cd.sklservices.com.Beststockage.Repository.Stocks.ApprovisionnementRepository;
import cd.sklservices.com.Beststockage.Repository.Stocks.BonLivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.Stocks.BonRepository;
import cd.sklservices.com.Beststockage.Repository.Stocks.FactureRepository;
import cd.sklservices.com.Beststockage.Repository.Stocks.LigneBonLivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.Stocks.LigneFactureRepository;
import cd.sklservices.com.Beststockage.Repository.Stocks.LivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.Stocks.OperationRepository;

/**
 * Created by SKL on 29/10/2019.
 */

public class
ServiceManageStock extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
            }


    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.d("Assert","  Synchronisation Get Stock ..... ") ;
        restart_timer_stock();
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
            new SyncOperation(new OperationRepository(getBaseContext())).envoi();
            new SyncFacture(new FactureRepository(getBaseContext())).envoi();
            new SyncLigneFacture(new LigneFactureRepository(getBaseContext())).envoi();
            new SyncBon(new BonRepository(getBaseContext())).envoi();
            new SyncBonlivraison(new BonLivraisonRepository(getBaseContext())).envoi();
            new SyncLigneBonlivraison(new LigneBonLivraisonRepository(getBaseContext())).envoi();
            new SyncLivraison(new LivraisonRepository(getBaseContext())).envoi();
            new SyncApprovisionnement(new ApprovisionnementRepository(getBaseContext())).envoi();
            restart_timer_stock();
        }
    }.start();

    void restart_timer_stock(){
        countDownTimerstock.start();
    }


}
