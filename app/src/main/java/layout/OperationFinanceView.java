package layout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Operation_expandable_adapter;
import cd.sklservices.com.Beststockage.Adapters.Operation_finance_expandable_adapter;
import cd.sklservices.com.Beststockage.Classes.Operation;
import cd.sklservices.com.Beststockage.Classes.OperationFinance;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationFinanceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationViewModel;

public class OperationFinanceView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= OperationFinanceView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;
    private OperationFinanceViewModel operationFinanceViewModel;
    private ArticleViewModel articleViewModel ;
    private AgenceViewModel agenceViewModel ;
    static SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton btn_add_stock ;

    View rootView;
    View loadingProgress;

    ExpandableListView elv_operation_finance;
    Operation_finance_expandable_adapter ela_operation_finance;
    List<String> periode;
    Map<String,List<OperationFinance>> mapOperationFinance;
    TextView tv_operation_finance_count;
    SearchView sv_filter;
    String  index ;
    int count_date;
    int count;

    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;


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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Operation Financière");
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
        rootView =inflater.inflate(R.layout.fragment_operation_finance, container, false);
        loadingProgress= (ProgressBar)rootView.findViewById(R.id.pb_operation_finance_loading);
        loadingProgress.setVisibility(View.GONE);

        btn_add_stock = (FloatingActionButton) rootView.findViewById(R.id.btn_add_operation_finance);

        btn_add_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).addStock();
            }
        });
        //JE MASQUE LE BOUTON AJOUTER
        btn_add_stock.setVisibility(View.INVISIBLE);



        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_operation_finance);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                init();
                refresh_end() ;
            }

        });

        LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).
                getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        footer= li.inflate(R.layout.footer_view,null);
        myHandler=new MyHandler();

        init();

        return rootView;
    }

    private void init(){

       try{
           count= new OperationFinanceViewModel(getActivity().getApplication()).count();
           count_date= new OperationFinanceViewModel(getActivity().getApplication()).count_distinct_date();
           this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class)  ;
           this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class)  ;


           sv_filter =(SearchView) rootView.findViewById(R.id.sv_operation_finance_filter);

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

                   ela_operation_finance.update(result);
                   count_element();
                   return false;
               }
           });

           elv_operation_finance =(ExpandableListView) rootView.findViewById(R.id.elv_operation_finance);

           elv_operation_finance.setOnScrollListener(new AbsListView.OnScrollListener() {
               @Override
               public void onScrollStateChanged(AbsListView view, int scrollState) {
               }

               @Override
               public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                   if (view.getLastVisiblePosition()==totalItemCount-1 && elv_operation_finance.getCount()>=50 && isLoading==false){
                       index=((String) ela_operation_finance.getGroup(totalItemCount-1));
                       isLoading=true;
                       Thread thread=new ThreaGetMoreData();
                       thread.start();
                   }
               }
           });

           fillData();
           ela_operation_finance =new Operation_finance_expandable_adapter(getContext(),periode, mapOperationFinance);
           ela_operation_finance =new Operation_finance_expandable_adapter(getContext(),periode,mapOperationFinance);
           elv_operation_finance.setAdapter(ela_operation_finance);
           elv_operation_finance.setLongClickable(true);
           elv_operation_finance.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
           count_element();


       }
       catch (Exception ex)
       {
           Toast.makeText(getActivity(),   ex.toString(), Toast.LENGTH_LONG).show();
       }
    }

    private void count_element() {
        tv_operation_finance_count =(TextView) rootView.findViewById(R.id.tv_operation_finance_count);
        tv_operation_finance_count.setText("Date ("+ elv_operation_finance.getCount()+"/"+count_date+")\n"+"Total operation ("+count+")");

    }


    private void fillData(){

        try {
            mapOperationFinance =new HashMap<>();
            periode = new OperationFinanceViewModel(getActivity().getApplication()).getDistinctPeriode() ;

            if (periode != null) {
                for (int k = 0; k < periode.size(); k++) {
                     mapOperationFinance.put(periode.get(k), new OperationFinanceViewModel(getActivity().getApplication()).getByPeriode(periode.get(k)));
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(),   ex.toString(), Toast.LENGTH_LONG).show();
        }


    }

    public static void refresh_end(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //inserer le view de loading pendant la recherche
                  //
                   if (elv_operation_finance.getCount()<count_date){
                       loadingProgress.setVisibility(View.VISIBLE);
                       try {
                           Thread.sleep(400);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                    break;
                case 1:
                    //update data adapter and UI
                    ela_operation_finance.addItem((Map<String,List<OperationFinance>>)msg.obj);
                    loadingProgress.setVisibility(View.GONE);
                    isLoading=false;
                    break;
                default:
                    break;
            }

        }
    }

    public class ThreaGetMoreData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);

            if(elv_operation_finance.getCount()<= count_date){
                List<String> instance=new ArrayList<>();
                operationFinanceViewModel.getLoading2(index,instance) ;
                for (String item:instance)
                {
                    periode.add(item);
                    mapOperationFinance.put(item, operationFinanceViewModel.getByPeriode(item));
                    count_element();
                }
            }

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg=myHandler.obtainMessage(1, mapOperationFinance);
            myHandler.sendMessage(msg);

        }
    }

}


