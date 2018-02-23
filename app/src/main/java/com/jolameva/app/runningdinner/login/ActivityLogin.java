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

import java.util.Arrays;


public class ActivityLogin extends AppCompatActivity {

    public static String name = "platzhalter name", surname = "platzhalter surname";
    public static Uri imageUrl;
    CallbackManager callbackManager;
    private LoginButton fbbtn_Login;
    private TextView tv_infoLogin;
    private ProfileTracker fbProfileTracker;


    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";

    private CallbackManager mCallbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCallbackManager = CallbackManager.Factory.create();

        fbbtn_Login = findViewById(R.id.fbbtn_login_button);

        // Set the initial permissions to request from the user while logging in
        fbbtn_Login.setReadPermissions(Arrays.asList(EMAIL, USER_POSTS));

        // Register a callback to respond to the user
        fbbtn_Login.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);
                finish();
            }

            @Override
            public void onError(FacebookException e) {
                // Handle exception
            }
        });
    }
}
