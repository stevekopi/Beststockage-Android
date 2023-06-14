package cd.sklservices.com.Beststockage.Adapters.Stocks;

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
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Classes.Stocks.Bonlivraison;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneBonlivraison;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by SKL on 05/11/2019.
 */

public class BonlivraisonSecondLevelAdapter extends BaseExpandableListAdapter {

    private ArticleViewModel articleViewModel ;
    private UserViewModel userViewModel ;

    private Context context;
    List<LigneBonlivraison[]> data;
    Bonlivraison[] headers;


    public BonlivraisonSecondLevelAdapter(Context context, ArticleViewModel article_v, UserViewModel user_v, Bonlivraison[] headers, List<LigneBonlivraison[]> data){
        try {
            this.context = context;
            this.headers = headers;
            this.data = data;

            this.articleViewModel = article_v;
            this.userViewModel = user_v;
        } catch (Exception ex) {
            Toast.makeText(context , "LivraisonSecondLevel 333 = " + ex.toString(), Toast.LENGTH_SHORT).show();
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
        convertView = inflater.inflate(R.layout.bonlivraison_row_third, null);

        try {

            TextView tv_article_desc =  convertView.findViewById(R.id.tv_livraison_row_third_article_description);
            TextView tv_quantite =  convertView.findViewById(R.id.tv_livraison_row_third_quantite);
            TextView tv_bonus =  convertView.findViewById(R.id.tv_livraison_row_third_bonus);
            TextView tv_montant = convertView.findViewById(R.id.tv_livraison_row_third_montant);
            LigneBonlivraison[] childArray = data.get(groupPosition);

            final LigneBonlivraison ligneBonlivraison = childArray[childPosition];

            Article art = articleViewModel.get(ligneBonlivraison.getArticle_id(),true,true);
            tv_article_desc.setText(art.getDescription());
            tv_quantite.setText("Q : " + String.valueOf(ligneBonlivraison.getQuantite()));
            tv_bonus.setText("B : " + String.valueOf(ligneBonlivraison.getBonus()));
            tv_montant.setText("Montant : " +  MesOutils.spacer(String.valueOf(ligneBonlivraison.getMontant().intValue()))  +" "+
                    String.valueOf(ligneBonlivraison.getDevise_id()));

            convertView.setTag(groupPosition);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).afficherDetailLigneBonLivraison(ligneBonlivraison);
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
            LigneBonlivraison[] childData;
            childData = data.get(groupPosition);
            return childData[childPosition];
        }  catch (Exception ex) {
            Toast.makeText(context , "LivraisonSecondLevel  555 = " + ex.toString(), Toast.LENGTH_SHORT).show();
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
        convertView=inflater.inflate(R.layout.bonlivraison_row_second,null);
        try {
            TextView tvNumeroBon = (TextView) convertView.findViewById(R.id.tv_livraison_row_second_numeroBon);
            TextView tvUser = (TextView) convertView.findViewById(R.id.tv_livraison_row_second_user);
            TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date_Bonlivraison);
            TextView tvSynchro = (TextView) convertView.findViewById(R.id.tv_synchronisation);

            tvNumeroBon.setText("N° Bon : " + (headers[groupPosition]).getBon().getNumero());
            tvUser.setText(userViewModel.get(headers[groupPosition].getUser_id(),true,false).getHuman().getIdentity().getName());
            tvDate.setText( (headers[groupPosition].getAdding_date()).substring(0, 10).toString()) ;

            if(headers[groupPosition].getSync_pos() == 0)
            {
                tvSynchro.setTextColor(Color.RED);
            }
            else
            {
                tvSynchro.setTextColor(Color.GREEN);
            }
        }
        catch (Exception ex) {
            Toast.makeText(context , " ****LivraisonSecondLevel 111 = " + ex.toString(), Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            LigneBonlivraison[] children = data.get(groupPosition);
            return children.length;
        } catch (Exception ex) {
           // Toast.makeText(context , " Aucun bon de livraison  enregistrée ..." , Toast.LENGTH_SHORT).show();
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
