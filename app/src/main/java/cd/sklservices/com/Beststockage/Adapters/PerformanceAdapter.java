package cd.sklservices.com.Beststockage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Agence;
import cd.sklservices.com.Beststockage.Classes.Performance;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import layout.PerformanceAgenceView;

/**
 * Created by SKL on 25/04/2019.
 */

public class PerformanceAdapter extends BaseAdapter {

    private AgenceViewModel agenceViewModel ;

    private Performance instance;
    private LayoutInflater inflater;
    private Context context;

    public PerformanceAdapter(Context context, AgenceViewModel agenceViewModel1, Performance instance){
        this.instance = instance;
        this.context=context;
        this.inflater=LayoutInflater.from(context);

        this.agenceViewModel = agenceViewModel1 ;
    }

    /**
     * Retourne Le nombre de lignes
     * @return
     */
    @Override
    public int getCount() {
        return 1;
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param position
     * @return
     */

    @Override
    public Object getItem(int position) {
        return 0;
    }

    /**
     * Retourne l'indice par rapport à la ligne actuelle
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Retourne la ligne (View) formatée avec gestion des evenements
     * @param i
     * @param view
     * @param parent
     * @return
     */

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        ViewHolder holder;

        try {

        //Si la ligne n'existe pas encore
        if (view==null){
            holder=new ViewHolder();
            view=inflater.inflate(R.layout.cell_performance,null);
            holder.tv_nom_agence=view.findViewById(R.id.tv_performance_nom_agence);
            holder.tv_tel_agence=view.findViewById(R.id.tv_performance_tel_agence);
            holder.iv_agence=view.findViewById(R.id.iv_performance_agence);
            holder.tv_type_agence= view.findViewById(R.id.tv_performance_type_agence);
            holder.tv_nombre_vente = view.findViewById(R.id.tv_performance_nombre_vente);
            holder.tv_quantite_vendu = view.findViewById(R.id.tv_performance_quantite_vendu);
            holder.tv_montant=view.findViewById(R.id.tv_performance_montant);
            holder.tv_quantite_livre = view.findViewById(R.id.tv_performance_quantite_livre);
            holder.tv_nombre_livraison=view.findViewById(R.id.tv_performance_nombre_livraison);
            holder.tv_nombre_total = view.findViewById(R.id.tv_performance_nombre_total);
            holder.tv_quantite_total = view.findViewById(R.id.tv_performance_quantite_total);
            holder.tv_montant_total=view.findViewById(R.id.tv_performance_montant_total);


            //Affecter le holder à la vue

            view.setTag(holder);
        }else{
            //Recuperation du Holder dans la vue existante
            holder=(ViewHolder)view.getTag();
        }

            Agence ag = agenceViewModel.get(instance.getAgence_1_id(),false,false);

            //Remplir le Holder
            holder.tv_nom_agence.setText(ag.getDenomination());
            holder.tv_tel_agence.setText(ag.getTel());
            holder.tv_type_agence.setText(ag.getType());
            holder.tv_nombre_vente.setText( MesOutils.spacer(instance.getNombre_vente()));
            holder.tv_quantite_vendu.setText( MesOutils.spacer(instance.getQuantite_vente()));
            holder.tv_montant.setText(MesOutils.spacer(String.valueOf(instance.getMontant().intValue()))+" " + instance.getDevise_id());
            holder.tv_nombre_livraison.setText( MesOutils.spacer(instance.getNombre_livraison()));
            holder.tv_quantite_livre.setText(MesOutils.spacer(instance.getQuantite_livraison()));
            holder.tv_nombre_total.setText( MesOutils.spacer(String.valueOf(Integer.valueOf(instance.getNombre_vente())+Integer.valueOf(instance.getNombre_livraison()))));
            holder.tv_quantite_total.setText( MesOutils.spacer(String.valueOf(Integer.valueOf(instance.getQuantite_vente())+Integer.valueOf(instance.getQuantite_livraison()))));
            holder.tv_montant_total.setText(MesOutils.spacer(String.valueOf(instance.getMontant().intValue()))+" " + instance.getDevise_id());

        }
        catch (Exception e){

        }

        return view;
    }


    private class ViewHolder{
        TextView tv_nom_agence,tv_tel_agence,tv_type_agence,tv_nombre_vente,tv_montant,tv_quantite_vendu,tv_quantite_livre,tv_nombre_livraison,
        tv_nombre_total,tv_quantite_total,tv_montant_total;
        ImageView iv_agence;


    }

    public void update(Performance instance){
        this.instance=instance;
        notifyDataSetChanged();
    }


}
