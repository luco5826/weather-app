package com.insegno.luca.weatherapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void search (View view) {

        String city = ((EditText)findViewById(R.id.city_input)).getText().toString();

        try {
            URL url = new URL ("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));

     //       Log.i("Weather", buff.readLine());

            JSONObject json = new JSONObject (buff.readLine());

            double longitudine = json.getJSONObject("coord").getDouble("lon");
            double latitudine = json.getJSONObject("coord").getDouble("lat");
            double temperaturaK = json.getJSONObject("main").getDouble("temp");
            double temperaturaC = temperaturaK - 273.15;

            String sky = json.getJSONArray("weather").getJSONObject(0).getString("main");


            Log.i("Weather", "Latitudine:" + latitudine + " longitudine: " + longitudine);

            NumberFormat formatter = NumberFormat.getInstance();

            ((TextView)findViewById (R.id.city)).setText(city);
            ((TextView)findViewById (R.id.sky)).setText(sky);

            ((TextView)findViewById (R.id.coords)).setText(String.valueOf (latitudine + " , " + longitudine));
            ((TextView)findViewById (R.id.temp)).setText(String.valueOf (formatter.format(temperaturaC) + "°C"));

            ((EditText)findViewById (R.id.city_input)).setText("");





        } catch (IOException  | JSONException e )  {

            e.printStackTrace();
        }




    }
}
