package com.example.lightmusicplayer.data.net;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceSingleton {


    private static ApiService instance;

    private static Retrofit retrofit;

    public static final String BASE_URL = "https://1319845944-d2xjrag5gz.ap-guangzhou.tencentscf.com";


    public synchronized static Retrofit getRetrofit() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

    public synchronized static ApiService getApiService(){
        if (instance == null){
            instance = getRetrofit().create(ApiService.class);
        }
        return instance;
    }


    public static String getMusicUrl(long id){
        return "https://music.163.com/song/media/outer/url?id="+id+".mp3";
    }
}
