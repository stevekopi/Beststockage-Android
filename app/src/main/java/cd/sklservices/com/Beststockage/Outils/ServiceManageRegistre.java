package cd.sklservices.com.Beststockage.Outils;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import cd.sklservices.com.Beststockage.Cloud.Registres.SyncAddress;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncAgence;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncArticle;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncCategorie;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncClient;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncContenance;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncConvoyeur;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncDelegue;
import cd.sklservices.com.Beststockage.Cloud.Parametres.SyncDevise;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncDistrict;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncDriver;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncFournisseur;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncHuman;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncIdentity;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncQuarter;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncStreet;
import cd.sklservices.com.Beststockage.Cloud.SyncTableKeyIncrementor;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncTown;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncTownship;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncUser;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncUserRole;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncVehicule;
import cd.sklservices.com.Beststockage.Repository.Registres.AddressRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.AgenceRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.ArticleRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.CategorieRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.ClientRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.ContenanceRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.ConvoyeurRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.DelegueRepository;
import cd.sklservices.com.Beststockage.Repository.Parametres.DeviseRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.DistrictRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.DriverRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.FournisseurRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.HumanRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.IdentityRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.QuarterRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.StreetRepository;
import cd.sklservices.com.Beststockage.Repository.TableKeyIncrementorRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.TownRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.TownshipRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.UserRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.UserRoleRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.VehiculeRepository;

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
