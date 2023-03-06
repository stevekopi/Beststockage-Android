package cd.sklservices.com.Beststockage.ActivityFolder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import cd.sklservices.com.Beststockage.Cloud.Registres.SyncAddress;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncAgence;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncArticle;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncCategorie;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncClient;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncContenance;
import cd.sklservices.com.Beststockage.Cloud.Parametres.SyncDevise;
import cd.sklservices.com.Beststockage.Cloud.Registres.SyncDistrict;
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
import cd.sklservices.com.Beststockage.Outils.Session;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Repository.Registres.AddressRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.AgenceRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.ArticleRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.CategorieRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.ClientRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.ContenanceRepository;
import cd.sklservices.com.Beststockage.Repository.Parametres.DeviseRepository;
import cd.sklservices.com.Beststockage.Repository.Registres.DistrictRepository;
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


/**
 * Created by SKL on 20/04/2019.
 */

public class SecureViewLoading extends AppCompatActivity  {
    //Propriétés
    static SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
     protected void onCreate(Bundle savedInstanceState) {

        try {

            new SyncTableKeyIncrementor(new TableKeyIncrementorRepository(getBaseContext())).envoi();

            new SyncTown(new TownRepository(getBaseContext())).envoi();
            new SyncDistrict(new DistrictRepository(getBaseContext())).envoi();
            new SyncTownship(new TownshipRepository(getBaseContext())).envoi();
            new SyncQuarter(new QuarterRepository(getBaseContext())).envoi();
            new SyncStreet(new StreetRepository(getBaseContext())).envoi();
            new SyncAddress(new AddressRepository(getBaseContext())).envoi();

            new SyncIdentity(new IdentityRepository(getBaseContext())).envoi();
            new SyncHuman(new HumanRepository(getBaseContext())).envoi();
            new SyncDevise(new DeviseRepository(getBaseContext())).envoi();
            new SyncFournisseur(new FournisseurRepository(getBaseContext())).envoi();
            new SyncCategorie(new CategorieRepository(getBaseContext())).envoi();
            new SyncContenance(new ContenanceRepository(getBaseContext())).envoi();
            new SyncArticle(new ArticleRepository(getBaseContext())).envoi();
            new SyncClient(new ClientRepository(getBaseContext())).envoi();
            new SyncAgence(new AgenceRepository(getBaseContext())).envoi();
            new SyncUserRole(new UserRoleRepository(getBaseContext())).envoi();
            new SyncUser(new UserRepository(getBaseContext())).envoi();




            super.onCreate(savedInstanceState);
           setContentView(R.layout.secure);

           new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    Toast.makeText(getApplication()," Chargement des données ... ",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFinish() {

                    Intent i=new Intent(SecureViewLoading.this ,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                }
            }.start();


        }
        catch (Exception ex)
        {
            Log.d("Assert","Exception Secure view  ............ " + ex.toString());
        }

    }

    private void close_secure()
    {
        this.onDestroy();
    }

    public static void Refresh_end(){

          try{
              swipeRefreshLayout.setRefreshing(false);
          }
          catch (Exception e){

          }

    }

public void setSession(Boolean value){
    Session session=new Session(getBaseContext());
    session.setCheckBoxSecureInfoValue(value);

}


    private boolean isServiceRuning(Class<?> serviceClass){
        ActivityManager manager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo serviceInfo:manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(serviceInfo.service.getClassName())){
                return true;
            }
        }
        return false;
    }

}
