package com.example.luci4.finapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    EditText dateView;
    EditText goal;
    EditText amount;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    String dateval;

    FirebaseDatabase database;
    DatabaseReference databaseGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseApp.initializeApp(getApplicationContext());

        database = FirebaseDatabase.getInstance();
        databaseGoals = database.getReference("Goals");


        dateView = findViewById(R.id.time);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Main2Activity.this, android.R.style.Theme_Material_Dialog, dateSetListener, year, month, day);


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;

                dateval = day + "/" + month + "/" + year;
                dateView.setText(dateval);
            }
        };

    }

    public void onClick(View view)
    {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date datenow = new Date();
        String daten = formatter.format(datenow); // today's date

        goal = findViewById(R.id.goal);
        amount = findViewById(R.id.amount);

        String goalVal = goal.getText().toString();
        Double amountVal = Double.parseDouble(amount.getText().toString());

        String id = databaseGoals.push().getKey();

        Goals goal = new Goals(id,goalVal,amountVal,daten,dateval);

        databaseGoals.child(id).setValue(goal);

        Toast.makeText(this, "Goal Added", Toast.LENGTH_SHORT).show();

    }
}
