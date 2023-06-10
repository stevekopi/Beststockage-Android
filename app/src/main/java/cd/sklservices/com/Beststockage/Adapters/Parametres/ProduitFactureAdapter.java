package cd.sklservices.com.Beststockage.Adapters.Parametres;

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
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;

/**
 * Created by SKL on 25/04/2019.
 */

public class ProduitFactureAdapter extends BaseAdapter {

    Context context;
    List<ProduitFacture> instances;
    boolean insideOnclick;

    public ProduitFactureAdapter(Context context, List<ProduitFacture> instances, boolean insideOnclick) {
        this.context = context;
        this.instances = instances;
        this.insideOnclick=insideOnclick;

    }

    public ProduitFactureAdapter(){

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
        final ProduitFacture instance=(ProduitFacture) getItem(i);

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.cell_produit_facture_lite,null);
        }

        TextView tv_designation=convertView.findViewById(R.id.tv_cell_produit_facture_designation);
        TextView tv_cell_devise= convertView.findViewById(R.id.tv_cell_produit_facture_devise);

        tv_designation.setText(instance.getDesignation().toUpperCase());
        tv_cell_devise.setText(new DeviseViewModel(MainActivity.application).get(instance.getDevise_id()).getCode());

        convertView.setTag(i);

        if(insideOnclick){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //Demande de l'affichage du details
                        ((MainActivity) context).afficherDetailsProduitFacture(instance);
                    }
                    catch (Exception e) {
                        Toast.makeText(context, e.toString() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return convertView;

    }

    public void addItem(List<ProduitFacture> result){
        instances.addAll(result);
        notifyDataSetChanged();
    }

    public void update(ArrayList<ProduitFacture> result){
        instances =new ArrayList<>();
        instances.addAll(result);
        notifyDataSetChanged();
    }

}
