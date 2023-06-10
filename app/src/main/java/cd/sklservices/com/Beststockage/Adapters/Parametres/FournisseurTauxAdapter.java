package cd.sklservices.com.Beststockage.Adapters.Parametres;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.FournisseurTaux;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.FournisseurTauxViewModel;

/**
 * Created by SKL on 25/04/2019.
 */

public class FournisseurTauxAdapter extends BaseAdapter {

    Context context;
    List<FournisseurTaux> instances;
    boolean insideOnclick;

    public FournisseurTauxAdapter(Context context, List<FournisseurTaux> instances, boolean insideOnclick) {
        this.context = context;
        this.instances = instances;
        this.insideOnclick=insideOnclick;

    }

    public FournisseurTauxAdapter(){

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
        FournisseurTaux instance=(FournisseurTaux) getItem(i);
        instance=new FournisseurTauxViewModel(MainActivity.application).get(instance.getId(),true,true);

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.cell_fournisseur_taux,null);
        }
        TextView tv_fournisseur=convertView.findViewById(R.id.tv_cell_fournisseur_taux_fournisseur);
        TextView tv_from=convertView.findViewById(R.id.tv_cell_fournisseur_taux_from_code);
        TextView tv_to= convertView.findViewById(R.id.tv_cell_fournisseur_taux_to_code);
        TextView tv_amount=convertView.findViewById(R.id.tv_cell_fournisseur_taux_amount);
        TextView tv_date= convertView.findViewById(R.id.tv_cell_fournisseur_taux_date);
        TextView tv_statut=convertView.findViewById(R.id.tv_cell_fournisseur_taux_statut);

        tv_fournisseur.setText(instance.getFournisseur().getIdentity().getName().toUpperCase(Locale.ROOT));
        tv_from.setText(new DeviseViewModel(MainActivity.application).get(instance.getFrom_id()).getCode());
        tv_to.setText(new DeviseViewModel(MainActivity.application).get(instance.getTo_id()).getCode());
        tv_amount.setText(String.valueOf(instance.getAmount()));
        tv_date.setText("Depuis\n"+MesOutils.Get_date_en_francais(instance.getDate()));
        if(instance.getStatut()==1){
            tv_statut.setText("En cours");
            tv_statut.setTextColor(Color.argb(255, 0, 121, 0));
        }
        else{
            tv_statut.setText("Expiré");
            tv_statut.setTextColor(Color.argb(255, 255, 0, 0));
        }

        convertView.setTag(i);

        return convertView;

    }

    public void addItem(List<FournisseurTaux> result){
        instances.addAll(result);
        notifyDataSetChanged();
    }

    public void update(ArrayList<FournisseurTaux> result){
        instances =new ArrayList<>();
        instances.addAll(result);
        notifyDataSetChanged();
    }

}
