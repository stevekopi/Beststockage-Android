package layout;

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
import cd.sklservices.com.Beststockage.Classes.Agence;
import cd.sklservices.com.Beststockage.Classes.Approvisionnement;
import cd.sklservices.com.Beststockage.Classes.Article;
import cd.sklservices.com.Beststockage.Classes.Commande;
import cd.sklservices.com.Beststockage.Classes.LigneCommande;
import cd.sklservices.com.Beststockage.Classes.Livraison;
import cd.sklservices.com.Beststockage.Classes.Operation;
import cd.sklservices.com.Beststockage.Classes.User;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ApprovisionnementViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.CommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.LigneCommandeViewModel;
import cd.sklservices.com.Beststockage.ViewModel.LivraisonViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.UserViewModel;

public class LivraisonViewUpdate1 extends Fragment  {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= LivraisonViewUpdate1.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private LigneCommandeViewModel ligneCommandeViewModel;
    private AgenceViewModel agenceViewModel;
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

    private Approvisionnement approvisionnement ;
    private Operation operation ;
    private Livraison livraison ;
    private LigneCommande ligneCommande ;

    String id_commande = "" ;
    String id_ligneCommande = "" ;

    private Boolean btn_insert_livraison = false ;
    private Boolean change_id_ligne_commande = false ;

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
        rootView =inflater.inflate(R.layout.fragment_add_livraison1, container, false);

        user = MainActivity.getCurrentUser() ;

        date_livraison = "" ;

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

                        if(date_livraison == "" || id_commande == "")
                        {
                            Toast.makeText(getActivity(), " Erreur ...  " , Toast.LENGTH_LONG).show();
                        }
                        else if(btn_insert_livraison.equals(false)) {

                           btn_insert_livraison = true ;

                            if(id_ligneCommande == "")
                            {
                                int compteur = 0;
                                List<LigneCommande> ligne = ligneCommandeViewModel.ligne_commande_from_commande2(id_commande);
                                for (LigneCommande l : ligne) {
                                    if (spin_ligneCommande.getSelectedItemId() == compteur) {
                                        id_ligneCommande = l.getId() ;
                                    }
                                    compteur++ ;
                                }
                            }

                           Livraison l=new Livraison(livraison.getId(),id_ligneCommande,date_livraison,user.getId(),
                                    user.getAgence_id(),livraison.getAdding_date(),MainActivity.getAddingDate(),
                                    0,livraison.getPos()+1);

                            livraisonViewModel.update2(l);



                            Operation op=new Operation(operation.getId(),user.getId(),"",ligneCommande.getAgence_id(),ligneCommande.getAgence_id(),
                                    ligneCommande.getArticle_id()
                                    ,"Fc","Livraison_PC",Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString())
                                    ,0.0,date_livraison,"R.A.S",1,0,MainActivity.getCurrentUser().getAgence_id(),operation.getAdding_date(),MainActivity.getAddingDate(),0,operation.getPos()+1);

                            operationViewModel.ajout(op);

                            Approvisionnement app=new Approvisionnement(approvisionnement.getId(),livraison.getId(),operation.getId(),ligneCommande.getAgence_id(),
                                    ligneCommande.getArticle_id(),Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString()),
                                    0,MainActivity.getCurrentUser().getId(),MainActivity.getCurrentUser().getAgence_id(),
                                    approvisionnement.getAdding_date(),MainActivity.getAddingDate()
                                    ,0,approvisionnement.getPos()+1);

                            approvisionnementViewModel.ajout_async(app);

                            Toast.makeText(getActivity(), " Livraison mise à jour .... ", Toast.LENGTH_LONG).show();
                             ((MainActivity)getActivity()).afficherLivraison1();

                        }

                    }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(), " Prière de saisir tous les champs obligatoire ", Toast.LENGTH_LONG).show();
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

        try {
            this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class);
            this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
            this.commandeViewModel = new ViewModelProvider(this).get(CommandeViewModel.class);
            this.ligneCommandeViewModel = new ViewModelProvider(this).get(LigneCommandeViewModel.class);
            this.identityViewModel = new ViewModelProvider(this).get(IdentityViewModel.class);
            this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            this.commandeViewModel = new ViewModelProvider(this).get(CommandeViewModel.class);
            this.livraisonViewModel = new ViewModelProvider(this).get(LivraisonViewModel.class);
            this.approvisionnementViewModel = new ViewModelProvider(this).get(ApprovisionnementViewModel.class);
            this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class);

            approvisionnement = approvisionnementViewModel.getApprovisionnement();
            operation = operationViewModel.get(approvisionnement.getOperation_id(),false,false);
            livraison = livraisonViewModel.get(approvisionnement.getLivraison_id());
            ligneCommande = ligneCommandeViewModel.get(livraison.getLigne_commande_id());

            spin_commande = (Spinner) v.findViewById(R.id.spin_commandeLivraison);
            spin_ligneCommande = (Spinner) v.findViewById(R.id.spin_ligne_commande);

            at_quantite = (AutoCompleteTextView) v.findViewById(R.id.at_quantiteLivraison);
            at_bonus = (AutoCompleteTextView) v.findViewById(R.id.at_bonusLivraison);
            at_date = (AutoCompleteTextView) v.findViewById(R.id.at_date_livraison);
            btn_valider = (Button) v.findViewById(R.id.btn_saveLivraison);

            at_quantite.setText(String.valueOf(approvisionnement.getQuantite()));
            at_bonus.setText(String.valueOf(approvisionnement.getBonus()));
            at_date.setText(String.valueOf(livraison.getDate()));

            date_livraison = String.valueOf(livraison.getDate());

            enabled(true);

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

                    if (spin_commande.getSelectedItemId() != 0) {
                        int compteur = 1;
                        List<Commande> commandes = commandeViewModel.select_commande();

                        for (Commande c : commandes) {
                            if (spin_commande.getSelectedItemId() == compteur) {
                                id_ligneCommande = "";
                                id_commande = c.getId();
                                charging_spinning_ligneCommande(spin_ligneCommande);

                                enabled(true);
                                change_id_ligne_commande = true ;
                            }
                            compteur++;
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spin_ligneCommande.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(change_id_ligne_commande.equals(true)) {
                        id_ligneCommande = "";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            id_ligneCommande = ligneCommande.getId();
            id_commande = ligneCommande.getCommande_id();
            charging_spinning_commande(spin_commande, ligneCommande.getCommande_id());
            charging_spinning_ligneCommande(spin_ligneCommande);

        }
         catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_commande(Spinner spin, String Id_commande)
    {
        try {
            List<Commande> commandes = commandeViewModel.select_commande() ;
            String[] ag = new String[commandes.size() + 1 ];  int compteur = 0 ;

            for (Commande c : commandes)
            {
                User user = userViewModel.get(c.getAdding_user_id(),true,false) ;

                if(Id_commande.equals(c.getId()))
                {
                    ag[0] = String.valueOf("1") + ". " + user.getId() + " - " + c.getDate();
                }
                else {
                    ag[compteur] = String.valueOf(compteur) + ". " + user.getId() + " - " + c.getDate();
                    compteur++;
                }
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

    private void charging_spinning_ligneCommande(Spinner spin)
    {
        try {
            List<LigneCommande> ligne =  ligneCommandeViewModel.ligne_commande_from_commande2(id_commande) ;
            String[] ag = null  ;

            if(id_ligneCommande.equals(""))
            {
                ag = new String[ligne.size()];
                int compteur = 0 ;
                for (LigneCommande l : ligne)
                {
                    Article article = articleViewModel.get(l.getArticle_id(),true,true) ;
                    Agence agence = agenceViewModel.get(l.getAgence_id(),false,false) ;

                        ag[compteur] = String.valueOf( 1 + compteur) + ". " +  article.getDesignation()
                                + " - " + agence.getDenomination() + " (" +  String.valueOf(l.getQuantite())  + " ) " ; compteur++ ;
                }

            }
            else
            {
                ag = new String[2];
                ag[0] =  " Choisir ..." ;

                for (LigneCommande l : ligne)
                {
                    Article article = articleViewModel.get(l.getArticle_id(),true,true) ;
                    Agence agence = agenceViewModel.get(l.getAgence_id(),false,false) ;

                    if(l.getId().equals(id_ligneCommande))
                    {
                        ag[1] = String.valueOf(1) + ". " +  article.getDesignation()
                                + " - " + agence.getDenomination() + " (" +  String.valueOf(l.getQuantite())  + " ) " ;
                    }
                }

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


