package com.example.oppari02;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);  /* Pikkunäppäin näytön alanurkassa (s-posti tms.) */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tähän jotain tekstiä joka tulee kun painaa alareunan Email-kuvaketta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View m = findViewById(R.id.textView);   //nämä kaksi laittaa sisällön/tekstin näkyvyyden
        m.setVisibility(View.VISIBLE);

        getJSON("http://91.158.253.42/fetch_all_stuff.php");

    }
    //tästä uutta (Tästä alkaa JSONin luku ^ olevasta osoitteesta

    private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                try {
                    loadIntoListView(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    //creating url
                    URL url = new URL(urlWebService);

                    //opening url
                    HttpURLConnection yhteys = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder stringBuilder = new StringBuilder();

                    //Use buffered reader to read the string
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(yhteys.getInputStream()));

                    //String to read values from each line
                    String json;

                    while ((json = bufferedReader.readLine()) != null) {
                        //appending it to string builder
                        stringBuilder.append(json + "\n");
                    }

                    //finally returning the read string
                    return stringBuilder.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    //Tähän loppuu JSONin luku
    //tästä alkaa jsonin parsetus

    private void loadIntoListView(String json) throws JSONException {
        //Creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);

        //creating a string array for listview
        String[] dataa = new String[jsonArray.length()];

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {
            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);

            //getting the name from the json object and putting it inside string array
            dataa[i] = obj.getString("temperature");
        }

        //Array adapter to load data into list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataa);

        //Attaching adapter to listview
        listView.setAdapter(arrayAdapter);

    }

    //Tähän loppuu JSON parsetus

    @Override
    public void onBackPressed() { //Takaisinpäin näppäimen toiminta
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) { //Jos edellinen sivu olemassa, mene siihen
            drawer.closeDrawer(GravityCompat.START);
        } else {    //Muuten
            super.onBackPressed();  //Poistu koko sovelluksesta
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                launchMainActivity();
                break;
            case R.id.nav_temperature:
                launchTemperatureActivity();
                break;
            case R.id.nav_humidity:
                launchHumidityActivity();
                break;
            case R.id.nav_airPressure:
                launchAirPressureActivity();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void launchMainActivity() {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }

    private void launchTemperatureActivity() {
        Intent temperature = new Intent(this, temperatureActivity.class);
        startActivity(temperature);
    }

    private void launchHumidityActivity() {
        Intent humidity = new Intent(this, humidityActivity.class);
        startActivity(humidity);
    }

    private void launchAirPressureActivity() {
        Intent airPressure = new Intent(this, airPressureActivity.class);
        startActivity(airPressure);
    }

}
