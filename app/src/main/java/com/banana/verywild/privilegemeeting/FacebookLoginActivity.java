package com.banana.verywild.privilegemeeting;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lineplus on 23/02/2018.
 */

public class FacebookLoginActivity extends Activity {
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";

    private CallbackManager mCallbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        mCallbackManager = CallbackManager.Factory.create();

        LoginButton mLoginButton = (LoginButton) findViewById(R.id.login_button);

        // Set the initial permissions to request from the user while logging in
        mLoginButton.setReadPermissions(Arrays.asList(EMAIL, PUBLIC_PROFILE));

        // Register a callback to respond to the user
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("lhd_read", "facebook~onSuccess");
            }

            @Override
            public void onCancel() {
                Log.d("lhd_read", "facebook~onCancel");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("lhd_read", "facebook~onError~" + e.toString());
            }
        });


        Button gotoProfileButton = (Button) findViewById(R.id.btn_profile);
        gotoProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("lhd_read", "facebook~AccessToken.getCurrentAccessToken()~" + AccessToken.getCurrentAccessToken());
                if (AccessToken.getCurrentAccessToken() == null) {
                    return;
                }
                Bundle params = new Bundle();
                params.putString("fields", "picture,name,id,email,permissions");
                String ME_ENDPOINT = "/me";
                GraphRequest request = new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        ME_ENDPOINT,
                        params,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                User user = null;
                                try {
                                    JSONObject userObj = response.getJSONObject();
                                    if (userObj == null) {
                                        return;
                                    }
                                    user = jsonToUser(userObj);

                                } catch (JSONException e) {
                                    // Handle exception ...
                                }
                            }
                        }
                );
                request.executeAsync();
            }
        });

        String pattern = "(([0-9]+)?(?i)h)?(([0-9]+)?(?i)m)?";
        String input = "100H0m";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        if (m.matches()) {
            Log.d("lhd_read", "LineVideoView~matcher~group~" + m.group(0));
            Log.d("lhd_read", "LineVideoView~matcher~group1~" + m.group(1));
            Log.d("lhd_read", "LineVideoView~matcher~group2~" + m.group(2));
            Log.d("lhd_read", "LineVideoView~matcher~group2~" + m.group(3));
            Log.d("lhd_read", "LineVideoView~matcher~group2~" + m.group(4));
        }
        if (m.hitEnd()) {
            Log.d("lhd_read", "LineVideoView~hitEnd~group~" + m.group());
//            Log.d("lhd_read", "LineVideoView~hitEnd~group1~" + m.group(1));
//            Log.d("lhd_read", "LineVideoView~hitEnd~group2~" + m.group(2));
        }
        Log.d("lhd_read", "LineVideoView~matcher~" + m.find());


//        String time = "30m20s".replace("h", ":").replace("m", ":").replace("s", ":");


        String time = "30h20s";

        String[] splitTime = time.split("m");
//
//
//        String sec = null;
//        String minute = null;
//        String hour = null;
//
//        for (int i = splitTime.length - 1; i >= 0; i--) {
//            Log.d("lhd_read", "splitTime~" + splitTime[i]);
//        }
    }


    private User jsonToUser(JSONObject user) throws JSONException {

        Log.d("lhd_read", "facebook~jsonToUser~" + user.toString());

        Uri picture = Uri.parse(user.getJSONObject("picture").getJSONObject("data").getString
                ("url"));
        String name = user.getString("name");
        String id = user.getString("id");
        String email = null;
        if (user.has("email")) {
            email = user.getString("email");
        }

        // Build permissions display string
        StringBuilder builder = new StringBuilder();
        JSONArray perms = user.getJSONObject("permissions").getJSONArray("data");
        builder.append("Permissions:\n");
        for (int i = 0; i < perms.length(); i++) {
            builder.append(perms.getJSONObject(i).get("permission")).append(": ").append(perms
                    .getJSONObject(i).get("status")).append("\n");
        }
        String permissions = builder.toString();

        return new User(picture, name, id, email, permissions);
    }

    class User {
        private final Uri picture;
        private final String name;
        private final String id;
        private final String email;
        private final String permissions;

        public User(Uri picture, String name,
                    String id, String email, String permissions) {
            this.picture = picture;
            this.name = name;
            this.id = id;
            this.email = email;
            this.permissions = permissions;
        }

        public Uri getPicture() {
            return picture;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getPermissions() {
            return permissions;
        }
    }
}

