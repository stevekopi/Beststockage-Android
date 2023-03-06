package cd.sklservices.com.Beststockage.Adapters.Stocks;

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
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.OperationViewModel;

/**
 * Created by SKL on 13/05/2019.
 */

public class Operation_expandable_adapter extends BaseExpandableListAdapter {

    private ArticleViewModel articleViewModel ;
    private AgenceViewModel agenceViewModel ;
    private OperationViewModel operationViewModel ;

    Context context;
    List<String> periode;
    Map<String,List<Operation>> operations;
    LinearLayout ll_operation;

    public Operation_expandable_adapter(Context context, ArticleViewModel articleViewModel1, AgenceViewModel agenceViewModel1 ,
                                        OperationViewModel operationViewModel1, List<String> periode, Map<String, List<Operation>> operation) {
        try {
            this.context = context;
            this.periode = periode;
            this.operations = operation;

            this.articleViewModel = articleViewModel1;
            this.agenceViewModel = agenceViewModel1;
            this.operationViewModel = operationViewModel1 ;
        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public Operation_expandable_adapter(){

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
            return operations.get(periode.get(groupPosition)).size();
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
        return  operations.get(periode.get(groupPosition)).get(childPosition);
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

            TextView tvDate =convertView.findViewById(R.id.tv_date);
            TextView tvSp =convertView.findViewById(R.id.tv_sp);
            TextView tvCountChildren =convertView.findViewById(R.id.tv_date_count_children);

            tvDate.setText(MesOutils.Get_date_en_francais(periode.toUpperCase()));
            tvCountChildren.setText(getChildrenCount(groupPosition)+" lignes");

            Boolean test = operationViewModel.test_if_operationNotSync(periode.toUpperCase()) ;


            tvSp.setTextColor(test.equals(true)?Color.RED:Color.GREEN);

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
            final Operation instance = (Operation) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_operation_enfant, null);
            }

            TextView tv_operation_type = convertView.findViewById(R.id.tv_operation_enfant_type);
            TextView tv_operation_agence = convertView.findViewById(R.id.tv_operation_enfant_agence);
            TextView tv_operation_date =  convertView.findViewById(R.id.tv_operation_enfant_date);
            TextView tv_operation_quantite = convertView.findViewById(R.id.tv_operation_enfant_quantite);
            TextView tv_operation_article =convertView.findViewById(R.id.tv_operation_enfant_article);
            TextView tv_operation_montant =  convertView.findViewById(R.id.tv_operation_enfant_montant);
            LinearLayout ll_confirm=convertView.findViewById(R.id.ll_operation_confirm);
            TextView tv_is_checked=convertView.findViewById(R.id.tv_operation_is_checked);

            LinearLayout ll_sync_pos=convertView.findViewById(R.id.ll_operation_sync_pos);
            ll_sync_pos.setBackgroundColor(instance.getSync_pos()==1?Color.GREEN:Color.RED);
            tv_operation_type.setText(instance.getType());
            tv_operation_date.setText(instance.getDate().substring(0, 10));



            tv_operation_article.setText(articleViewModel.get(instance.getArticle_id(),true,true).getDescription());

            if (instance.getType().toLowerCase().equals("livraison")) {
                tv_operation_type.setTextColor(Color.argb(255, 35, 9, 228));
                tv_operation_type.setText("Livraison (Bon)");
            } else if (instance.getType().toLowerCase().contains("entre")) {
                tv_operation_type.setTextColor(Color.argb(255, 248, 103, 7));
            } else if (instance.getType().toLowerCase().contains("livraison_c")) {
                tv_operation_type.setTextColor(Color.argb(255, 180, 21, 199));
                tv_operation_type.setText("Livraison client");
            } else if (instance.getType().toLowerCase().contains("livraison p")) {
                tv_operation_type.setTextColor(Color.argb(255, 255, 255, 0));
                tv_operation_type.setText("Livraison PC");
            } else if (instance.getType().toLowerCase().contains("sortie")) {
                tv_operation_type.setTextColor(Color.argb(255, 244, 53, 15));
            } else if (instance.getType().toLowerCase().contains("vente_a")) {
                tv_operation_type.setTextColor(Color.argb(255, 53, 219, 45));
                tv_operation_type.setText("Vente avec bonus");
            } else if (instance.getType().toLowerCase().contains("vente_d")) {
                tv_operation_type.setTextColor(Color.argb(255, 228, 148, 9));
                tv_operation_type.setText("Vente détail");
            } else if (instance.getType().toLowerCase().contains("remise")) {
                tv_operation_type.setTextColor(Color.argb(255, 9, 225, 228));

        }

            if (Integer.valueOf(instance.getBonus()) > 0) {
                tv_operation_quantite.setText("Q: " + String.valueOf(instance.getQuantite()) + "         B: "
                        + String.valueOf(instance.getBonus()));
            } else {
                tv_operation_quantite.setText("Q: " + String.valueOf(instance.getQuantite()));
            }

            String denom = "De " + agenceViewModel.get(instance.getAgence_1_id(),false,false).getDenomination() + " vers  " +
                    agenceViewModel.get(instance.getAgence_2_id(),false,false).getDenomination();

            String denom2 = "Provenance : " + agenceViewModel.get(instance.getAgence_2_id(),false,false).getDenomination();
            if (instance.getType().equals("Sortie") || instance.getType().contains("Vente") || instance.getType().contains("Livraison_Client")) {
                tv_operation_agence.setText(denom);
            }
            else  if (instance.getType().equals("Entree")) {
                tv_operation_agence.setText(denom2);
            }
            else{
                tv_operation_agence.setText(agenceViewModel.get(instance.getAgence_1_id(),false,false).getDenomination());
            }

            if (instance.getType().toLowerCase().contains("vente")) {
                tv_operation_montant.setText(MesOutils.spacer(String.valueOf(instance.getMontant().intValue())) +" "+ instance.getDevise_id());
                tv_operation_montant.setHeight(80);
                tv_operation_montant.setVisibility(View.VISIBLE);

            } else {
                tv_operation_montant.setVisibility(View.GONE);
                tv_operation_montant.setHeight(0);
            }

            if(instance.getType().equals("Sortie") || instance.getType().equals("Entree") || instance.getType().equals("Livraison_Client")){

                ll_confirm.setVisibility(View.VISIBLE);
                if(instance.getIs_confirmed()==1){
                    ll_confirm.setBackgroundColor(Color.rgb(0,255,0));
                }else {
                    ll_confirm.setBackgroundColor(Color.rgb(255,0,0));
                }
            }else {

                ll_confirm.setVisibility(View.GONE);
            }


            tv_is_checked.setTextColor(instance.getIs_checked()==1?Color.GREEN:Color.RED);

            ll_operation=convertView.findViewById(R.id.ll_operation);
            ll_operation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Click = " + item.getType_operation(), Toast.LENGTH_LONG).show();

                  //  if(instance.getType().equals("Entree_speciale")  || instance.getType().equals("Sortie") || instance.getType().equals("Entree")
                   //         || instance.getType().equals("Vente_detail") || instance.getType().equals("Vente_avec_bonus") || instance.getType().equals("Livraison_Client"))
                   // {
                        ((MainActivity)context).afficherDetailStock(instance);
                  //  }
                  //  else
                  //  {
                  //      Toast.makeText(context, " Impossible de voir la suite - Opération sécondaire ...  ", Toast.LENGTH_SHORT).show();

                   // }

                }
            });



        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }


        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void addItem(Map<String,List<Operation>> operations){
        this.operations.putAll(operations);
        notifyDataSetChanged();
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
