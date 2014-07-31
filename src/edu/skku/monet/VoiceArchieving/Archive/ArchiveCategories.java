package edu.skku.monet.VoiceArchieving.Archive;

/**
 * Created with IntelliJ IDEA.
 * User: Gyuhyeon
 * Date: 2014. 7. 19.
 * Time: 오후 11:27
 * To change this template use File | Settings | File Templates.
 */
public class ArchiveCategories {
    String archive_id;
    String category;

    public ArchiveCategories()
    {

    }

    public ArchiveCategories(String archive_id, String category)
    {
        this.archive_id = archive_id;
        this.category = category;
    }

    public String getArchiveid()
    {
        return this.archive_id;
    }

    public String getCategory()
    {
        return this.category;
    }
}
