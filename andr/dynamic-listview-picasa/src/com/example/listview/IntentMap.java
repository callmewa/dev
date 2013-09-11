package com.example.listview;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by callmewa on 8/11/13.
 */
public class IntentMap {

    public static final Map <Object, Object> SharedMap;
    public static final ImageDownloader IMAGE_DOWNLOADER;

    static {
        SharedMap = new HashMap<Object, Object>();
        IMAGE_DOWNLOADER = new ImageDownloader();
    }

}
