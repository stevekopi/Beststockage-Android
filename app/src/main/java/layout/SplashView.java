package layout;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cd.sklservices.com.Beststockage.R;


public class SplashView extends Fragment {

    private final static String COMMON_TAG="Orientation Change ";
    private final static String FRAGMENT_NAME= SplashView.class.getSimpleName();
    private final static String TAG=FRAGMENT_NAME;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
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
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

}
