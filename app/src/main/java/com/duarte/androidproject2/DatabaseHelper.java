package com.duarte.androidproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.TextView;

//this class is to handle all our database operations
//all tables should be a subclass of DatabaseHelper
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 0;
    private static final String DATABASE_NAME = "studentDb.db";

    private StudentRegistration studentRegistration;

    //Initialize Database
    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        studentRegistration = new StudentRegistration();
    }

    @Override //Create each table
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(studentRegistration.get_CREATETABLE_String());
    }

    @Override //Drop older tables
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + studentRegistration.getTableName());
    }

    //This subclass is used for creating the student registration table
    public class StudentRegistration implements BaseColumns {
        private static final String TABLE_NAME = "registration";
        private static final String COLUMN_NAME_USERNAME = "userName";
        private static final String COLUMN_NAME_EMAIL = "email";
        /*TODO: Research on what is secure way to store passwords as opposed to plain text. Maybe something like aws secure store*/
        private static final String COLUMN_NAME_PASSWORD = "password";

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_NAME_USERNAME +
                " TEXT, " +  COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_NAME_PASSWORD + "TEXT " + ")";

        String get_CREATETABLE_String(){ return CREATE_TABLE; }
        String getTableName(){ return TABLE_NAME; }
        String getColumnNameUsername() {return COLUMN_NAME_USERNAME; }
        String getColumnNameEmail() {return COLUMN_NAME_EMAIL; }
        String getColumnNamePassword() {return COLUMN_NAME_PASSWORD; }
    }

    public void createStudentRegistration(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(studentRegistration.getColumnNameUsername(), student.getUserName());
        values.put(studentRegistration.getColumnNameEmail(), student.getEmail());
        values.put(studentRegistration.getColumnNamePassword(), student.getPassWord());

        //TODO: add check to verify insert was successful
        long newRowId = db.insert(studentRegistration.getTableName(), null, values);
    }



}
