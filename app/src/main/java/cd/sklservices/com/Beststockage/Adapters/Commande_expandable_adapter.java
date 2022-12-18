package cd.sklservices.com.Beststockage.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.* ;

/**
 * Created by SKL on 13/05/2019.
 */

public class Commande_expandable_adapter extends BaseExpandableListAdapter {

    private CommandeViewModel commandeViewModel ;
    private ArticleViewModel articleViewModel ;
    private AgenceViewModel agenceViewModel ;
    private IdentityViewModel identityViewModel ;

    Context context;
    List<Commande> parents;
    Map<Commande,List<LigneCommande>> enfants;

    public Commande_expandable_adapter(Context context, CommandeViewModel commande_v,  ArticleViewModel article_v,
                                       AgenceViewModel agence_v , IdentityViewModel identity_v, List<Commande> parents, Map<Commande, List<LigneCommande>> enfants) {
        this.context = context;
        this.parents = parents;
        this.enfants = enfants;

        this.commandeViewModel = commande_v;        this.articleViewModel = article_v ;
        this.agenceViewModel = agence_v ;           this.identityViewModel = identity_v ;
    }

    public Commande_expandable_adapter(){

    }

    @Override
    public int getGroupCount() {
        return parents.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
       return enfants.get(parents.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return enfants.get(parents.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        try {
            Commande commande = (Commande) getGroup(groupPosition);
            Identity identity = identityViewModel.get(commande.getProprietaire_id(),false) ;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_commande_parent, null);
            }

            TextView tv_date = (TextView) convertView.findViewById(R.id.tv_commande_parent_date);
            TextView tv_id = (TextView) convertView.findViewById(R.id.tv_commande_parent_id);
            TextView tv_pourcentage = (TextView) convertView.findViewById(R.id.tv_livraison);
            TextView tv_user = (TextView) convertView.findViewById(R.id.tv_by_user);
            TextView tv_montant = (TextView) convertView.findViewById(R.id.tv_commande_parent_montant);

            TextView tv_synchronisation = (TextView) convertView.findViewById(R.id.tv_synchronisation);
           // ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.pb_commande_parent);


            tv_user.setText( identity.getName().toString());
            tv_montant.setText(String.valueOf(commande.getMontant()+" Fc"));

            tv_date.setText((commande.getDate()).substring(0, 10).toUpperCase());
            tv_id.setText(commande.getId());

            String id = commande.getId();
            int sqa = commandeViewModel.getSommeQuantiteApprovisionner(id);
            int sqc = commandeViewModel.getSommeQuantiteCommander(id);
            int reste = sqc - sqa;

            if (reste == sqc) {
                tv_synchronisation.setTextColor(Color.RED);
            } else if (reste == 0) {
                tv_synchronisation.setTextColor(Color.GREEN);
            } else {
                tv_synchronisation.setTextColor(Color.YELLOW);
            }

            Double va = Double.valueOf(sqa);
            Double vc = Double.valueOf(sqc);
            Double pourcentage = (va / vc) * 100;
            int val = pourcentage.intValue();

            // progressBar.setMax(100);
            // progressBar.setProgress(val);

            String valPourc = String.valueOf(pourcentage);


            if (valPourc.length() > 4) {
                tv_pourcentage.setText(valPourc.substring(0, 5) + "% Livré");
            } else if (valPourc.length() < 4) {
                tv_pourcentage.setText(valPourc.substring(0, 3) + "% Livré");
            }


        }
        catch (Exception e) {
            Toast.makeText(context, e.toString() , Toast.LENGTH_LONG).show();
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        try {
            final LigneCommande item = (LigneCommande) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_stock_enfant, null);
            }

            TextView indice = (TextView) convertView.findViewById(R.id.tv_stock_indice);
            LinearLayout ll_stock =  (LinearLayout) convertView.findViewById(R.id.lL_tv_stock);
            TextView tv_stock_article_designation = (TextView) convertView.findViewById(R.id.tv_stock_article_designation);
            TextView tv_stock_quantite = (TextView) convertView.findViewById(R.id.tv_stock_quantite);

            Article art = articleViewModel.get(item.getArticle_id(),true,true);
            Agence agence = agenceViewModel.get(item.getAgence_id(),false,false);

            String pos = String.valueOf(childPosition + 1)  ;
            indice.setText(pos);

            tv_stock_article_designation.setText(art.getDescription() );
            tv_stock_quantite.setText(String.valueOf(item.getMontant()) + " Fc");

            int qa = commandeViewModel.getSommeQuantiteApprovisionner(item.getId());
            int qt = item.getQuantite();
            int reste = qt - qa;

            if(item.getSync_pos() == 0)
            {
                ll_stock.setBackgroundColor(Color.parseColor("#FF0000"));
                tv_stock_quantite.setTextColor(Color.parseColor("#000000"));
            }
            else if (reste == qt) {
                ll_stock.setBackgroundColor(Color.parseColor("#FF81B4"));
                tv_stock_quantite.setTextColor(Color.parseColor("#000000"));
            } else if (reste == 0) {
                ll_stock.setBackgroundColor(Color.parseColor("#8CFFA5"));
                tv_stock_quantite.setTextColor(Color.parseColor("#000000"));
            } else {
                ll_stock.setBackgroundColor(Color.parseColor("#EEF372"));
                tv_stock_quantite.setTextColor(Color.parseColor("#000000"));
            }

            convertView.setTag(groupPosition);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redirectionVersItemChoisit(item);
                }
            });

        }
        catch (Exception e) {
          //  Toast.makeText(context, "Click == " + e.toString() , Toast.LENGTH_LONG).show();
        }
        return convertView;
    }

    private void redirectionVersItemChoisit(LigneCommande ligne){
        // int position=(int)v.getTag();
        //Demande de l'affichage du details
        ((MainActivity)context).afficherDetailsCommande(ligne);
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void update(ArrayList<Commande> result){
        parents =new ArrayList<>();
        parents.addAll(result);
        notifyDataSetChanged();
    }
}
