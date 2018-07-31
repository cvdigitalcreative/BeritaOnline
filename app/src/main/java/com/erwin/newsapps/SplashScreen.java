package com.erwin.newsapps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    private ProgressBar progressBar;
    private Timer timer;
    private int progressStatus = 0;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar =  findViewById(R.id.progressBar);
        textView =  findViewById(R.id.textView);
        // Start long running operation in a background thread

        final long period = 100;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (progressStatus<100){
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    progressBar.setProgress(progressStatus);
                    progressStatus++;
                }else{
                    //closing the timer
                    timer.cancel();
                    Intent intent =new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(intent);
                    // close this activity
                    finish();
                }
            }
        }, 0, period);
    }
}
