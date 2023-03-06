package layout.Stocks;

import static cd.sklservices.com.Beststockage.ViewModel.TableKeyIncrementorViewModel.keygen;

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
import android.widget.EditText;
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
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Classes.Stocks.Commande;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.CommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneCommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class CommandeViewAdd extends Fragment  {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= CommandeViewAdd.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private CommandeViewModel commandeViewModel;
    private LigneCommandeViewModel ligneCommandeViewModel;
    private AgenceViewModel agenceViewModel;
    private FournisseurViewModel fournisseurViewModel;
    private IdentityViewModel identityViewModel;
    private UserViewModel userViewModel;

    private Spinner spin_proprietaire, spin_fournisseur, spin_agence, spin_article ;
    private AutoCompleteTextView at_quantite ;
    private EditText et_bonus;
    private Button btn_valider ;

    private User user ;
    private String spin_devise_text ;

    private  Calendar calendar;
    private int jour, mois,day_of_month;
    private DatePickerDialog datePickerDialog;
    private String jours,moiss, date_commande ; //Conversion de day et month en string
    private String Appartenance_save, Proprietaire_save  ;

    private Boolean btn_insert_commande = false ;

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

        spin_devise_text = "Fc" ;    date_commande = "" ;     Appartenance_save = "" ;  Proprietaire_save  = "";

        init(rootView) ;

        try {

            btn_valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        String Today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                         // String Today = "0000-00-00 00:00:00" ;
                        String id_current_user =  ((MainActivity)getActivity()).getCurrentUser().getId() ;
                        String id_current_agence =  ((MainActivity)getActivity()).getCurrentUser().getAgence_id() ;

                        if(date_commande == "")
                        {
                            Toast.makeText(getActivity(), " Selectionner la date pour cette commande  " , Toast.LENGTH_LONG).show();
                        }
                        else if(btn_insert_commande.equals(false))
                        {

                            Boolean  test2, test3, test4, test5 ;
                            test2 = false ;   test3 = false ;  test4 = false ;  test5 = false ;
                            Double prix = 0.0 ;

                            String key_commande = keygen(getActivity(),"","commande");

                            String key_ligne_commande = keygen(getActivity(), "","ligne_commande");


                            String  Id_proprietaire, Id_fournisseur, Id_agence, Id_article ;
                            Id_proprietaire = "" ;  Id_fournisseur = "" ;  Id_agence = "" ;  Id_article = ""  ;

                            btn_insert_commande = true ;

                            int compteur = 0 ;
                            List<Identity> identity = identityViewModel.getAgencesArrayListe() ;
                            for (Identity identity1:identity)
                            {
                                if(spin_proprietaire.getSelectedItemId() == compteur) {
                                    Id_proprietaire = identity1.getId();
                                    test2 = true;
                                }
                                compteur++ ;
                            }

                            compteur = 0 ;
                            List<Article> article = articleViewModel.getArticlesArrayListe() ;
                            for (Article article1 : article)
                            {
                                if(spin_article.getSelectedItemId() == compteur) {
                                    Id_article = article1.getId();
                                    prix = article1.getPrix() * Integer.valueOf(at_quantite.getText().toString()) ;
                                    test3 = true;
                                }
                                compteur++ ;
                            }

                            compteur = 0 ;
                            List<Fournisseur> fournisseurs = fournisseurViewModel.getFournisseurArrayListe() ;
                            for (Fournisseur fournisseur1 : fournisseurs)
                            {
                                if(spin_fournisseur.getSelectedItemId() == compteur) {
                                    Id_fournisseur = fournisseur1.getId();
                                    test4 = true;
                                }
                                compteur++ ;
                            }

                            compteur = 0 ;
                            List<Agence> agences = agenceViewModel.getByAppartenanceProprietaire(Appartenance_save, Proprietaire_save) ;
                            for (Agence agence1 : agences)
                            {
                                if(spin_agence.getSelectedItemId() == compteur) {
                                    Id_agence = agence1.getId();
                                    test5 = true;
                                }
                                compteur++ ;
                            }

                            if(test2.equals(true) && test3.equals(true) && test4.equals(true)
                                    && test5.equals(true)) {

                                String id_commande = "" ;
                                List<Commande> mylist = commandeViewModel.getWhereAll(id_current_user, Id_proprietaire, Id_fournisseur,
                                        date_commande, "Prive", id_current_agence) ;
                                for(Commande c: mylist)
                                {
                                    id_commande = c.getId() ;
                                }

                                if(id_commande.equals("")) {
                                    Commande commande = new
                                            Commande(key_commande,"",Id_proprietaire, Id_fournisseur, date_commande, "Privee",id_current_user, id_current_agence
                                            , Today,Today,0,0);

                                    commandeViewModel.ajout_async(commande);
                                }
                                else
                                {
                                    key_commande = id_commande ;
                                }


                                LigneCommande ligne=new LigneCommande(key_ligne_commande,key_commande,Id_agence,Id_article,Integer.valueOf(at_quantite.getText().toString()),
                                        0,prix,"Fc",id_current_user,id_current_agence,MainActivity.getAddingDate(),MainActivity.getAddingDate(),0,0);

                                ligneCommandeViewModel.ajout_async(ligne);

                                Toast.makeText(getActivity(), "Commande enregistrée ", Toast.LENGTH_SHORT).show();
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

        spin_proprietaire = (Spinner)v.findViewById(R.id.spin_commande_add_proprietaire);
        spin_fournisseur = (Spinner)v.findViewById(R.id.spin_commande_add_fournisseur);
        spin_agence = (Spinner)v.findViewById(R.id.spin_commande_add_agence);
        spin_article = (Spinner)v.findViewById(R.id.spin_commande_add__article);

        at_quantite = (AutoCompleteTextView) v.findViewById(R.id.at_commande_add_quantite);
        et_bonus=(EditText) v.findViewById(R.id.et_commande_add_bonus);
        btn_valider = (Button) v.findViewById(R.id.btn_commande_add_valider);



        enabled(false) ;

        charging_spinning_appartenance() ;
        charging_spinning_proprietaire() ;

    }

    private void charging_spinning_agence(Spinner spin)
    {
        try {
            List<Agence> agences = agenceViewModel.getByAppartenanceProprietaire(Appartenance_save, Proprietaire_save) ;
            String[] ag = new String[agences.size()];  int compteur = 0 ;

            for (Agence agence : agences)
            {
                ag[compteur] = agence.getDenomination() ; compteur++ ;
            }

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
             Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_fournisseur(Spinner spin)
    {
        try {
            List<Fournisseur> fournisseurs = fournisseurViewModel.getFournisseurArrayListe() ;
            String[] ag = new String[fournisseurs.size()];  int compteur = 0 ;

            for (Fournisseur f : fournisseurs)
            {
                ag[compteur] = f.getId().toString() ; compteur++ ;
            }

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
             Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_proprietaire(Spinner spin)
    {
        try {
            List<Identity> identity = identityViewModel.getAgencesArrayListe() ;
            String[] ag = new String[identity.size()];  int compteur = 0 ;

            for (Identity id : identity)
            {
                ag[compteur] = id.getName().toString() ; compteur++ ;
            }

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
             Toast.makeText(getActivity(),  ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_destinataire(Spinner spin)
    {
        try {
            List<User> user = userViewModel.getUserArrayListe() ;
            String[] ag = new String[user.size()];  int compteur = 0 ;

            for (User user1 : user)
            {
                ag[compteur] = user1.getUsername(); compteur++ ;
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



    private void charging_spinning_article(Spinner spin)
    {
        try {
            List<Article> articles = articleViewModel.getArticlesArrayListe() ;
            String[] ag = new String[articles.size()];  int compteur = 0 ;

            for (Article article : articles)
            {
                ag[compteur] = article.getDesignation().toString() ; compteur++ ;
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

    private void enabled(Boolean value)
    {
        if(value.equals(true))
        {
            spin_proprietaire.setEnabled(true);   spin_fournisseur.setEnabled(true);
            spin_agence.setEnabled(true);
            spin_article.setEnabled(true);
            at_quantite.setEnabled(true);
            btn_valider.setVisibility(View.VISIBLE);
        }
        else
        {
            spin_proprietaire.setEnabled(false);   spin_fournisseur.setEnabled(false);
            spin_agence.setEnabled(false);
            spin_article.setEnabled(false);

            at_quantite.setEnabled(false);
            btn_valider.setVisibility(View.INVISIBLE);
        }
    }


    private void charging_spinning_appartenance()
    {

        String[] cat = { "Choisir...", "Agence Client ", "Agence Privée " };

        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item,cat );

        adapter_categorie.notifyDataSetChanged();

    }

    private void charging_spinning_proprietaire()
    {

        String[] cat = { "Choisir...", "Fournisseur" , "Client"};

        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item,cat );

        adapter_categorie.notifyDataSetChanged();

    }

    private void checking_data()
    {
        if(!Appartenance_save.equals("") && !Proprietaire_save.equals(""))
        {
            List<Agence> agences = agenceViewModel.getByAppartenanceProprietaire(Appartenance_save, Proprietaire_save) ;

            if(agences.size() ==  0)
            {
                enabled(false);
                Toast.makeText(getActivity(), " Aucune agence ...", Toast.LENGTH_SHORT).show();
            }
            else {
                enabled(true);

                charging_spinning_agence(spin_agence);
                charging_spinning_fournisseur(spin_fournisseur);
                charging_spinning_proprietaire(spin_proprietaire);
                charging_spinning_article(spin_article);
            }
        }
    }



}


