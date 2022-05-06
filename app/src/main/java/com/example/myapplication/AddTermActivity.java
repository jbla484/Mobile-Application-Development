package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class AddTermActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private Button dateButton;
    private Button dateButton2;

    public class AddTerm extends AsyncTask<String, Object, String> {
        StudentDatabase dbConnector = new StudentDatabase(getApplicationContext());

        @Override
        protected String doInBackground(String... params) {
            dbConnector.addTerm(params[0], params[1], params[2]);
            return "done";
        }
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Term Added!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        dateButton2 = findViewById(R.id.datePickerButton2);
        dateButton2.setText(getTodaysDate());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button button = (Button) this.findViewById(R.id.addTermButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText termTitle = (EditText) findViewById(R.id.termTitleText);
                String termTitleString = termTitle.getText().toString();

                Button termStart = (Button) findViewById(R.id.datePickerButton);
                String termStartString = termStart.getText().toString();

                Button termEnd = (Button) findViewById(R.id.datePickerButton2);
                String termEndString = termEnd.getText().toString();

                StudentDatabase sdb = new StudentDatabase(getApplicationContext());
                new AddTerm().execute(termTitleString, termStartString, termEndString);
                Log.i("INFORMATION", "onClick: Term Added to database");
                startActivity(new Intent(AddTermActivity.this, DashboardActivity.class));
            }
        });

        Button button2 = (Button) this.findViewById(R.id.datePickerButton);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openDatePicker(view);
            }
        });

        Button button3 = (Button) this.findViewById(R.id.datePickerButton2);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openDatePicker2(view);
            }
        });
    }

    private String getTodaysDate() {
        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        month += 1;
        int day = calender.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = makeDateString(day, month, year);
                dateButton2.setText(date);
            }
        };

        Calendar calender2 = Calendar.getInstance();
        int year2 = calender2.get(Calendar.YEAR);
        int month2 = calender2.get(Calendar.MONTH);
        int day2 = calender2.get(Calendar.DAY_OF_MONTH);

        int style2 = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog2 = new DatePickerDialog(this, style2, dateSetListener2, year2, month2, day2);

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1) {
            return "JAN";
        }
        if (month == 2) {
            return "FEB";
        }
        if (month == 3) {
            return "MAR";
        }
        if (month == 4) {
            return "APR";
        }
        if (month == 5) {
            return "MAY";
        }
        if (month == 6) {
            return "JUN";
        }
        if (month == 7) {
            return "JUL";
        }
        if (month == 8) {
            return "AUG";
        }
        if (month == 9) {
            return "SEP";
        }
        if (month == 10) {
            return "OCT";
        }
        if (month == 11) {
            return "NOV";
        }
        if (month == 12) {
            return "DEC";
        }
        return "JAN";
    }

    private void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private void openDatePicker2(View view) {
        datePickerDialog2.show();
    }

}