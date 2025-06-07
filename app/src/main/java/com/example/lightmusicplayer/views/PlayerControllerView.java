package com.example.lightmusicplayer.views;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.lightmusicplayer.IMusicServiceInterface;
import com.example.lightmusicplayer.IPlayerInfoCallbackInterface;
import com.example.lightmusicplayer.R;
import com.example.lightmusicplayer.common.GsonSingleton;
import com.example.lightmusicplayer.databinding.ViewPlayerControllerBinding;
import com.example.lightmusicplayer.entity.PlayerInfo;
import com.example.lightmusicplayer.page.player.PlayerActivity;
import com.example.lightmusicplayer.util.ExUtil;

public class PlayerControllerView extends LinearLayout {


    private ViewPlayerControllerBinding binding;


    public PlayerControllerView(Context context) {
        super(context);
        init();
    }

    public PlayerControllerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerControllerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PlayerControllerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private IMusicServiceInterface service;

    public void bindToService(IMusicServiceInterface musicService){
        try {
            musicService.registerCallback(callback);
            service = musicService;
        } catch (RemoteException e) {
            ExUtil.w(e);
        }
    }

    public void unBind(){
        try {
            service.unregisterCallback(callback);
        } catch (RemoteException e) {
            ExUtil.w(e);
        } finally {
            service = null;
        }

    }

    IPlayerInfoCallbackInterface callback = new IPlayerInfoCallbackInterface.Stub() {
        @Override
        public void onEvent(String infoJson){
            PlayerInfo t = GsonSingleton.getInstance().fromJson(infoJson,PlayerInfo.class);
            post(() -> {
                PlayerControllerView.this.onEvent(t);
            });
        }
    };


    public void serviceDisconnect(){
        setInfo(PlayerInfo.idle());
        service = null;
    }

    private PlayerInfo info = PlayerInfo.idle();

    public void setInfo(PlayerInfo info) {
        this.info = info;
        switch (info.mode){
            case PlayerInfo.IDLE -> idle();
            case PlayerInfo.PLAYING -> {
                binding.MusicName.setText(info.name);
                binding.play.setImageResource(R.drawable.pause_black);
            }
            case PlayerInfo.PAUSE -> {
                binding.MusicName.setText(info.name);
                binding.play.setImageResource(R.drawable.play_black);
            }
        }
    }

    public void onEvent(PlayerInfo info){
        setInfo(info);
    }

    void idle(){
        binding.MusicName.setText("无");
        binding.play.setImageResource(R.drawable.play_black);
    }

    private void init(){
        binding = ViewPlayerControllerBinding.inflate(LayoutInflater.from(getContext()));

        binding.play.setOnClickListener(v -> {
            switch (info.mode){
                case PlayerInfo.IDLE -> {}
                case PlayerInfo.PLAYING -> {
                    try {
                        service.pause();
                    } catch (RemoteException e) {
                        ExUtil.w(e);
                    }
                }
                case PlayerInfo.PAUSE -> {
                    try {
                        service.resume();
                    } catch (RemoteException e) {
                        ExUtil.w(e);
                    }
                }
            }
        });

        binding.next.setOnClickListener(v -> {
            try {
                service.next();
            } catch (RemoteException e) {
                ExUtil.w(e);
            }
        });

        binding.getRoot().setOnClickListener(v -> {
            Intent i = new Intent(getContext(), PlayerActivity.class);
            getContext().startActivity(i);
        });

        addView(binding.getRoot());
    }
}
