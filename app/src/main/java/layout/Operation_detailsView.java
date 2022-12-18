package layout;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.room.Transaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Classes.Agence;
import cd.sklservices.com.Beststockage.Classes.Article;
import cd.sklservices.com.Beststockage.Classes.Operation;
import cd.sklservices.com.Beststockage.Classes.User;
import cd.sklservices.com.Beststockage.Cloud.SyncOperation;
import cd.sklservices.com.Beststockage.Cloud.SyncOperationFinance;
import cd.sklservices.com.Beststockage.Outils.Converter;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Repository.OperationFinanceRepository;
import cd.sklservices.com.Beststockage.Repository.OperationRepository;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.UserViewModel;

public class Operation_detailsView extends Fragment {

    private AgenceViewModel agenceViewModel ;
    private ArticleViewModel articleViewModel ;
    private OperationViewModel operationViewModel ;
    private UserViewModel userViewModel ;

    private Operation operation ;

    private FloatingActionButton btn_delete, btn_update_stock,btn_confirmation ;


    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;
    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    static ListView lvStock;


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Opération");

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
                rootView=inflater.inflate(R.layout.fragment_details_stock, container,false);
                LayoutInflater li=(LayoutInflater) (getContext()).getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

                btn_update_stock = (FloatingActionButton) rootView.findViewById(R.id.btn_operation_detail_update);
                btn_delete = (FloatingActionButton) rootView.findViewById(R.id.btn_operation_detail_delete);
                btn_confirmation = (FloatingActionButton) rootView.findViewById(R.id.btn_operation_detail_confirmation);




           // footer= li.inflate(R.layout.footer_view,null);

            btn_confirmation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    confirmation(operation);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    Toast.makeText(getContext(), "Action annulée", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Etes-vous sûr de vouloir confirmer cette operation? si vous acceptez, votre stock sera modifié").setPositiveButton("Oui", dialogClickListener)
                            .setNegativeButton("Non", dialogClickListener).show();
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   if(operation.getType().toLowerCase(Locale.ROOT).equals("sortie") && operation.getIs_confirmed()==1){
                       Toast.makeText(getContext(), "Impossible de supprimer une sortie déjà confirmée. Soit, contactez le superviseur", Toast.LENGTH_LONG).show();
                   }
                   else if(operation.getIs_checked()==1){
                       Toast.makeText(getContext(), "Impossible de supprimer une operation déjà controlée. Soit, contactez le superviseur", Toast.LENGTH_LONG).show();
                   }
                   else{
                       if(MainActivity.getCurrentUser().getId().equals(operation.getUser_id()))
                       {
                           deleteItem(operation);
                       }else{
                           User user=new UserViewModel(getActivity().getApplication()).get(operation.getUser_id(),true,true);
                           Toast.makeText(getActivity().getApplicationContext(), "Cet enregistrement ne peut être supprimé que par "+user.getHuman().getIdentity().getName(), Toast.LENGTH_SHORT).show();
                       }

                   }
                }
            });

            btn_update_stock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(operation.getType().toLowerCase(Locale.ROOT).equals("sortie") && operation.getIs_confirmed()==1){
                        Toast.makeText(getContext(), "Impossible de modifier une sortie déjà confirmée. Soit, contactez le superviseur", Toast.LENGTH_LONG).show();
                    }
                    else if(operation.getIs_checked()==1){
                        Toast.makeText(getContext(), "Impossible de modifier une operation déjà controlée. Soit, contactez le superviseur", Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(MainActivity.getCurrentUser().getId().equals(operation.getUser_id()))
                        {
                            ((MainActivity)getActivity()).updateOperation(operation);
                        }else{
                            User user=new UserViewModel(getActivity().getApplication()).get(operation.getUser_id(),true,true);
                            Toast.makeText(getActivity().getApplicationContext(), "Cet enregistrement ne peut être modifié que par "+user.getHuman().getIdentity().getName(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });



                footer= li.inflate(R.layout.footer_view,null);
                init();

            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
            }

        return rootView;
    }

    private void confirmation(Operation operation) {

        if(operationViewModel.update_confirmation_transact(operation)==1){
            Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            new SyncOperation(new OperationRepository(getContext())).sendPost();
            ((MainActivity) getActivity()).afficherOperationView();
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "Echec, l'opération secondaire n'est pas synchronisée, veuillez patienter 2 minutes en laissant la connexion internet ouverte", Toast.LENGTH_LONG).show();
        }
    }

    @Transaction
    private void deleteItem(Operation instance) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        if( operationViewModel.update_like_delete_transact(instance)==1)
                        {
                            new SyncOperation(new OperationRepository(getContext())).sendPost();
                            new SyncOperationFinance(new OperationFinanceRepository(getContext())).sendPost();
                            new SyncOperation(new OperationRepository(getContext())).sendPost();
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                            ((MainActivity)getContext()).afficherOperationView();
                        }
                        else{
                            Toast.makeText(getContext(), "Echec", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        Toast.makeText(getContext(), "Action annulée", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Etes-vous sûr de vouloir supprimer cette operation?").setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();



    }

    private void  init() {

        this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class) ;
        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;

        lvStock =(ListView)rootView.findViewById(R.id.lv_stock);
        fillData();
    }

    private void fillData()
    {
        try {
             operation = operationViewModel.getOperation() ;
            Agence agence = agenceViewModel.get(operation.getAgence_1_id(),false,false);
            Agence agence2 = agenceViewModel.get(operation.getAgence_2_id(),true,true);
            Article article = articleViewModel.get(operation.getArticle_id(),true,true);


            String addressAgence =agence2.getAddress().getNumber() +", Av. "+ agence2.getAddress().getStreet().getName()+
                    ", Q. "+agence2.getAddress().getQuarter().getName()+", "+agence2.getAddress().getTown().getName()+
                    "/"+agence2.getAddress().getTownship().getName();

            ArrayList<String> mylistDetail = new ArrayList<>()  ;
            mylistDetail.add(agence.getDenomination());
            mylistDetail.add("Type : "+ Converter.setTypeOperation(operation.getType()));
            mylistDetail.add("Agence : "+agence2.getDenomination());
            mylistDetail.add(addressAgence);
            mylistDetail.add("Réf : " +agence2.getAddress().getReference());
            mylistDetail.add("Propriétaire : "+agence2.getProprietaire().getName());
            mylistDetail.add("Tél. : "+agence2.getProprietaire().getTelephone());
            mylistDetail.add(article.getDescription());
            mylistDetail.add("Qte : "+ String.valueOf(operation.getQuantite()));

            if(operation.getBonus()>0)
                mylistDetail.add("Bns : "+String.valueOf(operation.getBonus()));

            if(operation.getMontant().intValue()>0){
                mylistDetail.add("Montant : "+String.valueOf(MesOutils.spacer(String.valueOf(operation.getMontant().intValue()))) + " " +
                        String.valueOf(operation.getDevise_id()));
            }
            mylistDetail.add("Date : "+MesOutils.Get_date_en_francais(operation.getDate().toUpperCase()));
            mylistDetail.add("Obs : "+String.valueOf(operation.getObservation()));
            mylistDetail.add("Ad : "+String.valueOf(operation.getAdding_date()));
            mylistDetail.add("Ud : "+String.valueOf(operation.getUpdated_date()));
            mylistDetail.add("sp : "+String.valueOf(operation.getSync_pos()));
            mylistDetail.add("pos : "+String.valueOf(operation.getPos()));
            mylistDetail.add("Id : "+String.valueOf(operation.getId()));
            if(operation.getIs_checked()==1){
                Agence check_agence=new AgenceViewModel(getActivity().getApplication()).get(operation.getChecking_agence_id(),false,false);
               mylistDetail.add("Agence qui a contrôlée : "+String.valueOf(check_agence.getType()+" "+check_agence.getDenomination()));
            }

            DetailAdapter adapter = new DetailAdapter(getContext(), mylistDetail);
            lvStock.setAdapter(adapter);

            if((operation.getType().equals("Entree") || operation.getType().equals("Livraison_Client")) && operation.getIs_confirmed()==0){
                btn_confirmation.setVisibility(View.VISIBLE);

            }else {
                btn_confirmation.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

    }


    public static void agence_refresh_end(){
    swipeRefreshLayout.setRefreshing(false);
}

    public class myHandler extends Handler{
    @Override
    public void handleMessage(Message msg) {
       switch (msg.what){
           case 0:
               //inserer le view de loading pendant la recherche
              // lvCommande.addFooterView(footer);
               //Update data adapter and UI
       }

    }
}

}
