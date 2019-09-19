package com.example.tabbedactivity;

import android.annotation.TargetApi;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainFragment";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @TargetApi(24)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting...");


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    @TargetApi(24)
    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MonFragment(), "Monday");
        adapter.addFragment(new TueFragment(), "Tuesday");
        adapter.addFragment(new WedFragment(), "Wednesday");
        adapter.addFragment(new ThurFragment(), "Thursday");
        adapter.addFragment(new FriFragment(), "Friday");
        viewPager.setAdapter(adapter);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                viewPager.setCurrentItem(0);
                break;
            case Calendar.TUESDAY:
                viewPager.setCurrentItem(1);
                break;
            case Calendar.WEDNESDAY:
                viewPager.setCurrentItem(2);
                break;
            case Calendar.THURSDAY:
                viewPager.setCurrentItem(3);
                break;
            case Calendar.FRIDAY:
                viewPager.setCurrentItem(4);
                break;
            default:
                viewPager.setCurrentItem(0);
                break;

        }
    }
}