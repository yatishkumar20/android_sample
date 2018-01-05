package yatish.com.matrialex;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<ItemObject> data = getAllItemList();
        mAdapter = new MyAdapter(this,data);
        mRecyclerView.setAdapter(mAdapter);

        /*FloatingActionButton fab =  (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(new Explode());
                Intent intent = new Intent(MainActivity.this, MyOtherActivity.class);
                startActivity(intent,
                        ActivityOptions
                                .makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });*/


    }



    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("Peter James", "Vildansvagen 19, Lund Sweden", R.mipmap.ic_launcher_round));
        allItems.add(new ItemObject("Henry Jacobs", "3 Villa Crescent London, UK", R.mipmap.ic_launcher_round));
        allItems.add(new ItemObject("Bola Olumide", "Victoria Island Lagos, Nigeria", R.mipmap.ic_launcher_round));
        allItems.add(new ItemObject("Chidi Johnson", "New Heaven Enugu, Nigeria", R.mipmap.ic_launcher_round));
        allItems.add(new ItemObject("DeGordio Puritio", "Italion Gata, Padova, Italy", R.mipmap.ic_launcher_round));
        allItems.add(new ItemObject("Gary Cook", "San Fransisco, United States", R.mipmap.ic_launcher_round));
        allItems.add(new ItemObject("Edith Helen", "Queens Crescent, New Zealand", R.mipmap.ic_launcher_round));
        allItems.add(new ItemObject("Kingston Dude", "Ivory Lane, Abuja, Nigeria", R.mipmap.ic_launcher_round));
        allItems.add(new ItemObject("Edwin Bent", "Johnson Road, Port Harcourt, Nigeria", R.mipmap.ic_launcher_round));
        allItems.add(new ItemObject("Grace Praise", "Federal Quarters, Abuja Nigeria", R.mipmap.ic_launcher_round));

        return allItems;
    }

    public void submit(View b){

        //getWindow().setExitTransition(new Explode());
        Intent intent = new Intent(MainActivity.this, MyOtherActivity.class);
        startActivity(intent,
                ActivityOptions
                        .makeSceneTransitionAnimation(MainActivity.this).toBundle());

    }

}
