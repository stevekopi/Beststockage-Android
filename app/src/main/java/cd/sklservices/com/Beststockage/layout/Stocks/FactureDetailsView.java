package cd.sklservices.com.Beststockage.layout.Stocks;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Stocks.DetailsFactureAdapter;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Client;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.FactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ClientViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class FactureDetailsView extends Fragment {

    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;
    View rootView;
    static ListView lvDetailsFacture, lvCommande2;
    List<LigneCommande> lignesC;

    String id_current_agence = "" ;
    String id_current_user = "" ;


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Détails Facture");

        Log.d("Chrono","Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Chrono","onPause");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Chrono","onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Chrono","onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Chrono","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Chrono","onDestroy");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Chrono","onStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
                // Inflate the layout for this fragment
                rootView=inflater.inflate(R.layout.fragment_details_facture, container,false);
                id_current_agence = MainActivity.getCurrentUser().getAgence_id() ;
                id_current_user = MainActivity.getCurrentUser().getId() ;

                LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                footer= li.inflate(R.layout.footer_view,null);

            fillData();

            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
            }

        return rootView;
    }


    private void fillData()
    {
        try {
            Facture instance=new FactureViewModel(MainActivity.application).getFacture();
            List<LigneFacture> lignes=new LigneFactureViewModel(MainActivity.application).getListByFactureId(instance.getId());
            Devise devise=new DeviseViewModel(MainActivity.application).get(instance.getDevise_id());

            instance.setLines(lignes);
            lvDetailsFacture =rootView.findViewById(R.id.lv_details_facture);
            TextView tv_id=rootView.findViewById(R.id.tv_details_facture_id);
            TextView tv_date=rootView.findViewById(R.id.tv_details_facture_date);
            TextView tv_client_name=rootView.findViewById(R.id.tv_details_facture_client_name);
            TextView tv_agence_denomination=rootView.findViewById(R.id.tv_details_facture_agence_denomination);
            TextView tv_devise_code=rootView.findViewById(R.id.tv_details_facture_devise_code);
            TextView tv_produit_designation=rootView.findViewById(R.id.tv_details_facture_produit_designation);
            TextView tv_redduction=rootView.findViewById(R.id.tv_details_facture_reduction);
            TextView tv_user=rootView.findViewById(R.id.tv_details_facture_user);

            TextView tv_total_ht=rootView.findViewById(R.id.tv_details_facture_montant_total_ht);
            TextView tv_total_tva=rootView.findViewById(R.id.tv_details_facture_montant_total_tva);
            TextView tv_total_ttc=rootView.findViewById(R.id.tv_details_facture_montant_total_ttc);
            TextView tv_total_net=rootView.findViewById(R.id.tv_details_facture_montant_total_net);

            tv_total_ht.setText( MesOutils.spacer(String.valueOf(Math.round(instance.getMontant_ht())))+" "+devise.getCode());
            tv_total_tva.setText(MesOutils.spacer(String.valueOf(Math.round(instance.getTva())))+" "+devise.getCode());
            tv_total_ttc.setText( MesOutils.spacer(String.valueOf(Math.round(instance.getMontant_ttc())))+" "+devise.getCode());
            tv_total_net.setText( MesOutils.spacer(String.valueOf(Math.round(instance.getMontant_net())))+" "+devise.getCode());

            tv_id.setText(instance.getId());
            tv_date.setText(MesOutils.Get_date_en_francais(instance.getDate()));

            User user=new UserViewModel(MainActivity.application).get(instance.getUser_id(),true,true);
            tv_user.setText(user.getHuman().getIdentity().getName());

            Client client=new ClientViewModel(MainActivity.application).get(instance.getProprietaire_id(),true);
            tv_client_name.setText(client.getHuman().getIdentity().getName());

            Agence agence=new AgenceViewModel(MainActivity.application).get(instance.getAgence_id(),true,false);
            tv_agence_denomination.setText(agence.getType()+" "+agence.getDenomination());


            tv_devise_code.setText(devise.getDesignation()+" ("+devise.getCode()+")");

            ProduitFacture produitFacture=new ProduitFactureViewModel(MainActivity.application).get(instance.getProduit_id());
            tv_produit_designation.setText(produitFacture.getDesignation());

            tv_redduction.setText(instance.getReduction_rate() + "%");



            DetailsFactureAdapter adapter = new DetailsFactureAdapter(getContext(), lignes);
            lvDetailsFacture.setAdapter(adapter);




        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

    }



public class myHandler extends Handler{
    @Override
    public void handleMessage(Message msg) {
       switch (msg.what){
           case 0:
               //inserer le view de loading pendant la recherche
               lvDetailsFacture.addFooterView(footer);
               //Update data adapter and UI
       }

    }
}

}
