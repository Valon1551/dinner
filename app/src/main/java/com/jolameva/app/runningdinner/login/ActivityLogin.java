package com.jolameva.app.runningdinner.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.jolameva.app.runningdinner.BuildConfig;
import com.jolameva.app.runningdinner.MainActivity;
import com.jolameva.app.runningdinner.R;

import org.json.JSONObject;


public class ActivityLogin extends AppCompatActivity {

    public static String name = "platzhalter name", surname = "platzhalter surname";
    public static Uri imageUrl;
    CallbackManager callbackManager;
    private LoginButton fbbtn_Login;
    private TextView tv_infoLogin;
    private ProfileTracker fbProfileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        tv_infoLogin = findViewById(R.id.tv_logininfo);
        fbbtn_Login = findViewById(R.id.fbbtn_login_button);
        fbbtn_Login.setReadPermissions("email");

        // Callback Registrierung
        // WICHTIG: Es kann immer nur ein User pro App angemeldet sein. Entweder wir prüfen ob der AcccessToken != null ist oder wir sorgen dafür das der LoginButton verschwindet
        fbbtn_Login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Code
                if (Profile.getCurrentProfile() == null){
                    fbProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    if (BuildConfig.DEBUG) {
                                        FacebookSdk.setIsDebugEnabled(true);
                                        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

                                        Profile.getCurrentProfile().getId();
                                        Profile.getCurrentProfile().getFirstName();
                                        Profile.getCurrentProfile().getLastName();
                                        Profile.getCurrentProfile().getProfilePictureUri(250, 400);

                                        imageUrl = Profile.getCurrentProfile().getProfilePictureUri(200, 200);
                                        name = Profile.getCurrentProfile().getName();

                                    }
                                }
                            });
                            request.executeAsync();

                        }
                    };
                } else {
                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            if (BuildConfig.DEBUG) {
                                FacebookSdk.setIsDebugEnabled(true);
                                FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

                                Profile.getCurrentProfile().getId();
                                Profile.getCurrentProfile().getFirstName();
                                Profile.getCurrentProfile().getLastName();
                                Profile.getCurrentProfile().getProfilePictureUri(250, 400);

                                imageUrl = Profile.getCurrentProfile().getProfilePictureUri(200, 200);
                                name = Profile.getCurrentProfile().getName();

                            }
                        }
                    });
                    request.executeAsync();
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)){
            return;
        }
    }
}
