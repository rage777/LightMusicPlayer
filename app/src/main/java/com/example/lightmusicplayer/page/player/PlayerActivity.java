package com.example.lightmusicplayer.page.player;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lightmusicplayer.IMusicServiceInterface;
import com.example.lightmusicplayer.IPlayerInfoCallbackInterface;
import com.example.lightmusicplayer.MusicService;
import com.example.lightmusicplayer.R;
import com.example.lightmusicplayer.common.GsonSingleton;
import com.example.lightmusicplayer.common.Page;
import com.example.lightmusicplayer.databinding.ActivityPlayerBinding;
import com.example.lightmusicplayer.entity.PlayerInfo;
import com.example.lightmusicplayer.util.ExUtil;

public class PlayerActivity extends AppCompatActivity implements Page {

    private ActivityPlayerBinding binding;

    private IMusicServiceInterface musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectToService();
        initView();
        initData();
        initListener();
    }

    private void connectToService(){
        Intent intent = new Intent(this, MusicService.class);
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService = IMusicServiceInterface.Stub.asInterface(service);
                try {
                    (PlayerActivity.this.musicService).registerCallback(callback);
                } catch (RemoteException e) {
                    ExUtil.w(e);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    public void initView() {
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void initData() {}

    @Override
    public void initListener() {

        binding.prev.setOnClickListener(v -> {
            try {
                musicService.previous();
            } catch (RemoteException e) {
                ExUtil.w(e);
            }
        });

        binding.next.setOnClickListener(v -> {
            try {
                musicService.next();
            } catch (RemoteException e) {
                ExUtil.w(e);
            }
        });

        binding.pause.setOnClickListener(v -> {
            switch (info.mode){
                case PlayerInfo.IDLE -> {}
                case PlayerInfo.PLAYING -> {
                    try {
                        musicService.pause();
                    } catch (RemoteException e) {
                        ExUtil.w(e);
                    }
                }
                case PlayerInfo.PAUSE -> {
                    try {
                        musicService.resume();
                    } catch (RemoteException e) {
                        ExUtil.w(e);
                    }
                }
            }
        });
    }


    private PlayerInfo info = PlayerInfo.idle();

    IPlayerInfoCallbackInterface callback = new IPlayerInfoCallbackInterface.Stub() {
        @Override
        public void onEvent(String infoJson) {
            PlayerInfo t = GsonSingleton.getInstance().fromJson(infoJson,PlayerInfo.class);
            runOnUiThread(() -> {
                PlayerActivity.this.onEvent(t);
            });
        }
    };

    public void onEvent(PlayerInfo info){
        setInfo(info);
    }

    /**
     * 带有状态的View如下:
     * 暂停播放
     * 歌曲名字
     * 进度条
     * 当前播放进度数字
     * 歌曲长度数字
     * 歌曲队列
     */
    public void setInfo(PlayerInfo info) {
        this.info = info;
        switch (info.mode){
            case PlayerInfo.IDLE -> idle();
            case PlayerInfo.PLAYING -> {
                binding.name.setText(info.name);
                binding.pause.setImageResource(R.drawable.pause_black);
            }
            case PlayerInfo.PAUSE -> {
                binding.name.setText(info.name);
                binding.pause.setImageResource(R.drawable.play_black);
            }
        }
    }

    void idle(){
        binding.pause.setImageResource(R.drawable.play_black);
        binding.name.setText("未播放");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            musicService.unregisterCallback(callback);
        } catch (RemoteException e) {
            ExUtil.w(e);
        }
    }


}
