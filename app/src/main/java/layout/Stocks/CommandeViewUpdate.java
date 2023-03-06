package layout.Stocks;

import android.app.DatePickerDialog;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Stocks.Commande;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.CommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneCommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class CommandeViewUpdate extends Fragment  {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= CommandeViewUpdate.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private CommandeViewModel commandeViewModel;
    private LigneCommandeViewModel ligneCommandeViewModel;
    private AgenceViewModel agenceViewModel;
    private FournisseurViewModel fournisseurViewModel;
    private IdentityViewModel identityViewModel;
    private UserViewModel userViewModel;

    private Spinner spin_proprietaire, spin_fournisseur, spin_agence, spin_article ;
    private AutoCompleteTextView at_quantite ,at_date ;
    private Button btn_valider ;

    private User user ;
    private String spin_devise_text ;
    String[][] ag, af, ap, at ;

    private  Calendar calendar;
    private int jour, mois,day_of_month;
    private DatePickerDialog datePickerDialog;
    private String jours,moiss, date_commande ; //Conversion de day et month en string

    private LigneCommande ligneCommande ;
    private Commande commande  ;

    View rootView;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(COMMON_TAG,"Fragment HomeView SaveInstance");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG,FRAGMENT_NAME+ " onAttache");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,FRAGMENT_NAME+ " onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Commande");
        Log.i(TAG,FRAGMENT_NAME+ " onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,FRAGMENT_NAME+ " onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,FRAGMENT_NAME+ " onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,FRAGMENT_NAME+ " onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,FRAGMENT_NAME+ " onCreateView");
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_add_commande, container, false);

        user = MainActivity.getCurrentUser() ;

        spin_devise_text = "Fc" ;    date_commande = "" ;

        init(rootView) ;

        try {

            btn_valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                         // String Today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                         // String Today = "0000-00-00 00:00:00" ;
                        String id_current_user =  ((MainActivity)getActivity()).getCurrentUser().getId() ;
                        String id_current_agence =  ((MainActivity)getActivity()).getCurrentUser().getAgence_id() ;

                        if(date_commande == "")
                        {
                            Toast.makeText(getActivity(), " Selectionner la date pour cette commande  " , Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                            Boolean test2, test3, test4, test5 ;
                             test2 = false ;   test3 = false ;  test4 = false ;  test5 = false ;
                            Double prix = 0.0 ;

                            String Id_proprietaire, Id_fournisseur, Id_agence, Id_article ;
                            Id_proprietaire = "" ;  Id_fournisseur = "" ;  Id_agence = "" ;  Id_article = ""  ;

                            int compteur = identityViewModel.getAgencesArrayListe().size() ;
                            for (int i=0 ; i < compteur; i++)  {
                                if(spin_proprietaire.getSelectedItemId() == i) {
                                    Id_proprietaire = ap[i][1];
                                    test2 = true;
                                }
                            }

                            compteur =  articleViewModel.getArticlesArrayListe().size();
                            for (int i=0 ; i < compteur; i++)  {
                                if(spin_article.getSelectedItemId() == i) {
                                    Id_article = at[i][1] ;
                                    prix = articleViewModel.get(at[i][1],false,false).getPrix() * Integer.valueOf(at_quantite.getText().toString()) ;
                                    test3 = true;
                                }
                            }

                            compteur = fournisseurViewModel.getFournisseurArrayListe().size() ;
                            for (int i=0 ; i < compteur; i++)  {
                                if(spin_fournisseur.getSelectedItemId() == i) {
                                    Id_fournisseur = af[i][1];
                                    test4 = true;
                                }
                            }


                            compteur = agenceViewModel.getAgencesArrayListe().size() ;
                            for (int i=0 ; i < compteur; i++)  {
                                if(spin_agence.getSelectedItemId() == i) {
                                    Id_agence = ag[i][1];
                                    test5 = true;
                                }
                            }

                            if(test2.equals(true) && test3.equals(true) && test4.equals(true)
                                    && test5.equals(true)) {
                                String Today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                Commande cmd = new
                                Commande(commande.getId(),"",Id_proprietaire, Id_fournisseur, date_commande, "Privee",id_current_user,
                                id_current_agence,commande.getAdding_date(),Today,0,commande.getPos()+1);

                                commandeViewModel.ajout_async(cmd);
                                LigneCommande ligne=new LigneCommande(ligneCommande.getId(),commande.getId(),Id_agence,Id_article,
                                        Integer.valueOf(at_quantite.getText().toString()),0,prix,"Fc",id_current_user,id_current_agence,
                                        ligneCommande.getAdding_date(),ligneCommande.getUpdated_date(),0,0);

                                ligneCommandeViewModel.update2(ligne);

                                 Toast.makeText(getActivity(), "Commande mise à jour ", Toast.LENGTH_SHORT).show();
                                ((MainActivity) getActivity()).afficheCommandeView();
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "La commande n'a pas été enregistré", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(), " Prière de saisir tous les champs obligatoire " + ex.toString(), Toast.LENGTH_LONG).show();
                }

                }
            });

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    private void init(View v)
    {

        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class) ;
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class) ;
        this.commandeViewModel = new ViewModelProvider(this).get(CommandeViewModel.class) ;
        this.ligneCommandeViewModel = new ViewModelProvider(this).get(LigneCommandeViewModel.class) ;
        this.fournisseurViewModel = new ViewModelProvider(this).get(FournisseurViewModel.class) ;
        this.identityViewModel = new ViewModelProvider(this).get(IdentityViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;



        ligneCommande = ligneCommandeViewModel.getLigneCommande() ;
        commande = commandeViewModel.get(ligneCommandeViewModel.getLigneCommande().getCommande_id(),true) ;

        Fournisseur fournisseur = fournisseurViewModel.get(commande.getFournisseur_id(),true) ;
        Agence agence = agenceViewModel.get(ligneCommande.getAgence_id(),true,false) ;
        Article article = articleViewModel.get(ligneCommande.getArticle_id(),true,true) ;
        Identity identity = identityViewModel.get(commande.getProprietaire_id(),false) ;


        at_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                jour = calendar.get(Calendar.YEAR);
                mois = calendar.get(Calendar.MONTH);
                day_of_month = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        if (day < 10) {
                            jours = "0" + String.valueOf(day);
                        } else {
                            jours = String.valueOf(day);
                        }

                        if ((month + 1) < 10) {
                            moiss = "0" + String.valueOf(month + 1);
                        } else {
                            moiss = String.valueOf(month + 1);
                        }
                        date_commande = year + "-" + (moiss) + "-" + jours;
                        at_date.setText(jours + "/" + (moiss) + "/" + year);

                    }
                }, jour, mois, day_of_month);
                datePickerDialog.show();

            }
        });


        date_commande = commande.getDate() ;
        at_date.setText(date_commande);
        at_quantite.setText(String.valueOf (ligneCommande.getQuantite())) ;

        charging_spinning_agence(spin_agence, agence) ;
        charging_spinning_fournisseur(spin_fournisseur, fournisseur);
        charging_spinning_proprietaire(spin_proprietaire, identity) ;
        charging_spinning_article(spin_article, article) ;
    }

    private void charging_spinning_agence(Spinner spin, Agence agence1)
    {
        try {
            List<Agence> agences = agenceViewModel.getAgencesArrayListe() ;
            ag = new String[agences.size()][3];
            String[] ag2 = new String[agences.size()];  int compteur = 1 ;

            for (Agence agence : agences)
            {
                if(agence.getId().equals(agence1.getId()))
                {
                    ag[0][0] =  agence.getDenomination() ;
                    ag2[0] =  agence.getDenomination() ;
                    ag[0][1] =  agence.getId() ;
                }
                else {
                    ag[compteur][0] =  agence.getDenomination() ;
                    ag2[compteur] =  agence.getDenomination() ;
                    ag[compteur][1] =  agence.getId() ;    compteur++ ;
                }

            }

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag2 );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
             Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_fournisseur(Spinner spin, Fournisseur fournisseur1)
    {
        try {
            List<Fournisseur> fournisseurs = fournisseurViewModel.getFournisseurArrayListe() ;
            af = new String[fournisseurs.size()][3];
            String[] af2 = new String[fournisseurs.size()];  int compteur = 1 ;

            for (Fournisseur f : fournisseurs)
            {
                if(f.getId().equals(fournisseur1.getId()))
                {
                    af[0][0] =  f.getId().toString() ;
                    af2[0] =  f.getId().toString() ;
                    af[0][1] =  f.getId() ;
                }
                else {
                    af[compteur][0] =  f.getId().toString() ;
                    af2[compteur] =  f.getId().toString() ;
                    af[compteur][1] =  f.getId() ;
                    compteur++;
                }
            }

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, af2 );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
             Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_proprietaire(Spinner spin, Identity identity1)
    {
        try {
            List<Identity> identity = identityViewModel.getAgencesArrayListe() ;
            ap = new String[identity.size()][3];
            String[] ap2 = new String[identity.size()];  int compteur = 1 ;

            for (Identity id : identity)
            {
                if(id.getId().equals(identity1.getId()))
                {
                    ap[0][0] =  id.getName().toString();
                    ap2[0] =  id.getName().toString(); ;
                    ap[0][1] =  id.getId() ;
                }
                else {
                    ap[compteur][0] =  id.getName().toString();
                    ap2[compteur] =  id.getName().toString(); ;
                    ap[compteur][1] =  id.getId() ;
                    compteur++;
                }
            }

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ap2 );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
             Toast.makeText(getActivity(),  ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void charging_spinning_destinataire(Spinner spin, User user2)
    {
        try {
            List<User> user = userViewModel.getUserArrayListe() ;
            String[] ag = new String[user.size()];  int compteur = 1 ;

            for (User user1 : user)
            {
                if(user2.getId().equals(user1.getId()))
                {
                    ag[0] = user1.getUsername() ;
                }
                else
                {
                    ag[compteur] = user1.getUsername() ; compteur++ ;
                }

            }

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
              Toast.makeText(getActivity(), " 111 " +  ex.toString(), Toast.LENGTH_LONG).show();
        }
    }



    private void charging_spinning_article(Spinner spin, Article article1)
    {
        try {
            List<Article> articles = articleViewModel.getArticlesArrayListe() ;
            at = new String[articles.size()][3];
            String[] at2 = new String[articles.size()];  int compteur = 1 ;

            for (Article article : articles)
            {
                if(article.getId().equals(article1.getId()))
                {
                    at[0][0] =  article.getDesignation().toString() ;
                    at2[0] =  article.getDesignation().toString() ;
                    at[0][1] =  article.getId() ;
                }
                else
                {
                    at[compteur][0] =  article.getDesignation().toString() ;
                    at2[compteur] =  article.getDesignation().toString() ;
                    at[compteur][1] =  article.getId() ;
                    compteur++ ;
                }
            }

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, at2 );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), " 111 " +  ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

}


