package cd.sklservices.com.Beststockage.Adapters.Parametres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;

/**
 * Created by SKL on 25/04/2019.
 */

public class ArticleProduitFactureAdapter extends BaseAdapter {

    Context context;
    List<ArticleProduitFacture> instances;
    boolean insideOnclick;

    public ArticleProduitFactureAdapter(Context context, List<ArticleProduitFacture> instances, boolean insideOnclick) {
        this.context = context;
        this.instances = instances;
        this.insideOnclick=insideOnclick;
    }

    public ArticleProduitFactureAdapter(){

    }


    @Override
    public int getCount() {
        return instances.size();
    }

    @Override
    public Object getItem(int i) {
        return instances.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ArticleProduitFacture instance=(ArticleProduitFacture) getItem(i);

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.cell_article_produit_facture,null);
        }

        Article article= new ArticleViewModel(MainActivity.application).get(instance.getArticle_id(),true,true);

        TextView tv_description=convertView.findViewById(R.id.tv_cell_article_produit_facture_description);
        TextView tv_prix= convertView.findViewById(R.id.tv_cell_article_produit_facture_prix);

        tv_description.setText(article.getDescription().toUpperCase());
        Devise devise=new DeviseViewModel(MainActivity.application).get(instance.getDevise_id());
        tv_prix.setText(instance.getMontant_ttc().toString()+" "+devise.getCode());

        convertView.setTag(i);


        return convertView;

    }

    public void addItem(List<ArticleProduitFacture> result){
        instances.addAll(result);
        notifyDataSetChanged();
    }

    public void update(ArrayList<ArticleProduitFacture> result){
        instances =new ArrayList<>();
        instances.addAll(result);
        notifyDataSetChanged();
    }

}
