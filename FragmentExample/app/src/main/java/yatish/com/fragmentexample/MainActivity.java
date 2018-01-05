package yatish.com.fragmentexample;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
                    implements Headlinefragment.OnHeadlineSelectedListner{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(findViewById(R.id.fragment_container) != null){

            if(savedInstanceState != null){
                return;
            }

            Headlinefragment headlinefragment = new Headlinefragment();
            headlinefragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_container, headlinefragment).commit();

        }
    }

    @Override
    public void onArticleSelected(int position) {

        Articlefragment articlefragment = (Articlefragment) getSupportFragmentManager().findFragmentById(R.id.articlr_fragment);

        if(articlefragment != null){

            articlefragment.updateArticleView(position);

        } else {

            Articlefragment articlefragment1 = new Articlefragment();
            Bundle bundle= new Bundle();
            bundle.putInt(Articlefragment.ARG_POSITION,position);

            articlefragment1.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, articlefragment1).addToBackStack(null).commit();
        }
    }
}
