package cd.sklservices.com.Beststockage.Outils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.User;
import cd.sklservices.com.Beststockage.Cloud.SyncAddress;
import cd.sklservices.com.Beststockage.Cloud.SyncAgence;
import cd.sklservices.com.Beststockage.Cloud.SyncArticle;
import cd.sklservices.com.Beststockage.Cloud.SyncCategorie;
import cd.sklservices.com.Beststockage.Cloud.SyncClient;
import cd.sklservices.com.Beststockage.Cloud.SyncContenance;
import cd.sklservices.com.Beststockage.Cloud.SyncConvoyeur;
import cd.sklservices.com.Beststockage.Cloud.SyncDelegue;
import cd.sklservices.com.Beststockage.Cloud.SyncDevise;
import cd.sklservices.com.Beststockage.Cloud.SyncDistrict;
import cd.sklservices.com.Beststockage.Cloud.SyncDriver;
import cd.sklservices.com.Beststockage.Cloud.SyncFournisseur;
import cd.sklservices.com.Beststockage.Cloud.SyncHuman;
import cd.sklservices.com.Beststockage.Cloud.SyncIdentity;
import cd.sklservices.com.Beststockage.Cloud.SyncQuarter;
import cd.sklservices.com.Beststockage.Cloud.SyncStreet;
import cd.sklservices.com.Beststockage.Cloud.SyncTableKeyIncrementor;
import cd.sklservices.com.Beststockage.Cloud.SyncTown;
import cd.sklservices.com.Beststockage.Cloud.SyncTownship;
import cd.sklservices.com.Beststockage.Cloud.SyncUser;
import cd.sklservices.com.Beststockage.Cloud.SyncUserRole;
import cd.sklservices.com.Beststockage.Cloud.SyncVehicule;
import cd.sklservices.com.Beststockage.Repository.AddressRepository;
import cd.sklservices.com.Beststockage.Repository.AgenceRepository;
import cd.sklservices.com.Beststockage.Repository.ArticleRepository;
import cd.sklservices.com.Beststockage.Repository.CategorieRepository;
import cd.sklservices.com.Beststockage.Repository.ClientRepository;
import cd.sklservices.com.Beststockage.Repository.ContenanceRepository;
import cd.sklservices.com.Beststockage.Repository.ConvoyeurRepository;
import cd.sklservices.com.Beststockage.Repository.DelegueRepository;
import cd.sklservices.com.Beststockage.Repository.DeviseRepository;
import cd.sklservices.com.Beststockage.Repository.DistrictRepository;
import cd.sklservices.com.Beststockage.Repository.DriverRepository;
import cd.sklservices.com.Beststockage.Repository.FournisseurRepository;
import cd.sklservices.com.Beststockage.Repository.HumanRepository;
import cd.sklservices.com.Beststockage.Repository.IdentityRepository;
import cd.sklservices.com.Beststockage.Repository.QuarterRepository;
import cd.sklservices.com.Beststockage.Repository.StreetRepository;
import cd.sklservices.com.Beststockage.Repository.TableKeyIncrementorRepository;
import cd.sklservices.com.Beststockage.Repository.TownRepository;
import cd.sklservices.com.Beststockage.Repository.TownshipRepository;
import cd.sklservices.com.Beststockage.Repository.UserRepository;
import cd.sklservices.com.Beststockage.Repository.UserRoleRepository;
import cd.sklservices.com.Beststockage.Repository.VehiculeRepository;

/**
 * Created by SKL on 03/11/2019.
 */

public class ServiceManageRegistre extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        try{
            Log.d("Assert","  Synchronisation get registre ");
            restart_timer_registre();
            return START_STICKY;
        }
        catch (Exception e){
            Log.d("tous Services ","Synchronisation_get0 erreur\n "+e.toString());
            return START_STICKY;
        }
    }



    CountDownTimer countDownTimerRegistre =  new CountDownTimer(10000, 1000) {
        public void onTick(long millisUntilFinished) {
            try {
                Log.d("Assert","countDownTimerRegistre");
            } catch (Exception ex) {
                //Toast.makeText(getApplicationContext(), "Ezamaka ception 111  = " + ex.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFinish() {
            new SyncTableKeyIncrementor(new TableKeyIncrementorRepository(getBaseContext())).envoi();
            new SyncTown(new TownRepository(getBaseContext())).envoi();
            new SyncDistrict(new DistrictRepository(getBaseContext())).envoi();
            new SyncTownship(new TownshipRepository(getBaseContext())).envoi();
            new SyncQuarter(new QuarterRepository(getBaseContext())).envoi();
            new SyncStreet(new StreetRepository(getBaseContext())).envoi();
            new SyncAddress(new AddressRepository(getBaseContext())).envoi();

            new SyncIdentity(new IdentityRepository(getBaseContext())).envoi();
            new SyncHuman(new HumanRepository(getBaseContext())).envoi();
            new SyncAgence(new AgenceRepository(getBaseContext())).envoi();
            new SyncUserRole(new UserRoleRepository(getBaseContext())).envoi();
            new SyncDevise(new DeviseRepository(getBaseContext())).envoi();
            new SyncUser(new UserRepository(getBaseContext())).envoi();
            new SyncDelegue(new DelegueRepository(getBaseContext())).envoi();
            new SyncDriver(new DriverRepository(getBaseContext())).envoi();
            new SyncConvoyeur(new ConvoyeurRepository(getBaseContext())).envoi();
            new SyncVehicule(new VehiculeRepository(getBaseContext())).envoi();
            new SyncClient(new ClientRepository(getBaseContext())).envoi();
            new SyncFournisseur(new FournisseurRepository(getBaseContext())).envoi();
            new SyncCategorie(new CategorieRepository(getBaseContext())).envoi();
            new SyncContenance(new ContenanceRepository(getBaseContext())).envoi();
            new SyncArticle(new ArticleRepository(getBaseContext())).envoi();

            restart_timer_registre();
        }
    }.start();

    void restart_timer_registre(){
        countDownTimerRegistre.start();
    }
}
