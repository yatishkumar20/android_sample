package yatish.com.volleyex.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import yatish.com.volleyex.R;

public class CustomView extends AppCompatActivity {
    SimpleDrawingView sdv;
    ImageView iv_sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        sdv = (SimpleDrawingView) findViewById(R.id.simpleDrawingView1);
        iv_sign = (ImageView) findViewById(R.id.iv_sign);

    }

    public void getCanvas(View v){
        //iv_sign.setImageBitmap(sdv);

    }
}
