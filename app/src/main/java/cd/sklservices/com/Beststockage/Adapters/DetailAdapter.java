package cd.sklservices.com.Beststockage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.layout.Registres.AgenceView;

/**
 * Created by SKL on 25/04/2019.
 */

public class DetailAdapter extends BaseAdapter {

    private ArrayList<String> mylist;
    private LayoutInflater inflater;
    private Context context;


    public DetailAdapter(Context context, ArrayList<String> listDetail){
        this.mylist = listDetail;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
       /// this.mList= listDetail;
    }

    public void addListItemToAdapter(ArrayList<String> list){
       /// mList.addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * Retourne Le nombre de lignes
     * @return
     */
    @Override
    public int getCount() {
        return mylist.size();
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param position
     * @return
     */

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
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

        //Si la ligne n'existe pas encore
        if (view==null){
            holder=new ViewHolder();
            view=inflater.inflate(R.layout.cell_detail,null);
            holder.tv_titre =(TextView)view.findViewById(R.id.tv_titre_detail);


            //Affecter le holder à la vue

            view.setTag(holder);
        }else{
            //Recuperation du Holder dans la vue existante
            holder=(ViewHolder)view.getTag();
        }

        //Remplir le Holder
        holder.tv_titre .setText(mylist.get(i).toString());

        //Clic sur le reste de la ligne pour afficher le details des users

        return view;
    }

    private class ViewHolder{
        TextView tv_titre ,tv_categorie ,tv_description ;


    }

    public void update(ArrayList<String> result){
        mylist=new ArrayList<>();
        mylist.addAll(result);
        notifyDataSetChanged();
        AgenceView.agence_refresh_end();
    }


}
