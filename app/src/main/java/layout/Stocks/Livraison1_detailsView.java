package layout.Stocks;

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

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Stocks.Approvisionnement;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.ApprovisionnementViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LivraisonViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class Livraison1_detailsView extends Fragment {

    private AgenceViewModel agenceViewModel ;
    private ArticleViewModel articleViewModel ;
    private OperationViewModel operationViewModel ;
    private UserViewModel userViewModel ;
    private ApprovisionnementViewModel approvisionnementViewModel ;
    private LivraisonViewModel livraisonViewModel ;

    private Approvisionnement approvisionnement ;

    private FloatingActionButton btn_delete, btn_update ;

    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;
    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_synchronisation ;
    SearchView sv_agences_filter;
    static ListView lvLivraison;
    List<LigneCommande> lignesC;

    String id_current_agence = "" ;
    String id_current_user = "" ;


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

                btn_delete = (FloatingActionButton) rootView.findViewById(R.id.btn_delete_livraison1);
                btn_update = (FloatingActionButton) rootView.findViewById(R.id.btn_update_livraison1);

                id_current_agence =  ((MainActivity)getActivity()).getCurrentUser().getAgence_id();
                id_current_user =  ((MainActivity)getActivity()).getCurrentUser().getId();


                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).updateLivraison1(approvisionnement);
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
        this.approvisionnementViewModel = new ViewModelProvider(this).get(ApprovisionnementViewModel.class) ;
        this.livraisonViewModel = new ViewModelProvider(this).get(LivraisonViewModel.class) ;

        lvLivraison =(ListView)rootView.findViewById(R.id.lv_livraison1);

        fillData();
    }


    private void fillData()
    {
        try {

            approvisionnement = approvisionnementViewModel.getApprovisionnement() ;

            if(!id_current_user.equals(approvisionnement.getAdding_user_id()))
            {
                btn_update.setVisibility(View.GONE);
                btn_delete.setVisibility(View.GONE);
            }

            ArrayList<String> mylistDetail = new ArrayList<>()  ;

            Agence agence = agenceViewModel.get(approvisionnement.getAgence_id(),true,false) ;
            Article article = articleViewModel.get(approvisionnement.getArticle_id(),true,true) ;
            Operation operation = operationViewModel.get(approvisionnement.getOperation_id(),false,false) ;

                mylistDetail.add(agence.getDenomination().toString());
                mylistDetail.add(article.getDesignation().toString());
                mylistDetail.add(String.valueOf(operation.getQuantite()));
                mylistDetail.add(String.valueOf(operation.getBonus()));
                mylistDetail.add( String.valueOf(operation.getMontant()) + " " +
                        String.valueOf(operation.getDevise_id()));
                mylistDetail.add(String.valueOf(operation.getDate()));
                mylistDetail.add(String.valueOf(operation.getObservation()));

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
