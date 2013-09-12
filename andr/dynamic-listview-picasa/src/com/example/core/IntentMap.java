package com.example.core;

import com.example.feed.ImageDownloader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by callmewa on 8/11/13.
 */
public class IntentMap {

    public static final Map <Object, Object> SHARED_MAP;
    public static final ImageDownloader IMAGE_DOWNLOADER;

    static {
        SHARED_MAP = new HashMap<Object, Object>();
        IMAGE_DOWNLOADER = new ImageDownloader();
    }

}
