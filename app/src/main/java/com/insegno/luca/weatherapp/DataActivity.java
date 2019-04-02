package com.insegno.luca.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent intent = getIntent();

        //aggiornamento della vista
        ((TextView)findViewById(R.id.city)).setText(intent.getStringExtra("city"));
        ((TextView)findViewById(R.id.sky)).setText(intent.getStringExtra("sky"));
        ((TextView)findViewById(R.id.coords)).setText(intent.getStringExtra("coords"));
        ((TextView)findViewById(R.id.temp)).setText(intent.getStringExtra("temp"));
        ((TextView)findViewById(R.id.pressure)).setText(intent.getStringExtra("pressure"));
        ((TextView)findViewById(R.id.humidity)).setText(intent.getStringExtra("humidity"));
        ((TextView)findViewById(R.id.tempMin)).setText(intent.getStringExtra("tempMinC"));
        ((TextView)findViewById(R.id.tempMax)).setText(intent.getStringExtra("tempMaxC"));
    }
}
