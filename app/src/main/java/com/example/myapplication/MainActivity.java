package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) this.findViewById(R.id.button);

        button.setOnClickListener(view -> {

            StudentDatabase sdb = new StudentDatabase(getBaseContext());
            sdb.getWritableDatabase();
            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}