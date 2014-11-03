package edu.skku.monet.VoiceArchieving.Archive;

/**
 * Project IntelliJ IDEA
 * Module edu.skku.monet.VoiceArchieving.Archive
 * User: Gyuhyeon
 * Date: 2014. 8. 1.
 * Time: 오전 2:13
 */
public class Constants {
    public static final int DATABASE_VERSION = 4;

    public static final String DATABASE_NAME = "VA";
    public static final String ARCHIVE_DATABASE_NAME = "archive";
    public static final String ARCHIVE_KEYWORD_DATABASE_NAME = "archive_keywords";
    public static final String ARCHIVE_CATEGORY_DATABASE_NAME = "archive_categories";
    public static final String CATEGORY_DATABASE_NAME = "category";
    public static final String KEYWORD_DATABASE_NAME = "keyword";

    public static final String ARCHIVE_DB_CREATE_STATEMENTS = "CREATE TABLE " + Constants.ARCHIVE_DATABASE_NAME +
            "(id INTEGER primary key autoincrement, " +
            "title TEXT NOT NULL, " +
            "comment TEXT, " +
            "location TEXT, " +
            "keywordCount INTEGER DEFAULT 0, " +
            "length INTEGER DEFAULT 0," +
            "datetime INTEGER DEFAULT 0, " +
            "popularity INTEGER DEFAULT 0," +
            "fileName TEXT NOT NULL);";


    public static final String ARCHIVE_KEYWORD_DB_CREATE_STATEMENTS = "CREATE TABLE " + Constants.ARCHIVE_KEYWORD_DATABASE_NAME +
            " (archive_id TEXT, " +
            "keyword TEXT NOT NULL, " +
            "time INTEGER, " +
            "PRIMARY KEY (archive_id, time));";

    public static final String ARCHIVE_CATEGORY_DB_CREATE_STATEMENTS = "CREATE TABLE " + Constants.ARCHIVE_CATEGORY_DATABASE_NAME +
            " (archive_id TEXT, " +
            "category_srl INTEGER, " +
            "PRIMARY KEY (archive_id, category_srl)); ";

    public static final String CATEGORY_DB_CREATE_STATEMENTS = "CREATE TABLE " + Constants.CATEGORY_DATABASE_NAME +
            " (srl INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "category TEXT NOT NULL UNIQUE);";

    public static final String KEYWORD_DB_CREATE_STATEMENTS = "CREATE TABLE " + Constants.KEYWORD_DATABASE_NAME +
            " (srl INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "keyword TEXT NOT NULL UNIQUE);";

}
