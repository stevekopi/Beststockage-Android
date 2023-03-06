package layout.Stocks;

import android.app.DatePickerDialog;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


import cd.sklservices.com.Beststockage.Adapters.Registres.PerformanceAdapter;
import cd.sklservices.com.Beststockage.Classes.Registres.Performance;
import cd.sklservices.com.Beststockage.Outils.MesOutils;
import cd.sklservices.com.Beststockage.R;
import cd.sklservices.com.Beststockage.ViewModel.registres.AgenceViewModel;
import cd.sklservices.com.Beststockage.ViewModel.Stocks.OperationViewModel;

public class PerformanceAgenceView extends Fragment {

    private OperationViewModel operationViewModel ;
    private AgenceViewModel agenceViewModel ;

    View rootView;
    static SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_agences_count,tv_notif;
    SearchView sv_agences_filter;
    static ListView lvAgences;

    Button btn_dateDebut,btn_dateFin;
    TextView tv_dateDebut,tv_dateFin;
    Calendar calendar;

    String dateDebutPourRequete,dateFinPourRequete;

    String jours,moiss; //Conversion de day et month en string

    int jour, mois,day_of_month;

    DatePickerDialog datePickerDialog;

    Performance currentInstance;
    PerformanceAdapter performanceAdapter;

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Performance des agence");

        Log.d("Chrono","Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Chrono","onPause");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Chrono","onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Chrono","onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Chrono","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Chrono","onDestroy");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Chrono","onStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Chrono","onCreateView");
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_performance, container,false);
        init();
        return rootView;
    }

    private void  init() {

        try {

            this.agenceViewModel = new ViewModelProvider(this).get(AgenceViewModel.class);
            this.operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class);
            lvAgences = (ListView) rootView.findViewById(R.id.lv_performance_agences);
            tv_agences_count = (TextView) rootView.findViewById(R.id.tv_performance_agences_count);
            tv_dateDebut = (TextView) rootView.findViewById(R.id.tv_performance_dateDebut);
            tv_dateFin = (TextView) rootView.findViewById(R.id.tv_performance_dateFin);
            tv_notif = (TextView) rootView.findViewById(R.id.tv_performance_notif);
            btn_dateDebut = (Button) rootView.findViewById(R.id.btn_performance_dateDebut);
            btn_dateFin = (Button) rootView.findViewById(R.id.btn_performance_dateFin);
            swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_performance_agence);
            sv_agences_filter = (SearchView) rootView.findViewById(R.id.sv_performance_agences_filter);

            tv_notif.setText("Classement de tout le temps");

            //LISTENER BUTTON DATE DEBUT

            btn_dateDebut.setOnClickListener(new View.OnClickListener() {
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
                            dateDebutPourRequete = year + "-" + (moiss) + "-" + jours;
                            tv_dateDebut.setText(jours + "/" + (moiss) + "/" + year);
                            if (tv_dateFin.length() > 2) {
                                currentInstance = operationViewModel.getPerformancesByDates(dateDebutPourRequete, dateFinPourRequete);
                                if (currentInstance.agence_1_id!=null) {
                                    lvAgences.setVisibility(View.VISIBLE);
                                    performanceAdapter.update(currentInstance);
                                    tv_notif.setText("Classement allant du " + MesOutils.Get_date_en_francais(dateDebutPourRequete) + " au " + MesOutils.Get_date_en_francais(dateFinPourRequete));
                                    count_element();
                                }
                                else{
                                    lvAgences.setVisibility(View.GONE);
                                }
                            }
                        }
                    }, jour, mois, day_of_month);
                    datePickerDialog.show();

                }
            });

            btn_dateFin.setOnClickListener(new View.OnClickListener() {
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
                            dateFinPourRequete = year + "-" + (moiss) + "-" + jours;
                            tv_dateFin.setText(jours + "/" + (moiss) + "/" + year);
                            if (tv_dateDebut.length() > 2) {
                                currentInstance = operationViewModel.getPerformancesByDates(dateDebutPourRequete, dateFinPourRequete);
                                if (currentInstance.agence_1_id!=null) {
                                    lvAgences.setVisibility(View.VISIBLE);
                                    performanceAdapter.update(currentInstance);
                                    tv_notif.setText("Classement allant du " + tv_dateDebut.getText() + " au " + tv_dateFin.getText());
                                    count_element();
                                }
                                else{
                                    lvAgences.setVisibility(View.GONE);
                                }
                            }
                        }
                    }, jour, mois, day_of_month);
                    datePickerDialog.show();

                }
            });

            //LISTENER SWIPEREFRESH
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                   init();
                    tv_notif.setText("Classement de tout le temps");
                    refresh_end();

                }
            });



            fillData() ;


        }
        catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void fillData()
    {
        try {

                currentInstance = operationViewModel.getPerformances();

                // Collections.sort(lesPerformances,Collections.<Agence>reverseOrder());
                if (currentInstance != null) {
                    performanceAdapter = new PerformanceAdapter(getContext(), agenceViewModel, currentInstance);
                    lvAgences.setAdapter(performanceAdapter);
                    tv_agences_count.setText("Agence (" + this.lvAgences.getCount() + ")");
                }

                swipeRefreshLayout.setRefreshing(false);
            }
            catch (Exception e){
                Toast.makeText(getActivity(), " Filldata = " + e.toString(), Toast.LENGTH_LONG).show();
            }

    }

    private  void count_element() {
        tv_agences_count.setText("Agences ("+lvAgences.getCount()+")");
        swipeRefreshLayout.setRefreshing(false);
    }

public static void refresh_end(){
    swipeRefreshLayout.setRefreshing(false);
}


}
