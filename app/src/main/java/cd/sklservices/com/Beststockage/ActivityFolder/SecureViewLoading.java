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

import cd.sklservices.com.Beststockage.Cloud.SyncAddress;
import cd.sklservices.com.Beststockage.Cloud.SyncAgence;
import cd.sklservices.com.Beststockage.Cloud.SyncArticle;
import cd.sklservices.com.Beststockage.Cloud.SyncCategorie;
import cd.sklservices.com.Beststockage.Cloud.SyncClient;
import cd.sklservices.com.Beststockage.Cloud.SyncContenance;
import cd.sklservices.com.Beststockage.Cloud.SyncDevise;
import cd.sklservices.com.Beststockage.Cloud.SyncDistrict;
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
import cd.sklservices.com.Beststockage.Outils.Session;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Repository.AddressRepository;
import cd.sklservices.com.Beststockage.Repository.AgenceRepository;
import cd.sklservices.com.Beststockage.Repository.ArticleRepository;
import cd.sklservices.com.Beststockage.Repository.CategorieRepository;
import cd.sklservices.com.Beststockage.Repository.ClientRepository;
import cd.sklservices.com.Beststockage.Repository.ContenanceRepository;
import cd.sklservices.com.Beststockage.Repository.DeviseRepository;
import cd.sklservices.com.Beststockage.Repository.DistrictRepository;
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
