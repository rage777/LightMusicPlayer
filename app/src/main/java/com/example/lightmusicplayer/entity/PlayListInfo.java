package com.example.lightmusicplayer.entity;

public class PlayListInfo {

    private final String name;

    private final long id;

    private final String coverUrl;


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public PlayListInfo(String name, long id, String coverUrl) {
        this.name = name;
        this.id = id;
        this.coverUrl = coverUrl;
    }
}
