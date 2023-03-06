package cd.sklservices.com.Beststockage.Adapters.Finances;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Classes.Finances.OperationFinance;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Repository.Parametres.DeviseRepository;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Finances.OperationFinanceViewModel;

/**
 * Created by SKL on 13/05/2019.
 */

public class Operation_finance_expandable_adapter extends BaseExpandableListAdapter {

    Context context;
    Application application;
    List<String> periode;
    Map<String,List<OperationFinance>> operationFinances;
    LinearLayout ll_operation;

    public Operation_finance_expandable_adapter(Context context,List<String> periode, Map<String, List<OperationFinance>> operationFinances) {
        try {
            this.context = context;
            this.periode = periode;
            this.operationFinances = operationFinances;
            this.application=((MainActivity)context).getApplication();
        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public Operation_finance_expandable_adapter(){

    }

    @Override
    public int getGroupCount() {
     if (periode!=null){
         return periode.size();
     }else{
         return 0;
     }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return operationFinances.get(periode.get(groupPosition)).size();
        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }

        return 0 ;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return periode.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return  operationFinances.get(periode.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        try {
            String periode = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.fragment_date,null);
            }

            TextView tvDate =convertView.findViewById(R.id.tv_date);
            TextView tvSp =convertView.findViewById(R.id.tv_sp);
            TextView tvCountChildren =convertView.findViewById(R.id.tv_date_count_children);

            tvDate.setText(MesOutils.Get_date_en_francais(periode.toUpperCase()));
            tvCountChildren.setText(getChildrenCount(groupPosition)+" lignes");

            Boolean test = new OperationFinanceViewModel(application).test_if_operationNotSync(periode.toUpperCase()) ;


            tvSp.setTextColor(test.equals(true)?Color.RED:Color.GREEN);

        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        try {
            final OperationFinance instance = (OperationFinance) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_operation_finance_enfant, null);
            }

            TextView tv_type = convertView.findViewById(R.id.tv_operation_finance_enfant_type);
            TextView tv_agence = convertView.findViewById(R.id.tv_operation_finance_enfant_agence);
            TextView tv_date =  convertView.findViewById(R.id.tv_operation_finance_enfant_date);
            TextView tv_fournisseur_caisse = convertView.findViewById(R.id.tv_operation_finance_enfant_fournisseur_caisse);
            TextView tv_observation =convertView.findViewById(R.id.tv_operation_finance_enfant_observation);
            TextView tv_montant =  convertView.findViewById(R.id.tv_operation_finance_enfant_montant);
            TextView tv_is_checked=convertView.findViewById(R.id.tv_operation_finance_is_checked);


            tv_is_checked.setTextColor(instance.getIs_caisse_checked()==1?Color.GREEN:Color.RED);
            LinearLayout ll_sync_pos=convertView.findViewById(R.id.ll_operation_finance_sync_pos);
            ll_sync_pos.setBackgroundColor(instance.getSync_pos()==1?Color.GREEN:Color.RED);
            tv_type.setText(instance.getType());
            tv_date.setText(instance.getDate().substring(0, 10));
            tv_observation.setText(instance.getObservation());

            instance.setDevise(new DeviseRepository(DeviseRepository.getContext()).get(instance.getDevise_id()));
            instance.setLocalDevise(new DeviseRepository(DeviseRepository.getContext()).get(instance.getLocal_devise_id()));
            instance.setConvertDevise(new DeviseRepository(DeviseRepository.getContext()).get(instance.getConvert_devise_id()));

            tv_montant.setText(MesOutils.spacer(String.valueOf(instance.getMontant().intValue())) +" "+ instance.getDevise().getCode());

            if (instance.getType().toLowerCase().contains("entree")) {
                tv_type.setTextColor(Color.argb(255, 53, 219, 45));
                tv_type.setText("Entrée");
            } else if (instance.getType().toLowerCase().contains("sortie")) {
                tv_type.setTextColor(Color.argb(255, 244, 53, 15));
            } else if (instance.getType().toLowerCase().contains("transfert")) {
                tv_type.setTextColor(Color.argb(255, 180, 21, 199));
            } else if (instance.getType().toLowerCase().contains("reception")) {
                tv_type.setTextColor(Color.argb(255, 255, 255, 0));
            }

            Fournisseur fournisseur=new FournisseurViewModel(application).get(instance.getFournisseur_id(),true);
            tv_fournisseur_caisse.setText("Caisse "+instance.getCategorie()+"-"+fournisseur.getIdentity().getName());

            Agence agence=new AgenceViewModel(application).get(instance.getAgence_id(),false,false);
            tv_agence.setText(agence.getType()+" "+agence.getDenomination());




            ll_operation=convertView.findViewById(R.id.ll_operation);
            ll_operation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Click = " + item.getType_operation(), Toast.LENGTH_LONG).show();

                  //  if(instance.getType().equals("Entree_speciale")  || instance.getType().equals("Sortie") || instance.getType().equals("Entree")
                   //         || instance.getType().equals("Vente_detail") || instance.getType().equals("Vente_avec_bonus") || instance.getType().equals("Livraison_Client"))
                   // {
                        //((MainActivity)context).afficherDetailStock(instance);
                  //  }
                  //  else
                  //  {
                  //      Toast.makeText(context, " Impossible de voir la suite - Opération sécondaire ...  ", Toast.LENGTH_SHORT).show();

                   // }

                }
            });



        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }


        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void addItem(Map<String,List<OperationFinance>> operationFinances){
        this.operationFinances.putAll(operationFinances);
        notifyDataSetChanged();
    }

    public void update(ArrayList<String> result){
        try {
            periode = new ArrayList<>();
            periode.addAll(result);
            notifyDataSetChanged();
        }
        catch (Exception ex)
        {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
