package edu.skku.monet.VoiceArchieving.Archive;

/**
 * Project IntelliJ IDEA
 * Module edu.skku.monet.VoiceArchieving.Archive
 * User: Gyuhyeon
 * Date: 2014. 8. 1.
 * Time: 오전 2:13
 */
public class Constants {
    public static final int DATABASE_VERSION = 3;

    public static final String DATABASE_NAME = "VA";
    public static final String ARCHIVE_DATABASE_NAME = "archive";
    public static final String ARCHIVE_KEYWORD_DATABASE_NAME = "archive_keywords";
    public static final String ARCHIVE_CATEGORY_DATABASE_NAME = "archive_categories";
    public static final String CATEGORY_DATABASE_NAME = "category";
    public static final String KEYWORD_DATABASE_NAME = "keyword";

    public static final String ARCHIVE_DB_CREATE_STATEMENTS = "CREATE TABLE " + Constants.ARCHIVE_DATABASE_NAME +
            " (id varchar(255) PRIMARY KEY, " +
            "title varchar(255) NOT NULL, " +
            "comment text, " +
            "location varchar(255), " +
            "keywordCount bigint DEFAULT 0, " +
            "datetime bigint DEFAULT 0, " +
            "popularity int DEFAULT 0);";


    public static final String ARCHIVE_KEYWORD_DB_CREATE_STATEMENTS = "CREATE TABLE " + Constants.ARCHIVE_KEYWORD_DATABASE_NAME +
            "(archive_id VARCHAR(255), " +
            "keyword VARCHAR(255) NOT NULL, " +
            "index bigint, " +
            "PRIMARY KEY (archive_id, index));";

    public static final String ARCHIVE_CATEGORY_DB_CREATE_STATEMENTS = "CREATE TABLE " + Constants.ARCHIVE_CATEGORY_DATABASE_NAME +
            "(archive_id VARCHAR(255), " +
            "category_srl int(11), " +
            "PRIMARY KEY (archive_id, category_srl)); ";

    public static final String CATEGORY_DB_CREATE_STATEMENTS = "CREATE TABLE " + Constants.CATEGORY_DATABASE_NAME +
            "(srl int(11) PRIMARY KEY AUTOINCREMENT, " +
            "category VARCHAR(255) NOT NULL UNIQUE);";

    public static final String KEYWORD_DB_CREATE_STATEMENTS = "CREATE TABLE" + Constants.KEYWORD_DATABASE_NAME +
            "(srl int(11) PRIMARY KEY AUTOINCREMENT, " +
            "keyword VARCHAR(255) NOT NULL UNIQUE);";

}
