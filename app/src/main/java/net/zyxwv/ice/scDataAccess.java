package net.zyxwv.ice;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by clyde on 12/30/2017.
 */
public class scDataAccess extends Application {
    static Context me = App.getContext();
    static String DBNAME = me.getResources().getString(R.string.dbName);

    public static String HelloWorld(){
        return "This is the return string from RdDataMgr.HelloWorld.";
    }

    //Inputs: Tablename, ContentValues, Where clause
    //Outputs: _id
    public static long SqlInsert(String TableName, ContentValues cv){
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        long NewID = myDB.insert(TableName,null,cv);
        return NewID;
    }

    //Inputs: Tablename, ContentValues, Where clause
    //Outputs: _id
    public static void SqlUpdate(String TableName, ContentValues cv, String Where){
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        myDB.update(TableName, cv, Where, null);
    }

    //Inputs: Tablename, Where clause
    //Outputs: _id
    public static void SqlDelete(String TableName, String Where){
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        myDB.delete(TableName, Where, null);
    }

    //Inputs: a complete sql statement, but not for INSERT, UPDATE or DELETE
    //Outputs: none
    public static void SqlExec(String sql) {
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        myDB.execSQL(sql);
        myDB.close();
    }

    //Inputs: a complete sql SELECT statement
    //Outputs: an empty, single-row or many-row cursor
    public static Cursor SqlQryCrsr(String sql) {
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        Cursor crsr = myDB.rawQuery(sql, null);
        int cnt = crsr.getCount();
        myDB.close();
        return crsr;
    }

    //Inputs: a complete sql SELECT statement
    //Outputs: an empty, single-row or many-row ArrayList
    public static List<String> SqlQryList(String sql) {
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        Cursor crsr = myDB.rawQuery(sql, null);
        int cnt = crsr.getCount();
        myDB.close();
        List<String> list = new ArrayList<String>();
        crsr.moveToFirst();
        if(cnt>0) {
            do {
                list.add(crsr.getString(0));
            } while (crsr.moveToNext());
        }
        return list;
    }

    //Inputs: a complete sql SELECT statement
    //Outputs: a string containing the value in the first column of the first row of the returned dataset
    public static String SqlQryStr(String sql) {
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        Cursor crsr = myDB.rawQuery(sql, null);
        crsr.moveToFirst();
        String val = crsr.getString(0);
        myDB.close();
        return val;
    }

    //Inputs: The tablename from which to retrieve a single field of data. The name of the primary key field. The ID of the desired row. The name of the field from which to retrieve the data.
    //Outputs: The contents of a single field
    public static String SqlFieldValGet(String TableName, String KeyField, long ItemID, String FieldName) {
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        String sql = "SELECT " + FieldName + " FROM " + TableName + " WHERE " + KeyField + "=" + ItemID + ";";
        Cursor crsr = myDB.rawQuery(sql, null);
        crsr.moveToFirst();
        //MsgPopup("ItemID=" + ItemID + "; crsrCount=" + crsr.getCount());
        String val = "";
        if(crsr.getCount() > 0) val = crsr.getString(0);
        myDB.close();
        return val;
    }

    //Inputs: a complete sql DELETE statement
    //Outputs: none
    public static void SqlRowDelete(String TableName, String KeyField, long ID) {
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        myDB.delete(TableName, KeyField + "=" + ID, null);
        myDB.close();
    }

    //Inputs: a string to be displayed to the user in a toast
    //Outputs: displays the input string as a toast
    public static void MsgPopup(String Text){
        Toast.makeText(me,Text,Toast.LENGTH_LONG).show();
    }


    /*** Application specific routines ***/

    //Inputs: none. This method should always be called whenever the app starts. It creates your DB and tables if they do not already exist.
    //Outputs: none. If the database or tables don't exist, it creates them, otherwise it does nothing.
    public static void SqlCreateTables(){
        SQLiteDatabase myDB= null;
        myDB = me.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
    /* Create Tables in the Database. */
        myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                + "IceInfo"
                + " (IceID INTEGER PRIMARY KEY, Name VARCHAR, Address VARCHAR, City VARCHAR, State VARCHAR, PostalCode VARCHAR, Country VARCHAR, "
                + "HomePhone VARCHAR, Photo VARCHAR, Allergies VARCHAR, Conditions VARCHAR, Meds VARCHAR, Contact1 VARCHAR, Contact2 VARCHAR, "
                + "PCFacility VARCHAR, PCPhysician VARCHAR, SCPhysician VARCHAR, HealthInsurance VARCHAR, Notes VARCHAR, LastUpdated VARCHAR);");
        myDB.close();
    }

    public static void PersonDelete(String id){
        SqlRowDelete("IceInfo","IceID",Long.parseLong(id));
    }

}
