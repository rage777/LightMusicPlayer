package com.example.lightmusicplayer.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class ExUtil {

    public static final String TAG = "ExceptionUtil";
    public static void w(Throwable t, Activity activity){
        Log.w(TAG,"",t);
        Toast.makeText(activity,t.getClass().getName(),Toast.LENGTH_SHORT).show();
    }

    public static void w(Throwable t, Activity activity, String tag){
        Log.w(tag,"",t);
        Toast.makeText(activity,t.getClass().getName(),Toast.LENGTH_SHORT).show();
    }


    public static void w(Throwable t){
        Log.w(TAG,"",t);
    }

    public static void w(Throwable t,String tag){
        Log.w(tag,"",t);
    }
}
