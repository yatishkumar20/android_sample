package com.yatish.leakcanarysample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.leakcanary.RefWatcher;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        // Invoke the GC to accelerate the analysis.
        System.gc();
    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }*/
}
