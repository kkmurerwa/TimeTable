package com.example.tabbedactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class About extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    public static final String myPreference = "mypref";
    public static final String themeKey = "themeKey";

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
        }
        //Sets the main activity layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Event listener for Contribute button
        Button contribute = findViewById(R.id.contribute);
        contribute.setOnClickListener(this);

        //Event listener for Report Bug button
        Button bugReport = findViewById(R.id.report_bug);
        bugReport.setOnClickListener(this);

        //Event Listener for Feedback button
        Button feedback = findViewById(R.id.feedback_button);
        feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Method to handle button events
        switch (v.getId()) {

            case R.id.contribute:
                //Intent to open the GitHub Repo hosting the timetable
                Toast.makeText(this, "Opening project repo...", Toast.LENGTH_SHORT).show();
                Intent contributeIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/xwaxes01/TimeTable"));
                startActivity(contributeIntent);
                break;

            case R.id.report_bug:
                //Get system date for bug report
                Date bugReportDate = Calendar.getInstance().getTime();

                //Add extras and launch intent to send email
                Intent bugReportEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "xwaxes@gmail.com", null))//Set recipient and open primary mail client
                        .putExtra(Intent.EXTRA_SUBJECT, "Bug Report")//Set email subject
                        .putExtra(Intent.EXTRA_TEXT, "While using your app on " + bugReportDate + ", I discovered this bug:" +
                                "\n");//Set email body
                startActivity(Intent.createChooser(bugReportEmailIntent, null));
                break;

            case R.id.feedback_button:
                //Add extras and launch intent to send email
                Intent feedbackEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "xwaxes@gmail.com", null))//Set recipient and open primary mail client
                        .putExtra(Intent.EXTRA_SUBJECT, "Feedback")//Set email subject
                        .putExtra(Intent.EXTRA_TEXT, "This is my feedback on the app:" +
                                "\n");//Set email body
                startActivity(Intent.createChooser(feedbackEmailIntent, null));
                break;

            default:
                break;
        }
    }
}