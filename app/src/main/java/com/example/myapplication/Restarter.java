package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Restarter extends BroadcastReceiver {

    Alarm alarm = new Alarm();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("INFO", "onReceive: restarted");
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            alarm.setAlarm(context);
        }
        Intent serviceIntent = new Intent(context, NotificationService.class);
        context.startService(serviceIntent);
    }
}
