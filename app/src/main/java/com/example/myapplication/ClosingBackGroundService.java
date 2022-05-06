package com.example.myapplication;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class ClosingBackGroundService extends BroadcastReceiver {

    Context mainActContext;
    public void getMainActivityContext(Context context){

        mainActContext = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Service","closed");
        context.stopService(DashboardActivity.mServiceIntent);
    }
}