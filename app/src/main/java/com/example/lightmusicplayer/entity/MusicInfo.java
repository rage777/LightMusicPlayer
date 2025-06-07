package com.example.lightmusicplayer.entity;

import java.io.Serializable;
import java.util.List;

public class MusicInfo {

    private String name;
    private List<String> artists;
    private String albumName;
    private long id;
    private boolean isLocal;

    public String getName() {
        return name;
    }

    public List<String> getArtists() {
        return artists;
    }

    public String getAlbumName() {
        return albumName;
    }

    public long getId() {
        return id;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public MusicInfo(String name, List<String> artists, String albumName, long id, boolean isLocal) {
        this.name = name;
        this.artists = artists;
        this.albumName = albumName;
        this.id = id;
        this.isLocal = isLocal;
    }
}
