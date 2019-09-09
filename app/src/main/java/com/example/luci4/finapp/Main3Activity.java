package com.example.luci4.finapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {

    TextView t1,t2,t3,t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        setContentView( R.layout.activity_main3);

        t1 = findViewById(R.id.title);
        t2 = findViewById(R.id.amount);
        t3 = findViewById(R.id.date);
        t4 = findViewById(R.id.perday);

        Intent intent =  getIntent();

        String name = intent.getExtras().getString("id");
        double amount = intent.getExtras().getDouble("amount");
        String amt = Double.toString(amount);

        //String ndays = intent.getExtras().getString("days");
        String aperday = intent.getExtras().getString("amountperday");

        String date = intent.getExtras().getString("date");

        t1.setText(name);
        t2.setText("Amount: "+amt);
        t3.setText("Date: "+date);
        t4.setText("Per day: "+aperday);
    }
}
