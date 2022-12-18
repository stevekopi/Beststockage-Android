package cd.sklservices.com.Beststockage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Agence;
import cd.sklservices.com.Beststockage.Classes.Approvisionnement;
import cd.sklservices.com.Beststockage.Classes.Article;
import cd.sklservices.com.Beststockage.Classes.Livraison;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.LivraisonViewModel;


/**
 * Created by SKL on 13/05/2019.
 */

public class Livraison1_expandable_adapter extends BaseExpandableListAdapter {

    private ArticleViewModel articleViewModel ;
    private AgenceViewModel agenceViewModel;
    private LivraisonViewModel livraisonViewModel;
    Context context;
    List<String> periode;
    Map<String,List<Approvisionnement>> approvisionnement;

    public Livraison1_expandable_adapter(Context context, LivraisonViewModel livraisonViewModel1, AgenceViewModel agenceViewModel1, ArticleViewModel articleViewModel1,
                                         List<String> periode, Map<String, List<Approvisionnement>> approvisionnement) {
        this.context = context;
        this.approvisionnement = approvisionnement;
        this.periode = periode;

        this.agenceViewModel = agenceViewModel1 ;
        this.articleViewModel = articleViewModel1 ;
        this.livraisonViewModel = livraisonViewModel1 ;
    }

    public Livraison1_expandable_adapter(){

    }

    @Override
    public int getGroupCount() {
        if (periode!=null){
            return periode.size();
        }else{
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return approvisionnement.get(periode.get(groupPosition)).size();
        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }

        return 0 ;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return periode.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return  approvisionnement.get(periode.get(groupPosition)).get(childPosition);
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
            String periode = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.fragment_date,null);
            }

            TextView tvDate =(TextView) convertView.findViewById(R.id.tv_date);
            TextView tvCountChildren =(TextView) convertView.findViewById(R.id.tv_date_count_children);

            tvDate.setText(MesOutils.Get_date_en_francais(periode.toUpperCase()));
            tvCountChildren.setText(getChildrenCount(groupPosition)+" lignes");

            tvDate.setText(MesOutils.Get_date_en_francais(periode.toUpperCase()));

        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        try {
            final Approvisionnement item = (Approvisionnement) getChild(groupPosition, childPosition);
            Livraison livraison = livraisonViewModel.get(item.getLivraison_id());

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cell_livraison1, null);
            }

            TextView date = convertView.findViewById(R.id.tv_livraison_date);
            TextView tv_livraison_fournisseur_agence =  convertView.findViewById(R.id.tv_livraison_fournisseur_agence);
            TextView agence = convertView.findViewById(R.id.tv_livraison_agence);
            TextView article = convertView.findViewById(R.id.tv_livraison_article);
            TextView quantite = convertView.findViewById(R.id.tv_livraison_quantite);

            /////// On selectionne tous les stocks pour cette agences 0......


            final Article art = articleViewModel.get(item.getArticle_id(),true,true);
            Agence agence1 = agenceViewModel.get(item.getAgence_id(),false,false);
            Agence ConnectedAgence = agenceViewModel.get(MainActivity.getCurrentUser().getAgence_id(),false,false);

            date.setText(livraison.getDate().toString());
            tv_livraison_fournisseur_agence.setText(ConnectedAgence.getType()+" "+ConnectedAgence.getDenomination());
            agence.setText(agence1.getDenomination());
            article.setText(art.getDescription());
            quantite.setText("Q : " + item.getQuantite() + "  - B : " + item.getBonus());

            convertView.setTag(groupPosition);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).afficherDetailsLivraison1(item);
                }
            });


            return convertView;
        }catch (Exception ex)
        {

        }
        return null ;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void update(ArrayList<String> result){
        try {
            periode = new ArrayList<>();
            periode.addAll(result);
            notifyDataSetChanged();
        }
        catch (Exception ex)
        {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
