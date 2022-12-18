package layout;

import androidx.fragment.app.Fragment;

public class DetailsCaisseView extends Fragment {

//    private final static String COMMON_TAG="Orientation Change ";
//    private final static String FRAGMENT_NAME= DetailsCaisseView.class.getSimpleName();
//    private final static String TAG=FRAGMENT_NAME;
//
//    View rootView;
//
//    ExpandableListView elv_operation;
//    ExpandableListAdapter ela_operation;
//    List<String> rapportCumul;
//    Map<String,List<String>> rapportCaisseDates;
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.i(COMMON_TAG,"Fragment HomeView SaveInstance");
//    }
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.i(TAG,FRAGMENT_NAME+ " onAttache");
//
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.i(TAG,FRAGMENT_NAME+ " onCreate");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name)+" - Accueil");
//        Log.i(TAG,FRAGMENT_NAME+ " onResume");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.i(TAG,FRAGMENT_NAME+ " onStart");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.i(TAG,FRAGMENT_NAME+ " onStop");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG,FRAGMENT_NAME+ " onDestroy");
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        Log.i(TAG,FRAGMENT_NAME+ " onCreateView");
//        // Inflate the layout for this fragment
//        rootView=inflater.inflate(R.layout.fragment_home, container, false);
//        init();
//        return rootView;
//    }
//
//    private void init(){
//        elv_operation=(ExpandableListView)rootView.findViewById(R.id.elv_home_caisse);
//        fillData();
//        ela_operation=new Home_expandable_adapter_caisse(getContext(),rapportCumul,rapportCaisseDates);
//        elv_operation.setAdapter(ela_operation);
//
//        elv_operation.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView annees, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(getContext(),rapportCumul.get(groupPosition)+" : "+rapportCaisseDates.get(rapportCumul.get(groupPosition)).get(childPosition),Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });
//
//
//
//    }
//
//
//    private void fillData(){
//        rapportCumul=new ArrayList<>();
//        rapportCaisseDates=new HashMap<>();
//
//        rapportCumul.add("Janvier");
//        rapportCumul.add("Février");
//
//        List<String> Janvier=new ArrayList<>();
//        List<String> Février=new ArrayList<>();
//
//        Janvier.add("Eau");
//        Janvier.add("jus");
//        Janvier.add("Boisson");
//
//        Février.add("Eau");
//        Février.add("Boisson");
//
//        rapportCaisseDates.put(rapportCumul.get(0),Janvier);
//        rapportCaisseDates.put(rapportCumul.get(1),Février);
//
//
//    }


}


