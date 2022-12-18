package layout;

import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Depense_expandable_adapter;
import cd.sklservices.com.Beststockage.Adapters.Operation_expandable_adapter;
import cd.sklservices.com.Beststockage.Classes.Identity;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Classes.Operation;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationViewModel;

public class OperationView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= OperationView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;
    private OperationViewModel operationViewModel ;
    private ArticleViewModel articleViewModel ;
    private AgenceViewModel agenceViewModel ;
    static SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton btn_add_stock ;

    View rootView;
    View loadingProgress;

    ExpandableListView elv_operation;
    Operation_expandable_adapter ela_operation;
    List<String> periode;
    Map<String,List<Operation>> operation;
    TextView tv_operations_count;
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Operation");
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
        rootView =inflater.inflate(R.layout.fragment_operation, container, false);
        loadingProgress= (ProgressBar)rootView.findViewById(R.id.pb_operation_loading);
        loadingProgress.setVisibility(View.GONE);

        btn_add_stock = (FloatingActionButton) rootView.findViewById(R.id.btn_add_stock);

        btn_add_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).addStock();
            }
        });



        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_operation);
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

           this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class)  ;
           count=operationViewModel.count();
           count_date=operationViewModel.count_distinct_date();
           this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class)  ;
           this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class)  ;


           sv_filter =(SearchView) rootView.findViewById(R.id.sv_operations_filter);

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

                   ela_operation.update(result);
                   count_element();
                   return false;
               }
           });

           elv_operation =(ExpandableListView) rootView.findViewById(R.id.elv_operation);

           elv_operation.setOnScrollListener(new AbsListView.OnScrollListener() {
               @Override
               public void onScrollStateChanged(AbsListView view, int scrollState) {
               }

               @Override
               public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                   if (view.getLastVisiblePosition()==totalItemCount-1 && elv_operation.getCount()>=50 && isLoading==false){
                       index=((String)ela_operation.getGroup(totalItemCount-1));
                       isLoading=true;
                       Thread thread=new ThreaGetMoreData();
                       thread.start();
                   }
               }
           });

           fillData();
           ela_operation =new Operation_expandable_adapter(getContext(), articleViewModel, agenceViewModel,
                   operationViewModel, periode, operation);
           elv_operation.setAdapter(ela_operation);
           elv_operation.setLongClickable(true);
           elv_operation.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
           count_element();


       }
       catch (Exception ex)
       {
           Toast.makeText(getActivity(),   ex.toString(), Toast.LENGTH_LONG).show();
       }
    }

    private void count_element() {
        tv_operations_count =(TextView) rootView.findViewById(R.id.tv_operations_count);
        tv_operations_count.setText("Date ("+ elv_operation.getCount()+"/"+count_date+")\n"+"Total operation ("+count+")");

    }


    private void fillData(){

        try {
            operation =new HashMap<>();
            periode = operationViewModel.getDistinctPeriode() ;

            if (periode != null) {
                for (int k = 0; k < periode.size(); k++) {
                     operation.put(periode.get(k), operationViewModel.getByPeriode(periode.get(k)));
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
                   if (elv_operation.getCount()<count_date){
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
                    ela_operation.addItem((Map<String,List<Operation>>)msg.obj);
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

            if(elv_operation.getCount()<= count_date){
                List<String> instance=new ArrayList<>();
                operationViewModel.getLoading2(index,instance) ;
                for (String item:instance)
                {
                    periode.add(item);
                    operation.put(item, operationViewModel.getByPeriode(item));
                    count_element();

                }
            }

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg=myHandler.obtainMessage(1,operation);
            myHandler.sendMessage(msg);

        }
    }

}


