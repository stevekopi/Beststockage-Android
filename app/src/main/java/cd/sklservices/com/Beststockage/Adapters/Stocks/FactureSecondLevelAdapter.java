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
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by SKL on 05/11/2019.
 */

public class FactureSecondLevelAdapter extends BaseExpandableListAdapter {

    private ArticleProduitFactureViewModel articleProduitFactureViewModel;
    private UserViewModel userViewModel ;
    private IdentityViewModel identityViewModel;

    private Context context;
    List<LigneFacture[]> data;
    Facture[] headers;


    public FactureSecondLevelAdapter(Context context, ArticleProduitFactureViewModel articleProduitFactureViewModel,IdentityViewModel identityViewModel, UserViewModel user_v, Facture[] headers, List<LigneFacture[]> data){
        try {
            this.context = context;
            this.headers = headers;
            this.data = data;

            this.articleProduitFactureViewModel = articleProduitFactureViewModel;
            this.identityViewModel = identityViewModel;
            this.userViewModel=user_v;
        } catch (Exception ex) {
            Toast.makeText(context , "FactureSecondLevel = " + ex.toString(), Toast.LENGTH_SHORT).show();
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
        convertView = inflater.inflate(R.layout.facture_row_third, null);

        try {

            TextView tv_article_desc =  convertView.findViewById(R.id.tv_facture_row_third_article_description);
            TextView tv_quantite =  convertView.findViewById(R.id.tv_facture_row_third_quantite);
            TextView tv_bonus =  convertView.findViewById(R.id.tv_facture_row_third_bonus);
            TextView tv_montant_net = convertView.findViewById(R.id.tv_facture_row_third_montant_net);
            LigneFacture[] childArray = data.get(groupPosition);

            final LigneFacture ligneFacture = childArray[childPosition];

            ArticleProduitFacture art = articleProduitFactureViewModel.get(ligneFacture.getArticle_produit_facture_id(),true,false);
            tv_article_desc.setText(art.getArticle().getDescription());
            tv_quantite.setText("Q : " + String.valueOf(ligneFacture.getQuantite()));
            tv_bonus.setText("B : " + String.valueOf(ligneFacture.getBonus()));
            tv_montant_net.setText("Mont. Net : " +  MesOutils.spacer(String.valueOf(ligneFacture.getMontant_net().intValue())));

            convertView.setTag(groupPosition);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).afficherDetailLigneFacture(ligneFacture);
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
            LigneFacture[] childData;
            childData = data.get(groupPosition);
            return childData[childPosition];
        }  catch (Exception ex) {
            Toast.makeText(context , "FactureSecondLevel  555 = " + ex.toString(), Toast.LENGTH_SHORT).show();
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
        convertView=inflater.inflate(R.layout.facture_row_second,null);
        try {
            TextView tvId = (TextView) convertView.findViewById(R.id.tv_facture_row_second_id);
            TextView tvProduit = (TextView) convertView.findViewById(R.id.tv_facture_row_second_produit);
            TextView tvProp = (TextView) convertView.findViewById(R.id.tv_facture_row_second_proprietaire);
            TextView tvUser = (TextView) convertView.findViewById(R.id.tv_facture_row_second_user);
            TextView tvSynchro = (TextView) convertView.findViewById(R.id.tv_facture_synchronisation);

            tvId.setText("N° : " + (headers[groupPosition]).getId());
            tvProduit.setText((headers[groupPosition]).getProduit_id());
          //  tvProp.setText(identityViewModel.get(headers[groupPosition].getProprietaire_id(),false).getName());
            tvProp.setText(identityViewModel.get(headers[groupPosition].getProprietaire_id(),false).getName());
            tvUser.setText(userViewModel.get(headers[groupPosition].getUser_id(),true,false).getHuman().getIdentity().getName());

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
            Toast.makeText(context , " **** FactureSecondLevel 111 = " + ex.toString(), Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            LigneFacture[] children = data.get(groupPosition);
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
