package com.yatish.ex.singaleinstancebackstack;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.yatish.ex.singaleinstancebackstack.Main2Activity.a;
import static com.yatish.ex.singaleinstancebackstack.Main3Activity.b;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(getIntent().getStringExtra("finishingallact")!=null)
        {

            if(getIntent().getStringExtra("finishingallact").toLowerCase().equals("yes"))
            {

                AndroidKiller.killActivity(a);
                AndroidKiller.killActivity(b);

            }
        }



        Button button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);

            }
        });

    }
}
