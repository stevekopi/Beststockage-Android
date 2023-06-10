package cd.sklservices.com.Beststockage.layout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Outils.*;
import cd.sklservices.com.Beststockage.ViewModel.registres.UserViewModel;
import cd.sklservices.com.Beststockage.R;


public class ChangePwdView extends Fragment {

    private UserViewModel userViewModel ;
    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= ChangePwdView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    Button btn_valider;
    TextView tv_user_name,tv_user_role;
    EditText et_pwd_actuel,et_pwd_new_1,et_pwd_new_2;

    View rootView;
   static User user=null;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Changer Mot de passe");
if (user!=null){
    tv_user_name.setText(user.getUsername());
    tv_user_role.setText(user.getRole().getDesignation());
}
else{
    tv_user_name.setText("Aucun compte actif");
    tv_user_role.setText(user.getRole().getDesignation());
}
        }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,FRAGMENT_NAME+ " onCreateView");
        rootView= inflater.inflate(R.layout.fragment_change_passwordt, container, false);
        init();

        return rootView;
    }

    public void init(){

        this.userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;

        btn_valider=rootView.findViewById(R.id.btn_change_pwd_valider);
        tv_user_name= rootView.findViewById(R.id.tv_changepwd_user_nom);
        tv_user_role= rootView.findViewById(R.id.tv_changepwd_user_role);

        et_pwd_actuel=  rootView.findViewById(R.id.et_change_pwd_actuel);
        et_pwd_new_1=  rootView.findViewById(R.id.et_change_pwd_nouveau_1);
        et_pwd_new_2=  rootView.findViewById(R.id.et_change_pwd_nouveau_2);

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            user= MainActivity.getCurrentUser();
                if(user.getPassword().toString().equals( MD5.getEncodedPassword(et_pwd_actuel.getText().toString()))){
                    if (et_pwd_new_1.getText().toString().equals(et_pwd_new_2.getText().toString())){
                        user.setPassword(MD5.getEncodedPassword(et_pwd_new_1.getText().toString()));
                        if(userViewModel.update(user)==1){
                            Toast.makeText(getContext(),"SUCCES!", Toast.LENGTH_LONG).show();
                            ((MainActivity) getActivity()).afficherHomeView();
                        }
                        else{
                            Toast.makeText(getContext(),"Echec!", Toast.LENGTH_LONG).show();
                        }

                    }
                    else{
                        Toast.makeText(getContext(),"Les deux mots de passe saisi ne sont pas identiques", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"Le mot de passe actuel fourni n'est pas correct : \n"+user.getPassword()+"\n"+MD5.getEncodedPassword(et_pwd_actuel.getText().toString()), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void setCurrentUser(User currentUser){
        user=currentUser;
    }

    public static User getCurrentUser(){
        return user;
    }

}
