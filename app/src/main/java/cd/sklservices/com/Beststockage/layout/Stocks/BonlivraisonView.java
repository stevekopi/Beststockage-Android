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
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Stocks.BonlivraisonTreeLevelListViewAdapter;
import cd.sklservices.com.Beststockage.Classes.Stocks.Bonlivraison;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneBonlivraison;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.BonLivraisonViewModel;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class BonlivraisonView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= BonlivraisonView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private BonLivraisonViewModel bonlivraisonViewModel ;
    private ArticleViewModel articleViewModel  ;
    private UserViewModel userViewModel  ;

    ExpandableListView expandableListView;
    TextView tv_dateLivraison_count;
    SwipeRefreshLayout swipeRefreshLayout;

    List<String> parent   ;
    LinkedHashMap<String,LigneBonlivraison[]> thirdlevel ;

    FloatingActionButton btn_add ;

    /**
     * Second level arraylist
     */
    List<Bonlivraison[]> secondlevel=new ArrayList<>();

    /**
     * Inner level data
     */
    List<LinkedHashMap<String,LigneBonlivraison[]>> data=new ArrayList<>();


    View rootView;


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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Bon de livraison");
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

        rootView =inflater.inflate(R.layout.fragment_livraison, container, false);
        try {
            init() ;

            swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_livraison);

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
        tv_dateLivraison_count.setText("Dates de livraisons ("+expandableListView.getCount()+")");
    }

private  void setUpAdapter(){

    secondlevel = bonlivraisonViewModel.getBons(parent) ;
    List<String> storeBonId = new ArrayList<String>() { } ;

    for (int e=0;e<secondlevel.size();e++){
        thirdlevel = new LinkedHashMap<>();

       // int s=(secondlevel.get(e)).length ;

        for(Bonlivraison bon: secondlevel.get(e))
                {
                    try {
                        thirdlevel.put(bon.getId(), bonlivraisonViewModel.getByBonlivraisonId(bon.getId()));

                            if(!storeBonId.contains(bon.getId()))
                            {
                               storeBonId.add(bon.getId()) ;
                                thirdlevel.put(bon.getId(), bonlivraisonViewModel.getByBonlivraisonId(bon.getId()));
                            }

                    }
                    catch (Exception ex) {
                      Toast.makeText(getActivity(), " Exception = " +  String.valueOf( ex ), Toast.LENGTH_LONG ).show();
                }
        }
        data.add(thirdlevel);
    }

    expandableListView=(ExpandableListView) rootView.findViewById(R.id.elv_livraison);

    BonlivraisonTreeLevelListViewAdapter bonlivraisonTreeLevelListViewAdapter =
            new BonlivraisonTreeLevelListViewAdapter(getContext(),  articleViewModel, userViewModel, parent,secondlevel,data);

    expandableListView.setAdapter(bonlivraisonTreeLevelListViewAdapter);
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
      this.bonlivraisonViewModel = new ViewModelProvider(this).get(BonLivraisonViewModel.class) ;
      this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
      this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;

      parent = bonlivraisonViewModel.getDistinctDates() ;
      thirdlevel = new LinkedHashMap<>();

      Log.i(TAG,FRAGMENT_NAME+ " onCreateView");
      // Inflate the layout for this fragment


      tv_dateLivraison_count=(TextView)rootView.findViewById(R.id.tv_livraisons_count);

      btn_add =(FloatingActionButton)rootView.findViewById(R.id.btn_add_livraison);

      btn_add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ((MainActivity)getActivity()).addLivraison2();
          }
      });

      btn_add.setVisibility(View.GONE);

      setUpAdapter();

      //Compter les éléments de la treeLevelListView
      count_element();

  }




}


