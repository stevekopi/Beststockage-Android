package cd.sklservices.com.Beststockage.Adapters.Registres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;

/**
 * Created by SKL on 25/04/2019.
 */


public class ArticleAdapter extends BaseAdapter {

    private List<Article> lesArticles;
    private LayoutInflater inflater;
    private Context context;
    private boolean isInsideOnclick;

    public void addItem(List<Article> result){
        lesArticles.addAll(result);
        notifyDataSetChanged();
    }

    public ArticleAdapter(Context context, List<Article> lesArticles,boolean isInsideOnclick){
        this.lesArticles = lesArticles;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
        this.isInsideOnclick=isInsideOnclick;
    }

    public ArticleAdapter(){

    }


    public void update(List<Article> result, SwipeRefreshLayout swipeRefreshLayout){

        lesArticles=new ArrayList<>();
        lesArticles.addAll(result);
        notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public int getCount() {
       return lesArticles.size();
    }

    @Override
    public Object getItem(int i) {
     return   lesArticles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final Article article=(Article) getItem(i);
        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.cell_article,null);
        }

        TextView tv_description = (TextView) convertView.findViewById(R.id.tv_description_article);
        TextView tv_prix = (TextView) convertView.findViewById(R.id.tv_article_prix);
        TextView tv_prix_detail = (TextView) convertView.findViewById(R.id.tv_article_prix_detail);
        TextView tv_categorie = (TextView) convertView.findViewById(R.id.tv_categorie_article);
        TextView tv_adding_date = (TextView) convertView.findViewById(R.id.tv_addingdate_article);

        //  Categorie cat = categorieViewModel.get(lesArticles.get(i).getCategorie_id());

        //Remplir le Holder
        tv_description.setText(article.getDescription());
        tv_categorie.setText(article.getCategorie().getDesignation());
        tv_prix.setText("Prix de gros    : " + MesOutils.spacer(String.valueOf(article.getPrix().intValue())) + " " + article.getDevise());
        tv_prix_detail.setText("Prix en detail  : " + MesOutils.spacer(String.valueOf(article.getPrix_detail().intValue())) + " " +article.getDevise());
        tv_adding_date.setText(MesOutils.Get_date_en_francais(article.getAdding_date()));

        convertView.setTag(i);

        if(isInsideOnclick){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //Demande de l'affichage du details
                        ((MainActivity)context).afficherDetailsArticle(article);
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

