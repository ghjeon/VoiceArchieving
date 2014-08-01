package edu.skku.monet.VoiceArchieving.Archive;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gyuhyeon
 * Date: 2014. 7. 19.
 * Time: 오후 11:19
 * To change this template use File | Settings | File Templates.
 */

public class Archive {

    private static final String CREATE_STATEMENTS = "CREATE TABLE " + Constants.ARCHIVE_DATABASE_NAME + " (id varchar(255) PRIMARY KEY, " +
                                                                                                          "title varchar(255) NOT NULL, " +
                                                                                                          "comment text, " +
                                                                                                          "location varchar(255), " +
                                                                                                          "keywordCount bigint DEFAULT 0, " +
                                                                                                          "datetime bigint DEFAULT 0, " +
                                                                                                          "popularity int DEFAULT 0";


    String id;
    String title;
    String comment;
    String location;
    long keywordCount;
    long datetime;
    long length;
    int popularity;

    SQLiteDatabase db;


    public Archive(){


    }

    public Archive(String id, String title, String comment, String location, long keywordCount, long datetime, long length, int popularity) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.location = location;
        this.keywordCount = keywordCount;
        this.datetime = datetime;
        this.length = length;
        this.popularity = popularity;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCOmment() {
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

    public long getPopularity() {
        return this.popularity;
    }

    public void set(Archive archive) {

    }

    public Archive findById(String id) {
        return this;

    }

    public List<Archive> findByKeyword(String keyword) {
                    return null;
    }

    public List<Archive> findBy(int index, int count) {
                                return null;
    }

    public List<Archive> findByCategory(String category) {
                                            return null;
    }

    public Archive update(Archive archive) {
                                                        return null;
    }

    public void delete(String id) {

    }


}
