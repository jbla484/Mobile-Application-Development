package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ClosingBackGroundService extends BroadcastReceiver {

    private static final String TAG = "DEBUG";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"service closed");
        context.stopService(DashboardActivity.mServiceIntent);
    }
}