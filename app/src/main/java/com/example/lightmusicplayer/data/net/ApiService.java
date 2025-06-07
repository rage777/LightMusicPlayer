package com.example.lightmusicplayer.data.net;

import com.example.lightmusicplayer.entity.response.PlayListDetailResponse;
import com.example.lightmusicplayer.entity.response.PlaylistsResponse;
import com.example.lightmusicplayer.entity.response.SearchResponse;
import com.example.lightmusicplayer.entity.response.SongUrlResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search")
    Call<SearchResponse> search(@Query("keywords") String keywords);

    @GET("/top/playlist/highquality")
    Call<PlaylistsResponse> getHighQualityPlayLists();

    @GET("/playlist/detail")
    Call<PlayListDetailResponse> getAlbumDetail(@Query("id") long id);

    @GET("/song/url")
    Call<SongUrlResp> getUrl(@Query("id") long id);
}
