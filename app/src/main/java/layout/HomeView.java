package layout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Classes.Stocks.Bonlivraison;
import cd.sklservices.com.Beststockage.Classes.Stocks.Commande;
import cd.sklservices.com.Beststockage.Classes.RapportCaisseParDate;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.BonLivraisonViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.CommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Finances.RapportCaisseParDateViewModel;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class HomeView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= HomeView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;
    private RapportCaisseParDateViewModel rapportCaisseParDateViewModel ;
    private BonLivraisonViewModel bonLivraisonViewModel ;
    private CommandeViewModel commandeViewModel ;
    private UserViewModel userViewModel ;

    View rootView;
    ListView lv_homeView ;
    FloatingActionButton btn_rapport_caisse ;

      @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(COMMON_TAG,"Fragment HomeView SaveInstance");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG,FRAGMENT_NAME+ " onAttache");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,FRAGMENT_NAME+ " onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Accueil");
        Log.i(TAG,FRAGMENT_NAME+ " onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,FRAGMENT_NAME+ " onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,FRAGMENT_NAME+ " onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,FRAGMENT_NAME+ " onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,FRAGMENT_NAME+ " onCreateView");
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_home, container, false);
        lv_homeView = (ListView)rootView.findViewById(R.id.lv_homeView) ;

        init();
        return rootView;
    }

    private void init(){

        this.rapportCaisseParDateViewModel = new ViewModelProvider(this).get(RapportCaisseParDateViewModel.class) ;
        this.bonLivraisonViewModel = new ViewModelProvider(this).get(BonLivraisonViewModel.class) ;
        this.commandeViewModel = new ViewModelProvider(this).get(CommandeViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;

        fillData ();

    }


    private void fillData(){
             try{


                 Bonlivraison bl = bonLivraisonViewModel.getLast() ;
                 User user = userViewModel.get(bl.getUser_id(),true,false) ;
                 RapportCaisseParDate rapdat = rapportCaisseParDateViewModel.getLast();
                 Commande cmd = commandeViewModel.getLast() ;

                 ArrayList<String> mylistDetail = new ArrayList<>()  ;

                 mylistDetail.add("Dernier mouvement : le "+ rapdat.getDate().substring(0,10));

                 mylistDetail.add(rapdat.getMontantBrut() + " Fc");

                 mylistDetail.add(rapdat.getDepense() + " Fc" );

                 mylistDetail.add(rapdat.getReste() + " Fc");

                 mylistDetail.add("Bon n° " +   bl.getId());

                 mylistDetail.add("Dernier mouvement : le "+ bl.getAdding_date().substring(0,10) );

                 mylistDetail.add("Utilisateur " +  user.getId());

                 mylistDetail.add("Commande n° " +  cmd.getId());

                 mylistDetail.add("Date " + cmd.getDate().substring(0,10));

                 String id=cmd.getId();
                 int sqa = commandeViewModel.getSommeQuantiteApprovisionner(id) ;
                 int sqc = commandeViewModel.getSommeQuantiteCommander(id) ;
                 int reste=sqc-sqa;

                 Double va=Double.valueOf(sqa);
                 Double vc=Double.valueOf(sqc);
                 Double pourcentage=(va/vc)*100;
                 int val=pourcentage.intValue();

                 String valPourc=String.valueOf(pourcentage);

                 if (valPourc.length()>4){
                     mylistDetail.add(valPourc.substring(0,5)+"% Livré");
                 }
                 else if (valPourc.length()<4){
                     mylistDetail.add( valPourc.substring(0,3)+"% Livré");
                 }

                 DetailAdapter adapter = new DetailAdapter(getContext(), mylistDetail);
                 lv_homeView.setAdapter(adapter);
             }
             catch (Exception e){
                 Log.d("Erreur","HomeView.filldataCaisse "+e.toString());

             }


    }

}


