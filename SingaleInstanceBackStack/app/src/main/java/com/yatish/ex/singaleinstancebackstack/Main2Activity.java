package com.yatish.ex.singaleinstancebackstack;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    public static Activity a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        a = this;

        Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(i);

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("On Destroy","Main2");
    }
}
