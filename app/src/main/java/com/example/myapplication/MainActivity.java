package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = this.findViewById(R.id.button);

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