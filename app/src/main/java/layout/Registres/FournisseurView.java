package layout.Registres;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cd.sklservices.com.Beststockage.Adapters.Registres.FournisseurAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;

public class FournisseurView extends Fragment {

    private FournisseurViewModel fournisseurViewModel;

    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_fournisseurs_count;
    SearchView sv_fournisseurs_filter;
    ListView lv_fournisseurs;
    List<Fournisseur> lesFournisseurs;

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Fournisseur");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {
        rootView=inflater.inflate(R.layout.fragment_fournisseur, container,false);
        init();

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    public void init() {
        this.fournisseurViewModel = new ViewModelProvider(this).get(FournisseurViewModel.class) ;
        this.fournisseurViewModel.gets();

        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refresh_fournisseur);
        lv_fournisseurs=(ListView)rootView.findViewById(R.id.lv_fournisseurs);
        tv_fournisseurs_count=(TextView)rootView.findViewById(R.id.tv_fournisseurs_count);
        sv_fournisseurs_filter=(SearchView) rootView.findViewById(R.id.sv_fournisseurs_filter);

        //Rafraichir la page

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                refresh_end();
            }

        });

    sv_fournisseurs_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Log.d("Recherche",query);
            return true;
        }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Fournisseur> result=new ArrayList<>();
        for (Fournisseur x:lesFournisseurs){
            if ((x.getId().toLowerCase()+" "+x.getId().toLowerCase()).contains(newText.toLowerCase()))
            {
                result.add(x);
            }
        }

        ((FournisseurAdapter)lv_fournisseurs.getAdapter()).update(result);
        count_element();
        return false;
    }
});
        fillData();
    }



    private void fillData(){

       lesFournisseurs= fournisseurViewModel.getFournisseurArrayListe() ;
    Collections.sort(lesFournisseurs,Collections.<Fournisseur>reverseOrder());
    if (lesFournisseurs!=null){

        FournisseurAdapter adapterFournisseur=new FournisseurAdapter(getContext(),lesFournisseurs);
        lv_fournisseurs.setAdapter(adapterFournisseur);
        tv_fournisseurs_count.setText("Fournisseur ("+lv_fournisseurs.getCount()+")");
    }

    swipeRefreshLayout.setRefreshing(false);
}

    private void count_element() {
        tv_fournisseurs_count.setText("Fournisseurs ("+lv_fournisseurs.getCount()+")");
    }

    public static void refresh_end(){
        swipeRefreshLayout.setRefreshing(false);
    }

}
