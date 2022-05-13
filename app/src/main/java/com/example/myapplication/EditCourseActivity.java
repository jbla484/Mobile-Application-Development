package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class EditCourseActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private Button dateButton;
    private Button dateButton2;

    public class EditCourse extends AsyncTask<String, Object, String> {
        StudentDatabase dbConnector = new StudentDatabase(getApplicationContext());

        @Override
        protected String doInBackground(String... params) {
            dbConnector.updateCourse(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]);
            return "done";
        }
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Course Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        initDatePicker();

        Log.i(TAG, "onCreate: course id - " + getIntent().getStringExtra("termId"));
        EditText edit = findViewById(R.id.courseTitleTextEdit);
        edit.setText(getIntent().getStringExtra("termName"));

        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getIntent().getStringExtra("termStart"));

        dateButton2 = findViewById(R.id.datePickerButton2);
        dateButton2.setText(getIntent().getStringExtra("termEnd"));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.course_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        String progress = getIntent().getStringExtra("termProgress");
        int value = Arrays.asList((getResources().getStringArray(R.array.course_status))).indexOf(progress);
        spinner.setSelection(value);

        EditText edit2 = findViewById(R.id.courseInstructorNameEdit);
        edit2.setText(getIntent().getStringExtra("termInstructorName"));

        EditText edit3 = findViewById(R.id.courseInstructorPhoneEdit);
        edit3.setText(getIntent().getStringExtra("termInstructorPhone"));

        EditText edit4 = findViewById(R.id.courseInstructorEmailEdit);
        edit4.setText(getIntent().getStringExtra("termInstructorEmail"));

        EditText edit5 = findViewById(R.id.courseOptionalNoteTextEdit);
        edit5.setText(getIntent().getStringExtra("optionalNotes"));

        Button button = this.findViewById(R.id.editCourseButton);
        button.setOnClickListener(view -> {
            String courseTitleString = edit.getText().toString();

            Button termStart = dateButton;
            String courseStartString = termStart.getText().toString();

            Button termEnd = dateButton2;
            String courseEndString = termEnd.getText().toString();

            String courseProgressString = spinner.getSelectedItem().toString();

            String courseInstructorNameString = edit2.getText().toString();

            String courseInstructorPhoneString = edit3.getText().toString();

            String courseInstructorEmailString = edit4.getText().toString();

            String courseOptionalNoteString = edit5.getText().toString();

            new EditCourse().execute(getIntent().getStringExtra("courseId"), courseTitleString, courseStartString, courseEndString, courseProgressString, courseInstructorNameString, courseInstructorPhoneString, courseInstructorEmailString, courseOptionalNoteString);
            Log.i(TAG, "onClick: Course added to database. Term ID: " + DashboardActivity.term_id);
            onBackPressed();
            //startActivity(new Intent(EditCourseActivity.this, DashboardActivity.class));
        });

        Button button2 = this.findViewById(R.id.datePickerButton);
        button2.setOnClickListener(this::openDatePicker);

        Button button3 = this.findViewById(R.id.datePickerButton2);
        button3.setOnClickListener(this::openDatePicker2);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which menu option was chosen
        if (item.getItemId() == android.R.id.home) {//SHARE COURSE NOTES
            onBackPressed();
            return true;
        }
        Log.d(TAG, "onOptionsItemSelected: error");
        return true;
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