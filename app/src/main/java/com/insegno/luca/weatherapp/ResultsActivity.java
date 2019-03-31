package com.insegno.luca.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent source = getIntent();

        // settaggio delle caselle di testo
        ((TextView)findViewById(R.id.city)).setText(source.getStringExtra("city"));
        ((TextView)findViewById(R.id.sky)).setText(source.getStringExtra("sky"));
        ((TextView)findViewById(R.id.coords)).setText(source.getStringExtra("coords"));
        ((TextView)findViewById(R.id.temp)).setText(source.getStringExtra("temp"));
        ((TextView)findViewById(R.id.temp_min)).setText(source.getStringExtra("temp_min"));
        ((TextView)findViewById(R.id.temp_max)).setText(source.getStringExtra("temp_max"));
        ((TextView)findViewById(R.id.humidity)).setText(source.getStringExtra("humidity"));
        ((TextView)findViewById(R.id.pressure)).setText(source.getStringExtra("pressure"));
    }
}
