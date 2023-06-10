package cd.sklservices.com.Beststockage.layout.Registres;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Adapters.Registres.UserAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.AdresseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.QuarterViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.StreetViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by SKL on 03/06/2019.
 */

public class Agence_detailsView extends Fragment {

    private AgenceViewModel agenceViewModel ;
    private AdresseViewModel adresseViewModel ;
    private UserViewModel userViewModel ;
    private StreetViewModel streetViewModel ;
    private QuarterViewModel quarterViewModel ;

    View rootView;
    ListView listViewAgence;
    ListView listViewDetailsAgence;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details_agence, container, false);
        init();
        return rootView;
    }

    void init(){

        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;
        this.adresseViewModel = new ViewModelProvider(this).get(AdresseViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        this.streetViewModel = new ViewModelProvider(this).get(StreetViewModel.class) ;
        this.quarterViewModel = new ViewModelProvider(this).get(QuarterViewModel.class) ;

        listViewDetailsAgence=rootView.findViewById(R.id.lv_details_agence_user);
        listViewAgence=rootView.findViewById(R.id.lv_details_agence);

        fillData();

    }
    public void fillData(){
        try {

            Agence instance = agenceViewModel.getAgence() ;
            String address =instance.getAddress().getNumber() +", Av. "+ instance.getAddress().getStreet().getName()+
                    ", Q. "+instance.getAddress().getQuarter().getName()+", "+instance.getAddress().getTown().getName()+
                    "/"+instance.getAddress().getTownship().getName();


            ArrayList<String> mylistDetail = new ArrayList<>();

            mylistDetail.add(instance.getDenomination());
            mylistDetail.add( instance.getType());
            if(instance.getTelephone2().length()>4 || instance.getTelephone3().length()>4){
                mylistDetail.add("Tel 1 : "+ instance.getTel());
            }
            else {
                mylistDetail.add("Tel : "+ instance.getTel());
            }

            if(instance.getTelephone2().length()>4)
                mylistDetail.add("Tel 2 : "+ instance.getTelephone2());
            if(instance.getTelephone3().length()>4)
                mylistDetail.add("Tel 3 : "+ instance.getTelephone3());
            mylistDetail.add(address);
            mylistDetail.add("Réf : "+instance.getAddress().getReference());

            DetailAdapter adapter = new DetailAdapter(getContext(), mylistDetail);
            listViewAgence.setAdapter(adapter);

            ArrayList<User> arrayList = userViewModel.getByAgenceId(agenceViewModel.getId());

            UserAdapter adapter2 = new UserAdapter(getContext(), agenceViewModel, arrayList);
            listViewDetailsAgence.setAdapter(adapter2);

            agenceViewModel.setAgence(null);
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
