package com.banana.verywild.privilegemeeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

/**
 * Created by lineplus on 22/02/2018.
 */

public class KakaoSignupActivity extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalApplication.setCurrentActivity(this);
        requestMe();
    }

    void requestMe() {

        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("lhd_read", "onSessionClosed~" + errorResult);
                redirectLoginActivity();
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.d("lhd_read", "onFailure" + errorResult);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    Log.d("lhd_read", "onFailure~CLIENT_ERROR_CODE");
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onNotSignedUp() {
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(UserProfile result) {
                Log.d("lhd_read", "onSuccess~" + result);
                Log.d("lhd_read", "onSuccess~user~" + result.toString());
            }
        });
    }

    void redirectLoginActivity() {
        Intent t = new Intent(this, SampleLoginActivity.class);
        t.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(t);
        finish();
    }
}
