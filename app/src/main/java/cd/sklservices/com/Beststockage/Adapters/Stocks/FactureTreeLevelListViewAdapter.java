package cd.sklservices.com.Beststockage.Adapters.Stocks;

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

import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.OtherView.SecondLevelExpandableListView;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by SKL on 04/11/2019.
 */

public class FactureTreeLevelListViewAdapter extends BaseExpandableListAdapter {

    List<String> parentHeaders;
    List<Facture[]>secondLevel;
    private Context context;
    List<LinkedHashMap<String, LigneFacture[]>> data;

    public FactureTreeLevelListViewAdapter(Context context,List<String> parentHeaders, List<Facture[]>secondLevel,List<LinkedHashMap<String,LigneFacture[]>> data){
        this.context=context;
        this.secondLevel=secondLevel;
        this.parentHeaders=parentHeaders;
        this.data=data;

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
        convertView=inflater.inflate(R.layout.fragment_date,null);
        try {
            TextView tvDate =(TextView) convertView.findViewById(R.id.tv_date);
            TextView tvCountChildren =(TextView) convertView.findViewById(R.id.tv_date_count_children);


            tvDate.setText( MesOutils.Get_date_en_francais(this.parentHeaders.get(groupPosition)) );
            tvCountChildren.setText(customGetChildrenCount(groupPosition)+ " lignes");

        }
        catch (Exception ex) {
            Toast.makeText(context , ex.toString(), Toast.LENGTH_LONG).show();
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        Facture[] headers = secondLevel.get(groupPosition);

        try {

            List<LigneFacture[]> childData = new ArrayList<>();
            HashMap<String, LigneFacture[]> secondLevelData = data.get(groupPosition);
            for (String key : secondLevelData.keySet()) {
                childData.add(secondLevelData.get(key));
            }

           secondLevelELV.setAdapter(new FactureSecondLevelAdapter(context, headers, childData));
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

    public void update(ArrayList<String> result){
        parentHeaders=new ArrayList<>();
        parentHeaders.addAll(result);
        notifyDataSetChanged();
      //  AgenceView.agence_refresh_end();
    }
}
