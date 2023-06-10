package cd.sklservices.com.Beststockage.layout.Parametres;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Client;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.AdresseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ClientViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.QuarterViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.StreetViewModel;

/**
 * Created by SKL on 03/06/2019.
 */

public class ProduitFactureDetailsView extends Fragment {

    private IdentityViewModel identityViewModel ;
    private AdresseViewModel adresseViewModel ;
    private StreetViewModel streetViewModel ;
    private QuarterViewModel quarterViewModel ;

    View rootView;
    ListView listViewClient;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details_client, container, false);
        init();
        return rootView;
    }

    void init(){

        this.adresseViewModel = new ViewModelProvider(this).get(AdresseViewModel.class) ;
        this.identityViewModel = new ViewModelProvider(this).get(IdentityViewModel.class) ;
        this.streetViewModel = new ViewModelProvider(this).get(StreetViewModel.class) ;
        this.quarterViewModel = new ViewModelProvider(this).get(QuarterViewModel.class) ;

        listViewClient =(ListView)rootView.findViewById(R.id.lv_details_client);

        fillData();

    }
    public void fillData(){
        try {

        Identity instance = identityViewModel.getIdentity() ;
        Client client=new ClientViewModel(getActivity().getApplication()).get(instance.getId(),true);

        String address =instance.getAddress().getNumber() +", Av. "+ instance.getAddress().getStreet().getName()+
                ", Q. "+instance.getAddress().getQuarter().getName()+", "+instance.getAddress().getTown().getName()+
                "/"+instance.getAddress().getTownship().getName();


            ArrayList<String> mylistDetail = new ArrayList<>()  ;
            if(client.getHuman().getGender().equals("Masculin")){
                mylistDetail.add("Mr. "+instance.getName());
            }else if(client.getHuman().getGender().equals("Feminin")){
                mylistDetail.add("Mme. "+instance.getName());
            }else{
                mylistDetail.add(instance.getName());
            }

            mylistDetail.add(instance.getType());
            if(instance.getTelephone2().length()>4 || instance.getTelephone3().length()>4){
                mylistDetail.add("Tel 1 : "+ instance.getTelephone());
            }
            else {
                mylistDetail.add("Tel : "+ instance.getTelephone());
            }

            if(instance.getTelephone2().length()>4)
                mylistDetail.add("Tel 2 : "+ instance.getTelephone2());
            if(instance.getTelephone3().length()>4)
                mylistDetail.add("Tel 3 : "+ instance.getTelephone3());
            mylistDetail.add(address);
            mylistDetail.add("Réf : "+instance.getAddress().getReference());

            mylistDetail.add("Statut : "+client.getStatut());
            mylistDetail.add("Ajouté depuis : "+instance.getAdding_date());

            DetailAdapter adapter = new DetailAdapter(getContext(), mylistDetail);
            listViewClient.setAdapter(adapter);

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
