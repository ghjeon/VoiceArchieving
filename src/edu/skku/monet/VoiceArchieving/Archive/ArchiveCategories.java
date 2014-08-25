package edu.skku.monet.VoiceArchieving.Archive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gyuhyeon
 * Date: 2014. 7. 19.
 * Time: 오후 11:27
 * To change this template use File | Settings | File Templates.
 */
public class ArchiveCategories {
    String archive_id;
    int category;

    SQLiteDatabase db;

    DBHelper dbHelper;

    public ArchiveCategories(Context context)
    {
        dbHelper = new DBHelper(context);
        db = dbHelper.getDbObject();
    }

    public ArchiveCategories(String archive_id, int category)
    {
        this.archive_id = archive_id;
        this.category = category;
    }

    public String getArchiveid()
    {
        return this.archive_id;
    }

    public int getCategory()
    {
        return this.category;
    }

    public void set() {

        ContentValues c = new ContentValues();

        c.put("archive_id", this.archive_id);
        c.put("category", this.category);

        db.insert(Constants.ARCHIVE_CATEGORY_DATABASE_NAME, null, c);

    }

    public void update() {
        ContentValues c = new ContentValues();

        c.put("archive_id", this.archive_id);
        c.put("category", this.category);

        db.update(Constants.ARCHIVE_DATABASE_NAME, c, "archive_id = ? AND category = ? ", new String[] { this.archive_id, Integer.toString(this.category) });
    }

    public ArchiveCategories findByArchive(String id) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.ARCHIVE_CATEGORY_DATABASE_NAME + " WHERE " +
                "archive_id= '" + id + "';", null);
        return buildObject(res);
    }

    public List<ArchiveCategories> findByCategory(int category) {
        Cursor res = db.rawQuery("SELECT * FROM " +
                Constants.ARCHIVE_CATEGORY_DATABASE_NAME +
                " WHERE " +
                "category = " + category + ";", null);
        res.moveToFirst();
        List<ArchiveCategories> list = new ArrayList<ArchiveCategories>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res));
        }
        return list;
    }

    public List<ArchiveCategories> findBy(int index, int count) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.ARCHIVE_DATABASE_NAME + " LIMIT " + (index-1)*count + ", " + count + ";", null);

        res.moveToFirst();
        List<ArchiveCategories> list = new ArrayList<ArchiveCategories>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res));
        }
        return list;
    }

    public void delete(String id) {

    }

    private ArchiveCategories buildObject(Cursor res)
    {
        ArchiveCategories result = new ArchiveCategories(
                res.getString(res.getColumnIndex("archive_id")),
                res.getInt(res.getColumnIndex("category")));

        return result;
    }

}
