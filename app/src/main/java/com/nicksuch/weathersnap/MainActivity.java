package com.nicksuch.weathersnap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Parse setup
        ParseObject.registerSubclass(Report.class);
        Parse.initialize(this, "QSQ2VgUmVWZT4DKq31dWjzaGv0uVfpDzyDHpmdRs", "CLC2xjQJlFaKJQWseuAVvjVziWbhcEYqP1Gzujbo");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }

    public void checkWeather(View view) {
        Intent intent = new Intent(this, APIActivity.class);
        startActivity(intent);
    }

    public void weatherOnline(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }

    public void weatherMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }

}
