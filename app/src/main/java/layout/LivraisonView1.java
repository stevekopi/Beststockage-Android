package layout;

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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Livraison1_expandable_adapter;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ApprovisionnementViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.LivraisonViewModel;

public class LivraisonView1 extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= StockView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private AgenceViewModel agenceViewModel;
    private LivraisonViewModel livraisonViewModel;
    private ApprovisionnementViewModel approvisionnementViewModel;
    SwipeRefreshLayout swipeRefreshLayout;

    View rootView;

    ExpandableListView elv_livraison;
    ExpandableListAdapter ela_livraison;
    List<String> periode;
    Map<String,List<Approvisionnement>> approvisionnement;
    TextView tv_livraison_count;
    SearchView sv_filter;
    FloatingActionButton btn_add_livraison ;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Livraison");
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
        rootView =inflater.inflate(R.layout.fragment_livraison1, container, false);

        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_livraison1);

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

        btn_add_livraison = (FloatingActionButton) rootView.findViewById(R.id.btn_add_livraison1);

        btn_add_livraison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).addLivraison1();
            }
        });
        btn_add_livraison.setVisibility(View.GONE);
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;
        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;
        this.livraisonViewModel = new ViewModelProvider(this).get(LivraisonViewModel.class) ;
        this.approvisionnementViewModel = new ViewModelProvider(this).get(ApprovisionnementViewModel.class) ;

        sv_filter =(SearchView) rootView.findViewById(R.id.sv_livraison1_filter);

        sv_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> result=new ArrayList<>();
                for (String x: periode){
                    if (x.toLowerCase().contains(newText.toLowerCase()))
                    {
                        result.add(x);
                    }
                }

                ((Livraison1_expandable_adapter) elv_livraison.getExpandableListAdapter()).update(result);
                count_element();
                return false;
            }
        });

        periode = livraisonViewModel.getDistinctPeriode() ;

        fillData();

        elv_livraison =(ExpandableListView) rootView.findViewById(R.id.elv_livraison1);
        ela_livraison =new Livraison1_expandable_adapter(getContext(), livraisonViewModel ,agenceViewModel ,articleViewModel, periode
                , approvisionnement);
        elv_livraison.setAdapter(ela_livraison);

        count_element();

        elv_livraison.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               //  Article article = articleViewModel.get(stock.get(agences.get(groupPosition)).get(childPosition).getArticle_id()) ;

               // Toast.makeText(getContext(), agences.get(groupPosition).getDenomination()
              //          +" : "+ article.getDesignation() + "  " + article.getPrix() + " " + article.getDevise() ,Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }

    private void count_element() {
        tv_livraison_count=(TextView) rootView.findViewById(R.id.tv_livraison1_count);
        tv_livraison_count.setText("Livraison ("+elv_livraison.getCount()+")");
    }


    private void fillData(){
        approvisionnement =new HashMap<>();

        try {
            if (periode != null) {
                for (int k = 0; k < periode.size(); k++) {
                    approvisionnement.put(periode.get(k), approvisionnementViewModel.getByPeriode(periode.get(k)));
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(),   ex.toString(), Toast.LENGTH_LONG).show();
        }


    }


}


