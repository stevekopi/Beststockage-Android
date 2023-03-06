package cd.sklservices.com.Beststockage.Adapters.Registres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import layout.Registres.FournisseurView;

/**
 * Created by SKL on 25/04/2019.
 */

public class FournisseurAdapter extends BaseAdapter {

    private List<Fournisseur> lesFournisseurs;
    private LayoutInflater inflater;
    private Context context;

    public FournisseurAdapter(Context context, List<Fournisseur> lesFournisseurs){
        this.lesFournisseurs=lesFournisseurs;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }

    /**
     * Retourne Le nombre de lignes
     * @return
     */
    @Override
    public int getCount() {
        return lesFournisseurs.size();
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param position
     * @return
     */

    @Override
    public Object getItem(int position) {
        return lesFournisseurs.get(position);
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
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.cell_fournisseur, null);
                holder.tv_nom_fournisseur = (TextView) view.findViewById(R.id.tv_nom_fournisseur);
                holder.tv_fournisseur_tel = (TextView) view.findViewById(R.id.tv_fournisseur_tel);
                holder.tv_adding_date = (TextView) view.findViewById(R.id.tv_adding_date_user);
                holder.iv_Fournisseur = (ImageView) view.findViewById(R.id.iv_fournisseur);


                //Affecter le holder à la vue

                view.setTag(holder);
            } else {
                //Recuperation du Holder dans la vue existante
                holder = (ViewHolder) view.getTag();
            }

            //Remplir le Holder


           // String Noms = "" ;
           // if(lesFournisseurs.get(i).get().length() > 17)
            //{
            //    Noms = lesFournisseurs.get(i).getIdentity().getName().substring(0, 17).trim() ;
           // }
           // else
           // {
           //     Noms = lesFournisseurs.get(i).getId().trim() ;
           // }

            holder.tv_nom_fournisseur.setText(lesFournisseurs.get(i).getIdentity().getName());
            holder.tv_fournisseur_tel.setText(lesFournisseurs.get(i).getIdentity().getTelephone());
            holder.tv_adding_date.setText( MesOutils.Get_date_en_francais(lesFournisseurs.get(i).getAdding_date()));


            //    holder.tv_adding_date.setText("inseré depuis\n"+"1/1/1990");

            //Clic sur le reste de la ligne pour afficher le details

            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redirectionVersItemChoisit(v);
                }
            });

        }
        catch (Exception e) {
                //  Toast.makeText(context, e.toString() , Toast.LENGTH_LONG).show();
            }

        return view;
    }

    private void redirectionVersItemChoisit(View v){
        int position=(int)v.getTag();
        //Demande de l'affichage du details
        ((MainActivity)context).afficherDetailsFournisseur(lesFournisseurs.get(position));
    }

    private class ViewHolder{
        TextView tv_nom_fournisseur, tv_fournisseur_tel,tv_adding_date;
        ImageView iv_Fournisseur;


    }

    public void update(List<Fournisseur> result){
        lesFournisseurs=new ArrayList<>();
        lesFournisseurs.addAll(result);
        notifyDataSetChanged();
        FournisseurView.refresh_end();
    }


}
