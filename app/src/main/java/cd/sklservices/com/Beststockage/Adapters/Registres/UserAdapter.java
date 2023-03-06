package cd.sklservices.com.Beststockage.Adapters.Registres;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import layout.Registres.UserView;

/**
 * Created by SKL on 25/04/2019.
 */

public class UserAdapter extends BaseAdapter {

    private AgenceViewModel agenceViewModel ;

    private List<User> lesUsers;
    private LayoutInflater inflater;
    private Context context;

    public UserAdapter(Context context, AgenceViewModel agenceViewModel1, List<User> lesUsers){
        this.lesUsers=lesUsers;
        this.context=context;
        this.inflater=LayoutInflater.from(context);

        this.agenceViewModel = agenceViewModel1 ;
    }

    public void update(List<User> result){
        try {
            lesUsers = new ArrayList<>();
            lesUsers.addAll(result);
            notifyDataSetChanged();
            UserView.refresh_end();
        }
        catch (Exception ex)
        {
            //Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public UserAdapter(){

    }



    private void redirectionVersItemChoisit(View v){
        int position=(int)v.getTag();
        //Demande de l'affichage du details
        ((MainActivity)context).afficherDetailsUser(lesUsers.get(position));
    }

    @Override
    public int getCount() {
        return lesUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return lesUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        try
        {
            //Si la ligne n'existe pas encore
            if (view==null){
                LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                view=inflater.inflate(R.layout.cell_user,null);
            }

            TextView tv_nom_user=(TextView)view.findViewById(R.id.tv_nom_user);
            TextView tv_tel_user=(TextView)view.findViewById(R.id.tv_tel_user);
            TextView  tv_adding_date=(TextView)view.findViewById(R.id.tv_addingdate_user);
            // holder.ivUser=(ImageView)view.findViewById(R.id.ivUser);
            TextView tv_agence_nom=(TextView)view.findViewById(R.id.tv_user_agence_designation);


            //Affecter le holder à la vue


            User instance=lesUsers.get(i);
            Agence agence = agenceViewModel.get(instance.getAgence_id(),true,false) ;
            //Remplir le Holder
            String Noms_user = "" ;
            if(instance.getHuman().getIdentity().getName().length() > 17)
            {
                Noms_user = instance.getHuman().getIdentity().getName().substring(0, 17).trim().toUpperCase(Locale.ROOT) ;
            }
            else
            {
                Noms_user = instance.getHuman().getIdentity().getName().trim().toUpperCase(Locale.ROOT) ;
            }

            tv_nom_user.setText(Noms_user);
            tv_tel_user.setText( lesUsers.get(i).getHuman().getIdentity().getTelephone());
            tv_agence_nom.setText( "Agence: "+ agence.getDenomination());
            tv_adding_date.setText(" * ");

            if(lesUsers.get(i).getStatut().equals("Operationnel"))
            {
                tv_adding_date.setTextColor(Color.BLUE);
            }
            else
            {
                tv_adding_date.setTextColor(Color.RED);
            }

            //    holder.tv_adding_date.setText("inseré depuis\n"+"1/1/1990");

            //Clic sur le reste de la ligne pour afficher le details des users

            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redirectionVersItemChoisit(v);
                }
            });

        } catch (Exception e) {

        }

        return view;

    }

    private class ViewHolder{
        TextView tv_nom_user,tv_tel_user,tv_agence_nom,tv_adding_date;
        ImageView ivUser;


    }


}
