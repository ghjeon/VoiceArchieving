package edu.skku.monet.VoiceArchieving.Archive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gyuhyeon
 * Date: 2014. 7. 19.
 * Time: 오후 11:25
 * To change this template use File | Settings | File Templates.
 */
public class ArchiveKeywords {
    String archive_id;
    String archiveName;
    int keyword;
    String keywordName;
    long time;

    SQLiteDatabase db;

    DBHelper dbHelper;

    public ArchiveKeywords(Context context)
    {
        dbHelper = new DBHelper(context);
        db = dbHelper.getDbObject();
    }

    public ArchiveKeywords(String archive_id, int keyword, long time)
    {
        this.archive_id = archive_id;
        this.keyword = keyword;
        this.time = time;
    }

    public ArchiveKeywords(String archive_id, int keyword, long time, String keywordName)
    {
        this.archive_id = archive_id;
        this.keyword = keyword;
        this.time = time;
        this.keywordName = keywordName;
    }

    public ArchiveKeywords(String archive_id, String archiveName, int keyword, long time)
    {
        this.archive_id = archive_id;
        this.archiveName = archiveName;
        this.keyword = keyword;
        this.time = time;
    }

    public void Initialize(String archive_id, int keyword, long time)
    {
        this.archive_id = archive_id;
        this.keyword = keyword;
        this.time = time;
    }

    public void Initialize(String archive_id, int keyword, long time, String keywordName)
    {
        this.archive_id = archive_id;
        this.keyword = keyword;
        this.time = time;
        this.keywordName = keywordName;
    }

    public String getArchiveid()
    {
        return this.archive_id;
    }

    public String getArchiveName() { return this.archiveName; }

    public int getKeyword()
    {
        return this.keyword;
    }

    public String getKeywordName() { return this.keywordName; }

    public long getTime() { return this.time; }

    public void set() {

        ContentValues c = new ContentValues();

        c.put("archive_id", this.archive_id);
        c.put("keyword", this.keyword);
        c.put("time", this.time);

        db.insert(Constants.ARCHIVE_KEYWORD_DATABASE_NAME, null, c);

    }

    public void update() {
        ContentValues c = new ContentValues();

        c.put("archive_id", this.archive_id);
        c.put("keyword", this.keyword);
        c.put("time", this.time);

        db.update(Constants.ARCHIVE_KEYWORD_DATABASE_NAME, c, "archive_id = ? AND keyword = ? ", new String[] { this.archive_id, Integer.toString(this.keyword) });
    }

    public List<ArchiveKeywords> findByArchive(String id) {
        Cursor res = db.rawQuery("SELECT a.archive_id, a.keyword, k.keyword AS keywordName, a.time FROM " +
                Constants.ARCHIVE_KEYWORD_DATABASE_NAME +
                " AS a INNER JOIN " +
                Constants.KEYWORD_DATABASE_NAME  +
                " AS k ON a.keyword = k.srl WHERE " +
                "archive_id= '" + id + "' ORDER BY time asc;", null);
        res.moveToFirst();
        List<ArchiveKeywords> list = new ArrayList<ArchiveKeywords>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res, true, 0));
            res.moveToNext();
        }
        return list;
    }

    public List<ArchiveKeywords> findByKeyword(int keyword) {
        Cursor res = db.rawQuery("SELECT a.archive_id, a.keyword, ar.title AS archiveName, a.time FROM " +
                Constants.ARCHIVE_KEYWORD_DATABASE_NAME +
                " AS a INNER JOIN " +
                Constants.ARCHIVE_DATABASE_NAME  +
                " AS ar ON a.archive_id = ar.id WHERE " +
                "keyword = " + keyword + ";", null);
        res.moveToFirst();
        List<ArchiveKeywords> list = new ArrayList<ArchiveKeywords>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res, true, 1));
            res.moveToNext();
        }
        return list;
    }

    public List<ArchiveKeywords> findByKeyword(String keywordName) {
        Cursor res = db.rawQuery("SELECT a.archive_id, a.keyword, k.keyword AS keywordName, a.time FROM " +
                Constants.ARCHIVE_KEYWORD_DATABASE_NAME +
                " AS a INNER JOIN " +
                Constants.KEYWORD_DATABASE_NAME  +
                " AS k ON a.keyword = k.srl WHERE " +
                "keywordName = '" + keywordName + "';", null);
        res.moveToFirst();
        List<ArchiveKeywords> list = new ArrayList<ArchiveKeywords>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res, true, 0));
            res.moveToNext();
        }
        return list;
    }


    public List<ArchiveKeywords> findBy(int index, int count) {
        Cursor res = db.rawQuery("SELECT a.archive_id, a.keyword, k.keyword AS keywordName, a.time FROM " +
                Constants.ARCHIVE_KEYWORD_DATABASE_NAME +
                " AS a INNER JOIN " +
                Constants.KEYWORD_DATABASE_NAME  +
                " AS k ON a.keyword = k.srl LIMIT " + (index-1)*count + ", " + count + ";", null);

        res.moveToFirst();
        List<ArchiveKeywords> list = new ArrayList<ArchiveKeywords>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res, true, 0));
            res.moveToNext();
        }
        return list;
    }

    public void delete(String id) {

    }

    private ArchiveKeywords buildObject(Cursor res, Boolean isJoined, int typemode)
    {
        ArchiveKeywords result = null;
        if(isJoined)
            if(typemode == 0) {
                result = new ArchiveKeywords(
                        res.getString(res.getColumnIndex("archive_id")),
                        res.getInt(res.getColumnIndex("keyword")),
                        res.getLong(res.getColumnIndex("time")),
                        res.getString(res.getColumnIndex("keywordName")));
            }
            else if(typemode == 1) {
                result = new ArchiveKeywords(
                        res.getString(res.getColumnIndex("archive_id")),
                        res.getString(res.getColumnIndex("archiveName")),
                        res.getInt(res.getColumnIndex("keyword")),
                        res.getLong(res.getColumnIndex("time")));
            }
        else
            result = new ArchiveKeywords(
                    res.getString(res.getColumnIndex("archive_id")),
                    res.getInt(res.getColumnIndex("keyword")),
                    res.getLong(res.getColumnIndex("time")));

        return result;
    }
}
