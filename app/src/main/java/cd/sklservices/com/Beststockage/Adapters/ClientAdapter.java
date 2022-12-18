package cd.sklservices.com.Beststockage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Identity;
import cd.sklservices.com.Beststockage.R;

/**
 * Created by SKL on 25/04/2019.
 */

public class ClientAdapter extends BaseAdapter {

    Context context;
    List<Identity> client;
    private static LayoutInflater inflater;

    public ClientAdapter(Context context, List<Identity> client) {
        this.context = context;
        this.client = client;

    }

    public ClientAdapter(){

    }

    public void addItem(List<Identity> result){
        client.addAll(result);
        notifyDataSetChanged();
    }

    public void update(ArrayList<Identity> result){
        client=new ArrayList<>();
        client.addAll(result);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return client.size();
    }

    @Override
    public Object getItem(int i) {
        return client.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Identity identity=(Identity) getItem(position);
        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.cell_client,null);
        }

        TextView tv_nom = convertView.findViewById(R.id.tv_nom_client);
        TextView tv_tel = convertView.findViewById(R.id.tv_tel_client);
        TextView tv_type = convertView.findViewById(R.id.tv_type_client);
        TextView tv_addingdate = convertView.findViewById(R.id.tv_addingdate_client);


        tv_nom.setText(identity.getName().toUpperCase());
        tv_type.setText(identity.getType().toUpperCase());
        tv_tel.setText(identity.getTelephone().toString());
        tv_addingdate.setText("*");

        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Demande de l'affichage du details
                    ((MainActivity) context).afficherDetailsClient(identity);
                }
                catch (Exception e) {
                    Toast.makeText(context, e.toString() , Toast.LENGTH_LONG).show();
                }
            }
        });

        return convertView;
    }
}
