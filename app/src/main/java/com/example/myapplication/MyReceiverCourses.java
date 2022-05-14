package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyReceiverCourses extends BroadcastReceiver {

    String channel_id = "test";
    static int notification_id;
    private static final String TAG = "DEBUG";
    public static boolean created = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context, channel_id);

        boolean startFound = false;
        boolean endFound = false;

        String title = intent.getStringExtra("courseTitleCopy");
        String start = intent.getStringExtra("courseStartCopy");
        String end = intent.getStringExtra("courseEndCopy");

        Log.i(TAG, "onPostExecute: Course title: " + title);
        Log.i(TAG, "onPostExecute: Course start: " + start);
        Log.i(TAG, "onPostExecute: Course end: " + end);

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
        Log.i(TAG, "\n\nChecking Courses:\n\n");

        // Course start and end dates
        String[] dateParts3 = start.split(" ");
        String dateString3 = getMonthIndex(dateParts3[0]) + "/" + dateParts3[1] + "/" + dateParts3[2];
        String dateStringMinus7 = getMonthIndex(dateParts3[0]) + "/" + (Integer.parseInt(dateParts3[1]) - 7) + "/" + dateParts3[2];

        String[] dateParts4 = end.split(" ");
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
        } else if (dateNow.compareTo(endDate) < 0 && dateNow.compareTo(endDateMinus7) > 0) {
            Log.i(TAG, "Course end date is within the next 7 days.");
            endFound = true;
        } else {
            Log.i(TAG, "Course start date or end date is not within the next 7 days.");
        }
        if (startFound) {
            // Set notification if there is an upcoming course.
            String body = title + " is starting within the next 7 days.";

            Intent openIntent = new Intent(context, CourseDetailsActivity.class);

            // TERM VALUES
            openIntent.putExtra("termMonthValue", intent.getStringExtra("termMonthValue"));
            openIntent.putExtra("termName", intent.getStringExtra("termName"));
            openIntent.putExtra("termStart", intent.getStringExtra("termStart"));
            openIntent.putExtra("termEnd", intent.getStringExtra("termEnd"));
            openIntent.putExtra("termNameAndDate", intent.getStringExtra("termNameAndDate"));
            openIntent.putExtra("termId", intent.getStringExtra("termId"));

            // COURSE VALUES
            openIntent.putExtra("courseMonthValue", intent.getStringExtra("courseMonthValue"));
            openIntent.putExtra("courseNameAndDate", intent.getStringExtra("courseNameAndDate"));
            openIntent.putExtra("courseId", intent.getStringExtra("courseId"));
            openIntent.putExtra("courseOptionalNotes", intent.getStringExtra("courseOptionalNotes"));

            PendingIntent pendingIntent = PendingIntent.getActivity(context, MainActivity.numAlert++, openIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channel_id);
            Notification notification = notificationBuilder.setOngoing(false)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setContentTitle("Upcoming Course")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentText(body)
                    .setChannelId(channel_id)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();

            startFound = false;

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(notification_id++, notification);
        }
        if (endFound) {
            // Set notification if there is an upcoming course.
            String body = title + " is ending within the next 7 days.";

            Intent openIntent = new Intent(context, CourseDetailsActivity.class);

            // TERM VALUES
            openIntent.putExtra("termMonthValue", intent.getStringExtra("termMonthValue"));
            openIntent.putExtra("termName", intent.getStringExtra("termName"));
            openIntent.putExtra("termStart", intent.getStringExtra("termStart"));
            openIntent.putExtra("termEnd", intent.getStringExtra("termEnd"));
            openIntent.putExtra("termNameAndDate", intent.getStringExtra("termNameAndDate"));
            openIntent.putExtra("termId", intent.getStringExtra("termId"));

            // COURSE VALUES
            openIntent.putExtra("courseMonthValue", intent.getStringExtra("courseMonthValue"));
            openIntent.putExtra("courseNameAndDate", intent.getStringExtra("courseNameAndDate"));
            openIntent.putExtra("courseId", intent.getStringExtra("courseId"));
            openIntent.putExtra("courseOptionalNotes", intent.getStringExtra("courseOptionalNotes"));

            PendingIntent pendingIntent = PendingIntent.getActivity(context, MainActivity.numAlert++, openIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channel_id);
            Notification notification = notificationBuilder.setOngoing(false)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setContentTitle("Course Ending Soon")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentText(body)
                    .setChannelId(channel_id)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();

            endFound = false;

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(notification_id++, notification);
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

    private void createNotificationChannel(Context context,String CHANNEL_ID) {
        created = true;
        if (!MyReceiverAssessments.created) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}