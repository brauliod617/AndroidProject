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
    //Used to represent database being used in program
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentDb.db";

    //These classes are dataclasses
    private StudentRegistrationTable studentRegistrationTable;
    private ClassesTable classesTable;
    private IsInClassTable isInClassTable;


    //Initialize Database
    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        studentRegistrationTable = new StudentRegistrationTable();
        classesTable = new ClassesTable();
        isInClassTable = new IsInClassTable();
    }

    @Override //Create each table
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(studentRegistrationTable.get_CREATETABLE_String());
        db.execSQL(classesTable.getCreateTable());
        db.execSQL(isInClassTable.getCreateTable());
        Log.println(Log.DEBUG, "Log", "OnCreate Called");
    }

    @Override //Drop older tables
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + studentRegistrationTable.getTableName());
        onCreate(db);
    }

    //TODO: a lot of repeated code in following blocks, if we have time see if we can make this more
    //      efficient
/********************************STUDENT REGISTRATION**********************************************/
    //This subclass is used for creating the student registration table
    public class StudentRegistrationTable implements BaseColumns {
        private static final String TABLE_NAME = "registration";
        private static final String COLUMN_NAME_USERNAME = "userName";
        private static final String COLUMN_NAME_EMAIL = "email";
        /*TODO: Research on what is secure way to store passwords as opposed to plain text. Maybe something like aws secure store*/
        //TODO: Prevent duplicate usernames, maybe use UNIQUE?
        private static final String COLUMN_NAME_PASSWORD = "password";

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_NAME_USERNAME + " TEXT, "
                + COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_NAME_PASSWORD + " TEXT " + ")";

        String get_CREATETABLE_String(){ return CREATE_TABLE; }
        String getTableName(){ return TABLE_NAME; }
        String getColumnNameUsername() {return COLUMN_NAME_USERNAME; }
        String getColumnNameEmail() {return COLUMN_NAME_EMAIL; }
        String getColumnNamePassword() {return COLUMN_NAME_PASSWORD; }
    }
    //insert row into table
    void createStudentRegistration(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(studentRegistrationTable.getColumnNameUsername(), student.getUserName());
        values.put(studentRegistrationTable.getColumnNameEmail(), student.getEmail());
        values.put(studentRegistrationTable.getColumnNamePassword(), student.getPassWord());

        if(db.insert(studentRegistrationTable.getTableName(), null, values) == -1){
            Log.println(Log.DEBUG, "log", "Failed to insert student registration Table");
            //TODO: figure out what to do in this case, throw exception?
        }

    }
    //get student object
    Student getStudentRegistration(String studentEmail){
        SQLiteDatabase db = this.getWritableDatabase();

        //TODO: add validation on student email.. ie make sure is of type name_name@student.uml.edu
        String selectQuery = "SELECT * FROM " + studentRegistrationTable.getTableName() + " WHERE "
                + studentRegistrationTable.getColumnNameEmail()  + " = '" + studentEmail + "'";

//        Log.e("LOG", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null)
            cursor.moveToFirst();

        //TODO: add try catch for possible null excption from GetString
        Student student = new Student();
        student.setEmail(cursor.getString(cursor.getColumnIndex(studentRegistrationTable.getColumnNameEmail())));
        student.setPassWord(cursor.getString(cursor.getColumnIndex(studentRegistrationTable.getColumnNamePassword())));
        student.setUserName(cursor.getString(cursor.getColumnIndex(studentRegistrationTable.getColumnNameUsername())));

        cursor.close();
        return student;
    }
    //verify if a row exist
    boolean studentExist(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + studentRegistrationTable.getTableName() + " WHERE " +
                studentRegistrationTable.getColumnNameEmail() + " = '" + email + "'";

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
        String sqlQuery = "SELECT " + studentRegistrationTable.getColumnNamePassword() + " FROM " + studentRegistrationTable.getTableName() + " WHERE " +
                studentRegistrationTable.getColumnNameEmail() + " = '" + email + "'";

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
/********************************END OF STUDENT REGISTRATION***************************************/

/********************************CLASSES***********************************************************/
    //This subclass is used for creating the classes table
    public class ClassesTable implements BaseColumns {
        private static final String TABLE_NAME = "classes";
        private static final String COLUMN_NAME_CLASS_NAME = "className";
        private static final String COLUMN_NAME_CLASS_SECTION = "classSection";
        private static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_NAME_CLASS_NAME + " TEXT, " +
                COLUMN_NAME_CLASS_SECTION + " TEXT, " +
                "PRIMARY KEY( " +
                        COLUMN_NAME_CLASS_SECTION + ", " +
                        COLUMN_NAME_CLASS_SECTION +
                        ")" +
                ")";

        String getTableName() { return TABLE_NAME; }
        String getColumnNameClassName() { return COLUMN_NAME_CLASS_NAME; }
        String getColumnNameClassSection() { return COLUMN_NAME_CLASS_SECTION; }
        String getCreateTable() { return CREATE_TABLE; }
    }
    //insert row into table
    void createClass(Classes classes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(classesTable.getColumnNameClassName(), classes.getClassName());
        values.put(classesTable.getColumnNameClassSection(), classes.getSectionNumber());

        if(db.insert(classesTable.getTableName(), null, values) == -1){
            Log.println(Log.DEBUG, "log", "Failed to insert classes Table");
            //TODO: figure out what to do in this case, throw exception?
        }
    }
    //verify if a row exists
    boolean classExist(String className, String classSection) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + classesTable.getTableName() + " WHERE " +
                classesTable.getColumnNameClassName() + " = '" + className +
                "' AND " + classesTable.getColumnNameClassSection() + " = '" + classSection + "'";

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if(cursor.getCount() == 0){
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }
/********************************END OF CLASSES****************************************************/

/********************************IS IN CLASS*******************************************************/
    //This subclass is used for creating the isInClass table
    public class IsInClassTable implements BaseColumns {
        private static final String TABLE_NAME = "isInClass";
        private static final String COLUMN_NAME_STUDENT_EMAIL = "email";
        private static final String COLUMN_NAME_CLASS_NAME = "className";
        private static final String COLUMN_NAME_CLASS_SECTION = "classSection";
        private static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_NAME_STUDENT_EMAIL + " TEXT, " +
                COLUMN_NAME_CLASS_NAME + " TEXT, " +
                COLUMN_NAME_CLASS_SECTION + " TEXT, " +
                " PRIMARY KEY( " +
                        COLUMN_NAME_STUDENT_EMAIL + ", " +
                        COLUMN_NAME_CLASS_NAME +  ", " +
                        COLUMN_NAME_CLASS_SECTION +
                        ")" +
                ")";

        String getTableName() { return TABLE_NAME; }
        String getColumnNameStudentEmail() {return COLUMN_NAME_STUDENT_EMAIL; }
        String getColumnNameClassName() { return COLUMN_NAME_CLASS_NAME; }
        String getColumnNameClassSection() { return COLUMN_NAME_CLASS_SECTION; }
        String getCreateTable() { return CREATE_TABLE; }
    }
    //insert row into table
    void createIsInClass(IsInClass isInClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(isInClassTable.getColumnNameStudentEmail(), isInClass.getEmail());
        values.put(isInClassTable.getColumnNameClassName(), isInClass.getClassName());
        values.put(isInClassTable.getColumnNameClassSection(), isInClass.getSectionNumber());

        if(db.insert(isInClassTable.getTableName(), null, values) == -1){
            Log.println(Log.DEBUG, "log", "Failed to insert isInClass row to Table");
            //TODO: figure out what to do in this case, throw exception?
        }
    }

    boolean isInClassExist(String email, String className, String classSection) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + isInClassTable.getTableName() + " WHERE " +
                isInClassTable.getColumnNameStudentEmail() + " = '" + email + "' AND " +
                isInClassTable.getColumnNameClassName() + " = '" + className + "' AND " +
                isInClassTable.getColumnNameClassSection() + " = '" + classSection + "'";

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if(cursor.getCount() == 0){
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }
/********************************END OF IS IN CLASSES**********************************************/

/********************************CREATE CS DB******************************************************/

/********************************END OF CREATE CS DB***********************************************/



}
