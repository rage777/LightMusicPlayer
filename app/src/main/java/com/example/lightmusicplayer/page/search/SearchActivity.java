package com.example.lightmusicplayer.page.search;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightmusicplayer.IMusicServiceInterface;
import com.example.lightmusicplayer.MusicService;
import com.example.lightmusicplayer.R;
import com.example.lightmusicplayer.common.BaseAdapter;
import com.example.lightmusicplayer.common.GsonSingleton;
import com.example.lightmusicplayer.common.Page;
import com.example.lightmusicplayer.data.net.ApiServiceSingleton;
import com.example.lightmusicplayer.databinding.ActivitySearchBinding;
import com.example.lightmusicplayer.databinding.ItemMusicBinding;
import com.example.lightmusicplayer.databinding.ItemSearchHistoryBinding;
import com.example.lightmusicplayer.entity.MusicInfo;
import com.example.lightmusicplayer.entity.response.SearchResponse;
import com.example.lightmusicplayer.util.ExUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private HistoryAdapter historyAdapter;
    private IMusicServiceInterface musicService;

    private static final String SEARCH_HISTORY_KEY = "search_history";
    private SharedPreferences sharedPreferences;
    private List<String> searchHistory;

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

        // 搜索历史展示
        RecyclerView historyRv = binding.historyList;
        historyRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        historyAdapter = new HistoryAdapter();
        historyRv.setAdapter(historyAdapter);
    }

    @Override
    public void initData() {
        sharedPreferences = getSharedPreferences("search_prefs", Context.MODE_PRIVATE);
        String historyJson = sharedPreferences.getString(SEARCH_HISTORY_KEY, null);
        if (historyJson != null) {
            searchHistory = new Gson().fromJson(historyJson, new TypeToken<List<String>>(){}.getType());
        } else {
            searchHistory = new ArrayList<>();
        }
        updateHistoryView();
    }

    private void updateHistoryView() {
        historyAdapter.submitList(new ArrayList<>(searchHistory));
    }

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

        // 清除历史记录按钮
        binding.clearHistory.setOnClickListener(v -> {
            searchHistory.clear();
            sharedPreferences.edit().remove(SEARCH_HISTORY_KEY).apply();
            updateHistoryView();
        });

        historyAdapter.setOnItemClickListener((v, index) -> {
            search(binding.et.getEditableText().toString());
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

    private void search(String s) {
        if (!s.trim().isEmpty()) {
            // 添加到历史记录顶部（或去重后添加）
            if (!searchHistory.contains(s)) {
                searchHistory.add(0, s);

                // 限制最多保存 10 条记录
                if (searchHistory.size() > 10) {
                    searchHistory.remove(searchHistory.size() - 1);
                }

                // 保存到 SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SEARCH_HISTORY_KEY, new Gson().toJson(searchHistory));
                editor.apply();
                
                updateHistoryView();
            }

            // 发起网络请求
            ApiServiceSingleton.getApiService().search(s).enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                    List<MusicInfo> item = new ArrayList<>();

                    if (response.body() != null && response.body().getResult() != null && response.body().getResult().getSongs() != null) {
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
                    }

                    adapter.submitList(item);
                }

                @Override
                public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                    Log.e(TAG,"fail",t);
                }
            });
        }
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

    private static class HistoryHolder extends RecyclerView.ViewHolder {
        public ItemSearchHistoryBinding binding;
        public HistoryHolder(ItemSearchHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class HistoryAdapter extends BaseAdapter<String, HistoryHolder> {
        @Override
        public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType, Context context, LayoutInflater inflater) {
            return new HistoryHolder(ItemSearchHistoryBinding.inflate(inflater, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryHolder holder, int position, String history) {
            holder.binding.historyText.setText(history);
            holder.binding.historyText.setOnClickListener(v -> {
                binding.et.setText(history);
                search(history);
            });
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
