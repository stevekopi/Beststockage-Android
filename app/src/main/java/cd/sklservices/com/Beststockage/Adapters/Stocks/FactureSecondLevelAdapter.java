package cd.sklservices.com.Beststockage.Adapters.Stocks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.FactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by SKL on 05/11/2019.
 */

public class FactureSecondLevelAdapter extends BaseExpandableListAdapter {
    private Context context;
    List<LigneFacture[]> data;
    Facture[] headers;


    public FactureSecondLevelAdapter(Context context, Facture[] headers, List<LigneFacture[]> data){
        try {
            this.context = context;
            this.headers = headers;
            this.data = data;

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

            TextView tv_article_description =  convertView.findViewById(R.id.tv_facture_row_third_article_description);
            TextView tv_article_bonus_description=  convertView.findViewById(R.id.tv_facture_row_third_article_bonus_description);
            TextView tv_quantite =  convertView.findViewById(R.id.tv_facture_row_third_quantite);
            TextView tv_bonus =  convertView.findViewById(R.id.tv_facture_row_third_bonus);
            TextView tv_montant_net = convertView.findViewById(R.id.tv_facture_row_third_montant_net);
            LigneFacture[] childArray = data.get(groupPosition);

            final LigneFacture ligneFacture = childArray[childPosition];
            Facture facture=new FactureViewModel(MainActivity.application).get(ligneFacture.getFacture_id());
            Devise devise=new DeviseViewModel(MainActivity.application).get(facture.getDevise_id());

            ArticleProduitFacture art = new ArticleProduitFactureViewModel(MainActivity.application).get(ligneFacture.getArticle_produit_facture_id(),true,false);
            if(new ProduitFactureViewModel(MainActivity.application).get(art.getProduit_id()).getWith_bonus()==1){
                tv_article_bonus_description.setText("Art. Bns : "+new ArticleViewModel(MainActivity.application).get(ligneFacture.getArticle_bonus_id(),true,true).getDescription());
                tv_bonus.setText("B : " + ligneFacture.getBonus());
                tv_article_bonus_description.setVisibility(View.VISIBLE);
                tv_bonus.setVisibility(View.VISIBLE);
            }
            else
            {
                tv_article_bonus_description.setVisibility(View.GONE);
                tv_bonus.setVisibility(View.GONE);
            }

            tv_article_description.setText("Article : "+art.getArticle().getDescription());
            tv_quantite.setText("Q : " + ligneFacture.getQuantite());

            tv_montant_net.setText(MesOutils.spacer(String.valueOf(Math.round(ligneFacture.getMontant_net())))+" "+devise.getCode());

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
            TextView tvAgence =  convertView.findViewById(R.id.tv_facture_row_second_agence_denomination);
            TextView tvProduit = convertView.findViewById(R.id.tv_facture_row_second_produit);
            TextView tvProp =  convertView.findViewById(R.id.tv_facture_row_second_proprietaire);
             TextView tvSynchro = convertView.findViewById(R.id.tv_facture_synchronisation);

            Facture instance=headers[groupPosition];

            Agence agence=new AgenceViewModel(MainActivity.application).get(instance.getAgence_2_id(),false,false);

            tvAgence.setText(agence.getType()+" "+agence.getDenomination());
            tvProduit.setText(new ProduitFactureViewModel(MainActivity.application).get(instance.getProduit_id()).getDesignation());
            tvProp.setText(new IdentityViewModel(MainActivity.application).get(instance.getProprietaire_id(),false).getName());

            if(headers[groupPosition].getSync_pos() == 0)
            {
                tvSynchro.setTextColor(Color.RED);
            }
            else
            {
                tvSynchro.setTextColor(Color.GREEN);
            }

           TextView tv_details =convertView.findViewById(R.id.tv_facture_second_row_details);

            tv_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).afficherDetailsFacture(instance);
                }
            });


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
