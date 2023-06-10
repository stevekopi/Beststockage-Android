package cd.sklservices.com.Beststockage.Adapters.Parametres;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;

/**
 * Created by SKL on 25/04/2019.
 */

public class DeviseAdapter extends BaseAdapter {

    Context context;
    List<Devise> instances;
    boolean insideOnclick;

    public DeviseAdapter(Context context, List<Devise> instances, boolean insideOnclick) {
        this.context = context;
        this.instances = instances;
        this.insideOnclick=insideOnclick;

    }

    public DeviseAdapter(){

    }


    @Override
    public int getCount() {
        return instances.size();
    }

    @Override
    public Object getItem(int i) {
        return instances.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final Devise instance=(Devise) getItem(i);

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.cell_devise,null);
        }

        TextView tv_designation=convertView.findViewById(R.id.tv_cell_devise_designation);
        TextView tv_description= convertView.findViewById(R.id.tv_cell_devise_description);
        TextView tv_code= convertView.findViewById(R.id.tv_cell_devise_code);
        TextView tv_symbole= convertView.findViewById(R.id.tv_cell_devise_symbole);

        TextView tv_default= convertView.findViewById(R.id.tv_cell_devise_default);
        TextView tv_local= convertView.findViewById(R.id.tv_cell_devise_local);
        TextView tv_default_converter= convertView.findViewById(R.id.tv_cell_devise_default_converter);

        tv_default.setText("D");
        if(instance.getIs_default()==1){tv_default.setTextColor(Color.argb(255, 0, 255, 0));}
        else {tv_default.setTextColor(Color.argb(255, 255, 0, 0));}

        tv_local.setText("L");
        if(instance.getIs_local()==1){tv_local.setTextColor(Color.argb(255, 0, 255, 0));}
        else {tv_local.setTextColor(Color.argb(255, 255, 0, 0));}

        tv_default_converter.setText("C");
        if(instance.getIs_default_converter()==1){tv_default_converter.setTextColor(Color.argb(255, 0, 255, 0));}
        else {tv_default_converter.setTextColor(Color.argb(255, 255, 0, 0));}

        tv_designation.setText(instance.getDesignation().toUpperCase());
        tv_description.setText(instance.getDescription());
        tv_code.setText(instance.getCode());
        tv_symbole.setText(instance.getSymbole());

        convertView.setTag(i);

        if(insideOnclick){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //Demande de l'affichage du details
                        ((MainActivity) context).afficherDetailsDevise(instance);
                    }
                    catch (Exception e) {
                        Toast.makeText(context, e.toString() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return convertView;

    }

    public void addItem(List<Devise> result){
        instances.addAll(result);
        notifyDataSetChanged();
    }

    public void update(ArrayList<Devise> result){
        instances =new ArrayList<>();
        instances.addAll(result);
        notifyDataSetChanged();
    }

}
