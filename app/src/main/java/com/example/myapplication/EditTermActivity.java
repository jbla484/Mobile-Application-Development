package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class EditTermActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private Button dateButton;
    private Button dateButton2;

    public class EditTerm extends AsyncTask<String, Object, String> {
        StudentDatabase dbConnector = new StudentDatabase(getApplicationContext());

        @Override
        protected String doInBackground(String... params) {
            dbConnector.updateTerm(params[0], params[1], params[2], params[3]);
            return "done";
        }
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Term Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);

        initDatePicker();
        EditText edit = (EditText) findViewById(R.id.termTitleTextEdit);
        edit.setText(getIntent().getStringExtra("termName"));

        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getIntent().getStringExtra("termStart"));

        dateButton2 = findViewById(R.id.datePickerButton2);
        dateButton2.setText(getIntent().getStringExtra("termEnd"));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button button = (Button) this.findViewById(R.id.editTermButton);
        button.setOnClickListener(view -> {
            EditText termTitle = (EditText) findViewById(R.id.termTitleTextEdit);
            String termTitleString = termTitle.getText().toString();

            Button termStart = (Button) findViewById(R.id.datePickerButton);
            String termStartString = termStart.getText().toString();

            Button termEnd = (Button) findViewById(R.id.datePickerButton2);
            String termEndString = termEnd.getText().toString();

            new EditTerm().execute(getIntent().getStringExtra("termId"), termTitleString, termStartString, termEndString);
            Log.i("INFORMATION", "onClick: Added to database");

            Intent intent = new Intent(EditTermActivity.this, TermDetailsActivity.class);
            intent.putExtra("monthValue", getIntent().getStringExtra("monthValueTerm"));
            intent.putExtra("termName", getIntent().getStringExtra("termNameTerm"));
            intent.putExtra("termStart", getIntent().getStringExtra("termStartTerm"));
            intent.putExtra("termEnd", getIntent().getStringExtra("termEndTerm"));
            intent.putExtra("termNameAndDate", getIntent().getStringExtra("termNameAndDateTerm"));
            intent.putExtra("termId", getIntent().getStringExtra("termIdTerm"));
            startActivity(intent);
        });

        Button button2 = (Button) this.findViewById(R.id.datePickerButton);
        button2.setOnClickListener(view -> openDatePicker(view));

        Button button3 = (Button) this.findViewById(R.id.datePickerButton2);
        button3.setOnClickListener(view -> openDatePicker2(view));
    }

    private String getTodaysDate() {
        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        month += 1;
        int day = calender.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case android.R.id.home:
                //SHARE COURSE NOTES
                onBackPressed();
                return true;
        }
        return true;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        String[] split = getIntent().getStringExtra("termStart").split(" ");


        Calendar calender = Calendar.getInstance();
        int year = Integer.parseInt(split[2]);
        int month = getMonthNumber(split[0]);
        int day = Integer.parseInt(split[1]);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        DatePickerDialog.OnDateSetListener dateSetListener2 = (datePicker, year1, month1, day1) -> {
            month1 += 1;
            String date = makeDateString(day1, month1, year1);
            dateButton2.setText(date);
        };

        String[] split2 = getIntent().getStringExtra("termEnd").split(" ");


        Calendar calender2 = Calendar.getInstance();
        int year2 = Integer.parseInt(split2[2]);
        int month2 = getMonthNumber(split2[0]);
        int day2 = Integer.parseInt(split2[1]);

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

    private int getMonthNumber(String month) {
        if (month.equals("JAN")) {
            return 0;
        }
        if (month.equals("FEB")) {
            return 1;
        }
        if (month.equals("MAR")) {
            return 2;
        }
        if (month.equals("APR")) {
            return 3;
        }
        if (month.equals("MAY")) {
            return 4;
        }
        if (month.equals("JUN")) {
            return 5;
        }
        if (month.equals("JUL")) {
            return 6;
        }
        if (month.equals("AUG")) {
            return 7;
        }
        if (month.equals("SEP")) {
            return 8;
        }
        if (month.equals("OCT")) {
            return 9;
        }
        if (month.equals("NOV")) {
            return 10;
        }
        if (month.equals("DEC")) {
            return 11;
        }
        return 0;
    }

    private void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private void openDatePicker2(View view) {
        datePickerDialog2.show();
    }

}