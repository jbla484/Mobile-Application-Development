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
        Toast.makeText(context, "Restarted!", Toast.LENGTH_SHORT).show();
        Log.d("INFO", "onReceive: restarted");
        if ( intent.getAction().equals("android.intent.action.BOOT_COMPLETED") || intent.getAction().equals("android.intent.action.REBOOT") ||
                intent.getAction().equals("android.intent.action.QUICKBOOT_POWERON")|| intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED") ||
                intent.getAction().equals("android.hardware.usb.action.USB_STATE") || intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED") )
        {
            Log.d("INFO", "onReceive: BOOT_COMPLETED");
            Toast.makeText(context, "BOOT_COMPLETED!", Toast.LENGTH_SHORT).show();
            alarm.setAlarm(context);
        }
        Intent serviceIntent = new Intent(context, NotificationService.class);
        context.startService(serviceIntent);
    }
}
