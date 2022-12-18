package layout;

import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Context;
import android.media.metrics.Event;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.StockAdapter;
import cd.sklservices.com.Beststockage.Adapters.Stock_expandable_adapter;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Repository.ArticleRepository;
import cd.sklservices.com.Beststockage.Repository.StockRepository;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.StockViewModel;

public class StockView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= StockView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private StockViewModel stockViewModel;
    private OperationViewModel operationViewModel;
    private AgenceViewModel agenceViewModel;
    static SwipeRefreshLayout swipeRefreshLayout;

    View rootView;

    ListView lv_stock;
    StockAdapter stockAdapter;
    Agence agence;
    TextView tv_stocks_count;
    SearchView sv_filter;
    List<Stock> instances=new ArrayList<>();
    ProgressBar progressBar;
    public View footer;



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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Stock");
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
        rootView =inflater.inflate(R.layout.fragment_stock2, container, false);
        LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).
                getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        footer= li.inflate(R.layout.footer_view,null);

        lv_stock=rootView.findViewById(R.id.lv_stock);
        lv_stock.addFooterView(footer);
        stockAdapter=new StockAdapter(getContext(),instances);
        lv_stock.setAdapter(stockAdapter);
        sv_filter = rootView.findViewById(R.id.sv_stocks_filter);

        sv_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Stock> result=new ArrayList<>();
                for (Stock instance : instances) {
                    if ((instance.getArticle().getDescription().toLowerCase()).contains(newText.toLowerCase())) {
                        result.add(instance);
                    }
                }
                stockAdapter.update(result);
                count_element();
                return false;
            }
        });

        swipeRefreshLayout=rootView.findViewById(R.id.refresh_stock);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
             init();
             refresh_end() ;
            }

        });

        init();
        return rootView;
    }

    private void init(){

        if(loadStock.isAlive()){
            Toast.makeText(getContext(),"Patientez car un traitement est déjà en cours",Toast.LENGTH_LONG).show();
        }
       else {

           count_element();
            if (loadStock.getState().equals(Thread.State.TERMINATED)){

                Toast.makeText(getContext(),"Cliquez sur le bouton 'Stock disponible' du menu\npour recharger",Toast.LENGTH_LONG).show();
            }
            else {
                loadStock.start();
            }


       }
    }

    private void count_element() {
        tv_stocks_count= rootView.findViewById(R.id.tv_stocks_count);
        tv_stocks_count.setText("Stocks ("+ String.valueOf(lv_stock.getCount() - 1)+")");
    }

    public static void refresh_end(){
        swipeRefreshLayout.setRefreshing(false);
    }


   // List<Stock> instances=new ArrayList<>();
    Thread loadStock= new Thread(new Runnable() {

        List<Operation> operationList;
        int count;
        @Override
        public void run() {
            //Do your work

            agence = new AgenceViewModel(getActivity().getApplication()).get(MainActivity.getCurrentUser().getAgence_id(),false,false) ;
            operationList=new OperationViewModel(getActivity().getApplication()).select_byAgence_operation(MainActivity.getCurrentUser().getAgence_id());
            progressBar=rootView.findViewById(R.id.pb_stock);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(operationList.size());
            footer.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            count=0;


            for (Operation op : operationList){
                count++;
                progressBar.setProgress(count);
                Stock stock = new Stock( agence, new ArticleRepository(ArticleRepository.getContext()).get(op.getArticle_id(),true,true), new StockRepository(getContext()).Qte( agence.getId(), op.getArticle_id())) ;

                if(!instances.contains(stock) && stock.getQuantite()!=0) {
                    instances.add(stock);
                   getActivity().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           stockAdapter.add(stock);
                           count_element();
                       }
                   });


                }
            }

            //Do your work after job is finished
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(),"Chargement terminé",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    lv_stock.removeFooterView(footer);

                }
            });
        }
    });


}


