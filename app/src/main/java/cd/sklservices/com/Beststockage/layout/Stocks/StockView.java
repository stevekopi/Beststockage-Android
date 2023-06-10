package cd.sklservices.com.Beststockage.layout.Stocks;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Stocks.StockAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.Classes.Stocks.Stock;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Repository.Stocks.StockRepository;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.StockViewModel;

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

            agence = new AgenceViewModel(MainActivity.application).get(MainActivity.getCurrentUser().getAgence_id(),false,false) ;
            List<Article>articles=new ArticleViewModel(MainActivity.application).get_all();
            progressBar=rootView.findViewById(R.id.pb_stock);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(articles.size());
            footer.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            count=0;


            for (Article article : articles){
                count++;
                progressBar.setProgress(count);
                Stock stock = new Stock( agence,new ArticleViewModel(MainActivity.application).get(article.getId(),true,true), new StockRepository(getContext()).Qte( agence.getId(), article.getId())) ;

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


