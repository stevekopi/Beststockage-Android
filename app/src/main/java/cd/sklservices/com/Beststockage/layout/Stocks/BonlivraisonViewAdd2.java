package cd.sklservices.com.Beststockage.layout.Stocks;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.Convoyeur;
import cd.sklservices.com.Beststockage.Classes.Registres.Delegue;
import cd.sklservices.com.Beststockage.Classes.Registres.Driver;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Classes.Registres.Vehicule;
import cd.sklservices.com.Beststockage.Classes.Stocks.Bonlivraison;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneBonlivraison;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.BonLivraisonViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ConvoyeurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.DelegueViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.DriverViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.LigneBonLivraisonViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.VehiculelViewModel;

public class BonlivraisonViewAdd2 extends Fragment  {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= BonlivraisonViewAdd2.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private BonLivraisonViewModel bonLivraisonViewModel;
    private AgenceViewModel agenceViewModel;
    private UserViewModel userViewModel;
    private LigneBonLivraisonViewModel ligneBonLivraisonViewModel;
    private IdentityViewModel identityViewModel;
    private OperationViewModel operationViewModel;
    private FournisseurViewModel fournisseurViewModel;
    private VehiculelViewModel vehiculelViewModel;
    private DelegueViewModel deleguelViewModel;
    private DriverViewModel driverlViewModel;
    private ConvoyeurViewModel convoyeurViewModel;

    private Spinner spin_agence, spin_article, spin_user, spin_fournisseur, spin_proprietaire, spin_vehicule, spin_delegue, spin_driver,
            spin_convoyeur, spin_proprietaireAgence, spin_appartenanceAgence  ;
    private AutoCompleteTextView at_quantite, at_bonus, at_date ,at_tauxChange ;
    private Button btn_valider ;

    private User user ;
    private  Calendar calendar;
    private int jour, mois,day_of_month;
    private DatePickerDialog datePickerDialog;
    private String jours,moiss, date_livraison ; //Conversion de day et month en string
    private String Appartenance_save, Proprietaire_save  ;

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
        rootView =inflater.inflate(R.layout.fragment_add_livraison2, container, false);

        user = MainActivity.getCurrentUser() ;

        date_livraison = "" ;

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

                        String  Id_agence= "", Id_article = "",  Id_fournisseur = "", Id_proprietaire = "", Id_delegue = "",
                                Id_convoyeur = "", Id_driver = "", Id_vehicule = "";   Double prix = 0.0  ;

                        String key_operation = keygen(getActivity(), "", "operation");

                        String key_bon = keygen(getActivity(), "BL","bonlivraison");

                        String key_ligne = keygen(getActivity(), "", "ligne_bonlivraison");

                        int compteur = 0 ;
                        List<Agence> listAgence = agenceViewModel.getByAppartenanceProprietaire(Appartenance_save, Proprietaire_save);
                        for (Agence ag : listAgence) {
                            if(spin_agence.getSelectedItemId() == compteur) {
                                Id_agence = ag.getId();
                            }

                            compteur++ ;
                        }

                        compteur = 0 ;
                        List<Identity> listIdentity = identityViewModel.getAgencesArrayListe();
                        for (Identity id : listIdentity) {

                            if(spin_proprietaire.getSelectedItemId() == compteur) {
                                Id_proprietaire = id.getId();
                            }

                            compteur++ ;
                        }

                        compteur = 0 ;
                        List<Fournisseur> listF = fournisseurViewModel.getFournisseurArrayListe() ;
                        for (Fournisseur f : listF) {
                            if(spin_fournisseur.getSelectedItemId() == compteur) {
                                Id_fournisseur = f.getId();
                            }

                            compteur++ ;
                        }

                        compteur = 0 ;
                        List<Article> listArticle = articleViewModel.getArticlesArrayListe() ;
                        for (Article article : listArticle) {
                            if(spin_article.getSelectedItemId() == compteur) {
                                Id_article = article.getId();
                                prix = article.getPrix() * Integer.valueOf(at_quantite.getText().toString()) ;
                            }
                            compteur++ ;
                        }

                        compteur = 0 ;
                        List<Vehicule> listVehicule= vehiculelViewModel.getVehiculeArrayListe() ;
                        for (Vehicule vehicule : listVehicule) {

                            if(spin_vehicule.getSelectedItemId() == compteur) {
                                Id_vehicule = vehicule.getId();
                            }
                            compteur++ ;
                        }

                        compteur = 0 ;
                        List<Delegue> listDelegue = deleguelViewModel.getDelegueArrayListe() ;
                        for (Delegue del : listDelegue) {

                            if(spin_delegue.getSelectedItemId() == compteur) {
                                Id_delegue = del.getId();
                            }
                            compteur++ ;
                        }

                        compteur = 0 ;
                        List<Convoyeur> listConvoyeur = convoyeurViewModel.getConvoyeurArrayListe() ;
                        for (Convoyeur com : listConvoyeur) {

                            if(spin_convoyeur.getSelectedItemId() == compteur) {
                                Id_convoyeur = com.getId();
                            }
                            compteur++ ;
                        }

                        compteur = 0 ;
                        List<Driver> listDriver = driverlViewModel.getDriverArrayListe() ;
                        for (Driver driver : listDriver) {

                            if(spin_driver.getSelectedItemId() == compteur) {
                                Id_driver = driver.getId();
                            }
                            compteur++ ;
                        }


                        // Bonlivraison bon = new Bonlivraison(user.getId(), Id_fournisseur, Id_proprietaire, date_livraison,
                        // spin_vehicule.getSelectedItem().toString(), spin_delegue.getSelectedItem().toString()
                        //        , spin_driver.getSelectedItem().toString(), spin_convoyeur.getSelectedItem().toString(),
                       //         id_current_agence, id_current_user, Today) ;
                       /// bon.setId(key_bon);

                        Bonlivraison bon=new Bonlivraison(key_bon,user.getId(),Id_driver,Id_vehicule,Id_delegue,Id_convoyeur,
                                id_current_user,MainActivity.getCurrentUser().getAgence_id(), MainActivity.getAddingDate(),
                                MainActivity.getAddingDate(),0,0);

                        bonLivraisonViewModel.ajout_async(bon);


                        Operation op=new Operation(key_operation,user.getId(),"",Id_agence,Id_agence,Id_article
                                ,"Fc","Livraison",Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString())
                                ,0.0,date_livraison,"R.A.S",1,0,MainActivity.getCurrentUser().getAgence_id(),Today,Today,0,0);

                        operationViewModel.ajout(op);

                        Double prix_dollars = prix / Integer.valueOf (at_tauxChange.getText().toString()) ;

                        LigneBonlivraison ligne=new LigneBonlivraison(key_ligne,key_bon,key_operation,Id_article,Integer.valueOf(at_quantite.getText().toString()),
                                Integer.valueOf(at_bonus.getText().toString()),prix,prix_dollars,"Fc",
                                id_current_user,id_current_agence,MainActivity.getAddingDate(),MainActivity.getAddingDate(),0,0);

                        ligneBonLivraisonViewModel.ajout_async(ligne);

                        Toast.makeText(getActivity(), " Le bon de livraison a été enregistré avec succès  ", Toast.LENGTH_LONG).show();

                        ((MainActivity)getActivity()).afficherLivraison1();

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
        this.bonLivraisonViewModel = new ViewModelProvider(this).get(BonLivraisonViewModel.class) ;
        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        this.ligneBonLivraisonViewModel = new ViewModelProvider(this).get(LigneBonLivraisonViewModel.class) ;
        this.identityViewModel = new ViewModelProvider(this).get(IdentityViewModel.class) ;
        this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class) ;
        this.fournisseurViewModel = new ViewModelProvider(this).get(FournisseurViewModel.class) ;
        this.vehiculelViewModel = new ViewModelProvider(this).get(VehiculelViewModel.class) ;
        this.deleguelViewModel = new ViewModelProvider(this).get(DelegueViewModel.class) ;
        this.driverlViewModel = new ViewModelProvider(this).get(DriverViewModel.class) ;
        this.convoyeurViewModel = new ViewModelProvider(this).get(ConvoyeurViewModel.class) ;

        spin_agence = (Spinner)v.findViewById(R.id.spin_livraisonAgence);
        spin_article = (Spinner)v.findViewById(R.id.spin_livraisonArticle);
        spin_fournisseur = (Spinner)v.findViewById(R.id.spin_livraisonFournisseur);
        spin_proprietaire = (Spinner)v.findViewById(R.id.spin_livraisonProprietaire);
        spin_user = (Spinner)v.findViewById(R.id.spin_livraisonUser);
        spin_vehicule = (Spinner)v.findViewById(R.id.spin_livraisonVehicule);
        spin_delegue = (Spinner)v.findViewById(R.id.spin_livraisonDelegue);
        spin_driver = (Spinner)v.findViewById(R.id.spin_livraisonDriver);
        spin_convoyeur = (Spinner)v.findViewById(R.id.spin_livraisonConvoyeur);
        spin_appartenanceAgence = (Spinner)v.findViewById(R.id.spin_appartenanceLivraison);
        spin_proprietaireAgence = (Spinner)v.findViewById(R.id.spin_proprietaireLivraison);
        btn_valider = (Button)v.findViewById(R.id.btn_saveLivraison2);

        at_quantite = (AutoCompleteTextView) v.findViewById(R.id.at_livraisonQuantite);
        at_bonus = (AutoCompleteTextView) v.findViewById(R.id.at_livraisonBonus);
        at_date = (AutoCompleteTextView) v.findViewById(R.id.at_livraisonDate);
        at_tauxChange = (AutoCompleteTextView) v.findViewById(R.id.at_livraisonTauxChange);

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

        spin_appartenanceAgence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spin_appartenanceAgence.getSelectedItemId() == 1)
                {
                    Appartenance_save = "Agence Client" ;
                }
                else if(spin_appartenanceAgence.getSelectedItemId() == 2)
                {
                    Appartenance_save = "Agence Privée" ;
                }
                else {
                    Appartenance_save ="" ;
                }
                checking_data() ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_proprietaireAgence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spin_proprietaireAgence.getSelectedItemId() == 1)
                {
                    Proprietaire_save = "Provider" ;
                }
                else if(spin_proprietaireAgence.getSelectedItemId() == 2)
                {
                    Proprietaire_save = "Customer" ;
                }

                else {
                    Proprietaire_save = "" ;
                }

                checking_data() ;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        charging_spinning_appartenance() ;
        charging_spinning_proprietaire() ;

        enabled(false) ;

    }

    private void charging_spinning_fournisseur(Spinner spin)
    {
        try {
            List<Fournisseur> fournisseurs = fournisseurViewModel.getFournisseurArrayListe() ;
            String[] ag = new String[fournisseurs.size()];  int compteur = 0 ;

            for (Fournisseur f : fournisseurs)
            {
                ag[compteur] = String.valueOf( compteur + 1 ) + ". " +  f.getId()   ; compteur++ ;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void charging_spinning_proprietaire(Spinner spin)
    {
        try {
            List<Identity> identitys = identityViewModel.getAgencesArrayListe() ;
            String[] ag = new String[identitys.size()];  int compteur = 0 ;

            for (Identity id : identitys)
            {
                ag[compteur] = String.valueOf( compteur + 1 ) + ". " +  id.getName()   ; compteur++ ;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void charging_spinning_agence(Spinner spin)
    {
        try {
            List<Agence> agences = agenceViewModel.getByAppartenanceProprietaire(Appartenance_save, Proprietaire_save);
            String[] ag = new String[agences.size()];  int compteur = 0 ;

            for (Agence a : agences)
            {
                ag[compteur] = String.valueOf( compteur + 1 ) + ". " +  a.getDenomination()   ; compteur++ ;
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

    private void charging_spinning_article(Spinner spin)
    {
        try {
            List<Article> articles = articleViewModel.getArticlesArrayListe() ;
            String[] ag = new String[articles.size()];  int compteur = 0 ;

            for (Article article : articles)
            {
                ag[compteur] = String.valueOf( compteur + 1 ) + ". " +  article.getDesignation()  ; compteur++ ;
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


    private void charging_spinning_user(Spinner spin)
    {
        try {
            List<User> users = userViewModel.getUserArrayListe() ;
            String[] ag = new String[users.size()];  int compteur = 0 ;

            for (User u : users)
            {
                ag[compteur] = String.valueOf( compteur + 1 ) + ". " +  u.getId()   ; compteur++ ;
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



    public void enabled(Boolean value)
    {
        if(value.equals(true)) {
            spin_agence.setEnabled(true);  spin_article.setEnabled(true);
            spin_fournisseur.setEnabled(true);  spin_proprietaire.setEnabled(true);
            spin_user.setEnabled(true);    spin_vehicule.setEnabled(true);
            spin_delegue.setEnabled(true);  spin_driver.setEnabled(true);
            spin_convoyeur.setEnabled(true);
            btn_valider.setVisibility(View.VISIBLE);

            at_quantite.setEnabled(true);
            at_bonus.setEnabled(true);
            at_date.setEnabled(true);
            at_tauxChange.setEnabled(true);
        }
        else
        {
            spin_agence.setEnabled(false);  spin_article.setEnabled(false);
            spin_fournisseur.setEnabled(false);  spin_proprietaire.setEnabled(false);
            spin_user.setEnabled(false);    spin_vehicule.setEnabled(false);
            spin_delegue.setEnabled(false);  spin_driver.setEnabled(false);
            spin_convoyeur.setEnabled(false);
            btn_valider.setVisibility(View.INVISIBLE);

            at_quantite.setEnabled(false);
            at_bonus.setEnabled(false);
            at_date.setEnabled(false);
            at_tauxChange.setEnabled(false);
        }

    }

    private void charging_spinning_appartenance()
    {

        String[] cat = { "Choisir...", "Agence client ... ", "Agence privé ... " };

        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item,cat );

        spin_appartenanceAgence.setAdapter(adapter_categorie);
        adapter_categorie.notifyDataSetChanged();

    }

    private void charging_spinning_proprietaire()
    {

        String[] cat = { "Choisir...", "Fournisseur  " , "Client "};

        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item,cat );

        spin_proprietaireAgence.setAdapter(adapter_categorie);
        adapter_categorie.notifyDataSetChanged();

    }

    private void checking_data() {
        if (!Appartenance_save.equals("") && !Proprietaire_save.equals("")) {
            List<Agence> agences = agenceViewModel.getByAppartenanceProprietaire(Appartenance_save, Proprietaire_save);

            if (agences.size() == 0) {
                enabled(false);
                Toast.makeText(getActivity(), " Aucune agence ...", Toast.LENGTH_SHORT).show();
            } else {
                enabled(true);

                charging_spinning_agence(spin_agence) ;
                charging_spinning_article(spin_article) ;
                charging_spinning_user(spin_user) ;
                charging_spinning_vehicule(spin_vehicule) ;
                charging_spinning_delegue(spin_delegue) ;
                charging_spinning_driver(spin_driver) ;
                charging_spinning_convoyeur(spin_convoyeur) ;
                charging_spinning_fournisseur(spin_fournisseur);
                charging_spinning_proprietaire(spin_proprietaire) ;

            }
        }
    }

    private void charging_spinning_vehicule(Spinner spin)
    {
        try {
            List<Vehicule> vehicules = vehiculelViewModel.getVehiculeArrayListe() ;
            String[] ag = new String[vehicules.size()];  int compteur = 0 ;

            for (Vehicule v : vehicules)
            {
                Identity identity  =  identityViewModel.get(v.getProprietaire_id(),false) ;
                ag[compteur] = String.valueOf( compteur + 1 ) + ". " + identity.getName() + " - " +  v.getMarque()   ; compteur++ ;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_delegue(Spinner spin)
    {
        try {
            List<Delegue> delegues = deleguelViewModel.getDelegueArrayListe() ;
            String[] ag = new String[delegues.size()];  int compteur = 0 ;

            for (Delegue del : delegues)
            {
                Identity identity  =  identityViewModel.get(del.getProprietaire_id(),false) ;
                ag[compteur] = String.valueOf( compteur + 1 ) + ". " + identity.getName() + " - " +  del.getName()   ; compteur++ ;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void charging_spinning_driver(Spinner spin)
    {
        try {
            List<Driver> drivers = driverlViewModel.getDriverArrayListe() ;
            String[] ag = new String[drivers.size()];  int compteur = 0 ;

            for (Driver d : drivers)
            {
                Identity identity  =  identityViewModel.get(d.getProprietaire_id(),false) ;
                ag[compteur] = String.valueOf( compteur + 1 ) + ". " + identity.getName() + " - " +  d.getName()  ; compteur++ ;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_convoyeur(Spinner spin)
    {
        try {
            List<Convoyeur> convoyeurs = convoyeurViewModel.getConvoyeurArrayListe() ;
            String[] ag = new String[convoyeurs.size()];  int compteur = 0 ;

            for (Convoyeur c : convoyeurs)
            {
                Identity identity  =  identityViewModel.get(c.getProprietaire_id(),false) ;
                ag[compteur] = String.valueOf( compteur + 1 ) + ". " + identity.getName() + " - " +  c.getName()  ; compteur++ ;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }



}


