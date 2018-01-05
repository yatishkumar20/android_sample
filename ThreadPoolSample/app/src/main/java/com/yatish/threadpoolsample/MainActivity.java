package com.yatish.threadpoolsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    /*
   * Gets the number of available cores
   * (not always the same as the maximum number of cores)
   */
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1000;

    // Sets the Time Unit to Milliseconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.MILLISECONDS;

    // Used to update UI with work progress
    private int count = 0;
    private int count1 = 0;
    private int count2 = 0;

    // This is the runnable task that we will run 100 times
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // Do some work that takes 50 milliseconds
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the UI with progress
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    count++;
                    String msg = count < 100 ? "working " : "done ";
                    updateStatus(msg + count);
                }
            });

        }
    };

    private Runnable mRunnable1 = new Runnable() {
        @Override
        public void run() {
            // Do some work that takes 50 milliseconds
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the UI with progress
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    count1++;
                    String msg = count1 < 100 ? "working " : "done ";
                    updateStatus1(msg + count1);
                }
            });

        }
    };

    private Runnable mRunnable2 = new Runnable() {
        @Override
        public void run() {
            // Do some work that takes 50 milliseconds
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the UI with progress
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    count2++;
                    String msg = count2 < 100 ? "working " : "done ";
                    updateStatus2(msg + count2);
                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // button click - performs work on a single thread
    public void buttonClickSingleThread(View view) {
        count = 0;
        count1 = 0;
        count2 = 0;
        Executor mSingleThreadExecutor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 100; i++) {
            mSingleThreadExecutor.execute(mRunnable);
            mSingleThreadExecutor.execute(mRunnable1);
            mSingleThreadExecutor.execute(mRunnable2);
        }
    }

    // button click - performs work using a thread pool
    public void buttonClickThreadPool(View view) {
        count = 0;
        count1 = 0;
        count2 = 0;
        ThreadPoolExecutor mThreadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES + 5,   // Initial pool size
                NUMBER_OF_CORES + 8,   // Max pool size
                KEEP_ALIVE_TIME,       // Time idle thread waits before terminating
                KEEP_ALIVE_TIME_UNIT,  // Sets the Time Unit for KEEP_ALIVE_TIME
                new LinkedBlockingDeque<Runnable>());  // Work Queue




        for (int i = 0; i < 100; i++) {
            mThreadPoolExecutor.execute(mRunnable);
            mThreadPoolExecutor.execute(mRunnable1);
            mThreadPoolExecutor.execute(mRunnable2);
        }
    }

    private void updateStatus(String msg) {
        ((TextView) findViewById(R.id.text)).setText(msg);
    }

    private void updateStatus1(String msg) {
        ((TextView) findViewById(R.id.text1)).setText(msg);
    }

    private void updateStatus2(String msg) {
        ((TextView) findViewById(R.id.text2)).setText(msg);
    }
}
