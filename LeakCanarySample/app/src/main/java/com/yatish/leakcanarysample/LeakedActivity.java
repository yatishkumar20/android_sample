package com.yatish.leakcanarysample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.leakcanary.RefWatcher;

public class LeakedActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaked);

        View button = findViewById(R.id.async_task);
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startAsyncTask();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    void startAsyncTask() {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override protected Void doInBackground(Void... params) {
                // Do some slow work in background
                Log.i("Inside","Inside");
                SystemClock.sleep(20000);
                return null;
            }
        }.execute();
    }


}
