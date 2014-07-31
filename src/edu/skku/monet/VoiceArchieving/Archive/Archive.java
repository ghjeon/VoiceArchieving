package edu.skku.monet.VoiceArchieving.Archive;

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
}
