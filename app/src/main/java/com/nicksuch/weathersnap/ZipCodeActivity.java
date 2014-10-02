package com.nicksuch.weathersnap;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ZipCodeActivity extends ActionBarActivity {

    TextView currentWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_code);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        String city = "Rome,IT";

        currentWeatherTextView = (TextView) findViewById(R.id.currentWeatherTextView);

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.zip_code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_zip_code, container, false);
            return rootView;
        }
    }

    public class JSONWeatherParser {
        JSONObject jObj = new JSONObject(data);

        JSONObject coordObj = getObject("coord", jObj);

        JSONObject sysObj = getObject("sys", jObj);
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    public class WeatherHttpClient {

        private String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        private String IMG_URL = "http://openweathermap.org/img/w/";


        public String getWeatherData(String location) {
            HttpURLConnection con = null ;
            InputStream is = null;

            try {
                con = (HttpURLConnection) ( new URL(BASE_URL + location)).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();

                // Let's read the response
                StringBuffer buffer = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while (  (line = br.readLine()) != null )
                    buffer.append(line + "\r\n");

                is.close();
                con.disconnect();
                return buffer.toString();
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            finally {
                try { is.close(); } catch(Throwable t) {}
                try { con.disconnect(); } catch(Throwable t) {}
            }

            return null;

        }

        public byte[] getImage(String code) {
            HttpURLConnection con = null ;
            InputStream is = null;
            try {
                con = (HttpURLConnection) ( new URL(IMG_URL + code)).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();

                // Let's read the response
                is = con.getInputStream();
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while ( is.read(buffer) != -1)
                    baos.write(buffer);

                return baos.toByteArray();
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            finally {
                try { is.close(); } catch(Throwable t) {}
                try { con.disconnect(); } catch(Throwable t) {}
            }

            return null;

        }
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = JSONWeatherParser.getWeather(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }
    }

    public class Weather {

        public Location location;
        public CurrentCondition currentCondition = new CurrentCondition();

        public class CurrentCondition {
            private int weatherId;
            private String condition;
            private String descr;

            public int getWeatherId() {
                return weatherId;
            }

            public void setWeatherId(int weatherId) {
                this.weatherId = weatherId;
            }

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
            }

            public String getDescr() {
                return descr;
            }

            public void setDescr(String descr) {
                this.descr = descr;
            }
        }
    }

    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);
        currentWeatherTextView.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
    }
}
