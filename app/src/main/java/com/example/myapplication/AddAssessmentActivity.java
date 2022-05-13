package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class AddAssessmentActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private Button dateButton;
    private Button dateButton2;

    public class AddAssessment extends AsyncTask<String, Object, String> {
        StudentDatabase dbConnector = new StudentDatabase(getApplicationContext());

        @Override
        protected String doInBackground(String... params) {
            dbConnector.addAssessment(params[0], params[1], params[2], params[3], Integer.parseInt(params[4]));
            return "done";
        }
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Assessment Added!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        dateButton2 = findViewById(R.id.datePickerButton2);
        dateButton2.setText(getTodaysDate());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = this.findViewById(R.id.addAssessmentButton);
        button.setOnClickListener(view -> {
            EditText courseTitle = findViewById(R.id.assessmentTitleText);
            String assessmentTitleString = courseTitle.getText().toString();

            Button termStart = dateButton;
            String assessmentStartString = termStart.getText().toString();

            Button termEnd = dateButton2;
            String assessmentEndString = termEnd.getText().toString();

            String assessmentProgressString = spinner.getSelectedItem().toString();

            int course_id = getIntent().getIntExtra("course_id", -1);
            new AddAssessment().execute(assessmentTitleString, assessmentStartString, assessmentEndString, assessmentProgressString, String.valueOf(course_id));
            Log.i(TAG, "onClick: Assessment added to database. Term ID: ");

            Intent intent = new Intent(AddAssessmentActivity.this, CourseDetailsActivity.class);

            intent.putExtra("termMonthValue", getIntent().getStringExtra("termMonthValue"));
            intent.putExtra("termName", getIntent().getStringExtra("termName"));
            intent.putExtra("termStart", getIntent().getStringExtra("termStart"));
            intent.putExtra("termEnd", getIntent().getStringExtra("termEnd"));
            intent.putExtra("termNameAndDate", getIntent().getStringExtra("termNameAndDate"));
            intent.putExtra("termId", getIntent().getStringExtra("termId"));

            intent.putExtra("courseMonthValue", getIntent().getStringExtra("courseMonthValue"));
            intent.putExtra("courseNameAndDate", getIntent().getStringExtra("courseNameAndDate"));
            intent.putExtra("courseId", getIntent().getStringExtra("courseId"));
            intent.putExtra("courseOptionalNotes", getIntent().getStringExtra("courseOptionalNotes"));
            startActivity(intent);
        });

        Button button2 = this.findViewById(R.id.datePickerButton);
        button2.setOnClickListener(this::openDatePicker);

        Button button3 = this.findViewById(R.id.datePickerButton2);
        button3.setOnClickListener(this::openDatePicker2);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case android.R.id.home:
                //SHARE COURSE NOTES
                onBackPressed();
                return true;
            default:
                //SHARE COURSE NOTES
                Log.d(TAG, "onOptionsItemSelected: error");
                return true;
        }
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
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        DatePickerDialog.OnDateSetListener dateSetListener2 = (datePicker, year1, month1, day1) -> {
            month1 += 1;
            String date = makeDateString(day1, month1, year1);
            dateButton2.setText(date);
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