package com.banana.verywild.privilegemeeting;

import android.app.Activity;
import android.app.Application;

import com.facebook.FacebookSdk;
import com.kakao.auth.KakaoSDK;

/**
 * Created by lineplus on 22/02/2018.
 */

public class GlobalApplication extends Application {

    private static GlobalApplication instance;
    private static Activity currentActivity;

    public static GlobalApplication getInstance() {
        return instance;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalApplication.instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}