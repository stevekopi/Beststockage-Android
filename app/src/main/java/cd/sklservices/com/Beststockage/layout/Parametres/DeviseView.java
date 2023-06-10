package cd.sklservices.com.Beststockage.layout.Parametres;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Parametres.DeviseAdapter;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;

public class DeviseView extends Fragment {

    private DeviseViewModel deviseViewModel ;


    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;
    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_devises_count;
    SearchView sv_devises_filter;
    ListView lvDevises;
    List<Devise> lesDevises;
    DeviseAdapter adapterDevise ;
    int count_all;

    String index ;


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Devise");

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
            Log.d("Assert","onCreateView");
            // Inflate the layout for this fragment
            rootView=inflater.inflate(R.layout.fragment_devise, container,false);
            LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            footer= li.inflate(R.layout.footer_view,null);
            myHandler=new MyHandler();
            init();

        } catch (Exception e) {
           // Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    private void  init() {

        try
        {
        this.deviseViewModel = new ViewModelProvider(this).get(DeviseViewModel.class) ;
        count_all=deviseViewModel.count();

        lvDevises=rootView.findViewById(R.id.lv_devises);

        tv_devises_count =rootView.findViewById(R.id.tv_devises_count);
        swipeRefreshLayout=rootView.findViewById(R.id.refresh_devise);
        sv_devises_filter =rootView.findViewById(R.id.sv_devises_filter);


        lvDevises.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
               /* if (view.getLastVisiblePosition()==totalItemCount-1 && lvDevises.getCount()>=50 && isLoading==false){
                    index=((Devise)adapterDevise.getItem(totalItemCount-1)).getDenomination();
                    isLoading=true;
                    Thread thread=new ThreadGetMoreData();
                    thread.start();
                }

                */
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                init();
                devise_refresh_end() ;
            }

        });

        sv_devises_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Recherche",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ArrayList<Devise> result = new ArrayList<>();
                    for (Devise x : lesDevises) {
                        if ((x.getCode().toLowerCase() + " " + x.getSymbole().toLowerCase()+ " " + x.getDesignation().toLowerCase()).contains(newText.toLowerCase())) {
                            result.add(x);
                        }
                    }
                    adapterDevise.update(result);
                    count_element_search();
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        fillData();

        adapterDevise =new DeviseAdapter(getContext(), lesDevises,true);
        lvDevises.setAdapter(adapterDevise);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillData()
    {
        try {

            lesDevises = deviseViewModel.loading() ;

            List<Devise>  deviseList = new ArrayList<>();
            deviseList.clear();

            for (int k = 0; k< lesDevises.size(); k++){

                deviseList.add(lesDevises.get(k));
                index = lesDevises.get(k).getDesignation() ;

            }

           count_element();

            isLoading=true;

           swipeRefreshLayout.setRefreshing(false);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //inserer le view de loading pendant la recherche
                    lvDevises.addFooterView(footer);
                    break;
                case 1:
                    //update data adapter and UI
                    adapterDevise.addItem((List <Devise>)msg.obj);
                    lvDevises.removeFooterView(footer);
                    isLoading=false;
                    break;
                default:
                    break;
            }

        }
    }



   /* public class ThreadGetMoreData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            List<Devise> instances = deviseViewModel.getLoading2(index);

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg=myHandler.obtainMessage(1,instances);
            myHandler.sendMessage(msg);
            count_element();

        }
    }

    */

    private  void count_element() {
        tv_devises_count.setText("Devises ("+count_all+"/"+count_all+")");
        swipeRefreshLayout.setRefreshing(false);
    }

    private  void count_element_search() {
        tv_devises_count.setText("Devises ("+lvDevises.getCount()+"/"+count_all+")");
       swipeRefreshLayout.setRefreshing(false);
    }

    public static void devise_refresh_end(){
        swipeRefreshLayout.setRefreshing(false);
    }





}
