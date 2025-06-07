package com.example.lightmusicplayer.page.search;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

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
import com.example.lightmusicplayer.databinding.ActivitySearchBinding;
import com.example.lightmusicplayer.databinding.ItemMusicBinding;
import com.example.lightmusicplayer.entity.MusicInfo;
import com.example.lightmusicplayer.entity.response.SearchResponse;
import com.example.lightmusicplayer.util.ExUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements Page {

    public static final String TAG = "SearchActivity";

    private ActivitySearchBinding binding;
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
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 搜索结果展示
        RecyclerView rv = binding.list;
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InnerAdapter();
        rv.setAdapter(adapter);
    }

    @Override
    public void initData() {}

    @Override
    public void initListener() {

        binding.btnSearch.setOnClickListener(v -> {
            search(binding.et.getEditableText().toString());
        });

        adapter.setOnItemClickListener((v, index) -> {
            try {
                musicService.addMusicToListAndPlay(
                        GsonSingleton.getInstance().toJson(adapter.getItem(index))
                );
            } catch (RemoteException e) {
                ExUtil.w(e);
            }
        });

        // 搜索框
        binding.et.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                hideKeyboard(v);
                search(v.getText().toString());
                return true;
            }
            return false;
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

    private void hideKeyboard(View v){
        InputMethodManager manager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    private void search(String s){
        ApiServiceSingleton.getApiService().search(s).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                List<MusicInfo> item = new ArrayList<>();

                response.body().getResult().getSongs().forEach(song -> {

                    List<String> arList = song.getArtists()
                            .stream()
                            .map(SearchResponse.Songs.Song.Artist::getName)
                            .collect(Collectors.toList());

                    MusicInfo musicInfo = new MusicInfo(
                            song.getName(),
                            arList,
                            song.getAlbum().getName(),
                            song.getId(),
                            false
                    );

                    item.add(musicInfo);
                });

                adapter.submitList(item);

            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                Log.e(TAG,"fail",t);
            }
        });
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

    private static class InnerAdapter extends BaseAdapter<MusicInfo,ItemHolder>{
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
