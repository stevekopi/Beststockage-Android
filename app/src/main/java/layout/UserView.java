package layout;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.UserAdapter;
import cd.sklservices.com.Beststockage.Classes.Agence;
import cd.sklservices.com.Beststockage.Classes.User;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.UserViewModel;
import cd.sklservices.com.Beststockage.R;

public class UserView extends Fragment {
    private UserViewModel userViewModel ;
    private AgenceViewModel agenceViewModel ;


    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_users_count;
    SearchView sv_filter;
    ListView lvUsers;
    List<User> lesUsers;
    BaseAdapter adapterUser ;
    Map<User,List<Agence>> map;

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Utilisateur");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {

        rootView=inflater.inflate(R.layout.fragment_user, container,false);

        FloatingActionButton  btn_add = (FloatingActionButton) rootView.findViewById(R.id.btn_add_user);

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ((MainActivity) getActivity()).addUser();
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

         init();

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    public void init() {

        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;

        lvUsers=rootView.findViewById(R.id.lvUsers);
        tv_users_count=(TextView)rootView.findViewById(R.id.tv_users_count);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_user);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                refresh_end();
            }
        });

        sv_filter =(SearchView) rootView.findViewById(R.id.sv_users_filter);
        sv_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Log.d("Recherche",query);
            return true;
        }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("Recherche",newText);
        try {
        ArrayList<User> result=new ArrayList<>();
            for (User x:lesUsers){
                Agence agence = agenceViewModel.get(x.getAgence_id(),false,false) ;
                if ((x.getId().toLowerCase()+" "+x.getHuman().getIdentity().getName().toLowerCase()+" "+ agence.getDenomination().toLowerCase()).contains(newText.toLowerCase()))
                {
                    result.add(x);
                }
            }
            ((UserAdapter)lvUsers.getAdapter()).update(result);

        count_element();
         }
        catch (Exception ex)
            {
                Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
            }
        return false;
    }
});
        fillData();
    }

    private void fillData(){

        try {
        lesUsers = userViewModel.getUserArrayListe() ;
         Collections.sort(lesUsers,Collections.<User>reverseOrder());
        if (lesUsers!=null){
           adapterUser =new UserAdapter(getContext(),agenceViewModel ,lesUsers);
            lvUsers.setAdapter(adapterUser);
           count_element();
    }

    } catch (Exception e) {
       /// Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
    }

}

    private void count_element() {
        tv_users_count.setText("Utilisateur ("+lvUsers.getCount()+")");
    }

    public static void refresh_end(){
        swipeRefreshLayout.setRefreshing(false);
    }

}
