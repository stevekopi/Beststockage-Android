package cd.sklservices.com.Beststockage.ActivityFolder;

import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import cd.sklservices.com.Beststockage.R;

import cd.sklservices.com.Beststockage.Outils.*;
import cd.sklservices.com.Beststockage.ViewModel.*;
;

public class SplashView extends AppCompatActivity {

    private TableInfoViewModel tableInfoViewModel ;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("Orientation Change ","Fragment AgenceView SaveInstance");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("Orientation Change","Activity_Man SaveInstance: OnRestaure");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Action","SplashView onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Action","SplashView onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Action","SplashView onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Action","SplashView onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Action","SplashView onStop");

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        init();

            //if (checkNetwork()){
            //    Toast.makeText(this,"Internet disponible",Toast.LENGTH_SHORT);
            //    Log.d("Internet disponible","TRUE");
            //}
            //else {
            //    Toast.makeText(this,"Pas d'Internet",Toast.LENGTH_SHORT);
            //    Log.d("Internet disponible","FALSE");
            //}

        final Thread chrono=new Thread(){
            public  void run(){
                try{
                    sleep(3000);
                    Intent intent=new Intent(SplashView.this, SecureView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

//                    Synchronisaation sync=new Synchronisaation();
//                    sync.Start();
                    MainActivity.isFirstRoundSync=true;
                    startActivity(intent);
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                    Log.d("Assert","Exception Splash  ............ " + ex.toString());
                }
            }
        };
        chrono.start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            Log.i("Orientation Change ","LanCape");
        }else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            Log.i("Orientation Change ","Portrait");
        }
    }

    private Boolean checkNetwork(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }



   private void init(){

       this.tableInfoViewModel = new ViewModelProvider(this).get(TableInfoViewModel.class) ;

       ///// Initialisation de la connexion a la base de sdonnées ......

       MyDataBase.getInstance(getApplicationContext()) ;

       this.tableInfoViewModel.initialisation();

    }
}
