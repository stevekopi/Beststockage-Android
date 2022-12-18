package cd.sklservices.com.Beststockage.Outils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import cd.sklservices.com.Beststockage.Cloud.SyncApprovisionnement;
import cd.sklservices.com.Beststockage.Cloud.SyncBon;
import cd.sklservices.com.Beststockage.Cloud.SyncBonlivraison;
import cd.sklservices.com.Beststockage.Cloud.SyncLigneBonlivraison;
import cd.sklservices.com.Beststockage.Cloud.SyncLivraison;
import cd.sklservices.com.Beststockage.Cloud.SyncOperation;
import cd.sklservices.com.Beststockage.Repository.ApprovisionnementRepository;
import cd.sklservices.com.Beststockage.Repository.BonLivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.BonRepository;
import cd.sklservices.com.Beststockage.Repository.CommandeRepository;
import cd.sklservices.com.Beststockage.Repository.LigneBonLivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.LigneCommandeRepository;
import cd.sklservices.com.Beststockage.Repository.LivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.OperationRepository;

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
            new SyncOperation(new OperationRepository(getApplication().getApplicationContext())).envoi();
         //   new SyncCommande(new CommandeRepository(getBaseContext())).envoi();
          //  new SyncLigneCommande(new LigneCommandeRepository(getBaseContext())).envoi();
            new SyncOperation(new OperationRepository(getBaseContext())).envoi();
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
