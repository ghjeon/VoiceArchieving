package edu.skku.monet.VoiceArchieving.Archive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gyuhyeon
 * Date: 2014. 7. 19.
 * Time: 오후 11:19
 * To change this template use File | Settings | File Templates.
 */

public class Archive {

    String id;
    String title;
    String comment;
    String location;
    long keywordCount;
    long datetime;
    long length;
    int popularity;
    public static String fileName;

    SQLiteDatabase db;

    DBHelper dbHelper;


    public Archive(Context context){

        dbHelper = new DBHelper(context);
        db = dbHelper.getDbObject();

    }

    public void getConnection(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getDbObject();
    }

    public Archive(String id, String title, String comment, String location, long keywordCount, long datetime, long length, int popularity, String fileName) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.location = location;
        this.keywordCount = keywordCount;
        this.datetime = datetime;
        this.length = length;
        this.popularity = popularity;
        this.fileName = fileName;
    }

    public void Initialize(String id, String title, String comment, String location, long keywordCount, long datetime, long length, int popularity, String fileName) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.location = location;
        this.keywordCount = keywordCount;
        this.datetime = datetime;
        this.length = length;
        this.popularity = popularity;
        this.fileName = fileName;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getComment() {
        return this.comment;
    }

    public String getLocation() {
        return this.location;
    }

    public long getKeywordCount() {
        return this.keywordCount;
    }

    public long getDatetime() {
        return this.datetime;
    }

    public long getLength() {
        return this.length;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void set() {

        ContentValues c = new ContentValues();

        c.put("title", this.title);
        c.put("comment", this.comment);
        c.put("location", this.location);
        c.put("keywordCount", this.keywordCount);
        c.put("datetime", this.datetime);
        c.put("length", this.length);
        c.put("popularity", this.popularity);
        c.put("fileName", this.fileName);

        Long result = db.insert(Constants.ARCHIVE_DATABASE_NAME, null, c);
        ;

    }

    public void update() {
        ContentValues c = new ContentValues();

        c.put("id", this.id);
        c.put("title", this.title);
        c.put("comment", this.comment);
        c.put("location", this.location);
        c.put("keywordCount", this.keywordCount);
        c.put("datetime", this.datetime);
        c.put("length", this.length);
        c.put("popularity", this.popularity);
        c.put("fileName", this.fileName);

        db.update(Constants.ARCHIVE_DATABASE_NAME, c, "id = ? ", new String[] { this.id });
    }

    public Archive findById(String id) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.ARCHIVE_DATABASE_NAME + " WHERE " +
                                                                                "id = '" + id + "';", null);
        return buildObject(res);
    }

    public Archive findByFileName(String fileName) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.ARCHIVE_DATABASE_NAME + " WHERE fileName like '%" + fileName + "%';", null);
        res.moveToFirst();
        return buildObject(res);
    }

    public List<Archive> findByKeyword(int keyword) {
        Cursor res = db.rawQuery("SELECT DISTINCT * FROM " +
                Constants.ARCHIVE_KEYWORD_DATABASE_NAME +
                " AS K INNER JOIN " +
                Constants.ARCHIVE_DATABASE_NAME +
                " AS A ON K.archive_id = A.id WHERE " +
                "keyword = " + keyword + ";", null);
        res.moveToFirst();
        List<Archive> list = new ArrayList<Archive>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res));
        }
        return list;
    }

    public List<Archive> findBy(int index, int count) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.ARCHIVE_DATABASE_NAME + " LIMIT " + count + " OFFSET " + (index-1)*count + ";", null);

        res.moveToFirst();
        List<Archive> list = new ArrayList<Archive>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res));
            res.moveToNext();
        }
        return list;
    }

    public Cursor findByWithCursor(int index, int count) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.ARCHIVE_DATABASE_NAME + " LIMIT " + count + " OFFSET " + (index-1)*count + ";", null);

        return res;
    }

    public List<Archive> findByCategory(int category) {
        Cursor res = db.rawQuery("SELECT DISTINCT * FROM " +
                Constants.ARCHIVE_CATEGORY_DATABASE_NAME +
                " AS C INNER JOIN " +
                Constants.ARCHIVE_DATABASE_NAME +
                " AS A ON C.archive_id = A.id WHERE " +
                "category = " + category + ";", null);
        res.moveToFirst();
        List<Archive> list = new ArrayList<Archive>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res));
        }
        return list;
    }

    public void delete(String id) {

    }

    private Archive buildObject(Cursor res)
    {
        Archive result = new Archive(
                res.getString(res.getColumnIndex("id")),
                res.getString(res.getColumnIndex("title")),
                res.getString(res.getColumnIndex("comment")),
                res.getString(res.getColumnIndex("location")),
                res.getInt(res.getColumnIndex("keywordCount")),
                res.getLong(res.getColumnIndex("datetime")),
                res.getLong(res.getColumnIndex("length")),
                res.getInt(res.getColumnIndex("popularity")),
                res.getString(res.getColumnIndex("fileName")));

        return result;
    }


}
