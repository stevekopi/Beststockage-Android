package cd.sklservices.com.Beststockage.layout.Parametres;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Locale;

import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.AdresseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.QuarterViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.StreetViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by SKL on 03/06/2019.
 */

public class DeviseDetailsView extends Fragment {

    private DeviseViewModel deviseViewModel ;
    private AdresseViewModel adresseViewModel ;
    private UserViewModel userViewModel ;
    private StreetViewModel streetViewModel ;
    private QuarterViewModel quarterViewModel ;

    View rootView;
    ListView listViewDevise;
    ListView lv_details_devise;
    TextView tv_id,tv_designation,tv_description;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details_devise, container, false);
        init();
        return rootView;
    }

    void init(){

        this.deviseViewModel = new ViewModelProvider(this).get(DeviseViewModel.class) ;

        tv_id=rootView.findViewById(R.id.tv_details_devise_id);
        tv_designation=rootView.findViewById(R.id.tv_details_devise_designation);
        tv_description=rootView.findViewById(R.id.tv_details_devise_description);
        lv_details_devise =rootView.findViewById(R.id.lv_details_devise);
        listViewDevise=rootView.findViewById(R.id.lv_details_devise);

        fillData();

    }
    public void fillData(){
        try {
            ArrayList<String> detailsList = new ArrayList<>();
            Devise instance = deviseViewModel.getDevise();
            tv_id.setText("N° "+instance.getId());
            tv_designation.setText(instance.getDesignation().toUpperCase(Locale.ROOT));
            tv_description.setText(instance.getDescription());
            detailsList.add("Code : "+instance.getCode());
            detailsList.add("Symbole : "+instance.getSymbole());
            if(instance.getIs_default_converter()==1){detailsList.add("Devise de conversion par défaut");}
            if(instance.getIs_local()==1){detailsList.add("Devise locale");}
            if(instance.getIs_default()==1){detailsList.add("Devise par défaut");}

            detailsList.add("Inséré depuis le "+ MesOutils.Get_date_en_francais_et_heure(instance.getAdding_date()));
            detailsList.add("Dernière modification le "+ MesOutils.Get_date_en_francais_et_heure(instance.getUpdated_date()));
            detailsList.add("Info Serveur");
            detailsList.add("SP : "+ instance.getSync_pos());
            detailsList.add("Pos : "+ instance.getPos());




            DetailAdapter adapter = new DetailAdapter(getContext(), detailsList);
            lv_details_devise.setAdapter(adapter);


            deviseViewModel.setDevise(null);
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
