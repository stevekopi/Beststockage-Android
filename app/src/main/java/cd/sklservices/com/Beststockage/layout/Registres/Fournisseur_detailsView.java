package cd.sklservices.com.Beststockage.layout.Registres;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Adapters.Registres.ArticleAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.CategorieViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;

/**
 * Created by SKL on 03/06/2019.
 *
 */

public class Fournisseur_detailsView extends Fragment {
    View rootView;
    private FournisseurViewModel fournisseurViewModel ;
    private ArticleViewModel articleViewModel ;

    ListView  listViewFournisseur;
    TextView tv_name;
    ListView lvArticles ;
    BaseAdapter adapterArticle ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details_fournisseur, container, false);
        init();
        return rootView;
    }

    void init(){

        this.fournisseurViewModel = new ViewModelProvider(this).get(FournisseurViewModel.class) ;
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;

        listViewFournisseur=rootView.findViewById(R.id.lv_details_fournisseur);
        lvArticles =rootView.findViewById(R.id.lv_details_fournisseur_article);
        tv_name=rootView.findViewById(R.id.tv_details_fournisseur_name);

        fillData();

    }
    public void fillData(){

        try {
            Fournisseur instance = fournisseurViewModel.getFournisseur();
            String address =instance.getIdentity().getAddress().getNumber() +", Av. "+ instance.getIdentity().getAddress().getStreet().getName()+
                    ", Q. "+instance.getIdentity().getAddress().getQuarter().getName()+", "+instance.getIdentity().getAddress().getTown().getName()+
                    "/"+instance.getIdentity().getAddress().getTownship().getName();
            ArrayList<String> mylistDetail = new ArrayList<>()  ;

            tv_name.setText(instance.getIdentity().getName().toUpperCase(Locale.ROOT));
            if(instance.getIdentity().getTelephone2().length()>4 || instance.getIdentity().getTelephone3().length()>4){
                mylistDetail.add("Tel 1 : "+ instance.getIdentity().getTelephone());
            }
            else {
                mylistDetail.add("Tel : "+ instance.getIdentity().getTelephone());
            }

            if(instance.getIdentity().getTelephone2().length()>4)
                mylistDetail.add("Tel 2 : "+ instance.getIdentity().getTelephone2());
            if(instance.getIdentity().getTelephone3().length()>4)
                mylistDetail.add("Tel 3 : "+ instance.getIdentity().getTelephone3());


            mylistDetail.add(address);
            mylistDetail.add("Réf : "+instance.getIdentity().getAddress().getReference());

            DetailAdapter adapter = new DetailAdapter(getContext(), mylistDetail);
            listViewFournisseur.setAdapter(adapter);

            ArrayList<Article> articles = articleViewModel.getByFournisseur_article(fournisseurViewModel.getId());

            adapterArticle =new ArticleAdapter(getContext(), articles,true);
            lvArticles.setAdapter(adapterArticle);

            fournisseurViewModel.setFournisseur(null);

        }
        catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

    }
}
