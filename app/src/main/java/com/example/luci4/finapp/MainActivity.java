package com.example.luci4.finapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.ceil;
import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    static int sum = 0,allowanceamt = 1000;

    static List<String> items,date,id,noofdays,perday;
    static List<Double> amount;
    ArrayAdapter<String> arrayAdapter;

    Button button,reminder;

    FirebaseDatabase database;
    DatabaseReference databaseGoals,allowance;

    SimpleDateFormat dateFormat;
    String deleteid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        FirebaseApp.initializeApp(getApplicationContext());

        database = FirebaseDatabase.getInstance();
        databaseGoals = database.getReference("Goals");
        allowance = database.getReference("allowance");

        listView = findViewById(R.id.list);
        button = findViewById(R.id.button);
        reminder = findViewById(R.id.reminder);

        items = new ArrayList<>();
        amount = new ArrayList<>();
        date = new ArrayList<>();
        id = new ArrayList<>();
        noofdays = new ArrayList<>();
        perday = new ArrayList<>();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewinfo = new Intent(MainActivity.this, Main3Activity.class);
                viewinfo.putExtra("id",items.get(position));
                viewinfo.putExtra("amount",amount.get(position));
                viewinfo.putExtra("date",date.get(position));
                viewinfo.putExtra("days",noofdays.get(position));
                viewinfo.putExtra("amountperday",perday.get(position));
                startActivity(viewinfo);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long i) {
                deleteid = id.get(position);

                DatabaseReference dgoal = databaseGoals.child(deleteid);
                dgoal.removeValue();
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseGoals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                items.clear();
                amount.clear();
                date.clear();
                noofdays.clear();
                perday.clear();

                for(DataSnapshot goalSnapshot: dataSnapshot.getChildren()){
                    Goals goal = goalSnapshot.getValue(Goals.class);

                    items.add(goal.getName());
                    amount.add(goal.getAmount());
                    date.add(goal.getGoaldate());
                    id.add(goal.getId());

                    String goaldate = goal.getGoaldate();
                    String setdate = goal.getSetdate();
                    long days = 0;
                    try{
                        Date date1 = dateFormat.parse(goaldate);
                        Date date2 = dateFormat.parse(setdate);
                        long diff = date2.getTime() - date1.getTime();
                        days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    }
                    catch (Exception e)
                    {
                        Log.e("DateError:","Check Main Activity");
                    }

                    String d = Long.toString(days);
                    int da = Integer.parseInt(d);
                    int amountperday = (int) (round(goal.getAmount())/da);
                    sum+=amountperday;
                    noofdays.add(d);
                    perday.add(Integer.toString(amountperday));
                }

                arrayAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        allowance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_update,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent update = new Intent(getApplicationContext(), Update.class);
        startActivity(update);

        return super.onOptionsItemSelected(item);
    }

    public void addGoal(View view) {
        Intent add = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(add);
    }

    public void reminder(View view){
        Intent remind = new Intent(getApplicationContext(), Main4Activity.class);
        startActivity(remind);
    }


}
