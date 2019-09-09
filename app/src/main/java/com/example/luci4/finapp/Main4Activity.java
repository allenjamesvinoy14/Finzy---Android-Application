package com.example.luci4.finapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    EditText input;
    TextView output;
    Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        input = findViewById(R.id.input);
        output = findViewById(R.id.textView5);

        check = findViewById(R.id.button3);
    }

    public void onClick(View view){
        InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(input.getWindowToken(),0);

        String val = input.getText().toString();

        int total = Integer.parseInt(val);

        if((MainActivity.allowanceamt-total)<MainActivity.sum){
            output.setText("You need to control!");
        }
        else{
            output.setText("You are doing good!");
        }
    }
}
