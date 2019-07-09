package com.example.embededSystem1;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Activity_control extends AppCompatActivity {

    SeekBar mSeekBarMoist;
    SeekBar mSeekBarWater;
    TextView mTextViewMoist;
    TextView mTextViewWater;
    Button mButton;
    private Long progressValueMoist = 0L;
    private Long progressValueWater = 0L;
    private DatabaseReference mDatabaseReference;
    private Query generalQuery;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mTextViewMoist = (TextView) findViewById(R.id.textProgressMoist);
        mTextViewWater = (TextView) findViewById(R.id.textProgressWater);

        Init();

        //TODO max for seekBar need might change
        mSeekBarMoist = (SeekBar) findViewById(R.id.seekMoist);
        mSeekBarMoist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValueMoist = (long) progress;
                mTextViewMoist.setText(String.valueOf(progressValueMoist));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mDatabaseReference.child("Threshold").setValue(progressValueMoist);
                Toast.makeText(Activity_control.this, progressValueMoist + " Set", Toast.LENGTH_SHORT).show();
            }
        });

            mSeekBarWater = (SeekBar) findViewById(R.id.seekWater);
            mSeekBarWater.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressValueWater = (long) progress;
                mTextViewWater.setText(String.valueOf(progressValueWater));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mDatabaseReference.child("Threshold_Water").setValue(progressValueWater);
                Toast.makeText(Activity_control.this, progressValueWater + " Set", Toast.LENGTH_SHORT).show();
            }
        });

        mButton = (Button) findViewById(R.id.buttonWater);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference.child("Pump").setValue(1);
            }
        });

    }

    void Init(){

        mDatabaseReference.child("Threshold").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressValueMoist = (long)dataSnapshot.getValue();
                mTextViewMoist.setText(progressValueMoist.toString());
                mSeekBarMoist.setProgress(progressValueMoist.intValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabaseReference.child("Threshold_Water").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressValueWater = (long) dataSnapshot.getValue();
                    mTextViewWater.setText(progressValueWater.toString());
                    mSeekBarWater.setProgress(progressValueWater.intValue());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
