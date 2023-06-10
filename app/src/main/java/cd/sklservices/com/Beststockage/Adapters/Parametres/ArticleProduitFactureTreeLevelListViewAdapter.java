package cd.sklservices.com.Beststockage.Adapters.Parametres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cd.sklservices.com.Beststockage.Adapters.Stocks.FactureSecondLevelAdapter;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.OtherView.SecondLevelExpandableListView;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by SKL on 04/11/2019.
 */

public class ArticleProduitFactureTreeLevelListViewAdapter extends BaseExpandableListAdapter {

    private ArticleProduitFactureViewModel articleProduitFactureViewModel;
    private UserViewModel userViewModel;
    private IdentityViewModel identityViewModel;
    List<Devise> parentHeaders;
    List<ProduitFacture[]>secondLevel;
    private Context context;
    List<LinkedHashMap<String, ArticleProduitFacture[]>> data;

    public ArticleProduitFactureTreeLevelListViewAdapter(Context context, List<Devise> parentHeaders, List<ProduitFacture[]>secondLevel,
                                                         List<LinkedHashMap<String, ArticleProduitFacture[]>> data){
        this.context=context;
        this.secondLevel=secondLevel;
        this.parentHeaders=parentHeaders;
        this.data=data;
        this.articleProduitFactureViewModel = articleProduitFactureViewModel ;

    }

    @Override
    public int getGroupCount() {
        try {
           return parentHeaders.size();
         }
        catch (Exception ex) {
            Toast.makeText(context , ex.toString(), Toast.LENGTH_LONG).show();
        }
        return 0 ;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
      //  return secondLevel.get(groupPosition).length;
        return 1;
    }

    public int customGetChildrenCount(int groupPosition) {
        return secondLevel.get(groupPosition).length;
        //return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        try {
            return groupPosition;
        }
        catch (Exception ex) {
            Toast.makeText(context , ex.toString(), Toast.LENGTH_LONG).show();
        }
        return 0 ;

    }

    @Override
    public Object getChild(int group, int child) {
        try {
            return  child;
        }
        catch (Exception ex) {
            Toast.makeText(context , ex.toString(), Toast.LENGTH_LONG).show();
        }
        return null ;
    }

    @Override
    public long getGroupId(int groupPosition) {
        try {
            return groupPosition;
        }
         catch (Exception ex) {
            Toast.makeText(context , ex.toString(), Toast.LENGTH_LONG).show();
        }
        return 0 ;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        try {
            return childPosition;
        }
         catch (Exception ex) {
            Toast.makeText(context , ex.toString(), Toast.LENGTH_LONG).show();
        }
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.fragment_row_devise,null);
        try {
            TextView tvCode =(TextView) convertView.findViewById(R.id.tv_row_devise_code);
            TextView tvDesignation =(TextView) convertView.findViewById(R.id.tv_row_devise_designation);
            TextView tvCount =(TextView) convertView.findViewById(R.id.tv_row_devise_count_lines);


            tvCode.setText( this.parentHeaders.get(groupPosition).getCode());
            tvDesignation.setText(this.parentHeaders.get(groupPosition).getDesignation());
            tvCount.setText(customGetChildrenCount(groupPosition)+ " lignes");

        }
        catch (Exception ex) {
            Toast.makeText(context , ex.toString(), Toast.LENGTH_LONG).show();
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        ProduitFacture[] headers = secondLevel.get(groupPosition);

        try {

            List<ArticleProduitFacture[]> childData = new ArrayList<>();
            HashMap<String, ArticleProduitFacture[]> secondLevelData = data.get(groupPosition);

            for (String key : secondLevelData.keySet()) {
                childData.add(secondLevelData.get(key));
            }

           secondLevelELV.setAdapter(new ArticleProduitFactureSecondLevelAdapter(context,headers,childData));
           secondLevelELV.setGroupIndicator(null);

            secondLevelELV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                int previousGroup = -1;

                @Override
                public void onGroupExpand(int groupPosition) {
                    if (groupPosition != previousGroup)
                        secondLevelELV.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
                }
            });



        }
        catch (Exception ex) {
            Toast.makeText(context , ex.toString(), Toast.LENGTH_LONG).show();
        }

        return secondLevelELV;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void update(ArrayList<Devise> result){
        parentHeaders=new ArrayList<>();
        parentHeaders.addAll(result);
        notifyDataSetChanged();
      //  AgenceView.agence_refresh_end();
    }
}
