package com.nicksuch.weathersnap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;


public class PhotoActivity extends ActionBarActivity {

    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        weather = new Weather();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        // Begin with main data entry view,
        // NewMealFragment
        setContentView(R.layout.activity_photo);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewMealFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }


    }


}
