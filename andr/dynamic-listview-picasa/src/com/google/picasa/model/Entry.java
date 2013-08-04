package com.google.picasa.model;

import java.util.List;

/**
 * Created by callmewa on 8/2/13.
 */
public class Entry  {

    public $t id;

    public java.util.List<Link> link;

    public Content content;

    public $t updated;

    public $t title;

    public $t summary;

    public $t getId() {
        return id;
    }

    public List<Link> getLink() {
        return link;
    }

    public $t getUpdated() {
        return updated;
    }

    public $t getTitle() {
        return title;
    }

    public $t getSummary() {
        return summary;
    }

    public Content getContent() {
        return content;
    }
}