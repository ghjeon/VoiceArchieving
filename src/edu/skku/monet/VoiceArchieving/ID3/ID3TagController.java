package edu.skku.monet.VoiceArchieving.ID3;

import org.jaudiotagger.audio.*;
import org.jaudiotagger.tag.*;
import org.jaudiotagger.tag.images.*;

import java.io.*;
import java.util.*;

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

public class ID3TagController {

    private File file = null;
    private AudioFile f = null;
    private Tag t = null;

    public ID3TagController(String path) throws Exception
    {
        file = new File(path);
        f = AudioFileIO.read(file);
        t = f.getTag();
        t.setEncoding("UTF-8");
    }

    public boolean setLocation(String location) throws Exception
    {
        TagField field = t.getFirstField(FieldKey.COUNTRY);
        if(field.isEmpty() == true)
            t.createField(FieldKey.COUNTRY, location);
        else
            t.setField(FieldKey.COUNTRY, location);

        AudioFileIO.write(f);
        return true;
    }

    public String getLocation() throws Exception
    {
        TagField field = t.getFirstField(FieldKey.COUNTRY);
        if(field.isEmpty() == false)
            return field.toString();
        else
            return null;
    }

    public boolean setDateTime(Integer datetime) throws Exception
    {
        TagField field = t.getFirstField(FieldKey.YEAR);
        if(field.isEmpty() == true)
            t.createField(FieldKey.YEAR, datetime.toString());
        else
            t.setField(FieldKey.YEAR, datetime.toString());

        AudioFileIO.write(f);
        return true;
    }

    public Integer getDateTime() throws Exception
    {
        TagField field = t.getFirstField(FieldKey.YEAR);
        if(field.isEmpty() == true)
            return Integer.getInteger(field.toString());
        else
            return 0;
    }

    public boolean setLength(Integer length) throws Exception
    {
        TagField field = t.getFirstField(FieldKey.CUSTOM1);
        if(field.isEmpty() == true)
            t.createField(FieldKey.CUSTOM1, length.toString());
        else
            t.setField(FieldKey.CUSTOM1, length.toString());

        AudioFileIO.write(f);
        return true;
    }

    public Integer getLength() throws Exception
    {
        TagField field = t.getFirstField(FieldKey.CUSTOM1);
        if(field.isEmpty() == false)
            return Integer.getInteger(field.toString());
        else
            return 0;
    }

    public boolean setKeywords(List<String> keywords, Boolean overwrite) throws Exception
    {
        Integer i = 0;
        if(overwrite)
            t.deleteField(FieldKey.LYRICS);

        List<TagField> field = t.getFields(FieldKey.LYRICS);

        if(field.isEmpty() == true)
        {
            t.createField(FieldKey.LYRICS, keywords.get(i));
            i++;
        }
        for(; i < keywords.size(); i++)
        {
            t.addField(FieldKey.LYRICS, keywords.get(i));
        }
        AudioFileIO.write(f);
        return true;
    }

    public List<String> getKeywords() throws Exception
    {
        List<String> keywords = Arrays.asList();
        List<TagField> fields = t.getFields(FieldKey.LYRICS);
        if(fields.isEmpty() == false)
        {
            for(Integer i = 0; i < fields.size(); i++)
            {
                keywords.add(fields.get(i).toString());
            }
        }

        return keywords;
    }

    public boolean setKeywordCount(Integer count) throws Exception
    {
        TagField field = t.getFirstField(FieldKey.TRACK);

        if(field.isEmpty() == true)
            t.createField(FieldKey.TRACK, count.toString());
        else
            t.setField(FieldKey.TRACK, count.toString());
        AudioFileIO.write(f);
        return true;
    }

    public Integer getKeywordCount() throws Exception
    {
        TagField field = t.getFirstField(FieldKey.TRACK);
        if(field.isEmpty() == false)
            return Integer.getInteger(t.getFirstField(FieldKey.TRACK).toString());
        else
            return 0;
    }

    public boolean setSpeakers(List<String> speakers, Boolean overwrite) throws Exception
    {
        Integer i = 0;
        if(overwrite)
            t.deleteField(FieldKey.LYRICIST);

        List<TagField> fields = t.getFields(FieldKey.LYRICS);

        if(fields.isEmpty() == true)
        {
            t.createField(FieldKey.LYRICIST, speakers.get(i));
            i++;
        }

        for(; i < speakers.size(); i++)
        {
            t.addField(FieldKey.LYRICIST,speakers.get(i));
        }

        AudioFileIO.write(f);
        return true;
    }

    public List<String> getSpeakers() throws Exception
    {
        List<String> speakers = Arrays.asList();
        List<TagField> fields = t.getFields(FieldKey.LYRICIST);
        if(fields.isEmpty() == false)
        {
            for(Integer i = 0; i < fields.size(); i++)
            {
                speakers.add(fields.get(i).toString());
            }
        }

        return speakers;
    }

    public boolean setTitle(String title) throws Exception
    {
        TagField field = t.getFirstField(FieldKey.TITLE);
        if(field.isEmpty() == true)
            t.createField(FieldKey.TITLE, title);
        else
            t.setField(FieldKey.TITLE, title);

        AudioFileIO.write(f);
        return true;
    }

    public String getTitle() throws Exception
    {
        TagField field = t.getFirstField(FieldKey.TITLE);
        if(field.isEmpty() == false)
            return field.toString();
        else
            return null;
    }

    public boolean setComment(String comment) throws Exception
    {
        TagField field = t.getFirstField(FieldKey.COMMENT);
        if(field.isEmpty() == true)
            t.addField(FieldKey.COMMENT, comment);
        else
            t.setField(FieldKey.COMMENT, comment);

        AudioFileIO.write(f);
        return true;
    }

    public String getComment() throws Exception
    {
        TagField field = t.getFirstField(FieldKey.COMMENT);
        if(field.isEmpty() == false)
            return field.toString();
        else
            return null;
    }

    public boolean setPicture(File imgFile) throws Exception
    {
        Artwork field = t.getFirstArtwork();
        if(field == null)
            t.createField(ArtworkFactory.createArtworkFromFile(imgFile));
        else
            field.setFromFile(imgFile);
        AudioFileIO.write(f);
        return true;
    }

    public Artwork getPicture() throws Exception
    {
        if(t.getFirstArtwork() != null)
            return t.getFirstArtwork();
        else
            return null;
    }

    public boolean setCategories(List<String> categories, Boolean overwrite) throws Exception
    {
        Integer i = 0;
        if(overwrite)
            t.deleteField(FieldKey.CUSTOM2);

        if(t.getFields(FieldKey.CUSTOM2).isEmpty() == true)
        {
            t.createField(FieldKey.CUSTOM2, categories.get(i));
            i++;
        }

        for(; i < categories.size(); i++)
        {
            t.addField(FieldKey.CUSTOM2, categories.get(i));
        }

        AudioFileIO.write(f);
        return true;
    }

    public List<String> getCategories() throws Exception
    {
        List<String> categories = Arrays.asList();
        List<TagField> fields = t.getFields(FieldKey.CUSTOM2);

        if(fields.isEmpty() == false)
        {
            for(Integer i = 0; i < fields.size(); i++)
            {
                categories.add(fields.get(i).toString());
            }
        }

        return categories;
    }

    public boolean setTags(List<String> tags, Boolean overwrite) throws Exception
    {
        Integer i = 0;
        if(overwrite)
            t.deleteField(FieldKey.CUSTOM3);

        if(t.getFields(FieldKey.CUSTOM3).isEmpty() == true)
        {
            t.createField(FieldKey.CUSTOM3, tags.get(i));
            i++;
        }

        for(; i < tags.size(); i++)
        {
            t.addField(FieldKey.CUSTOM3, tags.get(i));
        }

        AudioFileIO.write(f);
        return true;
    }

    public List<String> getTags() throws Exception
    {
        List<String> tags = Arrays.asList();
        List<TagField> fields = t.getFields(FieldKey.CUSTOM3);

        if(fields.isEmpty() == false)
        {
            for(Integer i = 0; i < fields.size(); i++)
            {
                tags.add(fields.get(i).toString());
            }
        }

        return tags;
    }

    public boolean setPopular(Integer popular) throws Exception
    {
        TagField field = t.getFirstField(FieldKey.CUSTOM4);
        if(field.isEmpty() == true)
            t.addField(FieldKey.CUSTOM4, popular.toString());
        else
            t.setField(FieldKey.CUSTOM4, popular.toString());

        AudioFileIO.write(f);
        return true;
    }

    public Integer getPopular() throws Exception
    {
        TagField field = t.getFirstField(FieldKey.CUSTOM4);
        if(field.isEmpty() == false)
            return Integer.getInteger(field.toString());
        else
            return null;
    }
}
