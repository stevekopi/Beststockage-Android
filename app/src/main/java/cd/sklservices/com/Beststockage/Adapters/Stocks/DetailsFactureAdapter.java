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
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.layout.Registres.AgenceView;

/**
 * Created by SKL on 25/04/2019.
 */

public class DetailsFactureAdapter extends BaseAdapter {

    private List<LigneFacture> ligneFactures;
    private LayoutInflater inflater;
    private Context context;


    public DetailsFactureAdapter(Context context, List<LigneFacture> ligneFactures){
        this.ligneFactures = ligneFactures;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
       /// this.mList= ligneFactures;
    }

    public void addListItemToAdapter(ArrayList<String> list){
       /// mList.addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * Retourne Le nombre de lignes
     * @return
     */
    @Override
    public int getCount() {
        return ligneFactures.size();
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param position
     * @return
     */

    @Override
    public Object getItem(int position) {
        return ligneFactures.get(position);
    }

    /**
     * Retourne l'indice par rapport à la ligne actuelle
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Retourne la ligne (View) formatée avec gestion des evenements
     * @param i
     * @param view
     * @param parent
     * @return
     */

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LigneFacture ligneFacture=(LigneFacture) getItem(i);

        if (view==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.cell_facture_details_ligne,null);
        }
        TextView tv_position=view.findViewById(R.id.tv_cell_facture_details_ligne_position);
        TextView tv_article_description=view.findViewById(R.id.tv_cell_facture_details_ligne_article_description);
        TextView tv_article_label=view.findViewById(R.id.tv_cell_facture_details_ligne_article_bonus_label);
        TextView tv_bonus_label=view.findViewById(R.id.tv_cell_facture_details_ligne_bonus_label);
        TextView tv_article_bonus_description= view.findViewById(R.id.tv_cell_facture_details_ligne_article_bonus_description);
        TextView tv_quantite =view.findViewById(R.id.tv_cell_facture_details_ligne_quantite);
        TextView tv_bonus =view.findViewById(R.id.tv_cell_facture_details_ligne_bonus);
        TextView tv_prix_unitaire =view.findViewById(R.id.tv_cell_facture_details_ligne_pu);
        TextView tv_tva =view.findViewById(R.id.tv_cell_facture_details_ligne_tva);
        TextView tv_montant_ht =view.findViewById(R.id.tv_cell_facture_details_ligne_montant_ht);
        TextView tv_montant_ttc =view.findViewById(R.id.tv_cell_facture_details_ligne_montant_ttc);
        TextView tv_montant_net =view.findViewById(R.id.tv_cell_facture_details_ligne_montant_net);


        tv_position.setText(String.valueOf(i+1));
        ArticleProduitFacture articleProduitFacture=new ArticleProduitFactureViewModel(MainActivity.application).get(ligneFacture.getArticle_produit_facture_id(),true,false);

        tv_article_description.setText(articleProduitFacture.getArticle().getDescription());
        tv_quantite.setText(MesOutils.spacer(String.valueOf(ligneFacture.getQuantite())));

        tv_prix_unitaire.setText(String.valueOf(articleProduitFacture.getMontant_ttc()));
        tv_tva.setText(String.valueOf(ligneFacture.getTva()));

        tv_montant_ht.setText(String.valueOf(ligneFacture.getMontant_ht()));
        tv_montant_ttc.setText(String.valueOf(ligneFacture.getMontant_ttc()));

        tv_montant_net.setText(String.valueOf(ligneFacture.getMontant_net()));

        ArticleProduitFacture art = new ArticleProduitFactureViewModel(MainActivity.application).get(ligneFacture.getArticle_produit_facture_id(),true,false);


        if(new ProduitFactureViewModel(MainActivity.application).get(art.getProduit_id()).getWith_bonus()==1){
            Article articleBonus=new ArticleViewModel(MainActivity.application).get(ligneFacture.getArticle_bonus_id(),true,true);
            tv_article_bonus_description.setText(articleBonus.getDescription());
            tv_bonus.setText(String.valueOf(ligneFacture.getBonus()));
            tv_article_bonus_description.setVisibility(View.VISIBLE);
            tv_bonus.setVisibility(View.VISIBLE);
            tv_article_label.setVisibility(View.VISIBLE);
            tv_bonus_label.setVisibility(View.VISIBLE);
        }
        else
        {
            tv_article_bonus_description.setVisibility(View.GONE);
            tv_bonus.setVisibility(View.GONE);
            tv_article_label.setVisibility(View.GONE);
            tv_bonus_label.setVisibility(View.GONE);
        }

        view.setTag(i);


        return view;
    }


    public void update(ArrayList<LigneFacture> result){
        ligneFactures =new ArrayList<>();
        ligneFactures.addAll(result);
        notifyDataSetChanged();
        AgenceView.agence_refresh_end();
    }


}
