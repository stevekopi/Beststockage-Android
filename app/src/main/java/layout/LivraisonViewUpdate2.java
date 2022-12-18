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
import cd.sklservices.com.Beststockage.Classes.Article;
import cd.sklservices.com.Beststockage.Classes.Bonlivraison;
import cd.sklservices.com.Beststockage.Classes.Convoyeur;
import cd.sklservices.com.Beststockage.Classes.Delegue;
import cd.sklservices.com.Beststockage.Classes.Driver;
import cd.sklservices.com.Beststockage.Classes.Fournisseur;
import cd.sklservices.com.Beststockage.Classes.Identity;
import cd.sklservices.com.Beststockage.Classes.LigneBonlivraison;
import cd.sklservices.com.Beststockage.Classes.Operation;
import cd.sklservices.com.Beststockage.Classes.User;
import cd.sklservices.com.Beststockage.Classes.Vehicule;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.BonLivraisonViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ConvoyeurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.DelegueViewModel;
import cd.sklservices.com.Beststockage.ViewModel.DriverViewModel;
import cd.sklservices.com.Beststockage.ViewModel.FournisseurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.IdentityViewModel;
import cd.sklservices.com.Beststockage.ViewModel.LigneBonLivraisonViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.UserViewModel;
import cd.sklservices.com.Beststockage.ViewModel.VehiculelViewModel;

public class LivraisonViewUpdate2 extends Fragment  {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= LivraisonViewUpdate2.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private BonLivraisonViewModel bonLivraisonViewModel;
    private AgenceViewModel agenceViewModel;
    private UserViewModel userViewModel;
    private LigneBonLivraisonViewModel ligneBonLivraisonViewModel;
    private IdentityViewModel identityViewModel;
    private OperationViewModel operationViewModel;
    private FournisseurViewModel fournisseurViewModel;

    private Spinner spin_agence, spin_article, spin_user, spin_fournisseur, spin_proprietaire, spin_vehicule, spin_delegue,
            spin_driver, spin_convoyeur, spin_proprietaireAgence, spin_appartenanceAgence  ;
    private AutoCompleteTextView at_quantite, at_bonus, at_date ,at_tauxChange  ;
    private Button btn_valider ;

    private LigneBonlivraison ligneBonlivraison = null ;
    private Bonlivraison bonlivraison = null ;
    private Operation operation = null;

    private String Appartenance_save, Proprietaire_save  ;

    private User user ;
    private  Calendar calendar;
    private int jour, mois,day_of_month;
    private DatePickerDialog datePickerDialog;
    private String jours,moiss, date_livraison ; //Conversion de day et month en string

    private VehiculelViewModel vehiculelViewModel;
    private DelegueViewModel deleguelViewModel;
    private DriverViewModel driverlViewModel;
    private ConvoyeurViewModel convoyeurViewModel;

    String id_commande = "" ;

    private Boolean btn_insert_livraison = false ;

    String[][] af, ap, ag, at, veh , deleg, conv, driv ;

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
        rootView =inflater.inflate(R.layout.fragment_add_livraison2, container, false);

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

                        String Id_agence = "" ;       String Id_article = "" ;     Double prix = 0.0  ;
                        String Id_fournisseur = "" ;  String Id_proprietaire = "" ;
                        String  Id_delegue = "", Id_convoyeur = "", Id_driver = "", Id_vehicule = "";

                        int compteur = 0 ;

                        if(Appartenance_save.equals("") && Proprietaire_save.equals(""))
                        {
                            Agence agence = agenceViewModel.get (operation.getAgence_1_id(),false,false) ;
                            Id_agence = agence.getId() ;
                        }
                        else
                        {
                            compteur = 0 ;
                            List<Agence> listAgence = agenceViewModel.getByAppartenanceProprietaire(Appartenance_save, Proprietaire_save);
                            for (Agence agences : listAgence) {
                                if(spin_agence.getSelectedItemId() == compteur) {
                                    Id_agence = agences.getId();
                                }
                                compteur++ ;
                            }
                        }


                        compteur = identityViewModel.getAgencesArrayListe().size() ;
                        for (int i=0 ; i < compteur; i++)  {
                            if(spin_proprietaire.getSelectedItemId() == i) {
                                Id_proprietaire = ap[i][1];
                            }
                        }

                        compteur = fournisseurViewModel.getFournisseurArrayListe().size() ;
                        for (int i=0 ; i < compteur; i++)  {
                            if(spin_fournisseur.getSelectedItemId() == i) {
                                Id_fournisseur = af[i][1];
                            }
                        }

                        compteur = vehiculelViewModel.getVehiculeArrayListe().size() ;
                        for (int i=0 ; i < compteur; i++)  {
                            if(spin_vehicule.getSelectedItemId() == i) {
                                Id_vehicule = veh[i][1];
                            }
                        }

                        compteur = driverlViewModel.getDriverArrayListe().size() ;
                        for (int i=0 ; i < compteur; i++)  {
                            if(spin_driver.getSelectedItemId() == i) {
                                Id_driver = driv[i][1];
                            }
                        }

                        compteur = convoyeurViewModel.getConvoyeurArrayListe().size() ;
                        for (int i=0 ; i < compteur; i++)  {
                            if(spin_convoyeur.getSelectedItemId() == i) {
                                Id_convoyeur = conv[i][1];
                            }
                        }

                        compteur = deleguelViewModel.getDelegueArrayListe().size() ;
                        for (int i=0 ; i < compteur; i++)  {
                            if(spin_delegue.getSelectedItemId() == i) {
                                Id_delegue = deleg[i][1];
                            }
                        }

                        compteur =  articleViewModel.getArticlesArrayListe().size();
                        for (int i=0 ; i < compteur; i++)  {
                            if(spin_article.getSelectedItemId() == i) {
                                Id_article = at[i][1] ;
                                prix = articleViewModel.get(at[i][1],false,false).getPrix() * Integer.valueOf(at_quantite.getText().toString()) ;

                            }
                        }

                        Operation op=new Operation(operation.getId(),user.getId(),"",Id_agence,Id_agence,
                                Id_article,"Fc","Livraison",Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString())
                                ,0.0,date_livraison,"R.A.S",1,0,MainActivity.getCurrentUser().getAgence_id(),operation.getAdding_date(),MainActivity.getAddingDate(),0,operation.getPos()+1);


                        operationViewModel.ajout(op);

                        Double prix_dollars = prix / (Double.valueOf (at_tauxChange.getText().toString()) * 10 ) ;

                        LigneBonlivraison ligne=new LigneBonlivraison(ligneBonlivraison.getId(),bonlivraison.getId(),operation.getId(),Id_article,
                                Integer.valueOf(at_quantite.getText().toString()),
                                Integer.valueOf(at_bonus.getText().toString()),prix,prix_dollars,"Fc",
                                id_current_user,id_current_agence,MainActivity.getAddingDate(),MainActivity.getAddingDate(),0,ligneBonlivraison.getPos()+1);


                        ligneBonLivraisonViewModel.update(ligne);

                        Toast.makeText(getActivity(), " Le bon de livraison a été mise à jour  ", Toast.LENGTH_LONG).show();

                        ((MainActivity)getActivity()).afficherDetailLigneBonLivraison(ligne);

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
        this.ligneBonlivraison = ligneBonLivraisonViewModel.getLigneBonLivraison();
        this.operation = operationViewModel.get(ligneBonlivraison.getOperation_id(),false,false) ;
        this.bonlivraison = bonLivraisonViewModel.get(ligneBonlivraison.getBonlivraison_id(),false) ;

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

      //  Fournisseur fournisseur = fournisseurViewModel.get(bonlivraison.getFournisseur_id()) ;
      //  Identity identity = identityViewModel.get(bonlivraison.getProprietaire_id()) ;
        Agence agence = agenceViewModel.get (operation.getAgence_1_id(),false,false) ;
        Article article = articleViewModel.get(operation.getArticle_id(),true,true) ;
        User user = userViewModel.get(bonlivraison.getUser_id(),true,false) ;

        Vehicule myListvehicule = vehiculelViewModel.get(bonlivraison.getVehicule_id()) ;
        Driver myListdriver = driverlViewModel.get(bonlivraison.getDriver_id()) ;
        Convoyeur myListConvoyeur = convoyeurViewModel.get(bonlivraison.getConvoyeur_id()) ;
        Delegue myListDelegue = deleguelViewModel.get(bonlivraison.getDelegue_id()) ;

        Double taux = ligneBonlivraison.getMontant() / ( ligneBonlivraison.getMontant_dollars() * 10 ) ;

        at_bonus.setText(String.valueOf(operation.getBonus())) ;
        at_quantite.setText(String.valueOf(operation.getQuantite())) ;
        at_date.setText(String.valueOf(operation.getDate())) ;
        at_tauxChange.setText(String.valueOf(taux)) ;

        date_livraison = operation.getDate() ;

        charging_spinning_agence(spin_agence, agence) ;
        charging_spinning_article(spin_article, article) ;
        charging_spinning_user(spin_user, user) ;

        charging_spinning_vehicule(spin_vehicule, myListvehicule ) ;
        charging_spinning_Driver(spin_driver, myListdriver );
        charging_spinning_Convoyeur(spin_convoyeur, myListConvoyeur );
        charging_spinning_Delegue(spin_delegue, myListDelegue );


        spin_appartenanceAgence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spin_appartenanceAgence.getSelectedItemId() == 1)
                {
                    Appartenance_save = "Client" ;
                }
                else if(spin_appartenanceAgence.getSelectedItemId() == 2)
                {
                    Appartenance_save = "Privee" ;
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
        charging_spinning_proprietaire2() ;

        charging_spinning_fournisseur(spin_fournisseur, null );
        charging_spinning_proprietaire(spin_proprietaire, null) ;

    }

    private void charging_spinning_fournisseur(Spinner spin, Fournisseur fournisseur)
    {
        try {
            List<Fournisseur> fournisseurs = fournisseurViewModel.getFournisseurArrayListe() ;
            af = new String[fournisseurs.size()][3];
            String[] af2 = new String[fournisseurs.size()];  int compteur = 1 ;

            for (Fournisseur f : fournisseurs)
            {
                if(fournisseur.getId().equals(f.getId()))
                {
                    af[0][0] =  f.getId() ;
                    af2[0] =  f.getId() ;
                    af[0][1] =  f.getId() ;
                }
                else {
                    af[compteur][0] =  f.getId() ;
                    af2[compteur] =  f.getId() ;
                    af[compteur][1] =  f.getId() ;     compteur++ ;
                }

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, af2 );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void charging_spinning_proprietaire(Spinner spin, Identity identity)
    {
        try {
            List<Identity> identitys = identityViewModel.getAgencesArrayListe() ;
            ap = new String[identitys.size()][3];
            String[] ap2 = new String[identitys.size()]; int compteur = 0 ;

            for (Identity id : identitys)
            {
                if(id.getId().equals(identity.getId()))
                {
                    ap[0][0] =  id.getName() ;
                    ap2[0] =  id.getName()  ;
                    ap[0][1] =  id.getId() ;
                }
                else
                {
                    ap[compteur][0] =  id.getName() ;
                    ap2[compteur] =  id.getName()  ;
                    ap[compteur][1] =  id.getId() ;
                    compteur++ ;
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ap2 );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void charging_spinning_agence(Spinner spin, Agence agence)
    {
        try {
            List<Agence> agences = agenceViewModel.getAgencesArrayListe() ;
            ag = new String[1][3];
            String[] ag2 = new String[1];

            ag[0][0] =  agence.getDenomination();
            ag2[0] =  agence.getDenomination()  ;
            ag[0][1] =  agence.getId() ;

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ag2 );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
             Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_article(Spinner spin, Article art)
    {
        try {
            List<Article> articles = articleViewModel.getArticlesArrayListe() ;
            at = new String[articles.size() ][3];
            String[] at2 = new String[articles.size() ] ;  int compteur = 1 ;

            for (Article article : articles)
            {
                if(article.getId().equals(art.getId()))
                {
                    at[0][0] =  article.getDesignation();
                    at2[0] =  article.getDesignation()  ;
                    at[0][1] =  article.getId() ;
                }
                else {
                    at[compteur][0] =  article.getDesignation();
                    at2[compteur] =  article.getDesignation()  ;
                    at[compteur][1] =  article.getId() ;
                    compteur++;
                }
            }

            ArrayAdapter<String> adapter_agence = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, at2 );

            spin.setAdapter(adapter_agence);
            adapter_agence.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void charging_spinning_user(Spinner spin,  User user1)
    {
        try {
            List<User> users = userViewModel.getUserArrayListe() ;
            String[] ag = new String[users.size()];  int compteur = 1 ;

            for (User u : users)
            {
                if(u.getId().equals(user1.getId()))
                {
                    ag[0] =  u.getId()   ;
                }
                else {
                    ag[compteur] = u.getId();
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

    private void charging_spinning_vehicule(Spinner spin, Vehicule vehicule)
    {
        try {
            List<Vehicule> vehicules = vehiculelViewModel.getVehiculeArrayListe() ;
            veh = new String[vehicules.size()][3];
            String[] ap2 = new String[vehicules.size()]; int compteur = 1 ;

            for (Vehicule v : vehicules)
            {
                Identity identity  =  identityViewModel.get(v.getProprietaire_id(),false) ;

                if(v.getId().equals(vehicule.getId()))
                {
                    veh[0][0] =  identity.getName() + " - " +  v.getMarque()   ;
                    ap2[0] =  identity.getName() + " - " +  v.getMarque()   ;
                    veh[0][1] =  v.getId() ;
                }
                else
                {
                    veh[compteur][0] =  identity.getName() + " - " +  v.getMarque()   ;
                    ap2[compteur] =  identity.getName() + " - " +  v.getMarque()   ;
                    veh[compteur][1] =  v.getId() ;
                    compteur++ ;
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ap2 );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_Driver(Spinner spin, Driver driver)
    {
        try {
            List<Driver> drivers = driverlViewModel.getDriverArrayListe() ;
            driv = new String[drivers.size()][3];
            String[] ap2 = new String[drivers.size()]; int compteur = 1 ;

            for (Driver driver1 : drivers)
            {
                Identity identity  =  identityViewModel.get(driver1.getProprietaire_id(),false) ;

                if(driver.getId().equals(driver1.getId()))
                {
                    driv[0][0] =  identity.getName() + " - " +  driver1.getName()   ;
                    ap2[0] =  identity.getName() + " - " +  driver1.getName()   ;
                    driv[0][1] =  driver1.getId() ;
                }
                else
                {
                    driv[compteur][0] =  identity.getName() + " - " +  driver1.getName()    ;
                    ap2[compteur] =  identity.getName() + " - " +  driver1.getName()   ;
                    driv[compteur][1] =  driver1.getId() ;
                    compteur++ ;
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ap2 );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_Convoyeur(Spinner spin, Convoyeur convoyeur)
    {
        try {
            List<Convoyeur> convoyeurs = convoyeurViewModel.getConvoyeurArrayListe() ;
            conv = new String[convoyeurs.size()][3];
            String[] ap2 = new String[convoyeurs.size()]; int compteur = 1 ;

            for (Convoyeur convoyeur1 : convoyeurs)
            {
                Identity identity  =  identityViewModel.get(convoyeur1.getProprietaire_id(),false) ;

                if(convoyeur.getId().equals(convoyeur1.getId()))
                {
                    conv[0][0] =  identity.getName() + " - " +  convoyeur1.getName()   ;
                    ap2[0] =  identity.getName() + " - " +  convoyeur1.getName()   ;
                    conv[0][1] =  convoyeur1.getId() ;
                }
                else
                {
                    conv[compteur][0] =  identity.getName() + " - " +  convoyeur1.getName()    ;
                    ap2[compteur] =  identity.getName() + " - " +  convoyeur1.getName()   ;
                    conv[compteur][1] =  convoyeur1.getId() ;
                    compteur++ ;
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ap2 );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_Delegue(Spinner spin, Delegue delegue)
    {
        try {
            List<Delegue> delegues = deleguelViewModel.getDelegueArrayListe() ;
            deleg = new String[delegues.size()][3];
            String[] ap2 = new String[delegues.size()]; int compteur = 1 ;

            for (Delegue delegue1 : delegues)
            {
                Identity identity  =  identityViewModel.get(delegue1.getProprietaire_id(),false) ;

                if(delegue.getId().equals(delegue1.getId()))
                {
                    deleg[0][0] =  identity.getName() + " - " +  delegue1.getName()   ;
                    ap2[0] =  identity.getName() + " - " +  delegue1.getName()   ;
                    deleg[0][1] =  delegue1.getId() ;
                }
                else
                {
                    deleg[compteur][0] =  identity.getName() + " - " +  delegue1.getName()    ;
                    ap2[compteur] =  identity.getName() + " - " +  delegue1.getName()   ;
                    deleg[compteur][1] =  delegue1.getId() ;
                    compteur++ ;
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, ap2 );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void charging_spinning_appartenance()
    {

        String[] cat = { "Choisir...", "Agence Client", "Agence Privée " };

        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item,cat );

        spin_appartenanceAgence.setAdapter(adapter_categorie);
        adapter_categorie.notifyDataSetChanged();

    }

    private void charging_spinning_proprietaire2()
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
                Toast.makeText(getActivity(), " Aucune agence ...", Toast.LENGTH_SHORT).show();
            } else {
                charging_spinning_agence2(spin_agence) ;
            }
        }
    }

    private void charging_spinning_agence2(Spinner spin)
    {
        try {
            List<Agence> agences = agenceViewModel.getByAppartenanceProprietaire(Appartenance_save, Proprietaire_save);
            ag = new String[agences.size()][3];
            String[] ag2 = new String[agences.size()];  int compteur = 0 ;

            for (Agence a : agences)
            {
                ag[compteur][0] =  a.getDenomination();
                ag2[compteur] =  a.getDenomination()  ;
                ag[compteur][1] =  a.getId() ;
                compteur++;
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




}


