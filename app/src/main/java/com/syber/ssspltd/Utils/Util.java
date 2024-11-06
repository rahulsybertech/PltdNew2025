package com.syber.ssspltd.Utils;

import android.util.Log;
public class Util {

    private static Util instance;

    private Util() {}

    public static synchronized Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public void logLargeString(String tag, String content) {
        if (content.length() > 4000) {
            for (int i = 0; i < content.length(); i += 4000) {
                if (i + 4000 < content.length()) {
                    Log.d(tag, content.substring(i, i + 4000));
                } else {
                    Log.d(tag, content.substring(i));
                }
            }
        } else {
            Log.d(tag, content);
        }
    }
}

