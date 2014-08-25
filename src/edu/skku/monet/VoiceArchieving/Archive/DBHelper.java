package edu.skku.monet.VoiceArchieving.Archive;

/**
 * Project IntelliJ IDEA
 * Module edu.skku.monet.VoiceArchieving.Archive
 * User: Gyuhyeon
 * Date: 2014. 8. 1.
 * Time: 오전 2:31
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;

    public static final String DATABASE_NAME = "VA.db";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(Constants.CATEGORY_DB_CREATE_STATEMENTS);
        db.execSQL(Constants.KEYWORD_DB_CREATE_STATEMENTS);
        db.execSQL(Constants.ARCHIVE_DB_CREATE_STATEMENTS);
        db.execSQL(Constants.ARCHIVE_CATEGORY_DB_CREATE_STATEMENTS);
        db.execSQL(Constants.ARCHIVE_KEYWORD_DB_CREATE_STATEMENTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public SQLiteDatabase getDbObject() {
        return this.getWritableDatabase();
    }

    public Cursor getData(String table, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM" + table + " WHERE " + "" +" id="+id+"", null );
        return res;
    }
    public int numberOfRows(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, table);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public ArrayList findAll(String table)
    {
        ArrayList array_list = new ArrayList();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + table + ";", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(table)));
            res.moveToNext();
        }
        return array_list;
    }
}