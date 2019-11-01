package com.example.tabbedactivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.michaelflisar.changelog.ChangelogBuilder;
import com.michaelflisar.changelog.classes.DefaultAutoVersionNameFormatter;
import com.michaelflisar.changelog.classes.ImportanceChangelogSorter;

public class MainActivity extends AppCompatActivity {

    public static final String myPreference = "mypref";
    public static final String themeKey = "themeKey";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Handles shared preferences
        sharedPreferences = getSharedPreferences(myPreference,
                Context.MODE_PRIVATE);
        if (sharedPreferences.contains(themeKey)) {
            if (sharedPreferences.getString(themeKey, "").equals("1")) {
                setTheme(R.style.DarkTheme);
            } else setTheme(R.style.AppTheme);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(themeKey, "0");
            editor.apply();
            restartApp();//Bug fix for app not showing ActionBar on first install
        }
        //Sets the main activity layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.my_toolbar);

        new SectionsPageAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        //Set the tab layout and its qualities
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        String versionCode = AboutActivity.getAppVersion(MainActivity.this);
        SharedPreferences preferences = getSharedPreferences("PREF", 0);
        boolean firstRun = preferences.getBoolean("firstRun" + versionCode, true);

        if (firstRun) {
            // show changelog
            showChangelog();
            // update status of firstRun
            preferences = getSharedPreferences("PREF", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstRun" + versionCode, false);
            editor.apply();

        }

    }

    private void showChangelog() {
        ChangelogBuilder builder = new ChangelogBuilder()
                .withUseBulletList(true)
                .withSorter(new ImportanceChangelogSorter())
                .withVersionNameFormatter(new DefaultAutoVersionNameFormatter(DefaultAutoVersionNameFormatter.Type.MajorMinor, "b"));
        builder.buildAndShowDialog(this, false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Creates the menu inflater
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (sharedPreferences.getString(themeKey, "").equals("1")) {
            menu.findItem(R.id.dark_theme).setTitle("Light Theme");
            menu.findItem(R.id.three_dot_menu).setIcon(R.drawable.three_dot_menu_white);
        } else {
            menu.findItem(R.id.dark_theme).setTitle("Dark Theme");
            menu.findItem(R.id.three_dot_menu).setIcon(R.drawable.three_button_menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handles clicks on clicked menu items
        int id = item.getItemId();

        if (id == R.id.dark_theme) {
            //Add additional features to change the themes
            if (sharedPreferences.getString(themeKey, "").equals("1")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(themeKey, "0");
                editor.apply();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(themeKey, "1");
                editor.apply();
            }

            //Restart app to employ changes
            restartApp();
        }
        if (id == R.id.about) {
            Intent openAboutPage = new Intent(this, AboutActivity.class);
            startActivity(openAboutPage);
        }
        return true;
    }

    public void restartApp() {
        Intent restartApp = new Intent(this, MainActivity.class);
        startActivity(restartApp);
        finish();
    }

    @TargetApi(24)
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        //Set the fragments for each activity on the tabbed layout
        adapter.addFragment(new MonFragment(), "Monday");
        adapter.addFragment(new TueFragment(), "Tuesday");
        adapter.addFragment(new WedFragment(), "Wednesday");
        adapter.addFragment(new ThurFragment(), "Thursday");
        adapter.addFragment(new FriFragment(), "Friday");
        viewPager.setAdapter(adapter);

        //Retrieves the day of the week for devices with API 24 and above and opens the app on that tab
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