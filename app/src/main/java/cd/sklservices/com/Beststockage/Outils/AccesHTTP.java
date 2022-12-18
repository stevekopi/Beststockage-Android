package cd.sklservices.com.Beststockage.Outils;

import android.os.AsyncTask;
import android.util.Log;




import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;
import cd.sklservices.com.Beststockage.Interfaces.*;

/**
 * Created by SKL on 23/04/2019.
 */

public class AccesHTTP extends AsyncTask<String,Integer,Long> {
    private ArrayList<NameValuePair> parametres;
    private String ret=null;
    public AsyncResponse delegate=null;

    /**
     * Constructeur
     */

    public AccesHTTP(){
            parametres=new ArrayList<NameValuePair>();
    }

    public void addParam(String nom,String valeur){
        parametres.add(new BasicNameValuePair(nom,valeur));
    }

    /**
     * Connexion à la base de données distante. C'est une tâche qui s'execute en arrière plan.
     * @param strings
     * @return
     */

    @Override
    protected Long doInBackground(String... strings) {
        HttpClient cnxHttp=new DefaultHttpClient();
        HttpPost paramCnx=new HttpPost(strings[0]);
        try {
            // Encodage des parametres
            paramCnx.setEntity(new UrlEncodedFormEntity(parametres));

            // Connexion et envoie des parametres, attente de reponses
            HttpResponse reponse=cnxHttp.execute(paramCnx);

            // Transformation de la reponse
            ret= EntityUtils.toString(reponse.getEntity());


        } catch (UnsupportedEncodingException e) {
            Log.d("Erreur encodage","******************* "+e.toString());
        } catch (ClientProtocolException e) {
            Log.d("Erreur de protocole","******************* "+e.toString());
        } catch (IOException e) {
            Log.d("Erreur InputOutput","******************* : "+e.toString());
        }catch (Exception e) {
            Log.d("Erreur Exception DoInBG","******************* : "+e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Long result) {
     try {
         delegate.processFinish(ret.toString());

     }catch (Exception e){
         Log.d("Erreur Exception onPost","******************* : "+e.toString());
     }
    }
}
