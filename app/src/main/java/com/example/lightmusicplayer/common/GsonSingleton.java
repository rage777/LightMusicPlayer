package com.example.lightmusicplayer.common;

import com.google.gson.Gson;

public class GsonSingleton {

    private static Gson INSTANCE;

    public synchronized static Gson getInstance() {
        if (INSTANCE == null){
           INSTANCE = new Gson();
        }
        return INSTANCE;
    }
}
