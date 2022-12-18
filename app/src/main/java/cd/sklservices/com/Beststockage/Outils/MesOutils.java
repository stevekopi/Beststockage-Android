package cd.sklservices.com.Beststockage.Outils;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.TableInfo;
import cd.sklservices.com.Beststockage.Classes.TableKeyIncrementor;
import cd.sklservices.com.Beststockage.ViewModel.TableInfoViewModel;
import cd.sklservices.com.Beststockage.ViewModel.TableKeyIncrementorViewModel;

/**
 * Created by SKL on 25/04/2019.
 */

public abstract class MesOutils {

    /**
     * Conversion d'un string sous format Tue Feb 06 09:16:17 GMT 2019 (EEE MMM dd hh:mm:ss 'GMT' yyyy vers Date
     * @param uneDate
     * @return
     */
    public static Date converStringTodate(String uneDate){
       return converStringTodate(uneDate,"EEE MMM dd hh:mm:ss 'GMT' yyyy");

    }

    /**
     * Conversion d'un string reçu en parametre, vers Date
     * @param uneDate
     * @param formatAttendu
     * @return
     */
    @Nullable
    public static Date converStringTodate(String uneDate, String formatAttendu){
      try {
            SimpleDateFormat formatter=new SimpleDateFormat(formatAttendu);
            Date date=formatter.parse(uneDate);
            return date;
        } catch (ParseException e) {
            Log.d("Erreur","Parse de la date impossible "+e.toString());
        }
        return  null;

    }

    /**
     * Conversion d'une date en String sous la forme yyyy-mm-dd hh:mm:ss
     * @param uneDate
     * @return
     */
    public static String converDateToStringForMySql(Date uneDate){

        SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd");
        return   date.format(uneDate);
    }

    public static String converDateToStringFrancais(Date uneDate){

        SimpleDateFormat date=new SimpleDateFormat("dd-MM-yyyy");
        return   date.format(uneDate.getTime());
    }

      /**
     * Convertir un Float à un String avec 1 rang après la virgule
     * @param valeur: c'est la valeur à convertir
     * @return
     */
    public static String convertFloatToDecimal(Float valeur){
        return String.format("%.01f",valeur);
    }

    public static String getMonthInLetter(String mois){
        if (mois.equals("01") || mois.equals("1") ){
            return "Janvier";
        }
        else
        if (mois.equals("02") || mois.equals("2") ){
            return "Février";
        }
        else
        if (mois.equals("03") || mois.equals("3") ){
            return "Mars";
        }
        else
        if (mois.equals("04") || mois.equals("4") ){
            return "Avril";
        }
        else
        if (mois.equals("05") || mois.equals("5") ){
            return "Mai";
        }
        else
        if (mois.equals("06") || mois.equals("6") ){
            return "Juin";
        }
        else
        if (mois.equals("07") || mois.equals("7") ){
            return "Juillet";
        }
        else
        if (mois.equals("08") || mois.equals("8") ){
            return "Août";
        }
        else
        if (mois.equals("09") || mois.equals("9") ){
            return "Septembre";
        }
        else
        if (mois.equals("10") ){
            return "Octobre";
        }
        else
        if (mois.equals("11")  ){
            return "Novembre";
        }
        else
        if (mois.equals("12")){
            return "Décembre";
        }
            return null;
    }

    public static String Get_date_en_francais(String date){

        String day=date.substring(8,10);
        String mois=date.substring(5,7);
        String month=getMonthInLetter(mois);
        String year=date.substring(0,4);

        return  day.concat(" "+month+" "+year);
    }

    public static String replace_accents(String source){
        String obs=source.replace('é','e');
        obs=obs.replace('è','e');
        obs=obs.replace('ê','e');
        obs=obs.replace('È','E');
        obs=obs.replace('É','E');
        obs=obs.replace('é','e');
        obs=obs.replace('Ê','E');

        obs=obs.replace('à','a');
        obs=obs.replace('â','a');
        obs=obs.replace('À','A');
        obs=obs.replace('á','a');
        obs=obs.replace('Á','A');
        obs=obs.replace('Â','a');
        obs=obs.replace('Ã','A');
        obs=obs.replace('ã','a');

        obs=obs.replace('ï','i');
        obs=obs.replace('ì','i');
        obs=obs.replace('ï','i');
        obs=obs.replace('Ì','I');
        obs=obs.replace('í','i');
        obs=obs.replace('Í','I');
        obs=obs.replace('î','i');
        obs=obs.replace('Î','I');

        obs=obs.replace('ò','o');
        obs=obs.replace('Ò','O');
        obs=obs.replace('Ó','O');
        obs=obs.replace('ó','o');
        obs=obs.replace('Ô','O');
        obs=obs.replace('ô','o');

        obs=obs.replace('ù','u');
        obs=obs.replace('Ù','U');
        obs=obs.replace('ú','u');
        obs=obs.replace('Ú','U');
        obs=obs.replace('û','u');
        obs=obs.replace('Û','U');


        obs=obs.replace('õ','o');
        obs=obs.replace('Õ','O');

        obs=obs.replace('ý','y');
        obs=obs.replace('Ý','Y');

        obs=obs.replace('ñ','n');
       String result=String.format(obs.replace('Ñ','N'));
       return  result;
    }


    public static String LeftPad(String inputString, int length, char value)
    {
        if(inputString.length() >= length)
        {
            return inputString ;
        }

        StringBuilder sb = new StringBuilder() ;
        while(sb.length() < length - inputString.length() )
        {
            sb.append(value) ;
        }

        return sb.toString()+inputString ;
    }

    public enum type_operation{Vente_avec_bonus,Vente_detail,Sortie,Entree}



    public static String spacer(String number){
        StringBuilder strB = new StringBuilder();
        strB.append(number);
        int Three = 0;

        for(int i=number.length();i>0;i--){
            Three++;
            if(Three == 3){
                strB.insert(i-1, " ");
                Three = 0;
            }
        }
        return strB.toString();
    }// end Spacer()

}
