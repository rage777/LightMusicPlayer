package com.example.lightmusicplayer.entity;

import java.io.Serializable;

public class PlayerInfo {

    public String name = null;

    public int mode = IDLE;

    public static final int IDLE = -1;

    public static final int PLAYING = 0;

    public static final int PAUSE = 1;

    public PlayerInfo(String name,int mode) {
        this.name = name;
        this.mode = mode;
    }

    public PlayerInfo(){

    }

    public static PlayerInfo idle(){
        return new PlayerInfo();
    }

    public static PlayerInfo playing(String name){
        return new PlayerInfo(name,PLAYING);
    }

    public static PlayerInfo pause(String name){
        return new PlayerInfo(name,PAUSE);
    }

}
