package com.tleaf.lifelog.util;

import android.util.Log;

/**
 * Created by jangyoungjin on 2014. 3. 28..
 */
public class Mylog {
    private static final boolean isDebug = true;

    public static void i(String tag, String msg){
        if(isDebug){
            Log.i(tag, msg);
        }
    }
}


