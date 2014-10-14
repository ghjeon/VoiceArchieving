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
 * Time: 오후 11:26
 * To change this template use File | Settings | File Templates.
 */
public class Category {
    int srl;
    String category;

    SQLiteDatabase db;

    DBHelper dbHelper;

    public Category(Context context)
    {
        dbHelper = new DBHelper(context);
        db = dbHelper.getDbObject();
    }

    public Category(int srl, String category)
    {
        this.srl = srl;
        this.category = category;
    }

    public int getId()
    {
        return this.srl;
    }

    public String getCategory()
    {
        return this.category;
    }

    public void set() {

        ContentValues c = new ContentValues();

        c.put("srl", this.srl);
        c.put("category", this.category);

        db.insert(Constants.KEYWORD_DATABASE_NAME, null, c);

    }

    public void update() {
        ContentValues c = new ContentValues();

        c.put("srl", this.srl);
        c.put("category", this.category);

        db.update(Constants.KEYWORD_DATABASE_NAME, c, "srl = ?", new String[] { Integer.toString(this.srl) });
    }

    public Category findByCategory(String category) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.KEYWORD_DATABASE_NAME + " WHERE " +
                "category = '" + category + "';", null);
        return buildObject(res);
    }

    public List<Category> findBySrl(int srl) {
        Cursor res = db.rawQuery("SELECT * FROM " +
                Constants.KEYWORD_DATABASE_NAME +
                " WHERE " +
                "srl = " + srl + ";", null);
        res.moveToFirst();
        List<Category> list = new ArrayList<Category>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res));
            res.moveToNext();
        }
        return list;
    }

    public List<Category> findBy(int index, int count) {
        Cursor res = db.rawQuery("SELECT * FROM " + Constants.KEYWORD_DATABASE_NAME + " LIMIT " + (index-1)*count + ", " + count + ";", null);

        res.moveToFirst();
        List<Category> list = new ArrayList<Category>();
        while(res.isAfterLast() == false)
        {
            list.add(buildObject(res));
            res.moveToNext();
        }
        return list;
    }

    public void delete(String id) {

    }

    private Category buildObject(Cursor res)
    {
        Category result = new Category(
                res.getInt(res.getColumnIndex("srl")),
                res.getString(res.getColumnIndex("category")));

        return result;
    }
}
