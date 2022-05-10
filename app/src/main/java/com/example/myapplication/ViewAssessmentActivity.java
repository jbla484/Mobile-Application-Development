package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ViewAssessmentActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);

        TextView ed  = findViewById(R.id.assessmentTitleTextView);
        ed.setText(getIntent().getStringExtra("courseTitle"));

        TextView dateButton = findViewById(R.id.datePickerString);
        dateButton.setText(getIntent().getStringExtra("courseStart"));

        TextView dateButton2 = findViewById(R.id.datePickerString2);
        dateButton2.setText(getIntent().getStringExtra("courseEnd"));

        TextView ed2  = findViewById(R.id.assessmentStatusTextView);
        ed2.setText(getIntent().getStringExtra("courseType"));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessments, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public class DeleteAssessment extends AsyncTask<Integer, Object, Integer> {
        StudentDatabase dbConnector = new StudentDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            dbConnector.removeAssessment(params[0]);
            return 1;
        }
        protected void onPostExecute(Integer result) {
            Toast.makeText(getApplicationContext(), "Assessment Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case android.R.id.home:
                //BACK BUTTON
                StudentDatabase sdb2 = new StudentDatabase(getApplicationContext());
                Cursor c2 = sdb2.getAssessments(Integer.parseInt(getIntent().getStringExtra("courseId")));

                if (c2.moveToNext()) {
                    Intent intent = new Intent(ViewAssessmentActivity.this, CourseDetailsActivity.class);

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

                    intent.putExtra("monthValueCourse", getIntent().getStringExtra("monthValueCourse"));
                    intent.putExtra("courseTitle", getIntent().getStringExtra("courseTitle"));
                    intent.putExtra("courseStart", getIntent().getStringExtra("courseStart"));
                    intent.putExtra("courseEnd", getIntent().getStringExtra("courseEnd"));
                    intent.putExtra("courseType", getIntent().getStringExtra("courseType"));
                    intent.putExtra("courseNameAndDate", getIntent().getStringExtra("courseNameAndDate"));
                    intent.putExtra("courseId", getIntent().getStringExtra("courseId"));

                    intent.putExtra("assessmentId", getIntent().getStringExtra("courseId"));
                    intent.putExtra("assessmentTitle", c2.getString(1));
                    intent.putExtra("assessmentStart", c2.getString(2));
                    intent.putExtra("assessmentEnd", c2.getString(3));
                    intent.putExtra("assessmentType", c2.getString(4));
                    startActivity(intent);
                }
                return true;

            case R.id.action_one_assessments:
                //EDIT ASSESSMENT ACTIVITY

                Log.i(TAG, "onOptionsItemSelected:");
                StudentDatabase sdb1 = new StudentDatabase(getApplicationContext());
                Cursor c1 = sdb1.getAssessments(Integer.parseInt(getIntent().getStringExtra("courseId")));

                if (c1.moveToNext()) {
                    Intent intent = new Intent(ViewAssessmentActivity.this, EditAssessmentActivity.class);

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

                    intent.putExtra("monthValueCourse", getIntent().getStringExtra("monthValueCourse"));
                    intent.putExtra("courseTitle", getIntent().getStringExtra("courseTitle"));
                    intent.putExtra("courseStart", getIntent().getStringExtra("courseStart"));
                    intent.putExtra("courseEnd", getIntent().getStringExtra("courseEnd"));
                    intent.putExtra("courseType", getIntent().getStringExtra("courseType"));
                    intent.putExtra("courseNameAndDate", getIntent().getStringExtra("courseNameAndDate"));
                    intent.putExtra("courseId", getIntent().getStringExtra("courseId"));

                    intent.putExtra("assessmentId", getIntent().getStringExtra("courseId"));
                    intent.putExtra("assessmentTitle", c1.getString(1));
                    intent.putExtra("assessmentStart", c1.getString(2));
                    intent.putExtra("assessmentEnd", c1.getString(3));
                    intent.putExtra("assessmentType", c1.getString(4));
                    startActivity(intent);
                }

                return true;

            case R.id.action_two_assessments:
                //DELETE ASSESSMENT ACTIVITY
                Log.i(TAG, "onOptionsItemSelected: " + getIntent().getIntExtra("courseId", 0));

                new DeleteAssessment().execute(Integer.parseInt(getIntent().getStringExtra("courseId")));
                Intent intent = new Intent(ViewAssessmentActivity.this, CourseDetailsActivity.class);
                intent.putExtra("monthValue", getIntent().getStringExtra("monthValue"));
                intent.putExtra("termNameAndDate", getIntent().getStringExtra("termNameAndDate"));
                intent.putExtra("termId", getIntent().getStringExtra("termId"));
                intent.putExtra("optionalNotes", getIntent().getStringExtra("optionalNotes"));

                intent.putExtra("monthValueTerm", getIntent().getStringExtra("monthValueTerm"));
                intent.putExtra("termNameTerm", getIntent().getStringExtra("termNameTerm"));
                intent.putExtra("termStartTerm", getIntent().getStringExtra("termStartTerm"));
                intent.putExtra("termEndTerm", getIntent().getStringExtra("termEndTerm"));
                intent.putExtra("termNameAndDateTerm", getIntent().getStringExtra("termNameAndDateTerm"));
                intent.putExtra("termIdTerm", getIntent().getStringExtra("termIdTerm"));
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}