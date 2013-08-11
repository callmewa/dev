package com.google.picasa.model;

import java.util.List;

/**
 * Created by callmewa on 8/2/13.
 */


/**
 *
 *
 *
         "entry": [
         {
         "id": {
         "$t": "https://picasaweb.google.com/data/entry/api/user/103365738199970243739/albumid/5910250342995709537?alt=json"
         },
         "title": {
         "$t": "Fig",
         "type": "text"
         },
         "summary": {
         "$t": "",
         "type": "text"
         },
         "media$group": {
             "media$content": [
                 {
                     "url": "https://lh3.googleusercontent.com/-iWhvHvuUuJ4/UgVtX4PjzmE/AAAAAAAAANE/bSbwibzIHa8/Fig.jpg",
                     "type": "image/jpeg",
                     "medium": "image"
                 }
             ]
         }
         },
 */
public class Entry  {

    public $t id;

    public java.util.List<Link> link;

    public Content content;

    public $t updated;

    public $t title;

    public $t summary;

    public MediaGroup media$group;

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

    public MediaGroup getMedia$group() {
        return media$group;
    }
}