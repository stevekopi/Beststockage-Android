package layout;

import static cd.sklservices.com.Beststockage.ViewModel.TableKeyIncrementorViewModel.keygen;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Transaction;

import java.util.Calendar;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Adapters.SpinFournisseurAdapter;
import cd.sklservices.com.Beststockage.Classes.Depense;
import cd.sklservices.com.Beststockage.Classes.Fournisseur;
import cd.sklservices.com.Beststockage.Cloud.SyncDepense;
import cd.sklservices.com.Beststockage.Cloud.SyncOperation;
import cd.sklservices.com.Beststockage.Cloud.SyncOperationFinance;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Repository.DepenseRepository;
import cd.sklservices.com.Beststockage.Repository.OperationFinanceRepository;
import cd.sklservices.com.Beststockage.Repository.OperationRepository;
import cd.sklservices.com.Beststockage.ViewModel.DepenseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.FournisseurViewModel;

public class DepenseAdd extends Fragment  {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= DepenseAdd.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    private DepenseViewModel depenseViewModel;

    private AutoCompleteTextView at_montant;
    private MultiAutoCompleteTextView mact_observation;
    private Button btn_save;
    private Depense current_instance;
    private Spinner spin_depense_fournisseur;
    private Fournisseur fournisseur;

   private SpinFournisseurAdapter spinFournisseurAdapter;


    private Boolean btn_insert = false ;

    private Button btn_date_select;
    DatePickerDialog datePickerDialog;
    int jour, mois,day_of_month;
    String jours,moiss; //Conversion de day et month en string
    Calendar calendar;
    TextView tv_date;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Depense");
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
        rootView =inflater.inflate(R.layout.fragment_add_depense, container, false);

        at_montant = (AutoCompleteTextView) rootView.findViewById(R.id.at_depense_montant) ;
        spin_depense_fournisseur=(Spinner)rootView.findViewById(R.id.spin_depense_fournisseur);
        mact_observation = (MultiAutoCompleteTextView) rootView.findViewById(R.id.mact_depense_observation) ;
        tv_date=rootView.findViewById(R.id.tv_depense_add_date);
        btn_save = (Button) rootView.findViewById(R.id.bt_depense_save) ;

        btn_date_select=rootView.findViewById(R.id.btn_depense_add_date);

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

        spin_depense_fournisseur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fournisseur=spinFournisseurAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        init() ;

        try {

            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertItem();

                }
            });

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    @Transaction
    private void insertItem() {
        try {

            if(mact_observation.getText().toString().contains("@"))
            {
                Toast.makeText(getActivity(), "  L'observation ne doit pas contenir @  " , Toast.LENGTH_LONG).show();
            }
            else {

                String key = keygen(getActivity(), "","depense");
                String opeFinkey = keygen(getActivity(), "","operation_finance");


                Depense instance=new Depense(key,MainActivity.getCurrentUser().getAgence_id(),true,opeFinkey,MainActivity.CurrentDate,mact_observation.getText().toString(),
                        Double.valueOf(at_montant.getText().toString()),"Fc",MainActivity.getCurrentUser().getId(),MainActivity.getCurrentUser().getAgence_id(),
                        MainActivity.getAddingDate(),MainActivity.getAddingDate(),0,0);

                instance.setDate(current_instance.getDate());

                instance.getOperationFinance().setFournisseur(fournisseur);

                if(depenseViewModel.ajout(instance)==1){

                    new SyncOperationFinance(new OperationFinanceRepository(getContext())).sendPost();
                    new SyncDepense(new DepenseRepository(getContext())).sendPost();

                    Toast.makeText(getActivity(), " Succès", Toast.LENGTH_LONG).show();

                    ((MainActivity) getActivity()).afficherDepense();
                }
                else{
                    Toast.makeText(getActivity(), "Echec", Toast.LENGTH_LONG).show();
                }
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), "Echec\n" + ex.toString() , Toast.LENGTH_LONG).show();
        }
    }



    private void init()
    {
        this.depenseViewModel = new ViewModelProvider(this).get(DepenseViewModel.class) ;

        charging_spinning_fournisseur(spin_depense_fournisseur);

        initSave();

    }

    private void initSave() {
        current_instance=new Depense();
        current_instance.setDate(MainActivity.CurrentDate);
        tv_date.setText(MesOutils.Get_date_en_francais(current_instance.getDate()));
    }

    private void charging_spinning_fournisseur(Spinner spin) {
        try {
            List<Fournisseur> fournisseurs =new FournisseurViewModel(getActivity().getApplication()).getFournisseurArrayListe();
            spinFournisseurAdapter=new SpinFournisseurAdapter(getContext(),R.layout.spinner_custom,fournisseurs);
            spin.setAdapter(spinFournisseurAdapter);
        } catch (Exception ex) {
        }
    }

}


