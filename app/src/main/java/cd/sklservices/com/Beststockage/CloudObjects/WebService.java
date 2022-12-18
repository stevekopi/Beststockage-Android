package cd.sklservices.com.Beststockage.CloudObjects;

import static cd.sklservices.com.Beststockage.Outils.MesOutils.converDateToStringForMySql;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import cd.sklservices.com.Beststockage.Interfaces.AsyncResponse;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class WebService {
    String adresse = "http://192.168.0.100/projects/services/";
    //String adresse = "http://127.0.0.1/projects/services/";
    // String adresse = "http://www.fatimefresh.com/services/";



    public  String login(String username, String password) {

        try
        {
            // String url = "http://httpbin.org/ip";


            String url=adresse + "get/login.php?username=" + username+"&password="+password;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print in String
            System.out.println(response.toString());

            //Read JSON response and print
            //     JSONArray myResponse = new JSONArray(response.toString());
            //    System.out.println("result after Reading JSON Response");
            //     System.out.println("skl origin- "+myResponse.getString("origin"));

            return response.toString();
        }
        catch (Exception e)
        {
            System.out.println("Response Code : " + e.toString());
        }
        return null;
    }

    public  String get(String tableName, Date lastSyncDate) {

        try
        {
            // String url = "http://httpbin.org/ip";

            String last= converDateToStringForMySql(lastSyncDate);
            //  String url="http://www.fatimefresh.com/services/get/controle.php?last_sync=2020-01-01";
            String url=adresse + "get/" + tableName + ".php?last_sync=" + last;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print in String
            System.out.println(response.toString());

            //Read JSON response and print
            //     JSONArray myResponse = new JSONArray(response.toString());
            //    System.out.println("result after Reading JSON Response");
            //     System.out.println("skl origin- "+myResponse.getString("origin"));

            return response.toString();
        }
        catch (Exception e)
        {
            System.out.println("Response Code : " + e.toString());
        }
      return null;
    }

    public void makeRequest (String uri, String json) {

        try{
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            new DefaultHttpClient ().execute(httpPost);
     }
     catch (Exception e){
         Log.d("Assert","WebServiceErreur Post "+e.toString());
     }

    }
}

