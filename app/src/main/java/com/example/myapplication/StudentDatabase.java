package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "terms.db";
    private static final int VERSION = 1;

    public StudentDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class TermsTable {
        private static final String TABLE = "terms";
        private static final String COL_ID = "_id";
        private static final String COL_TITLE = "title";
        private static final String COL_START_DATE = "start_date";
        private static final String COL_END_DATE = "end_date";
    }

    private static final class CoursesTable {
        private static final String COURSE_TABLE = "courses";
        private static final String COURSE_COL_ID = "_id";
        private static final String COURSE_COL_TITLE = "title";
        private static final String COURSE_COL_START_DATE = "start_date";
        private static final String COURSE_COL_END_DATE = "end_date";
        private static final String COURSE_COL_STATUS = "status";
        private static final String COURSE_COL_INS_NAME = "instructor_name";
        private static final String COURSE_COL_INS_PHONE = "instructor_phone";
        private static final String COURSE_COL_INS_EMAIL = "instructor_email";
        private static final String COURSE_COL_TERM_ID = "term_id";
        private static final String COURSE_COL_OPTIONAL_NOTE = "optional_note";
    }

    private static final class AssignmentsTable {
        private static final String ASSESSMENT_TABLE = "assessments";
        private static final String ASSESSMENT_COL_ID = "_id";
        private static final String ASSESSMENT_COL_TITLE = "title";
        private static final String ASSESSMENT_COL_START_DATE = "start_date";
        private static final String ASSESSMENT_COL_END_DATE = "end_date";
        private static final String ASSESSMENT_COL_TYPE = "type";
        private static final String ASSESSMENT_COL_COURSE_ID = "course_id";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TermsTable.TABLE + " (" +
                TermsTable.COL_ID + " integer primary key autoincrement, " +
                TermsTable.COL_TITLE + " text, " +
                TermsTable.COL_START_DATE + " date, " +
                TermsTable.COL_END_DATE + " date" + ")");
        Log.i("INFORMATION", "onCreate: terms table created on local database");

        db.execSQL("create table " + CoursesTable.COURSE_TABLE + " (" +
                CoursesTable.COURSE_COL_ID + " integer primary key autoincrement, " +
                CoursesTable.COURSE_COL_TITLE + " text, " +
                CoursesTable.COURSE_COL_START_DATE + " date, " +
                CoursesTable.COURSE_COL_END_DATE + " date, " +
                CoursesTable.COURSE_COL_STATUS + " text, " +
                CoursesTable.COURSE_COL_INS_NAME + " text, " +
                CoursesTable.COURSE_COL_INS_PHONE + " phone, " +
                CoursesTable.COURSE_COL_INS_EMAIL + " email, " +
                CoursesTable.COURSE_COL_TERM_ID + " integer, " +
                CoursesTable.COURSE_COL_OPTIONAL_NOTE + " text " + ")");
        Log.i("INFORMATION", "onCreate:  courses table created on local database");

        db.execSQL("create table " + AssignmentsTable.ASSESSMENT_TABLE + " (" +
                AssignmentsTable.ASSESSMENT_COL_ID + " integer primary key autoincrement, " +
                AssignmentsTable.ASSESSMENT_COL_TITLE + " text, " +
                AssignmentsTable.ASSESSMENT_COL_START_DATE + " date, " +
                AssignmentsTable.ASSESSMENT_COL_END_DATE + " date, " +
                AssignmentsTable.ASSESSMENT_COL_TYPE + " text, " +
                AssignmentsTable.ASSESSMENT_COL_COURSE_ID + " integer " + ")");
        Log.i("INFORMATION", "onCreate:  assessment table created on local database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("drop table if exists " + TermsTable.TABLE);
        onCreate(db);
    }

    public void addTerm(String string, String start, String end) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TermsTable.COL_TITLE, string);
        values.put(TermsTable.COL_START_DATE, start);
        values.put(TermsTable.COL_END_DATE, end);

        db.insert(TermsTable.TABLE, null, values);
    }

    public void updateTerm(String id, String string, String start, String end) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TermsTable.COL_TITLE, string);
        values.put(TermsTable.COL_START_DATE, start);
        values.put(TermsTable.COL_END_DATE, end);

        db.update(TermsTable.TABLE, values, "_id = ?", new String[]{id});
    }

    public void updateAssessment(String id, String string, String start, String end, String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AssignmentsTable.ASSESSMENT_COL_TITLE, string);
        values.put(AssignmentsTable.ASSESSMENT_COL_START_DATE, start);
        values.put(AssignmentsTable.ASSESSMENT_COL_END_DATE, end);
        values.put(AssignmentsTable.ASSESSMENT_COL_TYPE, type);

        db.update(AssignmentsTable.ASSESSMENT_TABLE, values, "_id = ?", new String[]{id});
    }

    public void updateCourse(String id, String string, String start, String end, String status, String iName, String iPhone, String iEmail, String optionalNote) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CoursesTable.COURSE_COL_TITLE, string);
        values.put(CoursesTable.COURSE_COL_START_DATE, start);
        values.put(CoursesTable.COURSE_COL_END_DATE, end);
        values.put(CoursesTable.COURSE_COL_STATUS, status);
        values.put(CoursesTable.COURSE_COL_INS_NAME, iName);
        values.put(CoursesTable.COURSE_COL_INS_PHONE, iPhone);
        values.put(CoursesTable.COURSE_COL_INS_PHONE, iEmail);
        values.put(CoursesTable.COURSE_COL_OPTIONAL_NOTE, optionalNote);

        db.update(CoursesTable.COURSE_TABLE, values, "_id = ?", new String[]{id});
    }

    public void removeTerm(int termId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TermsTable.TABLE, "_id =?", new String[]{String.valueOf(termId)});
    }

    public void removeCourse(int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CoursesTable.COURSE_TABLE, "_id =?", new String[]{String.valueOf(courseId)});
    }

    public void removeAssessment(int assessmentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AssignmentsTable.ASSESSMENT_TABLE, "_id =?", new String[]{String.valueOf(assessmentId)});
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TermsTable.TABLE;
        return db.rawQuery(sql, null);
    }

    public Cursor getData2(int term_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TermsTable.TABLE + " where _id = " + term_id;
        return db.rawQuery(sql, null);
    }

    public void addCourse(String title, String start, String end, String status, String iName, String iPhone, String iEmail, int term_id, String optionalNote) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CoursesTable.COURSE_COL_TITLE, title);
        values.put(CoursesTable.COURSE_COL_START_DATE, start);
        values.put(CoursesTable.COURSE_COL_END_DATE, end);
        values.put(CoursesTable.COURSE_COL_STATUS, status);
        values.put(CoursesTable.COURSE_COL_INS_NAME, iName);
        values.put(CoursesTable.COURSE_COL_INS_PHONE, iPhone);
        values.put(CoursesTable.COURSE_COL_INS_EMAIL, iEmail);
        values.put(CoursesTable.COURSE_COL_TERM_ID, term_id);
        values.put(CoursesTable.COURSE_COL_OPTIONAL_NOTE, optionalNote);

        db.insert(CoursesTable.COURSE_TABLE, null, values);
    }

    public void addAssessment(String title, String start, String end, String type, int course_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AssignmentsTable.ASSESSMENT_COL_TITLE, title);
        values.put(AssignmentsTable.ASSESSMENT_COL_START_DATE, start);
        values.put(AssignmentsTable.ASSESSMENT_COL_END_DATE, end);
        values.put(AssignmentsTable.ASSESSMENT_COL_TYPE, type);
        values.put(AssignmentsTable.ASSESSMENT_COL_COURSE_ID, course_id);

        db.insert(AssignmentsTable.ASSESSMENT_TABLE, null, values);
    }

    public Cursor getCourses() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + CoursesTable.COURSE_TABLE;
        return db.rawQuery(sql, null);
    }

    public Cursor getAssessments() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + AssignmentsTable.ASSESSMENT_TABLE;
        return db.rawQuery(sql, null);
    }
    public Cursor getAssessments(int assessmentId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + AssignmentsTable.ASSESSMENT_TABLE + " where _id = " + assessmentId;
        return db.rawQuery(sql, null);
    }

    public Cursor getCourses2(int term_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + CoursesTable.COURSE_TABLE + " where term_id = " + term_id;
        return db.rawQuery(sql, null);
    }

    public Cursor getCourses3(int term_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + CoursesTable.COURSE_TABLE + " where _id = " + term_id;
        return db.rawQuery(sql, null);
    }

    public Cursor getCourses(int term_id, String title) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + CoursesTable.COURSE_TABLE + " where term_id = " + term_id + " and title = '" + title + "'";
        return db.rawQuery(sql, null);
    }

    public Cursor getAssessments2(int course_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + AssignmentsTable.ASSESSMENT_TABLE + " where course_id = " + course_id;
        return db.rawQuery(sql, null);
    }

    public Cursor getCourseLength() {

        SQLiteDatabase db = this.getReadableDatabase();
        //String sql = "select * from " + StudentTable.TABLE + " where _id = " + DashboardActivity.term_id;
        String sql = "select * from " + TermsTable.TABLE;
        return db.rawQuery(sql, null);
    }

    public Cursor getCourseLength2() {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + CoursesTable.COURSE_TABLE;
        return db.rawQuery(sql, null);
    }
}