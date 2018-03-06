package com.jolameva.app.runningdinner.fbauth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.jolameva.app.runningdinner.MainActivity;
import com.jolameva.app.runningdinner.R;

import java.util.ArrayList;
import java.util.List;

public class AuthRunningActivity extends AppCompatActivity {

    private static final String TAG = "AuthUiActivity";

    private static final int RC_SIGN_IN = 100;

    public static Intent createIntent(Context context) {
        return new Intent(context, AuthRunningActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_running);

        signIn();

    }

    public void signIn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(getSelectedProviders())
                        .setIsSmartLockEnabled(true,
                                true)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startSignedInActivity(null);
            finish();
        }
    }

    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        // Successfully signed in
        if (resultCode == RESULT_OK) {
            startSignedInActivity(response);
            finish();
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                return;
            }

            if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                return;
            }

            Log.e(TAG, "Sign-in error: ", response.getError());
        }
    }

    private void startSignedInActivity(IdpResponse response) {
        startActivity(
                MainActivity.createIntent(
                        this,
                        response,
                        new MainActivity.SignedInConfig(
                                getSelectedProviders(),true,true)));
    }

    private List<AuthUI.IdpConfig> getSelectedProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();

            selectedProviders.add(
                    new AuthUI.IdpConfig.GoogleBuilder().setScopes(getGoogleScopes()).build());

            selectedProviders.add(new AuthUI.IdpConfig.FacebookBuilder()
                    .setPermissions(getFacebookPermissions())
                    .build());
        return selectedProviders;
    }


    private List<String> getFacebookPermissions() {
        List<String> result = new ArrayList<>();

            result.add("user_friends");
            result.add("public_profile");
            result.add("user_photos");
        return result;
    }

    private List<String> getGoogleScopes() {
        List<String> result = new ArrayList<>();
//        if (mGoogleScopeYoutubeData.isChecked()) {
//            result.add("https://www.googleapis.com/auth/youtube.readonly");
//        }
//        if (mGoogleScopeDriveFile.isChecked()) {
//            result.add(Scopes.DRIVE_FILE);
//        }
        return result;
    }
}
