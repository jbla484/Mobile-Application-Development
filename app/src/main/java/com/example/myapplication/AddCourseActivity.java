package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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

import java.util.Calendar;
import java.util.Objects;

public class AddCourseActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private Button dateButton;
    private Button dateButton2;

    public class AddCourse extends AsyncTask<String, Object, String> {
        StudentDatabase dbConnector = new StudentDatabase(getApplicationContext());

        @Override
        protected String doInBackground(String... params) {
            dbConnector.addCourse(params[0], params[1], params[2], params[3], params[4], params[5], params[6], Integer.parseInt(params[7]), params[8]);
            return "done";
        }
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Course Added!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        dateButton2 = findViewById(R.id.datePickerButton2);
        dateButton2.setText(getTodaysDate());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.course_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = (Button) this.findViewById(R.id.addCourseButton);
        button.setOnClickListener(view -> {
            EditText courseTitle = (EditText) findViewById(R.id.courseTitleText);
            String courseTitleString = courseTitle.getText().toString();

            String courseStartString = dateButton.getText().toString();
            String courseEndString = dateButton2.getText().toString();
            String courseProgressString = spinner.getSelectedItem().toString();

            EditText courseInstructorName = (EditText) findViewById(R.id.courseInstructorName);
            String courseInstructorNameString = courseInstructorName.getText().toString();

            EditText courseInstructorPhone = (EditText) findViewById(R.id.courseInstructorPhone);
            String courseInstructorPhoneString = courseInstructorPhone.getText().toString();

            EditText courseInstructorEmail = (EditText) findViewById(R.id.courseInstructorEmail);
            String courseInstructorEmailString = courseInstructorEmail.getText().toString();

            EditText courseOptionalNote = (EditText) findViewById(R.id.courseOptionalNoteText);
            String courseOptionalNoteString = courseOptionalNote.getText().toString();

            StudentDatabase sdb = new StudentDatabase(getApplicationContext());
            new AddCourse().execute(courseTitleString, courseStartString, courseEndString, courseProgressString, courseInstructorNameString, courseInstructorPhoneString, courseInstructorEmailString, String.valueOf(DashboardActivity.term_id), courseOptionalNoteString);

            Log.i("INFORMATION", "onClick: Course added to database. Term ID: " + DashboardActivity.term_id);
            Intent intent = new Intent(AddCourseActivity.this, TermDetailsActivity.class);
            intent.putExtra("monthValue", getIntent().getStringExtra("monthValueTerm"));
            intent.putExtra("termName", getIntent().getStringExtra("termNameTerm"));
            intent.putExtra("termStart", getIntent().getStringExtra("termStartTerm"));
            intent.putExtra("termEnd", getIntent().getStringExtra("termEndTerm"));
            intent.putExtra("termNameAndDate", getIntent().getStringExtra("termNameAndDateTerm"));
            intent.putExtra("termId", getIntent().getStringExtra("termIdTerm"));
            Log.d("DEBUG", "onCreate: " + getIntent().getStringExtra("termIdTerm"));
            onBackPressed();
            startActivity(intent);
        });

        dateButton.setOnClickListener(this::openDatePicker);
        dateButton2.setOnClickListener(this::openDatePicker2);
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