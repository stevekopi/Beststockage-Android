package cd.sklservices.com.Beststockage.Outils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by SKL on 16/11/2019.
 */

public class Session {
    private SharedPreferences prefs;
    public Session(Context context){
        if(context!=null)
            prefs= PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLogin(String login){
        prefs.edit().putString("login",login).commit();
    }

    public String getLogin(){
        String login=prefs.getString("login","");
        return login;
    }

    public void setLastAgenceId(String lastAgenceId){
        prefs.edit().putString("lastAgenceId",lastAgenceId).commit();
    }

    public String getAgenceId(){
        String lastAgenceId=prefs.getString("lastAgenceId","");
        return lastAgenceId;
    }



    public void setPassword(String password){
        prefs.edit().putString("password",password).commit();
    }

    public String getPassword(){
        String password=prefs.getString("password","");
        return password;
    }

    public void setCheckBoxSecureInfoValue(Boolean value){
        prefs.edit().putBoolean("checkboxSecure",value).commit();
    }

    public Boolean getCheckBoxSecureInfoValue(){
        Boolean value=prefs.getBoolean("checkboxSecure",false);
        return value;
    }
}
