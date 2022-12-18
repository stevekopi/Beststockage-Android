package cd.sklservices.com.Beststockage.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.StockViewModel;


/**
 * Created by SKL on 13/05/2019.
 */

public class Stock_expandable_adapter extends BaseExpandableListAdapter {

    private ArticleViewModel articleViewModel ;
    private StockViewModel stockViewModel ;
    private OperationViewModel operationViewModel ;
    Context context;
    List<Agence> agence;
    Map<Agence,List<Stock>> stocks;

    public Stock_expandable_adapter(Context context, OperationViewModel operationViewModel1, ArticleViewModel articleViewModel1,
            StockViewModel stockViewModel1  , List<Agence> agence, Map<Agence, List<Stock>> stock) {
        this.context = context;
        this.agence = agence;
        this.stocks = stock;

        this.operationViewModel = operationViewModel1 ;
        this.articleViewModel = articleViewModel1 ;
        this.stockViewModel = stockViewModel1 ;
    }

    public Stock_expandable_adapter(){

    }

    @Override
    public int getGroupCount() {
        return agence.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
       return stocks.get(agence.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return agence.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return stocks.get(agence.get(groupPosition)).get(childPosition);
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

        Agence agence=(Agence) getGroup(groupPosition);
        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.fragment_stock_parent,null);
        }

        TextView tvAgence=(TextView) convertView.findViewById(R.id.tv_stock_agence);
        TextView tvType=(TextView) convertView.findViewById(R.id.tv_stock_agence_type);
        TextView tvTelephone =(TextView) convertView.findViewById(R.id.tv_tel_agence_stock);
        TextView tvNombre_Article =(TextView) convertView.findViewById(R.id.tv_nombre_article);

        int count_agence = operationViewModel.getOperationWhere(agence.getId()) ;

        if(count_agence == 0)
        {
            tvNombre_Article.setText("*") ;
        }
        else if(count_agence == 1)
        {
            tvNombre_Article.setText("1 article") ;
        }
        else
        {
            tvNombre_Article.setText(String.valueOf(count_agence) + " articles") ;
        }

        tvAgence.setText(agence.getDenomination().toUpperCase());
        tvType.setText(agence.getType().toUpperCase());
        tvTelephone.setText(agence.getTel().toString());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_stock_enfant, null);
        }

        TextView indice = (TextView) convertView.findViewById(R.id.tv_stock_indice);
        LinearLayout ll_stock =  (LinearLayout) convertView.findViewById(R.id.lL_tv_stock);
        TextView tv_stock_article_designation = (TextView) convertView.findViewById(R.id.tv_stock_article_designation);
        TextView tv_stock_quantite = (TextView) convertView.findViewById(R.id.tv_stock_quantite);

        /////// On selectionne tous les stocks pour cette agences 0......

        Stock item = (Stock) getChild(groupPosition,childPosition);

        String pos = String.valueOf(childPosition + 1)  ;

        tv_stock_article_designation.setText(item.getArticle().getDescription());
        tv_stock_quantite.setText(String.valueOf(item.getQuantite()));
        indice.setText(pos);

        Boolean test = stockViewModel.test_if_operation_attente(item.getAgence().getId(), item.getArticle().getId());

        if(test.equals(true))
        {
            ll_stock.setBackgroundColor(Color.parseColor("#FF81B4"));
            tv_stock_quantite.setTextColor(Color.parseColor("#000000"));
        }
        else
        {
            ll_stock.setBackgroundColor(Color.parseColor("#2563D5"));
            tv_stock_quantite.setTextColor(Color.parseColor("#FFFFFF"));
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void update(ArrayList<Agence> result){
        agence=new ArrayList<>();
        agence.addAll(result);
        notifyDataSetChanged();
    }

}
