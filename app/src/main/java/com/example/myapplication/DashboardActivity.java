package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";
    public static int term_id;

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Log.d(TAG, "onCreate: ");

        //View terms
        StudentDatabase sdb = new StudentDatabase(getApplicationContext());
        Cursor c = sdb.getData();

        Button btn0 = findViewById(R.id.button_first);
        Button btn1 = findViewById(R.id.button_second);
        Button btn2 = findViewById(R.id.button_third);
        Button btn3 = findViewById(R.id.button_fourth);
        Button btn4 = findViewById(R.id.button_fifth);
        Button btn5 = findViewById(R.id.button_sixth);
        Button btn6 = findViewById(R.id.button_seventh);
        Button btn7 = findViewById(R.id.button_eighth);
        Button btn8 = findViewById(R.id.button_ninth);
        Button btn9 = findViewById(R.id.button_tenth);

        btn0.setOnClickListener(view -> {

            StudentDatabase sdb1 = new StudentDatabase(getApplicationContext());
            Cursor c1 = sdb1.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);

            while (c1.moveToNext()) {
                if (btn0.getText().toString().equals(c1.getString(c1.getColumnIndexOrThrow("title")))) {

                    term_id = Integer.parseInt(c1.getString(c1.getColumnIndexOrThrow("_id")));
                    String termTitle = c1.getString(c1.getColumnIndexOrThrow("title"));
                    String startDate = c1.getString(c1.getColumnIndexOrThrow("start_date"));
                    String endDate = c1.getString(c1.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn1.setOnClickListener(view -> {

            StudentDatabase sdb12 = new StudentDatabase(getApplicationContext());
            Cursor c12 = sdb12.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);
            while (c12.moveToNext()) {
                if (btn1.getText().toString().equals(c12.getString(c12.getColumnIndexOrThrow("title")))) {
                    term_id = Integer.parseInt(c12.getString(c12.getColumnIndexOrThrow("_id")));
                    String termTitle = c12.getString(c12.getColumnIndexOrThrow("title"));
                    String startDate = c12.getString(c12.getColumnIndexOrThrow("start_date"));
                    String endDate = c12.getString(c12.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn2.setOnClickListener(view -> {

            StudentDatabase sdb13 = new StudentDatabase(getApplicationContext());
            Cursor c13 = sdb13.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);
            while (c13.moveToNext()) {
                if (btn2.getText().toString().equals(c13.getString(c13.getColumnIndexOrThrow("title")))) {
                    term_id = Integer.parseInt(c13.getString(c13.getColumnIndexOrThrow("_id")));
                    String termTitle = c13.getString(c13.getColumnIndexOrThrow("title"));
                    String startDate = c13.getString(c13.getColumnIndexOrThrow("start_date"));
                    String endDate = c13.getString(c13.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn3.setOnClickListener(view -> {

            StudentDatabase sdb14 = new StudentDatabase(getApplicationContext());
            Cursor c14 = sdb14.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);
            while (c14.moveToNext()) {
                if (btn3.getText().toString().equals(c14.getString(c14.getColumnIndexOrThrow("title")))) {
                    term_id = Integer.parseInt(c14.getString(c14.getColumnIndexOrThrow("_id")));
                    String termTitle = c14.getString(c14.getColumnIndexOrThrow("title"));
                    String startDate = c14.getString(c14.getColumnIndexOrThrow("start_date"));
                    String endDate = c14.getString(c14.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn4.setOnClickListener(view -> {

            StudentDatabase sdb15 = new StudentDatabase(getApplicationContext());
            Cursor c15 = sdb15.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);
            while (c15.moveToNext()) {
                if (btn4.getText().toString().equals(c15.getString(c15.getColumnIndexOrThrow("title")))) {
                    term_id = Integer.parseInt(c15.getString(c15.getColumnIndexOrThrow("_id")));
                    String termTitle = c15.getString(c15.getColumnIndexOrThrow("title"));
                    String startDate = c15.getString(c15.getColumnIndexOrThrow("start_date"));
                    String endDate = c15.getString(c15.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn5.setOnClickListener(view -> {

            StudentDatabase sdb16 = new StudentDatabase(getApplicationContext());
            Cursor c16 = sdb16.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);
            while (c16.moveToNext()) {
                if (btn5.getText().toString().equals(c16.getString(c16.getColumnIndexOrThrow("title")))) {
                    term_id = Integer.parseInt(c16.getString(c16.getColumnIndexOrThrow("_id")));
                    String termTitle = c16.getString(c16.getColumnIndexOrThrow("title"));
                    String startDate = c16.getString(c16.getColumnIndexOrThrow("start_date"));
                    String endDate = c16.getString(c16.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn6.setOnClickListener(view -> {

            StudentDatabase sdb1 = new StudentDatabase(getApplicationContext());
            Cursor c1 = sdb1.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);

            while (c1.moveToNext()) {
                if (btn6.getText().toString().equals(c1.getString(c1.getColumnIndexOrThrow("title")))) {

                    term_id = Integer.parseInt(c1.getString(c1.getColumnIndexOrThrow("_id")));
                    String termTitle = c1.getString(c1.getColumnIndexOrThrow("title"));
                    String startDate = c1.getString(c1.getColumnIndexOrThrow("start_date"));
                    String endDate = c1.getString(c1.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn7.setOnClickListener(view -> {

            StudentDatabase sdb1 = new StudentDatabase(getApplicationContext());
            Cursor c1 = sdb1.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);

            while (c1.moveToNext()) {
                if (btn7.getText().toString().equals(c1.getString(c1.getColumnIndexOrThrow("title")))) {

                    term_id = Integer.parseInt(c1.getString(c1.getColumnIndexOrThrow("_id")));
                    String termTitle = c1.getString(c1.getColumnIndexOrThrow("title"));
                    String startDate = c1.getString(c1.getColumnIndexOrThrow("start_date"));
                    String endDate = c1.getString(c1.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn8.setOnClickListener(view -> {

            StudentDatabase sdb1 = new StudentDatabase(getApplicationContext());
            Cursor c1 = sdb1.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);

            while (c1.moveToNext()) {
                if (btn8.getText().toString().equals(c1.getString(c1.getColumnIndexOrThrow("title")))) {

                    term_id = Integer.parseInt(c1.getString(c1.getColumnIndexOrThrow("_id")));
                    String termTitle = c1.getString(c1.getColumnIndexOrThrow("title"));
                    String startDate = c1.getString(c1.getColumnIndexOrThrow("start_date"));
                    String endDate = c1.getString(c1.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn9.setOnClickListener(view -> {

            StudentDatabase sdb1 = new StudentDatabase(getApplicationContext());
            Cursor c1 = sdb1.getCourseLength();

            Intent intent = new Intent(DashboardActivity.this, TermDetailsActivity.class);

            while (c1.moveToNext()) {
                if (btn9.getText().toString().equals(c1.getString(c1.getColumnIndexOrThrow("title")))) {

                    term_id = Integer.parseInt(c1.getString(c1.getColumnIndexOrThrow("_id")));
                    String termTitle = c1.getString(c1.getColumnIndexOrThrow("title"));
                    String startDate = c1.getString(c1.getColumnIndexOrThrow("start_date"));
                    String endDate = c1.getString(c1.getColumnIndexOrThrow("end_date"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id);
                }
            }
        });

        btn0.setVisibility(View.INVISIBLE);
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        btn5.setVisibility(View.INVISIBLE);
        btn6.setVisibility(View.INVISIBLE);
        btn7.setVisibility(View.INVISIBLE);
        btn8.setVisibility(View.INVISIBLE);
        btn9.setVisibility(View.INVISIBLE);

        if (c.getCount() >= -1) {
            int i = 0;
            while (c.moveToNext()) {
                switch (i) {
                    case 0:
                        btn0.setText(c.getString(1));
                        btn0.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                    case 1:
                        btn1.setText(c.getString(1));
                        btn1.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                    case 2:
                        btn2.setText(c.getString(1));
                        btn2.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                    case 3:
                        btn3.setText(c.getString(1));
                        btn3.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                    case 4:
                        btn4.setText(c.getString(1));
                        btn4.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                    case 5:
                        btn5.setText(c.getString(1));
                        btn5.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                    case 6:
                        btn6.setText(c.getString(1));
                        btn6.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                    case 7:
                        btn7.setText(c.getString(1));
                        btn7.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                    case 8:
                        btn8.setText(c.getString(1));
                        btn8.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                    case 9:
                        btn9.setText(c.getString(1));
                        btn9.setVisibility(View.VISIBLE);
                        ++i;
                        break;
                }
            }
            if (!(c.getCount() == -1)) {
                //Nothing
                System.out.println();
            } else {
                c.moveToPrevious();
                term_id = Integer.parseInt(c.getString(0));
            }
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> startActivity(new Intent(DashboardActivity.this, AddTermActivity.class)));
    }

    public void findRemainingWeeks(String termTitle, String startDate, String endDate, Intent intent, int term_id) {
        String[] parseStart = startDate.split(" ");
        int monthIndexStart = 0;

        if (parseStart[0].equals("JAN")) {
            monthIndexStart = 1;
        }
        if (parseStart[0].equals("FEB")) {
            monthIndexStart = 2;
        }
        if (parseStart[0].equals("MAR")) {
            monthIndexStart = 3;
        }
        if (parseStart[0].equals("APR")) {
            monthIndexStart = 4;
        }
        if (parseStart[0].equals("MAY")) {
            monthIndexStart = 5;
        }
        if (parseStart[0].equals("JUN")) {
            monthIndexStart = 6;
        }
        if (parseStart[0].equals("JUL")) {
            monthIndexStart = 7;
        }
        if (parseStart[0].equals("AUG")) {
            monthIndexStart = 8;
        }
        if (parseStart[0].equals("SEP")) {
            monthIndexStart = 9;
        }
        if (parseStart[0].equals("OCT")) {
            monthIndexStart = 10;
        }
        if (parseStart[0].equals("NOV")) {
            monthIndexStart = 11;
        }
        if (parseStart[0].equals("DEC")) {
            monthIndexStart = 12;
        }

        String[] parseEnd = endDate.split(" ");
        int monthIndexEnd = 0;

        if (parseEnd[0].equals("JAN")) {
            monthIndexEnd = 1;
        }
        if (parseEnd[0].equals("FEB")) {
            monthIndexEnd = 2;
        }
        if (parseEnd[0].equals("MAR")) {
            monthIndexEnd = 3;
        }
        if (parseEnd[0].equals("APR")) {
            monthIndexEnd = 4;
        }
        if (parseEnd[0].equals("MAY")) {
            monthIndexEnd = 5;
        }
        if (parseEnd[0].equals("JUN")) {
            monthIndexEnd = 6;
        }
        if (parseEnd[0].equals("JUL")) {
            monthIndexEnd = 7;
        }
        if (parseEnd[0].equals("AUG")) {
            monthIndexEnd = 8;
        }
        if (parseEnd[0].equals("SEP")) {
            monthIndexEnd = 9;
        }
        if (parseEnd[0].equals("OCT")) {
            monthIndexEnd = 10;
        }
        if (parseEnd[0].equals("NOV")) {
            monthIndexEnd = 11;
        }
        if (parseEnd[0].equals("DEC")) {
            monthIndexEnd = 12;
        }

        int numOfWeeks = 0;
        try {
            @SuppressLint("SimpleDateFormat") Date userStart = new SimpleDateFormat("yyyy MM dd").parse(parseStart[2] + " " + monthIndexStart + " " + parseStart[1]);
            @SuppressLint("SimpleDateFormat") Date userEnd = new SimpleDateFormat("yyyy MM dd").parse(parseEnd[2] + " " + monthIndexEnd + " " + parseEnd[1]);
            assert userEnd != null;
            assert userStart != null;
            long diff =  userEnd.getTime() - userStart.getTime();
            int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            numOfWeeks = (numOfDays / 7);

            Log.i(TAG, "onClick: NUMBER OF WEEKS: " + numOfWeeks);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        intent.putExtra("termMonthValue", String.valueOf(numOfWeeks));
        intent.putExtra("termName", termTitle);
        intent.putExtra("termStart", startDate);
        intent.putExtra("termEnd", endDate);
        intent.putExtra("termNameAndDate", termTitle + "\n" + startDate + " - \n" + endDate);
        intent.putExtra("termId", String.valueOf(term_id));
        startActivity(intent);
    }
}