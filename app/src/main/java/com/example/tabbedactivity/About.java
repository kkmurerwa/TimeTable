package com.example.tabbedactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;



public class About extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String themeKey = "themeKey";

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
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

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
                //Create intent to open the GitHub Repo hosting the timetable
                break;

            case R.id.report_bug:
                //Get system date for bug report
                Date bugReportDate = Calendar.getInstance().getTime();

                //Launch intent
                Intent bugReportEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "xwaxes@gmail.com", null))//Set recipient and open primary mail client
                .putExtra(Intent.EXTRA_SUBJECT, "Bug Report")//Set email subject
                .putExtra(Intent.EXTRA_TEXT,"While using your app on " +bugReportDate +", I discovered this bug:" +
                                "\n");//Set email body
                startActivity(Intent.createChooser(bugReportEmailIntent, null));
                break;

            case R.id.feedback_button:
                Intent feedbackEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "xwaxes@gmail.com", null))//Set recipient and open primary mail client
                        .putExtra(Intent.EXTRA_SUBJECT, "Feedback")//Set email subject
                        .putExtra(Intent.EXTRA_TEXT,"This is my feedback on the app:" +
                                "\n");//Set email body
                startActivity(Intent.createChooser(feedbackEmailIntent, null));
                break;

            default:
                break;
        }
    }
}
