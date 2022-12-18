package layout;

import static cd.sklservices.com.Beststockage.ViewModel.TableKeyIncrementorViewModel.keygen;

import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Classes.Agence;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.*;

public class Bonlivraison_detailsView extends Fragment {

    private AgenceViewModel agenceViewModel ;
    private ArticleViewModel articleViewModel ;
    private OperationViewModel operationViewModel ;
    private UserViewModel userViewModel ;
    private LigneBonLivraisonViewModel ligneBonLivraisonViewModel ;
    private LivraisonViewModel livraisonViewModel ;
    private BonLivraisonViewModel bonLivraisonViewModel ;

    private VehiculelViewModel vehiculeViewModel ;
    private DriverViewModel driverViewModel ;
    private ConvoyeurViewModel convoyeurViewModel ;
    private DelegueViewModel delegueViewModel ;

    private LigneBonlivraison ligneBonLivraison ;
    private Operation operation ;
    private Bonlivraison bonlivraison ;

    private FloatingActionButton btn_delete, btn_update ;

    private String id_current_user, id_current_agence ;

    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;
    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_synchronisation ;
    SearchView sv_agences_filter;
    static ListView lvLivraison;
    List<LigneCommande> lignesC;


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Livraison");

        Log.d("Chrono","Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Chrono","onPause");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Chrono","onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Chrono","onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Chrono","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Chrono","onDestroy");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Chrono","onStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
                // Inflate the layout for this fragment
                rootView=inflater.inflate(R.layout.fragment_details_livraison1, container,false);
                LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

                btn_delete =  rootView.findViewById(R.id.btn_delete_livraison1);
                btn_update =  rootView.findViewById(R.id.btn_update_livraison1);

                id_current_user =  ((MainActivity)getActivity()).getCurrentUser().getId() ;
                id_current_agence =  ((MainActivity)getActivity()).getCurrentUser().getAgence_id() ;

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            // int taille_deleted =  deletedViewModel.getList().size() ;
                            String Today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                            String key_deleted = keygen(getActivity(), "","deleted");

                           /* Deleted deleted = new Deleted(id_current_agence, id_current_user, ligneBonLivraison.getId(), "ligne_bonlivraison", Today);
                            deleted.setId(key_deleted);

                            String key_deleted2 = keygen(getActivity(), "","deleted");

                            Deleted deleted2 = new Deleted(id_current_agence, id_current_user, operation.getId(), "operation", Today);
                            deleted2.setId(key_deleted2);

                             // taille_deleted++ ;
                            // String key_deleted3 = MesOutils.keygen(getActivity(), "", id_current_agence, id_current_user,
                            //        taille_deleted, "deleted");

                           // Deleted deleted3 = new Deleted(id_current_agence, id_current_user, bonlivraison.getId(),
                            //        "bonlivraison", Today);
                            // deleted3.setId(key_deleted3);

                            deletedViewModel.ajout(deleted);
                            deletedViewModel.ajout(deleted2);

                            //  bonlivraison.setSync_pos(3);
                            //  bonLivraisonViewModel.update (bonlivraison);

                            ligneBonLivraison.setSync_pos(3);
                            ligneBonLivraisonViewModel.update(ligneBonLivraison);

                           // operation.setSync_pos(3);
                           // operationViewModel.update2(operation);

                            Toast.makeText(getActivity(), "La livraison a été supprimée !!!", Toast.LENGTH_LONG).show();

                            ((MainActivity) getActivity()).afficherLivraison1();

                            */
                        }

                        catch (Exception e) {
                            Toast.makeText(getActivity(), "Impossible de supprimer la livraison !!!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       ((MainActivity)getActivity()).updateLivraison2(ligneBonLivraison);
                    }
                });

                footer= li.inflate(R.layout.footer_view,null);
                init();

            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
            }

        return rootView;
    }


    private void  init() {

        this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class) ;
        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        this.ligneBonLivraisonViewModel = new ViewModelProvider(this).get(LigneBonLivraisonViewModel.class) ;
        this.bonLivraisonViewModel = new ViewModelProvider(this).get(BonLivraisonViewModel.class) ;

        this.vehiculeViewModel = new ViewModelProvider(this).get(VehiculelViewModel.class) ;
        this.driverViewModel = new ViewModelProvider(this).get(DriverViewModel.class) ;
        this.convoyeurViewModel = new ViewModelProvider(this).get(ConvoyeurViewModel.class) ;
        this.delegueViewModel = new ViewModelProvider(this).get(DelegueViewModel.class) ;

        lvLivraison =(ListView)rootView.findViewById(R.id.lv_livraison1);

        fillData();
    }

    private void fillData()
    {
        try {

            ligneBonLivraison = ligneBonLivraisonViewModel.getLigneBonLivraison();
            bonlivraison = bonLivraisonViewModel.get(ligneBonLivraison.getBonlivraison_id(),true) ;
            operation = operationViewModel.get(ligneBonLivraison.getOperation_id(),false,false) ;

            if(!id_current_user.equals(operation.getUser_id()))
            {
                btn_update.setVisibility(View.GONE);
                btn_delete.setVisibility(View.GONE);
            }

            String Vehicule = "" ; String Driver = "" ;   String Convoyeur = "" ;   String Delegue = "" ;

            ArrayList<String> mylistDetail = new ArrayList<>()  ;

            Agence agence = agenceViewModel.get(operation.getAgence_1_id(),false,false) ;
            Article article = articleViewModel.get(operation.getArticle_id(),true,true) ;

            Vehicule V = vehiculeViewModel.get(bonlivraison.getVehicule_id()) ;
            Vehicule = V.getMarque()+" "+V.getMatricule() ;


            Driver D = driverViewModel.get(bonlivraison.getDriver_id()) ;

                    Driver = D.getName() ;


            Convoyeur C = convoyeurViewModel.get(bonlivraison.getConvoyeur_id()) ;
            Convoyeur = C.getName() ;


            Delegue Del = delegueViewModel.get(bonlivraison.getDelegue_id()) ;
            Delegue = Del.getName() ;


                mylistDetail.add(agence.getDenomination().toString());
                mylistDetail.add(article.getDescription().toString());
                mylistDetail.add(String.valueOf(operation.getQuantite()));
                mylistDetail.add(String.valueOf(operation.getBonus()) );
                mylistDetail.add(String.valueOf(operation.getMontant()) + " " +
                        String.valueOf(operation.getDevise_id()));
                mylistDetail.add(String.valueOf(operation.getDate()));
                mylistDetail.add("Vehicule      : " + String.valueOf( Vehicule ));
                mylistDetail.add("Delegue       : " + String.valueOf( Delegue ));
                mylistDetail.add("Conducteur    : " +  String.valueOf( Driver ));
                mylistDetail.add("Convoyeur     : " +  String.valueOf( Convoyeur ));

                DetailAdapter adapter = new DetailAdapter(getContext(), mylistDetail);
                lvLivraison.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

    }


public static void agence_refresh_end(){
    swipeRefreshLayout.setRefreshing(false);
}

public class myHandler extends Handler{
    @Override
    public void handleMessage(Message msg) {
       switch (msg.what){
           case 0:
               //inserer le view de loading pendant la recherche
              // lvCommande.addFooterView(footer);
               //Update data adapter and UI
       }

    }
}

}
