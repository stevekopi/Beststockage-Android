package cd.sklservices.com.Beststockage.layout.Stocks;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Transaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Registres.AgenceAdapter;
import cd.sklservices.com.Beststockage.Adapters.Registres.ArticleAdapter;
import cd.sklservices.com.Beststockage.Adapters.Stocks.SpinOperationTypeAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.Cloud.Stocks.SyncOperation;
import cd.sklservices.com.Beststockage.Cloud.Finances.SyncOperationFinance;
import cd.sklservices.com.Beststockage.Outils.Converter;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Repository.Finances.OperationFinanceRepository;
import cd.sklservices.com.Beststockage.Repository.Stocks.OperationRepository;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Finances.OperationFinanceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.OperationViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.StockViewModel;

public class OperationUpdate extends Fragment {

    private final static String COMMON_TAG = "Orientation Change ";
    private final static String FRAGMENT_NAME = OperationUpdate.class.getSimpleName();
    private final static String TAG = FRAGMENT_NAME;

    private ArticleViewModel articleViewModel;
    private StockViewModel stockViewModel;
    private OperationViewModel operationViewModel;
    private AgenceViewModel agenceViewModel;

    private Spinner spin_type;
    private AutoCompleteTextView  at_bonus;
    private MultiAutoCompleteTextView mact_observation;
    private TextInputLayout til_bonus;
    private EditText et_quantite;

    private String spin_devise_text;

    private Boolean btn_insert_stock = false;

    public Operation current_instance,old_instance,old_second_instance;

    private ArrayAdapter<String> spin_operation_type_adapter;

    private LinearLayout ll_agence_ben,ll_article;
    private TextView tv_agence_name;
    private TextView tv_agence_proprietaire;
    private TextView tv_article_description;
    private TextView tv_article_fournisseur;
    private TextView tv_stock_count;
    SearchView sv_agences_filter;
    SearchView sv_articles_filter;

    List<Agence> listAgences;
    List<Article> listArticles;

    String indexAgence ;
    AgenceAdapter adapterAgence ;
    int count_all_agences;
    public boolean isAgenceLoading=false;
    public Handler myHandler;
    public View footer;

    static SwipeRefreshLayout swipeRefreshLayoutAgences;
    static SwipeRefreshLayout swipeRefreshLayoutArticles;
    TextView tv_agences_count,tv_montant;
    TextView tv_articles_count,tv_date;

    ListView lv_agences;
    ListView lv_articles;

    private Button btn_date_select;
    DatePickerDialog datePickerDialog;
    int jour, mois,day_of_month;
    String jours,moiss; //Conversion de day et month en string
    Calendar calendar;

    View rootView;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(COMMON_TAG, "Fragment Operation Add SaveInstance");
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, FRAGMENT_NAME + " onAttache");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, FRAGMENT_NAME + " onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Stock");
        Log.i(TAG, FRAGMENT_NAME + " onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, FRAGMENT_NAME + " onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, FRAGMENT_NAME + " onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, FRAGMENT_NAME + " onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, FRAGMENT_NAME + " onCreateView");
        // Inflate the layout for this fragment



        rootView = inflater.inflate(R.layout.fragment_operation_add, container, false);

        LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        footer= li.inflate(R.layout.footer_view,null);
        myHandler=new MyHandler();

        spin_devise_text = "Fc";

       // spin_agence_ben = (Spinner)  rootView.findViewById(R.id.spin_operation_agence_ben);

        spin_type=(Spinner) rootView.findViewById(R.id.spin_operation_type);
        charging_spinning_type_operation();

        et_quantite = rootView.findViewById(R.id.at_operation_quantite);
        at_bonus = rootView.findViewById(R.id.at_operation_bonus);
        at_bonus.setEnabled(false);
        mact_observation = rootView.findViewById(R.id.mact_operation_observation);
        til_bonus=rootView.findViewById(R.id.til_operation_bonus);
        ll_agence_ben=rootView.findViewById(R.id.ll_operation_add_agence_ben);
        ll_article=rootView.findViewById(R.id.ll_operation_add_article);

        tv_agence_name=ll_agence_ben.findViewById(R.id.tv_operation_add_agence_name);
        tv_agence_proprietaire=ll_agence_ben.findViewById(R.id.tv_operation_add_agence_proprietaire);

        tv_article_description=ll_article.findViewById(R.id.tv_operation_add_article_description);
        tv_article_fournisseur=ll_article.findViewById(R.id.tv_operation_add_article_fournisseur);

        tv_montant=rootView.findViewById(R.id.tv_operation_add_montant);

        tv_stock_count=rootView.findViewById(R.id.tv_operation_add_stock_count);
        tv_date=rootView.findViewById(R.id.tv_operation_add_date);
        btn_date_select=rootView.findViewById(R.id.btn_operation_add_date);

        btn_date_select.setOnClickListener(new View.OnClickListener() {
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
                        current_instance.setDate(year + "-" + moiss + "-" + jours);
                        tv_date.setText(MesOutils.Get_date_en_francais(current_instance.getDate()));

                    }
                }, jour, mois, day_of_month);
                datePickerDialog.show();

            }
        });


        at_bonus.setEnabled(true);
        et_quantite.setEnabled(true);

        //DEBUT AFFECTATION DES VALEURS
        current_instance=new OperationViewModel(getActivity().getApplication()).get(current_instance.getId(),true,true);
        old_instance=new OperationViewModel(getActivity().getApplication()).get(current_instance.getId(),true,true);

        current_instance.setOperationFinance(new OperationFinanceViewModel(getActivity().getApplication()).get(current_instance.getOperation_finance_id(),false,false));
        old_second_instance=new OperationViewModel(getActivity().getApplication()).getOtherOperation(old_instance);
        old_second_instance=new OperationViewModel(getActivity().getApplication()).get(old_second_instance.getId(),true,true);

        spin_type.setSelection(spin_operation_type_adapter.getPosition(Converter.setTypeOperation(current_instance.getType())));
        tv_date.setText(MesOutils.Get_date_en_francais(current_instance.getDate()));
        tv_agence_name.setText(current_instance.getAgenceBeneficiaire().getType()+" "+current_instance.getAgenceBeneficiaire().getDenomination());
        tv_agence_proprietaire.setText("De : "+current_instance.getAgenceBeneficiaire().getProprietaire().getName());
        tv_article_description.setText(current_instance.getArticle().getDescription());
        tv_article_fournisseur.setText(new FournisseurViewModel(getActivity().getApplication()).get(current_instance.getArticle().getFournisseur_id(),true).getIdentity().getName());
        et_quantite.setText(String.valueOf(current_instance.getQuantite()));
        at_bonus.setText(String.valueOf(current_instance.getBonus()));
        mact_observation.setText(current_instance.getObservation());
        //FIN AFFECTATION DES VALEURS

        et_quantite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               try{
                   if(current_instance.getArticle()==null)
                   {
                       Toast.makeText(getActivity(), " Choisissez d'abord l'article", Toast.LENGTH_SHORT).show();

                   }
               }
               catch (Exception e){

               }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()>0)
                {
                    current_instance.setQuantite(Integer.valueOf(charSequence.toString()));
                }
                else {
                    current_instance.setQuantite(0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
               try{
                   if(String.valueOf(editable.toString()).length()>0){
                      calculMontant();
                   }
                   else{
                       current_instance.setMontant(0.0);
                       tv_montant.setText("Montant : 0 Fc");
                   }
               }
               catch (Exception e){

               }
            }
        });

        Button btn_valider = rootView.findViewById(R.id.bt_operation_save);



        init();


        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(spin_type.getSelectedItem()).contains("Choisir"))
                    {
                        if(String.valueOf(spin_type.getSelectedItem()).toLowerCase(Locale.ROOT).contains("vente") && current_instance.getAgenceBeneficiaire().getAppartenance().toLowerCase().contains("privee")){
                            Toast.makeText(getActivity(), " Vous ne pouvez pas vendre à une agence privée ", Toast.LENGTH_SHORT).show();
                        }else{
                            updateItem();
                        }
                }
                else
                    {
                    Toast.makeText(getActivity(), " Veuillez choisir le type d'operation ", Toast.LENGTH_SHORT).show();
                    }

            }
        });

        spin_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String item = spin_operation_type_adapter.getItem(position);
                    current_instance.setType(Converter.getTypeOperation(item));
                    if (current_instance.getType().contains("detail")){
                        withoutBonus();
                    }
                    else{
                        if (!current_instance.getType().toLowerCase(Locale.ROOT).contains("choisir"))
                                withBonus();
                    }
                    calculMontant();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        ll_agence_ben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_instance.getType().toLowerCase().contains("choisir")){
                    Toast.makeText(getActivity(), " Choisissez d'abord le type de l'operation", Toast.LENGTH_SHORT).show();
                }
                else{
                    showCustomAlertDialogBoxForAgenceList();
                }

            }
        });

        ll_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_instance.getType().toLowerCase().contains("choisir")){
                    Toast.makeText(getActivity(), " Choisissez d'abord le type de l'operation", Toast.LENGTH_SHORT).show();
                    et_quantite.setEnabled(false);
                    at_bonus.setEnabled(false);
                }
                else{
                    showCustomAlertDialogBoxForArticleList();
                    et_quantite.setEnabled(true);
                    at_bonus.setEnabled(true);
                }

            }
        });




        return rootView;
    }

    private void calculMontant() {
        if(current_instance.getArticle()!=null && current_instance.getQuantite()>0){
            if(current_instance.getType().equals("Vente_avec_bonus")){
                current_instance.setMontant(current_instance.getArticle().getPrix() * current_instance.getQuantite());
            }
            else if(current_instance.getType().equals("Vente_detail")){
                current_instance.setMontant(current_instance.getArticle().getPrix_detail() * current_instance.getQuantite());
            }
            else
            {
                current_instance.setMontant(0.0);
                current_instance.setOperation_finance_id(MainActivity.DefaultFinancialKey);
            }
            tv_montant.setText(String.valueOf("Montant : "+ MesOutils.spacer(String.valueOf(current_instance.getMontant().intValue())) +" Fc"));
        }
        else{
            tv_montant.setText(String.valueOf("Montant : 0 Fc"));
        }


    }


    @Transaction
    private void updateItem(){

        current_instance.setQuantite(Integer.valueOf(et_quantite.getText().toString()));
        current_instance.setBonus(at_bonus.getText().toString().length()>0?Integer.valueOf(at_bonus.getText().toString()):0);
        current_instance.setObservation(mact_observation.getText().toString());
        calculMontant();

        Agence agenceBen=new AgenceViewModel(getActivity().getApplication()).get(current_instance.getAgence_2_id(),false,false);

        int is_confirmed=0;
        if(agenceBen.getAppartenance().equals("Client")){
            current_instance.setIs_confirmed(1);
        }
        else{
            current_instance.setIs_confirmed(current_instance.getType().equals("Sortie")?0:1);
        }

        current_instance.setUpdated_date(MainActivity.getAddingDate());
        current_instance.setSync_pos(0);
        current_instance.setPos(current_instance.getPos()+1);

        if(checkStockExist()){

            String type=current_instance.getType().contains("Vente")?"Achat":"Entree";

            is_confirmed=0;
            int is_checked=0;
            if(agenceBen.getAppartenance().equals("Privee")){
                is_confirmed=current_instance.getType().equals("Sortie")?0:1;
            }
            else
            {
                is_confirmed=1;
                is_checked=1;
            }

            old_second_instance.setUser_id(current_instance.getUser_id());
            old_second_instance.setAgence_1_id(current_instance.getAgence_2_id());
            old_second_instance.setAgence_2_id(current_instance.getAgence_1_id());
            old_second_instance.setArticle_id(current_instance.getArticle_id());
            old_second_instance.setQuantite(current_instance.getQuantite());
            old_second_instance.setBonus(current_instance.getBonus());
            old_second_instance.setObservation(current_instance.getObservation());
            old_second_instance.setIs_confirmed(is_confirmed);
            old_second_instance.setUpdated_date(current_instance.getUpdated_date());
            old_second_instance.setSync_pos(current_instance.getSync_pos());
            old_second_instance.setPos(old_second_instance.getPos()+1);
            old_second_instance.setDevise_id(current_instance.getDevise_id());
            old_second_instance.setMontant(current_instance.getMontant());
            old_second_instance.setIs_checked(is_checked);
            old_second_instance.setType(type);

            if( operationViewModel.update(current_instance,old_second_instance)==1)
            {
                new SyncOperationFinance(new OperationFinanceRepository(getContext())).sendPost();
                new SyncOperation(new OperationRepository(getContext())).sendPost();
                Toast.makeText(getActivity(), " Succès", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).afficherOperationView();
            }
            else {
                Toast.makeText(getActivity(), " Echec", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(getActivity(), " Echec", Toast.LENGTH_SHORT).show();
        }

    }


    // Pass list of your model as arraylist

    private void showCustomAlertDialogBoxForAgenceList() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.agences_list);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
        }
        lv_agences = dialog.findViewById(R.id.lv_agences_list_agences);

        TextView tv = dialog.findViewById(R.id.tv_popup_title);
        sv_agences_filter = dialog.findViewById(R.id.sv_agences_list_filter);
        tv_agences_count =dialog.findViewById(R.id.tv_agences_list_count);
        swipeRefreshLayoutAgences =dialog.findViewById(R.id.refresh_list_agences);

        swipeRefreshLayoutAgences.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                try{
                    showCustomAlertDialogBoxForAgenceList();
                }
                catch (Exception e){

                }
                finally {
                    swipeRefreshLayoutAgences.setRefreshing(false);
                }
            }

        });

        tv.setText("Choisir l'agence");

        fillData();


        adapterAgence =new AgenceAdapter(getContext(), listAgences,false);
        lv_agences.setAdapter(adapterAgence);

        lv_agences.setOnItemClickListener((adapterView, view, which, l) -> {
            // Log.d(TAG, "showAssignmentsList: " + allUsersList.get(which).getUserId());
            // TODO : Listen to click callbacks at the position
            current_instance.setAgenceBeneficiaire(new AgenceViewModel(getActivity().getApplication()).get(((Agence)adapterAgence.getItem(which)).getId(),false,true));
            current_instance.setAgence_2_id(current_instance.getAgenceBeneficiaire().getId());
            tv_agence_name.setText(current_instance.getAgenceBeneficiaire().getType()+" "+current_instance.getAgenceBeneficiaire().getDenomination());
            tv_agence_proprietaire.setText("De : "+current_instance.getAgenceBeneficiaire().getProprietaire().getName());
            dialog.cancel();
        });

        sv_agences_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Recherche",query);
                count_element_agences_search();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ArrayList<Agence> result = new ArrayList<>();
                    for (Agence x : listAgences) {
                        if ((x.getType().toLowerCase() + " " + x.getDenomination().toLowerCase()+ " " + x.getProprietaire().getName().toLowerCase()).contains(newText.toLowerCase())) {
                            result.add(x);
                        }
                    }
                    adapterAgence.update(result);
                    count_element_agences_search();
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        count_element_agences();
        dialog.show();
    }

    private  void count_element_agences_search() {
        tv_agences_count.setText("Agences ("+lv_agences.getCount()+"/"+count_all_agences+")");
        swipeRefreshLayoutAgences.setRefreshing(false);
    }

    public void fillData()
    {
        try {
            listAgences = agenceViewModel.getLoading() ;
            List<Agence>  agenceList = new ArrayList<>();
            agenceList.clear();

            for (int k = 0; k< listAgences.size(); k++){

                agenceList.add(listAgences.get(k));
                indexAgence = listAgences.get(k).getDenomination() ;

            }

            count_element_agences();

            isAgenceLoading=true;
            Thread thread=new ThreadGetMoreData();
            thread.start();

            swipeRefreshLayoutAgences.setRefreshing(false);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //inserer le view de loading pendant la recherche
                    lv_agences.addFooterView(footer);
                    break;
                case 1:
                    //update data adapter and UI
                    adapterAgence.addItem((List <Agence>)msg.obj);
                    lv_agences.removeFooterView(footer);
                    isAgenceLoading=false;
                    break;
                default:
                    break;
            }

        }
    }

    public class ThreadGetMoreData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            List<Agence> instances = agenceViewModel.getLoading2(indexAgence);

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message msg=myHandler.obtainMessage(1,instances);
            myHandler.sendMessage(msg);
            count_element_agences();

        }
    }

    private void showCustomAlertDialogBoxForArticleList() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.articles_list);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
        }
        lv_articles = dialog.findViewById(R.id.lv_articles_list_articles);
        TextView tv = dialog.findViewById(R.id.tv_popup_title);
        sv_articles_filter = dialog.findViewById(R.id.sv_articles_list_filter);
        tv_articles_count =dialog.findViewById(R.id.tv_articles_list_count);
        swipeRefreshLayoutArticles =dialog.findViewById(R.id.refresh_list_articles);

        swipeRefreshLayoutArticles.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
               try{

                   showCustomAlertDialogBoxForArticleList();
               }
               catch (Exception e){

               }
               finally {
                   swipeRefreshLayoutArticles.setRefreshing(false);

               }

            }

        });


        tv.setText("Choisir l'article");


        listArticles = articleViewModel.getArticlesArrayListe();

        ArticleAdapter adapterArticle =new ArticleAdapter(getContext(), listArticles,false);
        lv_articles.setAdapter(adapterArticle);
        lv_articles.setOnItemClickListener((adapterView, view, which, l) -> {
            // Log.d(TAG, "showAssignmentsList: " + allUsersList.get(which).getUserId());
            // TODO : Listen to click callbacks at the position
            current_instance.setArticle(new ArticleViewModel(getActivity().getApplication()).get(((Article)adapterArticle.getItem(which)).getId(),false,true));
            current_instance.setArticle_id(current_instance.getArticle().getId());
            tv_article_description.setText(current_instance.getArticle().getDescription());
            tv_article_fournisseur.setText("De : "+new FournisseurViewModel(getActivity().getApplication()).get(current_instance.getArticle().getFournisseur_id(),true).getIdentity().getName());
            int qte_disponible = stockViewModel.Qte(current_instance.getArticle_id());

            tv_stock_count.setText(String.valueOf(qte_disponible+old_instance.getQuantite()+old_instance.getBonus()));


            dialog.cancel();
        });

        sv_articles_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Recherche",query);
                count_element_articles();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ArrayList<Article> result = new ArrayList<>();
                    for (Article x : listArticles) {
                        if ((x.getDescription().toLowerCase()).contains(newText.toLowerCase())) {
                            result.add(x);
                        }
                    }
                    ((ArticleAdapter) lv_articles.getAdapter()).update(result,swipeRefreshLayoutArticles);
                    count_element_articles();
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        count_element_articles();
        dialog.show();
    }

    private  void count_element_agences() {
        tv_agences_count.setText("Agences ("+lv_agences.getCount()+")");
        swipeRefreshLayoutAgences.setRefreshing(false);
    }

    private  void count_element_articles() {
        tv_articles_count.setText("Articles ("+lv_articles.getCount()+")");
       swipeRefreshLayoutArticles.setRefreshing(false);
    }


    boolean checkStockExist(){
            int qte_commande=0;

        if(at_bonus.getText().toString().length()>0){
            qte_commande = Integer.valueOf(et_quantite.getText().toString()) +
                    Integer.valueOf(at_bonus.getText().toString());
        }
        else {
            qte_commande = Integer.valueOf(et_quantite.getText().toString());
        }

            int qte_disponible = stockViewModel.Qte(current_instance.getArticle_id()) + old_instance.getQuantite()+old_instance.getBonus();


        if (current_instance.getAgence_1_id().equals(current_instance.getAgence_2_id())) {
                Toast.makeText(getActivity(), " Veuillez selectionner une autre Agence  ", Toast.LENGTH_SHORT).show();
            } else if (qte_commande > qte_disponible) {
                Toast.makeText(getActivity(), " En stock : "+qte_disponible, Toast.LENGTH_SHORT).show();
                return false;
            } else if (spin_devise_text.equals("---")) {
                Toast.makeText(getActivity(), " Veuillez selectionner la monnaie  ", Toast.LENGTH_SHORT).show();
            } else if (qte_commande == 0) {
                Toast.makeText(getActivity(), " La quantité commandée est nulle  ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (btn_insert_stock.equals(false)) {
                btn_insert_stock = true;
            }

            return true;
        }


    private void withoutBonus() {
        try {
            til_bonus.setVisibility(View.GONE);
            at_bonus.setVisibility(View.GONE);
            at_bonus.setEnabled(false);

        } catch (Exception ex) {
            // Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void withBonus() {
        try {
            til_bonus.setVisibility(View.VISIBLE);
            at_bonus.setVisibility(View.VISIBLE);
            at_bonus.setEnabled(true);

        } catch (Exception ex) {
            /// Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void charging_spinning_type_operation() {

        List<String>type= Arrays.asList("Choisir...","Vente avec bonus","Vente en détail","Sortie");
        spin_operation_type_adapter = new SpinOperationTypeAdapter(getActivity(), R.layout.spinner_custom, type);
        spin_type.setAdapter(spin_operation_type_adapter);
        spin_operation_type_adapter.notifyDataSetChanged();



    }


    private void init() {
        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class);
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        this.stockViewModel = new ViewModelProvider(this).get(StockViewModel.class);
        this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class);

    }
}