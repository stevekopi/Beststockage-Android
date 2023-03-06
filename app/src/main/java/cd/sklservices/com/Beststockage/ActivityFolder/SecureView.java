package cd.sklservices.com.Beststockage.ActivityFolder;

import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Cloud.SyncLogin;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Outils.*;
import cd.sklservices.com.Beststockage.Repository.Registres.UserRepository;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;


/**
 * Created by SKL on 20/04/2019.
 */

public class SecureView extends AppCompatActivity  {
    //Propriétés
    static SwipeRefreshLayout swipeRefreshLayout;
    Button btnConnect;
    LinearLayout ll_progressBar, ll_login;
    CheckBox cb_save_info;
    AutoCompleteTextView tv_login,tv_password ;
    EditText txtLogin,txtPassword;

    private UserViewModel userViewModel ;

    private boolean login_test = false ;
    private boolean login_btn = false ;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.secure);

            init();
            // start_services(); //Demanrage des services

            btnConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.isDisconnect=false;
                    login_btn = true ;
                    try_connect.cancel();
                    try_connect.start();
                    if (cb_save_info.isChecked()==true){
                        Session session=new Session(getBaseContext());
                        session.setLogin(txtLogin.getText().toString());
                        session.setPassword(txtPassword.getText().toString());
                        session.setCheckBoxSecureInfoValue(true);
                    }
                    else{

                        Session session=new Session(getBaseContext());
                        session.setLogin("");
                        session.setPassword("");
                        session.setCheckBoxSecureInfoValue(false);

                    }

                    ll_progressBar.setVisibility(View.VISIBLE);
                    ll_login.setVisibility(View.GONE);

                    //  new SyncLogin(new UserRepository(getApplication())).get(txtLogin.getText().toString(), MD5.getEncodedPassword(txtPassword.getText().toString()));
                    try
                    {
                        new SyncLogin(new UserRepository(getApplication()),getApplication()).envoi(txtLogin.getText().toString(),txtPassword.getText().toString());

                    }
                    catch (Exception e){}

                }
            });

        }
        catch (Exception ex)
        {
            Log.d("Assert","Exception Secure view  ............ " + ex.toString());
        }

    }

    CountDownTimer try_connect= new CountDownTimer(10000, 1000) {
        public void onTick(long millisUntilFinished) {
            try {
                //  if (isNetworkConnected() == true)
                {
                   if(!MainActivity.isDisconnect)
                       connect(txtLogin.getText().toString(),txtPassword.getText().toString());
                }

            } catch (Exception ex) {
                //Toast.makeText(getApplicationContext(), "Ezamaka ception 111  = " + ex.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFinish() {

            ll_progressBar.setVisibility(View.GONE);
            ll_login.setVisibility(View.VISIBLE);
            if(login_test == false){
                Toast.makeText(getApplication()," Impossible de se connecter ",Toast.LENGTH_LONG).show();
            }


        }
    }.start();

    private void connect(String login,String password) throws InterruptedException {
        try{
            User user = userViewModel.acces(login, MD5.getEncodedPassword(password)) ;
            User connected=MainActivity.getCurrentUser();

            if(user!=null)
                MainActivity.setCurrentUser(getApplication(),user);
            if(connected!=null && MainActivity.getCurrentUser()==null)
                MainActivity.setCurrentUser(getApplication(),connected);

            if ((user!=null || connected!=null) && login_test==false){

                Session session=new Session(getBaseContext());
                MainActivity.setLastAgenceId(session.getAgenceId());
                Intent i ;
/*
                if(MainActivity.getCurrentUser().getStatut().equals("Operationnel"))
                {

 */
                    if(login_btn == true)
                    {
                        i = new Intent(SecureView.this , SecureViewLoading.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Toast.makeText(getApplication()," Bienvenue : " + MainActivity.getCurrentUser().getUsername() ,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        i = new Intent(SecureView.this , MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    }

                    try_connect.cancel();
                    startActivity(i);
                    login_test = true ;

              /*  }else{
                    try_connect.cancel();
                    Toast.makeText(getApplication(), MainActivity.getCurrentUser().getUsername()+ ", votre statut actuel est "+MainActivity.getCurrentUser().getStatut()+", veuillez contacter la hierarchie" ,Toast.LENGTH_LONG).show();
                    ll_progressBar.setVisibility(View.GONE);
                    ll_login.setVisibility(View.VISIBLE);
                }

               */

            }

        }
        catch (Exception e){
            Log.d("Assert"," SecureView Error for connecr "+ e.toString());
        }

    }

    private void init() {
        try
        {
            this.userViewModel =new ViewModelProvider(this).get(UserViewModel.class);
            this.userViewModel.gets();
            btnConnect =  findViewById(R.id.ib_secure_connect);
            txtLogin =  findViewById(R.id.txtLogin);
            txtPassword =  findViewById(R.id.txtPassword);
            tv_login =  findViewById(R.id.txtLogin);
            tv_password =  findViewById(R.id.txtPassword);
            cb_save_info =  findViewById(R.id.cb_secure_save_info);
            ll_progressBar =  findViewById(R.id.ll_progressBar);
            ll_login =  findViewById(R.id.ll_login);

            ll_progressBar.setVisibility(View.GONE);

            swipeRefreshLayout =  findViewById(R.id.refresh_secure);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                }
            });

            cb_save_info.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });

            //VERIFICATION DE SESSION
            Session session = new Session(getBaseContext());
            if (session.getCheckBoxSecureInfoValue() == true) {
                String login = session.getLogin();
                String pass = session.getPassword();

                txtLogin.setText(login);
                txtPassword.setText(pass);
                cb_save_info.setChecked(true);
                connect(login, pass);
            }

            else if (session.getLogin().length() > 0 && session.getLogin().length() > 0) {

                txtLogin.setText("");
                txtPassword.setText("");
                cb_save_info.setChecked(false);

                String login = session.getLogin();
                String pass = session.getPassword();

                txtLogin.setText(login);
                txtPassword.setText(pass);
                cb_save_info.setChecked(true);
                session.setCheckBoxSecureInfoValue(false);
            }
            else {
                txtLogin.setText("");
                txtPassword.setText("");
                cb_save_info.setChecked(false);

            }

            ///// Initialisation de la connexion a la base de sdonnées ......
            MyDataBase.getInstance(getApplicationContext()) ;

        }
        catch (Exception ex)
        {
            Log.d("Assert"," EREURE SECUREVIEW "+ ex.toString());
        }

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

    int tourSync=0;
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
            if(tourSync<6){
                tourSync++;
                Log.d("Assert",tourSync+"è tour");
                restart_timer();
            }
        }
    }.start();

    void restart_timer(){
        countDownTimerSecureRegistre.start();
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

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        countDownTimerSecureRegistre.cancel();
    }
}

