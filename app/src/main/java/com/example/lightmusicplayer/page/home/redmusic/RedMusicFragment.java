package com.example.lightmusicplayer.page.home.redmusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightmusicplayer.common.BaseAdapter;
import com.example.lightmusicplayer.common.GsonSingleton;
import com.example.lightmusicplayer.common.Page;
import com.example.lightmusicplayer.data.net.ApiServiceSingleton;
import com.example.lightmusicplayer.databinding.FragmentLocalBinding;
import com.example.lightmusicplayer.databinding.ItemMusicBinding;
import com.example.lightmusicplayer.entity.MusicInfo;
import com.example.lightmusicplayer.entity.response.PlayListDetailResponse;
import com.example.lightmusicplayer.page.home.MainActivity;
import com.example.lightmusicplayer.util.ExUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedMusicFragment extends Fragment implements Page {


    public static final String TAG = "RedMusicFragment";
    private FragmentLocalBinding binding;

    private InnerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocalBinding.inflate(getLayoutInflater(),container,false);
        initView();
        initData();
        initListener();
        return binding.getRoot();
    }

    @Override
    public void initView() {
        adapter = new InnerAdapter();
        binding.showlist.setAdapter(adapter);
        binding.showlist.setLayoutManager(new LinearLayoutManager(
                requireContext(),LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void initData(){
        ApiServiceSingleton.getApiService().getAlbumDetail(145196957).enqueue(new Callback<>() {
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
                ((MainActivity) requireActivity())
                        .getMusicService()
                        .addMusicToListAndPlay(
                                GsonSingleton.getInstance().toJson(adapter.getItem(index))
                        );
            } catch (RemoteException e) {
                ExUtil.w(e);
            }

        });
    }

    private static class ItemHolder extends RecyclerView.ViewHolder{
        public final ItemMusicBinding binding;
        public ItemHolder(ItemMusicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class InnerAdapter extends BaseAdapter<MusicInfo, ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType, Context context, LayoutInflater inflater) {
            return  new ItemHolder(ItemMusicBinding.inflate(inflater,parent,false));
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