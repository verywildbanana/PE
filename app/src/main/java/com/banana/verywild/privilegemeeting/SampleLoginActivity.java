package com.banana.verywild.privilegemeeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

public class SampleLoginActivity extends AppCompatActivity {

    private SessionCallback callback;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_login);
        Log.d("lhd_read", "SampleLoginActivity~onCreate");
        GlobalApplication.setCurrentActivity(this);
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lhd_read", "SampleLoginActivity~onDestroy");
        Session.getCurrentSession().removeCallback(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("lhd_read", "SampleLoginActivity~onActivityResult");
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Log.d("lhd_read", "onSessionOpened");
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.d("lhd_read", "onSessionOpenFailed");
        }
    }

    void redirectSignupActivity() {
        Intent t = new Intent(this, KakaoSignupActivity.class);
        t.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(t);
        finish();
    }
}