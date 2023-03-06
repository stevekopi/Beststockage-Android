package cd.sklservices.com.Beststockage.Adapters.Stocks;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinOperationTypeAdapter extends ArrayAdapter<String> {

     private Context context;
    private List<String> values;

    public SpinOperationTypeAdapter(Context context, int textViewResourceId,
                                    List<String> strings) {
        super(context, textViewResourceId, strings);
        this.context = context;
        this.values = strings;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public String getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

   @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLUE);
        label.setText(values.get(position));

         return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position));
        return label;
    }
}
