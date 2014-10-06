package com.nicksuch.weathersnap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class PhotoActivity extends ActionBarActivity {

    private Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        report = new Report();
        super.onCreate(savedInstanceState);

        // Begin with main data entry view,NewReportFragment
        setContentView(R.layout.activity_photo);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewReportFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public Report getCurrentReport() {
        return report;
    }

}
