package com.example.lightmusicplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.lightmusicplayer.common.GsonSingleton;
import com.example.lightmusicplayer.data.net.ApiServiceSingleton;
import com.example.lightmusicplayer.entity.MusicInfo;
import com.example.lightmusicplayer.entity.PlayerInfo;
import com.example.lightmusicplayer.entity.response.SongUrlResp;
import com.example.lightmusicplayer.util.ExUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicService extends Service implements
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener
{

    public static final String TAG = "MusicService";


    private MediaPlayer mediaPlayer;

    private final List<MusicInfo> playlist = new ArrayList<>();

    private int currentIndex = 0;

    private boolean isLoaded = false;

    private Handler mainThreadHandler;

// 状态管理

    private PlayerInfo info = PlayerInfo.idle();

    public PlayerInfo getInfo() {
        return info;
    }

    private void setInfo(PlayerInfo info) {
        this.info = info;
        dispatch();
    }

// 选曲相关

    public void addMusicToListAndPlay(MusicInfo info){
        playlist.add(0,info);
        currentIndex = 0;
        setInfo(PlayerInfo.playing(info.getName()));
        play(0);
    }

    public void loadPlaylistAndPlay(List<MusicInfo> urls,int playIndex) {
        playlist.clear();
        playlist.addAll(urls);
        currentIndex = playIndex;
        setInfo(PlayerInfo.playing(urls.get(playIndex).getName()));
        play(playIndex);
    }

    private volatile long subtaskId;
// 播放控制相关
    private void play(int index) {
        if (index >= 0 && index < playlist.size()) {
            isLoaded = false;
            currentIndex = index;
            subtaskId = System.currentTimeMillis();
            Log.v(TAG,"play");
            MusicInfo i = playlist.get(currentIndex);
            if (i.isLocal()){
                Log.v(TAG,"local"+i.getId());
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(
                            this,ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, i.getId())
                    );
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ApiServiceSingleton
                        .getApiService()
                        .getUrl(i.getId())
                        .enqueue(new Callback<>() {
                            @Override
                            public void onResponse(@NonNull Call<SongUrlResp> call, @NonNull Response<SongUrlResp> response) {
                                long t = subtaskId;
                                if (subtaskId == t) {
                                    assert response.body() != null;
                                    String url = response.body().getData().get(0).getUrl();
                                    mainThreadHandler.post(() -> {
                                        Log.v(TAG, "MusicUrl:" + url);
                                        try {
                                            mediaPlayer.reset();
                                            mediaPlayer.setDataSource(url);
                                            mediaPlayer.prepareAsync();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    });
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<SongUrlResp> call, @NonNull Throwable t) {
                                ExUtil.w(t, TAG);
                            }
                        });
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.v(TAG,"Player prepared");
        mediaPlayer.start();
        isLoaded = true;
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            setInfo(PlayerInfo.pause(playlist.get(currentIndex).getName()));
            mediaPlayer.pause();
        }
    }

    public void resume() {
        if (!mediaPlayer.isPlaying()) {
            setInfo(PlayerInfo.playing(playlist.get(currentIndex).getName()));
            mediaPlayer.start();
        }
    }

    public void seekTo(int position) {
        if (isLoaded){
            mediaPlayer.seekTo(position);
        }
    }

    public void next() {
        if (!playlist.isEmpty()) {
            int index = ++currentIndex % playlist.size();
            setInfo(PlayerInfo.playing(playlist.get(index).getName()));
            play(index);
        }
    }

    public void previous() {
        if (!playlist.isEmpty()) {
            int index = ++currentIndex % playlist.size();
            setInfo(PlayerInfo.playing(playlist.get(index).getName()));
            play(index);
        }
    }

// 主动获取播放器状态

    /**
     * @return 当前播放进度
     */
    public long getProgress(){
        if (isLoaded){
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    /**
     * @return 歌曲总长度
     */
    public long getDuration(){
        if (isLoaded){
            return mediaPlayer.getDuration();
        } else {
            return 0;
        }
    }


// 主动发送状态给连接的控制View

    public void bindView(IPlayerInfoCallbackInterface cb){
        controllerViews.add(cb);
        dispatch();
    }

    public void unBindView(IPlayerInfoCallbackInterface view){
        controllerViews.remove(view);
    }

    private final Set<IPlayerInfoCallbackInterface> controllerViews = new HashSet<>();

    private void dispatch()  {
        for (IPlayerInfoCallbackInterface v:controllerViews){
            try {
                v.onEvent(GsonSingleton.getInstance().toJson(getInfo()));
            } catch (RemoteException e) {
                ExUtil.w(e);
            }
        }
    }


//　初始化与结束

    private static final int NOTIFICATION_ID = 1;

    private static final String CHANNEL_ID = "cc";

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Player Channel";
            String description = "播放器通知频道";
            int importance = NotificationManager.IMPORTANCE_LOW; // 根据需要调整重要性
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // 注册通知渠道
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mainThreadHandler = new Handler(Looper.getMainLooper());

        createNotificationChannel();

        // 创建通知
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("音乐服务正在运行")
                .setContentText("...")
                .setSmallIcon(R.drawable.logo)
                .build();

        // 注册通知渠道
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1,notification);


        // 开始前台服务
        startForeground(NOTIFICATION_ID, notification);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
       next();
    }

    public final IBinder binder = new LocalBinder();

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        Log.e("Player","what:"+what);
        Log.e("Player","extra:"+what);
        return false;
    }

    public class LocalBinder extends IMusicServiceInterface.Stub {
        @Override
        public void registerCallback(IPlayerInfoCallbackInterface callback) throws RemoteException {
            mainThreadHandler.post(() -> bindView(callback));
        }

        @Override
        public void unregisterCallback(IPlayerInfoCallbackInterface callback) throws RemoteException {
            mainThreadHandler.post(() -> unBindView(callback));
        }

        @Override
        public void addMusicToListAndPlay(String musicInfoJson) throws RemoteException {
            MusicInfo t = GsonSingleton.getInstance().fromJson(musicInfoJson,MusicInfo.class);
            mainThreadHandler.post(() -> MusicService.this.addMusicToListAndPlay(t));
        }

        @Override
        public void play(int index) throws RemoteException {
            mainThreadHandler.post(() -> MusicService.this.play(index));

        }

        @Override
        public void pause() throws RemoteException {
            mainThreadHandler.post(MusicService.this::pause);
        }

        @Override
        public void resume() throws RemoteException {
            mainThreadHandler.post(MusicService.this::resume);
        }

        @Override
        public void seekTo(int position) throws RemoteException {
            mainThreadHandler.post(() -> MusicService.this.seekTo(position));
        }

        @Override
        public void next() throws RemoteException {
            mainThreadHandler.post(MusicService.this::next);
        }

        @Override
        public void previous() throws RemoteException {
            mainThreadHandler.post(MusicService.this::previous);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}