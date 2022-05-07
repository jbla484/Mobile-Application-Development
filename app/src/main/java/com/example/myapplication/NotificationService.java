package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    public int counter=0;

    Alarm alarm = new Alarm();

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        Log.d("DEBUG", "startMyOwnForeground: ");
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(chan);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        alarm.setAlarm(this);

        Log.d("DEBUG", "onStartCommand: notification");
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("DEBUG", "onDestroy: notification");
        startService(new Intent(this, NotificationService.class));
        super.onDestroy();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }

    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void run() {

                //Check for upcoming courses.
                boolean startFound = false;
                boolean endFound = false;
                StudentDatabase dbConnector = new StudentDatabase(getApplicationContext());
                Cursor result = dbConnector.getCourses();

                // Get today's date.
                Date currentTime = Calendar.getInstance().getTime();
                String[] dateParts = currentTime.toString().split(" ");
                String dateString = dateParts[1].toUpperCase() + " " + Integer.parseInt(dateParts[2]) + " " + dateParts[5];
                String[] dateParts2 = dateString.split(" ");
                String dateString2 = getMonthIndex(dateParts2[0]) + "/" + dateParts2[1] + "/" + dateParts2[2];

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                Date dateNow = null;

                try {
                    dateNow = sdf.parse(dateString2);
                } catch (ParseException pe) {

                }

                Log.i("INFO", "onPostExecute: Today's date: " + dateNow);
                Log.i("INFO", "\n\nChecking Courses and Assessments:\n\n");

                // If courses were found in the database, search them for their start and end time.
                while (result.moveToNext()) {

                    // Course start and end dates
                    String[] dateParts3 = result.getString(2).split(" ");
                    String dateString3 = getMonthIndex(dateParts3[0]) + "/" + dateParts3[1] + "/" + dateParts3[2];
                    String dateStringMinus7 = getMonthIndex(dateParts3[0]) + "/" + (Integer.parseInt(dateParts3[1]) - 7) + "/" + dateParts3[2];

                    String[] dateParts4 = result.getString(3).split(" ");
                    String dateString4 = getMonthIndex(dateParts4[0]) + "/" + dateParts4[1] + "/" + dateParts4[2];
                    String dateStringMinus7End = getMonthIndex(dateParts4[0]) + "/" + (Integer.parseInt(dateParts4[1]) - 7) + "/" + dateParts4[2];

                    Date startDate = null;
                    try {
                        startDate = sdf.parse(dateString3);
                    } catch (ParseException pe) {

                    }

                    Date endDate = null;
                    try {
                        endDate = sdf.parse(dateString4);
                    } catch (ParseException pe) {

                    }

                    Log.d("INFO", "run: course title " + result.getString(1));
                    Log.d("INFO", "run: start date " + startDate);
                    Log.d("INFO", "run: end date " + endDate);

                    Date startDateMinus4 = null;
                    try {
                        startDateMinus4 = sdf.parse(dateStringMinus7);
                    } catch (ParseException pe) {

                    }

                    Date endDateMinus7 = null;
                    try {
                        endDateMinus7 = sdf.parse(dateStringMinus7End);
                    } catch (ParseException pe) {

                    }

                    // Compare dates
                    if (dateNow.compareTo(startDate) < 0 && dateNow.compareTo(startDateMinus4) > 0) {
                        Log.i("INFO", "Course start date is within the next 7 days.");
                        startFound = true;
                        break;
                    } if (dateNow.compareTo(endDate) < 0 && dateNow.compareTo(endDateMinus7) > 0) {
                        Log.i("INFO", "Course end date is within the next 7 days.");
                        endFound = true;
                        break;
                    } else {
                        Log.i("INFO", "Course start date or end date is not within the next 7 days.");

                    }
                    if (startFound || endFound) {
                        break;
                    }
                }
                if (startFound) {
                    // Set notification if there is an upcoming course.
                    Intent notiIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent notiPendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notiIntent, PendingIntent.FLAG_IMMUTABLE);

                    String body = "You have an upcoming course starting within the next 7 days.";

                    Intent closingIntent = new Intent(NotificationService.this, ClosingBackGroundService.class);
                    PendingIntent actionIntent = PendingIntent.getBroadcast(NotificationService.this,0,closingIntent,PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationService.this, "example.permanence");
                    Notification notification = notificationBuilder.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .addAction(R.mipmap.ic_launcher,"Close Notification", actionIntent)
                            .setContentTitle("Upcoming Course")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                            .setContentText(body)
                            .setContentIntent(notiPendingIntent)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .build();
                    notification.flags = 1337;
                    startForeground(2, notification);
                    Log.i("Count", "=========  "+ (counter++));

                    startFound = false;
                }
                if (endFound) {
                    // Set notification if there is an upcoming course.
                    Intent notiIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent notiPendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notiIntent, PendingIntent.FLAG_IMMUTABLE);

                    String body = "You have an course ending within the next 7 days.";

                    Intent closingIntent = new Intent(NotificationService.this, ClosingBackGroundService.class);
                    PendingIntent actionIntent = PendingIntent.getBroadcast(NotificationService.this,0,closingIntent,PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationService.this, "example.permanence");
                    Notification notification = notificationBuilder.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .addAction(R.mipmap.ic_launcher,"Close Notification", actionIntent)
                            .setContentTitle("Course Ending Soon")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                            .setContentText(body)
                            .setContentIntent(notiPendingIntent)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .build();
                    notification.flags = 1337;
                    startForeground(2, notification);
                    Log.i("Count", "=========  "+ (counter++));

                    endFound = false;
                }

                // Check for upcoming courses.
                Cursor result2 = dbConnector.getAssessments();

                // If assessments were found in the database, search them for their start and end time.
                while (result2.moveToNext()) {

                    // Course start and end dates
                    String[] assessmentDateArrayStart = result2.getString(2).split(" ");
                    String assessmentStringStart = getMonthIndex(assessmentDateArrayStart[0]) + "/" + assessmentDateArrayStart[1] + "/" + assessmentDateArrayStart[2];
                    String assessmentStringMinus7Start = getMonthIndex(assessmentDateArrayStart[0]) + "/" + (Integer.parseInt(assessmentDateArrayStart[1]) - 7) + "/" + assessmentDateArrayStart[2];

                    String[] assessmentDateArrayEnd = result2.getString(3).split(" ");
                    String assessmentStringEnd = getMonthIndex(assessmentDateArrayEnd[0]) + "/" + assessmentDateArrayEnd[1] + "/" + assessmentDateArrayEnd[2];
                    String assessmentStringMinus7End = getMonthIndex(assessmentDateArrayEnd[0]) + "/" + (Integer.parseInt(assessmentDateArrayEnd[1]) - 7) + "/" + assessmentDateArrayEnd[2];

                    Date startDate = null;
                    try {
                        startDate = sdf.parse(assessmentStringStart);
                    } catch (ParseException pe) {

                    }

                    Date endDate = null;
                    try {
                        endDate = sdf.parse(assessmentStringEnd);
                    } catch (ParseException pe) {

                    }

                    Log.d("INFO", "run: assessment title " + result2.getString(1));
                    Log.d("INFO", "run: start date " + startDate);
                    Log.d("INFO", "run: end date " + endDate);

                    Date startDateMinus4 = null;
                    try {
                        startDateMinus4 = sdf.parse(assessmentStringMinus7Start);
                    } catch (ParseException pe) {

                    }

                    Date endDateMinus7 = null;
                    try {
                        endDateMinus7 = sdf.parse(assessmentStringMinus7End);
                    } catch (ParseException pe) {

                    }

                    // Compare dates
                    if (dateNow.compareTo(startDate) < 0 && dateNow.compareTo(startDateMinus4) > 0) {
                        Log.i("INFO", "Assessment start date is within the next 7 days.");
                        startFound = true;
                        break;
                    } if (dateNow.compareTo(endDate) < 0 && dateNow.compareTo(endDateMinus7) > 0) {
                        Log.i("INFO", "Assessment end date is within the next 7 days.");
                        endFound = true;
                        break;
                    } else {
                        Log.i("INFO", "Assessment start date or end date is not within the next 7 days.");
                    }
                    if (startFound || endFound) {
                        break;
                    }
                }
                if (startFound) {
                    // Set notification if there is an upcoming assessment.
                    Intent notiIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent notiPendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notiIntent, PendingIntent.FLAG_IMMUTABLE);

                    String body = "You have an upcoming assessment starting within the next 7 days.";

                    Intent closingIntent = new Intent(NotificationService.this, ClosingBackGroundService.class);
                    PendingIntent actionIntent = PendingIntent.getBroadcast(NotificationService.this,0,closingIntent,PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationService.this, "example.permanence");
                    Notification notification = notificationBuilder.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .addAction(R.mipmap.ic_launcher,"Close Notification", actionIntent)
                            .setContentTitle("Upcoming Assessment")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                            .setContentText(body)
                            .setContentIntent(notiPendingIntent)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .build();
                    notification.flags = 1337;
                    startForeground(2, notification);
                    Log.i("Count", "=========  "+ (counter++));

                    startFound = false;
                }
                if (endFound) {
                    // Set notification if there is an upcoming assessment.
                    Intent notiIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent notiPendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notiIntent, PendingIntent.FLAG_IMMUTABLE);

                    String body = "You have an assessment ending within the next 7 days.";

                    Intent closingIntent = new Intent(NotificationService.this, ClosingBackGroundService.class);
                    PendingIntent actionIntent = PendingIntent.getBroadcast(NotificationService.this,0,closingIntent,PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationService.this, "example.permanence");
                    Notification notification = notificationBuilder.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .addAction(R.mipmap.ic_launcher,"Close Notification", actionIntent)
                            .setContentTitle("Assessment Ending Soon")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                            .setContentText(body)
                            .setContentIntent(notiPendingIntent)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .build();
                    notification.flags = 1337;
                    startForeground(2, notification);
                    Log.i("Count", "=========  "+ (counter++));

                    endFound = false;
                }
            }
        };
        // Wait 4 hours to receive the notification.
        timer.schedule(timerTask, 1000, 1000);
        //timer.schedule(timerTask, 14400000, 43200000);
    }

    private int getMonthIndex(String month) {

        if (month.equals("JAN")) {
            return 1;
        }
        if (month.equals("FEB")) {
            return 2;
        }
        if (month.equals("MAR")) {
            return 3;
        }
        if (month.equals("APR")) {
            return 4;
        }
        if (month.equals("MAY")) {
            return 5;
        }
        if (month.equals("JUN")) {
            return 6;
        }
        if (month.equals("JUL")) {
            return 7;
        }
        if (month.equals("AUG")) {
            return 8;
        }
        if (month.equals("SEP")) {
            return 9;
        }
        if (month.equals("OCT")) {
            return 10;
        }
        if (month.equals("NOV")) {
            return 11;
        }
        if (month.equals("DEC")) {
            return 12;
        }
        return 0;
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
