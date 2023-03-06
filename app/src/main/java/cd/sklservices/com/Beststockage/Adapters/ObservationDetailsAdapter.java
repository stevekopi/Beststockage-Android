package cd.sklservices.com.Beststockage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cd.sklservices.com.Beststockage.R;

import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.OperationViewModel;

/**
 * Created by SKL on 25/04/2019.
 */

public class ObservationDetailsAdapter extends BaseAdapter {

    private OperationViewModel operationViewModel ;
    private AgenceViewModel agenceViewModel ;

    //**** private List<Observation> lesObservations;
    private LayoutInflater inflater;
    private Context context;


    public ObservationDetailsAdapter(Context context, OperationViewModel op_viewmodel, AgenceViewModel ag_viewmodel
                                    //*** List<Observation> lesObservations
    ){
      //***  this.lesObservations =lesObservations;
        this.context=context;
        this.inflater=LayoutInflater.from(context);

        this.operationViewModel = op_viewmodel ;
        this.agenceViewModel = ag_viewmodel ;
    }

   //** public void update(ArrayList<Observation> result){
    //**    lesObservations =new ArrayList<>();
    //**    lesObservations.addAll(result);
  //** notifyDataSetChanged();
   //** }

    /**
     * Retourne Le nombre de lignes
     * @return
     */
    @Override
    public int getCount() { return  0 ;
//**    return lesObservations.size();
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param position
     * @return
     */

    @Override
    public Object getItem(int position) {
        return null ;
      //**   return lesObservations.get(position);
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
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.cell_observation_details, null);
            holder.tv_observation_details_adapter_montant = (TextView) view.findViewById(R.id.tv_observation_details_adapter_montant);
            holder.tv_observation_details_adapter_remarque = (TextView) view.findViewById(R.id.tv_observation_details_adapter_remarque);
            //Affecter le holder à la vue

            view.setTag(holder);
        } else {
            //Recuperation du Holder dans la vue existante
            holder = (ViewHolder) view.getTag();
        }

        //Remplir le Holder
        //** holder.tv_observation_details_adapter_montant.setText(String.valueOf(lesObservations.get(i).getMontant()));
        //** holder.tv_observation_details_adapter_remarque.setText(lesObservations.get(i).getRemarque());

        //** Operation operation = operationViewModel.get(lesObservations.get(i).getOperation_id());

        //**  Agence ag1 = agenceViewModel.get(operation.getAgence_1_id()) ;
        //**  Agence ag2 = agenceViewModel.get(operation.getAgence_2_id()) ;

        //** ((ObservationDetailsView) context).setData(ag1.getType() +" : "+ ag1.getDenomination(),
        // Date.valueOf(operation.getDate_operation())) ;


        return view;
    }


    private class ViewHolder{
        TextView tv_observation_details_adapter_montant,tv_observation_details_adapter_remarque;


    }


}
