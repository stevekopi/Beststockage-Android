package cd.sklservices.com.Beststockage.Adapters.Registres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;

/**
 * Created by SKL on 25/04/2019.
 */

public class AgenceAdapter extends BaseAdapter {

    Context context;
    List<Agence> agences;
    boolean insideOnclick;

    public AgenceAdapter(Context context, List<Agence> agences,boolean insideOnclick) {
        this.context = context;
        this.agences = agences;
        this.insideOnclick=insideOnclick;

    }

    public AgenceAdapter(){

    }


    @Override
    public int getCount() {
        return agences.size();
    }

    @Override
    public Object getItem(int i) {
        return agences.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final Agence agence=(Agence) getItem(i);

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.cell_agence,null);
        }

        TextView tv_nom_agence=convertView.findViewById(R.id.tv_nom_agence);
        TextView tv_tel_agence= convertView.findViewById(R.id.tv_tel_agence);
        TextView tv_type_agence =convertView.findViewById(R.id.tv_type_agence);
        TextView tv_proprietaire =convertView.findViewById(R.id.tv_agence_proprietaire_name);
        TextView tv_addingdate_agence =convertView.findViewById(R.id.tv_addingdate_agence);


        tv_nom_agence.setText(agence.getDenomination().toUpperCase());
        tv_type_agence.setText(agence.getType().toUpperCase());
        tv_tel_agence.setText(agence.getTel());
        tv_addingdate_agence.setText("*");
        tv_proprietaire.setText("De : "+agence.getProprietaire().getName());

        convertView.setTag(i);

        if(insideOnclick){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //Demande de l'affichage du details
                        ((MainActivity) context).afficherDetailsAgence(agence);
                    }
                    catch (Exception e) {
                        Toast.makeText(context, e.toString() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return convertView;

    }

    public void addItem(List<Agence> result){
        agences.addAll(result);
        notifyDataSetChanged();
    }

    public void update(ArrayList<Agence> result){
        agences=new ArrayList<>();
        agences.addAll(result);
        notifyDataSetChanged();
    }

}
