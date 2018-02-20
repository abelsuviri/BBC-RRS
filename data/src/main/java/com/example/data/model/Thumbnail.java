package com.example.data.model;

import org.simpleframework.xml.Attribute;

/**
 * @author Abel Suviri
 */

public class Thumbnail {

    @Attribute
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
