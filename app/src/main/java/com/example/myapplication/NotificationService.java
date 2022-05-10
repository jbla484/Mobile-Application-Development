package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    private static final String TAG = "DEBUG";
    public int counter=0;

    private final Alarm alarm = new Alarm();
    private final ArrayList<Alarm> ALARMS = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        startMyOwnForeground();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        Log.d(TAG, "startMyOwnForeground: ");
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        alarm.setAlarm(this);
        ALARMS.add(alarm);

        Log.d(TAG, "onStartCommand: notification");
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: notification");
        super.onDestroy();
        stopTimerTask();

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
                    Log.d(TAG, "run: caught exception");
                }

                Log.i(TAG, "onPostExecute: Today's date: " + dateNow);
                Log.i(TAG, "\n\nChecking Courses and Assessments:\n\n");

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
                        Log.d(TAG, "run: caught exception");
                    }

                    Date endDate = null;
                    try {
                        endDate = sdf.parse(dateString4);
                    } catch (ParseException pe) {
                        Log.d(TAG, "run: caught exception");
                    }

                    Log.d(TAG, "run: course title " + result.getString(1));
                    Log.d(TAG, "run: start date " + startDate);
                    Log.d(TAG, "run: end date " + endDate);

                    Date startDateMinus4 = null;
                    try {
                        startDateMinus4 = sdf.parse(dateStringMinus7);
                    } catch (ParseException pe) {
                        Log.d(TAG, "run: caught exception");
                    }

                    Date endDateMinus7 = null;
                    try {
                        endDateMinus7 = sdf.parse(dateStringMinus7End);
                    } catch (ParseException pe) {
                        Log.d(TAG, "run: caught exception");
                    }

                    // Compare dates
                    assert dateNow != null;
                    if (dateNow.compareTo(startDate) < 0 && dateNow.compareTo(startDateMinus4) > 0) {
                        Log.i(TAG, "Course start date is within the next 7 days.");
                        startFound = true;
                        break;
                    }
                    if (dateNow.compareTo(endDate) < 0 && dateNow.compareTo(endDateMinus7) > 0) {
                        Log.i(TAG, "Course end date is within the next 7 days.");
                        endFound = true;
                        break;
                    } else {
                        Log.i(TAG, "Course start date or end date is not within the next 7 days.");

                    }
                }
                if (startFound) {
                    // Set notification if there is an upcoming course.
                    Intent notiIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent notiPendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notiIntent, PendingIntent.FLAG_IMMUTABLE);

                    String body = "You have an upcoming course starting within the next 7 days.";

                    Intent closingIntent = new Intent(NotificationService.this, ClosingBackGroundService.class);
                    PendingIntent actionIntent = PendingIntent.getBroadcast(NotificationService.this, 0, closingIntent, PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationService.this, "example.permanence");
                    Notification notification = notificationBuilder.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .addAction(R.mipmap.ic_launcher, "Close Notification", actionIntent)
                            .setContentTitle("Upcoming Course")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                            .setContentText(body)
                            .setContentIntent(notiPendingIntent)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .build();
                    notification.flags = 1337;
                    startForeground(2, notification);
                    Log.i(TAG, "Count =========  " + (counter++));

                    startFound = false;
                }
                if (endFound) {
                    // Set notification if there is an upcoming course.
                    Intent notiIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent notiPendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notiIntent, PendingIntent.FLAG_IMMUTABLE);

                    String body = "You have an course ending within the next 7 days.";

                    Intent closingIntent = new Intent(NotificationService.this, ClosingBackGroundService.class);
                    PendingIntent actionIntent = PendingIntent.getBroadcast(NotificationService.this, 0, closingIntent, PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationService.this, "example.permanence");
                    Notification notification = notificationBuilder.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .addAction(R.mipmap.ic_launcher, "Close Notification", actionIntent)
                            .setContentTitle("Course Ending Soon")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                            .setContentText(body)
                            .setContentIntent(notiPendingIntent)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .build();
                    notification.flags = 1337;
                    //FIXME PROBLEM AREA
                    startForeground(2, notification);
                    Log.i(TAG, "Count =========  " + (counter++));

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
                        Log.d(TAG, "run: caught exception");
                    }

                    Date endDate = null;
                    try {
                        endDate = sdf.parse(assessmentStringEnd);
                    } catch (ParseException pe) {
                        Log.d(TAG, "run: caught exception");
                    }

                    Log.d(TAG, "run: assessment title " + result2.getString(1));
                    Log.d(TAG, "run: start date " + startDate);
                    Log.d(TAG, "run: end date " + endDate);

                    Date startDateMinus4 = null;
                    try {
                        startDateMinus4 = sdf.parse(assessmentStringMinus7Start);
                    } catch (ParseException pe) {
                        Log.d(TAG, "run: caught exception");
                    }

                    Date endDateMinus7 = null;
                    try {
                        endDateMinus7 = sdf.parse(assessmentStringMinus7End);
                    } catch (ParseException pe) {
                        Log.d(TAG, "run: caught exception");
                    }

                    // Compare dates
                    assert dateNow != null;
                    if (dateNow.compareTo(startDate) < 0 && dateNow.compareTo(startDateMinus4) > 0) {
                        Log.i(TAG, "Assessment start date is within the next 7 days.");
                        startFound = true;
                        break;
                    }
                    if (dateNow.compareTo(endDate) < 0 && dateNow.compareTo(endDateMinus7) > 0) {
                        Log.i(TAG, "Assessment end date is within the next 7 days.");
                        endFound = true;
                        break;
                    } else {
                        Log.i(TAG, "Assessment start date or end date is not within the next 7 days.");
                    }
                }
                if (startFound) {
                    // Set notification if there is an upcoming assessment.
                    Intent notiIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent notiPendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notiIntent, PendingIntent.FLAG_IMMUTABLE);

                    String body = "You have an upcoming assessment starting within the next 7 days.";

                    Intent closingIntent = new Intent(NotificationService.this, ClosingBackGroundService.class);
                    PendingIntent actionIntent = PendingIntent.getBroadcast(NotificationService.this, 0, closingIntent, PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationService.this, "example.permanence");
                    Notification notification = notificationBuilder.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .addAction(R.mipmap.ic_launcher, "Close Notification", actionIntent)
                            .setContentTitle("Upcoming Assessment")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                            .setContentText(body)
                            .setContentIntent(notiPendingIntent)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .build();
                    notification.flags = 1337;
                    startForeground(2, notification);
                    Log.i(TAG, "Count =========  " + (counter++));

                }
                if (endFound) {
                    // Set notification if there is an upcoming assessment.
                    Intent notiIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent notiPendingIntent = PendingIntent.getActivity(NotificationService.this, 0, notiIntent, PendingIntent.FLAG_IMMUTABLE);

                    String body = "You have an assessment ending within the next 7 days.";

                    Intent closingIntent = new Intent(NotificationService.this, ClosingBackGroundService.class);
                    PendingIntent actionIntent = PendingIntent.getBroadcast(NotificationService.this, 0, closingIntent, PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationService.this, "example.permanence");
                    Notification notification = notificationBuilder.setOngoing(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .addAction(R.mipmap.ic_launcher, "Close Notification", actionIntent)
                            .setContentTitle("Assessment Ending Soon")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                            .setContentText(body)
                            .setContentIntent(notiPendingIntent)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .setCategory(Notification.CATEGORY_SERVICE)
                            .build();
                    notification.flags = 1337;
                    startForeground(2, notification);
                    Log.i(TAG, "Count =========  " + (counter++));

                }
            }
        };
        // Wait 4 hours to receive the notification.
        timer.schedule(timerTask, 14400000, 43200000);
    }

    public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
