package com.example.lenovo.hoay;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.example.lenovo.hoay.Chat.CustomUserProvider;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by chanst on 2017/3/25.
 */

public class MyLeanCloudApp extends Application {

    private final String APP_ID = "fUXx8Ao50WQQMaKA7xTRs9ck-gzGzoHsz";
    private final String APP_KEY = "1IJfV0oHAqlp3hrmPBcIjVbf";
    @Override
    public void onCreate() {
        super.onCreate();
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"fUXx8Ao50WQQMaKA7xTRs9ck-gzGzoHsz","1IJfV0oHAqlp3hrmPBcIjVbf");
    }
}
