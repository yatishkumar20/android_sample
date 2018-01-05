package tasktest.yatish.com.tasktest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.button1);

        Button b2 = (Button) findViewById(R.id.button2);

        Button b3 = (Button) findViewById(R.id.button3);

        Button b4 = (Button) findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i1);


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(i1);


            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(MainActivity.this, Main4Activity.class);
                startActivity(i1);


            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(MainActivity.this, Main5Activity.class);
                startActivity(i1);


            }
        });




    }
}
