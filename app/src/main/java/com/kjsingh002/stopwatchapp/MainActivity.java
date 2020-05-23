package com.kjsingh002.stopwatchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView displayWatch;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private Handler handler;
    private boolean running = false,wasRunning = false;
    private int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        setContentView(R.layout.activity_main);
        displayWatch = findViewById(R.id.display_watch);
        startButton = findViewById(R.id.start);
        stopButton = findViewById(R.id.stop);
        resetButton = findViewById(R.id.reset);
        handler = new Handler();
        runTimer();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seconds = 0;
                running = false;
                displayWatch.setText(R.string.reset);
            }
        });
    }

    private void runTimer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds % 3600)/60;
                int second = seconds % 60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,second);
                displayWatch.setText(time);
                if (running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds",seconds);
        outState.putBoolean("running",running);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning){
            running = wasRunning;
        }
    }
}