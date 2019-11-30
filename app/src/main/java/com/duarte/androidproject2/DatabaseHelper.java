package com.duarte.androidproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

//this class is to handle all our database operations
//all tables should be a subclass of DatabaseHelper
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
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
        Log.println(Log.DEBUG, "Log", "OnCreate Called");
    }

    @Override //Drop older tables
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + studentRegistration.getTableName());
        onCreate(db);
    }

    void createStudentRegistration(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(studentRegistration.getColumnNameUsername(), student.getUserName());
        values.put(studentRegistration.getColumnNameEmail(), student.getEmail());
        values.put(studentRegistration.getColumnNamePassword(), student.getPassWord());

//      TODO: add check to verify insert was successful
        long newRowId = db.insert(studentRegistration.getTableName(), null, values);
    }

    Student getStudentRegistration(String studentEmail){
        SQLiteDatabase db = this.getWritableDatabase();

//      TODO: add validation on student email.. ie make sure is of type name_name@student.uml.edu
        String selectQuery = "SELECT * FROM " + studentRegistration.getTableName() + " WHERE "
                + studentRegistration.getColumnNameEmail()  + " = '" + studentEmail + "'";

        Log.e("LOG", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null)
            cursor.moveToFirst();

//      TODO: add try catch for possible null excption from GetString
        Student student = new Student();
        student.setEmail(cursor.getString(cursor.getColumnIndex(studentRegistration.getColumnNameEmail())));
        student.setPassWord(cursor.getString(cursor.getColumnIndex(studentRegistration.getColumnNamePassword())));
        student.setUserName(cursor.getString(cursor.getColumnIndex(studentRegistration.getColumnNameUsername())));

        cursor.close();
        return student;
    }

    //verify if a row exist
    boolean studentExist(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + studentRegistration.getTableName() + " WHERE " +
                studentRegistration.getColumnNameEmail() + " = '" + email + "'";

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    //Probably a better way to handle passwords but going with this for now
    boolean validatePassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT " + studentRegistration.getColumnNamePassword() + " FROM " + studentRegistration.getTableName() + " WHERE " +
                studentRegistration.getColumnNameEmail() + " = '" + email + "'";

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if ((cursor == null) || (cursor.getCount() > 1 || cursor.getCount() < 1)) {
            Log.println(Log.DEBUG, "Log","Cursor is null of count is wrong");
            return false;
            //TODO: This function should only be called after studentExist() has been called and
            //      returned true. So it should not be null, but the count of rows should only be 1
            //      if we are in here that means there may be more than 1 student email registed
            //      this might never happen but just in case this is here, maybe throw exection...
        }

        //if stored password matches given password
        if( cursor.getString(0).equals(password) ){
            cursor.close();
            return true;
        }else {
            cursor.close();
            return false;
        }
    }

    //This subclass is used for creating the student registration table
    public class StudentRegistration implements BaseColumns {
        private static final String TABLE_NAME = "registration";
        private static final String COLUMN_NAME_USERNAME = "userName";
        private static final String COLUMN_NAME_EMAIL = "email";
        /*TODO: Research on what is secure way to store passwords as opposed to plain text. Maybe something like aws secure store*/
        private static final String COLUMN_NAME_PASSWORD = "password";

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_NAME_USERNAME +
                " TEXT, " +  COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_NAME_PASSWORD + " TEXT " + ")";

        String get_CREATETABLE_String(){ return CREATE_TABLE; }
        String getTableName(){ return TABLE_NAME; }
        String getColumnNameUsername() {return COLUMN_NAME_USERNAME; }
        String getColumnNameEmail() {return COLUMN_NAME_EMAIL; }
        String getColumnNamePassword() {return COLUMN_NAME_PASSWORD; }
    }

}
