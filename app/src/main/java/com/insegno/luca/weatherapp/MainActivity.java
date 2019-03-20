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

    public void searchCity(View view){
        String city = ((EditText)findViewById(R.id.cityInput)).getText().toString();
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            JSONObject json = new JSONObject(buff.readLine());
            //lettura oggetto JSON + estrazione
            double longitudine = json.getJSONObject("coord").getDouble("lon");
            double latitudine = json.getJSONObject("coord").getDouble("lat");
            double tempK = json.getJSONObject("main").getDouble("temp");
            double tempC = tempK - 273.15;
            String sky = json.getJSONArray("weather").getJSONObject(0).getString("main");
            //Log di controllo
            Log.i("WEATHER", "Latitudine:" + latitudine + ", Longitudine:" + longitudine);
            Log.i("WEATHER", "temperatura" + tempC);
            //formattatore per la temperatura
            NumberFormat formatter = NumberFormat.getInstance();

            //aggiornamento della vista
            ((TextView)findViewById(R.id.city)).setText(city);
            ((TextView)findViewById(R.id.sky)).setText(sky);
            ((TextView)findViewById(R.id.coords)).setText(latitudine + ", " + longitudine);
            ((TextView)findViewById(R.id.temp)).setText(String.valueOf(formatter.format(tempC) + " °C"));

            ((EditText)findViewById(R.id.cityInput)).setText("");


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}
