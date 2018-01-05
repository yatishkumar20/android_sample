package com.yatish.ex.singaleinstancebackstack;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {


    public static Activity b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        b = this;

        Button button3 = (Button) findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Main3Activity.this,MainActivity.class);
                i.putExtra("finishingallact","yes");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //TaskStackBuilder.create(Main3Activity.this).addNextIntentWithParentStack(i).startActivities();
                startActivity(i);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("On Destroy","Main3");
    }
}
