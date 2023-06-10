package cd.sklservices.com.Beststockage.Adapters.Parametres;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by SKL on 05/11/2019.
 */

public class ArticleProduitFactureSecondLevelAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<ArticleProduitFacture[]> data;
    ProduitFacture[] headers;


    public ArticleProduitFactureSecondLevelAdapter(Context context, ProduitFacture[] headers, List<ArticleProduitFacture[]> data){
        try {
            this.context = context;
            this.headers = headers;
            this.data = data;
        } catch (Exception ex) {
            Toast.makeText(context , "ArticleProduitFactureSecondLevel = " + ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {

        return headers[groupPosition];
    }
    @Override
    public int getGroupCount() {

        return headers.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.article_produit_facture_row_third, null);

        try {

            TextView tv_article_desc =  convertView.findViewById(R.id.tv_article_produit_facture_row_third_article_description);
            TextView tv_montant_net = convertView.findViewById(R.id.tv_article_produit_facture_row_third_montant);

            ArticleProduitFacture[] childArray = data.get(groupPosition);
            final ArticleProduitFacture articleProduitFacture = childArray[childPosition];
            final Article article=new ArticleViewModel(MainActivity.application).get(articleProduitFacture.getArticle_id(),true,true);

            tv_article_desc.setText(article.getDescription());
            tv_montant_net.setText(String.valueOf(articleProduitFacture.getMontant_ttc()));

            convertView.setTag(groupPosition);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  ((MainActivity)context).afficherDetailLigneFacture(articleProduitFacture);
                }
            });

        }
        catch (Exception ex) {
            Toast.makeText(context , "LivraisonSecondLevel 444 = " + ex.toString(), Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        try {
            ArticleProduitFacture[] childData;
            childData = data.get(groupPosition);
            return childData[childPosition];
        }  catch (Exception ex) {
            Toast.makeText(context , "ArticleProduitFactureSecondLevel  555 = " + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return null ;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.article_produit_facture_row_second,null);
        try {
            TextView tvDesignation = (TextView) convertView.findViewById(R.id.tv_article_produit_facture_row_second_produit_designation);
             tvDesignation.setText((headers[groupPosition]).getDesignation());
        }
        catch (Exception ex) {
            Toast.makeText(context , " **** ArticleProduitFactureSecondLevel 111 = " + ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            ArticleProduitFacture[] children = data.get(groupPosition);
            return children.length;
        } catch (Exception ex) {
           // Toast.makeText(context , " Aucune facture  enregistrée ..." , Toast.LENGTH_SHORT).show();
        }
        return 0 ;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
