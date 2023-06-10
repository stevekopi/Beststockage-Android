 package cd.sklservices.com.Beststockage.layout.Stocks;

import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Stocks.Commande_expandable_adapter;
import cd.sklservices.com.Beststockage.Classes.Stocks.Commande;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.CommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneCommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

 public class CommandeView extends Fragment {

    private CommandeViewModel commandeViewModel ;
    private ArticleViewModel articleViewModel ;
    private AgenceViewModel agenceViewModel ;
    private UserViewModel userViewModel ;

    private LigneCommandeViewModel ligneCommandeViewModel ;

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= CommandeView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    SwipeRefreshLayout swipeRefreshLayout;

    private IdentityViewModel identityViewModel ;

    private FloatingActionButton btn_add_commande ;

    View rootView;

    ExpandableListView elv_commande;
    ExpandableListAdapter ela_commande;
    List<Commande> parents;
    Map<Commande,List<LigneCommande>> enfants;
    TextView tv_count;
    SearchView sv_filter;

    private String id_current_role ;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Commande");
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
        rootView =inflater.inflate(R.layout.fragment_commande, container, false);

        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_commande);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                refresh_end();
            }
        });

        init();
        return rootView;
    }

    private void refresh_end() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void init(){

        this.commandeViewModel = new ViewModelProvider(this).get(CommandeViewModel.class) ;
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;
        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;
        this.ligneCommandeViewModel = new ViewModelProvider(this).get(LigneCommandeViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        this.identityViewModel = new ViewModelProvider(this).get(IdentityViewModel.class) ;

        sv_filter =(SearchView) rootView.findViewById(R.id.sv_commandes_filter);
        btn_add_commande =(FloatingActionButton) rootView.findViewById(R.id.btn_add_commande);

        id_current_role = ((MainActivity)getActivity()).getCurrentUser().getUser_role_id() ;

        if(id_current_role.equals("03"))
        {
            btn_add_commande.setVisibility(View.GONE);
        }

        btn_add_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).addCommande();
            }
        });

        sv_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Commande> result=new ArrayList<>();
                for (Commande x: parents){
                    if ((x.getDate() + " "+x.getId()).toLowerCase().contains(newText.toLowerCase()))
                    {
                        result.add(x);
                    }
                }

                ((Commande_expandable_adapter) elv_commande.getExpandableListAdapter()).update(result);
                count_element();
                return false;
            }
        });

        fillData();

        elv_commande =(ExpandableListView) rootView.findViewById(R.id.elv_commande);
        ela_commande = new Commande_expandable_adapter(getContext(), commandeViewModel, articleViewModel, agenceViewModel,
                identityViewModel, parents, enfants);
        elv_commande.setAdapter(ela_commande);

        count_element();

        elv_commande.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               // Toast.makeText(getContext(), " ***** ",Toast.LENGTH_LONG).show();
                /// Toast.makeText(getContext(), parents.get(groupPosition)+" : "+ enfants.get(parents.get(groupPosition)).get(childPosition),Toast.LENGTH_LONG).show();
                return false;
            }
        });



    }

    private void count_element() {
        tv_count =(TextView) rootView.findViewById(R.id.tv_commandes_count);
        tv_count.setText("Commandes ("+ elv_commande.getCount()+")");
    }


    private void fillData(){

        parents = commandeViewModel.getDistinct() ;
        enfants =new HashMap<>();

        for (int k = 0; k< parents.size(); k++){

             enfants.put(parents.get(k), ligneCommandeViewModel.ligne_commande_from_commande((parents.get(k)).getId()) );
            }


    }


}


