package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TermDetailsActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";
    private int termId = 0;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermDetailsActivity.this,AddCourseActivity.class);
            intent.putExtra("monthValueTerm", getIntent().getStringExtra("monthValue"));
            intent.putExtra("termNameTerm", getIntent().getStringExtra("termName"));
            intent.putExtra("termStartTerm", getIntent().getStringExtra("termStart"));
            intent.putExtra("termEndTerm", getIntent().getStringExtra("termEnd"));
            intent.putExtra("termNameAndDateTerm", getIntent().getStringExtra("termNameAndDate"));
            intent.putExtra("termIdTerm", getIntent().getStringExtra("termId"));
            startActivity(intent);
        });

        //View courses and term details
        StudentDatabase sdb = new StudentDatabase(getApplicationContext());
        Cursor c = null;
        try {
            termId = Integer.parseInt(getIntent().getStringExtra("termId"));
            c = sdb.getCourses2(termId);
        } catch (Exception e) {
            Log.i(TAG, "ERROR onCreate: s == null");
        }

        //Populate remaining weeks
        String weeks = getIntent().getStringExtra("monthValue");
        TextView textView = findViewById(R.id.remainingWeeksValue);
        textView.setText(weeks);
        TextView textViewTitle = findViewById(R.id.termView);
        textViewTitle.setText(getIntent().getStringExtra("termNameAndDate"));

        Button btn0 = findViewById(R.id.course_button_first);
        Button btn1 = findViewById(R.id.course_button_second);
        Button btn2 = findViewById(R.id.course_button_third);
        Button btn3 = findViewById(R.id.course_button_fourth);
        Button btn4 = findViewById(R.id.course_button_fifth);
        Button btn5 = findViewById(R.id.course_button_sixth);

        btn0.setOnClickListener(view -> {

            StudentDatabase sdb1 = new StudentDatabase(getApplicationContext());
            Cursor c1 = sdb1.getCourseLength2();

            Intent intent = new Intent(TermDetailsActivity.this, CourseDetailsActivity.class);

            while (c1.moveToNext()) {
                if (btn0.getText().toString().equals(c1.getString(c1.getColumnIndexOrThrow("title")))) {

                    int term_id = Integer.parseInt(c1.getString(c1.getColumnIndexOrThrow("_id")));
                    String termTitle = c1.getString(c1.getColumnIndexOrThrow("title"));
                    String startDate = c1.getString(c1.getColumnIndexOrThrow("start_date"));
                    String endDate = c1.getString(c1.getColumnIndexOrThrow("end_date"));
                    String optionalNotes = c1.getString(c1.getColumnIndexOrThrow("optional_note"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id, optionalNotes);
                }
            }
        });

        btn1.setOnClickListener(view -> {

            StudentDatabase sdb12 = new StudentDatabase(getApplicationContext());
            Cursor c12 = sdb12.getCourseLength2();

            Intent intent = new Intent(TermDetailsActivity.this, CourseDetailsActivity.class);

            while (c12.moveToNext()) {
                if (btn1.getText().toString().equals(c12.getString(c12.getColumnIndexOrThrow("title")))) {

                    int term_id = Integer.parseInt(c12.getString(c12.getColumnIndexOrThrow("_id")));
                    String termTitle = c12.getString(c12.getColumnIndexOrThrow("title"));
                    String startDate = c12.getString(c12.getColumnIndexOrThrow("start_date"));
                    String endDate = c12.getString(c12.getColumnIndexOrThrow("end_date"));
                    String optionalNotes = c12.getString(c12.getColumnIndexOrThrow("optional_note"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id, optionalNotes);
                }
            }
        });

        btn2.setOnClickListener(view -> {

            StudentDatabase sdb13 = new StudentDatabase(getApplicationContext());
            Cursor c13 = sdb13.getCourseLength2();

            Intent intent = new Intent(TermDetailsActivity.this, CourseDetailsActivity.class);

            while (c13.moveToNext()) {
                if (btn2.getText().toString().equals(c13.getString(c13.getColumnIndexOrThrow("title")))) {

                    int term_id = Integer.parseInt(c13.getString(c13.getColumnIndexOrThrow("_id")));
                    String termTitle = c13.getString(c13.getColumnIndexOrThrow("title"));
                    String startDate = c13.getString(c13.getColumnIndexOrThrow("start_date"));
                    String endDate = c13.getString(c13.getColumnIndexOrThrow("end_date"));
                    String optionalNotes = c13.getString(c13.getColumnIndexOrThrow("optional_note"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id, optionalNotes);
                }
            }
        });

        btn3.setOnClickListener(view -> {

            StudentDatabase sdb14 = new StudentDatabase(getApplicationContext());
            Cursor c14 = sdb14.getCourseLength2();

            Intent intent = new Intent(TermDetailsActivity.this, CourseDetailsActivity.class);

            while (c14.moveToNext()) {
                if (btn3.getText().toString().equals(c14.getString(c14.getColumnIndexOrThrow("title")))) {

                    int term_id = Integer.parseInt(c14.getString(c14.getColumnIndexOrThrow("_id")));
                    String termTitle = c14.getString(c14.getColumnIndexOrThrow("title"));
                    String startDate = c14.getString(c14.getColumnIndexOrThrow("start_date"));
                    String endDate = c14.getString(c14.getColumnIndexOrThrow("end_date"));
                    String optionalNotes = c14.getString(c14.getColumnIndexOrThrow("optional_note"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id, optionalNotes);
                }
            }
        });

        btn4.setOnClickListener(view -> {

            StudentDatabase sdb16 = new StudentDatabase(getApplicationContext());
            Cursor c16 = sdb16.getCourseLength2();

            Intent intent = new Intent(TermDetailsActivity.this, CourseDetailsActivity.class);

            while (c16.moveToNext()) {
                if (btn4.getText().toString().equals(c16.getString(c16.getColumnIndexOrThrow("title")))) {

                    int term_id = Integer.parseInt(c16.getString(c16.getColumnIndexOrThrow("_id")));
                    String termTitle = c16.getString(c16.getColumnIndexOrThrow("title"));
                    String startDate = c16.getString(c16.getColumnIndexOrThrow("start_date"));
                    String endDate = c16.getString(c16.getColumnIndexOrThrow("end_date"));
                    String optionalNotes = c16.getString(c16.getColumnIndexOrThrow("optional_note"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id, optionalNotes);
                }
            }
        });

        btn5.setOnClickListener(view -> {

            StudentDatabase sdb15 = new StudentDatabase(getApplicationContext());
            Cursor c15 = sdb15.getCourseLength2();

            Intent intent = new Intent(TermDetailsActivity.this, CourseDetailsActivity.class);

            while (c15.moveToNext()) {
                if (btn5.getText().toString().equals(c15.getString(c15.getColumnIndexOrThrow("title")))) {

                    int term_id = Integer.parseInt(c15.getString(c15.getColumnIndexOrThrow("_id")));
                    String termTitle = c15.getString(c15.getColumnIndexOrThrow("title"));
                    String startDate = c15.getString(c15.getColumnIndexOrThrow("start_date"));
                    String endDate = c15.getString(c15.getColumnIndexOrThrow("end_date"));
                    String optionalNotes = c15.getString(c15.getColumnIndexOrThrow("optional_note"));
                    findRemainingWeeks(termTitle, startDate, endDate, intent, term_id, optionalNotes);
                }
            }
        });

        btn0.setVisibility(View.INVISIBLE);
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        btn5.setVisibility(View.INVISIBLE);


        assert c != null;
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
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void findRemainingWeeks(String termTitle, String startDate, String endDate, Intent intent, int term_id, String optionalNotes) {
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

            Log.i(TAG, "onClick: " + numOfWeeks);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        intent.putExtra("monthValueTerm", getIntent().getStringExtra("monthValue"));
        intent.putExtra("termNameTerm", getIntent().getStringExtra("termName"));
        intent.putExtra("termStartTerm", getIntent().getStringExtra("termStart"));
        intent.putExtra("termEndTerm", getIntent().getStringExtra("termEnd"));
        intent.putExtra("termNameAndDateTerm", getIntent().getStringExtra("termNameAndDate"));
        intent.putExtra("termIdTerm", getIntent().getStringExtra("termId"));

        intent.putExtra("monthValue", String.valueOf(numOfWeeks));
        intent.putExtra("termNameAndDate", termTitle + "\n" + startDate + " - \n" + endDate);
        intent.putExtra("termId", String.valueOf(term_id));
        intent.putExtra("optionalNotes", optionalNotes);
        startActivity(intent);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            Log.i(TAG, "onDown: ");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(TAG, "onFling: ");
            if (velocityX > 500) {
                startActivity(new Intent(TermDetailsActivity.this, DashboardActivity.class));
            }
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public class DeleteTerm extends AsyncTask<Integer, Object, Integer> {
        StudentDatabase dbConnector = new StudentDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            dbConnector.removeTerm(params[0]);
            return 1;
        }
        protected void onPostExecute(Integer result) {
            Toast.makeText(getApplicationContext(), "Term Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case R.id.action_one:
                //EDIT TERM ACTIVITY
                StudentDatabase sdb1 = new StudentDatabase(getApplicationContext());
                Cursor c1 = sdb1.getData2(termId);

                if (c1.moveToNext()) {
                    Intent intent = new Intent(TermDetailsActivity.this, EditTermActivity.class);

                    intent.putExtra("monthValueTerm", getIntent().getStringExtra("monthValue"));
                    intent.putExtra("termNameTerm", getIntent().getStringExtra("termName"));
                    intent.putExtra("termStartTerm", getIntent().getStringExtra("termStart"));
                    intent.putExtra("termEndTerm", getIntent().getStringExtra("termEnd"));
                    intent.putExtra("termNameAndDateTerm", getIntent().getStringExtra("termNameAndDate"));
                    intent.putExtra("termIdTerm", getIntent().getStringExtra("termId"));

                    intent.putExtra("termId", getIntent().getStringExtra("termId"));
                    intent.putExtra("termName", getIntent().getStringExtra("termName"));
                    intent.putExtra("termStart", getIntent().getStringExtra("termStart"));
                    intent.putExtra("termEnd", getIntent().getStringExtra("termEnd"));
                    startActivity(intent);
                }

                return true;

            case R.id.action_two:
                //DELETE TERM ACTIVITY
                StudentDatabase sdb = new StudentDatabase(getApplicationContext());
                Cursor c = sdb.getCourses2(termId);

                if (c.moveToNext()) {
                    FragmentManager manager = getSupportFragmentManager();
                    DeleteErrorDialogFragment dialog = new DeleteErrorDialogFragment();
                    dialog.show(manager, "deleteErrorDialog");
                } else {
                    new DeleteTerm().execute(termId);
                    startActivity(new Intent(TermDetailsActivity.this, DashboardActivity.class));
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
