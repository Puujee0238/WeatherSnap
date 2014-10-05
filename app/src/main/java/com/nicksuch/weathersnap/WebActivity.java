package com.nicksuch.weathersnap;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class WebActivity extends ActionBarActivity {

    public static String defaultURL = "http://www.wunderground.com";
    public static String alternateURL = "http://www.weather.com";
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        myWebView = (WebView) findViewById(R.id.webView);

        // Prevent pages from loading in other browser
        myWebView.setWebViewClient(new WebViewClient() {
           @Override
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               return false;
           }
        });
        // load defaultURL when page loads
        loadWeb(defaultURL, myWebView);
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
        if (id == R.id.action_alternate) {
            loadWeb(alternateURL, findViewById(R.id.webView));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadWeb (String url, View view) {
        WebView myWebView = (WebView) view;
        myWebView.loadUrl(url);
        Toast.makeText(getApplicationContext(), "Now loading " + url, Toast.LENGTH_LONG).show();
    }
}
