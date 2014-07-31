package edu.skku.monet.VoiceArchieving.Archive;

/**
 * Created with IntelliJ IDEA.
 * User: Gyuhyeon
 * Date: 2014. 7. 16.
 * Time: 오후 3:53
 * To change this template use File | Settings | File Templates.
 */

/*
장소 - Copyright Message     --> FieldKey.COUNTRY
시간, 날짜 - Date, Time  --> FieldKey.YEAR
길이 - Length --> FieldKey.CUSTOM1
키워드 목록 - Lyric
키워드 개수 - Track Number
대화 참여자 - Composer
제목 - Title
코멘트 - Comment
사진 - Attached Picture
카테고리 - Cotent Description
태그 - Content Group Description
Favorite -Popularimeter
 */



/*

public class ID3TagController {

    private Mp3File mp3File = null;
    private ID3v1 otag = null;
    private ID3v2 tag = null;
    private String srcPath = "";
    private String srcName = "";
    private String destPath = "";

    public ID3TagController(String path) throws Exception
    {
        String[] pathInfo = path.split("/");
        String[] nameInfo = pathInfo[pathInfo.length - 1].split("\\.");
        for(int i = 0; i < pathInfo.length - 1; i++)
            srcPath += pathInfo[i] + "/";
        for(int j = 0; j < nameInfo.length - 1; j++)
            srcName += nameInfo[j];

        destPath = srcPath + srcName + "_.mp3";

        mp3File = new Mp3File(path);
        otag = mp3File.getId3v1Tag();
        tag = mp3File.getId3v2Tag();
        if(tag == null)
        {
            ID3Wrapper newWarpper = new ID3Wrapper(new ID3v1Tag(), new ID3v24Tag());
            mp3File.setId3v1Tag(newWarpper.getId3v1Tag());
            mp3File.setId3v2Tag(newWarpper.getId3v2Tag());
            mp3File.save(destPath);
            tag = mp3File.getId3v2Tag();
        }

    }

    public boolean setLocation(String location) throws Exception
    {
        tag.setCopyright(location);
        mp3File.save(destPath);
        return true;
    }

    public String getLocation() throws Exception
    {
        return tag.getCopyright();
    }

    public boolean setDateTime(Integer datetime) throws Exception
    {
        tag.setGenre(datetime);
        return true;
    }

    public Integer getDateTime() throws Exception
    {
        return tag.getGenre();
    }

    public boolean setLength(Integer length) throws Exception
    {
        tag.setAlbum(length.toString());
        return true;
    }

    public Integer getLength() throws Exception
    {
        return Integer.getInteger(tag.getAlbum());
    }

    public boolean setKeywords(String keywords, Boolean overwrite) throws Exception
    {
        tag.setGenreDescription(keywords);
        return true;
    }

    public List<String> getKeywords() throws Exception
    {
        if(tag.getGenreDescription() != null)
            return Arrays.asList(tag.getGenreDescription().split(","));
        else
            return null;
    }

    public boolean setKeywordCount(Integer count) throws Exception
    {
        tag.setTrack(count.toString());
        return true;
    }

    public Integer getKeywordCount() throws Exception
    {
        return Integer.getInteger(tag.getTrack());
    }

    public boolean setSpeakers(String speakers, Boolean overwrite) throws Exception
    {
        tag.setComposer(speakers);
        return true;
    }

    public List<String> getSpeakers() throws Exception
    {
        if(tag.getComposer() != null)
            return Arrays.asList(tag.getComposer().toString().split(","));
        else
            return null;
    }

    public boolean setTitle(String title) throws Exception
    {
        tag.setTitle(title);
        return true;
    }

    public String getTitle() throws Exception
    {
        return tag.getTitle();
    }

    public boolean setComment(String comment) throws Exception
    {
        tag.setComment(comment);
        return true;
    }

    public String getComment() throws Exception
    {
        return tag.getComment();
    }

    public boolean setPicture(File imgFile) throws Exception
    {

        Artwork field = t.getFirstArtwork();
        if(field == null)
            t.createField(ArtworkFactory.createArtworkFromFile(imgFile));
        else
            field.setFromFile(imgFile);
        f.commit();
        return true;
    }

    public void getPicture() throws Exception
    {
        if(t.getFirstArtwork() != null)
            return t.getFirstArtwork();
        else
            return null;
    }

    public boolean setCategories(String categories, Boolean overwrite) throws Exception
    {
        tag.setPartOfSet(categories);
        return true;
    }

    public List<String> getCategories() throws Exception
    {
        if(tag.getPartOfSet() != null)
            return Arrays.asList(tag.getPartOfSet().split(","));
        else
            return null;
    }

    public boolean setTags(String tags, Boolean overwrite) throws Exception
    {
        tag.setItunesComment(tags);
        return true;
    }

    public List<String> getTags() throws Exception
    {
        if(tag.getItunesComment() != null)
            return Arrays.asList(tag.getItunesComment().split(","));
        else
            return null;
    }

    public boolean setPopular(Integer popular) throws Exception
    {
        tag.setPublisher(popular.toString());
        return true;
    }

    public Integer getPopular() throws Exception
    {
        if(tag.getPublisher() != null)
            return Integer.getInteger(tag.getPublisher());
        else
            return 0;
    }
}
        */