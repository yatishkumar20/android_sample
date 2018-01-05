package yatish.com.matrialex;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static yatish.com.matrialex.R.id.iv_android;


public class MyOtherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_other);

        final View img =findViewById(iv_android);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =  new Intent(MyOtherActivity.this, Main2Activity.class);
                ActivityOptionsCompat apt = ActivityOptionsCompat.makeSceneTransitionAnimation(MyOtherActivity.this,img,"android");
                startActivity(i, apt.toBundle());
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
