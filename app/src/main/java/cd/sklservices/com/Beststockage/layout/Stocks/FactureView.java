package cd.sklservices.com.Beststockage.layout.Stocks;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Stocks.FactureTreeLevelListViewAdapter;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.FactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class FactureView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= FactureView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private FactureViewModel factureViewModel;
    private ArticleProduitFactureViewModel articleProduitFactureViewModel;
    private UserViewModel userViewModel  ;
    private IdentityViewModel identityViewModel;

    View loadingProgress;
    FloatingActionButton btn_add ;

    ExpandableListView expandableListView;
    TextView tv_date_count;
    SwipeRefreshLayout swipeRefreshLayout;

    List<String> parent   ;
    LinkedHashMap<String,LigneFacture[]> thirdlevel ;

    /**
     * Second level arraylist
     */
    List<Facture[]> secondlevel=new ArrayList<>();

    /**
     * Inner level data
     */
    List<LinkedHashMap<String,LigneFacture[]>> data=new ArrayList<>();


    View rootView;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(COMMON_TAG,"Fragment FactureView SaveInstance");
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Facture");
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

        rootView =inflater.inflate(R.layout.fragment_facture, container, false);
        loadingProgress= (ProgressBar)rootView.findViewById(R.id.pb_facture_loading);
        loadingProgress.setVisibility(View.GONE);

        try {

            init() ;

            swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_facture);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    init();
                    refresh_end();
                }
            });



        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    private void refresh_end() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void count_element() {
        tv_date_count.setText("Dates ("+expandableListView.getCount()+")");
    }

    private  void setUpAdapter(){

        secondlevel = factureViewModel.gets(parent) ;
        List<String> storeId = new ArrayList<String>() { } ;

        for (int e=0;e<secondlevel.size();e++){
            thirdlevel = new LinkedHashMap<>();

            // int s=(secondlevel.get(e)).length ;

            for(Facture facture: secondlevel.get(e))
            {
                try {
                    if(!storeId.contains(facture.getId()))
                    {
                        storeId.add(facture.getId()) ;
                        thirdlevel.put(facture.getId(), new LigneFactureViewModel(getActivity().getApplication()).getByFactureId(facture.getId()));
                    }
                }
                catch (Exception ex) {
                    Toast.makeText(getActivity(), " Exception = " +  String.valueOf( ex ), Toast.LENGTH_LONG ).show();
                }
            }
            data.add(thirdlevel);
        }

        expandableListView=(ExpandableListView) rootView.findViewById(R.id.elv_facture);

        FactureTreeLevelListViewAdapter factureTreeLevelListViewAdapter =
                new FactureTreeLevelListViewAdapter(getContext(), articleProduitFactureViewModel,identityViewModel, userViewModel, parent,secondlevel,data);

        expandableListView.setAdapter(factureTreeLevelListViewAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousgroup=-1;

            @Override
            public void onGroupExpand(int groupPosition) {

                if (groupPosition!=previousgroup){
                    expandableListView.collapseGroup(previousgroup);
                    previousgroup=groupPosition;
                }
            }
        });
    }

    private void init()
    {
        this.factureViewModel = new ViewModelProvider(this).get(FactureViewModel.class) ;
        this.identityViewModel = new ViewModelProvider(this).get(IdentityViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        this.articleProduitFactureViewModel = new ViewModelProvider(this).get(ArticleProduitFactureViewModel.class) ;

        parent = factureViewModel.getDistinctDates() ;
        thirdlevel = new LinkedHashMap<>();

        Log.i(TAG,FRAGMENT_NAME+ " onCreateView");
        // Inflate the layout for this fragment


        tv_date_count =(TextView)rootView.findViewById(R.id.tv_facture_count);

        btn_add =(FloatingActionButton)rootView.findViewById(R.id.btn_add_facture);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).addFacture();
            }
        });


        setUpAdapter();

        //Compter les éléments de la treeLevelListView
        count_element();

    }
}


