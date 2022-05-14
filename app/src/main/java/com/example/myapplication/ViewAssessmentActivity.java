package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
        ed.setText(getIntent().getStringExtra("assessmentTitle"));

        TextView dateButton = findViewById(R.id.datePickerString);
        dateButton.setText(getIntent().getStringExtra("assessmentStart"));

        TextView dateButton2 = findViewById(R.id.datePickerString2);
        dateButton2.setText(getIntent().getStringExtra("assessmentEnd"));

        TextView ed2  = findViewById(R.id.assessmentStatusTextView);
        ed2.setText(getIntent().getStringExtra("assessmentType"));

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
                Cursor c2 = sdb2.getAssessments(getIntent().getIntExtra("assessmentId", 0));

                if (c2.moveToNext()) {
                    Intent intent = new Intent(ViewAssessmentActivity.this, CourseDetailsActivity.class);

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
                }
                return true;

            case R.id.action_zero_assessments:
                // SET ALERTS
                Log.i(TAG, "onOptionsItemSelected: ");
                Toast.makeText(getApplicationContext(), "Assessment Alerts Set", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ViewAssessmentActivity.this, MyReceiverAssessments.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                i.putExtra("termMonthValue", getIntent().getStringExtra("termMonthValue"));
                i.putExtra("termName", getIntent().getStringExtra("termName"));
                i.putExtra("termStart", getIntent().getStringExtra("termStart"));
                i.putExtra("termEnd", getIntent().getStringExtra("termEnd"));
                i.putExtra("termNameAndDate", getIntent().getStringExtra("termNameAndDate"));
                i.putExtra("termId", getIntent().getStringExtra("termId"));

                i.putExtra("courseMonthValue", getIntent().getStringExtra("courseMonthValue"));
                i.putExtra("courseNameAndDate", getIntent().getStringExtra("courseNameAndDate"));
                i.putExtra("courseId", getIntent().getStringExtra("courseId"));
                i.putExtra("courseOptionalNotes", getIntent().getStringExtra("courseOptionalNotes"));

                i.putExtra("assessmentMonthValue", getIntent().getStringExtra("assessmentMonthValue"));
                i.putExtra("assessmentId", getIntent().getIntExtra("assessmentId", 0));
                i.putExtra("assessmentTitle", getIntent().getStringExtra("assessmentTitle"));
                i.putExtra("assessmentStart", getIntent().getStringExtra("assessmentStart"));
                i.putExtra("assessmentEnd", getIntent().getStringExtra("assessmentEnd"));
                i.putExtra("assessmentType", getIntent().getStringExtra("assessmentType"));

                // COPY OF COURSE VALUES
                i.putExtra("assessmentTitleCopy", getIntent().getStringExtra("assessmentTitle"));
                Log.i(TAG, "onOptionsItemSelected: " + getIntent().getStringExtra("assessmentTitle"));
                i.putExtra("assessmentStartCopy", getIntent().getStringExtra("assessmentStart"));
                Log.i(TAG, "onOptionsItemSelected: " + getIntent().getStringExtra("assessmentStart"));
                i.putExtra("assessmentEndCopy", getIntent().getStringExtra("assessmentEnd"));
                Log.i(TAG, "onOptionsItemSelected: " + getIntent().getStringExtra("assessmentEnd"));

                PendingIntent sender = null;
                sender = PendingIntent.getBroadcast(ViewAssessmentActivity.this, MainActivity.numAlert++, i, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                // INTERVAL IS 24 HOURS.
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, sender);

                return true;

            case R.id.action_one_assessments:
                //EDIT ASSESSMENT ACTIVITY

                Log.i(TAG, "onOptionsItemSelected:");
                StudentDatabase sdb1 = new StudentDatabase(getApplicationContext());
                Cursor c1 = sdb1.getAssessments(getIntent().getIntExtra("assessmentId", 0));

                if (c1.moveToNext()) {
                    Intent intent = new Intent(ViewAssessmentActivity.this, EditAssessmentActivity.class);

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
                }

                return true;

            case R.id.action_two_assessments:
                //DELETE ASSESSMENT ACTIVITY
                Log.i(TAG, "onOptionsItemSelected: " + getIntent().getIntExtra("assessmentId", 0));

                new DeleteAssessment().execute(getIntent().getIntExtra("assessmentId", 0));
                Intent intent = new Intent(ViewAssessmentActivity.this, CourseDetailsActivity.class);
                // TERM VALUES
                intent.putExtra("termMonthValue", getIntent().getStringExtra("termMonthValue"));
                intent.putExtra("termName", getIntent().getStringExtra("termName"));
                intent.putExtra("termStart", getIntent().getStringExtra("termStart"));
                intent.putExtra("termEnd", getIntent().getStringExtra("termEnd"));
                intent.putExtra("termNameAndDate", getIntent().getStringExtra("termNameAndDate"));
                intent.putExtra("termId", getIntent().getStringExtra("termId"));

                // COURSE VALUES
                intent.putExtra("courseMonthValue", getIntent().getStringExtra("courseMonthValue"));
                intent.putExtra("courseNameAndDate", getIntent().getStringExtra("courseNameAndDate"));
                intent.putExtra("courseId", getIntent().getStringExtra("courseId"));
                intent.putExtra("courseOptionalNotes", getIntent().getStringExtra("courseOptionalNotes"));

                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}