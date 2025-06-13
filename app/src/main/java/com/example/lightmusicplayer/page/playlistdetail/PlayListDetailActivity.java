package com.example.lightmusicplayer.page.playlistdetail;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightmusicplayer.IMusicServiceInterface;
import com.example.lightmusicplayer.MusicService;
import com.example.lightmusicplayer.common.BaseAdapter;
import com.example.lightmusicplayer.common.GsonSingleton;
import com.example.lightmusicplayer.common.Page;
import com.example.lightmusicplayer.data.net.ApiServiceSingleton;
import com.example.lightmusicplayer.databinding.ActivityPlaylistdetailBinding;
import com.example.lightmusicplayer.databinding.ItemMusicBinding;
import com.example.lightmusicplayer.entity.MusicInfo;
import com.example.lightmusicplayer.entity.response.PlayListDetailResponse;
import com.example.lightmusicplayer.util.ExUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayListDetailActivity extends AppCompatActivity implements Page {

    public static final String TAG = "PlayListDetailActivity";

    private ActivityPlaylistdetailBinding binding;
    private InnerAdapter adapter;
    private IMusicServiceInterface musicService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
        bindService();
    }

    @Override
    public void initView(){
        binding = ActivityPlaylistdetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView rv = binding.list;
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InnerAdapter();
        rv.setAdapter(adapter);
    }

    public static final String KEY_ID = "id";

    @Override
    public void initData() {

        long id = getIntent().getLongExtra(KEY_ID,-1);
        if (id == -1){
            throw new RuntimeException("未传入要查看的歌单id");
        }
        ApiServiceSingleton.getApiService().getAlbumDetail(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PlayListDetailResponse> call, Response<PlayListDetailResponse> response) {

                List<MusicInfo> infoList = new ArrayList<>();
                response.body().getPlaylist().getTracks().forEach(track -> {
                    MusicInfo info = new MusicInfo(
                            track.getName(),
                            Arrays.stream(track.getAr())
                                    .map(PlayListDetailResponse.Playlist.Track.Artist::getName)
                                    .collect(Collectors.toList()),
                            track.getAl().getName(),
                            track.getId(),
                            false
                    );

                    infoList.add(info);


                });
                adapter.submitList(infoList);
            }

            @Override
            public void onFailure(Call<PlayListDetailResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener((v, index) -> {
            try {
                musicService.addMusicToListAndPlay(
                        GsonSingleton.getInstance().toJson(adapter.getItem(index))
                );
            } catch (RemoteException e) {
                ExUtil.w(e);
            }
        });

        binding.playall.setOnClickListener(v -> {
            try {
                musicService.loadPlaylistAndPlay(
                        GsonSingleton.getInstance().toJson(adapter.getAllItem()),
                        0
                );
            } catch (RemoteException e) {
                ExUtil.w(e);
            }

        });
    }

    public void bindService(){
        Intent intent = new Intent(this, MusicService.class);
        ContextCompat.startForegroundService(this,intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService = IMusicServiceInterface.Stub.asInterface(service);
                binding.playerControllerView.bindToService(musicService);

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.playerControllerView.unBind();
    }

    private static class ItemHolder extends RecyclerView.ViewHolder{
        public ItemMusicBinding binding;
        public ItemHolder(ItemMusicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class InnerAdapter extends BaseAdapter<MusicInfo, ItemHolder> {
        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType,
                                                            Context context,
                                                            LayoutInflater inflater
        ) {
            return new ItemHolder(ItemMusicBinding.inflate(inflater,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position, MusicInfo musicInfo) {
            String s = musicInfo.getName();
            s+="(";
            s+=musicInfo.getAlbumName();
            s+=")";
            s+="   --";
            String arStr = musicInfo.getArtists().toString();
            s+=arStr.substring(1,arStr.length()-1);
            holder.binding.name.setText(s);
        }

    }
}
