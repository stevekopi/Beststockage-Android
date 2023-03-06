package layout.Finances;

import static cd.sklservices.com.Beststockage.ViewModel.TableKeyIncrementorViewModel.keygen;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Controles.Controle;
import cd.sklservices.com.Beststockage.Cloud.Controles.SyncControle;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.Repository.Controles.ControleRepository;
import cd.sklservices.com.Beststockage.ViewModel.Controles.ControleViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Finances.DepenseViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Finances.OperationFinanceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.OperationViewModel;

public class RapportCaisseView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= RapportCaisseView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    TextView tv_rapport_caisse_confirmation;

    View rootView;

    DatePicker dp_rapport_caisse;
    TextView tv_ventes,tv_depense,tv_reste,tv_entrees,tv_sorties;
    ImageButton img_btn_controle_confirmation;
    String currentDate;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rapport caisse");
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
        rootView =inflater.inflate(R.layout.fragment_rapport_caisse, container, false);
        init();
        return rootView;
    }

    private void init(){

        try {

            fillData();
             dp_rapport_caisse = (DatePicker) rootView.findViewById(R.id.dp_rapport_caisse_date);
            tv_depense = (TextView) rootView.findViewById(R.id.tv_rapport_caisse_depense);
            tv_ventes = (TextView) rootView.findViewById(R.id.tv_rapport_caisse_montant);
            tv_entrees = (TextView) rootView.findViewById(R.id.tv_rapport_caisse_entrees);
            tv_sorties = (TextView) rootView.findViewById(R.id.tv_rapport_caisse_sorties);
            tv_reste = (TextView) rootView.findViewById(R.id.tv_rapport_caisse_reste);
            tv_rapport_caisse_confirmation=(TextView) rootView.findViewById(R.id.tv_rapport_caisse_confirmation);
            img_btn_controle_confirmation=(ImageButton) rootView.findViewById(R.id.img_btn_controle_confirmation);

            img_btn_controle_confirmation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    String id = keygen(getActivity(), "","controle");
                                    Controle instance=new Controle(id,MainActivity.getCurrentUser().getId(),MainActivity.getCurrentUser().getAgence_id(),
                                            currentDate,"Confirmation","Correct",MainActivity.getCurrentUser().getId(),MainActivity.getCurrentUser().getAgence_id(),
                                            MainActivity.getAddingDate(),MainActivity.getAddingDate(),0,0);

                                    if(new OperationFinanceViewModel(getActivity().getApplication()).confirmation_rapport_journalier(currentDate)==1){
                                        if (new ControleViewModel(getActivity().getApplication()).ajout_async(instance)==1){
                                            new SyncControle(new ControleRepository(getContext())).sendPost();
                                            Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(getContext(), "Echec", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Aucune donnée financière", Toast.LENGTH_LONG).show();
                                    }

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    Toast.makeText(getContext(), "Action annulée", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Confirmez-vous que le rapport financier est-il correct?").setPositiveButton("Oui", dialogClickListener)
                            .setNegativeButton("Non", dialogClickListener).show();

                }
            });

            try{
                dp_rapport_caisse.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                        calcul();

                        Controle instance=new ControleViewModel(getActivity().getApplication()).get_by_date_and_agence_id(currentDate);
                        if(instance==null){
                            tv_rapport_caisse_confirmation.setText("Confimer le rapport");
                            tv_rapport_caisse_confirmation.setTextColor(Color.RED);
                            img_btn_controle_confirmation.setVisibility(View.VISIBLE);
                        }
                        else {
                            if(instance.getEtat().toLowerCase(Locale.ROOT).equals("correct")){
                                tv_rapport_caisse_confirmation.setText("Dejà confirmé");
                                tv_rapport_caisse_confirmation.setTextColor(Color.GREEN);
                                img_btn_controle_confirmation.setVisibility(View.GONE);
                            }
                            else if(instance.getEtat().toLowerCase(Locale.ROOT).equals("incorrect")){
                                tv_rapport_caisse_confirmation.setText("Signalé incorrect");
                                tv_rapport_caisse_confirmation.setTextColor(Color.BLUE);
                                img_btn_controle_confirmation.setVisibility(View.VISIBLE);
                            }
                            else {
                                tv_rapport_caisse_confirmation.setText("Consulter le responsable");
                                tv_rapport_caisse_confirmation.setTextColor(Color.YELLOW);
                            }
                        }

                    }
                });
            }
            catch (Exception e){}

           }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

    }

    void calcul(){
        String date= String.valueOf(dp_rapport_caisse.getDayOfMonth());
        String jour=date.length()==1?"0"+date:date;
        String month=String.valueOf(dp_rapport_caisse.getMonth()+1);
        String mois=month.length()==1?"0"+month:month;
        String year=String.valueOf(dp_rapport_caisse.getYear());

        currentDate=String.valueOf(year)+"-"+String.valueOf(mois)+"-"+String.valueOf(jour);

        Double MontantBrut=new OperationViewModel(getActivity().getApplication()).get_amount_by_date_and_agence_id(currentDate,MainActivity.getCurrentUser().getAgence_id());
        Double Depense=new DepenseViewModel(getActivity().getApplication()).get_amount_by_date_and_agence_id(currentDate,MainActivity.getCurrentUser().getAgence_id());
        String ventes=MesOutils.spacer(String.valueOf(MontantBrut.intValue()));
        String depense=MesOutils.spacer(String.valueOf(Depense.intValue()));

       // Double net=MontantBrut-Depense;
        Double entrees=new OperationFinanceViewModel(getActivity().getApplication()).get_all_entrees_amount_by_date_and_agence_id(currentDate,MainActivity.getCurrentUser().getAgence_id());
        Double sorties=new OperationFinanceViewModel(getActivity().getApplication()).get_all_sorties_amount_by_date_and_agence_id(currentDate,MainActivity.getCurrentUser().getAgence_id());
        Double net=new OperationFinanceViewModel(getActivity().getApplication()).get_net_amount_by_date_and_agence_id(currentDate,MainActivity.getCurrentUser().getAgence_id());
        String reste=MesOutils.spacer(String.valueOf(net.intValue()));

        tv_ventes.setText(ventes);
        tv_entrees.setText(MesOutils.spacer(String.valueOf(entrees.intValue())));
        tv_sorties.setText(MesOutils.spacer(String.valueOf(sorties.intValue())));
        tv_depense.setText(MesOutils.spacer(depense));
        tv_reste.setText(reste);
    }


    private void fillData(){

    }


}


