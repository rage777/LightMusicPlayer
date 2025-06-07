package com.example.lightmusicplayer.page.home.localmusic;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightmusicplayer.MyApp;
import com.example.lightmusicplayer.common.BaseAdapter;
import com.example.lightmusicplayer.common.GsonSingleton;
import com.example.lightmusicplayer.common.Page;
import com.example.lightmusicplayer.databinding.FragmentLocalBinding;
import com.example.lightmusicplayer.databinding.ItemMusicBinding;
import com.example.lightmusicplayer.entity.MusicInfo;
import com.example.lightmusicplayer.page.home.MainActivity;
import com.example.lightmusicplayer.util.ExUtil;

import java.util.ArrayList;
import java.util.List;

public class LocalDetailFragment extends Fragment implements Page {


    public static final String TAG = "LocalDetailFragment";
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
        binding.showlist.setLayoutManager(new GridLayoutManager(
                requireContext(),2,GridLayoutManager.VERTICAL,false));

    }

    @Override
    public void initData(){
        List<MusicInfo> musicList = new ArrayList<>();

        ContentResolver resolver = requireActivity().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            Log.v(TAG,"条数:"+cursor.getCount());
            int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int dataIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int durationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

            do {
                String title = cursor.getString(titleIndex);
                String artist = cursor.getString(artistIndex);
                String path = cursor.getString(dataIndex);
                long duration = cursor.getLong(durationIndex);
                long id = cursor.getLong(idIndex);

                List<String> arList = new ArrayList<>();
                arList.add(artist);
                musicList.add(new MusicInfo(title, arList, "本地音乐",id,true));
            } while (cursor.moveToNext());

            cursor.close();

        } else {
            Log.v(TAG,"null!!");
        }

        adapter.submitList(musicList);
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