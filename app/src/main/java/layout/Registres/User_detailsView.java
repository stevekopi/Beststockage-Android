package layout.Registres;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Registres.AgenceAdapter;
import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;
import cd.sklservices.com.Beststockage.R;

/**
 * Created by SKL on 03/06/2019.
 */

public class User_detailsView extends Fragment {

    private UserViewModel userViewModel ;
    private AgenceViewModel agenceViewModel ;
    View rootView;

    ListView lvAgence;
    FloatingActionButton btn_open, btn_close, btn_delete ;
    BaseAdapter adapterAgence ;
    ListView lvUser;

    User user ;
    String id_current_user = "" ;
    String id_current_agence = "" ;
    String id_role = "" ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details_user, container, false);
        init();
        return rootView;
    }

    void init(){

        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;

        lvUser = rootView.findViewById(R.id.lv_user);
        lvAgence = rootView.findViewById(R.id.lv_agence_user);

        btn_close = rootView.findViewById(R.id.btn_close_user);
        btn_open = rootView.findViewById(R.id.btn_open_user);
        btn_delete = rootView.findViewById(R.id.btn_delete_user);

        id_current_user =  ((MainActivity)getActivity()).getCurrentUser().getId() ;
        id_current_agence =  ((MainActivity)getActivity()).getCurrentUser().getAgence_id() ;
        id_role =  ((MainActivity)getActivity()).getCurrentUser().getUser_role_id() ;

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setStatut("Operationnel");
                user.setSync_pos(0);
                userViewModel.ajout_sync(user);

                Toast.makeText(getActivity(), "L'utilisateur a été activé avec succès !!!", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).afficherUtilisateur();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setStatut("Non operationnel");
                user.setSync_pos(0);
                userViewModel.ajout_sync(user);

                Toast.makeText(getActivity(), "L'utilisateur a été desactivé avec succès !!!", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).afficherUtilisateur();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        fillData();

    }

    public void fillData(){
        user = userViewModel.getUser() ;
        Agence agence = agenceViewModel.get(userViewModel.getAgence_id(),true,false) ;

        if(id_current_user.equals(user.getId()) || !id_role.equals("00")){
            btn_open.setVisibility(View.GONE);
            btn_close.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
        }
        else if(user.getStatut().equals("Operationnel"))
        {
            btn_open.setVisibility(View.GONE);
            btn_close.setVisibility(View.VISIBLE);
        }
        else
        {
            btn_open.setVisibility(View.VISIBLE);
            btn_close.setVisibility(View.GONE);
        }

        ArrayList<String> mylistDetail = new ArrayList<>()  ;

        mylistDetail.add(user.getHuman().getIdentity().getName().toUpperCase(Locale.ROOT));
        mylistDetail.add(agence.getDenomination());
        mylistDetail.add(user.getRole().getDesignation());

        if(user.getHuman().getIdentity().getTelephone2().length()>4 || user.getHuman().getIdentity().getTelephone3().length()>4){
            mylistDetail.add("Tel 1 : "+ user.getHuman().getIdentity().getTelephone());
        }
        else {
            mylistDetail.add("Tel   : "+ user.getHuman().getIdentity().getTelephone());
        }

        if(user.getHuman().getIdentity().getTelephone2().length()>4)
            mylistDetail.add("Tel 2 : "+ user.getHuman().getIdentity().getTelephone2());
        if(user.getHuman().getIdentity().getTelephone3().length()>4)
            mylistDetail.add("Tel 3 : "+ user.getHuman().getIdentity().getTelephone3());

       String address =user.getHuman().getIdentity().getAddress().getNumber() +", Av. "+ user.getHuman().getIdentity().getAddress().getStreet().getName()+
                ", Q. "+user.getHuman().getIdentity().getAddress().getQuarter().getName()+", "+user.getHuman().getIdentity().getAddress().getTown().getName()+
                "/"+user.getHuman().getIdentity().getAddress().getTownship().getName();
       mylistDetail.add(address);
        mylistDetail.add("Réf : "+user.getHuman().getIdentity().getAddress().getReference());

        DetailAdapter adapter = new DetailAdapter(getContext(), mylistDetail);
        lvUser.setAdapter(adapter);

        List<Agence> arrayList = new ArrayList<>();
        arrayList.add(user.getAgence());

        adapterAgence =new AgenceAdapter(getContext(), arrayList,true);
        lvAgence.setAdapter(adapterAgence);

    }
}
