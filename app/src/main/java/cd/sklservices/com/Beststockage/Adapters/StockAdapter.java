package cd.sklservices.com.Beststockage.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stock;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.StockViewModel;

public class StockAdapter extends ArrayAdapter{

    Context context;
    List<Stock> instances;

    public StockAdapter(@NonNull Context context, @NonNull List<Stock> stocks) {
        super(context,0);

        this.context = context;
        this.instances = stocks;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.fragment_stock_enfant,null);
        }

        TextView indice = convertView.findViewById(R.id.tv_stock_indice);
        LinearLayout ll_stock =  convertView.findViewById(R.id.lL_tv_stock);
        TextView tv_stock_article_designation = convertView.findViewById(R.id.tv_stock_article_designation);
        TextView tv_stock_quantite = convertView.findViewById(R.id.tv_stock_quantite);

        /////// On selectionne tous les stocks pour cette agences 0......

        Stock item = (Stock) getItem(position);

        tv_stock_article_designation.setText(item.getArticle().getDescription());
        tv_stock_quantite.setText(MesOutils.spacer(String.valueOf(item.getQuantite())));
        indice.setText(String.valueOf(position+1));

        if(item.getQuantite()<10)
        {
            ll_stock.setBackgroundColor(Color.parseColor("#FF0000"));
            tv_stock_quantite.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            ll_stock.setBackgroundColor(Color.parseColor("#2563D5"));
            tv_stock_quantite.setTextColor(Color.parseColor("#FFFFFF"));
        }


        return convertView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
      return  super.getItem(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

   public void update(List<Stock> result){
        instances =new ArrayList<>();
        instances.addAll(result);
        notifyDataSetChanged();
    }


}
