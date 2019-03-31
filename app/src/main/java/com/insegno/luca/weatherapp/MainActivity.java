package com.insegno.luca.weatherapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

    public void searchCity(View view)
    {
        String city = ((EditText)findViewById(R.id.city_input)).getText().toString();
        city = city.substring(0, 1).toUpperCase() + city.substring(1);
        String key = "";

        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + key);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JSONObject json = new JSONObject(buff.readLine());

            // recupero e creazione dati e risorse necessari
            double longitudine = json.getJSONObject("coord").getDouble("lon");
            double latitudine = json.getJSONObject("coord").getDouble("lat");
            double tempK = json.getJSONObject("main").getDouble("temp");
            double tempC = tempK - 273.15;
            String sky = json.getJSONArray("weather").getJSONObject(0).getString("main");
            double tempKmin = json.getJSONObject("main").getDouble("temp_min");
            double tempCmin = tempKmin - 273.15;
            double tempKmax = json.getJSONObject("main").getDouble("temp_max");
            double tempCmax = tempKmax - 273.15;
            double pressure = json.getJSONObject("main").getDouble("pressure");
            double humidity = json.getJSONObject("main").getDouble("humidity");

            NumberFormat formatter = NumberFormat.getInstance();

            Intent data = new Intent(MainActivity.this, ResultsActivity.class);
            // inserimeto dati nell'intent
            data.putExtra("city", json.getString("name"));
            data.putExtra("sky", sky);
            data.putExtra("coords", String.valueOf(latitudine + ", " + longitudine));
            data.putExtra("temp", String.valueOf(formatter.format(tempC) + " °C"));
            data.putExtra("temp_min", String.valueOf(formatter.format(tempCmin) + " °C"));
            data.putExtra("temp_max", String.valueOf(formatter.format(tempCmax) + " °C"));
            data.putExtra("humidity", String.valueOf(formatter.format(humidity) + "%"));
            data.putExtra("pressure", String.valueOf(formatter.format(pressure) + " hPa"));
            // svuotamento casella iniziale
            ((EditText)findViewById(R.id.city_input)).setText("");
            // lancio activity
            startActivity(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
