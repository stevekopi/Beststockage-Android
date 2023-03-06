package cd.sklservices.com.Beststockage.Adapters.Registres;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;

public class SpinFournisseurAdapter extends ArrayAdapter<Fournisseur> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<Fournisseur> instances;

    public SpinFournisseurAdapter(Context context, int textViewResourceId,List<Fournisseur> instances) {
        super(context, textViewResourceId, instances);
        this.context = context;
        this.instances = instances;
    }

    @Override
    public int getCount(){
        return instances.size();
    }

    @Override
    public Fournisseur getItem(int position){
        return instances.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLUE);
        label.setText(instances.get(position).getIdentity().getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;


    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(instances.get(position).getIdentity().getName());

        return label;
    }
}
