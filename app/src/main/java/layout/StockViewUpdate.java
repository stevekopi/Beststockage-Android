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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Agence;
import cd.sklservices.com.Beststockage.Classes.Article;
import cd.sklservices.com.Beststockage.Classes.Operation;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.StockViewModel;

public class StockViewUpdate extends Fragment  {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= StockViewUpdate.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private StockViewModel stockViewModel;
    private OperationViewModel operationViewModel;
    private AgenceViewModel agenceViewModel;

    private Spinner spin_operation, spin_agence, spin_agence2, spin_article ;
    private TextView tv_agence, tv_agence2, tv_article ;
    private AutoCompleteTextView at_quantite, at_bonus, at_date  ;
    private MultiAutoCompleteTextView multi_observation ;
    private Button btn_valider ;
    private Operation operation ;

    private  Calendar calendar;
    private int jour, mois,day_of_month;
    private DatePickerDialog datePickerDialog;
    private String jours,moiss, date_operation ; //Conversion de day et month en string

    private String  spin_devise_text, id_current_role , id_current_user , id_current_agence ;
    String[][] ag ;
    String[][] at ;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Stock");
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
        rootView =inflater.inflate(R.layout.fragment_operation_add, container, false);

        id_current_user =  ((MainActivity)getActivity()).getCurrentUser().getId() ;
        id_current_agence =  ((MainActivity)getActivity()).getCurrentUser().getAgence_id() ;
        id_current_role =  ((MainActivity)getActivity()).getCurrentUser().getUser_role_id() ;


        try {

            init(rootView) ;

            spin_devise_text = "Fc" ;

            btn_valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        if(multi_observation.getText().toString().contains("@"))
                        {
                            Toast.makeText(getActivity(), "  L'observation ne doit pas contenir @  " , Toast.LENGTH_LONG).show();
                        }
                        else if(operation.getType().equals("Entree_speciale")) {

                            Boolean test1 , test2  ;
                            test1 = false ;  test2 = false ;

                            String Id_agence = "";
                            String Id_article = "";

                            if(id_current_role.equals("03"))
                            {
                                spin_agence.setVisibility(View.GONE);
                            }
                            else
                            {
                                int compteur = agenceViewModel.getAgencesArrayListe().size() ;
                                for (int i=0 ; i < compteur; i++)  {
                                    if(spin_agence.getSelectedItemId() == i) {
                                        Id_agence = ag[i][1];
                                        test1 = true;
                                    }
                                }

                            }


                            int compteur =  articleViewModel.getArticlesArrayListe().size();
                            for (int i=0 ; i < compteur; i++)  {
                                if(spin_article.getSelectedItemId() == i) {
                                    Id_article = at[i][1] ;
                                    test2 = true;
                                }
                            }

                            if(test1.equals(true) && test2.equals(true)) {
                                if (Integer.valueOf(at_quantite.getText().toString()) == 0) {
                                    Toast.makeText(getActivity(), " La quantité commandée est nulle  ", Toast.LENGTH_SHORT).show();
                                } else if (!operation.getUser_id().equals(id_current_user)) {
                                    Toast.makeText(getActivity(), " Impossible de faire une mise à jour - L'opération n'a pas été enregistrée par vous  ", Toast.LENGTH_SHORT).show();
                                } else {
                                    ////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////// Gestion de Stock //////////////////////////////////////
                                    ///////////////////////////////////////////////////////////////////////////////


                                    Operation op=new Operation(operation.getId(),id_current_user,"",Id_agence,Id_agence,
                                            Id_article,"Fc","Entree_speciale",Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString())
                                            ,0.0,date_operation,multi_observation.getText().toString(),1,0,MainActivity.getCurrentUser().getAgence_id(),MainActivity.getAddingDate(),MainActivity.getAddingDate(),0,0);


                                    operationViewModel.ajout(op);


                                    /////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////
                                    /////////////////////////////////////////////////////////////////////////////

                                    Toast.makeText(getActivity(), " Opération mise à jour  ", Toast.LENGTH_SHORT).show();
                                    ((MainActivity) getActivity()).afficherOperationView();
                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(), " L'opération a échouée   ", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else if(operation.getType().equals("Sortie")) {
                            Boolean test1 , test2, test3 ;
                            test1 = false ;  test2 = false ;   test3 = false ;

                            String Id_agence = "";    String Id_agence2 = "" ;
                            String Id_article = "";

                            if(id_current_role.equals("03"))
                            {
                                spin_agence.setVisibility(View.GONE);
                            }
                            else
                            {

                                int compteur = agenceViewModel.getAgencesArrayListe().size() ;
                                for (int i=0 ; i < compteur; i++)  {
                                    if(spin_agence.getSelectedItemId() == i) {
                                        Id_agence = ag[i][1];
                                        test1 = true;
                                    }
                                }

                            }

                            int compteur = agenceViewModel.getAgencesArrayListe().size() ;
                            for (int i=0 ; i < compteur; i++)  {
                                if(spin_agence2.getSelectedItemId() == i) {
                                    Id_agence2 = ag[i][1];
                                    test2 = true;
                                }
                            }

                            compteur =  articleViewModel.getArticlesArrayListe().size();
                            for (int i=0 ; i < compteur; i++)  {
                                if(spin_article.getSelectedItemId() == i) {
                                    Id_article = at[i][1] ;
                                    test3 = true;
                                }
                            }

                            if(test1.equals(true) && test2.equals(true) && test3.equals(true)) {
                                int qte_commande = Integer.valueOf(at_quantite.getText().toString()) + Integer.valueOf(at_bonus.getText().toString());
                                int qte_disponible = stockViewModel.Qte(Id_article);

                                if (Id_agence.equals(Id_agence2)) {
                                    Toast.makeText(getActivity(), " Veuillez selectionner une autre Agence  ", Toast.LENGTH_SHORT).show();
                                } else if (qte_commande > qte_disponible) {
                                    Toast.makeText(getActivity(), " La quantité commandé n'est pas disponible  ", Toast.LENGTH_SHORT).show();
                                } else if (qte_commande == 0) {
                                    Toast.makeText(getActivity(), " La quantité commandé est nulle  ", Toast.LENGTH_SHORT).show();
                                } else if (!operation.getUser_id().equals(id_current_user)) {
                                    Toast.makeText(getActivity(), " Impossible de faire une mise à jour - L'opération n'a pas été enregistrée par vous  "
                                            , Toast.LENGTH_SHORT).show();
                                } else {
                                    ////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////// Gestion de Stock //////////////////////////////////////
                                    /////////////////////  Sortie //////////////////////////////////////////////////////////

                                    List<Operation> before = operationViewModel.getOperationWhere(operation.getUser_id(),
                                            operation.getAgence_2_id(), operation.getAgence_1_id(), operation.getArticle_id(),
                                            "Entree", operation.getQuantite(), operation.getBonus(),
                                            operation.getDate(),
                                            operation.getSync_pos());

                                    Boolean test_insert = false;

                                    for (Operation myop : before) {


                                        Operation op=new Operation(operation.getId(),id_current_user,"",Id_agence,Id_agence2,
                                                Id_article,"Fc","Sortie",Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString())
                                                ,0.0,date_operation,multi_observation.getText().toString(),0,0,MainActivity.getCurrentUser().getAgence_id(),MainActivity.getAddingDate(),MainActivity.getAddingDate(),0,0);


                                        operationViewModel.ajout(op);



                                        Operation op2=new Operation(myop.getId(),id_current_user,"",Id_agence2,Id_agence,
                                                Id_article,"Fc","Entree",Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString())
                                                ,0.0,date_operation,multi_observation.getText().toString(),0,0,myop.getChecking_agence_id(),myop.getAdding_date(),MainActivity.getAddingDate(),0,myop.getPos()+1);


                                        operationViewModel.ajout(op2);

                                        test_insert = true;
                                    }

                                    if (test_insert.equals(false)) {
                                        Toast.makeText(getActivity(), "Erreur lors de la mise à jour ...  ", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), " Opération mise à jour  ", Toast.LENGTH_SHORT).show();
                                    }

                                    /////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////
                                    /////////////////////////////////////////////////////////////////////////////


                                    ((MainActivity) getActivity()).afficherOperationView();
                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(), " L'opération a échouée   ", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else if(operation.getType().equals("Vente_detail")) {
                            Boolean test1 , test2, test3 ;
                            test1 = false ;  test2 = false ;   test3 = false ;

                            String Id_agence = "";    String Id_agence2 = "" ;
                            String Id_article = "";   Double prix = 0.0 ;

                            if(id_current_role.equals("03"))
                            {
                                spin_agence.setVisibility(View.GONE);
                            }
                            else
                            {
                                int compteur = agenceViewModel.getAgencesArrayListe().size() ;
                                for (int i=0 ; i < compteur; i++)  {
                                    if(spin_agence.getSelectedItemId() == i) {
                                        Id_agence = ag[i][1];
                                        test1 = true;
                                    }
                                }

                            }

                            int compteur = agenceViewModel.getAgencesArrayListe().size() ;
                            for (int i=0 ; i < compteur; i++)  {
                                if(spin_agence2.getSelectedItemId() == i) {
                                    Id_agence2 = ag[i][1];
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

                            if(test1.equals(true) && test2.equals(true) && test3.equals(true)) {
                                int qte_commande = Integer.valueOf(at_quantite.getText().toString());
                                int qte_disponible = stockViewModel.Qte(Id_article);

                                if (Id_agence.equals(Id_agence2)) {
                                    Toast.makeText(getActivity(), " Veuillez selectionner une autre Agence  ", Toast.LENGTH_SHORT).show();
                                } else if (qte_commande > qte_disponible) {
                                    Toast.makeText(getActivity(), " La quantité commandée n'est pas disponible  ", Toast.LENGTH_SHORT).show();
                                } else if (spin_devise_text.equals("---")) {
                                    Toast.makeText(getActivity(), " Veuillez selectionner la monaie  ", Toast.LENGTH_SHORT).show();
                                } else if (qte_commande == 0) {
                                    Toast.makeText(getActivity(), " La quantité commandée est nulle  ", Toast.LENGTH_SHORT).show();
                                } else if (!operation.getUser_id().equals(id_current_user)) {
                                    Toast.makeText(getActivity(), " Impossible de faire une mise à jour - L'opération n'a pas été enregistrée par vous  "
                                            , Toast.LENGTH_SHORT).show();
                                } else {


                                    List<Operation> before = operationViewModel.getOperationWhere(operation.getUser_id(),
                                            operation.getAgence_2_id(), operation.getAgence_1_id(), operation.getArticle_id(),
                                            "Achat", operation.getQuantite(), 0, operation.getDate(),
                                            operation.getSync_pos());

                                    Boolean test_insert = false;

                                    for (Operation myop : before) {
                                        ////////////////////////////////////////////////////////////////////////////////
                                        ////////////////////// Gestion de Stock //////////////////////////////////////
                                        ////////////////////  Insertion de la vente /////////////////////////////////////////////////////////

                                       Operation op=new Operation(operation.getId(),id_current_user,"",Id_agence,Id_agence2,
                                                Id_article,spin_devise_text,"Vente_detail",Integer.valueOf(at_quantite.getText().toString()),0
                                                ,prix,date_operation,multi_observation.getText().toString(),0,0,operation.getChecking_agence_id(),operation.getAdding_date(),MainActivity.getAddingDate(),0,operation.getPos()+1);


                                        operationViewModel.ajout(op);

                                        /////////////////////////////////////////////////////////////////////////////
                                        ////////////////////////////////////////////////////////////////////////////

                                        Operation op2=new Operation(myop.getId(),id_current_user,"",Id_agence2,Id_agence,
                                                Id_article,spin_devise_text,"Achat",Integer.valueOf(at_quantite.getText().toString()),0
                                                ,prix,date_operation,multi_observation.getText().toString(),0,0,myop.getChecking_agence_id(),myop.getAdding_date(),myop.getAdding_date(),0,myop.getPos()+1);


                                        operationViewModel.ajout(op2);

                                        test_insert = true;
                                    }

                                    if (test_insert.equals(false)) {
                                        Toast.makeText(getActivity(), "Erreur lors de la mise à jour ...  ", Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(getActivity(), " Opération mise à jour ...  ", Toast.LENGTH_SHORT).show();
                                    ((MainActivity) getActivity()).afficherOperationView();
                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(), " L'opération a échouée   ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(operation.getType().equals("Vente_avec_bonus"))
                        {
                            Boolean test1 , test2, test3 ;
                            test1 = false ;  test2 = false ;   test3 = false ;
                            String Id_agence = "";    String Id_agence2 = "" ;
                            String Id_article = "";   Double prix = 0.0 ;

                            if(id_current_role.equals("03"))
                            {
                                spin_agence.setVisibility(View.GONE);
                            }
                            else
                            {

                                int compteur = agenceViewModel.getAgencesArrayListe().size() ;
                                for (int i=0 ; i < compteur; i++)  {
                                    if(spin_agence.getSelectedItemId() == i) {
                                        Id_agence = ag[i][1];
                                        test1 = true;
                                    }
                                }


                            }


                            int compteur = agenceViewModel.getAgencesArrayListe().size() ;
                            for (int i=0 ; i < compteur; i++)  {
                                if(spin_agence2.getSelectedItemId() == i) {
                                    Id_agence2 = ag[i][1];
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

                            if(test1.equals(true) && test2.equals(true) && test3.equals(true)) {
                                int qte_commande = Integer.valueOf(at_quantite.getText().toString()) +
                                        Integer.valueOf(at_bonus.getText().toString());
                                int qte_disponible = stockViewModel.Qte(Id_article);

                                if (Id_agence.equals(Id_agence2)) {
                                    Toast.makeText(getActivity(), " Veuillez selectionner une autre Agence  ", Toast.LENGTH_SHORT).show();
                                } else if (qte_commande > qte_disponible) {
                                    Toast.makeText(getActivity(), " La quantité commandé n'est pas disponible  ", Toast.LENGTH_SHORT).show();
                                } else if (spin_devise_text.equals("---")) {
                                    Toast.makeText(getActivity(), " Veuillez selectionner la monaie  ", Toast.LENGTH_SHORT).show();
                                } else if (qte_commande == 0) {
                                    Toast.makeText(getActivity(), " La quantité commandée est nulle  ", Toast.LENGTH_SHORT).show();
                                } else {

                                    List<Operation> before = operationViewModel.getOperationWhere(operation.getUser_id(),
                                            operation.getAgence_2_id(), operation.getAgence_1_id(), operation.getArticle_id(),
                                            "Achat", operation.getQuantite(), operation.getBonus(), operation.getDate(),
                                            operation.getSync_pos());

                                    Boolean test_insert = false;

                                    for (Operation myop : before) {
                                        ////////////////////////////////////////////////////////////////////////////////
                                        ////////////////////// Gestion de Stock //////////////////////////////////////
                                        ////////////////////  Insertion de la vente /////////////////////////////////////////////////////////


                                        Operation op=new Operation(operation.getId(),id_current_user,"",Id_agence,Id_agence2,
                                                Id_article,spin_devise_text,"Vente_avec_bonus",Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString())
                                                ,0.0,date_operation,multi_observation.getText().toString(),1,0,operation.getChecking_agence_id(),operation.getAdding_date(),MainActivity.getAddingDate(),0,operation.getPos()+1);


                                        operationViewModel.ajout(op);

                                        /////////////////////////////////////////////////////////////////////////////
                                        ////////////////////////////////////////////////////////////////////////////

                                        Operation op2=new Operation(myop.getId(),id_current_user,"",Id_agence2,Id_agence,
                                                Id_article,spin_devise_text,"Achat",Integer.valueOf(at_quantite.getText().toString()),Integer.valueOf(at_bonus.getText().toString())
                                                ,prix,date_operation,multi_observation.getText().toString(),1,1,myop.getChecking_agence_id(),myop.getAdding_date(),MainActivity.getAddingDate(),0,myop.getPos()+1);


                                        operationViewModel.ajout(op2);

                                        test_insert = true;
                                    }

                                    if (test_insert.equals(false)) {
                                        Toast.makeText(getActivity(), "Erreur lors de la mise à jour ...  ", Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(getActivity(), " Opération mise à jour ...  ", Toast.LENGTH_SHORT).show();
                                    ((MainActivity) getActivity()).afficherOperationView();

                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(), " L'opératuon a échouée  ", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(getActivity(), " Impossible d'enregistrer l'operation  : " , Toast.LENGTH_SHORT).show();
                        }


                    }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(), " Prière de saisir tous les champs obligatoire " + ex.toString(), Toast.LENGTH_LONG).show();
                }

                }
            });

            if(operation.getType().equals("Entree_speciale"))
            {
                entree_special(rootView);
            }
            else if(operation.getType().equals("Sortie"))
            {
                sortie(rootView);
            }
            else if(operation.getType().equals("Vente_detail"))
            {
                vente_detail(rootView);
            }
            else if(operation.getType().equals("Vente_avec_bonus"))
            {
                vente_bonus(rootView);
            }
            else
            {
                Toast.makeText(getActivity(), " Impossible de mettre à jour l'opération  ", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).afficherOperationView() ;
            }


        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

        return rootView;
    }


    private void entree_special(View v) {

        try {


            spin_operation.setVisibility(View.GONE);
            spin_agence2.setVisibility(View.GONE);
            spin_article.setVisibility(View.VISIBLE);
            spin_article.setEnabled(true);
            tv_agence.setVisibility(View.VISIBLE);
            tv_agence.setEnabled(true);
            spin_agence.setVisibility(View.VISIBLE);
            spin_agence.setEnabled(true);
            tv_agence2.setVisibility(View.GONE);
            tv_article.setVisibility(View.VISIBLE);
            tv_article.setEnabled(true);
            at_quantite.setVisibility(View.VISIBLE);
            at_quantite.setEnabled(true);
            at_quantite.setText(String.valueOf(operation.getQuantite())) ;
            at_bonus.setVisibility(View.GONE);


            if(id_current_role.equals("03"))
            {
                spin_agence.setVisibility(View.GONE);
            }
            else
            {
                charging_spinning_agence(spin_agence, 1);
            }

            charging_spinning_article(spin_article);

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void sortie(View v)
    {
        try {


            spin_operation.setVisibility(View.GONE);
            spin_agence2.setVisibility(View.VISIBLE);
            spin_agence2.setEnabled(true);
            spin_article.setVisibility(View.VISIBLE);
            spin_article.setEnabled(true);
            tv_agence.setVisibility(View.VISIBLE);
            tv_agence.setEnabled(true);
            spin_agence.setVisibility(View.VISIBLE);
            spin_agence.setEnabled(true);
            tv_agence2.setVisibility(View.VISIBLE);
            tv_agence2.setEnabled(true);
            tv_article.setVisibility(View.VISIBLE);
            tv_article.setEnabled(true);
            at_quantite.setVisibility(View.VISIBLE);
            at_quantite.setText(String.valueOf(operation.getQuantite())) ;
            at_quantite.setEnabled(true);
            at_bonus.setVisibility(View.VISIBLE);
            at_bonus.setText(String.valueOf(operation.getBonus())) ;
            at_bonus.setEnabled(true);


            if(id_current_role.equals("03"))
            {
                spin_agence.setVisibility(View.GONE);
            }
            else
            {
                charging_spinning_agence(spin_agence, 1);
            }

            charging_spinning_agence(spin_agence2, 2);
            charging_spinning_article(spin_article);

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void vente_detail(View v)
    {
        try {


            spin_operation.setVisibility(View.GONE);
            spin_agence2.setVisibility(View.VISIBLE);
            spin_agence2.setEnabled(true);
            spin_article.setVisibility(View.VISIBLE);
            spin_article.setEnabled(true);
            tv_agence.setVisibility(View.VISIBLE);
            tv_agence.setEnabled(true);
            spin_agence.setVisibility(View.VISIBLE);
            spin_agence.setEnabled(true);
            tv_agence2.setVisibility(View.VISIBLE);
            tv_agence2.setEnabled(true);
            tv_article.setVisibility(View.VISIBLE);
            tv_article.setEnabled(true);
            at_quantite.setVisibility(View.VISIBLE);
            at_quantite.setText(String.valueOf(operation.getQuantite())) ;
            at_quantite.setEnabled(true);
            at_bonus.setVisibility(View.GONE);
            at_bonus.setEnabled(false);

            if(id_current_role.equals("03"))
            {
                spin_agence.setVisibility(View.GONE);
            }
            else
            {
                charging_spinning_agence(spin_agence, 1);
            }

            charging_spinning_agence(spin_agence2, 2);
            charging_spinning_article(spin_article);

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void vente_bonus(View v)
    {
        try {


            spin_operation.setVisibility(View.GONE);
            spin_agence2.setVisibility(View.VISIBLE);
            spin_agence2.setEnabled(true);
            spin_article.setVisibility(View.VISIBLE);
            spin_article.setEnabled(true);
            tv_agence.setVisibility(View.VISIBLE);
            tv_agence.setEnabled(true);
            spin_agence.setVisibility(View.VISIBLE);
            spin_agence.setEnabled(true);
            tv_agence2.setVisibility(View.VISIBLE);
            tv_agence2.setEnabled(true);
            tv_article.setVisibility(View.VISIBLE);
            tv_article.setEnabled(true);
            at_quantite.setVisibility(View.VISIBLE);
            at_quantite.setEnabled(true);
            at_quantite.setText(String.valueOf(operation.getQuantite())) ;
            at_bonus.setVisibility(View.VISIBLE);
            at_bonus.setEnabled(true);
            at_bonus.setText(String.valueOf(operation.getBonus())) ;

            if(id_current_role.equals("03"))
            {
                spin_agence.setVisibility(View.GONE);
            }
            else
            {
                charging_spinning_agence(spin_agence, 1);
            }

            charging_spinning_agence(spin_agence2, 2);
            charging_spinning_article(spin_article);

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void charging_spinning_agence(Spinner spin, int type_agence)
    {
        try {
            List<Agence> agences = agenceViewModel.getAgencesArrayListe() ;
            ag = new String[agences.size()][3];
            String[] ag2 = new String[agences.size()];      int compteur = 1 ;

            for (Agence agence : agences)
            {
               if(type_agence == 1 && operation.getAgence_1_id().equals(agence.getId()))
                {
                   ag[0][0] =  agence.getDenomination() ;
                   ag2[0] =  agence.getDenomination() ;
                   ag[0][1] =  agence.getId() ;
                   ag[0][2] =  "1" ;
                }
               else if(type_agence == 2 && operation.getAgence_2_id().equals(agence.getId()))
                {
                   ag[0][0] =  agence.getDenomination() ;
                    ag2[0] =  agence.getDenomination() ;
                    ag[0][1] =  agence.getId() ;
                    ag[0][2] =  "2" ;
                }
               else
                {
                   ag[compteur][0] = agence.getDenomination() ;
                    ag2[compteur] =  agence.getDenomination() ;
                    ag[compteur][1] =  agence.getId() ;

                    if(type_agence == 2)
                    {
                        ag[compteur][2] =  "2" ;
                    }
                    else
                    {
                        ag[compteur][2] =  "1" ;
                    }

                    compteur++ ;
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

    private void charging_spinning_article(Spinner spin)
    {
        List<Article> articles = articleViewModel.getArticlesArrayListe() ;
        at = new String[articles.size()][3];
        String[] at2 = new String[articles.size()];      int compteur = 1 ;

        for (Article article : articles)
        {
            if(operation.getArticle_id().equals(article.getId()))
            {
                at[0][0] =  article.getDesignation() ;
                at2[0] =  article.getDesignation() ;
                at[0][1] =  article.getId() ;

            }
            else
            {
                at[compteur][0] =  article.getDesignation() ;
                at2[compteur] =  article.getDesignation();
                at[compteur][1] =  article.getId() ;     compteur++ ;
            }

        }

        ArrayAdapter<String> adapter_article = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_gallery_item, at2 );

        spin.setAdapter(adapter_article);
        adapter_article.notifyDataSetChanged();
    }

    private void init(View v)
    {
        try {
            this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class);
            this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
            this.stockViewModel = new ViewModelProvider(this).get(StockViewModel.class);
            this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class);


            operation = operationViewModel.getOperation() ;



            spin_operation.setEnabled(true);
            spin_agence.setEnabled(false);
            spin_agence2.setEnabled(false);
            spin_article.setEnabled(false);
            tv_agence.setEnabled(false);
            tv_agence2.setEnabled(false);
            tv_article.setEnabled(false);
            at_quantite.setEnabled(false);
            at_bonus.setEnabled(false);

            at_date.setText(operation.getDate());

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
                            date_operation = year + "-" + (moiss) + "-" + jours;
                            at_date.setText(jours + "/" + (moiss) + "/" + year);

                        }
                    }, jour, mois, day_of_month);
                    datePickerDialog.show();

                }
            });

            date_operation = operation.getDate() ;


            multi_observation.setText(operation.getObservation().toString());

        }
        catch (Exception ex)
        {
            multi_observation.setText("R.A.S");
        }

    }

}


