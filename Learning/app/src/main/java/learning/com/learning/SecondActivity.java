package learning.com.learning;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView header;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("2nd Activity Life Cycle", "onDestroy()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("2nd Activity Life Cycle", "onStop()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("2nd Activity Life Cycle", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("2nd Activity Life Cycle", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("2nd Activity Life Cycle", "onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("2nd Activity Life Cycle", "onRestart()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("2nd Activity Life Cycle", "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("2nd Activity Life Cycle", "onRestoreInstanceState()");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setupToolbar();

        setupViewPager();

        setupCollapsingToolbar();

        Log.d("2nd Activity Life Cycle", "onCreate()");


    }

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);
    }

    private void setupViewPager() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        header = (ImageView) findViewById(R.id.header);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int pos = tabLayout.getSelectedTabPosition();
                if (pos == 0) {
                    header.setImageResource(R.drawable.side_nav_bar);
                } else if (pos == 1) {
                    header.setImageResource(R.drawable.images);
                }else if (pos == 2) {
                    header.setImageResource(R.drawable.imagesqw);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "One");
        adapter.addFrag(new OneFragment(), "Two");
        adapter.addFrag(new OneFragment(), "Three");

        viewPager.setAdapter(adapter);

    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Learning");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
