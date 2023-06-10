package cd.sklservices.com.Beststockage.layout.Registres;

import androidx.annotation.Nullable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.Adapters.*;
import cd.sklservices.com.Beststockage.Adapters.Registres.FournisseurAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.CategorieViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;

/**
 * Created by SKL on 03/06/2019.
 */

public class Article_detailsView extends Fragment {

    private ArticleViewModel articleViewModel ;
    private CategorieViewModel categorieViewModel ;
    private FournisseurViewModel fournisseurViewModel ;
    private Article article ;
    View rootView;
    ListView listViewFournisseur, listViewDetail;
    TextView tv_details_article_description;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details_article, container, false);

       //     Bundle bundle = this.getArguments() ;
       // String valeur = bundle.getString ("id") ;
        init();
        return rootView;
    }

    void init(){

        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;
        this.categorieViewModel = new ViewModelProvider(this).get(CategorieViewModel.class) ;
        this.fournisseurViewModel = new ViewModelProvider(this).get(FournisseurViewModel.class) ;


        listViewFournisseur=(ListView)rootView.findViewById(R.id.lv_details_article_fournisseur);
        listViewDetail=(ListView)rootView.findViewById(R.id.lv_details_article);
        tv_details_article_description=(TextView)rootView.findViewById(R.id.tv_details_article_description);

        fillData();

    }

    public void fillData() {

        try
        {
            Article article = articleViewModel.getArticle();
            Fournisseur item= fournisseurViewModel.get(article.getFournisseur_id(),true) ;
            ArrayList<String> mylistDetail = new ArrayList<>()  ;

            tv_details_article_description.setText((article.getCategorie().getDesignation()+" "+article.getDescription()).toUpperCase(Locale.ROOT));
            mylistDetail.add("Fournisseur   : "+item.getIdentity().getName());
            mylistDetail.add("Categorie     : "+article.getCategorie().getDesignation());
            mylistDetail.add("Section 1     : "+article.getSection_1());
            mylistDetail.add("Section 2     : "+article.getSection_2());
            mylistDetail.add("Contenance    : "+article.getContenance().getCapacite());
            mylistDetail.add("Prix de gros  : "+ MesOutils.spacer(String.valueOf(article.getPrix().intValue()))+" "+article.getDevise());
            mylistDetail.add("Prix en detail: "+ MesOutils.spacer(String.valueOf(article.getPrix_detail().intValue()))+" "+article.getDevise());
            mylistDetail.add("Remise        : "+article.getRemise());
            mylistDetail.add("Poids          : "+MesOutils.spacer(String.valueOf(article.getPoids().intValue()))+" "+article.getUnite_poids());
             mylistDetail.add("Grammage    : "+article.getGrammage());
            mylistDetail.add("Qr Code        : "+article.getQr_code());
            mylistDetail.add("Code barre    : "+article.getCode_barre());
            mylistDetail.add("Lien Web       : "+article.getLien_web());

            DetailAdapter adapter = new DetailAdapter(getContext(), mylistDetail);
            listViewDetail.setAdapter(adapter);


            List<Fournisseur> arrayList=new ArrayList<>();
            arrayList.add(item);
            FournisseurAdapter adapter2 =new FournisseurAdapter(getContext(),arrayList);
        listViewFournisseur.setAdapter(adapter2);
        articleViewModel.setArticle(null);


     }
     catch (Exception ex)
        {
            Toast.makeText (getActivity(), ex.toString(), Toast.LENGTH_LONG ).show();
        }

    }
}
