package com.example.tabbedactivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String themeKey = "themeKey";

    private static final String TAG = "MainFragment";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Toolbar mTopToolbar;
    private MenuItem darkThemeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Handles shared preferences
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(themeKey)) {
            if (sharedpreferences.getString(themeKey, "").equals("1")){
                setTheme(R.style.DarkTheme);
            }
            else setTheme(R.style.AppTheme);
        }
        else{
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(themeKey, "0");
            editor.commit();
            restartApp();//Bug fix for app not showing ActionBar on first install
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopToolbar = findViewById(R.id.my_toolbar);


        Log.d(TAG, "onCreate: Starting...");
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Creates the menu inflater
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (sharedpreferences.getString(themeKey, "").equals("1")){
            menu.findItem(R.id.dark_theme).setTitle("Light Theme");
            menu.findItem(R.id.three_dot_menu).setIcon(R.drawable.three_dot_menu_white);
        }
        else {
            menu.findItem(R.id.dark_theme).setTitle("Dark Theme");
            menu.findItem(R.id.three_dot_menu).setIcon(R.drawable.three_button_menu);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Handles clicks on clicked menu items
        int id = item.getItemId();

        if (id==R.id.dark_theme){
            //Add additional features to change the themes
            if (sharedpreferences.getString(themeKey, "").equals("1")){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(themeKey, "0");
                editor.commit();
            }
            else {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(themeKey, "1");
                editor.commit();
            }
            restartApp();
        }
        if (id==R.id.about){
            //Creates an intent that opens the relevant page
            Intent openAboutPage = new Intent(this, About.class);
            startActivity(openAboutPage);
        }
        return true;
    }

    public void restartApp(){
        Intent restartapp = new Intent(this, MainActivity.class);
        startActivity(restartapp);
        finish();
    }

    @TargetApi(24)
    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        //Set the fragments for each activity on the tabbed layout
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