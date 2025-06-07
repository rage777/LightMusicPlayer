package com.example.lightmusicplayer.page.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.lightmusicplayer.IMusicServiceInterface;
import com.example.lightmusicplayer.MusicService;
import com.example.lightmusicplayer.common.Page;
import com.example.lightmusicplayer.databinding.ActivityMainBinding;
import com.example.lightmusicplayer.page.home.discovery.DiscoverFragment;
import com.example.lightmusicplayer.page.home.localmusic.LocalDetailFragment;
import com.example.lightmusicplayer.page.home.redmusic.RedMusicFragment;

public class MainActivity extends AppCompatActivity implements Page {

    private ActivityMainBinding binding;

    private IMusicServiceInterface musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ensurePermission();
        connectToService();
        initView();
        initData();
        initListener();
    }

    private void ensurePermission() {
        if (!isNotificationEnabled()){
            requestNotificationPermission();
        }

        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

    }

    /**
     * 判断通知权限是否已开启
     */
    private boolean isNotificationEnabled() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        return manager.areNotificationsEnabled();
    }

    /**
     * 跳转到通知设置界面
     */
    private void requestNotificationPermission() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8.0+ 直接跳转到应用通知设置页
            intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        } else {
            // 旧版本跳转到应用详情页
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        }

        startActivity(intent);
    }

    public void connectToService(){
        Intent intent = new Intent(this, MusicService.class);
        ContextCompat.startForegroundService(this,intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService = IMusicServiceInterface.Stub.asInterface(service);
                binding.playerBar.bindToService(musicService);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);

    }

    public IMusicServiceInterface getMusicService() {
        return musicService;
    }

    @Override
    public void initView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //用fragment配合viewpage2实现页面切换
        MyPagerAdapter adapter = new MyPagerAdapter(this);
        binding.page.setAdapter(adapter);
        setContentView(binding.getRoot());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.navigationBar.setOnItemSelectedListener((@NonNull MenuItem item) -> {
            binding.page.setCurrentItem(item.getOrder());
            return true;
        });


        binding.page.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.navigationBar.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.playerBar.unBind();
    }

    public static class MyPagerAdapter extends FragmentStateAdapter {
        public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return switch (position){
                case 0 -> new DiscoverFragment();
                case 1 -> new LocalDetailFragment();
                case 2 -> new RedMusicFragment();
                default -> new DiscoverFragment();
            };
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}