package cd.sklservices.com.Beststockage.ActivityFolder;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.app.ActivityManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Cloud.SyncAddress;
import cd.sklservices.com.Beststockage.Cloud.SyncAgence;
import cd.sklservices.com.Beststockage.Cloud.SyncApprovisionnement;
import cd.sklservices.com.Beststockage.Cloud.SyncArticle;
import cd.sklservices.com.Beststockage.Cloud.SyncBon;
import cd.sklservices.com.Beststockage.Cloud.SyncBonlivraison;
import cd.sklservices.com.Beststockage.Cloud.SyncCategorie;
import cd.sklservices.com.Beststockage.Cloud.SyncClient;
import cd.sklservices.com.Beststockage.Cloud.SyncContenance;
import cd.sklservices.com.Beststockage.Cloud.SyncControle;
import cd.sklservices.com.Beststockage.Cloud.SyncConvoyeur;
import cd.sklservices.com.Beststockage.Cloud.SyncDelegue;
import cd.sklservices.com.Beststockage.Cloud.SyncDepense;
import cd.sklservices.com.Beststockage.Cloud.SyncDevise;
import cd.sklservices.com.Beststockage.Cloud.SyncDistrict;
import cd.sklservices.com.Beststockage.Cloud.SyncDriver;
import cd.sklservices.com.Beststockage.Cloud.SyncFournisseur;
import cd.sklservices.com.Beststockage.Cloud.SyncHuman;
import cd.sklservices.com.Beststockage.Cloud.SyncIdentity;
import cd.sklservices.com.Beststockage.Cloud.SyncLigneBonlivraison;
import cd.sklservices.com.Beststockage.Cloud.SyncLivraison;
import cd.sklservices.com.Beststockage.Cloud.SyncOperation;
import cd.sklservices.com.Beststockage.Cloud.SyncOperationFinance;
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
import cd.sklservices.com.Beststockage.Repository.ApprovisionnementRepository;
import cd.sklservices.com.Beststockage.Repository.ArticleRepository;
import cd.sklservices.com.Beststockage.Repository.BonLivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.BonRepository;
import cd.sklservices.com.Beststockage.Repository.CategorieRepository;
import cd.sklservices.com.Beststockage.Repository.ClientRepository;
import cd.sklservices.com.Beststockage.Repository.ContenanceRepository;
import cd.sklservices.com.Beststockage.Repository.ControleRepository;
import cd.sklservices.com.Beststockage.Repository.ConvoyeurRepository;
import cd.sklservices.com.Beststockage.Repository.DelegueRepository;
import cd.sklservices.com.Beststockage.Repository.DepenseRepository;
import cd.sklservices.com.Beststockage.Repository.DeviseRepository;
import cd.sklservices.com.Beststockage.Repository.DistrictRepository;
import cd.sklservices.com.Beststockage.Repository.DriverRepository;
import cd.sklservices.com.Beststockage.Repository.FournisseurRepository;
import cd.sklservices.com.Beststockage.Repository.HumanRepository;
import cd.sklservices.com.Beststockage.Repository.IdentityRepository;
import cd.sklservices.com.Beststockage.Repository.LigneBonLivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.LivraisonRepository;
import cd.sklservices.com.Beststockage.Repository.OperationFinanceRepository;
import cd.sklservices.com.Beststockage.Repository.OperationRepository;
import cd.sklservices.com.Beststockage.Repository.QuarterRepository;
import cd.sklservices.com.Beststockage.Repository.StreetRepository;
import cd.sklservices.com.Beststockage.Repository.TableKeyIncrementorRepository;
import cd.sklservices.com.Beststockage.Repository.TownRepository;
import cd.sklservices.com.Beststockage.Repository.TownshipRepository;
import cd.sklservices.com.Beststockage.Repository.UserRepository;
import cd.sklservices.com.Beststockage.Repository.UserRoleRepository;
import cd.sklservices.com.Beststockage.Repository.VehiculeRepository;
import cd.sklservices.com.Beststockage.ViewModel.*;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Outils.*;
import layout.*;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
    public static boolean isFirstRoundSync=true;
    public static boolean isDisconnect=false;
    private static int countRound;
    private final static String COMMON_TAG="Orientation Change ";
    private final static String ACTIVITY_NAME=MainActivity.class.getSimpleName();
    private final static String TAG=ACTIVITY_NAME;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    static TextView tv_content_main_notification;

    public static String CurrentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    public static String DefaultFinancialKey ="00000000000000000000000000000000";
    private AdresseViewModel adresseViewModel ;
    private AgenceViewModel agenceViewModel ;
    private ApprovisionnementViewModel approvisionnementViewModel ;
    private ArticleViewModel articleViewModel ;
    private ClientViewModel clientViewModel ;
    private BonLivraisonViewModel bonLivraisonViewModel ;
    private CategorieViewModel categorieViewModel ;
    private CommandeViewModel commandeViewModel;
    private DepenseViewModel  depenseViewModel;
    private FournisseurViewModel fournisseurViewModel;
    private IdentityViewModel identityViewModel;
    private LigneBonLivraisonViewModel ligneBonLivraisonViewModel ;
    private LivraisonViewModel livraisonViewModel ;
    private BonViewModel bonViewModel;
    private UserViewModel userViewModel;
    private OperationViewModel operationViewModel;
    private OperationFinanceViewModel operationFinanceViewModel;
    private LigneCommandeViewModel ligneCommandeViewModel;

    static User current_user;
    static String last_agence_id;


    public TextView tv_main_connected_agence,tv_main_connected_user;
   // public NavigationView nav_header_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(MainActivity.getCurrentUser()!=null && MainActivity.getCurrentUser().getAgence()!=null){
            if(!isServiceRuning(ServiceManageStock.class))
                startService(new Intent(getBaseContext(), ServiceManageStock.class));
            if(!isServiceRuning(ServiceManageFinancial.class))
                startService(new Intent(getBaseContext(), ServiceManageFinancial.class));
            if(!isServiceRuning(ServiceManageControle.class))
                startService(new Intent(getBaseContext(), ServiceManageControle.class));
        }

        if(!isServiceRuning(ServiceManageRegistre.class))
            startService(new Intent(getBaseContext(), ServiceManageRegistre.class));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigating_menu( navigationView ) ;

        View ll;
        ll=(View)navigationView.getHeaderView(0);
        tv_main_connected_agence=(TextView) ll.findViewById(R.id.tv_main_connected_agence);
        tv_main_connected_user=(TextView) ll.findViewById(R.id.tv_main_connected_user);


        init();

     }

    CountDownTimer countDownTimerDeleteOldData =  new CountDownTimer(3000, 1000) {
        public void onTick(long millisUntilFinished) {
            try {

            } catch (Exception ex) {
                //Toast.makeText(getApplicationContext(), "Ezamaka ception 111  = " + ex.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFinish() {
            try{
                set_values_for_delete_old_data();
                restart_delete_old_data();
            }
            catch (Exception e){

            }
        }
    }.start();

    private void restart_delete_old_data(){
        countDownTimerDeleteOldData.start();
    }

    public static String getAddingDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public void init(){
        fragmentManager=getSupportFragmentManager();
        this.agenceViewModel=new ViewModelProvider((ViewModelStoreOwner) this).get(AgenceViewModel.class) ;
        this.adresseViewModel =new ViewModelProvider(this).get(AdresseViewModel.class) ;
        this.commandeViewModel = new ViewModelProvider(this).get(CommandeViewModel.class) ;
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;
        this.clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class) ;
        this.categorieViewModel = new ViewModelProvider(this).get(CategorieViewModel.class) ;
        this.depenseViewModel = new ViewModelProvider(this).get(DepenseViewModel.class) ;
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;
        this.fournisseurViewModel = new ViewModelProvider(this).get(FournisseurViewModel.class) ;
        this.identityViewModel = new ViewModelProvider(this).get(IdentityViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class) ;
        this.bonLivraisonViewModel = new ViewModelProvider(this).get(BonLivraisonViewModel.class) ;
        this.ligneCommandeViewModel = new ViewModelProvider(this).get(LigneCommandeViewModel.class) ;
        this.approvisionnementViewModel = new ViewModelProvider(this).get(ApprovisionnementViewModel.class) ;
        this.ligneBonLivraisonViewModel = new ViewModelProvider(this).get(LigneBonLivraisonViewModel.class) ;
        this.livraisonViewModel = new ViewModelProvider(this).get(LivraisonViewModel.class) ;
        this.bonViewModel=new ViewModelProvider(this).get(BonViewModel.class);
        this.operationFinanceViewModel=new ViewModelProvider(this).get(OperationFinanceViewModel.class);


//MASQUAGE DES QUELQUES VUES


        if(!RoundSyncFinished.isAlive()){
            RoundSyncFinished.start();
        }

        connectedUserInfo();

    }


    public static  Thread RoundSyncFinished = new Thread(){
        public void run(){
            try {
                Thread.sleep(10000);
                if(countRound>=6){
                    //Après 60 secondes on synchronise les données recentes
                    isFirstRoundSync=false;
                }
                else{
                    countRound++;
                    Log.d("Assert","countRound progress "+countRound);
                }
                run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };



    public static  void SeeTvSync( Boolean value){
        if (value==true){
            tv_content_main_notification.setVisibility(View.VISIBLE);
        }else{
            tv_content_main_notification.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(COMMON_TAG,"Activity_Man SaveInstance: OnSave");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }else {
            super.onBackPressed();
        }
    }



    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,ACTIVITY_NAME+" onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,ACTIVITY_NAME+" onStop");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Homeew/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_password) {
            ChangePwdView.setCurrentUser(current_user);
            fragment=new ChangePwdView();
            addFragment1(fragment);
        }else if (id == R.id.action_about) {
            fragment= new AboutView();
            addFragment1(fragment);
        }
       else if (id == R.id.action_deconnexion) {


            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            Session session=new Session(getBaseContext()); //On demarre une session pour enregistrer l'id de la dernière agence
                            session.setCheckBoxSecureInfoValue (false);
                          if(MainActivity.getCurrentUser()!=null)
                              session.setLastAgenceId(MainActivity.getCurrentUser().getAgence_id());
                            stop_service_get(); //On arrete les services gets des finances et stocks pour eviter le melange de données
                            MainActivity.setCurrentUser(getApplication(),null);
                            isDisconnect=true;
                            Intent i=new Intent(MainActivity.this, SecureView.class);
                            startActivity(i);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            Toast.makeText(MainActivity.this, "Action annulée", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Etes-vous sûr de vouloir vous deconnecter?").setPositiveButton("Oui", dialogClickListener)
                    .setNegativeButton("Non", dialogClickListener).show();



        }
        else if (id == R.id.action_quitter) {
            System.exit(0);

        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accueil) {
            fragment=new Home() ;
            addFragment1(fragment);
        }
        else if (id == R.id.nav_fournisseur) {
            fragment=new FournisseurView();
            addFragment1(fragment);
        }
        else if (id == R.id.nav_agence) {
            fragment=new AgenceView();
            addFragment1(fragment);
        }
        else if (id == R.id.nav_user) {
            afficherUtilisateur();
        }
        else if (id == R.id.nav_article) {
            fragment=new ArticleView();
            addFragment1(fragment);
        }
        else if (id == R.id.nav_stock) {
            fragment = new StockView();
            addFragment1(fragment);
        }
        else if (id == R.id.nav_livraison) {
            afficherLivraison1() ;
        }
        else if (id == R.id.nav_commande) {
           afficheCommandeView();
        }
        else if (id == R.id.nav_operation) {
            fragment = new OperationView();
            addFragment1(fragment);
        }
        else if (id == R.id.nav_performance) {
            fragment = new PerformanceAgenceView();
            addFragment1(fragment);
        }
        else if (id == R.id.nav_livraison2) {
            afficherLivraison2();
        }
        else if (id == R.id.nav_depense) {
            afficherDepense();
        }
        else if (id == R.id.nav_operation_finance) {
            afficherOperationFinance();
        }
        else if (id == R.id.nav_client) {
            fragment = new ClientView() ;
            addFragment1(fragment);
        }
        else if (id == R.id.nav_rapport_caisse) {
            fragment = new RapportCaisseView() ;
            addFragment1(fragment);
        }
        else if (id == R.id.nav_divers_autres) {
             fragment = new HomeView() ;
             addFragment1(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public  void addFragment1(Fragment fragment){
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null) ;
        fragmentTransaction.commit();
    }


    public  void addFragment2(Fragment fragment){

        fragmentTransaction=fragmentManager.beginTransaction();
       // fragment.setArguments(args);
        fragmentTransaction.add(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void afficherDetailsAgence(Agence instance){
         agenceViewModel.setAgence(instance);
         addFragment1(new Agence_detailsView());
    }

    public void addUser(){
        addFragment1(new UserViewAdd());
    }

    public void afficherDetailsClient(Identity instance){
        identityViewModel.setIdentity(instance);
        addFragment1(new Client_detailsView());
    }

    public void afficherDetailsFournisseur(Fournisseur instance){
         fournisseurViewModel.setFournisseur(instance);
         addFragment1(new Fournisseur_detailsView());
    }

    public void afficherDetailsUser(User user){
        userViewModel.setUser(user);
        addFragment1(new User_detailsView());
    }

    public void afficherDetailsArticle(Article instance){
         articleViewModel.setArticle(instance);
         addFragment1(new Article_detailsView());
    }

    public void afficherDetailsCommande(LigneCommande instance){
        ligneCommandeViewModel.setLigneCommande(instance);
        addFragment1(new Commande_detailsView());
    }

    public void afficherDetailsLivraison1(Approvisionnement instance){
        approvisionnementViewModel.setapprovisionnement(instance);
        addFragment1(new Livraison1_detailsView());
    }

    public void updateLivraison1(Approvisionnement instance){
        approvisionnementViewModel.setapprovisionnement(instance);
        addFragment1(new LivraisonViewUpdate1());
    }


    public void addStock(){
        addFragment1(new OperationAdd());
    }

    public void addLivraison1(){
        addFragment1(new LivraisonViewAdd1());
    }

    public void addCommande(){
        addFragment1(new CommandeViewAdd());
    }

    public void updateOperation(Operation instance)
    {
        operationViewModel.setOperation(instance);
        OperationUpdate instanceView=new OperationUpdate();
        instanceView.current_instance=instance;
        addFragment1(instanceView);
    }

    public void updateDepense(Depense instance)
    {
        DepenseUpdate instanceView=new DepenseUpdate();
        instanceView.current_instance=instance;
        addFragment1(instanceView);
    }

    public void afficherDetailStock(Operation instance)
    {
        operationViewModel.setOperation(instance);
        addFragment1(new Operation_detailsView());
    }

    public void afficherStock(){
        addFragment1(new StockView());
    }



    public void afficherLivraison2(){
        fragment = new LivraisonView1();
        addFragment1(fragment);
    }

    public void afficherLivraison1(){
        fragment = new BonlivraisonView();
        addFragment1(fragment);
    }

    public void addLivraison2(){
        addFragment1(new BonlivraisonViewAdd2());
    }

    public void afficherOperationView()
    {
        fragment = new OperationView();
        addFragment1(fragment);
    }

    public void afficherOperationFinance()
    {
        fragment=new OperationFinanceView();
        addFragment1(fragment);
    }

    public void afficheCommandeView()
    {
        fragment = new CommandeView();
        addFragment1(fragment);
    }

    public void updateCommande(LigneCommande instance)
    {
        ligneCommandeViewModel.setLigneCommande(instance) ;
        addFragment1(new CommandeViewUpdate());
    }

    public void afficherDetailLigneBonLivraison(LigneBonlivraison instance)
    {
        ligneBonLivraisonViewModel.setLigneBonLivraison(instance);
        addFragment1(new Bonlivraison_detailsView());
    }

    public void updateLivraison2(LigneBonlivraison instance){
        ligneBonLivraisonViewModel.setLigneBonLivraison(instance);
        addFragment1(new LivraisonViewUpdate2());
    }

    public void afficherUtilisateur()
    {
        fragment=new UserView();
        addFragment1(fragment);
    }

    public void afficherDepense()
    {
        fragment=new DepenseView() ;
        addFragment1(fragment);
    }


    public void addDepense()
    {
        fragment=new DepenseAdd() ;
        addFragment1(fragment);
    }


    public void afficherHomeView()
    {
        fragment=new HomeView() ;
        addFragment1(fragment);
    }

    public static void setCurrentUser(Application application,User currentUser){
       try{
           if(currentUser!=null){
               User user=new UserViewModel(application).get(currentUser.getId(),true,true);
               if(user!=null){
                   current_user =user;
               }
               else{
                   current_user=currentUser;
               }
           }
       }
       catch (Exception e){

       }
    }
    public static void setLastAgenceId(String lastAgenceId){
        last_agence_id =lastAgenceId;
    }

    public static User getCurrentUser(){
        return current_user;
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


    public void set_values_for_delete_old_data()
    {
        try{
            if(last_agence_id!=null && last_agence_id.length()>1 && !last_agence_id.equals(current_user.getAgence_id()))
            {
                isFirstRoundSync=true;
                countRound=0;
                Toast.makeText(getApplicationContext(), "Suppression des anciennes données", Toast.LENGTH_LONG).show();
                this.approvisionnementViewModel.delete_all();
                this.livraisonViewModel.delete_all();
                this.ligneBonLivraisonViewModel.delete_all();
                this.bonLivraisonViewModel.delete_all();
                this.bonViewModel.delete_all();
            }
        }
        catch (Exception e)
        {
            Log.d("Assert","delete old data error **** "+e.toString());
        }
       if(current_user!=null){
           MainActivity.setLastAgenceId(MainActivity.getCurrentUser().getAgence_id());
          try {
              Session session=new Session(getBaseContext());
              if(!session.getAgenceId().equals(MainActivity.getCurrentUser().getAgence_id()))
                  session.setLastAgenceId(MainActivity.getCurrentUser().getAgence_id());

          }catch (Exception e){

          }

           this.operationViewModel.delete_data_old_agence(current_user.getAgence_id());
           this.operationFinanceViewModel.delete_data_old_agence(current_user.getAgence_id());
           this.depenseViewModel.delete_data_old_agence(current_user.getAgence_id());
          /* this.ligneBonLivraisonViewModel.delete_data_old_agence(current_user.getAgence_id());
           this.bonLivraisonViewModel.delete_data_old_agence(current_user.getAgence_id());
           this.bonViewModel.delete_data_old_agence(current_user.getAgence_id());

           */
       }
    }



    public  void stop_service_get()
    {
        stopService(new Intent(getBaseContext(), ServiceManageFinancial.class));
        stopService(new Intent(getBaseContext(), ServiceManageStock.class));
        stopService(new Intent(getBaseContext(), ServiceManageControle.class));
    }


    public static Boolean TestNetworkConnected(){
       new Thread(){
           @Override
           public void run() {
               String link = "https://fatimefresh.com" ;
               //String link = "http://192.168.1.124:88";
               try {
                   URL url=new URL(link);
                   HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                   if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                       result(true);
                   }
               }
               catch (Exception e){
                   Log.d("Assert","isNetworkConnected error : MainView");
               }
               result(false);
           }

           private boolean result(boolean value){
               return value;
           }
       }.start();
       return false;
    }


     private void navigating_menu(NavigationView navigationView ) {
         Menu nav_Menu = navigationView.getMenu();
         try{
             String role = getCurrentUser().getRole().getDesignation() ;

             if(!role.toLowerCase(Locale.ROOT).contains("super"))
             {
                 // nav_Menu.findItem(R.id.nav_agence).setVisible(false);
                 nav_Menu.findItem(R.id.nav_fournisseur).setVisible(false);
                 //  nav_Menu.findItem(R.id.nav_user).setVisible(false);
                 // nav_Menu.findItem(R.id.nav_article).setVisible(false);

             }else{
                 nav_Menu.findItem(R.id.nav_fournisseur).setVisible(true);
             }
         }
         catch (Exception e){
             nav_Menu.findItem(R.id.nav_fournisseur).setVisible(false);
         }



     }


    CountDownTimer countDownTimerSecureRegistre =  new CountDownTimer(10000, 1000) {
        public void onTick(long millisUntilFinished) {
            try {
                Log.d("Assert","countDownTimerRegistre : SecureView");

            } catch (Exception ex) {
                //Toast.makeText(getApplicationContext(), "Ezamaka ception 111  = " + ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish() {

            Log.d("Assert","Synchronisation globale : MainView");

            new SyncTableKeyIncrementor(new TableKeyIncrementorRepository(getBaseContext())).envoi();
            // LES POSTS

           if(MainActivity.getCurrentUser()!=null && MainActivity.getCurrentUser().getAgence()!=null){
               new SyncOperationFinance(new OperationFinanceRepository(getBaseContext())).sendPost();
               new SyncDepense(new DepenseRepository(getBaseContext())).sendPost();
               new SyncOperation(new OperationRepository(getBaseContext())).sendPost();
               new SyncControle(new ControleRepository(getBaseContext())).sendPost();
               new SyncUser(new UserRepository(getBaseContext())).sendPost();

               //FINANCIAL


               new SyncOperationFinance(new OperationFinanceRepository(getBaseContext())).envoi();
               new SyncDepense(new DepenseRepository(getBaseContext())).envoi();


               //STOCK

               new SyncOperation(new OperationRepository(getBaseContext())).envoi();
               new SyncBon(new BonRepository(getBaseContext())).envoi();
               new SyncBonlivraison(new BonLivraisonRepository(getBaseContext())).envoi();
               new SyncLigneBonlivraison(new LigneBonLivraisonRepository(getBaseContext())).envoi();
               new SyncLivraison(new LivraisonRepository(getBaseContext())).envoi();
               new SyncApprovisionnement(new ApprovisionnementRepository(getBaseContext())).envoi();

               //CONTROLE
               new SyncControle(new ControleRepository(getBaseContext())).envoi();
            }

            //REGISTRE
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

           //STOCK
          //  new SyncCommande(new CommandeRepository(getBaseContext())).envoi();
           // new SyncLigneCommande(new LigneCommandeRepository(getBaseContext())).envoi();

            restart_timer();
        }
    }.start();

    void restart_timer(){

        countDownTimerSecureRegistre.start();
        try{

         connectedUserInfo();

        }
        catch (Exception e){

        }
    }

    public void connectedUserInfo() {
        if(current_user!=null && current_user.getAgence()==null)
            current_user=new UserViewModel(getApplication()).get(current_user.getId(),true,true);
        if(current_user!=null && current_user.getAgence()!=null){
            tv_main_connected_agence.setText(current_user.getAgence().getType()+" "+current_user.getAgence().getDenomination());
            tv_main_connected_user.setText(current_user.getHuman().getIdentity().getName().toUpperCase(Locale.ROOT));
        }
    }

    public Application getApp(){
        return this.getApplication();
}


}



