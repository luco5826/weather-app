package com.insegno.luca.weatherapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void searchCity(View view){
        String city = "";
        try {
            city = ((EditText) findViewById(R.id.cityInput)).getText().toString();
            if (city.isEmpty()) {
                throw new IllegalArgumentException("nessuna città");
            } else{
                String iniziale = city.substring(0, 1);
                String finale = city.substring(1);
                city = iniziale.toUpperCase() + finale;
            }
        }catch(IllegalArgumentException e){
                Toast.makeText(MainActivity.this, R.string.city_error, Toast.LENGTH_SHORT).show();
            }

        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=");//serve la key per la API
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            JSONObject json = new JSONObject(buff.readLine());
            //lettura oggetto JSON + estrazione
            double longitudine = json.getJSONObject("coord").getDouble("lon");
            double latitudine = json.getJSONObject("coord").getDouble("lat");
            double tempK = json.getJSONObject("main").getDouble("temp");
            double tempC = tempK - 273.15;
            String sky = json.getJSONArray("weather").getJSONObject(0).getString("main");
            int pressione = json.getJSONObject("main").getInt("pressure");
            int umidità = json.getJSONObject("main").getInt("humidity");
            double tempMinK = json.getJSONObject("main").getDouble("temp_min");
            double tempMaxK = json.getJSONObject("main").getDouble("temp_max");
            double tempMinC = tempMinK - 273.15;
            double tempMaxC = tempMaxK - 273.15;

            //Log di controllo
            Log.i("WEATHER", "Latitudine:" + latitudine + ", Longitudine:" + longitudine);
            Log.i("WEATHER", "temperatura" + tempC);
            //formattatore per la temperatura
            NumberFormat formatter = NumberFormat.getInstance();

            Intent data = new Intent(MainActivity.this, DataActivity.class);

            data.putExtra("city", city);
            data.putExtra("sky", sky);
            data.putExtra("coords", (latitudine + ", " + longitudine));
            data.putExtra("temp", formatter.format(tempC) + " °C");
            data.putExtra("pressure", formatter.format(pressione)+" hPa");
            data.putExtra("humidity", formatter.format(umidità) + "%");
            data.putExtra("tempMinC", formatter.format(tempMinC) + " °C");
            data.putExtra("tempMaxC", formatter.format(tempMaxC) + " °C");

            startActivity(data);
            ((EditText)findViewById(R.id.cityInput)).setText("");






        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}
