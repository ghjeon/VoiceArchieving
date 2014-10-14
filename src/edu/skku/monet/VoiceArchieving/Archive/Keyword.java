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
 * Time: 오후 11:20
 * To change this template use File | Settings | File Templates.
 */
public class Keyword {
    int srl;
    String keyword;

    SQLiteDatabase db;

    DBHelper dbHelper;

    public Keyword(Context context)
    {
        dbHelper = new DBHelper(context);
        db = dbHelper.getDbObject();
    }

    public Keyword(int srl, String keyword)
    {
        this.srl = srl;
        this.keyword = keyword;
    }

    public void Initialize(int srl, String keyword)
    {
        this.srl = srl;
        this.keyword = keyword;
    }

    public int getId()
    {
        return this.srl;
    }

    public String getKeyword()
    {
        return this.keyword;
    }

    public void set() {

        ContentValues c = new ContentValues();

        c.put("keyword", this.keyword);

        db.insert(Constants.KEYWORD_DATABASE_NAME, null, c);

    }

    public void update() {
        ContentValues c = new ContentValues();

        c.put("srl", this.srl);
        c.put("keyword", this.keyword);

        db.update(Constants.KEYWORD_DATABASE_NAME, c, "srl = ?", new String[] { Integer.toString(this.srl) });
    }

    public Keyword findByKeyword(String keyword) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.KEYWORD_DATABASE_NAME + " WHERE " +
                "keyword = '" + keyword + "';", null);
        res.moveToFirst();
        return buildObject(res);
    }

    public List<Keyword> findBySrl(int srl) {
        Cursor res = db.rawQuery("SELECT * FROM " +
                Constants.KEYWORD_DATABASE_NAME +
                " WHERE " +
                "srl = " + srl + ";", null);
        res.moveToFirst();
        List<Keyword> list = new ArrayList<Keyword>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res));
            res.moveToNext();
        }
        return list;
    }

    public List<Keyword> findBy(int index, int count) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.KEYWORD_DATABASE_NAME + " LIMIT " + (index-1)*count + ", " + count + ";", null);

        res.moveToFirst();
        List<Keyword> list = new ArrayList<Keyword>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res));
            res.moveToNext();
        }
        return list;
    }

    public void delete(String id) {

    }

    private Keyword buildObject(Cursor res)
    {
        try {
            Keyword result = new Keyword(
                    res.getInt(res.getColumnIndex("srl")),
                    res.getString(res.getColumnIndex("keyword")));

            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
