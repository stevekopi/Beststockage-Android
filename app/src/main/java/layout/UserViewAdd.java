package layout;

import static cd.sklservices.com.Beststockage.ViewModel.TableKeyIncrementorViewModel.keygen;

import android.app.DatePickerDialog;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Outils.MD5;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.* ;

public class UserViewAdd extends Fragment  {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= UserViewAdd.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private UserViewModel userViewModel;
    private AgenceViewModel agenceViewModel;
    private TownViewModel townViewModel;
    private DistrictViewModel districtViewModel;
    private TownshipViewModel townshipViewModel;
    private QuarterViewModel quarterViewModel;
    private StreetViewModel streetViewModel;
    private AdresseViewModel adresseViewModel;
    private IdentityViewModel identityViewModel;

    private Spinner spin_categorie, spin_agence, spin_role, spin_sexe, spin_province, spin_district, spin_commune ;
    private AutoCompleteTextView at_login, at_passe, at_passe2, at_nom, at_telephone,at_telephone2,at_telephone3,
                 at_quartier , at_avenue, at_numero ;
    private Button btn_valider ;

    private User user ;
    private  Calendar calendar;
    private int jour, mois,day_of_month;
    private DatePickerDialog datePickerDialog;
    private String jours,moiss ;
    private String Id_town = "" , Id_district = "", Id_commune = "", Id_quartier = "", Id_avenue = "", Id_adresse = "";

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Utilisateur");
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
        try {

        rootView =inflater.inflate(R.layout.fragment_add_user, container, false);

        user = MainActivity.getCurrentUser() ;

        init(rootView) ;


            btn_valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        String Today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        // String Today = "0000-00-00 00:00:00" ;
                        String id_current_user =  ((MainActivity)getActivity()).getCurrentUser().getId() ;
                        String id_current_agence =  ((MainActivity)getActivity()).getCurrentUser().getAgence_id() ;

                        if(!at_passe.getText().toString().equals(at_passe2.getText().toString()) )
                        {
                            Toast.makeText(getActivity(), " Les mots de passe ne concordent pas !!! " , Toast.LENGTH_LONG).show();
                        }
                        else if(Id_town.equals("") || Id_district.equals("") || Id_commune.equals(""))
                        {
                            Toast.makeText(getActivity(), " Veuillez inserer l'adresse S.V.P !!! " , Toast.LENGTH_LONG).show();
                        }
                        else if(at_passe.getText().length() == 0)
                        {
                            Toast.makeText(getActivity(), " Le mot de passe ne doit pas etre vide " , Toast.LENGTH_LONG).show();
                        }
                        else
                            {
                                String Id_agence = "" ;  int compteur = 0 ;
                                String pwd =     MD5.getEncodedPassword(at_passe2.getText().toString());
                                String Key_identity = keygen(getActivity(), "", "identity");
                                String Id_role = "" ;

                                if (spin_categorie.getSelectedItemId() == 1) {

                                    compteur = 0 ;

                                    List<Agence> agences = agenceViewModel.getByPrivee() ;
                                    for (Agence ag : agences) {
                                        if(spin_agence.getSelectedItemId() == compteur) {
                                            Id_agence = ag.getId();
                                        }
                                        compteur++ ;
                                    }


                                } else if (spin_categorie.getSelectedItemId() == 2) {
                                    compteur = 0 ;

                                    List<Agence> agences = agenceViewModel.getByCustomer(false,false) ;
                                    for (Agence ag : agences) {
                                        if(spin_agence.getSelectedItemId() == compteur) {
                                            Id_agence = ag.getId();
                                        }
                                        compteur++ ;
                                    }
                                }


                            //////////////////////////////////////////
                            ///////////////////////// Gestion des adresse s /////
                            /////////////////////////////////////////////////////////

                            if(Id_quartier.equals(""))
                            {
                                Id_quartier = keygen(getActivity(), "", "quarter");

                                Quarter q = new Quarter(Id_quartier,Id_commune,  at_quartier.getText().toString(), Today,Today,0,0) ;
                                quarterViewModel.ajout_async(q);
                            }

                            if(Id_avenue.equals(""))
                            {
                                Id_avenue = keygen(getActivity(), "", "street");

                                Street t = new Street(Id_avenue,Id_quartier, at_avenue.getText().toString(), Today,Today,0,0) ;


                                streetViewModel.ajout_async(t);
                            }

                                Id_adresse = keygen(getActivity(), "", "address");

                                Address address  = new Address(Id_adresse, Id_avenue, at_numero.getText().toString(),"",Today,Today,0,0) ;

                                adresseViewModel.ajout(address);


                            ////////////////////////////////////////////////
                            ////////////  Gestion Identity ///////////////////////////////////
                            ///////////////////////////////////////////////

                            Identity identity = new Identity("",Id_adresse, "User", at_nom.getText().toString(), at_telephone.getText().toString(),
                                    at_telephone2.getText().toString(), at_telephone3.getText().toString()
                                    , Today,Today,0,0) ;
                            identity.setId(Key_identity);

                            identityViewModel.ajout(identity);


                            //////////////////////////////////////////////////
                            ////////  Ajout User ///////////////////////
                            /////////////////////////////////////////


                                if(spin_role.getSelectedItemId() == 0) {
                                    Id_role = "00";
                                }
                                else  if(spin_role.getSelectedItemId() == 1) {
                                    Id_role = "01";
                                }
                                else  if(spin_role.getSelectedItemId() == 2) {
                                    Id_role = "02";
                                }
                                else  if(spin_role.getSelectedItemId() == 3) {
                                    Id_role = "03";
                                }
                                else  if(spin_role.getSelectedItemId() == 4) {
                                    Id_role = "04";
                                }

                            User user = new User("",Id_agence, Id_role,at_login.getText().toString(),
                                   pwd,  "Operationnel", Today,Today,0,0) ;
                            user.setId(Key_identity);

                            userViewModel.ajout_sync(user);


                            Toast.makeText(getActivity(), "L'utilisateur a été ajouté avec succès !!! ", Toast.LENGTH_SHORT).show();

                            ((MainActivity)getActivity()).afficherUtilisateur();

                        }

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getActivity(), " Prière de saisir tous les champs obligatoire " , Toast.LENGTH_LONG).show();
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
            this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class);
            this.townViewModel = new ViewModelProvider(this).get(TownViewModel.class);
            this.districtViewModel = new ViewModelProvider(this).get(DistrictViewModel.class);
            this.townViewModel = new ViewModelProvider(this).get(TownViewModel.class);
            this.townshipViewModel = new ViewModelProvider(this).get(TownshipViewModel.class);
            this.quarterViewModel = new ViewModelProvider(this).get(QuarterViewModel.class);
            this.streetViewModel = new ViewModelProvider(this).get(StreetViewModel.class);
            this.adresseViewModel = new ViewModelProvider(this).get(AdresseViewModel.class);
            this.identityViewModel = new ViewModelProvider(this).get(IdentityViewModel.class);

            spin_agence = (Spinner) v.findViewById(R.id.spin_agenceUser);
            spin_categorie = (Spinner) v.findViewById(R.id.spin_categorieUser);
            spin_role = (Spinner) v.findViewById(R.id.spin_roleUser);
            spin_sexe = (Spinner) v.findViewById(R.id.spin_sexeUser);
            btn_valider = (Button) v.findViewById(R.id.btn_saveUser);

            at_login = (AutoCompleteTextView) v.findViewById(R.id.at_loginUser);
            at_passe = (AutoCompleteTextView) v.findViewById(R.id.at_passeUser);
            at_passe2 = (AutoCompleteTextView) v.findViewById(R.id.at_passe2User);
            at_nom = (AutoCompleteTextView) v.findViewById(R.id.at_nomUser);
            at_telephone = (AutoCompleteTextView) v.findViewById(R.id.at_telephoneUser);
            at_telephone2 = (AutoCompleteTextView) v.findViewById(R.id.at_telephoneUser2);
            at_telephone3 = (AutoCompleteTextView) v.findViewById(R.id.at_telephoneUser3);
            spin_province = (Spinner) v.findViewById(R.id.at_provinceUser);
            spin_district = (Spinner) v.findViewById(R.id.at_districtUser);
            spin_commune = (Spinner) v.findViewById(R.id.at_communeUser);
            at_quartier = (AutoCompleteTextView) v.findViewById(R.id.at_quartierUser);
            at_avenue = (AutoCompleteTextView) v.findViewById(R.id.at_avenueUser);
            at_numero = (AutoCompleteTextView) v.findViewById(R.id.at_NumeroUser);


            spin_categorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    try {
                        if (spin_categorie.getSelectedItemId() == 1) {
                            enabled(true);
                            charging_spinning_agence1(spin_agence);
                            charging_spinning_role(spin_role);
                        } else if (spin_categorie.getSelectedItemId() == 2) {
                            enabled(true);
                            charging_spinning_agence2(spin_agence);
                            charging_spinning_role(spin_role);
                        } else {
                            enabled(false);
                        }
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spin_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    try {

                        List<Town> towns = townViewModel.getTownArrayListe() ;
                        int compteur = 1 ;

                        for (Town town : towns) {
                            if (spin_province.getSelectedItemId() == compteur) {
                                charging_spinning_district( spin_district , town.getId()) ;
                                Id_town = town.getId() ;
                            }
                            compteur++ ;
                        }

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spin_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    try {

                        List<District> districts = districtViewModel.getByTown(Id_town) ;
                        int compteur = 1 ;

                        for (District district : districts) {
                            if (spin_district.getSelectedItemId() == compteur) {
                                charging_spinning_commune( spin_commune , district.getId()) ;
                                Id_district = district.getId() ;
                            }
                            compteur++ ;
                        }

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spin_commune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    try {

                        List<Township> townships = townshipViewModel.getByDistrict(Id_district) ;
                        int compteur = 1 ;

                        for (Township township : townships) {
                            if (spin_commune.getSelectedItemId() == compteur) {
                                Id_commune = township.getId() ;
                            }
                            compteur++ ;
                        }

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            at_quartier.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        Quarter q = quarterViewModel.find(Id_commune, at_quartier.getText().toString());
                        if (!q.getId().equals(null)) {
                            Toast.makeText(getActivity(), q.getName().toString(), Toast.LENGTH_SHORT).show();
                            Id_quartier = q.getId() ;
                        }
                        else
                        {
                            Id_quartier = "" ;
                        }
                    }catch (Exception ex)
                    {
                        Id_quartier = "" ;
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            }) ;

            at_avenue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        Street street = streetViewModel.find(Id_quartier, at_avenue.getText().toString());
                        if (!street.getId().equals(null)) {
                            Toast.makeText(getActivity(), street.getName().toString(), Toast.LENGTH_SHORT).show();
                            Id_avenue = street.getId() ;
                        }
                        else
                        {
                            Id_avenue = "" ;
                        }
                    }catch (Exception ex)
                    {
                        Id_avenue = "" ;
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            }) ;


            enabled(false);
            charging_spinning_categorie(spin_categorie);
            charging_spinning_sexe(spin_sexe) ;
            charging_spinning_province(spin_province) ;
            ///// charging_spinning_district(spin_district) ;

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString() , Toast.LENGTH_LONG).show();
        }
    }

    public void enabled(Boolean value)
    {
        if(value.equals(true))
        {
            spin_agence.setEnabled(true);  spin_role.setEnabled(true);
            btn_valider.setVisibility(View.VISIBLE);

            at_login.setEnabled(true);    at_passe.setEnabled(true);
            at_passe2.setEnabled(true);
        }
        else
        {
            spin_agence.setEnabled(false);  spin_role.setEnabled(false);
            btn_valider.setVisibility(View.INVISIBLE);

            at_login.setEnabled(false);    at_passe.setEnabled(false);
            at_passe2.setEnabled(false);
        }
    }

    private void charging_spinning_sexe(Spinner spin)
    {

        String[] cat = { "Masculin", "Feminin" };

        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item,cat );

        spin.setAdapter(adapter_categorie);
        adapter_categorie.notifyDataSetChanged();

    }

    private void charging_spinning_categorie(Spinner spin)
    {

        String[] cat = { "Choisir...", "Agence Privée", "Agence Client" };

        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item,cat );

        spin.setAdapter(adapter_categorie);
        adapter_categorie.notifyDataSetChanged();

    }

    private void charging_spinning_role(Spinner spin)
    {

        String[] cat = {  "Administrateur du sytème ... ", "Superviseur général des affaires ... ", "Superviseur Adjoint des affaires ... " ,
                "Le responsable d'une agence ou d'un véhicule ... ", "Caissier ..." };

        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item,cat );

        spin.setAdapter(adapter_categorie);
        adapter_categorie.notifyDataSetChanged();

    }

    private void charging_spinning_agence1(Spinner spin)
    {
        try {
            List<Agence> agences = agenceViewModel.getByPrivee() ;
            String[] ag = new String[agences.size() ];  int compteur = 0 ;

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
            ///  Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_agence2(Spinner spin)
    {
        try {
            List<Agence> agences = agenceViewModel.getByCustomer(false,false) ;
            String[] ag = new String[agences.size() ];  int compteur = 0 ;

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
            ///  Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_province(Spinner spin)
    {

        try {
            List<Town> towns = townViewModel.getTownArrayListe() ;
            String[] d = new String[ towns.size()  + 1];  int compteur = 1 ;

            d[0] =  "Choisir ..." ;
            for (Town dist : towns)
            {
                d[compteur] = dist.getName() ; compteur++ ;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, d );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            ///  Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void charging_spinning_district(Spinner spin, String Id_town)
    {

        try {
            List<District> districts = districtViewModel.getByTown(Id_town) ;
            String[] d = new String[ districts.size() + 1];  int compteur = 1 ;
            d[0] = "Choisir ... ";
            for (District dist : districts)
            {
                d[compteur] = dist.getName() ; compteur++ ;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, d );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            ///  Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

    }


    private void charging_spinning_commune(Spinner spin, String Id_district)
    {

        try {
            List<Township> townships = townshipViewModel.getByDistrict(Id_district) ;
            String[] d = new String[ townships.size() + 1];  int compteur = 1 ;
            d[0] = "Choisir ... ";
            for (Township township : townships)
            {
                d[compteur] = township.getName() ; compteur++ ;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, d );

            spin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            ///  Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

    }



}


