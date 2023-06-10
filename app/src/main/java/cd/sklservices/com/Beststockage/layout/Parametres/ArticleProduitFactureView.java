package cd.sklservices.com.Beststockage.layout.Parametres;

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
import cd.sklservices.com.Beststockage.Adapters.Parametres.ArticleProduitFactureTreeLevelListViewAdapter;
import cd.sklservices.com.Beststockage.Adapters.Stocks.FactureTreeLevelListViewAdapter;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.FactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class ArticleProduitFactureView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= ArticleProduitFactureView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ProduitFactureViewModel produitFactureViewModel;
    private ArticleProduitFactureViewModel articleProduitFactureViewModel;

    View loadingProgress;
    FloatingActionButton btn_add ;

    ExpandableListView expandableListView;
    TextView tv_count;
    SwipeRefreshLayout swipeRefreshLayout;

    List<Devise> parent   ;
    LinkedHashMap<String, ArticleProduitFacture[]> thirdlevel ;

    /**
     * Second level arraylist
     */
    List<ProduitFacture[]> secondlevel=new ArrayList<>();

    /**
     * Inner level data
     */
    List<LinkedHashMap<String,ArticleProduitFacture[]>> data=new ArrayList<>();


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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Article Produit Facture");
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

        rootView =inflater.inflate(R.layout.fragment_article_produit_facture, container, false);
        loadingProgress= (ProgressBar)rootView.findViewById(R.id.pb_article_produit_facture_loading);
        loadingProgress.setVisibility(View.GONE);

        try {

            init() ;

            swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_article_produit_facture);

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
        tv_count.setText("Article Produit Facture ("+expandableListView.getCount()+")");
    }

    private  void setUpAdapter(){

        secondlevel = produitFactureViewModel.gets(parent) ;
        List<String> storeId = new ArrayList<String>() { } ;

        for (int e=0;e<secondlevel.size();e++){
            thirdlevel = new LinkedHashMap<>();

            // int s=(secondlevel.get(e)).length ;

            for(ProduitFacture produitFacture: secondlevel.get(e))
            {
                try {
                    if(!storeId.contains(produitFacture.getId()))
                    {
                        storeId.add(produitFacture.getId()) ;
                        thirdlevel.put(produitFacture.getId(), new ArticleProduitFactureViewModel(MainActivity.application).getByProduitFactureId(produitFacture.getId()));
                    }
                }
                catch (Exception ex) {
                    Toast.makeText(getActivity(), " ArticleProduitFactureView = " +  String.valueOf( ex ), Toast.LENGTH_LONG ).show();
                }
            }
            data.add(thirdlevel);
        }

        expandableListView=(ExpandableListView) rootView.findViewById(R.id.elv_article_produit_facture);

        ArticleProduitFactureTreeLevelListViewAdapter factureTreeLevelListViewAdapter =
                new ArticleProduitFactureTreeLevelListViewAdapter(getContext(), parent,secondlevel,data);

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
        Log.i(TAG,FRAGMENT_NAME+ " onCreateView");
        this.produitFactureViewModel = new ViewModelProvider(this).get(ProduitFactureViewModel.class) ;
        this.articleProduitFactureViewModel = new ViewModelProvider(this).get(ArticleProduitFactureViewModel.class) ;

        parent = produitFactureViewModel.getDistinctDevises() ;
        thirdlevel = new LinkedHashMap<>();


        // Inflate the layout for this fragment


        tv_count =(TextView)rootView.findViewById(R.id.tv_article_produit_facture_count);

       // btn_add =(FloatingActionButton)rootView.findViewById(R.id.btn_add_facture);

        //btn_add.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  ((MainActivity)getActivity()).addFacture();
            //}
      //  });


        setUpAdapter();

        //Compter les éléments de la treeLevelListView
        count_element();

    }
}


