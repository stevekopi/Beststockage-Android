package cd.sklservices.com.Beststockage.Adapters.Stocks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;

/**
 * Created by SKL on 25/04/2019.
 */


public class LigneFactureAdapter extends BaseAdapter {

    private List<LigneFacture> instances;
    private LayoutInflater inflater;
    private Context context;
    private boolean isInsideOnclick;

    public void addItem(List<LigneFacture> result){
        instances.addAll(result);
        notifyDataSetChanged();
    }

    public LigneFactureAdapter(Context context, List<LigneFacture> instances){
        this.instances = instances;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
      //  this.isInsideOnclick=isInsideOnclick;
    }

    public LigneFactureAdapter(){

    }


    public void update(List<LigneFacture> result){

        instances =new ArrayList<>();
        instances.addAll(result);
        notifyDataSetChanged();
      //  swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public int getCount() {
       return instances.size();
    }

    @Override
    public Object getItem(int i) {
     return   instances.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final LigneFacture instance=(LigneFacture) getItem(i);
      //  final ArticleProduitFacture articleProduitFacture=new ArticleProduitFactureViewModel(MainActivity.application).get(instance.getArticle_produit_facture_id(),true,false);
        final ArticleProduitFacture articleProduitFacture=new ArticleProduitFactureViewModel(MainActivity.application).get(instance.getArticleProduitFacture().getId(),true,false);
        final Article articleBonus=new ArticleViewModel(MainActivity.application).get(instance.getArticle_bonus_id(),true,true);
        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.cell_facture_line_lite,null);
        }

        TextView tv_article_produit_description = (TextView) convertView.findViewById(R.id.tv_cell_facture_line_lite_article_produit_description);

        TextView tv_article_bonus_description = (TextView) convertView.findViewById(R.id.tv_cell_facture_line_lite_article_bonus_description);

        TextView tv_quantite = (TextView) convertView.findViewById(R.id.tv_cell_facture_line_lite_quantite);
        TextView tv_bonus = (TextView) convertView.findViewById(R.id.tv_cell_facture_line_lite_bonus);

        TextView tv_montant_net = (TextView) convertView.findViewById(R.id.tv_cell_facture_line_lite_montant_net);


        //  Categorie cat = categorieViewModel.get(lesArticles.get(i).getCategorie_id());

        //Remplir le Holder
        tv_article_produit_description.setText(articleProduitFacture.getArticle().getCategorie().getDesignation()+"-"+articleProduitFacture.getArticle().getDescription());
        tv_article_bonus_description.setText(articleBonus.getCategorie().getDesignation()+"-"+articleBonus.getDescription());
        tv_quantite.setText(String.valueOf(instance.getQuantite()));
        tv_bonus.setText(String.valueOf(instance.getBonus()));

        Devise devise=new DeviseViewModel(MainActivity.application).get(instance.getFacture().getDevise_id());
        tv_montant_net.setText(MesOutils.spacer(String.valueOf(instance.getMontant_net().intValue())) + " " + devise.getCode());

        convertView.setTag(i);

        if(isInsideOnclick){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //Demande de l'affichage du details
                        ((MainActivity)context).afficherDetailsArticle(articleBonus);
                    }
                    catch (Exception e) {
                        Toast.makeText(context, e.toString() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return convertView;

    }
}

