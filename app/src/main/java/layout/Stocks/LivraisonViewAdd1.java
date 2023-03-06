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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Stocks.Approvisionnement;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Stocks.Commande;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.Classes.Stocks.Livraison;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.ApprovisionnementViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.CommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneCommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LivraisonViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;

public class LivraisonViewAdd1 extends Fragment  {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= LivraisonViewAdd1.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private LigneCommandeViewModel ligneCommandeViewModel;
    private AgenceViewModel agenceViewModel;
    private FournisseurViewModel fournisseurViewModel;
    private IdentityViewModel identityViewModel;
    private UserViewModel userViewModel;
    private CommandeViewModel commandeViewModel;
    private LivraisonViewModel livraisonViewModel;
    private ApprovisionnementViewModel approvisionnementViewModel;
    private OperationViewModel operationViewModel;

    private Spinner spin_commande, spin_ligneCommande ;
    private AutoCompleteTextView at_quantite, at_bonus ,at_date ;
    private Button btn_valider ;

    private User user ;
    private  Calendar calendar;
    private int jour, mois,day_of_month;
    private DatePickerDialog datePickerDialog;
    private String jours,moiss, date_livraison ; //Conversion de day et month en string

    String id_commande = "" ;

    private Boolean btn_insert_livraison = false ;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Livraison");
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
        rootView =inflater.inflate(R.layout.fragment_add_livraison1, container, false);

        user = MainActivity.getCurrentUser() ;

        date_livraison = "" ;

        init(rootView) ;

        try {

            btn_valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                         // String Today = "0000-00-00 00:00:00" ;
                        String id_current_user =  ((MainActivity)getActivity()).getCurrentUser().getId() ;
                        String id_current_agence =  ((MainActivity)getActivity()).getCurrentUser().getAgence_id() ;

                        if(date_livraison == "" || id_commande == "")
                        {
                            Toast.makeText(getActivity(), " Erreur ...  " , Toast.LENGTH_LONG).show();
                        }
                        else if(btn_insert_livraison.equals(false)) {

                            Boolean test = false ; btn_insert_livraison = true ;


                            String key_livraison = keygen(getActivity(), "LIV", "livraison");

                            String key_approvisionnement = keygen(getActivity(), "", "approvisionnement");

                            String key_operation = keygen(getActivity(), "", "operation");

                            int compteur = 0;
                            List<LigneCommande> ligne = ligneCommandeViewModel.ligne_commande_from_commande2(id_commande);
                            for (LigneCommande l : ligne) {
                                if (spin_ligneCommande.getSelectedItemId() == compteur) {

                                   Livraison livraison=new Livraison(key_livraison,l.getId(),date_livraison,MainActivity.getCurrentUser().getId(),
                                            MainActivity.getCurrentUser().getAgence_id(),MainActivity.getAddingDate(),MainActivity.getAddingDate(),
                                            0,0);

                                    livraisonViewModel.ajout_async(livraison);


                                    Operation op=new Operation(key_operation,user.getId(),"",l.getAgence_id(),l.getAgence_id(),l.getArticle_id()
                                    ,"Fc","Livraison_PC",Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString())
                                            ,0.0,date_livraison,"R.A.S",1,0,MainActivity.getCurrentUser().getAgence_id(),MainActivity.getAddingDate(),MainActivity.getAddingDate(),0,0);

                                    operationViewModel.ajout(op);

                                   Approvisionnement app=new Approvisionnement(key_approvisionnement,key_livraison,key_operation,l.getAgence_id(),
                                            l.getArticle_id(),Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString()),
                                            0,MainActivity.getCurrentUser().getId(),MainActivity.getCurrentUser().getAgence_id(),
                                            MainActivity.getAddingDate(),MainActivity.getAddingDate()
                                            ,0,0);

                                    approvisionnementViewModel.ajout_async(app);

                                    test = true ;

                                    ((MainActivity)getActivity()).afficherLivraison2();

                                }
                                compteur++;
                            }

                            if(test.equals(true))
                            {
                                Toast.makeText(getActivity(), " Livraison enregistrée ", Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Toast.makeText(getActivity(), " Erreur lors de l'enregistrement de la livraison ... ", Toast.LENGTH_LONG).show();

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
        this.commandeViewModel = new ViewModelProvider(this).get(CommandeViewModel.class) ;
        this.livraisonViewModel = new ViewModelProvider(this).get(LivraisonViewModel.class) ;
        this.approvisionnementViewModel = new ViewModelProvider(this).get(ApprovisionnementViewModel.class) ;
        this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class) ;

        spin_commande = (Spinner)v.findViewById(R.id.spin_commandeLivraison);
        spin_ligneCommande = (Spinner)v.findViewById(R.id.spin_ligne_commande);

        at_quantite = (AutoCompleteTextView) v.findViewById(R.id.at_quantiteLivraison);
        at_bonus = (AutoCompleteTextView) v.findViewById(R.id.at_bonusLivraison);
        at_date = (AutoCompleteTextView) v.findViewById(R.id.at_date_livraison);
        btn_valider = (Button) v.findViewById(R.id.btn_saveLivraison);

        enabled(false) ;

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
                        date_livraison = year + "-" + (moiss) + "-" + jours;
                        at_date.setText(jours + "/" + (moiss) + "/" + year);

                    }
                }, jour, mois, day_of_month);
                datePickerDialog.show();

            }
        });



        spin_commande.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spin_commande.getSelectedItemId() != 0) {
                    int compteur = 1;
                    List<Commande> commandes = commandeViewModel.select_commande();

                    for (Commande c : commandes) {
                        if (spin_commande.getSelectedItemId() == compteur) {
                            charging_spinning_ligneCommande(spin_ligneCommande, c.getId());

                            id_commande = c.getId() ;
                            enabled(true);
                        }
                        compteur++;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        charging_spinning_commande(spin_commande); ;
    }

    private void charging_spinning_commande(Spinner spin)
    {
        try {
            List<Commande> commandes = commandeViewModel.select_commande() ;
            String[] ag = new String[commandes.size() + 1 ];  int compteur = 1 ;

            ag[0] = "Choisir ..." ;
            for (Commande c : commandes)
            {
                User user = userViewModel.get(c.getAdding_user_id(),true,false) ;

                ag[compteur] = String.valueOf( compteur) + ". " +  user.getId() + " - " + c.getDate()  ; compteur++ ;
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

    private void charging_spinning_ligneCommande(Spinner spin, String id_commande)
    {
        try {
            List<LigneCommande> ligne =  ligneCommandeViewModel.ligne_commande_from_commande2(id_commande) ;
            String[] ag = new String[ligne.size()];  int compteur = 0 ;

            for (LigneCommande l : ligne)
            {
                Article article = articleViewModel.get(l.getArticle_id(),true,true) ;
                Agence agence = agenceViewModel.get(l.getAgence_id(),false,false) ;

                ag[compteur] = String.valueOf( 1 + compteur) + ". " +  article.getDesignation()
                        + " - " + agence.getDenomination() + " (" +  String.valueOf(l.getQuantite())  + " ) " ; compteur++ ;
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


    private void enabled(Boolean val)
    {
        if(val.equals(true))
        {
            spin_ligneCommande.setEnabled(true);            at_quantite.setEnabled(true);
            at_bonus.setEnabled(true);                      at_date.setEnabled(true);
            btn_valider.setEnabled(true);
        }
        else
        {
            spin_ligneCommande.setEnabled(false);            at_quantite.setEnabled(false);
            at_bonus.setEnabled(false);                      at_date.setEnabled(false);
            btn_valider.setEnabled(false);
        }
    }



}


