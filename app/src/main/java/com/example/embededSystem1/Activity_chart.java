package com.example.embededSystem1;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.embededSystem1.helper.Data_struct;
import com.example.embededSystem1.helper.HourAxisValueFormatter;
import com.example.embededSystem1.helper.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Objects;

public class Activity_chart extends AppCompatActivity {

    //variable name
    TextView disGeneral;
    LineChart generalGraph;
    TextView text;

    private DatabaseReference mDatabaseReference;
    private Query generalQuery;
    private ArrayList<Data_struct> mData = new ArrayList<>();
    private Data_struct data = new Data_struct();
    private Long refTimestamp;
    private String temp;
   // private ArrayList<String> sensorlist = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        temp = Objects.requireNonNull(getIntent().getExtras()).getString("myKey");

        Log.d("String", "onCreate: " + temp);

        disGeneral = (TextView) findViewById(R.id.displayGeneral);
        generalGraph = (LineChart) findViewById(R.id.generalGraph);
        text = (TextView) findViewById(R.id.readingDesrib);

        text.setText(temp+ " :");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        update();


    }

    private void update(){

        generalQuery = mDatabaseReference.child(temp).orderByChild("timestamp").limitToLast(720);
        generalQuery.addChildEventListener(new ChildEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.exists()) {

                    mData.add(dataSnapshot.getValue(Data_struct.class));

                    switch (temp) {
                        case "Temperature":
                            disGeneral.setText(String.format("%s°C", mData.get(mData.size() - 1).getData().toString()));
                            break;

                        case "Humidity":
                            disGeneral.setText((mData.get(mData.size() - 1).getData().toString()) + " %");
                            break;

                        case "Soil Moisture":
                            disGeneral.setText((mData.get(mData.size() - 1).getData().toString()) + " %");
                            break;


                    }


                    //graphing
                    ArrayList<Entry> mEntries = new ArrayList<>();
                    refTimestamp = mData.get(0).getTimestamp()/1000;
                    IAxisValueFormatter xAxisFormater = new HourAxisValueFormatter(refTimestamp);
                    XAxis xAxis = generalGraph.getXAxis();
                    xAxis.setValueFormatter(xAxisFormater);

                    for(int x = 0; x<mData.size(); x++){
                        mEntries.add(new Entry(((mData.get(x).getTimestamp()/1000)-refTimestamp), mData.get(x).getData()));

                    }

                    LineDataSet dataSet = new LineDataSet(mEntries, temp);
                    LineData lineData = new LineData(dataSet);
                    lineData.setDrawValues(false);
                    MyMarkerView myMarkerView= new MyMarkerView(getApplicationContext(), R.layout.my_marker_view_layout, refTimestamp, temp);
                    myMarkerView.setChartView(generalGraph);
                    generalGraph.setMarker(myMarkerView);
                    generalGraph.setData(lineData);
                    generalGraph.invalidate();

                    //debug
                    Log.d("check", "onChildAdded: true");
                    Log.d("Iot", mData.get(0).getData().toString());
                    Log.d("Iot", "size : " + String.valueOf(mData.size()));


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
