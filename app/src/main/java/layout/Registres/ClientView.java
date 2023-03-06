package layout.Registres;

import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Registres.ClientAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.ClientViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;

public class ClientView extends Fragment {

    private ClientViewModel clientViewModel ;
    private IdentityViewModel identityViewModel ;


    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;

    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_clients_count;
    SearchView sv_clients_filter;
    ListView lvClients;
    List<Identity> lesClients;
    ClientAdapter adapterClient ;
    //Map<Identity,List<Identity>> identityList;

    String index ;
    int count_all;


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Client");

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

            // Inflate the layout for this fragment
            rootView=inflater.inflate(R.layout.fragment_client, container,false);
            LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).
                    getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
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
        this.identityViewModel = new ViewModelProvider(this).get(IdentityViewModel.class) ;
        this.clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class) ;

        lvClients=(ListView) rootView.findViewById(R.id.lv_clients);
        tv_clients_count =(TextView)rootView.findViewById(R.id.tv_clients_count);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_client);
        sv_clients_filter =(SearchView) rootView.findViewById(R.id.sv_clients_filter);

        count_all=clientViewModel.count();

        lvClients.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
              /*if (view.getLastVisiblePosition()==totalItemCount-1 && lvClients.getCount()>=50 && isLoading==false){
                 index=((Identity)adapterClient.getItem(totalItemCount-1)).getName();
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

        sv_clients_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Recherche",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ArrayList<Identity> result = new ArrayList<>();
                    for (Identity x : lesClients) {
                        if ((x.getType().toLowerCase() + " " + x.getName().toLowerCase()).contains(newText.toLowerCase())) {
                            result.add(x);
                        }
                    }

                    adapterClient.update(result);
                    count_element_search();
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });



            fillData();
            adapterClient =new ClientAdapter(getContext(), lesClients);
            lvClients.setAdapter(adapterClient);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillData()
    {
        try {

            lesClients = clientViewModel.getLoading() ;

           List<Identity> identityList = new ArrayList<>();
            identityList.clear();

            for (int k = 0; k< lesClients.size(); k++){

                identityList.add(lesClients.get(k));
                index = lesClients.get(k).getName() ;

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

    private  void count_element() {
        if(lvClients.getCount()<=count_all)
            tv_clients_count.setText("Client : ("+count_all + "/"+count_all+")");
        swipeRefreshLayout.setRefreshing(false);
    }

    private  void count_element_search() {
        if(lvClients.getCount()<=count_all)
            tv_clients_count.setText("Client : ("+lvClients.getCount() + "/"+count_all+")");
        swipeRefreshLayout.setRefreshing(false);
    }

    public static void agence_refresh_end(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //inserer le view de loading pendant la recherche
                    lvClients.addFooterView(footer);
                    break;
                case 1:
                    //update data adapter and UI
                    adapterClient.addItem((ArrayList <Identity>)msg.obj);
                    lvClients.removeFooterView(footer);
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
            List<Identity> instances = clientViewModel.getLoading2(index);

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

}
