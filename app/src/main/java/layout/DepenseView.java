package layout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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
import cd.sklservices.com.Beststockage.Adapters.Depense_expandable_adapter;
import cd.sklservices.com.Beststockage.Classes.Depense;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.DepenseViewModel;

public class DepenseView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= DepenseView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private DepenseViewModel depenseViewModel ;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton btn_add ;

    View rootView;

    ExpandableListView elv_depense;
    ExpandableListAdapter ela_depense;
    List<String> periode;
    Map<String,List<Depense>> depense;
    TextView tv_depenses_count;
    SearchView sv_filter;
    String  index ;
    int count_date;
    int count;


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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Caisse");
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
        rootView =inflater.inflate(R.layout.fragment_depense, container, false);

        btn_add = (FloatingActionButton) rootView.findViewById(R.id.btn_add_depense);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).addDepense();
            }
        });

        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_depense);

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

       try{
           this.depenseViewModel = new ViewModelProvider(this).get(DepenseViewModel.class)  ;
           count_date =depenseViewModel.count_distinct_date();
           count =depenseViewModel.count();
           periode = depenseViewModel.getDistinctPeriode() ;

           sv_filter =(SearchView) rootView.findViewById(R.id.sv_depenses_filter);

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

                   ((Depense_expandable_adapter) elv_depense.getExpandableListAdapter()).update(result);
                   count_element();
                   return false;
               }
           });

           elv_depense =(ExpandableListView) rootView.findViewById(R.id.elv_depenses);
           elv_depense.setOnScrollListener(new AbsListView.OnScrollListener() {
               @Override
               public void onScrollStateChanged(AbsListView view, int scrollState) {

                   try {

                       if(elv_depense.getCount()< count_date){
                           List<String> mylist=new ArrayList<>();
                               depenseViewModel.getLoading2(index,mylist) ;
                           for (String item:mylist)
                           {
                                   if(!periode.contains(item) && elv_depense.getCount()<count_date) {
                                       index = item;
                                       periode.add(item);
                                       depense.put(item, depenseViewModel.getByPeriode(item));
                                   }
                           }
                           ((Depense_expandable_adapter)elv_depense.getExpandableListAdapter()).notifyDataSetChanged();
                          count_element();
                       }
                   }
                   catch (Exception e) {
                       Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
                   }

               }

               @Override
               public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                   //  Toast.makeText(getActivity(), "111 3333 " , Toast.LENGTH_SHORT).show();
               }
           });

           fillData();
           ela_depense = new Depense_expandable_adapter(getActivity() ,getContext(), depenseViewModel,
                    periode,  depense) ;
           elv_depense.setAdapter(ela_depense);
           elv_depense.setLongClickable(true);
           elv_depense.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

           count_element();


       }
       catch (Exception ex)
       {
           Toast.makeText(getActivity(),   ex.toString(), Toast.LENGTH_LONG).show();
       }
    }



    private void count_element() {
        tv_depenses_count =(TextView) rootView.findViewById(R.id.tv_depenses_count);
        tv_depenses_count.setText(" Dates ("+ elv_depense.getCount()+ "/"+count_date+ ")\nTotal depense ("+count+")");
    }


    private void fillData(){

       depense =new HashMap<>();

        try {
            if (periode != null) {
                for (int k = 0; k < periode.size(); k++) {
                     depense.put(periode.get(k), depenseViewModel.getByPeriode(periode.get(k)));
                    index = periode.get(k) ;
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(),   ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
}


