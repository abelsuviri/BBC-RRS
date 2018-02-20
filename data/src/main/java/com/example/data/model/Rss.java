package com.example.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Abel Suviri
 */

@Root(strict = false)
public class Rss {

    @Element(required = false)
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
