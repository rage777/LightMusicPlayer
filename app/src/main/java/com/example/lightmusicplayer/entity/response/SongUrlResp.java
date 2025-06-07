package com.example.lightmusicplayer.entity.response;

import java.util.List;

public class SongUrlResp {

    private List<Result> data;

    public SongUrlResp(List<Result> data) {
        this.data = data;
    }

    public List<Result> getData() {
        return data;
    }

    public static class Result{
        public Result(String url) {
            this.url = url;
        }

        private String url;

        public String getUrl() {
            return url;
        }
    }
}
