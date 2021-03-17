package com.example.sqliteexample;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperClass extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 2;
    //Database Name
    private static final String DATABASE_NAME = "employee_database";
    //Database Table name
    private static final String TABLE_NAME = "EMPLOYEE";
    //Table columns
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public static SQLiteDatabase sqLiteDatabase;


    //creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT NOT NULL," + EMAIL + " TEXT NOT NULL," + ADDRESS + " TEXT NOT NULL, "+LATITUDE +"  DOUBLE NOT NULL, "+LONGITUDE+" DOUBLE NOT NULL);";

    //Constructor
    public DatabaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add Employee Data
    public void addEmployee(EmployeeModelClass employeeModelClass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperClass.NAME, employeeModelClass.getName());
        contentValues.put(DatabaseHelperClass.EMAIL, employeeModelClass.getEmail());
        contentValues.put(DatabaseHelperClass.ADDRESS,employeeModelClass.getAddress());
       contentValues.put(DatabaseHelperClass.LATITUDE, employeeModelClass.getLatitude());
        contentValues.put(DatabaseHelperClass.LONGITUDE, employeeModelClass.getLongitude());

        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DatabaseHelperClass.TABLE_NAME, null, contentValues);
    }

    public List<EmployeeModelClass> getEmployeeList() {
        String sql = "select * from " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<EmployeeModelClass> storeEmployee = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String address = cursor.getString(3);
                //double latitude = cursor.getDouble(4);
               // double longitude = cursor.getDouble(5);
                double latitude = Double.parseDouble(cursor.getString(4));
                double longitude = Double.parseDouble(cursor.getString(5));

                storeEmployee.add(new EmployeeModelClass(id, name, email, address,latitude,longitude));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storeEmployee;
    }

    public List<EmployeeModelClass> getAllPlaces(){
       sqLiteDatabase = this.getReadableDatabase();
        List<EmployeeModelClass> placeList = new ArrayList<>();
        String getAll = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(getAll,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String address = cursor.getString(3);
                double latitude = Double.parseDouble(cursor.getString(4));
                double longitude = Double.parseDouble(cursor.getString(5));

                placeList.add(new EmployeeModelClass(id, name, email, address,latitude, longitude));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  placeList;
    }


    public void updateEmployee(EmployeeModelClass employeeModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperClass.NAME,employeeModelClass.getName());
        contentValues.put(DatabaseHelperClass.EMAIL,employeeModelClass.getEmail());
        contentValues.put(DatabaseHelperClass.ADDRESS,employeeModelClass.getAddress());
        contentValues.put(DatabaseHelperClass.LATITUDE,employeeModelClass.getLatitude());
        contentValues.put(DatabaseHelperClass.LONGITUDE,employeeModelClass.getLongitude());

        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(TABLE_NAME,contentValues,ID + " = ?" , new String[]
                {String.valueOf(employeeModelClass.getId())});
    }

    public void deleteEmployee(int id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID + " = ? ", new String[]
                {String.valueOf(id)});
    }

}
