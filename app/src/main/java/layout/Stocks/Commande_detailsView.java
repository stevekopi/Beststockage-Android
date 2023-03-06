package layout.Stocks;

import static cd.sklservices.com.Beststockage.ViewModel.TableKeyIncrementorViewModel.keygen;

import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.DetailAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Classes.Stocks.Commande;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.Classes.Stocks.Livraison;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.CommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneCommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LivraisonViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class Commande_detailsView extends Fragment {

    private AgenceViewModel agenceViewModel ;
    private ArticleViewModel articleViewModel ;
    private LigneCommandeViewModel ligneCommandeViewModel ;
    private CommandeViewModel commandeViewModel ;
    private UserViewModel userViewModel ;
    private FournisseurViewModel fournisseurViewModel ;
    private IdentityViewModel identityViewModel ;
    private LivraisonViewModel livraisonViewModel ;

    public Handler myHandler;
    public View footer;
    public boolean isLoading=false;
    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    SearchView sv_agences_filter;
    static ListView lvCommande, lvCommande2;
    List<LigneCommande> lignesC;

    String id_current_agence = "" ;
    String id_current_user = "" ;

    private FloatingActionButton btn_update, btn_delete, btn_synchronisation ;


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Commande");

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
                rootView=inflater.inflate(R.layout.fragment_details_commande, container,false);

                btn_update = (FloatingActionButton)rootView.findViewById(R.id.btn_update_commande) ;
                btn_delete = (FloatingActionButton)rootView.findViewById(R.id.btn_delete_commande) ;
                btn_synchronisation = (FloatingActionButton)rootView.findViewById(R.id.btn_indicator_commande) ;

                id_current_agence = MainActivity.getCurrentUser().getAgence_id().toString() ;
                id_current_user = MainActivity.getCurrentUser().getId().toString() ;

                LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                footer= li.inflate(R.layout.footer_view,null);

                init();

            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
            }

        return rootView;
    }

    private void  init() {

        this.commandeViewModel = new ViewModelProvider(this).get(CommandeViewModel.class) ;
        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;
        this.ligneCommandeViewModel = new ViewModelProvider(this).get(LigneCommandeViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        this.fournisseurViewModel = new ViewModelProvider(this).get(FournisseurViewModel.class) ;
        this.identityViewModel  = new ViewModelProvider(this).get(IdentityViewModel.class) ;
        this.livraisonViewModel  = new ViewModelProvider(this).get(LivraisonViewModel.class) ;

        lvCommande =(ListView)rootView.findViewById(R.id.lv_commande);
        lvCommande2 =(ListView)rootView.findViewById(R.id.lv_commande2);

        fillData();
    }

    private void fillData()
    {
        try {
            // lesAgences = agenceViewModel.getAgencesArrayListe();

           // Toast.makeText(getActivity(),  ligneCommandeViewModel.getLigneCommande().getId(), Toast.LENGTH_SHORT).show();

            final LigneCommande ligneCommande = ligneCommandeViewModel.getLigneCommande() ;
            final Commande commande = commandeViewModel.get(ligneCommandeViewModel.getLigneCommande().getCommande_id(),true) ;
            User user = userViewModel.get(commande.getAdding_user_id(),true,false) ;
            Fournisseur fournisseur = fournisseurViewModel.get(commande.getFournisseur_id(),true) ;
            Agence agence = agenceViewModel.get(ligneCommande.getAgence_id(),false,false) ;
            Article article = articleViewModel.get(ligneCommande.getArticle_id(),true,true) ;
            Identity identity = identityViewModel.get(commande.getProprietaire_id(),false) ;

            ArrayList<String> mylistDetail = new ArrayList<>()  ;
            ArrayList<String> mylistDetail2 = new ArrayList<>()  ;  ;

            if(!commande.getAdding_user_id().equals(id_current_user))
            {
                btn_delete.setVisibility(View.GONE);
                btn_update.setVisibility(View.GONE);
            }

            String id=commande.getId();
            int sqa = commandeViewModel.getSommeQuantiteApprovisionner(id);
            int sqc=  commandeViewModel.getSommeQuantiteCommander(id);
            int reste=sqc-sqa;

            Double va=Double.valueOf(sqa);
            Double vc=Double.valueOf(sqc);
            Double pourcentage=(va/vc)*100;
            int val=pourcentage.intValue();
            String valPourc=String.valueOf(pourcentage);


            if (reste == sqc) {
                btn_synchronisation.setBackgroundColor(Color.RED);
            } else if (reste == 0) {
                btn_synchronisation.setBackgroundColor(Color.GREEN);
            } else {
                btn_synchronisation.setBackgroundColor(Color.YELLOW);
            }


            mylistDetail.add(user.getUsername());
            mylistDetail.add(identity.getName().toString());
            mylistDetail.add( fournisseur.getId().toString());
            mylistDetail.add(commande.getDate());

            mylistDetail2.add("N° " +  commande.getId().toString());
            mylistDetail2.add(agence.getDenomination().toString());
            mylistDetail2.add( article.getDesignation().toString());
            mylistDetail2.add(String.valueOf(ligneCommande.getQuantite()));

            if (valPourc.length()>4){
                mylistDetail2.add( "Reste : " + String.valueOf(reste) + "  et " +
                        valPourc.substring(0,5)+"% Livré");
            }
            else if (valPourc.length()<4){
                mylistDetail2.add("Reste : " +  String.valueOf(reste) + " et " +
                        valPourc.substring(0,3)+"% Livré");
            }
            else
            {
                mylistDetail2.add("Reste : " +  String.valueOf(reste) + " et " +
                        " 0 % Livré");
            }

            mylistDetail2.add(ligneCommande.getMontant() + " " +
                    ligneCommande.getDevise_id());

            DetailAdapter adapter = new DetailAdapter(getContext(), mylistDetail);
            lvCommande.setAdapter(adapter);

            DetailAdapter adapter2 = new DetailAdapter(getContext(), mylistDetail2);
            lvCommande2.setAdapter(adapter2);

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).updateCommande(ligneCommande);
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String Today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                      //  String key_deleted = MesOutils.keygen(getActivity(), "", id_current_agence, id_current_user,
                        //        deletedViewModel.getList().size(), "deleted");

                        // Deleted deleted = new Deleted(id_current_agence, id_current_user, commande.getId(), "commande", Today);
                       // deleted.setId(key_deleted);

                       // deletedViewModel.ajout(deleted);

                       // commande.setSync_pos(3);
                       // commandeViewModel.update2(commande);

                        List<LigneCommande> mylist = ligneCommandeViewModel.ligne_commande_from_commande(commande.getId()) ;
                        for(LigneCommande ligne: mylist) {
                            List<Livraison> mylist2 = livraisonViewModel.getbyId_ligneCommande(ligne.getId());
                            if (mylist2.size() > 0) {
                                Toast.makeText(getActivity(), " La commande a déjà été livrée ... ", Toast.LENGTH_LONG).show();
                            } else {
                                String key_deleted = keygen(getActivity(), "","deleted");

                               /* Deleted deleted = new Deleted(id_current_agence, id_current_user, ligne.getId(), "ligne_commande", Today);
                                deleted.setId(key_deleted);

                                deletedViewModel.ajout(deleted);

                                */

                                ligne.setSync_pos(3);
                                ligneCommandeViewModel.update2(ligne);


                                Toast.makeText(getActivity(), "La commande a été supprimée !!!", Toast.LENGTH_LONG).show();

                                ((MainActivity) getActivity()).afficheCommandeView();
                            }
                        }
                    }
                    catch (Exception e) {
                        Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
                    }
                }
            });

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
               lvCommande.addFooterView(footer);
               //Update data adapter and UI
       }

    }
}

}
