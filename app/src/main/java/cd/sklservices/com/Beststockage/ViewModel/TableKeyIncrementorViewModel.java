package cd.sklservices.com.Beststockage.ViewModel;

import static android.text.TextUtils.concat;
import static cd.sklservices.com.Beststockage.Outils.MesOutils.LeftPad;

import android.app.Application;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.TableKeyIncrementor;
import cd.sklservices.com.Beststockage.Repository.TableKeyIncrementorRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class TableKeyIncrementorViewModel extends AndroidViewModel {

    private static TableKeyIncrementorRepository repository ;

    public TableKeyIncrementorViewModel(@NonNull Application application) {
        super(application);
        repository = new TableKeyIncrementorRepository(application) ;
    }

    public void update (TableKeyIncrementor instance)
    {
        instance.setSync_pos(0);
        instance.setPos(instance.getPos()+1);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }


    public static String keygen(FragmentActivity activity, String v_prefixe, String  table_name){


        String v_id=null ;
        int position =0;
        int key_length = 4;
        String sec;
        String v_agence_id=MainActivity.getCurrentUser().getAgence_id();
        String v_current_user_id=MainActivity.getCurrentUser().getId();

        // select  concat(substr(rand(),3,2),substr(lpad(second(now()),2,0),2,1)) into v_sec;

        String rand=String.valueOf(Math.random());
        sec= rand.substring(3,5);
        String now=MainActivity.getAddingDate().substring(17,19);
        String Lp1=LeftPad(now,2,'0');
        String substringLefPad=Lp1.substring(1,2);

        sec=sec+substringLefPad; //Recuperationde 3 caractères du sec : Second+random

        repository=new TableKeyIncrementorRepository(TableKeyIncrementorRepository.getContext());

        position=repository.getPosition(table_name);
        if(position==0){
            position=1;
            TableKeyIncrementor instance=new TableKeyIncrementor(MainActivity.getCurrentUser().getAgence_id(),table_name,position,
                    MainActivity.getAddingDate(),MainActivity.getAddingDate(),0,0);
            repository.ajout_sync(instance);

        }
        else{
            TableKeyIncrementor instance=repository.get(table_name);
            instance.setPosition(instance.getPosition()+1);
            instance.setPos(instance.getPos()+1);
            instance.setSync_pos(0);
            instance.setUpdated_date(MainActivity.getAddingDate());
            repository.ajout_sync(instance);

        }

        if(table_name.equals("identity"))
        {
         //   v_id =concat(concat(v_agence_id,v_sec), LPAD(v_pos, 14 - length(concat(v_agence_id,v_sec)), '0'));
            v_id=v_agence_id+sec+LeftPad(String.valueOf(position),14-String.valueOf(v_agence_id+sec).length(),'0');
        }
        else if(table_name.equals("commande") || table_name.equals("bon") || table_name.equals("livraison"))
        {
           // set v_id = concat(concat(v_prefixe,'/',lpad(v_current_user_id,14,0),'/',v_sec,'-'),LPAD(v_pos, 28 - length(concat(v_prefixe,'/',lpad(v_current_user_id,14,0),'/',v_sec,'-')), '0'));
             v_id =v_prefixe+"/"+LeftPad(v_current_user_id,14,'0')+"/"+sec+"-"+LeftPad(String.valueOf(position), 28 - String.valueOf(concat(v_prefixe+"/"+LeftPad(v_current_user_id,14,'0')+"/"+sec+"/")).length(), '0');
        }
	   else
        {
           // set v_id = concat(concat(concat(lpad(v_current_user_id,14,0),v_sec)),LPAD(v_pos,32 - length(concat(concat(lpad(v_current_user_id,14,0),v_sec))) ,'0'));
              v_id = LeftPad(v_current_user_id,14,'0')+sec+LeftPad(String.valueOf(position),32 - String.valueOf(LeftPad(v_current_user_id,14,'0')+sec).length() ,'0');
        }

        return v_id;
    }

}
