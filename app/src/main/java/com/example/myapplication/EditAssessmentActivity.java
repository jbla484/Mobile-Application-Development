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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class EditAssessmentActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private Button dateButton;
    private Button dateButton2;

    public class EditAssessment extends AsyncTask<String, Object, String> {
        StudentDatabase dbConnector = new StudentDatabase(getApplicationContext());

        @Override
        protected String doInBackground(String... params) {
            dbConnector.updateAssessment(params[0], params[1], params[2], params[3], params[4]);
            return "done";
        }
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Assessment Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        initDatePicker();
        EditText edit = findViewById(R.id.assessmentTitleText);
        edit.setText(getIntent().getStringExtra("assessmentTitle"));

        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getIntent().getStringExtra("assessmentStart"));

        dateButton2 = findViewById(R.id.datePickerButton2);
        dateButton2.setText(getIntent().getStringExtra("assessmentEnd"));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        String progress = getIntent().getStringExtra("assessmentType");
        int value = Arrays.asList((getResources().getStringArray(R.array.assessment_type))).indexOf(progress);
        spinner.setSelection(value);

        Button button = this.findViewById(R.id.editAssessmentButton);
        button.setOnClickListener(view -> {
            String assessmentTitleString = edit.getText().toString();

            Button termStart = dateButton;
            String assessmentStartString = termStart.getText().toString();

            Button termEnd = dateButton2;
            String assessmentEndString = termEnd.getText().toString();

            String assessmentProgressString = spinner.getSelectedItem().toString();

            int course_id = getIntent().getIntExtra("assessmentId", 0);
            new EditAssessment().execute(String.valueOf(course_id), assessmentTitleString, assessmentStartString, assessmentEndString, assessmentProgressString);
            Log.i(TAG, "onClick: Assessment added to database. Term ID: ");

            Intent intent = new Intent(EditAssessmentActivity.this, ViewAssessmentActivity.class);
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

            intent.putExtra("monthValueCourse", getIntent().getStringExtra("monthValueCourse"));
            intent.putExtra("courseTitle", getIntent().getStringExtra("courseTitle"));
            intent.putExtra("courseStart", getIntent().getStringExtra("courseStart"));
            intent.putExtra("courseEnd", getIntent().getStringExtra("courseEnd"));
            intent.putExtra("courseType", getIntent().getStringExtra("courseType"));

            intent.putExtra("assessmentId", getIntent().getIntExtra("assessmentId", 0));
            intent.putExtra("assessmentMonthValue", getIntent().getStringExtra("assessmentMonthValue"));
            intent.putExtra("assessmentTitle", getIntent().getStringExtra("assessmentTitle"));
            intent.putExtra("assessmentStart", getIntent().getStringExtra("assessmentStart"));
            intent.putExtra("assessmentEnd", getIntent().getStringExtra("assessmentEnd"));
            intent.putExtra("assessmentType", getIntent().getStringExtra("assessmentType"));
            startActivity(intent);
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
        return false;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        String[] split = getIntent().getStringExtra("assessmentStart").split(" ");

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

        String[] split2 = getIntent().getStringExtra("assessmentEnd").split(" ");

        int year2 = Integer.parseInt(split2[2]);
        int month2 = getMonthNumber(split2[0]);
        int day2 = Integer.parseInt(split2[1]);

        int style2 = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog2 = new DatePickerDialog(this, style2, dateSetListener2, year2, month2, day2);

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