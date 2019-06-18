package com.example.embededSystem1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class chartSelection extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> readingType = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_selection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        readingType.add("Temperature");
        readingType.add("Humidity");
        readingType.add("Soil Moisture");

        Log.d("debug", "onCreate: " + readingType.toString());
        listView = findViewById(R.id.list_view);

        //list adapter
        adapter = new ArrayAdapter(this, R.layout.list_row, readingType);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent;

                switch (position) {
                    case 0:
                        Log.d("position", "onItemClick: " + parent.getItemAtPosition(position));
                        intent = new Intent(chartSelection.this, Activity_chart.class);
                        intent.putExtra("myKey",parent.getItemAtPosition(position).toString());
                        startActivity(intent);

                        break;
                    case 1:
                        Log.d("position", "onItemClick: " + parent.getItemAtPosition(position));
                        intent = new Intent(chartSelection.this, Activity_chart.class);
                        intent.putExtra("myKey", parent.getItemAtPosition(position).toString());
                        startActivity(intent);

                        break;
                    case 2:
                        Log.d("position", "onItemClick: " + parent.getItemAtPosition(position));
                        intent = new Intent(chartSelection.this, Activity_chart.class);
                        intent.putExtra("myKey",parent.getItemAtPosition(position).toString());
                        startActivity(intent);
                        break;

                }

            }
        });


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
