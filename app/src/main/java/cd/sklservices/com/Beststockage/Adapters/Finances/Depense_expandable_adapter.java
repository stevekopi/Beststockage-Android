package cd.sklservices.com.Beststockage.Adapters.Finances;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Finances.Depense;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Cloud.Finances.SyncDepense;
import cd.sklservices.com.Beststockage.Cloud.Finances.SyncOperationFinance;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Repository.Finances.DepenseRepository;
import cd.sklservices.com.Beststockage.Repository.Finances.OperationFinanceRepository;
import cd.sklservices.com.Beststockage.Repository.Parametres.DeviseRepository;
import cd.sklservices.com.Beststockage.ViewModel.Finances.DepenseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

/**
 * Created by SKL on 13/05/2019.
 */

public class Depense_expandable_adapter extends BaseExpandableListAdapter {

    private DepenseViewModel depenseViewModel ;

    FragmentActivity activity ;
    Context context ;
    List<String> periode;
    Map<String,List<Depense>> depenses;

    public Depense_expandable_adapter(FragmentActivity activity, Context context, DepenseViewModel depenseViewModel,
                                      List<String> periode, Map<String, List<Depense>> depense) {
        try {
            this.context = context;
            this.periode = periode;
            this.depenses = depense;

            this.activity = activity ;
            this.depenseViewModel = depenseViewModel ;
        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public Depense_expandable_adapter(){

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
            return depenses.get(periode.get(groupPosition)).size();
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
        return  depenses.get(periode.get(groupPosition)).get(childPosition);
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

            TextView tvDate =(TextView) convertView.findViewById(R.id.tv_date);
            TextView tvSp =(TextView) convertView.findViewById(R.id.tv_sp);

            TextView tvCountChildren =(TextView) convertView.findViewById(R.id.tv_date_count_children);

            tvDate.setText(MesOutils.Get_date_en_francais(periode.toUpperCase()));
            tvCountChildren.setText(getChildrenCount(groupPosition)+" lignes");


            Boolean testSp = depenseViewModel.test_if_depenseNotSync(periode.toUpperCase()) ;

            tvSp.setTextColor(testSp.equals(true)?Color.RED:Color.GREEN);

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
            Depense instance = new DepenseViewModel(activity.getApplication()).get(((Depense) getChild(groupPosition, childPosition)).getId(),true);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_depense_enfant, null);
            }
            TextView tv_date = convertView.findViewById(R.id.tv_depense_enfant_date);
            TextView tv_fournisseur_caisse =  convertView.findViewById(R.id.tv_depense_enfant_fournisseur_caisse);
            TextView tv_montant =  convertView.findViewById(R.id.tv_depense_enfant_montant);
            TextView tv_observation =  convertView.findViewById(R.id.tv_depense_enfant_observation);
            LinearLayout ll_sync_pos=convertView.findViewById(R.id.ll_depense_sync_pos);
            LinearLayout ll_depense,ll_depense_action;
            tv_date.setText(instance.getDate());
            tv_fournisseur_caisse.setText("Caisse "+instance.getOperationFinance().getCategorie()+" "+instance.getOperationFinance().getFournisseur().getIdentity().getName());

            instance.setDevise(new DeviseRepository(DeviseRepository.getContext()).get(instance.getDevise_id()));
            instance.setLocalDevise(new DeviseRepository(DeviseRepository.getContext()).get(instance.getLocal_devise_id()));
            instance.setConvertDevise(new DeviseRepository(DeviseRepository.getContext()).get(instance.getConvert_devise_id()));

            tv_montant.setText(MesOutils.spacer(String.valueOf(instance.getMontant().intValue())) +" "+ instance.getDevise().getCode());

            tv_observation.setText(instance.getObservation());
            ll_sync_pos.setBackgroundColor(instance.getSync_pos()==1?Color.GREEN:Color.RED);
            ImageButton btn_delete=convertView.findViewById(R.id.btn_depense_delete);
            ImageButton btn_update=convertView.findViewById(R.id.btn_depense_update);


            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  if(MainActivity.getCurrentUser().getId().equals(instance.getAdding_user_id())){
                      deleteItem(instance);
                  }
                  else{
                      User user=new UserViewModel(activity.getApplication()).get(instance.getAdding_user_id(),true,true);
                      Toast.makeText(activity.getApplicationContext(), "Cet enregistrement ne peut être supprimé que par "+user.getHuman().getIdentity().getName(), Toast.LENGTH_SHORT).show();
                  }
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(MainActivity.getCurrentUser().getId().equals(instance.getAdding_user_id())){
                        ((MainActivity)activity).updateDepense(instance);
                    }
                    else{
                        User user=new UserViewModel(activity.getApplication()).get(instance.getAdding_user_id(),true,true);
                        Toast.makeText(activity.getApplicationContext(), "Cet enregistrement ne peut être modifié que par "+user.getHuman().getIdentity().getName(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            ll_depense=convertView.findViewById(R.id.ll_depense);
            ll_depense_action=convertView.findViewById(R.id.ll_depense_action);
            ll_depense.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) ll_depense_action.getLayoutParams();
                    if(params.width==0){
                        while (params.width<200)
                            params.width=params.width+1;
                    }
                    else {
                        while (params.width>0)
                            params.width=params.width-1;
                    }
                    ll_depense_action.setLayoutParams(params);
                    return true;
                }
            });


        }
        catch (Exception ex)
        {
            Toast.makeText(context,  ex.toString(), Toast.LENGTH_LONG).show();
        }


        return convertView;
    }
    @Transaction
    private void deleteItem(Depense instance) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        if( depenseViewModel.update_like_delete_transact(instance)==1)
                        {
                            Toast.makeText(activity.getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            new SyncDepense(new DepenseRepository(context)).sendPost();
                            new SyncOperationFinance(new OperationFinanceRepository(context)).sendPost();
                            new SyncDepense(new DepenseRepository(context)).sendPost();
                            ((MainActivity)activity).afficherDepense();
                        }
                        else{
                            Toast.makeText(activity.getApplicationContext(), "Echec", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        Toast.makeText(context, "Action annulée", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Etes-vous sûr de vouloir supprimer cette depense?").setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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
/*
    protected final Dialog onCreateDialog(final int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ID:
                AlertDialog.Builder builder = new AlertDialog.Builder(activity.getBaseContext());
                builder.setMessage(
                        "some message")
                        .setCancelable(false)
                        .setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        //to perform on ok


                                    }
                                })
                        .setNegativeButton("cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        //dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                dialog = alert;
                break;

            default:

        }
        return dialog;
    }

 */
}
