package com.example.lightmusicplayer.page.home.discovery;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lightmusicplayer.common.BaseAdapter;
import com.example.lightmusicplayer.common.Page;
import com.example.lightmusicplayer.data.net.ApiServiceSingleton;
import com.example.lightmusicplayer.databinding.FragmentDiscoverBinding;
import com.example.lightmusicplayer.databinding.ItemPlaylistBinding;
import com.example.lightmusicplayer.entity.PlayListInfo;
import com.example.lightmusicplayer.entity.response.PlaylistsResponse;
import com.example.lightmusicplayer.page.playlistdetail.PlayListDetailActivity;
import com.example.lightmusicplayer.page.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFragment extends Fragment implements Page {

    private FragmentDiscoverBinding binding;

    private InnerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(getLayoutInflater(),container,false);
        initView();
        initData();
        initListener();
        return binding.getRoot();
    }

    @Override
    public void initView() {
        adapter = new InnerAdapter();
        binding.showlist.setAdapter(adapter);
        binding.showlist.setLayoutManager(new GridLayoutManager(
                requireContext(),2,GridLayoutManager.VERTICAL,false));

    }

    @Override
    public void initData(){
        ApiServiceSingleton.getApiService()
                .getHighQualityPlayLists()
                .enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PlaylistsResponse> call, Response<PlaylistsResponse> response) {
                if (!response.isSuccessful()){return;}
                List<PlayListInfo> infoList = new ArrayList<>();
                response.body().getPlaylists().forEach(playlist -> {
                    PlayListInfo playListInfo = new PlayListInfo(
                            playlist.getName(),
                            playlist.getId(),
                            playlist.getCoverImgUrl()
                    );

                    infoList.add(playListInfo);
                    adapter.submitList(infoList);
                });
            }

            @Override public void onFailure(Call<PlaylistsResponse> call, Throwable t) {}
        });
    }

    @Override
    public void initListener() {
        binding.search.getRoot().setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), SearchActivity.class));
        });

        adapter.setOnItemClickListener((v, index) -> {
            Intent i = new Intent(requireContext(), PlayListDetailActivity.class);
            i.putExtra(PlayListDetailActivity.KEY_ID,adapter.getItem(index).getId());
            startActivity(i);
        });
    }

    private static class ItemHolder extends RecyclerView.ViewHolder{
        public final ItemPlaylistBinding binding;
        public ItemHolder(ItemPlaylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class InnerAdapter extends BaseAdapter<PlayListInfo,ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType, Context context, LayoutInflater inflater) {
            return  new ItemHolder(ItemPlaylistBinding.inflate(inflater,parent,false));
        }


        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position, PlayListInfo playListInfo) {
            holder.binding.name.setText(playListInfo.getName());
            Glide.with(DiscoverFragment.this).clear(holder.binding.imageView7);
            Glide.with(DiscoverFragment.this)
                    .load(playListInfo.getCoverUrl())
                    .into(holder.binding.imageView7);
        }
    }

}