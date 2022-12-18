package layout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.*;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.*;

public class AgenceView extends Fragment {

    private AgenceViewModel agenceViewModel ;


    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;
    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_agences_count;
    SearchView sv_agences_filter;
    ListView lvAgences;
    List<Agence> lesAgences;
    AgenceAdapter adapterAgence ;
    int count_all;

    String index ;


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Agence");

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
            rootView=inflater.inflate(R.layout.fragment_agence, container,false);
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
        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;
        count_all=agenceViewModel.count();

        lvAgences=rootView.findViewById(R.id.lv_agences);

        tv_agences_count =rootView.findViewById(R.id.tv_agences_count);
        swipeRefreshLayout=rootView.findViewById(R.id.refresh_agence);
        sv_agences_filter =rootView.findViewById(R.id.sv_agences_filter);


        lvAgences.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
               /* if (view.getLastVisiblePosition()==totalItemCount-1 && lvAgences.getCount()>=50 && isLoading==false){
                    index=((Agence)adapterAgence.getItem(totalItemCount-1)).getDenomination();
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
                agence_refresh_end() ;
            }

        });

        sv_agences_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Recherche",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ArrayList<Agence> result = new ArrayList<>();
                    for (Agence x : lesAgences) {
                        if ((x.getType().toLowerCase() + " " + x.getDenomination().toLowerCase()+ " " + x.getProprietaire().getName().toLowerCase()).contains(newText.toLowerCase())) {
                            result.add(x);
                        }
                    }
                    adapterAgence.update(result);
                    count_element_search();
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        fillData();

        adapterAgence =new AgenceAdapter(getContext(), lesAgences,true);
        lvAgences.setAdapter(adapterAgence);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillData()
    {
        try {

            lesAgences = agenceViewModel.getLoading() ;

            List<Agence>  agenceList = new ArrayList<>();
            agenceList.clear();

            for (int k = 0; k< lesAgences.size(); k++){

                agenceList.add(lesAgences.get(k));
                index = lesAgences.get(k).getDenomination() ;

            }

           count_element();

            isLoading=true;
            Thread thread=new ThreadGetMoreData();
            thread.start();

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
                    lvAgences.addFooterView(footer);
                    break;
                case 1:
                    //update data adapter and UI
                    adapterAgence.addItem((List <Agence>)msg.obj);
                    lvAgences.removeFooterView(footer);
                    isLoading=false;
                    break;
                default:
                    break;
            }

        }
    }

    public class ThreadGetMoreData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            List<Agence> instances = agenceViewModel.getLoading2(index);

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

   /* public class ThreadGetMoreData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            List<Agence> instances = agenceViewModel.getLoading2(index);

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
        tv_agences_count.setText("Agences ("+count_all+"/"+count_all+")");
        swipeRefreshLayout.setRefreshing(false);
    }

    private  void count_element_search() {
        tv_agences_count.setText("Agences ("+lvAgences.getCount()+"/"+count_all+")");
       swipeRefreshLayout.setRefreshing(false);
    }

    public static void agence_refresh_end(){
        swipeRefreshLayout.setRefreshing(false);
    }





}
