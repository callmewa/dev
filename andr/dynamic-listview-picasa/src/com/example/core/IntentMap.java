package com.example.core;

import com.example.feed.ImageDownloader;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by callmewa on 8/11/13.
 */
public class IntentMap {

    public static final Map <Object, Object> SHARED_MAP;
    public static final ImageDownloader IMAGE_DOWNLOADER;
    public static final Gson GSON;

    static {
        SHARED_MAP = new HashMap<Object, Object>();
        IMAGE_DOWNLOADER = new ImageDownloader();
        IMAGE_DOWNLOADER.setMode(ImageDownloader.Mode.CORRECT);
        GSON = new Gson();
    }

}
