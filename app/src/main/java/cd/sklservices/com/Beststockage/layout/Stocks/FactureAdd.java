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
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.Parametres.ArticleProduitFactureAdapter;
import cd.sklservices.com.Beststockage.Adapters.Parametres.ProduitFactureAdapter;
import cd.sklservices.com.Beststockage.Adapters.Registres.AgenceAdapter;
import cd.sklservices.com.Beststockage.Adapters.Registres.ArticleAdapter;
import cd.sklservices.com.Beststockage.Adapters.Stocks.LigneFactureAdapter;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ArticleProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.DeviseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Parametres.ProduitFactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.FactureViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.StockViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.registres.FournisseurViewModel;

public class FactureAdd extends Fragment {

    private final static String COMMON_TAG = "Orientation Change ";
    private final static String FRAGMENT_NAME = FactureAdd.class.getSimpleName();
    private final static String TAG = FRAGMENT_NAME;

    private FactureViewModel factureViewModel;

   //AGENCES
    private AgenceViewModel agenceViewModel;
    String indexAgence ;
    AgenceAdapter adapterAgence;
    int count_all_agences;
    public boolean isAgenceLoading=false;
    private LinearLayout ll_agence_ben;
    private TextView tv_agence_name;
    private TextView tv_agence_proprietaire;
    SearchView sv_agences_filter;
    List<Agence> listAgences;
    ListView lv_agences;
    static SwipeRefreshLayout swipeRefreshLayoutAgences;
    TextView tv_agences_count,tv_montant,tv_montant_net_total;

    //PRODUITS FACTURE
    private LinearLayout ll_facture_add_produit;
    List<ProduitFacture> produitFactureList;
    SearchView sv_produit_factures_filter;
    private ProduitFactureViewModel produitFactureViewModel;
    String indexProduitFacture ;
    TextView tv_produit_facture_count,tv_produit_facture_designation,tv_produit_facture_devise;
    int count_all_produit_facture;
    public boolean isProduitFactureLoading=false;
    ListView lv_produit_factures;
    static SwipeRefreshLayout swipeRefreshLayoutProduitFactures;
    ProduitFactureAdapter produitFactureAdapter;
    private TextView tv_date;

    //ARTICLE PRODUITS FACTURE
    private LinearLayout ll_facture_add_article_produit;
    List<ArticleProduitFacture> articleProduitFactureList;
    SearchView sv_article_produit_factures_filter;
    private ArticleProduitFactureViewModel articleProduitFactureViewModel;
    String indexArticleProduitFacture;
    TextView tv_article_produit_facture_count,tv_article_produit_facture_designation,tv_article_produit_facture_devise;
    private TextView tv_article_produit_facture_description;
    private TextView tv_article_produit_facture_fournisseur;
    int count_all_article_produit_facture;
    public boolean isArticleProduitFactureLoading=false;
    ListView lv_article_produit_factures;
    static SwipeRefreshLayout swipeRefreshLayoutArticleProduitFactures;
    ArticleProduitFactureAdapter articleProduitFactureAdapter;

    private Button btn_date_select;
    DatePickerDialog datePickerDialog;

    //ARTICLE BONUS
    private ArticleViewModel articleViewModel;
    List<Article> listArticles;
    SearchView sv_articles_filter;
    private TextView tv_article_description;
    static SwipeRefreshLayout swipeRefreshLayoutArticles;
    TextView tv_articles_count;
    ListView lv_articles;
    private LinearLayout ll_article;
    private TextView tv_article_fournisseur;
    private TextView tv_stock_count,tv_stock_bonus_count;

    private Facture current_instance;
    private LigneFacture ligneFacture;

    int jour, mois,day_of_month;
    String jours,moiss; //Conversion de day et month en string
    Calendar calendar;

    private StockViewModel stockViewModel;


    //LIGNE FACTURE
    LigneFactureAdapter ligneFactureAdapter;
    ListView lv_ligne_facture;
    List<LigneFacture> ligneFactureList;
    private AutoCompleteTextView at_bonus;
    private MultiAutoCompleteTextView mact_observation;
    private TextInputLayout til_bonus;
    private EditText et_quantite;

    public Handler myHandler;
    public View footer;
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



        rootView = inflater.inflate(R.layout.fragment_facture_add, container, false);

        LayoutInflater li=(LayoutInflater) ((MainActivity)getContext()).getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        footer= li.inflate(R.layout.footer_view,null);
        myHandler=new MyHandler();

        ll_agence_ben=rootView.findViewById(R.id.ll_facture_add_agence_ben);
        tv_agence_name=ll_agence_ben.findViewById(R.id.tv_facture_add_agence_name);
        tv_agence_proprietaire=ll_agence_ben.findViewById(R.id.tv_facture_add_agence_proprietaire);
        ll_agence_ben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //  if(current_instance.getProduit_id().toLowerCase().contains("choisir")){
            //Toast.makeText(getActivity(), " Choisissez d'abord le type de l'operation", Toast.LENGTH_SHORT).show();
            //  }
                //    else{
                    showCustomAlertDialogBoxForAgenceList();
            //    }

             }
        });

        ll_facture_add_produit=rootView.findViewById(R.id.ll_facture_add_produit);
        tv_produit_facture_designation=ll_facture_add_produit.findViewById(R.id.tv_facture_add_produit_designation);
        tv_produit_facture_devise=ll_facture_add_produit.findViewById(R.id.tv_facture_add_produit_devise);
        tv_montant_net_total=rootView.findViewById(R.id.tv_facture_add_montant_net_total);
        ll_facture_add_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  if(current_instance.getProduit_id().toLowerCase().contains("choisir")){
                //Toast.makeText(getActivity(), " Choisissez d'abord le type de l'operation", Toast.LENGTH_SHORT).show();
                //  }
                //    else{
                showCustomAlertDialogBoxForProduitFactureList();
                //    }

            }
        });

        ll_facture_add_article_produit=rootView.findViewById(R.id.ll_facture_add_article_produit);
        ll_facture_add_article_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(current_instance.getProduit()==null){
                Toast.makeText(getActivity(), " Choisissez d'abord le type de l'operation", Toast.LENGTH_SHORT).show();
                 }
                    else{
                     if(current_instance.getProduit().getWith_bonus()==0){
                         et_quantite.setEnabled(true);
                         at_bonus.setEnabled(false);
                     }
                     else{
                         et_quantite.setEnabled(true);
                         at_bonus.setEnabled(true);
                     }
                     showCustomAlertDialogBoxForArticleProduitFactureList();
                 }

            }
        });
        tv_article_produit_facture_description=ll_facture_add_article_produit.findViewById(R.id.tv_facture_add_article_produit_description);
        tv_article_produit_facture_fournisseur=ll_facture_add_article_produit.findViewById(R.id.tv_facture_add_article_produit_fournisseur);


        et_quantite = rootView.findViewById(R.id.at_facture_add_quantite);
        et_quantite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if(ligneFacture.getArticleProduitFacture()==null)
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
                    ligneFacture.setQuantite(Integer.valueOf(charSequence.toString()));
                }
                else {
                    ligneFacture.setQuantite(0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    if(String.valueOf(editable.toString()).length()>0){
                     tv_montant.setText("Montant : "+ligneFacture.getMontant_net());
                    }
                    else{
                       tv_montant.setText("Montant : 0 Fc");
                    }
                }
                catch (Exception e){

                }
            }
        });


        ll_article=rootView.findViewById(R.id.ll_facture_add_article_bonus);
        ll_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCustomAlertDialogBoxForArticleList();

            }
        });
        tv_article_description=ll_article.findViewById(R.id.tv_facture_add_article_bonus_description);
        tv_article_fournisseur=ll_article.findViewById(R.id.tv_facture_add_article_bonus_fournisseur);
        at_bonus = rootView.findViewById(R.id.at_facture_add_bonus);
        at_bonus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if(ligneFacture.getArticleProduitFacture()==null)
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
                    ligneFacture.setBonus(Integer.valueOf(charSequence.toString()));
                }
                else {
                    ligneFacture.setBonus(0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    if(String.valueOf(editable.toString()).length()>0){

                    }
                    else{

                    }
                }
                catch (Exception e){

                }
            }
        });

        tv_stock_count=rootView.findViewById(R.id.tv_facture_add_stock_count);
        tv_stock_bonus_count=rootView.findViewById(R.id.tv_facture_add_stock_article_bonus_count);

        tv_montant = rootView.findViewById(R.id.tv_facture_add_montant);

        tv_date=rootView.findViewById(R.id.tv_facture_add_date);

        btn_date_select=rootView.findViewById(R.id.btn_facture_add_date);
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

        lv_ligne_facture=rootView.findViewById(R.id.lv_facture_add_lignes);

        Button bt_add_line = rootView.findViewById(R.id.bt_facture_add_line);
        bt_add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ligneFactureList.add(ligneFacture);
                ligneFactureAdapter.update(ligneFactureList);
                ll_facture_add_produit.setEnabled(false);
                ll_agence_ben.setEnabled(false);
                btn_date_select.setEnabled(false);
                Double montantNet=0.0;
                for (int i=0;i<ligneFactureList.size();i++){
                    montantNet+=ligneFactureList.get(i).getMontant_net();
                }
                Devise devise=new DeviseViewModel(MainActivity.application).get(current_instance.getDevise_id());
                tv_montant_net_total.setText(MesOutils.spacer(String.valueOf(montantNet.intValue())) + " " + devise.getCode());
                initLineSave();
                tv_article_produit_facture_description.setText("");
                tv_article_description.setText("");
                et_quantite.setText("");
                at_bonus.setText("");


            }
        });

        Button btn_valider = rootView.findViewById(R.id.bt_facture_add_valider);
        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               current_instance.setLines(ligneFactureList);
               new FactureViewModel(MainActivity.application,getActivity()).manage_add_async(current_instance);
            }
        });

        init();

        return rootView;
    }

    void initSave(){
        current_instance=new Facture();
        ligneFactureList=new ArrayList<>();
        tv_date.setText(MesOutils.Get_date_en_francais(current_instance.getDate()));
        initLineSave();
        ligneFactureAdapter = new LigneFactureAdapter(getContext(),ligneFactureList);

        lv_ligne_facture.setAdapter(ligneFactureAdapter);
        lv_ligne_facture.setLongClickable(true);
        lv_ligne_facture.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);


    }

    void initLineSave(){
        ligneFacture=new LigneFacture(current_instance);
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
            ligneFacture.setArticleBonus(new ArticleViewModel(getActivity().getApplication()).get(((Article)adapterArticle.getItem(which)).getId(),true,true));
            ligneFacture.setArticle_bonus_id(ligneFacture.getArticleBonus().getId());
            tv_article_description.setText(ligneFacture.getArticleBonus().getDescription());
            tv_article_fournisseur.setText("De : "+new FournisseurViewModel(getActivity().getApplication()).get(ligneFacture.getArticleBonus().getFournisseur_id(),true).getIdentity().getName());
            int qte_disponible = stockViewModel.Qte(ligneFacture.getArticleBonus().getId());

            tv_stock_bonus_count.setText(String.valueOf(qte_disponible));

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

    private  void count_element_articles() {
        tv_articles_count.setText("Articles ("+lv_articles.getCount()+")");
        swipeRefreshLayoutArticles.setRefreshing(false);
    }

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

        fillAgencesData();


        adapterAgence =new AgenceAdapter(getContext(), listAgences,false);
        lv_agences.setAdapter(adapterAgence);

        lv_agences.setOnItemClickListener((adapterView, view, which, l) -> {
            // Log.d(TAG, "showAssignmentsList: " + allUsersList.get(which).getUserId());
            // TODO : Listen to click callbacks at the position
            current_instance.setAgence2(new AgenceViewModel(getActivity().getApplication()).get(((Agence)adapterAgence.getItem(which)).getId(),false,true));
            current_instance.setAgence_2_id(current_instance.getAgence2().getId());
            tv_agence_name.setText(current_instance.getAgence2().getType()+" "+current_instance.getAgence2().getDenomination());
            tv_agence_proprietaire.setText("De : "+current_instance.getAgence2().getProprietaire().getName());
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

    private void init() {
        initSave();
        current_instance=new Facture();
        this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class);
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        this.stockViewModel = new ViewModelProvider(this).get(StockViewModel.class);
        this.factureViewModel = new ViewModelProvider(this).get(FactureViewModel.class);
        this.produitFactureViewModel = new ViewModelProvider(this).get(ProduitFactureViewModel.class);
        this.articleProduitFactureViewModel=new ViewModelProvider(this).get(ArticleProduitFactureViewModel.class);
        count_all_agences=agenceViewModel.count();

        initSave();
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

    private  void count_element_agences() {
        tv_agences_count.setText("Agences ("+lv_agences.getCount()+"/"+count_all_agences+")");
        swipeRefreshLayoutAgences.setRefreshing(false);
        tv_agences_count.setText("Agences ("+lv_agences.getCount()+"/"+count_all_agences+")");
    }

    private  void count_element_agences_search() {
        tv_agences_count.setText("Agences ("+lv_agences.getCount()+"/"+count_all_agences+")");
        swipeRefreshLayoutAgences.setRefreshing(false);
        tv_agences_count.setText("Agences ("+lv_agences.getCount()+"/"+count_all_agences+")");
    }

    public void fillAgencesData()
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


    //-----------------------------------------------------------

    // ARTICLE PRODUIT FACTURE - DEBUT

    private void showCustomAlertDialogBoxForArticleProduitFactureList() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.article_produit_factures_list);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
        }
        lv_article_produit_factures = dialog.findViewById(R.id.lv_article_produit_factures);

        TextView tv_title = dialog.findViewById(R.id.tv_article_produit_facture_popup_title);
        sv_article_produit_factures_filter = dialog.findViewById(R.id.sv_article_produit_factures_filter);
        tv_article_produit_facture_count =dialog.findViewById(R.id.tv_article_produit_factures_count);
        swipeRefreshLayoutArticleProduitFactures =dialog.findViewById(R.id.refresh_article_produit_factures);


        swipeRefreshLayoutArticleProduitFactures.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                try{
                    showCustomAlertDialogBoxForArticleProduitFactureList();
                }
                catch (Exception e){

                }
                finally {
                    swipeRefreshLayoutArticleProduitFactures.setRefreshing(false);
                }
            }

        });

        tv_title.setText("Choisir l'article");

        fillArticleProduitFactureData();

        articleProduitFactureAdapter =new ArticleProduitFactureAdapter(getContext(), articleProduitFactureList,false);
        lv_article_produit_factures.setAdapter(articleProduitFactureAdapter);

        lv_article_produit_factures.setOnItemClickListener((adapterView, view, which, l) -> {
            // Log.d(TAG, "showAssignmentsList: " + allUsersList.get(which).getUserId());
            // TODO : Listen to click callbacks at the position
            ligneFacture =new LigneFacture(current_instance);
            ArticleProduitFacture articleProduitFacture=(ArticleProduitFacture)articleProduitFactureAdapter.getItem(which);
            ligneFacture.setArticleProduitFacture(articleProduitFacture);
            Article article =  new ArticleViewModel(getActivity().getApplication()).get(articleProduitFacture.getArticle_id(),true,true);
            tv_article_produit_facture_description.setText(article.getDescription());
            tv_article_produit_facture_fournisseur.setText("De : "+new FournisseurViewModel(getActivity().getApplication()).get(article.getFournisseur_id(),true).getIdentity().getName());
            int qte_disponible = stockViewModel.Qte(article.getId());

            tv_stock_count.setText(String.valueOf(qte_disponible));

            dialog.cancel();

        });

        sv_produit_factures_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Recherche",query);
                count_produit_facture_element_Search();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ArrayList<ProduitFacture> result = new ArrayList<>();
                    for (ProduitFacture x : produitFactureList) {
                        if ((x.getType().toLowerCase() + " " + x.getDesignation().toLowerCase()).contains(newText.toLowerCase())) {
                            result.add(x);
                        }
                    }
                    produitFactureAdapter.update(result);
                    count_produit_facture_element_Search();
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        count_produit_facture_element();
        dialog.show();
    }

    // ARTICLE PRODUIT FACTURE - FIN


    private void showCustomAlertDialogBoxForProduitFactureList() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.produit_factures_list);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
        }
        lv_produit_factures = dialog.findViewById(R.id.lv_produit_factures_list);

        TextView tv = dialog.findViewById(R.id.tv_produit_facture_popup_title);
        sv_produit_factures_filter = dialog.findViewById(R.id.sv_produit_factures_list_filter);
        tv_produit_facture_count =dialog.findViewById(R.id.tv_produit_factures_list_count);
        swipeRefreshLayoutProduitFactures =dialog.findViewById(R.id.refresh_list_produit_factures);

        swipeRefreshLayoutProduitFactures.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                try{
                    showCustomAlertDialogBoxForProduitFactureList();
                }
                catch (Exception e){

                }
                finally {
                    swipeRefreshLayoutProduitFactures.setRefreshing(false);
                }
            }

        });

        tv.setText("Choisir l'opération");

        fillFactureProductsData();


        produitFactureAdapter =new ProduitFactureAdapter(getContext(), produitFactureList,false);
        lv_produit_factures.setAdapter(produitFactureAdapter);

        lv_produit_factures.setOnItemClickListener((adapterView, view, which, l) -> {
            // Log.d(TAG, "showAssignmentsList: " + allUsersList.get(which).getUserId());
            // TODO : Listen to click callbacks at the position
            current_instance.setProduit(new ProduitFactureViewModel(getActivity().getApplication()).get(((ProduitFacture)produitFactureAdapter.getItem(which)).getId()));
            current_instance.setProduit_id(current_instance.getProduit().getId());
            tv_produit_facture_designation.setText(current_instance.getProduit().getDesignation());
            tv_produit_facture_devise.setText(new DeviseViewModel(MainActivity.application).get(current_instance.getProduit().getDevise_id()).getCode());

            dialog.cancel();

        });

        sv_produit_factures_filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Recherche",query);
                count_produit_facture_element_Search();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ArrayList<ProduitFacture> result = new ArrayList<>();
                    for (ProduitFacture x : produitFactureList) {
                        if ((x.getType().toLowerCase() + " " + x.getDesignation().toLowerCase()).contains(newText.toLowerCase())) {
                            result.add(x);
                        }
                    }
                    produitFactureAdapter.update(result);
                    count_produit_facture_element_Search();
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        count_produit_facture_element();
        dialog.show();
    }

    public void fillArticleProduitFactureData()
    {
        try {
            articleProduitFactureList = articleProduitFactureViewModel.loadingByProduit(current_instance.getProduit_id(),false,false) ;
            List<ArticleProduitFacture>  instances = new ArrayList<>();
            instances.clear();

            for (int k = 0; k< articleProduitFactureList.size(); k++){

                instances.add(articleProduitFactureList.get(k));
                indexArticleProduitFacture = articleProduitFactureList.get(k).getProduit_id();
            }

            count_article_produit_facture_element();

            isArticleProduitFactureLoading=true;

            swipeRefreshLayoutArticleProduitFactures.setRefreshing(false);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }
    }

    public void fillFactureProductsData()
    {
        try {
            produitFactureList = produitFactureViewModel.loading() ;
            List<ProduitFacture>  instances = new ArrayList<>();
            instances.clear();

            for (int k = 0; k< produitFactureList.size(); k++){

                instances.add(produitFactureList.get(k));
                indexProduitFacture = produitFactureList.get(k).getDesignation() ;

            }

            count_produit_facture_element();

            isProduitFactureLoading=true;

            swipeRefreshLayoutProduitFactures.setRefreshing(false);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString() , Toast.LENGTH_LONG).show();
        }

    }

    private  void count_article_produit_facture_element() {
        tv_article_produit_facture_count.setText("article ("+lv_article_produit_factures.getCount()+"/"+count_all_produit_facture+")");
        swipeRefreshLayoutProduitFactures.setRefreshing(false);
        tv_produit_facture_count.setText("Operations ("+lv_article_produit_factures.getCount()+"/"+count_all_produit_facture+")");
    }

    private  void count_produit_facture_element() {
        tv_produit_facture_count.setText("Operations ("+lv_produit_factures.getCount()+"/"+count_all_produit_facture+")");
        swipeRefreshLayoutProduitFactures.setRefreshing(false);
        tv_produit_facture_count.setText("Operations ("+lv_produit_factures.getCount()+"/"+count_all_produit_facture+")");
    }

    private  void count_produit_facture_element_Search() {
        tv_produit_facture_count.setText("Operations ("+lv_produit_factures.getCount()+"/"+count_all_produit_facture+")");
        swipeRefreshLayoutProduitFactures.setRefreshing(false);
        tv_produit_facture_count.setText("Operations ("+lv_produit_factures.getCount()+"/"+count_all_produit_facture+")");
    }


}