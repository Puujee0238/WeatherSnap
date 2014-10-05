package com.nicksuch.weathersnap;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;


public class WebActivity extends ActionBarActivity {

    public static String defaultURL = "http://www.wunderground.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        loadWeb(defaultURL, this.findViewById(R.id.webView));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_reload) {
            EditText editText = (EditText) findViewById(R.id.editTextUri);
            String currentURL = editText.getText().toString();
            if (currentURL.length() > 0 ) {
                loadWeb(currentURL, this.findViewById(R.id.webView));
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Please enter a URL";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadWeb (String url, View view) {
        WebView myWebView = (WebView) view;
        myWebView.loadUrl(url);
    }
}
