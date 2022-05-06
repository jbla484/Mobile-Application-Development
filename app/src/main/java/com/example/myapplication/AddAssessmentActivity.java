package com.example.myapplication;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddAssessmentActivity extends AppCompatActivity {

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

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = (Button) this.findViewById(R.id.addAssessmentButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText courseTitle = (EditText) findViewById(R.id.assessmentTitleText);
                String assessmentTitleString = courseTitle.getText().toString();

                Button termStart = (Button) findViewById(R.id.datePickerButton);
                String assessmentStartString = termStart.getText().toString();

                Button termEnd = (Button) findViewById(R.id.datePickerButton2);
                String assessmentEndString = termEnd.getText().toString();

                Spinner courseProgress = (Spinner) findViewById(R.id.spinner2);
                String assessmentProgressString = courseProgress.getSelectedItem().toString();

                int course_id = getIntent().getIntExtra("course_id", -1);
                new AddAssessment().execute(assessmentTitleString, assessmentStartString, assessmentEndString, assessmentProgressString, String.valueOf(course_id));
                Log.i("INFORMATION", "onClick: Assessment added to database. Term ID: ");

                Intent intent = new Intent(AddAssessmentActivity.this, CourseDetailsActivity.class);
                intent.putExtra("monthValueTerm", getIntent().getStringExtra("monthValueTerm"));
                intent.putExtra("termNameTerm", getIntent().getStringExtra("termNameTerm"));
                intent.putExtra("termStartTerm", getIntent().getStringExtra("termStartTerm"));
                intent.putExtra("termEndTerm", getIntent().getStringExtra("termEndTerm"));
                intent.putExtra("termNameAndDateTerm", getIntent().getStringExtra("termNameAndDateTerm"));
                intent.putExtra("termIdTerm", getIntent().getStringExtra("termIdTerm"));
                intent.putExtra("monthValue", getIntent().getStringExtra("monthValue"));
                intent.putExtra("termNameAndDate", getIntent().getStringExtra("termNameAndDate"));
                intent.putExtra("termId", getIntent().getStringExtra("termId"));
                intent.putExtra("optionalNotes", getIntent().getStringExtra("optionalNotes"));
                startActivity(intent);
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

    public void findRemainingWeeks(String termTitle, String startDate, String endDate, Intent intent, int course_id) {
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
            Date userStart = new SimpleDateFormat("yyyy MM dd").parse(parseStart[2] + " " + monthIndexStart + " " + parseStart[1]);
            Date userEnd = new SimpleDateFormat("yyyy MM dd").parse(parseEnd[2] + " " + monthIndexEnd + " " + parseEnd[1]);
            long diff =  userEnd.getTime() - userStart.getTime();
            int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            numOfWeeks = (numOfDays / 7);

            Log.i("INFORMATION", "onClick: " + numOfWeeks);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        intent.putExtra("monthValue3", String.valueOf(numOfWeeks));
        intent.putExtra("termNameAndDate3", termTitle + "\n" + startDate + " - \n" + endDate);
        intent.putExtra("courseId", String.valueOf(course_id));
        startActivity(intent);
    }
}