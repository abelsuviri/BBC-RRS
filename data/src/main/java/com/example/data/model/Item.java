package com.example.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Abel Suviri
 */

@Root(strict = false)
public class Item {

    @Element
    private String title;

    @Element
    private String description;

    @Element
    private String link;

    @Element
    private String pubDate;

    @Element(required = false)
    private Thumbnail thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublishDate() {
        return pubDate;
    }

    public void setPublishDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}